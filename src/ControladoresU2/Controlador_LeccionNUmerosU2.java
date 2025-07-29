package ControladoresU2;

import Controlador.Controlador_Unidades;
import Controlador.ControladorDashboard;
import ControladoresU2.ControladorUnidad2;
import Modelo.Modelo_ProgresoUnidad2;
import Modelo.Modelo_Unidades;
import Vista.Vista_NumerosActividadUnidad2;
import Vista.Vista_Unidad2;
import Vista.Estudiante.Dashboard;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Controlador_LeccionNUmerosU2 {

    private final Vista_NumerosActividadUnidad2 vista;
    private final Dashboard dashboard;
    private final String correoUsuario;
    private final ControladorUnidad2 controladorUnidad2;
    private final Controlador_Unidades controladorUnidades;
    private final Connection conexion;
    private final ControladorDashboard controladorDashboard;

    private static final String[] RESPUESTAS_CORRECTAS = {
        "shuk", "ishkay", "kimsa", "chusku", "pichka",
        "sukta", "kanchis", "pusak", "iskun", "chunka"
    };

    private static final int ID_UNIDAD = 2;
    private static final int PROGRESO_ACTIVIDAD = 25;

    private List<Integer> ordenRandom;

    // Constructor principal con todos los parámetros necesarios
    public Controlador_LeccionNUmerosU2(Vista_NumerosActividadUnidad2 vista,
                                      Dashboard dashboard,
                                      ControladorUnidad2 controladorUnidad2,
                                      Controlador_Unidades controladorUnidades,
                                      String correoUsuario,
                                      Connection conexion,
                                      ControladorDashboard controladorDashboard) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser null");
        this.dashboard = Objects.requireNonNull(dashboard, "El dashboard no puede ser null");
        this.controladorUnidad2 = controladorUnidad2;
        this.controladorUnidades = controladorUnidades;
        this.correoUsuario = Objects.requireNonNull(correoUsuario, "El correo no puede ser null");
        this.conexion = Objects.requireNonNull(conexion, "La conexión no puede ser null");
        this.controladorDashboard = Objects.requireNonNull(controladorDashboard, "El controladorDashboard no puede ser null");

        if (this.correoUsuario.isEmpty()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }

        generarOrdenAleatorio();
        configurarVista();
        agregarListeners();
    }

    private void generarOrdenAleatorio() {
        ordenRandom = new ArrayList<>();
        for (int i = 0; i < RESPUESTAS_CORRECTAS.length; i++) {
            ordenRandom.add(i);
        }
        Collections.shuffle(ordenRandom);
    }

    private void configurarVista() {
        vista.getjButton1Continuar().setEnabled(false);
    }

    private void agregarListeners() {
        vista.getjButton1ComprobarAudios().addActionListener(this::manejarComprobacion);
        vista.getjButton1Continuar().addActionListener(this::manejarFinalizacion);
        configurarBotonesAudio();
    }

    private void configurarBotonesAudio() {
        JButton[] botonesAudio = vista.getBotonesAudio();
        for (int i = 0; i < botonesAudio.length; i++) {
            final int index = i;
            botonesAudio[i].addActionListener(e -> 
                reproducirAudio(RESPUESTAS_CORRECTAS[ordenRandom.get(index)])
            );
        }
    }

    private void manejarComprobacion(ActionEvent e) {
        try {
            String[] respuestasUsuario = vista.getRespuestasIngresadas();
            ResultadoValidacion resultado = validarRespuestas(respuestasUsuario);

            mostrarFeedbackValidacion(resultado);
            vista.getjButton1Continuar().setEnabled(resultado.esCorrecto());

        } catch (Exception ex) {
            manejarError("Error al validar respuestas: " + ex.getMessage());
        }
    }

    private ResultadoValidacion validarRespuestas(String[] respuestasUsuario) {
        boolean todoCorrecto = true;
        StringBuilder mensaje = new StringBuilder();

        for (int i = 0; i < RESPUESTAS_CORRECTAS.length; i++) {
            int index = ordenRandom.get(i);
            String correcta = normalizarTexto(RESPUESTAS_CORRECTAS[index]);
            String usuario = normalizarTexto(respuestasUsuario[i]);

            if (!correcta.equals(usuario)) {
                mensaje.append(String.format("❌ Número #%d: escribiste '%s', lo correcto era '%s'%n",
                        i + 1, respuestasUsuario[i], RESPUESTAS_CORRECTAS[index]));
                todoCorrecto = false;
            }
        }

        return new ResultadoValidacion(todoCorrecto, mensaje.toString());
    }

    private void manejarFinalizacion(ActionEvent e) {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas finalizar la actividad de números?",
                "Confirmar finalización",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirmacion == JOptionPane.YES_OPTION) {
            procesarFinalizacion();
        }
    }

    private void procesarFinalizacion() {
        try {
            registrarProgreso();
            mostrarMensajeExito();
            navegarAUnidad();
        } catch (Exception e) {
            manejarError("Error al finalizar actividad: " + e.getMessage());
        }
    }

    private void registrarProgreso() throws Exception {
        Modelo_ProgresoUnidad2.actualizarProgreso(correoUsuario, PROGRESO_ACTIVIDAD);
        Modelo_Unidades.registrarActividadCompletadaUnidad2(correoUsuario);

        if (controladorUnidad2 != null) {
            controladorUnidad2.actualizarVista();
        }
    }

    private void navegarAUnidad() {
        try {
            Vista_Unidad2 vistaUnidad = new Vista_Unidad2();
            
            ControladorUnidad2 controlador = new ControladorUnidad2(
                vistaUnidad,
                conexion,
                controladorDashboard,
                correoUsuario,
                controladorUnidades
            );
            
            dashboard.mostrarVista(vistaUnidad);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                vista,
                "Error al navegar a la unidad: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Clase interna para manejo de resultados
    private static class ResultadoValidacion {
        private final boolean correcto;
        private final String mensaje;

        public ResultadoValidacion(boolean correcto, String mensaje) {
            this.correcto = correcto;
            this.mensaje = mensaje;
        }

        public boolean esCorrecto() {
            return correcto;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

    // Métodos auxiliares
    private String normalizarTexto(String texto) {
        return texto.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    private void mostrarFeedbackValidacion(ResultadoValidacion resultado) {
        if (resultado.esCorrecto()) {
            JOptionPane.showMessageDialog(vista,
                    "✔️ ¡Todos los números están bien escritos!\nPuedes continuar ✨",
                    "Validación exitosa",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista,
                    resultado.getMensaje() + "\n⚠️ Revisa las palabras en Kichwa. Escríbelas correctamente.",
                    "Corrige los errores",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(vista,
                "✨ Actividad completada correctamente.\n🔓 Se ha desbloqueado la lección de Operaciones.",
                "Actividad completada",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void manejarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void reproducirAudio(String texto) {
        try {
            System.setProperty("freetts.voices",
                    "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            com.sun.speech.freetts.VoiceManager voiceManager
                    = com.sun.speech.freetts.VoiceManager.getInstance();
            com.sun.speech.freetts.Voice voice = voiceManager.getVoice("kevin16");

            if (voice != null) {
                voice.allocate();
                voice.speak(texto);
                voice.deallocate();
            } else {
                JOptionPane.showMessageDialog(vista,
                        "No se pudo inicializar el sistema de voz",
                        "Error de audio",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista,
                    "Error al reproducir audio: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}