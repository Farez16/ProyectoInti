package Controlador;

import VistasUnidad4.Vista_Leccion_Colores;
import Vista.Estudiante.Dashboard;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import VistasUnidad4.Vista_Unidad4;
import java.sql.Connection;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;

public class ControladorLeccionColores {
    private final Vista_Leccion_Colores vista;
    private final Dashboard dashboard;
    private final Connection connection;
    private final String correoUsuario;
    private final ControladorDashboard controladorDashboard;
    private final int ID_UNIDAD = 4; // Cambiado a unidad 4

    public ControladorLeccionColores(Vista_Leccion_Colores vista, Dashboard dashboard, 
                                     Connection connection, ControladorDashboard controladorDashboard,
                                     String correoUsuario) {
        // Validación de parámetros
        if (vista == null || dashboard == null || connection == null || 
            controladorDashboard == null || correoUsuario == null) {
            throw new IllegalArgumentException("Ningún parámetro puede ser null");
        }

        this.vista = vista;
        this.dashboard = dashboard;
        this.connection = connection;
        this.controladorDashboard = controladorDashboard;
        this.correoUsuario = correoUsuario;
        
        configurarVista();
        agregarListeners();
    }

    private void configurarVista() {
        vista.getCompletarLeccion().setEnabled(true);
    }

    private void agregarListeners() {
        vista.getCompletarLeccion().addActionListener(e -> completarLeccion());
    }

    private void completarLeccion() {
        int confirmacion = JOptionPane.showConfirmDialog(
            vista,
            "¿Has terminado de estudiar la lección sobre los colores?",
            "Confirmar finalización",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            registrarProgresoLeccion();
        }
    }

    private void registrarProgresoLeccion() {
        try {
            int idUsuario = obtenerIdUsuario();
            if (idUsuario <= 0) {
                throw new Exception("No se pudo obtener el ID del usuario");
            }

            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
            if (progreso == null) {
                // Si no existe progreso, crear uno nuevo con lección completada
                progreso = new Modelo_Progreso_Usuario(0, idUsuario, ID_UNIDAD, 1, 0, false, 0, LocalDateTime.now());
                if (!Modelo_Progreso_Usuario.crearProgreso(progreso)) {
                    throw new Exception("No se pudo crear el registro de progreso");
                }
                mostrarMensajeExito();
            } else if (progreso.getLeccionesCompletadas() == 0) {
                // Solo actualizar si no hay lecciones completadas
                progreso.setLeccionesCompletadas(1);
                progreso.setFechaActualizacion(LocalDateTime.now());
                if (!Modelo_Progreso_Usuario.actualizarProgreso(progreso)) {
                    throw new Exception("No se pudo actualizar el progreso");
                }
                mostrarMensajeExito();
            } else {
                JOptionPane.showMessageDialog(
                    vista,
                    "Ya has completado esta lección anteriormente",
                    "Lección ya completada",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
            navegarAUnidad4();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private int obtenerIdUsuario() throws Exception {
        int idUsuario = Usuario.obtenerIdPorCorreo(correoUsuario);
        if (idUsuario <= 0) {
            throw new Exception("No se pudo obtener el ID del usuario");
        }
        return idUsuario;
    }

    private void mostrarMensajeExito() {
        JOptionPane.showMessageDialog(
            vista,
            "¡Lección sobre colores marcada como completada!",
            "Progreso guardado",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(
            vista,
            "Error: " + mensaje,
            "Error al guardar",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private void navegarAUnidad4() {
        // Regresar al panel de unidades para que se actualice la vista existente
        // Esto permite que el controlador de unidades actualice el progreso automáticamente
        System.out.println("[ControladorLeccionColores] Navegando de vuelta al panel de unidades tras completar lección");
        dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
    }
}
