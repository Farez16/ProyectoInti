package Controlador;

import Modelo.Modelo_Evaluaciones;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vistas_Unidad1.Vista_EvaluacionU1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
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

public class Controlador_Evaluaciones {

    private final Vista_EvaluacionU1 vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idUnidad;
    private final Controlador_Unidades controladorUnidades;

    private List<Modelo_Evaluaciones> preguntas;
    private int indicePreguntaActual;
    private int respuestasCorrectas;
     private boolean primeraPreguntaMostrada = false;

    public Controlador_Evaluaciones(Vista_EvaluacionU1 vista, ControladorDashboard controladorDashboard,
            Connection conn, String correo, int idUnidad, Controlador_Unidades controladorUnidades) {
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;
        this.correo = correo;
        this.idUnidad = idUnidad;
        this.controladorUnidades = controladorUnidades;

        this.preguntas = Modelo_Evaluaciones.obtenerPreguntasPorUnidad(conn, idUnidad);
        this.indicePreguntaActual = 0;
        this.respuestasCorrectas = 0;

        configurarEventos();
        vista.getjButtonFINALIZARUNIDAD().setVisible(false); // Ocultar botón al iniciar
        SwingUtilities.invokeLater(() -> {
            mostrarPreguntaActual();
        });
    }

    private void configurarEventos() {
        vista.getjButtonSIGUIENTE().addActionListener(e -> validarRespuestaYContinuar());
        vista.getjButtonFINALIZARUNIDAD().addActionListener(e -> finalizarEvaluacion());
    }



private void mostrarPreguntaActual() {
    if (indicePreguntaActual >= preguntas.size()) {
        finalizarEvaluacion();
        return;
    }

    Modelo_Evaluaciones pregunta = preguntas.get(indicePreguntaActual);
    vista.getjTextAreaPregunta().setText(pregunta.getPregunta());

    // Opciones mezcladas
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

    // Imagen con control para la primera vez
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
}

    private void mostrarImagenPregunta(String rutaImagen) {
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            URL imageUrl = getClass().getResource("/" + rutaImagen);
            if (imageUrl != null) {
                int width = vista.getjLabelImagen().getWidth();
                int height = vista.getjLabelImagen().getHeight();
                System.out.println("Tamaño jLabelImagen: " + width + "x" + height);
                if (width > 0 && height > 0) {
                    ImageIcon icono = new ImageIcon(imageUrl);
                    Image img = icono.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    vista.getjLabelImagen().setIcon(new ImageIcon(img));
                } else {
                    System.err.println("❌ No se puede escalar la imagen porque el tamaño del JLabel es 0x0");
                    vista.getjLabelImagen().setIcon(new ImageIcon(imageUrl));
                }
            } else {
                System.err.println("La imagen NO se encontró como recurso: " + rutaImagen);
                vista.getjLabelImagen().setIcon(null);
            }
        } else {
            vista.getjLabelImagen().setIcon(null);
        }
    }

    private void validarRespuestaYContinuar() {
        String seleccion = obtenerRespuestaSeleccionada();
        if (seleccion == null) {
            JOptionPane.showMessageDialog(vista, "Selecciona una opción antes de continuar.");
            return;
        }

        Modelo_Evaluaciones pregunta = preguntas.get(indicePreguntaActual);
        String respuestaCorrecta = obtenerTextoRespuestaCorrecta(pregunta);

        if (seleccion.equals(respuestaCorrecta)) {
            respuestasCorrectas++;
        }

        indicePreguntaActual++;

        if (indicePreguntaActual < preguntas.size()) {
            mostrarPreguntaActual();
        } else {
            finalizarEvaluacion();
        }
    }

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

    private void finalizarEvaluacion() {
        vista.getjButtonSIGUIENTE().setVisible(false);
        vista.getjButtonFINALIZARUNIDAD().setVisible(true);

        if (respuestasCorrectas >= 7) {
            JOptionPane.showMessageDialog(vista, "¡Felicidades! Has aprobado con " + respuestasCorrectas + " respuestas correctas.");

            int idUsuario = Usuario.obtenerIdPorCorreo(correo);  // Asume que tienes este método en tu modelo Usuario
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, idUnidad);

            if (progreso == null) {
                progreso = new Modelo_Progreso_Usuario(0, idUsuario, idUnidad, 0, 0, true, respuestasCorrectas, LocalDateTime.now());
                Modelo_Progreso_Usuario.guardarProgreso(progreso);
            } else {
                progreso.setEvaluacionAprobada(true);
                progreso.setCalificacion(respuestasCorrectas);
                progreso.setFechaActualizacion(LocalDateTime.now());
                Modelo_Progreso_Usuario.actualizarProgreso(progreso);
            }

            irAUnidades();
        } else {
            JOptionPane.showMessageDialog(vista, "No alcanzaste el mínimo de 7 respuestas correctas. Intenta nuevamente.");
            reiniciarEvaluacion();
        }
    }

    private void reiniciarEvaluacion() {
        this.preguntas = Modelo_Evaluaciones.obtenerPreguntasPorUnidad(conn, idUnidad);
        this.indicePreguntaActual = 0;
        this.respuestasCorrectas = 0;

        vista.getjButtonSIGUIENTE().setVisible(true);
        vista.getjButtonFINALIZARUNIDAD().setVisible(false); // ocultar aquí también

        mostrarPreguntaActual();
    }

    private void irAUnidades() {
        Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
        new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo, controladorUnidades);
        // Mostrar la vista Unidad1
        controladorDashboard.getVista().mostrarVista(vistaUnidad1);
    }
  
}
