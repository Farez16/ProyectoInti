/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Controlador_LeccionNumerosU2.java
package Controlador;

import Modelo.Modelo_Unidades;
import Vista.*;
import Vista.Estudiante.Dashboard;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class Controlador_LeccionNumerosU2 {

    private final Vista_NumerosUnidad2 vista;
    private final Dashboard dashboard;
    private final int idUnidad = 2;

    public Controlador_LeccionNumerosU2(Vista_NumerosUnidad2 vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;

        vista.getjButton1CONTINUAR().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVistaActividad();
            }
        });
    }

    private void abrirVistaActividad() {
        Vista_NumerosActividadUnidad2 actividad = new Vista_NumerosActividadUnidad2();
        new Controlador_NumerosUnidad2(actividad, dashboard);
        dashboard.mostrarVista(actividad);
    }
    private void finalizarActividad() {
    JOptionPane.showMessageDialog(vista, "🎉 Actividad completada correctamente.\nVolviendo al menú...");

    // Actualizar progreso
    Modelo_Unidades.actualizarProgresoActividades(idUnidad, dashboard.getCorreoUsuario(), 1); // 1 actividad hecha

    // Cargar nueva vista Unidad 2
    Vista_Unidad2 nuevaVistaUnidad2 = new Vista_Unidad2();
    ControladorUnidad2 controladorUnidad2 = new ControladorUnidad2(nuevaVistaUnidad2, dashboard);

    dashboard.mostrarVista(nuevaVistaUnidad2);
    controladorUnidad2.desbloquearBotonOperaciones(); // <- asegúrate de que este método exista y lo habilite
}

}
