/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Modelo_AnimacionTextoUnidad2 {
private final JLabel[] etiquetas;
private int indice;

public Modelo_AnimacionTextoUnidad2(JLabel[] etiquetas) {
    this.etiquetas = etiquetas;
}

public void iniciarAnimacion() {
    Timer timer = new Timer();
    indice = 0;

    for (JLabel label : etiquetas) {
        label.setVisible(false);
    }

    timer.schedule(new TimerTask() {
        @Override
        public void run() {
            if (indice < etiquetas.length) {
                etiquetas[indice].setVisible(true);
                indice++;
            } else {
                timer.cancel();
            }
        }
    }, 0, 500); // Cada 500ms muestra una etiqueta
}
}