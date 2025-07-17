package Controlador;

import Modelo.Modelo_Actividades;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vista_Actividad1U1;
import Vista.Vista_Unidad1;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controlador_Actividad_PreguntaRespuesta {

    private final Vista_Actividad1U1 vista;
    private final Connection conn;
    private final int idActividad;
    private Modelo_Actividades actividad;
    
    private final ControladorDashboard controladorDashboard;
    private final Controlador_Unidad1 controladorUnidad1;
    private final String correo;

    public Controlador_Actividad_PreguntaRespuesta(Vista_Actividad1U1 vista, Connection conn, int idActividad,
                                                  ControladorDashboard controladorDashboard, Controlador_Unidad1 controladorUnidad1, String correo) {
        this.vista = vista;
        this.conn = conn;
        this.idActividad = idActividad;
        this.controladorDashboard = controladorDashboard;
        this.controladorUnidad1 = controladorUnidad1;
        this.correo = correo;

        cargarDatos();
        agregarEventos();
    }

    private void cargarDatos() {
        actividad = Modelo_Actividades.obtenerPorId(conn, idActividad);
        if (actividad == null) {
            vista.jLabelMensajeRespuesta.setText("No se encontró la actividad.");
            return;
        }

        // Setear pregunta
        vista.jTextAreaPregunta.setText(actividad.getPregunta());

        // Opciones aleatorias
        List<String> opciones = new ArrayList<>();
        opciones.add(actividad.getOpcionA());
        opciones.add(actividad.getOpcionB());
        opciones.add(actividad.getOpcionC());
        Collections.shuffle(opciones);

        // Asignar opciones a los radio buttons
        vista.jRadioButtonOpcion1.setText(opciones.get(0));
        vista.jRadioButtonOpcion2.setText(opciones.get(1));
        vista.jRadioButtonOpcion3.setText(opciones.get(2));

        // Limpiar selección previa
        vista.buttonGroupOpciones.clearSelection();

        // Deshabilitar botón completar al iniciar
        vista.jButtonCOMPLETOACTV1.setEnabled(false);

        vista.jLabelMensajeRespuesta.setText("");
    }

    private void agregarEventos() {
        vista.jButtonResponder.addActionListener(e -> validarRespuesta());
        vista.jButtonCOMPLETOACTV1.addActionListener(e -> completarActividad());
    }

    private void validarRespuesta() {
        String seleccion = null;
        if (vista.jRadioButtonOpcion1.isSelected()) {
            seleccion = vista.jRadioButtonOpcion1.getText();
        } else if (vista.jRadioButtonOpcion2.isSelected()) {
            seleccion = vista.jRadioButtonOpcion2.getText();
        } else if (vista.jRadioButtonOpcion3.isSelected()) {
            seleccion = vista.jRadioButtonOpcion3.getText();
        }

        if (seleccion == null) {
            vista.jLabelMensajeRespuesta.setText("Por favor, selecciona una opción.");
            return;
        }

        // Comparar respuesta correcta
        String correctaTexto = null;
        switch (actividad.getRespuestaCorrecta()) {
            case 'A': correctaTexto = actividad.getOpcionA(); break;
            case 'B': correctaTexto = actividad.getOpcionB(); break;
            case 'C': correctaTexto = actividad.getOpcionC(); break;
        }

        if (seleccion.equals(correctaTexto)) {
            vista.jLabelMensajeRespuesta.setText("¡Respuesta correcta!");
            vista.jButtonCOMPLETOACTV1.setEnabled(true);
        } else {
            vista.jLabelMensajeRespuesta.setText("Respuesta incorrecta, intenta de nuevo.");
            vista.jButtonCOMPLETOACTV1.setEnabled(false);
        }
    }

    private void completarActividad() {
        try {
            int idUsuario = Usuario.obtenerIdPorCorreo(correo);

            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, actividad.getIdUnidad());
            boolean actualizado = ControladorProgresoUsuario.actualizarActividad(progreso, idActividad);

            if (actualizado) {
                System.out.println("Actividad " + idActividad + " completada");
            } else {
                System.out.println("No se pudo actualizar el progreso");
            }

            Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
            Controlador_Unidad1 controladorUnidad = new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo,
                    controladorUnidad1.getControladorUnidades());
            controladorDashboard.getVista().mostrarVista(vistaUnidad1);

        } catch (Exception ex) {
            ex.printStackTrace();
            vista.jLabelMensajeRespuesta.setText("Error al completar la actividad.");
        }
    }
}
