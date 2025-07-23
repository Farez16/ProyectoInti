/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Controlador_InicioOperacionesU2.java
package Controlador;

import Modelo.Modelo_AnimacionTextoUnidad2;
import Vista.Estudiante.Dashboard;
import Vista.Vista_InicioOperacionesUnidad2;
import Vista.Vista_ActividadOperacionesUnidad2;
import Vista.Vista_OperacionesUnidad2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Controlador_InicioOperacionesU2 {

    private final Vista_InicioOperacionesUnidad2 vista;
    private final Dashboard dashboard;

    public Controlador_InicioOperacionesU2(Vista_InicioOperacionesUnidad2 vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;

        animarEnOrden();
        vista.getjButton1().addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        Vista_OperacionesUnidad2 panel = new Vista_OperacionesUnidad2();
        new Controlador_OperacionesUnidad2(panel, dashboard); // crea este si aún no está
        dashboard.mostrarVista(panel);
    }
});

    }

    private void animarEnOrden() {
        List<JLabel> orden = Arrays.asList(
            vista.getjLabel2debeaparecer1(),
            vista.getjLabel3debeaparecersegundo(),
            vista.getjLabel1debeaparecertercero2(),
            vista.getjLabel1debeaparecertercero(),
            vista.getjLabel1debeaparecercuarto(),
            vista.getjLabel1debeaparecercquinto1(),
            vista.getjLabel1debeaparecercsexto1(),
            vista.getjLabel1debeaparecercseptimo1(),
            vista.getjLabel1debeaparecercsoctavo1(),
            vista.getjLabel1debeaparecercsonoveno(),
            vista.getjLabel1debeaparecercdecimo()
        );

        Timer timer = new Timer();
        for (int i = 0; i < orden.size(); i++) {
            JLabel label = orden.get(i);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    label.setVisible(true);
                }
            }, (i + 1) * 1000);
        }
    }
}

