//package Controlador;
//
//import Modelo.Modelo_ProgresoUnidad2;
//import Vista.*;
//import Modelo.Modelo_Unidades;
//import Vista.Estudiante.Dashboard;
//import VistasUnidad3.Vista_Actividad_Familia;
//import javax.swing.*;
//import java.awt.event.*;
//import java.util.*;
//
//public class Controlador_FamiliaUnidad3 {
//
//    private final Vista_Actividad_Familia vista;
//    private final Dashboard dashboard;
//    private final String[] respuestasCorrectas = {
//        "shuk", "ishkay", "kimsa", "chusku", "pichka",
//        "sukta", "kanchis", "pusak", "iskun", "chunka"
//    };
//    private final int idUnidad = 3;
//    private List<Integer> ordenRandom;
//
//    public Controlador_FamiliaUnidad3(Vista_Actividad_Familia vista, Dashboard dashboard) {
//        this.vista = vista;
//        this.dashboard = dashboard;
//        inicializar();
//    }
//
//
//    private void inicializar() {
//        vista.getjButton1Continuar().addActionListener(e -> finalizarActividad());
//
//        JButton[] botonesAudio = vista.getBotonesAudio();
//
//        for (int i = 0; i < botonesAudio.length; i++) {
//            final int index = i;
//            botonesAudio[i].addActionListener(e -> {
//                String texto = respuestasCorrectas[ordenRandom.get(index)];
//                reproducirAudioTexto(texto);
//            });
//        }
//    }
//
//       private void finalizarActividad() {
//    // Guardar progreso (25%)
//    Modelo_ProgresoUnidad2.actualizarProgreso(
//        dashboard.getCorreoUsuario(), 
//        25
//            
//    );
//    
//        JOptionPane.showMessageDialog(vista, "✨ Actividad completada correctamente.\n🔓 Se ha desbloqueado la lección de Operaciones.");
//
//// Registrar progreso
//Modelo_Unidades.registrarActividadCompletadaUnidad2(dashboard.getCorreoUsuario());
//
//// Mostrar Unidad 2
//Vista_Unidad2 nuevaVistaUnidad2 = new Vista_Unidad2();
//ControladorUnidad2 controladorUnidad2 = new ControladorUnidad2(nuevaVistaUnidad2, dashboard);
//
//// Mostrar progreso 25%
//nuevaVistaUnidad2.getjProgressBarUNIDAD1().setValue(25);
//
//// Desbloquear botón de operaciones
//controladorUnidad2.desbloquearBotonOperaciones();
//
//dashboard.mostrarVista(nuevaVistaUnidad2);
//}
//        
//
//
//    private void reproducirAudioTexto(String texto) {
//        try {
//            System.setProperty("freetts.voices", 
//                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
//
//            com.sun.speech.freetts.VoiceManager voiceManager = com.sun.speech.freetts.VoiceManager.getInstance();
//            com.sun.speech.freetts.Voice voice = voiceManager.getVoice("kevin16");
//
//            if (voice != null) {
//                voice.allocate();
//                voice.speak(texto);
//                voice.deallocate();
//            } else {
//                System.err.println("⚠️ Voz 'kevin16' no encontrada.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
