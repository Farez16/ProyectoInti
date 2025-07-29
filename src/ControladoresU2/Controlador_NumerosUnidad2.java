package ControladoresU2;

import Controlador.Controlador_Unidades;
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

public class Controlador_NumerosUnidad2 {

    private final Vista_NumerosActividadUnidad2 vista;
    private final Dashboard dashboard;
    private final String correoUsuario;
    private final ControladorUnidad2 controladorUnidad2;
    private final Controlador_Unidades controladorUnidades;
    private final Connection conn;
    
    private static final String[] RESPUESTAS_CORRECTAS = {
        "shuk", "ishkay", "kimsa", "chusku", "pichka",
        "sukta", "kanchis", "pusak", "iskun", "chunka"
    };
    
    private static final int ID_UNIDAD = 2;
    private static final int PROGRESO_ACTIVIDAD = 25;
    
    private List<Integer> ordenRandom;

    // Constructor principal, recibe controladorUnidad2, controladorUnidades y conexión
    public Controlador_NumerosUnidad2(Vista_NumerosActividadUnidad2 vista, 
                                      Dashboard dashboard,
                                      ControladorUnidad2 controladorUnidad2,
                                      Controlador_Unidades controladorUnidades,
                                      Connection conn) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser null");
        this.dashboard = Objects.requireNonNull(dashboard, "El dashboard no puede ser null");
        this.controladorUnidad2 = Objects.requireNonNull(controladorUnidad2, "El controlador de unidad no puede ser null");
        this.controladorUnidades = Objects.requireNonNull(controladorUnidades, "El controladorUnidades no puede ser null");
        this.conn = Objects.requireNonNull(conn, "La conexión no puede ser null");
        this.correoUsuario = dashboard.getCorreoUsuario();

        if (this.correoUsuario == null || this.correoUsuario.isEmpty()) {
            throw new IllegalArgumentException("No se pudo obtener el correo del usuario");
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
        vista.getjButton1ComprobarAudios().addActionListener(this::comprobarRespuestas);
        vista.getjButton1Continuar().addActionListener(this::finalizarActividad);

        JButton[] botonesAudio = vista.getBotonesAudio();
        for (int i = 0; i < botonesAudio.length; i++) {
            final int index = i;
            botonesAudio[i].addActionListener(e -> reproducirAudio(RESPUESTAS_CORRECTAS[ordenRandom.get(index)]));
        }
    }

    private void comprobarRespuestas(ActionEvent e) {
        String[] respuestasUsuario = vista.getRespuestasIngresadas();
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

        if (todoCorrecto) {
            JOptionPane.showMessageDialog(vista,
                "✔️ ¡Todos los números están bien escritos!\nPuedes continuar ✨",
                "Validación exitosa",
                JOptionPane.INFORMATION_MESSAGE);
            vista.getjButton1Continuar().setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(vista,
                mensaje.toString() + "\n⚠️ Revisa las palabras en Kichwa. Escríbelas correctamente.",
                "Corrige los errores",
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private String normalizarTexto(String texto) {
        return texto.trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
    }

    private void finalizarActividad(ActionEvent e) {
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
            regresarAUnidad();
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

    private void regresarAUnidad() {
        try {
            // Reusar la instancia existente para no perder estado ni crear una nueva innecesariamente
            dashboard.mostrarVista(controladorUnidad2.getVista());
            controladorUnidad2.actualizarVista();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                vista,
                "Error al regresar a la unidad: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
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

            com.sun.speech.freetts.VoiceManager voiceManager = 
                com.sun.speech.freetts.VoiceManager.getInstance();
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
