/*
 * Controlador para la lección de operaciones matemáticas en Kichwa
 */
package ControladoresU2;

import Controlador.ControladorDashboard;
import Controlador.Controlador_Unidades;
import ControladoresU2.ControladorUnidad2;
import Vista.Estudiante.Dashboard;
import Vista.Vista_ActividadOperacionesUnidad2;
import Vista.Vista_OperacionesUnidad2;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.Objects;
import javax.swing.JOptionPane;

public class Controlador_OperacionesUnidad2 {

    private final Vista_OperacionesUnidad2 vista;
    private final Dashboard dashboard;
    private final ControladorUnidad2 controladorUnidad;
    private final Connection conexion;
    private final ControladorDashboard controladorDashboard;
    private final Controlador_Unidades controladorUnidades;

    public Controlador_OperacionesUnidad2(
            Vista_OperacionesUnidad2 vista,
            Dashboard dashboard,
            ControladorUnidad2 controladorUnidad,
            Connection conexion,
            ControladorDashboard controladorDashboard,
            Controlador_Unidades controladorUnidades
    ) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser nula");
        this.dashboard = Objects.requireNonNull(dashboard, "El dashboard no puede ser nulo");
        this.controladorUnidad = controladorUnidad; // Puede ser null si no se necesita
        this.conexion = Objects.requireNonNull(conexion, "La conexión no puede ser nula");
        this.controladorDashboard = Objects.requireNonNull(controladorDashboard, "El controladorDashboard no puede ser nulo");
        this.controladorUnidades = Objects.requireNonNull(controladorUnidades, "El controladorUnidades no puede ser nulo");

        configurarInteracciones();
    }

    private void configurarInteracciones() {
        configurarBotonContinuar();
        // Puedes agregar más configuraciones aquí si es necesario
    }

    private void configurarBotonContinuar() {
        vista.getjButton1CONTINUAR().addActionListener(this::manejarContinuar);
    }

    private void manejarContinuar(ActionEvent e) {
        try {
            int confirmacion = JOptionPane.showConfirmDialog(
                    vista,
                    "¿Estás listo para comenzar la actividad de operaciones?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (confirmacion == JOptionPane.YES_OPTION) {
                navegarAActividad();
            }
        } catch (Exception ex) {
            manejarError("Error al navegar a la actividad: " + ex.getMessage());
        }
    }

    private void navegarAActividad() {
        Vista_ActividadOperacionesUnidad2 panelActividad = new Vista_ActividadOperacionesUnidad2();
        new Controlador_ActividadOperacionesU2(
                panelActividad,
                dashboard,
                conexion,
                controladorDashboard,
                controladorUnidades
        );
        dashboard.mostrarVista(panelActividad);
    }

    private void manejarError(String mensaje) {
        JOptionPane.showMessageDialog(
                vista,
                mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}

