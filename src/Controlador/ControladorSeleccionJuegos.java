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
    System.out.println(" Iniciando juego de animales...");
    
    try {
        // Crear vista del juego
        VistaJuego juegoAnimales = new VistaJuego();
        System.out.println(" Vista del juego creada");
        
        // Crear modelo con conexión a BD
        Juego modelo = new Juego(Conexion.conectar());
        System.out.println(" Modelo del juego creado");
        
        // Crear controlador (parámetros: vista, modelo)
        new ControladorJuego(juegoAnimales, modelo);
        System.out.println(" Controlador del juego creado");
        
        System.out.println(" Juego inicializado correctamente");
        
        // Mostrar la vista en el dashboard
        if (dashboard != null) {
            dashboard.mostrarVista(juegoAnimales);
            System.out.println(" Vista del juego mostrada en dashboard");
        } else {
            System.err.println(" Error: Dashboard es null");
        }
        
    } catch (Exception e) {
        System.err.println(" Error al abrir juego de animales: " + e.getMessage());
        e.printStackTrace();
        
        // Mostrar mensaje de error al usuario si es posible
        if (dashboard != null) {
            javax.swing.JOptionPane.showMessageDialog(
                null,
                "<html><div style='text-align: center;'>" +
                "<h3> Error al Cargar Juego</h3>" +
                "<p>No se pudo inicializar el juego de asociación.</p>" +
                "<p>Error: " + e.getMessage() + "</p>" +
                "</div></html>",
                "Error de Juego",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
}