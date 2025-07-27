/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_Evaluaciones;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import VistasUnidad3.Vista_EvaluacionU3;
import VistasUnidad3.Vista_Unidad3;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Controlador para la evaluación de la Unidad 3
 * Maneja la lógica de presentación de preguntas, validación de respuestas
 * y finalización de la evaluación de la Unidad 3 (Familia y Vestimenta Kichwa)
 * 
 * @author Johnny
 */
public class Controlador_EvaluacionU3 {

    private final Vista_EvaluacionU3 vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idUnidad = 3; // Unidad 3
    private final Controlador_Unidades controladorUnidades;

    private List<Modelo_Evaluaciones> preguntas;
    private int indicePreguntaActual;
    private int respuestasCorrectas;
    private boolean primeraPreguntaMostrada = false;

    /**
     * Constructor del controlador de evaluación U3
     * 
     * @param vista Vista de la evaluación U3
     * @param controladorDashboard Controlador del dashboard principal
     * @param conn Conexión a la base de datos
     * @param correo Correo del usuario actual
     * @param controladorUnidades Controlador de unidades
     */
    public Controlador_EvaluacionU3(Vista_EvaluacionU3 vista, ControladorDashboard controladorDashboard,
            Connection conn, String correo, Controlador_Unidades controladorUnidades) {
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;
        this.correo = correo;
        this.controladorUnidades = controladorUnidades;

        System.out.println("🎯 Iniciando evaluación Unidad 3 para usuario: " + correo);

        // Validar que el usuario puede acceder a la evaluación
        if (!validarAccesoEvaluacion()) {
            return;
        }

        // Cargar preguntas de la Unidad 3
        this.preguntas = Modelo_Evaluaciones.obtenerPreguntasPorUnidad(conn, idUnidad);
        this.indicePreguntaActual = 0;
        this.respuestasCorrectas = 0;

        System.out.println("📚 Cargadas " + preguntas.size() + " preguntas para la evaluación U3");

        configurarEventos();
        vista.getjButtonFINALIZARUNIDAD().setVisible(false); // Ocultar botón al iniciar
        
        SwingUtilities.invokeLater(() -> {
            mostrarPreguntaActual();
        });
    }

    /**
     * Valida si el usuario puede acceder a la evaluación
     * Requiere haber completado todas las lecciones y actividades de la unidad
     */
    private boolean validarAccesoEvaluacion() {
        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, idUnidad);
        
        // Para Unidad 3: requiere 2 lecciones y 2 actividades completadas
        int leccionesRequeridas = 2;
        int actividadesRequeridas = 2;
        
        if (progreso == null || progreso.getLeccionesCompletadas() < leccionesRequeridas || 
            progreso.getActividadesCompletadas() < actividadesRequeridas) {
            
            JOptionPane.showMessageDialog(vista, 
                "<html><body style='width: 300px;'>" +
                "<h3>🚫 Acceso Denegado</h3>" +
                "<p>Para acceder a la evaluación de la <strong>Unidad 3</strong> debes completar:</p>" +
                "<ul>" +
                "<li>Las 2 lecciones de la unidad (Familia y Vestimenta)</li>" +
                "<li>Las 2 actividades de la unidad</li>" +
                "</ul>" +
                "<p><strong>Tu progreso actual:</strong></p>" +
                "<ul>" +
                "<li>Lecciones: " + (progreso != null ? progreso.getLeccionesCompletadas() : 0) + "/" + leccionesRequeridas + "</li>" +
                "<li>Actividades: " + (progreso != null ? progreso.getActividadesCompletadas() : 0) + "/" + actividadesRequeridas + "</li>" +
                "</ul>" +
                "</body></html>",
                "Evaluación no disponible", 
                JOptionPane.WARNING_MESSAGE);
            
            // Regresar a la unidad
            SwingUtilities.invokeLater(() -> {
                irAUnidad3();
            });
            return false;
        }
        
        return true;
    }

    /**
     * Configura los eventos de los botones de la vista
     */
    private void configurarEventos() {
        vista.getjButtonSIGUIENTE().addActionListener(e -> validarRespuestaYContinuar());
        vista.getjButtonFINALIZARUNIDAD().addActionListener(e -> finalizarEvaluacion());
        
        System.out.println("⚙️ Eventos configurados para evaluación U3");
    }

    /**
     * Muestra la pregunta actual en la interfaz
     */
    private void mostrarPreguntaActual() {
        if (indicePreguntaActual >= preguntas.size()) {
            finalizarEvaluacion();
            return;
        }

        Modelo_Evaluaciones pregunta = preguntas.get(indicePreguntaActual);
        vista.getjTextAreaPregunta().setText(pregunta.getPregunta());

        // Opciones mezcladas para mayor variedad
        List<String> opciones = new ArrayList<>();
        opciones.add(pregunta.getOpcionA());
        opciones.add(pregunta.getOpcionB());
        opciones.add(pregunta.getOpcionC());
        Collections.shuffle(opciones);

        vista.getjRadioButtonA().setText(opciones.get(0));
        vista.getjRadioButtonB().setText(opciones.get(1));
        vista.getjRadioButtonC().setText(opciones.get(2));

        vista.getButtonGroup1().clearSelection();
        vista.getjLabelProgresoDePregunta().setText("Pregunta " + (indicePreguntaActual + 1) + " de " + preguntas.size());

        // Manejo de imágenes
        if (!primeraPreguntaMostrada) {
            vista.getjLabelImagen().addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if (vista.getjLabelImagen().getWidth() > 0 && vista.getjLabelImagen().getHeight() > 0) {
                        mostrarImagenPregunta(pregunta.getRutaImagen());
                        vista.getjLabelImagen().removeComponentListener(this);
                        primeraPreguntaMostrada = true;
                    }
                }
            });
        } else {
            mostrarImagenPregunta(pregunta.getRutaImagen());
        }
        
        System.out.println("❓ Mostrando pregunta " + (indicePreguntaActual + 1) + ": " + pregunta.getPregunta().substring(0, Math.min(50, pregunta.getPregunta().length())) + "...");
    }

    /**
     * Muestra la imagen asociada a la pregunta si existe
     */
    private void mostrarImagenPregunta(String rutaImagen) {
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            URL imageUrl = getClass().getResource("/" + rutaImagen);
            if (imageUrl != null) {
                int width = vista.getjLabelImagen().getWidth();
                int height = vista.getjLabelImagen().getHeight();
                System.out.println("🖼️ Cargando imagen: " + rutaImagen + " (" + width + "x" + height + ")");
                
                if (width > 0 && height > 0) {
                    ImageIcon icono = new ImageIcon(imageUrl);
                    Image img = icono.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    vista.getjLabelImagen().setIcon(new ImageIcon(img));
                } else {
                    System.err.println("⚠️ No se puede escalar la imagen porque el tamaño del JLabel es 0x0");
                    vista.getjLabelImagen().setIcon(new ImageIcon(imageUrl));
                }
            } else {
                System.err.println("❌ La imagen NO se encontró como recurso: " + rutaImagen);
                vista.getjLabelImagen().setIcon(null);
            }
        } else {
            vista.getjLabelImagen().setIcon(null);
        }
    }

    /**
     * Valida la respuesta seleccionada y continúa a la siguiente pregunta
     */
    private void validarRespuestaYContinuar() {
        String seleccion = obtenerRespuestaSeleccionada();
        if (seleccion == null) {
            JOptionPane.showMessageDialog(vista, 
                "<html><body style='width: 250px;'>" +
                "<h3>⚠️ Selección requerida</h3>" +
                "<p>Debes seleccionar una opción antes de continuar.</p>" +
                "</body></html>",
                "Respuesta requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        Modelo_Evaluaciones pregunta = preguntas.get(indicePreguntaActual);
        String respuestaCorrecta = obtenerTextoRespuestaCorrecta(pregunta);

        boolean esCorrecta = seleccion.equals(respuestaCorrecta);
        if (esCorrecta) {
            respuestasCorrectas++;
            System.out.println("✅ Respuesta correcta! Total: " + respuestasCorrectas);
        } else {
            System.out.println("❌ Respuesta incorrecta. Correcta era: " + respuestaCorrecta);
        }

        indicePreguntaActual++;

        if (indicePreguntaActual < preguntas.size()) {
            mostrarPreguntaActual();
        } else {
            mostrarResultadosFinales();
        }
    }

    /**
     * Obtiene la respuesta seleccionada por el usuario
     */
    private String obtenerRespuestaSeleccionada() {
        if (vista.getjRadioButtonA().isSelected()) {
            return vista.getjRadioButtonA().getText();
        }
        if (vista.getjRadioButtonB().isSelected()) {
            return vista.getjRadioButtonB().getText();
        }
        if (vista.getjRadioButtonC().isSelected()) {
            return vista.getjRadioButtonC().getText();
        }
        return null;
    }

    /**
     * Obtiene el texto de la respuesta correcta basado en la letra
     */
    private String obtenerTextoRespuestaCorrecta(Modelo_Evaluaciones pregunta) {
        switch (pregunta.getRespuestaCorrecta()) {
            case 'A':
                return pregunta.getOpcionA();
            case 'B':
                return pregunta.getOpcionB();
            case 'C':
                return pregunta.getOpcionC();
            default:
                return "";
        }
    }

    /**
     * Muestra los resultados finales de la evaluación
     */
    private void mostrarResultadosFinales() {
        vista.getjButtonSIGUIENTE().setVisible(false);
        vista.getjButtonFINALIZARUNIDAD().setVisible(true);

        double porcentaje = (double) respuestasCorrectas / preguntas.size() * 100;
        int minimoAprobacion = 11; // 70% de 15 preguntas = 10.5, redondeado a 11
        
        System.out.println("📊 Evaluación completada - Respuestas correctas: " + respuestasCorrectas + "/" + preguntas.size() + " (" + String.format("%.1f", porcentaje) + "%)");

        if (respuestasCorrectas >= minimoAprobacion) {
            // Evaluación aprobada
            JOptionPane.showMessageDialog(vista, 
                "<html><body style='width: 350px;'>" +
                "<h2>🎉 ¡Felicidades!</h2>" +
                "<p><strong>Has aprobado la evaluación de la Unidad 3</strong></p>" +
                "<hr>" +
                "<p><strong>Resultados:</strong></p>" +
                "<ul>" +
                "<li>Respuestas correctas: <strong>" + respuestasCorrectas + " de " + preguntas.size() + "</strong></li>" +
                "<li>Porcentaje: <strong>" + String.format("%.1f", porcentaje) + "%</strong></li>" +
                "</ul>" +
                "<p>🏆 Has completado exitosamente el aprendizaje sobre <strong>Familia y Vestimenta Kichwa</strong>.</p>" +
                "</body></html>",
                "Evaluación Aprobada", 
                JOptionPane.INFORMATION_MESSAGE);

            actualizarProgresoUsuario(true);
        } else {
            // Evaluación no aprobada
            JOptionPane.showMessageDialog(vista, 
                "<html><body style='width: 350px;'>" +
                "<h3>📚 Evaluación no aprobada</h3>" +
                "<p>No alcanzaste el mínimo requerido para aprobar.</p>" +
                "<hr>" +
                "<p><strong>Resultados:</strong></p>" +
                "<ul>" +
                "<li>Respuestas correctas: <strong>" + respuestasCorrectas + " de " + preguntas.size() + "</strong></li>" +
                "<li>Porcentaje: <strong>" + String.format("%.1f", porcentaje) + "%</strong></li>" +
                "<li>Mínimo requerido: <strong>" + minimoAprobacion + " respuestas (70%)</strong></li>" +
                "</ul>" +
                "<p>💪 Revisa las lecciones y vuelve a intentarlo.</p>" +
                "</body></html>",
                "Evaluación no aprobada", 
                JOptionPane.WARNING_MESSAGE);

            reiniciarEvaluacion();
        }
    }

    /**
     * Actualiza el progreso del usuario en la base de datos
     */
    private void actualizarProgresoUsuario(boolean aprobada) {
        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, idUnidad);

        if (progreso == null) {
            progreso = new Modelo_Progreso_Usuario(0, idUsuario, idUnidad, 0, 0, aprobada, respuestasCorrectas, LocalDateTime.now());
            Modelo_Progreso_Usuario.guardarProgreso(progreso);
            System.out.println("💾 Progreso creado para usuario " + idUsuario + " en unidad " + idUnidad);
        } else {
            progreso.setEvaluacionAprobada(aprobada);
            progreso.setCalificacion(respuestasCorrectas);
            progreso.setFechaActualizacion(LocalDateTime.now());
            Modelo_Progreso_Usuario.actualizarProgreso(progreso);
            System.out.println("💾 Progreso actualizado para usuario " + idUsuario + " en unidad " + idUnidad);
        }
    }

    /**
     * Reinicia la evaluación para un nuevo intento
     */
    private void reiniciarEvaluacion() {
        this.preguntas = Modelo_Evaluaciones.obtenerPreguntasPorUnidad(conn, idUnidad);
        this.indicePreguntaActual = 0;
        this.respuestasCorrectas = 0;

        vista.getjButtonSIGUIENTE().setVisible(true);
        vista.getjButtonFINALIZARUNIDAD().setVisible(false);

        System.out.println("🔄 Evaluación reiniciada - Nuevas preguntas cargadas");
        mostrarPreguntaActual();
    }

    /**
     * Finaliza la evaluación y regresa a la vista de Unidad 3
     */
    private void finalizarEvaluacion() {
        System.out.println("🏁 Finalizando evaluación U3 - Regresando a Unidad 3");
        irAUnidad3();
    }

    /**
     * Navega de regreso a la vista de Unidad 3
     */
    private void irAUnidad3() {
        Vista_Unidad3 vistaUnidad3 = new Vista_Unidad3();
        new Controlador_Unidad3(vistaUnidad3, conn, controladorDashboard, correo, controladorUnidades);
        controladorDashboard.getVista().mostrarVista(vistaUnidad3);
    }
}
