package ControladoresU2;

import Controlador.ControladorDashboard;
import Controlador.Controlador_Unidades;
import Vista.Estudiante.Dashboard;
import Vista.Vista_InicioOperacionesUnidad2;
import Vista.Vista_OperacionesUnidad2;
import java.util.Objects;
import java.awt.event.ActionEvent;
import java.sql.Connection;

public class Controlador_InicioOperacionesU2 {

    private final Vista_InicioOperacionesUnidad2 vista;
    private final Dashboard dashboard;
    private final ControladorUnidad2 controladorUnidad;
    private final Connection conexion;
    private final ControladorDashboard controladorDashboard;
    private final Controlador_Unidades controladorUnidades;

    public Controlador_InicioOperacionesU2(Vista_InicioOperacionesUnidad2 vista,
                                           Dashboard dashboard,
                                           ControladorUnidad2 controladorUnidad,
                                           Connection conexion,
                                           ControladorDashboard controladorDashboard,
                                           Controlador_Unidades controladorUnidades) {
        this.vista = Objects.requireNonNull(vista);
        this.dashboard = Objects.requireNonNull(dashboard);
        this.controladorUnidad = controladorUnidad;
        this.conexion = Objects.requireNonNull(conexion);
        this.controladorDashboard = Objects.requireNonNull(controladorDashboard);
        this.controladorUnidades = Objects.requireNonNull(controladorUnidades);

        configurarEventos();
        mostrarContenidoInmediatamente();
    }

    private void mostrarContenidoInmediatamente() {
        vista.getjLabel2debeaparecer1().setVisible(true);
        vista.getjLabel3debeaparecersegundo().setVisible(true);
        // otros elementos...
    }

    private void configurarEventos() {
        vista.getjButton1().addActionListener(this::manejarBotonContinuar);
    }

    private void manejarBotonContinuar(ActionEvent e) {
        Vista_OperacionesUnidad2 panel = new Vista_OperacionesUnidad2();
        new Controlador_OperacionesUnidad2(
                panel,
                dashboard,
                controladorUnidad,
                conexion,
                controladorDashboard,
                controladorUnidades
        );
        dashboard.mostrarVista(panel);
    }
}
