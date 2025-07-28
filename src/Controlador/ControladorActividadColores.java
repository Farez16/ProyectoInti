package Controlador;

import VistasUnidad4.Vista_Actividad_Colores;
import Vista.Estudiante.Dashboard;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import VistasUnidad4.Vista_Unidad4;
import java.sql.Connection;
import java.time.LocalDateTime;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ControladorActividadColores {
    private final Vista_Actividad_Colores vista;
    private final Dashboard dashboard;
    private final Connection connection;
    private final String correoUsuario;
    private final ControladorDashboard controladorDashboard;
    private final int ID_UNIDAD = 4;
    
    // Mapa de validaciones con las respuestas correctas en kichwa
    private final Map<String, String> respuestasCorrectas = new HashMap<>();

    public ControladorActividadColores(Vista_Actividad_Colores vista, Dashboard dashboard, 
                                      Connection connection, ControladorDashboard controladorDashboard,
                                      String correoUsuario) {
        if (vista == null || dashboard == null || connection == null || 
            controladorDashboard == null || correoUsuario == null) {
            throw new IllegalArgumentException("Error: Parámetros no pueden ser null");
        }

        this.vista = vista;
        this.dashboard = dashboard;
        this.connection = connection;
        this.controladorDashboard = controladorDashboard;
        this.correoUsuario = correoUsuario;
        
        inicializarRespuestasCorrectas();
        configurarVista();
        agregarListeners();
    }

    private void inicializarRespuestasCorrectas() {
        // Configura las respuestas correctas para los colores en kichwa
        respuestasCorrectas.put("amarillo", "killu");
        respuestasCorrectas.put("rojo", "puka");
        respuestasCorrectas.put("naranjaz", "kishpu");
        respuestasCorrectas.put("naranja", "kishpu");
        respuestasCorrectas.put("verde", "waylla");
        respuestasCorrectas.put("morado", "maywa");
    }

    private void configurarVista() {
        vista.getjButton1().setEnabled(true);
    }

    private void agregarListeners() {
        vista.getjButton1().addActionListener(e -> validarYCompletarActividad());
    }

    private void validarYCompletarActividad() {
        if (validarRespuestas()) {
            int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Confirmas que deseas completar la actividad sobre colores?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                registrarProgresoActividad();
            }
        } else {
            JOptionPane.showMessageDialog(
                vista,
                "¡Algunas respuestas son incorrectas! Revisa nuevamente.",
                "Error en respuestas",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean validarRespuestas() {
        try {
            // Validar cada campo de texto
            return validarCampo(vista.getAmarillo(), "amarillo") &&
                   validarCampo(vista.getRojo(), "rojo") &&
                   validarCampo(vista.getNaranjaz(), "naranjaz") &&
                   validarCampo(vista.getNaranja(), "naranja") &&
                   validarCampo(vista.getVerde(), "verde") &&
                   validarCampo(vista.getMorado(), "morado");
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validarCampo(JTextField campo, String nombreCampo) {
        String respuestaUsuario = campo.getText().trim().toLowerCase();
        String respuestaCorrecta = respuestasCorrectas.get(nombreCampo).toLowerCase();
        
        if (respuestaUsuario.isEmpty()) {
            campo.setBackground(new java.awt.Color(255, 200, 200)); // Rojo claro si está vacío
            return false;
        }
        
        boolean esCorrecta = respuestaUsuario.equals(respuestaCorrecta);
        campo.setBackground(esCorrecta ? 
            new java.awt.Color(200, 255, 200) : // Verde claro si es correcta
            new java.awt.Color(255, 200, 200)); // Rojo claro si es incorrecta
        
        return esCorrecta;
    }

    private void registrarProgresoActividad() {
        try {
            int idUsuario = Usuario.obtenerIdPorCorreo(correoUsuario);
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);

            if (progreso == null) {
                progreso = new Modelo_Progreso_Usuario(0, idUsuario, ID_UNIDAD, 0, 1, false, 0, LocalDateTime.now());
                if (!Modelo_Progreso_Usuario.crearProgreso(progreso)) {
                    throw new Exception("Error al crear registro de progreso");
                }
            } else {
                progreso.setActividadesCompletadas(progreso.getActividadesCompletadas() + 1);
                progreso.setFechaActualizacion(LocalDateTime.now());
                if (!Modelo_Progreso_Usuario.actualizarProgreso(progreso)) {
                    throw new Exception("Error al actualizar progreso");
                }
            }

            JOptionPane.showMessageDialog(
                vista,
                "¡Actividad sobre colores completada correctamente!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            navegarAUnidad4();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                vista,
                "Error al guardar progreso: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void navegarAUnidad4() {
        Vista_Unidad4 vistaUnidad4 = new Vista_Unidad4();
        new Controlador_Unidad4(
            vistaUnidad4,
            connection,
            controladorDashboard,
            correoUsuario,
            null
        );
        dashboard.mostrarVista(vistaUnidad4);
    }
}