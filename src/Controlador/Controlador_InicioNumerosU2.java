/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.Vista_InicioNumerosUnidad2;
import Vista.Vista_NumerosUnidad2;
import Vista.Estudiante.Dashboard;
import Vista.Vista_NumerosActividadUnidad2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Controlador_InicioNumerosU2 {

    private final Vista_InicioNumerosUnidad2 vista;
    private final Dashboard dashboard;
    private final Timer timer;
    private int indice = 0;

    public Controlador_InicioNumerosU2(Vista_InicioNumerosUnidad2 vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;

        JLabel[] labels = vista.getLabelsOrdenados();

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (indice < labels.length) {
                    labels[indice].setVisible(true);
                    indice++;
                } else {
                    timer.stop();
                }
            }
        });

        timer.setInitialDelay(500);
        timer.start();

        vista.getjButton1CONTINUAR().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVistaNumerosActividad();
            }
        });
    }


    private void abrirVistaNumerosActividad() {
    Vista_NumerosUnidad2 vistaLeccion = new Vista_NumerosUnidad2();
    new Controlador_LeccionNumerosU2(vistaLeccion, dashboard); // Lección con botón CONTINUAR
    dashboard.mostrarVista(vistaLeccion);
}

}
