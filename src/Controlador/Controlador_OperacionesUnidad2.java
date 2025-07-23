/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.Estudiante.Dashboard;
import Vista.Vista_ActividadOperacionesUnidad2;
import Vista.Vista_OperacionesUnidad2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controlador_OperacionesUnidad2 {

private final Vista_OperacionesUnidad2 vista;
private final Dashboard dashboard;

public Controlador_OperacionesUnidad2(Vista_OperacionesUnidad2 vista, Dashboard dashboard) {
    this.vista = vista;
    this.dashboard = dashboard;

    agregarEventoBoton();
}

private void agregarEventoBoton() {
    vista.getjButton1CONTINUAR().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            Vista_ActividadOperacionesUnidad2 panelActividad = new Vista_ActividadOperacionesUnidad2();
            new Controlador_ActividadOperacionesU2(panelActividad, dashboard);
            dashboard.mostrarVista(panelActividad);
        }
    });
}

}