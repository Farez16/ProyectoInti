package Controlador;

import Modelo.Modelo_Evaluaciones;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vista_EvaluacionU1;
import Vista.Vista_Unidad1;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Controlador_Evaluaciones {

    private final Vista_EvaluacionU1 vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idUnidad;
    private final Controlador_Unidades controladorUnidades;

    private List<Modelo_Evaluaciones> preguntas; // Lista de preguntas seleccionadas
    private final List<ButtonGroup> gruposBotones; // Lista de ButtonGroups para validar respuestas

    // Constructor corregido - ahora recibe controladorUnidades como parámetro
    public Controlador_Evaluaciones(Vista_EvaluacionU1 vista, ControladorDashboard controladorDashboard, 
                                   Connection conn, String correo, int idUnidad, Controlador_Unidades controladorUnidades) {
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;
        this.correo = correo;
        this.idUnidad = idUnidad;
        this.controladorUnidades = controladorUnidades; // Asignar el parámetro recibido

        // Inicializar la lista de ButtonGroups con los grupos de la vista en orden
        gruposBotones = new ArrayList<>();
        gruposBotones.add(vista.buttonGroupPregunta1);
        gruposBotones.add(vista.buttonGroupPregunta2);
        gruposBotones.add(vista.buttonGroupPregunta3);
        gruposBotones.add(vista.buttonGroupPregunta4);
        gruposBotones.add(vista.buttonGroupPregunta5);
        gruposBotones.add(vista.buttonGroupPregunta6);
        gruposBotones.add(vista.buttonGroupPregunta7);
        gruposBotones.add(vista.buttonGroupPregunta8);
        gruposBotones.add(vista.buttonGroupPregunta9);
        gruposBotones.add(vista.buttonGroupPregunta10);

        vista.jButtonCOMPLETOEVALUACION1.setEnabled(false); // Deshabilitar botón completar inicialmente

        cargarPreguntasAleatorias();
        agregarEventos();
    }

    private void agregarEventos() {
        // Evento para validar respuestas y habilitar botón completar
        vista.jButtonEnviarRespuestas.addActionListener(e -> {
            int correctas = validarRespuestas();
            if (correctas >= 7) {
                JOptionPane.showMessageDialog(vista, "¡Muy bien! Puedes completar la evaluación.");
                vista.jButtonCOMPLETOEVALUACION1.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(vista, "Obtuviste " + correctas + " respuestas correctas. Debes obtener al menos 7 para completar la evaluación. Intenta de nuevo.");
                vista.jButtonCOMPLETOEVALUACION1.setEnabled(false);
            }
        });

        // Evento para completar evaluación y actualizar progreso
        vista.jButtonCOMPLETOEVALUACION1.addActionListener(e -> {
            int idUsuario = Usuario.obtenerIdPorCorreo(correo);
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, idUnidad);
            boolean actualizado = ControladorProgresoUsuario.aprobarEvaluacion(progreso, 100);
            if (actualizado) {
                JOptionPane.showMessageDialog(vista, "Evaluación aprobada. Puedes continuar con la siguiente unidad.");
                Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
                // Ahora controladorUnidades ya no es null
                new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo, controladorUnidades);
                controladorDashboard.getVista().mostrarVista(vistaUnidad1);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al actualizar el progreso.");
            }
        });
    }

    private void cargarPreguntasAleatorias() {
        preguntas = Modelo_Evaluaciones.obtenerPreguntasPorUnidad(conn, idUnidad);
        if (preguntas.size() < 10) {
            JOptionPane.showMessageDialog(vista, "No hay suficientes preguntas en la base de datos.");
            return;
        }

        Collections.shuffle(preguntas);
        preguntas = preguntas.subList(0, 10);

        for (int i = 0; i < 10; i++) {
            Modelo_Evaluaciones p = preguntas.get(i);
            switch (i) {
                case 0:
                    vista.jTextAreaPregunta1.setText(p.getPregunta());
                    vista.jRadioButtonP1Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP1Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP1Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP1Opcion1.setActionCommand("A");
                    vista.jRadioButtonP1Opcion2.setActionCommand("B");
                    vista.jRadioButtonP1Opcion3.setActionCommand("C");
                    break;

                case 1:
                    vista.jTextAreaPregunta2.setText(p.getPregunta());
                    vista.jRadioButtonP2Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP2Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP2Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP2Opcion1.setActionCommand("A");
                    vista.jRadioButtonP2Opcion2.setActionCommand("B");
                    vista.jRadioButtonP2Opcion3.setActionCommand("C");
                    break;

                case 2:
                    vista.jTextAreaPregunta3.setText(p.getPregunta());
                    vista.jRadioButtonP3Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP3Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP3Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP3Opcion1.setActionCommand("A");
                    vista.jRadioButtonP3Opcion2.setActionCommand("B");
                    vista.jRadioButtonP3Opcion3.setActionCommand("C");
                    break;

                case 3:
                    vista.jTextAreaPregunta4.setText(p.getPregunta());
                    vista.jRadioButtonP4Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP4Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP4Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP4Opcion1.setActionCommand("A");
                    vista.jRadioButtonP4Opcion2.setActionCommand("B");
                    vista.jRadioButtonP4Opcion3.setActionCommand("C");
                    break;

                case 4:
                    vista.jTextAreaPregunta5.setText(p.getPregunta());
                    vista.jRadioButtonP5Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP5Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP5Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP5Opcion1.setActionCommand("A");
                    vista.jRadioButtonP5Opcion2.setActionCommand("B");
                    vista.jRadioButtonP5Opcion3.setActionCommand("C");
                    break;

                case 5:
                    vista.jTextAreaPregunta6.setText(p.getPregunta());
                    vista.jRadioButtonP6Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP6Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP6Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP6Opcion1.setActionCommand("A");
                    vista.jRadioButtonP6Opcion2.setActionCommand("B");
                    vista.jRadioButtonP6Opcion3.setActionCommand("C");
                    break;

                case 6:
                    vista.jTextAreaPregunta7.setText(p.getPregunta());
                    vista.jRadioButtonP7Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP7Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP7Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP7Opcion1.setActionCommand("A");
                    vista.jRadioButtonP7Opcion2.setActionCommand("B");
                    vista.jRadioButtonP7Opcion3.setActionCommand("C");
                    break;

                case 7:
                    vista.jTextAreaPregunta8.setText(p.getPregunta());
                    vista.jRadioButtonP8Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP8Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP8Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP8Opcion1.setActionCommand("A");
                    vista.jRadioButtonP8Opcion2.setActionCommand("B");
                    vista.jRadioButtonP8Opcion3.setActionCommand("C");
                    break;

                case 8:
                    vista.jTextAreaPregunta9.setText(p.getPregunta());
                    vista.jRadioButtonP9Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP9Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP9Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP9Opcion1.setActionCommand("A");
                    vista.jRadioButtonP9Opcion2.setActionCommand("B");
                    vista.jRadioButtonP9Opcion3.setActionCommand("C");
                    break;

                case 9:
                    vista.jTextAreaPregunta10.setText(p.getPregunta());
                    vista.jRadioButtonP10Opcion1.setText(p.getOpcionA());
                    vista.jRadioButtonP10Opcion2.setText(p.getOpcionB());
                    vista.jRadioButtonP10Opcion3.setText(p.getOpcionC());
                    vista.jRadioButtonP10Opcion1.setActionCommand("A");
                    vista.jRadioButtonP10Opcion2.setActionCommand("B");
                    vista.jRadioButtonP10Opcion3.setActionCommand("C");
                    break;
            }
        }
    }

    private int validarRespuestas() {
        int correctas = 0;

        // Pregunta 1
        String seleccion = vista.buttonGroupPregunta1.getSelection() != null
            ? vista.buttonGroupPregunta1.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(0).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 2
        seleccion = vista.buttonGroupPregunta2.getSelection() != null
            ? vista.buttonGroupPregunta2.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(1).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 3
        seleccion = vista.buttonGroupPregunta3.getSelection() != null
            ? vista.buttonGroupPregunta3.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(2).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 4
        seleccion = vista.buttonGroupPregunta4.getSelection() != null
            ? vista.buttonGroupPregunta4.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(3).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 5
        seleccion = vista.buttonGroupPregunta5.getSelection() != null
            ? vista.buttonGroupPregunta5.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(4).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 6
        seleccion = vista.buttonGroupPregunta6.getSelection() != null
            ? vista.buttonGroupPregunta6.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(5).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 7
        seleccion = vista.buttonGroupPregunta7.getSelection() != null
            ? vista.buttonGroupPregunta7.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(6).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 8
        seleccion = vista.buttonGroupPregunta8.getSelection() != null
            ? vista.buttonGroupPregunta8.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(7).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 9
        seleccion = vista.buttonGroupPregunta9.getSelection() != null
            ? vista.buttonGroupPregunta9.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(8).getRespuestaCorrecta()) {
            correctas++;
        }

        // Pregunta 10
        seleccion = vista.buttonGroupPregunta10.getSelection() != null
            ? vista.buttonGroupPregunta10.getSelection().getActionCommand()
            : null;
        if (seleccion != null && seleccion.charAt(0) == preguntas.get(9).getRespuestaCorrecta()) {
            correctas++;
        }

        return correctas;
    }
}