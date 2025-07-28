package Controlador;

import VistasUnidad4.Vista_Actividad_PartesCuerpo;
import Vista.Estudiante.Dashboard;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import VistasUnidad4.Vista_Unidad4;
import java.sql.Connection;
import java.time.LocalDateTime;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class ControladorActividadPartesCuerpo {
    private final Vista_Actividad_PartesCuerpo vista;
    private final Dashboard dashboard;
    private final Connection connection;
    private final String correoUsuario;
    private final ControladorDashboard controladorDashboard;
    private final int ID_UNIDAD = 4;
    
    // Mapa de validaciones con las respuestas correctas en kichwa
    private final Map<String, String> respuestasCorrectas = new HashMap<>();

    public ControladorActividadPartesCuerpo(Vista_Actividad_PartesCuerpo vista, Dashboard dashboard, 
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
        // Configura las respuestas correctas para partes del cuerpo en kichwa
        respuestasCorrectas.put("cabeza", "uma");
        respuestasCorrectas.put("cuello", "kunka");
        respuestasCorrectas.put("mejilla", "ñawi chichu");
        respuestasCorrectas.put("pecho", "kasku");
        respuestasCorrectas.put("estomago", "puzun");
        respuestasCorrectas.put("mano", "maki");
        respuestasCorrectas.put("rodilla", "kunkuri");
        respuestasCorrectas.put("pierna", "chanka");
        respuestasCorrectas.put("tobillo", "pinkullu");
        respuestasCorrectas.put("pie", "chaki");
    }

    private void configurarVista() {
        vista.getCompletarActividad().setEnabled(true);
    }

    private void agregarListeners() {
        vista.getCompletarActividad().addActionListener(e -> validarYCompletarActividad());
    }

    private void validarYCompletarActividad() {
        if (validarRespuestas()) {
            int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Confirmas que deseas completar la actividad sobre partes del cuerpo?",
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
            return validarCampo(vista.getCabeza(), "cabeza") &&
                   validarCampo(vista.getCuello(), "cuello") &&
                   validarCampo(vista.getMejilla(), "mejilla") &&
                   validarCampo(vista.getPecho(), "pecho") &&
                   validarCampo(vista.getEstomago(), "estomago") &&
                   validarCampo(vista.getMano(), "mano") &&
                   validarCampo(vista.getRodilla(), "rodilla") &&
                   validarCampo(vista.getPierna(), "pierna") &&
                   validarCampo(vista.getTobillo(), "tobillo") &&
                   validarCampo(vista.getPie(), "pie");
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
                "¡Actividad sobre partes del cuerpo completada correctamente!",
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