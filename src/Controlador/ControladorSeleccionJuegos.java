/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// ControladorSeleccionJuegos.java (nuevo)
package Controlador;

import Conexion.Conexion;
import Modelo.Juego;
import Vista.Estudiante.Dashboard;
import VistaJuegos.VistaJuego;
import VistaJuegos.Vista_SelecciondeJuegos;
import VistaJuegos.Vista_JuegoMemorama;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorSeleccionJuegos {

    private Vista_SelecciondeJuegos vista;
    private Dashboard dashboard;

    public ControladorSeleccionJuegos(Vista_SelecciondeJuegos vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;
        agregarEventos();
    }

    private void agregarEventos() {
    vista.getjButton1Memorama().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            abrirMemorama();
        }
    });

    // ✅ ESTE ES EL EVENTO QUE FALTABA PARA ABRIR EL JUEGO DE ANIMALES
    vista.getjButton2Animales().addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            abrirJuegoAnimales();
        }
    });
}
    private void abrirMemorama() {
        Vista_JuegoMemorama memorama = new Vista_JuegoMemorama();
        new ControladorMemorama(memorama, dashboard);
        dashboard.mostrarVista(memorama);
    }
    private void abrirJuegoAnimales() {
    VistaJuego juegoAnimales = new VistaJuego();
    Juego modelo = new Juego(Conexion.conectar());
    new ControladorJuego(juegoAnimales, modelo); // Asegúrate de que este constructor exista y funcione
    dashboard.mostrarVista(juegoAnimales);
}
}