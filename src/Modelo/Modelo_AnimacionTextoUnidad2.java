/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Modelo_AnimacionTextoUnidad2 {

private final JLabel[] textos;
private int indice = 0;
private Timer timer;

public Modelo_AnimacionTextoUnidad2(JLabel[] textos) {
    this.textos = textos;
}

public void iniciarAnimacion() {
    timer = new Timer(800, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (indice < textos.length) {
                textos[indice].setVisible(true);
                indice++;
            } else {
                timer.stop();
            }
        }
    });

    timer.setInitialDelay(400); // Espera inicial antes de iniciar
    timer.start();
}}
 