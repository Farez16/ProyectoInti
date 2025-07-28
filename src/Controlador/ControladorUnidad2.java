package Controlador;

import Modelo.Modelo_AnimacionEntradaUnidad2;
import Modelo.Modelo_ProgresoUnidad2;
import Vista.Vista_EvaluacionU2;
import Vista.Estudiante.Vista_PanelUnidades;
import javax.swing.*;
import Vista.Estudiante.Dashboard;
import Vista.Estudiante.Vista_PanelUnidades;
import Vista.Vista_ActividadMorfemasUnidad2;
import Vista.Vista_InicioNumerosUnidad2;
import Vista.Vista_InicioOperacionesUnidad2;
import Vista.Vista_Unidad2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorUnidad2 {

    private final Vista_Unidad2 vista;
    private boolean numerosCompletado = false;
    private boolean operacionesCompletado = false;
    private boolean morfemasCompletado = false;
    private boolean animalesCompletado = false;
    private boolean evaluacionCompletada = true; // ← Para pruebas, marcar como true
    private final Dashboard dashboard;
    private Controlador_Unidades controladorUnidades;
    private String correoUsuario;

    public ControladorUnidad2(Vista_Unidad2 vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;
        inicializar();
        cargarProgreso();
        configurarEventos();
    }
    
    // Constructor con controlador de unidades para sincronización
    public ControladorUnidad2(Vista_Unidad2 vista, Dashboard dashboard, Controlador_Unidades controladorUnidades, String correoUsuario) {
        this.vista = vista;
        this.dashboard = dashboard;
        this.controladorUnidades = controladorUnidades;
        this.correoUsuario = correoUsuario;
        inicializar();
        cargarProgreso();
        configurarEventos();
        sincronizarProgresoConDashboard();
    }

    private void inicializar() {
        desactivarBotonesIniciales();
        agregarEventos();
    }

    public void desbloquearBotonOperaciones() {
        vista.getjButtonOperaciones().setEnabled(true);
        vista.getjButtonOperaciones().addActionListener(e -> {
            Vista_InicioOperacionesUnidad2 panel = new Vista_InicioOperacionesUnidad2();
            dashboard.mostrarVista(panel);
        });
    }

    /**
     * Sincroniza el progreso de la Unidad 2 con la barra ProgresoU2 del dashboard
     */
    private void sincronizarProgresoConDashboard() {
        if (controladorUnidades != null && correoUsuario != null) {
            // Obtener progreso actual de la Unidad 2 desde el modelo
            int progresoActual = Modelo_ProgresoUnidad2.obtenerProgreso(correoUsuario);
            
            // Sincronizar con la barra ProgresoU2 del dashboard
            controladorUnidades.actualizarProgresoUnidad(2, progresoActual);
            System.out.println("Unidad 2: Sincronizando progreso con dashboard: " + progresoActual + "%");
        }
    }
    
    /**
     * Actualiza el progreso de la Unidad 2 y sincroniza con el dashboard
     * @param nuevoProgreso Nuevo valor de progreso (0-100)
     */
    public void actualizarProgreso(int nuevoProgreso) {
        if (correoUsuario != null) {
            // Actualizar en la base de datos
            Modelo_ProgresoUnidad2.actualizarProgreso(correoUsuario, nuevoProgreso);
            
            // Sincronizar con dashboard
            sincronizarProgresoConDashboard();
        }
    }
    
    /**
     * Incrementa el progreso de la Unidad 2 y sincroniza con el dashboard
     * @param incremento Cantidad a incrementar (porcentaje)
     */
    public void incrementarProgreso(int incremento) {
        if (correoUsuario != null) {
            // Incrementar en la base de datos
            Modelo_ProgresoUnidad2.incrementarProgreso(correoUsuario, incremento);
            
            // Sincronizar con dashboard
            sincronizarProgresoConDashboard();
        }
    }

    private void desactivarBotonesIniciales() {
        vista.getjButtonOperaciones().setEnabled(false);
        vista.getjButtonMorfemas().setEnabled(false);
        vista.getjButtonAnimales().setEnabled(false);
        vista.getjButtonEvaluacion().setEnabled(true); // ← FORZAMOS QUE SIEMPRE ESTÉ ACTIVO
        vista.getjButtonFINALIZARUNIDAD1().setEnabled(true);
    }

    private void agregarEventos() {
        vista.getjButtonNumeros().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numerosCompletado = true;
                vista.getjButtonOperaciones().setEnabled(true);
            }
        });
        vista.getjButtonNumeros().addActionListener(e -> {
            Vista_InicioNumerosUnidad2 panelNumeros = new Vista_InicioNumerosUnidad2();
            new Controlador_InicioNumerosU2(panelNumeros, dashboard);
            dashboard.mostrarVista(panelNumeros);
        });

        vista.getjButtonOperaciones().addActionListener(e -> {
            Vista_InicioOperacionesUnidad2 panel = new Vista_InicioOperacionesUnidad2();
            new Controlador_InicioOperacionesU2(panel, dashboard);
            Modelo_AnimacionEntradaUnidad2 anim = new Modelo_AnimacionEntradaUnidad2(panel);
            anim.mostrarConAnimacion();
            dashboard.mostrarVista(panel);
        });

        vista.getjButtonMorfemas().addActionListener(e -> {
            Vista_ActividadMorfemasUnidad2 panel = new Vista_ActividadMorfemasUnidad2();
            new Controlador_ActividadMorfemasU2(panel, dashboard);
            dashboard.mostrarVista(panel);
        });

        vista.getjButtonAnimales().addActionListener(e -> {
            boolean esDomestico = true;
            new Controlador_ActividadAnimalesdelaSierraUnidad2(dashboard, esDomestico);
        });

        vista.getjButtonEvaluacion().addActionListener(e -> {
            Vista_EvaluacionU2 vistaEvaluacion = new Vista_EvaluacionU2();
            new Controlador_EvaluacionU2(vistaEvaluacion, dashboard);
            dashboard.mostrarVista(vistaEvaluacion);
        });

        vista.getjButtonFINALIZARUNIDAD1().addActionListener(e -> {
            if (evaluacionCompletada) {
                JOptionPane.showMessageDialog(vista, "🎉 ¡Has cumplido con mucho esfuerzo la Unidad 2!\n🚀 Ahora continúa tu transcurso en la Unidad 3.");
                Vista_PanelUnidades panelUnidades = new Vista_PanelUnidades();
                dashboard.mostrarVista(panelUnidades);
            } else {
                JOptionPane.showMessageDialog(vista, "⚠️ Debes completar la evaluación para finalizar la unidad.");
            }
        });

        vista.getjButtonBack().addActionListener(e -> {
            JOptionPane.showMessageDialog(vista, "Volviendo al panel de unidades...");
        });

        vista.getjButtonREINICIARU1().addActionListener(e -> reiniciarUnidad());
    }

    private void reiniciarUnidad() {
        numerosCompletado = false;
        operacionesCompletado = false;
        morfemasCompletado = false;
        animalesCompletado = false;
        evaluacionCompletada = false;

        vista.getjButtonNumeros().setEnabled(true);
        vista.getjButtonOperaciones().setEnabled(false);
        vista.getjButtonMorfemas().setEnabled(false);
        vista.getjButtonAnimales().setEnabled(false);
        vista.getjButtonEvaluacion().setEnabled(true);
        vista.getjButtonFINALIZARUNIDAD1().setEnabled(false);

        JOptionPane.showMessageDialog(vista, "Unidad 2 reiniciada.");
    }

    private void cargarProgreso() {
        int progreso = Modelo_ProgresoUnidad2.obtenerProgreso(dashboard.getCorreoUsuario());
        vista.actualizarProgreso(progreso);
    }

     private void configurarEventos() {
        // ControladorUnidad2.java
vista.getjButtonBack().addActionListener((ActionEvent e) -> {
    dashboard.mostrarVista(new Vista_PanelUnidades()); // Corregido aquí
});

        vista.getjButtonNumeros().addActionListener((ActionEvent e) -> {
            Vista_InicioNumerosUnidad2 panel = new Vista_InicioNumerosUnidad2();
            new Controlador_InicioNumerosU2(panel, dashboard);
            dashboard.mostrarVista(panel);
        });

        vista.getjButtonOperaciones().addActionListener((ActionEvent e) -> {
            Vista_InicioOperacionesUnidad2 panel = new Vista_InicioOperacionesUnidad2();
            new Controlador_InicioOperacionesU2(panel, dashboard);
            dashboard.mostrarVista(panel);
        });

        vista.getjButtonAnimales().addActionListener((ActionEvent e) -> {
            // Controlador maneja sus propias vistas
            new Controlador_ActividadAnimalesdelaSierraUnidad2(dashboard, true);
        });

        vista.getjButtonMorfemas().addActionListener((ActionEvent e) -> {
            Vista_ActividadMorfemasUnidad2 panel = new Vista_ActividadMorfemasUnidad2();
            new Controlador_ActividadMorfemasU2(panel, dashboard);
            dashboard.mostrarVista(panel);
        });

        vista.getjButtonEvaluacion().addActionListener((ActionEvent e) -> {
            Vista_EvaluacionU2 panel = new Vista_EvaluacionU2();
            new Controlador_EvaluacionU2(panel, dashboard);
            dashboard.mostrarVista(panel);
        });

        vista.getjButtonFINALIZARUNIDAD1().addActionListener(e -> {
            int progreso = Modelo_ProgresoUnidad2.obtenerProgreso(dashboard.getCorreoUsuario());
            if (progreso >= 100) {
                JOptionPane.showMessageDialog(vista, "🎉 ¡Felicidades! Completaste la Unidad 2");
                dashboard.mostrarVista(new Vista_PanelUnidades());
            } else {
                JOptionPane.showMessageDialog(vista, "⚠️ Completa todas las actividades primero");
            }
        });
    }
    
    public void actualizarProgreso() {
        cargarProgreso();
    }

    public void marcarOperacionesComoCompletadas() {
        // Actualizar estado en la vista
        vista.getjButtonOperaciones().setEnabled(true);
        
        // Actualizar base de datos (55% de progreso)
        Modelo_ProgresoUnidad2.actualizarProgreso(
            dashboard.getCorreoUsuario(), 
            55
        );
        
        // Actualizar barra de progreso
        cargarProgreso();
    }
    

    public void marcarMorfemasComoCompletadas() {
        this.morfemasCompletado = true;
        vista.getjButtonMorfemas().setEnabled(true);
        vista.getjButtonAnimales().setEnabled(true);
        JOptionPane.showMessageDialog(vista, "✔️ ¡Actividad de morfemas completada!\nAvanzaste un 30%.");
    }

    public void marcarAnimalesComoCompletados() {
        this.animalesCompletado = true;
        vista.getjButtonAnimales().setEnabled(true);
        vista.getjButtonEvaluacion().setEnabled(true);
        JOptionPane.showMessageDialog(vista, "✔️ ¡Actividad de animales completada!\nAvanzaste un 40%.");
    }
    
} 