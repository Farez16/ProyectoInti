/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Modelo_AnimacionEntradaUnidad2;
import Vista.Dashboard;
import Vista.Vista_ActividadMorfemasUnidad2;
import Vista.Vista_InicioNumerosUnidad2;
import Vista.Vista_InicioOperacionesUnidad2;
import Vista.Vista_Unidad2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorUnidad2 {

    private final Vista_Unidad2 vista;
    private boolean numerosCompletado = false;
    private boolean operacionesCompletado = false;
    private boolean morfemasCompletado = false;
    private boolean animalesCompletado = false;
    private boolean evaluacionCompletada = false;
    private final Dashboard dashboard;


    public ControladorUnidad2(Vista_Unidad2 vista, Dashboard dashboard) {
    this.vista = vista;
    this.dashboard = dashboard;
    inicializar();
}

    private void inicializar() {
        desactivarBotonesIniciales();
        agregarEventos();
    }
        public void desbloquearBotonOperaciones() {
vista.getjButtonOperaciones().setEnabled(true);
vista.getjButtonOperaciones().addActionListener(e -> {
    Vista_InicioOperacionesUnidad2 panel = new Vista_InicioOperacionesUnidad2();
    dashboard.mostrarVista(panel);
});}

    private void desactivarBotonesIniciales() {
        vista.getjButtonOperaciones().setEnabled(false);
        vista.getjButtonMorfemas().setEnabled(false);
        vista.getjButtonAnimales().setEnabled(false);
        vista.getjButtonEvaluacion().setEnabled(false);
        vista.getjButtonFINALIZARUNIDAD1().setEnabled(false);
    }

    private void agregarEventos() {
        vista.getjButtonNumeros().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numerosCompletado = true;
                vista.getjButtonOperaciones().setEnabled(true);
            }
        });
        vista.getjButtonNumeros().addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        Vista_InicioNumerosUnidad2 panelNumeros = new Vista_InicioNumerosUnidad2();
        new Controlador_InicioNumerosU2(panelNumeros, dashboard);
        dashboard.mostrarVista(panelNumeros); // ← ¡ya no dará error!
    }
});

        
        vista.getjButtonOperaciones().addActionListener(e -> {
    Vista_InicioOperacionesUnidad2 panel = new Vista_InicioOperacionesUnidad2();
    new Controlador_InicioOperacionesU2(panel, dashboard);

    // Aplica animación de entrada
    Modelo_AnimacionEntradaUnidad2 anim = new Modelo_AnimacionEntradaUnidad2(panel);
    anim.mostrarConAnimacion();

    dashboard.mostrarVista(panel);
});


        vista.getjButtonMorfemas().addActionListener(e -> {
Vista_ActividadMorfemasUnidad2 panel = new Vista_ActividadMorfemasUnidad2();
new Controlador_ActividadMorfemasU2(panel, dashboard);
dashboard.mostrarVista(panel);
});

        vista.getjButtonAnimales().addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        if (morfemasCompletado) {
            new Controlador_ActividadAnimalesdelaSierraUnidad2(dashboard);
        } else {
            JOptionPane.showMessageDialog(vista, "Debes completar primero la actividad de Morfemas.");
        }
    }
});

        

        vista.getjButtonEvaluacion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animalesCompletado) {
                    evaluacionCompletada = true;
                    vista.getjButtonFINALIZARUNIDAD1().setEnabled(true);
                    JOptionPane.showMessageDialog(vista, "¡Evaluación aprobada!");
                }
            }
        });

        vista.getjButtonFINALIZARUNIDAD1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (evaluacionCompletada) {
                    JOptionPane.showMessageDialog(vista, "¡Unidad 2 completada!");
                    // Aquí podrías desbloquear Unidad 3 desde el controlador de unidades
                }
            }
        });

        vista.getjButtonBack().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Si tienes referencia al dashboard, podrías volver al panel de unidades
                JOptionPane.showMessageDialog(vista, "Volviendo al panel de unidades...");
            }
        });

        vista.getjButtonREINICIARU1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarUnidad();
            }
        });


    }

    private void reiniciarUnidad() {
        numerosCompletado = false;
        operacionesCompletado = false;
        morfemasCompletado = false;
        animalesCompletado = false;
        evaluacionCompletada = false;

        vista.getjButtonNumeros().setEnabled(true);
        vista.getjButtonOperaciones().setEnabled(false);
        vista.getjButtonMorfemas().setEnabled(false);
        vista.getjButtonAnimales().setEnabled(false);
        vista.getjButtonEvaluacion().setEnabled(false);
        vista.getjButtonFINALIZARUNIDAD1().setEnabled(false);

        JOptionPane.showMessageDialog(vista, "Unidad 2 reiniciada.");
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
            System.err.println("⚠️ Voz 'kevin16' no encontrada. Verifica que FreeTTS esté en el classpath.");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}
public void marcarOperacionesComoCompletadas() {
this.operacionesCompletado = true;

vista.getjButtonOperaciones().setEnabled(true);
vista.getjButtonMorfemas().setEnabled(true);
JOptionPane.showMessageDialog(vista, "✔️ ¡Actividad de operaciones completada!\nAvanzaste un 15%.");
}

}
