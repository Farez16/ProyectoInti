/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.Estudiante.Dashboard;
import Vista.*;
import Modelo.Modelo_Unidades;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class Controlador_NumerosUnidad2 {

    private final Vista_NumerosActividadUnidad2 vista;
    private final Dashboard dashboard;
    private final String[] respuestasCorrectas = {
        "shuk", "ishkay", "kimsa", "chusku", "pichka",
        "sukta", "kanchis", "pusak", "iskun", "chunka"
    };
    private final int idUnidad = 2;
    private List<Integer> ordenRandom;

    public Controlador_NumerosUnidad2(Vista_NumerosActividadUnidad2 vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;
        generarOrdenAleatorio();
        inicializar();
    }

    private void generarOrdenAleatorio() {
        ordenRandom = new ArrayList<>();
        for (int i = 0; i < respuestasCorrectas.length; i++) {
            ordenRandom.add(i);
        }
        Collections.shuffle(ordenRandom); // Mezclar el orden
    }

    private void inicializar() {
        vista.getjButton1ComprobarAudios().addActionListener(e -> comprobarRespuestas());
        vista.getjButton1Continuar().addActionListener(e -> finalizarActividad());

        JButton[] botonesAudio = vista.getBotonesAudio();

        for (int i = 0; i < botonesAudio.length; i++) {
            final int index = i;
            botonesAudio[i].addActionListener(e -> {
                String texto = respuestasCorrectas[ordenRandom.get(index)];
                reproducirAudioTexto(texto);
            });
        }
    }

    private void comprobarRespuestas() {
    String[] respuestasUsuario = vista.getRespuestasIngresadas();
    boolean todoCorrecto = true;
    StringBuilder mensaje = new StringBuilder();

    for (int i = 0; i < respuestasCorrectas.length; i++) {
        int index = ordenRandom.get(i);
        String correcta = respuestasCorrectas[index]
                .trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");
        String usuario = respuestasUsuario[i]
                .trim().toLowerCase(Locale.ROOT).replaceAll("[^a-z]", "");

        if (!correcta.equals(usuario)) {
            mensaje.append("❌ Número #").append(i + 1)
                   .append(": escribiste '").append(respuestasUsuario[i])
                   .append("', lo correcto era '").append(respuestasCorrectas[index]).append("'\n");
            todoCorrecto = false;
        }
    }

    if (todoCorrecto) {
        JOptionPane.showMessageDialog(vista,
            "✔️ ¡Todos los números están bien escritos!\nPuedes continuar ✨");
        vista.getjButton1Continuar().setEnabled(true);
    } else {
        mensaje.append("\n⚠️ Revisa las palabras en Kichwa. Escríbelas correctamente.");
        JOptionPane.showMessageDialog(vista, mensaje.toString());
        vista.getjButton1Continuar().setEnabled(false);
    }
}

        private void finalizarActividad() {
JOptionPane.showMessageDialog(vista, "✨ Actividad completada correctamente.\n🔓 Se ha desbloqueado la lección de Operaciones.");

// Registrar progreso
Modelo_Unidades.registrarActividadCompletadaUnidad2(dashboard.getCorreoUsuario());

// Mostrar Unidad 2
Vista_Unidad2 nuevaVistaUnidad2 = new Vista_Unidad2();
ControladorUnidad2 controladorUnidad2 = new ControladorUnidad2(nuevaVistaUnidad2, dashboard);

// Mostrar progreso 25%
nuevaVistaUnidad2.getjProgressBarUNIDAD1().setValue(25);

// Desbloquear botón de operaciones
controladorUnidad2.desbloquearBotonOperaciones();

dashboard.mostrarVista(nuevaVistaUnidad2);
}
    

    private void reproducirAudioTexto(String texto) {
        try {
            System.setProperty("freetts.voices", 
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

            com.sun.speech.freetts.VoiceManager voiceManager = com.sun.speech.freetts.VoiceManager.getInstance();
            com.sun.speech.freetts.Voice voice = voiceManager.getVoice("kevin16");

            if (voice != null) {
                voice.allocate();
                voice.speak(texto);
                voice.deallocate();
            } else {
                System.err.println("⚠️ Voz 'kevin16' no encontrada.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
