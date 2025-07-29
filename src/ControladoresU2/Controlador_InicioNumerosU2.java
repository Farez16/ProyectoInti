package ControladoresU2;

import Vista.Vista_InicioNumerosUnidad2;
import Vista.Vista_NumerosActividadUnidad2;
import Vista.Estudiante.Dashboard;
import ControladoresU2.Controlador_LeccionNUmerosU2;
import Controlador.Controlador_Unidades;
import Controlador.ControladorDashboard;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Objects;

public class Controlador_InicioNumerosU2 {

    private final Vista_InicioNumerosUnidad2 vista;
    private final Dashboard dashboard;
    private final ControladorUnidad2 controladorUnidad2;
    private final Controlador_Unidades controladorUnidades;
    private final String correoUsuario;
    private final Connection conexion;
    private final ControladorDashboard controladorDashboard;

    private static final int ID_UNIDAD = 2;

    public Controlador_InicioNumerosU2(Vista_InicioNumerosUnidad2 vista,
                                       Dashboard dashboard,
                                       ControladorUnidad2 controladorUnidad2,
                                       Controlador_Unidades controladorUnidades,
                                       String correoUsuario,
                                       Connection conexion,
                                       ControladorDashboard controladorDashboard) {
        this.vista = Objects.requireNonNull(vista);
        this.dashboard = Objects.requireNonNull(dashboard);
        this.controladorUnidad2 = Objects.requireNonNull(controladorUnidad2);
        this.controladorUnidades = Objects.requireNonNull(controladorUnidades);
        this.correoUsuario = Objects.requireNonNull(correoUsuario);
        this.conexion = Objects.requireNonNull(conexion);
        this.controladorDashboard = Objects.requireNonNull(controladorDashboard);

        mostrarContenidoInmediatamente();
        configurarListeners();
    }

    private void mostrarContenidoInmediatamente() {
        JLabel[] labels = vista.getLabelsOrdenados();
        for (JLabel label : labels) {
            label.setVisible(true);
        }
        vista.getjButton1CONTINUAR().setEnabled(true);
    }

    private void configurarListeners() {
        vista.getjButton1CONTINUAR().addActionListener(this::manejarBotonContinuar);
    }

    private void manejarBotonContinuar(ActionEvent e) {
        registrarProgresoLeccion();

        Vista_NumerosActividadUnidad2 vistaLeccion = new Vista_NumerosActividadUnidad2();
        Controlador_LeccionNUmerosU2 controlador = new Controlador_LeccionNUmerosU2(
            vistaLeccion,
            dashboard,
            controladorUnidad2,
            controladorUnidades,
            correoUsuario,
            conexion,
            controladorDashboard
        );
        dashboard.mostrarVista(vistaLeccion);
    }

    private void registrarProgresoLeccion() {
        try {
            int idUsuario = obtenerIdUsuario();
            if (idUsuario <= 0) {
                throw new Exception("No se pudo obtener el ID del usuario");
            }

            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);

            if (progreso == null) {
                progreso = new Modelo_Progreso_Usuario(0, idUsuario, ID_UNIDAD, 1, 0, false, 0, LocalDateTime.now());
                if (!Modelo_Progreso_Usuario.crearProgreso(progreso)) {
                    throw new Exception("No se pudo crear el registro de progreso");
                }
                actualizarBarraProgreso(1);
                mostrarMensajeExito();
            } else if (progreso.getLeccionesCompletadas() == 0) {
                progreso.setLeccionesCompletadas(1);
                progreso.setFechaActualizacion(LocalDateTime.now());
                if (!Modelo_Progreso_Usuario.actualizarProgreso(progreso)) {
                    throw new Exception("No se pudo actualizar el progreso");
                }
                actualizarBarraProgreso(1);
                mostrarMensajeExito();
            } else {
                JOptionPane.showMessageDialog(
                    vista,
                    "Ya has completado esta lección anteriormente",
                    "Lección ya completada",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void actualizarBarraProgreso(int leccionesCompletadas) {
        JProgressBar barra = controladorUnidad2.getVista().getjProgressBarUNIDAD2();
        if (barra != null) {
            barra.setValue(leccionesCompletadas);
        }
    }

    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(
            vista,
            "¡Has completado la lección correctamente!",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            vista,
            mensaje,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    private int obtenerIdUsuario() throws Exception {
        int idUsuario = Usuario.obtenerIdPorCorreo(correoUsuario);
        if (idUsuario <= 0) {
            throw new Exception("No se pudo obtener el ID del usuario");
        }
        return idUsuario;
    }
}



