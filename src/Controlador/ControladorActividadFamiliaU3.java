package Controlador;

import VistasUnidad3.Vista_Actividad_Familia;
import Vista.Estudiante.Dashboard;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import VistasUnidad3.Vista_Unidad3;
import java.sql.Connection;
import java.time.LocalDateTime;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ControladorActividadFamiliaU3 {
    private final Vista_Actividad_Familia vista;
    private final Dashboard dashboard;
    private final Connection connection;
    private final String correoUsuario;
    private final ControladorDashboard controladorDashboard;
    private final int ID_UNIDAD = 3;
    
    // Mapa de validaciones (llena con las respuestas correctas)
    private final Map<String, String> respuestasCorrectas = new HashMap<>();

    public ControladorActividadFamiliaU3(Vista_Actividad_Familia vista, Dashboard dashboard, 
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
        
        inicializarRespuestasCorrectas(); // Configura las respuestas esperadas
        configurarVista();
        agregarListeners();
    }

    private void inicializarRespuestasCorrectas() {
        // EJEMPLO - REEMPLAZA CON LAS RESPUESTAS CORRECTAS EN KICHWA
        respuestasCorrectas.put("jTextField2", "hatun mama");  // Abuela
        respuestasCorrectas.put("jTextField3", "hatun tayta"); // Abuelo
        respuestasCorrectas.put("jTextField4", "mama");        // Mamá
        respuestasCorrectas.put("jTextField5", "tayta");       // Papá
        respuestasCorrectas.put("jTextField6", "churi");       // Hijo
        respuestasCorrectas.put("jTextField7", "ushi");        // Hija
        respuestasCorrectas.put("jTextField8", "wawki");       // Hermano
        respuestasCorrectas.put("jTextField9", "ñaña");        // Hermana
        respuestasCorrectas.put("jTextField10", "tiyu mama");  // Tía
        respuestasCorrectas.put("jTextField1", "tiyu tayta");  // Tío
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
                "¿Confirmas que deseas completar la actividad?",
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
            return validarCampo(vista.jTextField2, "jTextField2") &&
                   validarCampo(vista.jTextField3, "jTextField3") &&
                   validarCampo(vista.jTextField4, "jTextField4") &&
                   validarCampo(vista.jTextField5, "jTextField5") &&
                   validarCampo(vista.jTextField6, "jTextField6") &&
                   validarCampo(vista.jTextField7, "jTextField7") &&
                   validarCampo(vista.jTextField8, "jTextField8") &&
                   validarCampo(vista.jTextField9, "jTextField9") &&
                   validarCampo(vista.jTextField10, "jTextField10") &&
                   validarCampo(vista.jTextField1, "jTextField1");
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
                "¡Actividad completada correctamente!",
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
