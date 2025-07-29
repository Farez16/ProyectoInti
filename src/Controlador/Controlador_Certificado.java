package Controlador;

import Modelo.EmailSender;
import Modelo.Modelo_Progreso_Usuario;
import Vista.Estudiante.Dashboard;
import Vista.Vista_Certificado;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.Desktop;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;

public class Controlador_Certificado {

    private final Vista_Certificado vista;
    private final Dashboard dashboard;
    private File certificadoGenerado;

    public Controlador_Certificado(Vista_Certificado vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;
        agregarEventos();
        verificarBloqueoImagen(); // Verificar estado inicial de la imagen
    }

    private void agregarEventos() {
        vista.getjButton1VisualizarCertificado().addActionListener(e -> visualizarCertificado());
        vista.getjButton1DescargarCertificado1().addActionListener(e -> descargarCertificado());
        vista.getjButton1REENVIARCERTIFICADOALCORREO().addActionListener(e -> enviarCertificado());
    }

    private void visualizarCertificado() {
        try {
            generarCertificado();
            if (certificadoGenerado != null && certificadoGenerado.exists()) {
                Desktop.getDesktop().open(certificadoGenerado);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al abrir el certificado: " + ex.getMessage());
        }
    }

    private void descargarCertificado() {
        generarCertificado();
        if (certificadoGenerado == null || !certificadoGenerado.exists()) {
            JOptionPane.showMessageDialog(vista, "Primero debe generar el certificado");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File("Certificado_Kwichua.pdf"));
        fileChooser.setDialogTitle("Guardar certificado en su escritorio");
        int result = fileChooser.showSaveDialog(vista);
        if (result == JFileChooser.APPROVE_OPTION) {
            File destino = fileChooser.getSelectedFile();
            try {
                java.nio.file.Files.copy(
                        certificadoGenerado.toPath(),
                        destino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
                JOptionPane.showMessageDialog(vista, "Certificado guardado en: " + destino.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al guardar: " + ex.getMessage());
            }
        }
    }

    private void enviarCertificado() {
        generarCertificado();
        if (certificadoGenerado == null || !certificadoGenerado.exists()) {
            JOptionPane.showMessageDialog(vista, "Primero debe generar el certificado");
            return;
        }

        String correoRegistrado = dashboard.getCorreoUsuario();
        String mensaje = "¿Deseas enviar el certificado a tu correo registrado (" + correoRegistrado + ")?\n"
                + "Selecciona 'No' para ingresar otro correo.";
        int opcion = JOptionPane.showConfirmDialog(vista, mensaje, "Enviar certificado", JOptionPane.YES_NO_OPTION);

        String correoDestino = opcion == JOptionPane.YES_OPTION
                ? correoRegistrado
                : JOptionPane.showInputDialog(vista, "Ingresa el correo electrónico:");

        if (correoDestino == null || correoDestino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debes ingresar un correo válido");
            return;
        }

        String asunto = "Certificado de Finalización - Kwichua";
        String cuerpo = "¡Felicidades! Adjunto encontrarás tu certificado de finalización del curso de Kwichua.";
        boolean enviado = EmailSender.enviarCorreoConAdjunto(correoDestino, asunto, cuerpo, certificadoGenerado.getAbsolutePath());

        if (enviado) {
            JOptionPane.showMessageDialog(vista, "Certificado enviado a: " + correoDestino);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al enviar el correo");
        }
    }

    private void generarCertificado() {
    if (!verificarCompletitudCurso()) return;

    try {
        // Pedimos el nombre del usuario
        String nombreUsuario = JOptionPane.showInputDialog(vista, "Ingresa tus nombres completos:");
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debes ingresar un nombre válido.");
            return;
        }

        // Ruta del certificado base en horizontal
        String rutaBase = "C:\\Users\\diego\\Desktop\\ProyectoInti\\src\\Certificado\\Certificado Kwichua.pdf";
        certificadoGenerado = File.createTempFile("certificado_personalizado_", ".pdf");

        // Leer el PDF original
        PdfReader reader = new PdfReader(rutaBase);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(certificadoGenerado));
        PdfContentByte over = stamper.getOverContent(1);

        // Configurar fuente y estilo
        BaseFont fuente = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, BaseFont.EMBEDDED);
        over.beginText();
        over.setFontAndSize(fuente, 28);
        over.setColorFill(BaseColor.BLACK);

        // Centrar texto en PDF horizontal (A4 Landscape)
        float x = 842 / 2f;  // centro horizontal exacto
        float y = 595 / 2f;  // centro vertical exacto

        over.showTextAligned(Element.ALIGN_CENTER, nombreUsuario, x, y, 0);
        over.endText();

        stamper.close();
        reader.close();

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(vista, "Error al generar certificado personalizado: " + ex.getMessage());
    }
}


    private boolean verificarCompletitudCurso() {
        String correo = dashboard.getCorreoUsuario();
        int idUsuario = Modelo_Progreso_Usuario.obtenerIdUsuarioPorCorreo(correo);
        
        if (idUsuario <= 0) {
            JOptionPane.showMessageDialog(vista, "Usuario no encontrado");
            return false;
        }
        
        // Verificar si todas las unidades están completas
        boolean[] unidadesCompletas = new boolean[4];
        for (int i = 1; i <= 4; i++) {
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, i);
            unidadesCompletas[i-1] = (progreso != null && progreso.isEvaluacionAprobada());
        }

        boolean cursoCompleto = unidadesCompletas[0] && unidadesCompletas[1] && 
                               unidadesCompletas[2] && unidadesCompletas[3];

        if (!cursoCompleto) {
            JOptionPane.showMessageDialog(vista,
                    "⚠️ Aún no has completado todas las unidades del curso\n" +
                    "Completa todas las unidades para obtener tu certificado",
                    "Curso Incompleto",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }
    
    // Verificar si se debe bloquear la imagen
    private void verificarBloqueoImagen() {
        String correo = dashboard.getCorreoUsuario();
        int idUsuario = Modelo_Progreso_Usuario.obtenerIdUsuarioPorCorreo(correo);
        
        if (idUsuario > 0) {
            // Verificar si todas las unidades están completas
            boolean todasCompletas = true;
            for (int i = 1; i <= 4; i++) {
                Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, i);
                if (progreso == null || !progreso.isEvaluacionAprobada()) {
                    todasCompletas = false;
                    break;
                }
            }
            // Bloquear/desbloquear imagen según estado
            vista.getjLabel2() .setEnabled(todasCompletas);
        }
    }
}