package Controlador;

import Modelo.Reportes;
import Vista.Admin.Vista_Reportes;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import Conexion.Conexion;
import Vista.Admin.DashboardAdmin;

public class ControladorReportes {

    private final Vista_Reportes vista;
    private final Reportes modelo;
    private final DashboardAdmin dashboardAdmin;
    private final String rutaLogo;
    private final Connection conexion;

    public ControladorReportes(Vista_Reportes vista, DashboardAdmin dashboardAdmin) {
        this.vista = vista;
        this.dashboardAdmin = dashboardAdmin;
        this.conexion = Conexion.conectar();
        this.modelo = new Reportes(conexion);
        this.rutaLogo = "src/Imagenes/Logo178x174.png"; // Ajusta esta ruta según sea necesario

        configurarListeners();
    }

    private void configurarListeners() {
        vista.getBtnProgresoGeneral().addActionListener(e -> generarReporteProgresoGeneral());
        vista.getBtnRendimientoUnidad().addActionListener(e -> generarReporteRendimientoUnidad());
        vista.getBtnUltimaActividad().addActionListener(e -> generarReporteUsuariosActividad());
    }

    private void generarReporteProgresoGeneral() {
        generarReporte(
                "Progreso General de Estudiantes",
                () -> modelo.obtenerProgresoGeneral(vista.getFechaInicio(), vista.getFechaFin()),
                new String[]{"Nombre", "Unidad", "Lecciones", "Actividades", "Calificación", "Última Actualización"},
                "Reporte_Progreso_General.pdf"
        );
    }

    private void generarReporteRendimientoUnidad() {
        generarReporte(
                "Estadísticas de Rendimiento por Unidad",
                () -> modelo.obtenerEstadisticasUnidad(vista.getFechaInicio(), vista.getFechaFin()),
                new String[]{"Unidad", "Cantidad Estudiantes", "Promedio Calificaciones", "Última Actividad"},
                "Reporte_Rendimiento_Unidad.pdf"
        );
    }

    private void generarReporteUsuariosActividad() {
        generarReporte(
                "Listado de Usuarios con Última Actividad",
                () -> modelo.obtenerUsuariosUltimaActividad(vista.getFechaInicio(), vista.getFechaFin()),
                new String[]{"Nombre", "Correo", "Rol", "Última Actividad", "Fecha Registro"},
                "Reporte_Usuarios_Actividad.pdf"
        );
    }

    @FunctionalInterface
    private interface ConsultaReporte {
        ResultSet ejecutar() throws SQLException;
    }

    private void generarReporte(String titulo, ConsultaReporte consulta, String[] columnas, String nombreArchivo) {
        try {
            // Validación de fechas
            if (!validarFechas()) {
                return;
            }

            // Validar si hay datos antes de continuar
            if (!validarExistenciaDatos(consulta)) {
                JOptionPane.showMessageDialog(vista, 
                    "No se encontraron datos para generar el reporte en el rango de fechas seleccionado", 
                    "Datos no encontrados", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            // Preguntar por la orientación
            int opcion = JOptionPane.showOptionDialog(vista,
                    "Seleccione la orientación del reporte:",
                    "Orientación del Reporte",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{"Vertical", "Horizontal"},
                    "Vertical");

            boolean vertical = (opcion == 0); // 0=Vertical, 1=Horizontal
            File archivo = seleccionarUbicacionArchivo(nombreArchivo);
            if (archivo == null) {
                return;
            }

            ResultSet datos = consulta.ejecutar();
            generarPDF(titulo, datos, columnas, archivo, vertical);

            JOptionPane.showMessageDialog(vista, "Reporte generado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCamposFecha();
        } catch (Exception e) {
            manejarError(e);
        }
    }

    private boolean validarExistenciaDatos(ConsultaReporte consulta) throws SQLException {
        try (ResultSet rs = consulta.ejecutar()) {
            return rs.next(); // Retorna true si hay al menos un registro
        }
    }

    private boolean validarFechas() {
        if (vista.getFechaInicio() == null || vista.getFechaFin() == null) {
            JOptionPane.showMessageDialog(vista, 
                "Para generar un reporte necesita seleccionar una fecha de inicio y una de fin.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (vista.getFechaInicio().after(vista.getFechaFin())) {
            JOptionPane.showMessageDialog(vista, 
                "La fecha de inicio no puede ser posterior a la fecha final", 
                "Error en fechas", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private File seleccionarUbicacionArchivo(String nombreArchivo) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar reporte");
        fileChooser.setSelectedFile(new File(nombreArchivo));

        if (fileChooser.showSaveDialog(vista) == JFileChooser.APPROVE_OPTION) {
            File archivo = fileChooser.getSelectedFile();
            if (!archivo.getName().toLowerCase().endsWith(".pdf")) {
                return new File(archivo.getAbsolutePath() + ".pdf");
            }
            return archivo;
        }
        return null;
    }

    private void generarPDF(String titulo, ResultSet datos, String[] columnas, File archivo, boolean vertical) throws Exception {
        Document documento = new Document(vertical ? PageSize.A4 : PageSize.A4.rotate());
        PdfWriter.getInstance(documento, new FileOutputStream(archivo));
        documento.open();

        agregarLogo(documento);
        agregarTitulo(documento, titulo);

        PdfPTable tabla = new PdfPTable(columnas.length);
        tabla.setWidthPercentage(vertical ? 95 : 100);
        tabla.setSpacingBefore(10f);

        if (vertical) {
            tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.setWidths(calcularAnchosColumnas(columnas.length));
        }

        agregarEncabezadosTabla(tabla, columnas);
        agregarDatosTabla(tabla, datos, columnas.length);

        documento.add(tabla);
        agregarPiePagina(documento);
        documento.close();
        datos.close();
    }

    private float[] calcularAnchosColumnas(int numColumnas) {
        float[] anchos = new float[numColumnas];
        float anchoBase = 100f / numColumnas;
        for (int i = 0; i < numColumnas; i++) {
            anchos[i] = anchoBase;
        }
        return anchos;
    }

    private void agregarLogo(Document documento) {
        try {
            Image logo = Image.getInstance(rutaLogo);
            logo.scaleToFit(100, 100);
            logo.setAlignment(Element.ALIGN_CENTER);
            documento.add(logo);
            documento.add(Chunk.NEWLINE);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }
    }

    private void agregarTitulo(Document documento, String titulo) throws DocumentException {
        Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.DARK_GRAY);
        Paragraph parrafoTitulo = new Paragraph(titulo, fontTitulo);
        parrafoTitulo.setAlignment(Element.ALIGN_CENTER);
        parrafoTitulo.setSpacingAfter(20);
        documento.add(parrafoTitulo);

        Font fontSubtitulo = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GRAY);
        String periodo = "Período: " + formatearFecha(vista.getFechaInicio()) + " a " + formatearFecha(vista.getFechaFin());
        Paragraph parrafoPeriodo = new Paragraph(periodo, fontSubtitulo);
        parrafoPeriodo.setAlignment(Element.ALIGN_CENTER);
        parrafoPeriodo.setSpacingAfter(20);
        documento.add(parrafoPeriodo);
    }

    private String formatearFecha(Date fecha) {
        return fecha != null ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "N/A";
    }

    private void agregarEncabezadosTabla(PdfPTable tabla, String[] columnas) {
        Font fontHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
        for (String columna : columnas) {
            PdfPCell celda = new PdfPCell(new Phrase(columna, fontHeader));
            celda.setBackgroundColor(new BaseColor(70, 130, 180)); // SteelBlue
            celda.setPadding(5);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(celda);
        }
    }

    private void agregarDatosTabla(PdfPTable tabla, ResultSet datos, int numColumnas) throws SQLException {
        Font fontData = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
        while (datos.next()) {
            for (int i = 1; i <= numColumnas; i++) {
                String valor = datos.getString(i) != null ? datos.getString(i) : "N/A";
                PdfPCell celda = new PdfPCell(new Phrase(valor, fontData));
                celda.setPadding(5);
                tabla.addCell(celda);
            }
        }
    }

    private void agregarPiePagina(Document documento) throws DocumentException {
        String nombreUsuario = dashboardAdmin.getNombreUsuario();
        String fechaGeneracion = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        Paragraph footer = new Paragraph(
                "Generado por: " + nombreUsuario + "\n"
                + "Fecha: " + fechaGeneracion + "\n"
                + "Sistema Inti - Proyecto Kiwcha",
                new Font(Font.FontFamily.HELVETICA, 8, Font.ITALIC, BaseColor.GRAY)
        );
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(20);
        documento.add(footer);
    }

    private void manejarError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(
                vista,
                "Error al generar reporte: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void limpiarCamposFecha() {
        vista.setFechaInicio(null);
        vista.setFechaFin(null);
    }
}

