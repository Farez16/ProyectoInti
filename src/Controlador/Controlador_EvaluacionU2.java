package Controlador;

import Modelo.Modelo_EvaluacionUnidad2;
import Modelo.Modelo_ProgresoUnidad2;
import Modelo.Modelo_EvaluacionUnidad2.Pregunta;
import Vista.*;
import Vista.Estudiante.Dashboard;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class Controlador_EvaluacionU2 {

private final Vista_EvaluacionU2 vista;
private final Dashboard dashboard;
private final List<Pregunta> preguntas;
private int respuestasCorrectas = 0;

public Controlador_EvaluacionU2(Vista_EvaluacionU2 vista, Dashboard dashboard) {
    this.vista = vista;
    this.dashboard = dashboard;
    this.preguntas = Modelo_EvaluacionUnidad2.obtenerPreguntasAleatorias();

    cargarPreguntas();
    configurarEventos();
}

private void cargarPreguntas() {
    for (int i = 0; i < Math.min(5, preguntas.size()); i++) {
        Pregunta p = preguntas.get(i);
        switch (i) {
            case 0 -> configurarPregunta1(p);
            case 1 -> configurarPregunta2(p);
            case 2 -> configurarPregunta3(p);
            case 3 -> configurarPregunta4(p);
            case 4 -> configurarPregunta5(p);
        }
    }
}

private void configurarPregunta1(Pregunta p) {
    vista.getjLabel1Pregunta1Generada1().setText(p.texto);
    if ("opcion_multiple".equals(p.tipo)) {
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1().setText(p.opcionA);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2().setText(p.opcionB);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3().setText(p.opcionC);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1().setVisible(true);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2().setVisible(true);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3().setVisible(true);
        vista.getjTextField1arrastraropcion2().setVisible(false);
    } else {
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1().setVisible(false);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2().setVisible(false);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3().setVisible(false);
        vista.getjTextField1arrastraropcion2().setVisible(true);
    }
}

private void configurarPregunta2(Pregunta p) {
    vista.getjLabel1Pregunta2Generada1().setText(p.texto);
    if ("opcion_multiple".equals(p.tipo)) {
        vista.getjRadioButtonOpcion1().setText(p.opcionA);
        vista.getjRadioButtonOpcion2().setText(p.opcionB);
        vista.getjRadioButton3().setText(p.opcionC);
        vista.getjRadioButtonOpcion1().setVisible(true);
        vista.getjRadioButtonOpcion2().setVisible(true);
        vista.getjRadioButton3().setVisible(true);
        vista.getjTextField1arrastraropcion3().setVisible(false);
    } else {
        vista.getjRadioButtonOpcion1().setVisible(false);
        vista.getjRadioButtonOpcion2().setVisible(false);
        vista.getjRadioButton3().setVisible(false);
        vista.getjTextField1arrastraropcion3().setVisible(true);
    }
}

private void configurarPregunta3(Pregunta p) {
    vista.getjLabel1Pregunta3GeneradaEleijalaopcioncorrecta().setText(p.texto);
    if ("opcion_multiple".equals(p.tipo)) {
        vista.getjRadioButton2().setText(p.opcionA);
        vista.getjRadioButton4().setText(p.opcionB);
        vista.getjRadioButton5().setText(p.opcionC);
        vista.getjRadioButton2().setVisible(true);
        vista.getjRadioButton4().setVisible(true);
        vista.getjRadioButton5().setVisible(true);
        vista.getjTextField1OPCCIONAARRASTRAR2().setVisible(false);
    } else {
        vista.getjRadioButton2().setVisible(false);
        vista.getjRadioButton4().setVisible(false);
        vista.getjRadioButton5().setVisible(false);
        vista.getjTextField1OPCCIONAARRASTRAR2().setVisible(true);
    }
}

private void configurarPregunta4(Pregunta p) {
    vista.getjLabel1Pregunta4GeneradaCompletelafrase().setText(p.texto);
    if ("opcion_multiple".equals(p.tipo)) {
        vista.getjRadioButton5().setText(p.opcionA);
        vista.getjRadioButtonOpcion1().setText(p.opcionB);
        vista.getjRadioButtonOpcion2().setText(p.opcionC);
        vista.getjRadioButton5().setVisible(true);
        vista.getjRadioButtonOpcion1().setVisible(true);
        vista.getjRadioButtonOpcion2().setVisible(true);
        vista.getjTextField1OPCCIONAARRASTRAR3().setVisible(false);
    } else {
        vista.getjRadioButton5().setVisible(false);
        vista.getjRadioButtonOpcion1().setVisible(false);
        vista.getjRadioButtonOpcion2().setVisible(false);
        vista.getjTextField1OPCCIONAARRASTRAR3().setVisible(true);
    }
}

private void configurarPregunta5(Pregunta p) {
    vista.getjLabel2TextoMorfemas1().setText(p.texto);
    if ("opcion_multiple".equals(p.tipo)) {
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1().setText(p.opcionA);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2().setText(p.opcionB);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3().setText(p.opcionC);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1().setVisible(true);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2().setVisible(true);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3().setVisible(true);
        vista.getjTextField1OPCCIONAARRASTRAR4().setVisible(false);
    } else {
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1().setVisible(false);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2().setVisible(false);
        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3().setVisible(false);
        vista.getjTextField1OPCCIONAARRASTRAR4().setVisible(true);
    }
}

private void configurarEventos() {
    vista.getjButton1ComprobaryFinalizarUnidad().addActionListener(this::verificarRespuestas);
}

private void verificarRespuestas(ActionEvent e) {
    respuestasCorrectas = 0;

    for (int i = 0; i < preguntas.size(); i++) {
        Pregunta p = preguntas.get(i);
        if (verificarPregunta(i, p)) {
            respuestasCorrectas++;
            marcarRespuestaCorrecta(i);
        } else {
            marcarRespuestaIncorrecta(i);
        }
    }

    int calificacion = (respuestasCorrectas * 100) / preguntas.size();

    if (calificacion >= 70) {
        Modelo_ProgresoUnidad2.completarUnidad(dashboard.getCorreoUsuario(), calificacion);
        
        JOptionPane.showMessageDialog(vista,
            "¡Felicidades! Has completado la Unidad 2.\nCalificación: " + calificacion + "%",
            "Unidad Completada", JOptionPane.INFORMATION_MESSAGE);

        dashboard.mostrarVista(new Vista_Unidad2());
    } else {
        JOptionPane.showMessageDialog(vista,
                "Calificación: " + calificacion + "%\nNecesitas al menos 70% para aprobar.",
                "Intenta Nuevamente", JOptionPane.WARNING_MESSAGE);
    }
}

private boolean verificarPregunta(int index, Pregunta p) {
    switch (index) {
        case 0 -> {
            if ("opcion_multiple".equals(p.tipo)) {
                return verificarOpcionMultiple(p,
                        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1(),
                        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2(),
                        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3());
            } else {
                return verificarTexto(p, vista.getjTextField1arrastraropcion2().getText());
            }
        }
        case 1 -> {
            if ("opcion_multiple".equals(p.tipo)) {
                return verificarOpcionMultiple(p,
                        vista.getjRadioButtonOpcion1(),
                        vista.getjRadioButtonOpcion2(),
                        vista.getjRadioButton3());
            } else {
                return verificarTexto(p, vista.getjTextField1arrastraropcion3().getText());
            }
        }
        case 2 -> {
            if ("opcion_multiple".equals(p.tipo)) {
                return verificarOpcionMultiple(p,
                        vista.getjRadioButton2(),
                        vista.getjRadioButton4(),
                        vista.getjRadioButton5());
            } else {
                return verificarTexto(p, vista.getjTextField1OPCCIONAARRASTRAR2().getText());
            }
        }
        case 3 -> {
            if ("opcion_multiple".equals(p.tipo)) {
                return verificarOpcionMultiple(p,
                        vista.getjRadioButton5(),
                        vista.getjRadioButtonOpcion1(),
                        vista.getjRadioButtonOpcion2());
            } else {
                return verificarTexto(p, vista.getjTextField1OPCCIONAARRASTRAR3().getText());
            }
        }
        case 4 -> {
            if ("opcion_multiple".equals(p.tipo)) {
                return verificarOpcionMultiple(p,
                        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion1(),
                        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion2(),
                        vista.getjRadioButtonSeleccionelnombrecorrectodelanimalopcion3());
            } else {
                return verificarTexto(p, vista.getjTextField1OPCCIONAARRASTRAR4().getText());
            }
        }
        default -> {
            return false;
        }
    }
}

private boolean verificarOpcionMultiple(Pregunta p, JRadioButton... opciones) {
    for (int i = 0; i < opciones.length; i++) {
        if (opciones[i].isSelected()) {
            char seleccion = (char) ('a' + i);
            return String.valueOf(seleccion).equalsIgnoreCase(p.respuesta);
        }
    }
    return false;
}

private boolean verificarTexto(Pregunta p, String respuestaUsuario) {
    return respuestaUsuario.trim().equalsIgnoreCase(p.respuesta);
}

private void marcarRespuestaCorrecta(int index) {
    switch (index) {
        case 0 -> vista.getjLabel1Pregunta1Generada1().setForeground(Color.GREEN);
        case 1 -> vista.getjLabel1Pregunta2Generada1().setForeground(Color.GREEN);
        case 2 -> vista.getjLabel1Pregunta3GeneradaEleijalaopcioncorrecta().setForeground(Color.GREEN);
        case 3 -> vista.getjLabel1Pregunta4GeneradaCompletelafrase().setForeground(Color.GREEN);
        case 4 -> vista.getjLabel2TextoMorfemas1().setForeground(Color.GREEN);
    }
}

private void marcarRespuestaIncorrecta(int index) {
    switch (index) {
        case 0 -> vista.getjLabel1Pregunta1Generada1().setForeground(Color.RED);
        case 1 -> vista.getjLabel1Pregunta2Generada1().setForeground(Color.RED);
        case 2 -> vista.getjLabel1Pregunta3GeneradaEleijalaopcioncorrecta().setForeground(Color.RED);
        case 3 -> vista.getjLabel1Pregunta4GeneradaCompletelafrase().setForeground(Color.RED);
        case 4 -> vista.getjLabel2TextoMorfemas1().setForeground(Color.RED);
    }
}
}