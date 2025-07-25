package Controlador;

import Modelo.EmailSender;
import Modelo.Modelo_ProgresoUnidad2;
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
        int result = fileChooser.showSaveDialog(vista);
        if (result == JFileChooser.APPROVE_OPTION) {
            File destino = fileChooser.getSelectedFile();
            try {
                java.nio.file.Files.copy(
                        certificadoGenerado.toPath(),
                        destino.toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING
                );
                JOptionPane.showMessageDialog(vista, "Certificado descargado en: " + destino.getAbsolutePath());
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
            String nombreUsuario = dashboard.getLblNombre().getText();
            if (nombreUsuario == null || nombreUsuario.isEmpty()) {
                nombreUsuario = "Estudiante Destacado";
            }

            certificadoGenerado = File.createTempFile("certificado", ".pdf");

            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(certificadoGenerado));
            document.open();

            // Título
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 24, Font.BOLD);
            Paragraph titulo = new Paragraph("CERTIFICADO DE FINALIZACIÓN", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(Chunk.NEWLINE);

            // Subtítulo
            Font subFont = new Font(Font.FontFamily.HELVETICA, 16, Font.NORMAL);
            Paragraph subtitulo = new Paragraph("Se otorga el presente certificado a:", subFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitulo);

            document.add(Chunk.NEWLINE);

            // Nombre usuario
            Font nombreFont = new Font(Font.FontFamily.HELVETICA, 22, Font.BOLD, BaseColor.BLUE);
            Paragraph nombre = new Paragraph(nombreUsuario, nombreFont);
            nombre.setAlignment(Element.ALIGN_CENTER);
            document.add(nombre);

            document.add(Chunk.NEWLINE);

            // Texto final
            Paragraph texto = new Paragraph("Por haber completado exitosamente el curso de Kwichua Básico.", subFont);
            texto.setAlignment(Element.ALIGN_CENTER);
            document.add(texto);

            document.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al generar certificado: " + ex.getMessage());
        }
    }

    private boolean verificarCompletitudCurso() {
        String correo = dashboard.getCorreoUsuario();
        int progreso = Modelo_ProgresoUnidad2.obtenerProgreso(correo);

        if (progreso < 100) {
            JOptionPane.showMessageDialog(vista,
                    "⚠️ Aún no has completado el curso\n" +
                            "Completa todas las unidades para obtener tu certificado",
                    "Curso Incompleto",
                    JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        return true;
    }
    
}
