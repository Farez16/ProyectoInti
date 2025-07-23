package Controlador;

import Modelo.Modelo_Actividades;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vistas_Unidad1.Vista_Actividad1U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Controlador_Actividad_PreguntaRespuesta {

    private final Vista_Actividad1U1 vista;
    private final Connection conn;
    private final int idUnidad = 1;
    private final ControladorDashboard controladorDashboard;
    private final String correo;
    private final Controlador_Unidad1 controladorUnidad1;
    
    private List<Modelo_Actividades> preguntasActividad;
    private boolean[] respuestasCorrectas;

    public Controlador_Actividad_PreguntaRespuesta(Vista_Actividad1U1 vista, Connection conn, 
            ControladorDashboard controladorDashboard, Controlador_Unidad1 controladorUnidad1, 
            String correo) {
        this.vista = vista;
        this.conn = conn;
        this.controladorDashboard = controladorDashboard;
        this.controladorUnidad1 = controladorUnidad1;
        this.correo = correo;
        this.respuestasCorrectas = new boolean[3];
        
        cargarDatos();
        agregarEventos();
    }

    private void cargarDatos() {
        preguntasActividad = obtenerPreguntasAleatorias(3);
        
        if (preguntasActividad.size() < 3) {
            JOptionPane.showMessageDialog(vista, "No hay suficientes preguntas disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        configurarPregunta(preguntasActividad.get(0), 0, 
                vista.jLabelPregunta1, 
                vista.jRadioButtonOpcion1P1, 
                vista.jRadioButtonOpcion2P1, 
                vista.jRadioButtonOpcion3P1);
        
        configurarPregunta(preguntasActividad.get(1), 1,
                vista.jLabelPregunta2, 
                vista.jRadioButtonOpcion1P2, 
                vista.jRadioButtonOpcion2P2, 
                vista.jRadioButtonOpcion3P2);
        
        configurarPregunta(preguntasActividad.get(2), 2,
                vista.jLabelPregunta3, 
                vista.jRadioButtonOpcion1P3, 
                vista.jRadioButtonOpcion2P3, 
                vista.jRadioButtonOpcion3P3);
        
        vista.jButtonCOMPLETOACTV1.setEnabled(false);
    }

    private void configurarPregunta(Modelo_Actividades pregunta, int indice,
            JLabel labelPregunta,
            JRadioButton opcion1,
            JRadioButton opcion2,
            JRadioButton opcion3) {
        labelPregunta.setText(pregunta.getPregunta());
        
        List<String> opciones = new ArrayList<>();
        opciones.add(pregunta.getOpcionA());
        opciones.add(pregunta.getOpcionB());
        opciones.add(pregunta.getOpcionC());
        Collections.shuffle(opciones);
        
        opcion1.setText(opciones.get(0));
        opcion2.setText(opciones.get(1));
        opcion3.setText(opciones.get(2));
        
        opcion1.addActionListener(e -> verificarRespuesta(indice));
        opcion2.addActionListener(e -> verificarRespuesta(indice));
        opcion3.addActionListener(e -> verificarRespuesta(indice));
    }

    private List<Modelo_Actividades> obtenerPreguntasAleatorias(int cantidad) {
        List<Modelo_Actividades> preguntas = new ArrayList<>();
        String sql = "SELECT * FROM actividades WHERE id_unidad = ? AND tipo = 'pregunta_respuesta' ORDER BY RAND() LIMIT ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUnidad);
            ps.setInt(2, cantidad);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Modelo_Actividades pregunta = new Modelo_Actividades();
                pregunta.setIdActividad(rs.getInt("id_actividad"));
                pregunta.setIdUnidad(rs.getInt("id_unidad"));
                pregunta.setPregunta(rs.getString("pregunta"));
                pregunta.setOpcionA(rs.getString("opcion_a"));
                pregunta.setOpcionB(rs.getString("opcion_b"));
                pregunta.setOpcionC(rs.getString("opcion_c"));
                pregunta.setRespuestaCorrecta(rs.getString("respuesta_correcta").charAt(0));
                
                preguntas.add(pregunta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar preguntas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return preguntas;
    }

    private void agregarEventos() {
        vista.jButtonCOMPLETOACTV1.addActionListener(e -> validarYCompletarActividad());
    }

    private void verificarRespuesta(int indicePregunta) {
        Modelo_Actividades pregunta = preguntasActividad.get(indicePregunta);
        JRadioButton[] opciones = obtenerOpcionesPorIndice(indicePregunta);
        
        String respuestaSeleccionada = null;
        for (JRadioButton opcion : opciones) {
            if (opcion.isSelected()) {
                respuestaSeleccionada = opcion.getText();
                break;
            }
        }
        
        if (respuestaSeleccionada != null) {
            String respuestaCorrecta = obtenerRespuestaCorrecta(pregunta);
            respuestasCorrectas[indicePregunta] = respuestaSeleccionada.equals(respuestaCorrecta);
        }
        
        verificarEstadoBoton();
    }

    private JRadioButton[] obtenerOpcionesPorIndice(int indice) {
        switch(indice) {
            case 0: return new JRadioButton[]{vista.jRadioButtonOpcion1P1, vista.jRadioButtonOpcion2P1, vista.jRadioButtonOpcion3P1};
            case 1: return new JRadioButton[]{vista.jRadioButtonOpcion1P2, vista.jRadioButtonOpcion2P2, vista.jRadioButtonOpcion3P2};
            case 2: return new JRadioButton[]{vista.jRadioButtonOpcion1P3, vista.jRadioButtonOpcion2P3, vista.jRadioButtonOpcion3P3};
            default: return new JRadioButton[0];
        }
    }

    private String obtenerRespuestaCorrecta(Modelo_Actividades pregunta) {
        switch (pregunta.getRespuestaCorrecta()) {
            case 'A': return pregunta.getOpcionA();
            case 'B': return pregunta.getOpcionB();
            case 'C': return pregunta.getOpcionC();
            default: return "";
        }
    }

    private void verificarEstadoBoton() {
        boolean todasRespondidas = true;
        for (int i = 0; i < 3; i++) {
            JRadioButton[] opciones = obtenerOpcionesPorIndice(i);
            boolean respondida = false;
            for (JRadioButton opcion : opciones) {
                if (opcion.isSelected()) {
                    respondida = true;
                    break;
                }
            }
            todasRespondidas &= respondida;
        }
        vista.jButtonCOMPLETOACTV1.setEnabled(todasRespondidas);
    }

    private void validarYCompletarActividad() {
        boolean todasCorrectas = true;
        for (boolean correcta : respuestasCorrectas) {
            todasCorrectas &= correcta;
        }
        
        if (todasCorrectas) {
            if (guardarProgreso()) {
                JOptionPane.showMessageDialog(vista, "¡Todas las respuestas son correctas!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                regresarAUnidad();
            }
        } else {
            mostrarResultadosDetallados();
        }
    }

    private boolean guardarProgreso() {
        try {
            int idUsuario = Usuario.obtenerIdPorCorreo(correo);
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, idUnidad);
            
            if (progreso == null) {
                JOptionPane.showMessageDialog(vista, "No se encontró el progreso del usuario", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // Incrementar actividades completadas
            int nuevasActividades = progreso.getActividadesCompletadas() + 1;
            progreso.setActividadesCompletadas(nuevasActividades);
            progreso.setFechaActualizacion(LocalDateTime.now());
            
            boolean actualizado = Modelo_Progreso_Usuario.actualizarProgreso(progreso);
            
            if (!actualizado) {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar el progreso", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            return actualizado;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al guardar el progreso: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void mostrarResultadosDetallados() {
        StringBuilder mensaje = new StringBuilder();
        mensaje.append("<html><body>");
        mensaje.append("<h3>Resultados de la actividad:</h3><ul>");
        
        for (int i = 0; i < 3; i++) {
            mensaje.append("<li>Pregunta ").append(i+1).append(": ");
            if (respuestasCorrectas[i]) {
                mensaje.append("<font color='green'>Correcta</font>");
            } else {
                mensaje.append("<font color='red'>Incorrecta</font>");
            }
            mensaje.append("</li>");
        }
        
        mensaje.append("</ul><p>Por favor corrige las respuestas incorrectas.</p></body></html>");
        
        JOptionPane.showMessageDialog(vista, mensaje.toString(), "Resultados", JOptionPane.WARNING_MESSAGE);
    }

    private void regresarAUnidad() {
        Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
        new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo,
                controladorUnidad1.getControladorUnidades());
        controladorDashboard.getVista().mostrarVista(vistaUnidad1);
    }
}