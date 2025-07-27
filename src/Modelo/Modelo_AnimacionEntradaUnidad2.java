/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Esta clase muestra un JPanel con una animación de entrada (fade o delay)
 */
public class Modelo_AnimacionEntradaUnidad2 {

    private final JPanel panel;
    private Timer timer;
    private int paso = 0;

    public Modelo_AnimacionEntradaUnidad2(JPanel panel) {
        this.panel = panel;
    }

    public void mostrarConAnimacion() {
        panel.setVisible(false);
        paso = 0;

        timer = new Timer(0, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paso++;
                if (paso >= 3) {
                    panel.setVisible(true);
                    timer.stop();
                }
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }
}
