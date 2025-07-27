package Controlador;

import VistasUnidad3.Vista_Actividad_Vestimenta;
import Vista.Estudiante.Dashboard;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import VistasUnidad3.Vista_Unidad3;
import java.sql.Connection;
import java.time.LocalDateTime;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ControladorActividadVestimentaU3 {
    private final Vista_Actividad_Vestimenta vista;
    private final Dashboard dashboard;
    private final Connection connection;
    private final String correoUsuario;
    private final ControladorDashboard controladorDashboard;
    private final int ID_UNIDAD = 3;
    
    // Mapa de validaciones con las respuestas correctas en kichwa
    private final Map<String, String> respuestasCorrectas = new HashMap<>();

    public ControladorActividadVestimentaU3(Vista_Actividad_Vestimenta vista, Dashboard dashboard, 
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
        // Configura las respuestas correctas para la vestimenta en kichwa
        respuestasCorrectas.put("Zapatos", "ushuta");
        respuestasCorrectas.put("Camiseta", "uku kushma");
        respuestasCorrectas.put("Pantalon", "wara");
        respuestasCorrectas.put("Falda", "anaku");
        respuestasCorrectas.put("Blusa", "pintu");
    }

    private void configurarVista() {
        vista.jButton1.setEnabled(true);
    }

    private void agregarListeners() {
        vista.jButton1.addActionListener(e -> validarYCompletarActividad());
    }

    private void validarYCompletarActividad() {
        if (validarRespuestas()) {
            int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Confirmas que deseas completar la actividad sobre vestimenta?",
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
            return validarCampo(vista.Zapatos, "Zapatos") &&
                   validarCampo(vista.Camiseta, "Camiseta") &&
                   validarCampo(vista.Pantalon, "Pantalon") &&
                   validarCampo(vista.Falda, "Falda") &&
                   validarCampo(vista.Blusa, "Blusa");
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
                "¡Actividad sobre vestimenta completada correctamente!",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            navegarAUnidad3();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                vista,
                "Error al guardar progreso: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void navegarAUnidad3() {
        Vista_Unidad3 vistaUnidad3 = new Vista_Unidad3();
        new Controlador_Unidad3(
            vistaUnidad3,
            connection,
            controladorDashboard,
            correoUsuario,
            null
        );
        dashboard.mostrarVista(vistaUnidad3);
    }
}