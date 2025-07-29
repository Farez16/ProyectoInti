package Controlador;

import Vista.Estudiante.Dashboard;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Objects;
import VistasUnidad4.Vista_EvaluacionU4;
import VistasUnidad4.Vista_Actividad_Colores;
import VistasUnidad4.Vista_Actividad_PartesCuerpo;
import VistasUnidad4.Vista_Leccion_Colores;
import VistasUnidad4.Vista_Leccion_PartesCuerpo;
import VistasUnidad4.Vista_Unidad4;
import javax.swing.JOptionPane;

public class Controlador_Unidad4 {

    private final Vista_Unidad4 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 4;
    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;
    private final Controlador_Unidades controladorUnidades;

    public Controlador_Unidad4(Vista_Unidad4 vista, Connection conn,
            ControladorDashboard controladorDashboard,
            String correo, Controlador_Unidades controladorUnidades) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser null");
        this.conn = Objects.requireNonNull(conn, "La conexión no puede ser null");
        this.controladorDashboard = Objects.requireNonNull(controladorDashboard,
                "El controladorDashboard no puede ser null");
        this.correo = Objects.requireNonNull(correo, "El correo no puede ser null");
        this.controladorUnidades = controladorUnidades;
        this.dashboard = controladorDashboard.getVista();
        this.idUsuario = Usuario.obtenerIdPorCorreo(correo);

        if (this.idUsuario <= 0) {
            throw new IllegalArgumentException("No se pudo obtener el ID del usuario");
        }

        inicializarVista();
        agregarListeners();
    }

    private void inicializarVista() {
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        if (progreso == null) {
            progreso = new Modelo_Progreso_Usuario(0, idUsuario, ID_UNIDAD, 0, 0, false, 0, LocalDateTime.now());
            Modelo_Progreso_Usuario.guardarProgreso(progreso);
        }

        int progresoTotal = calcularProgreso(
                progreso.getLeccionesCompletadas(),
                progreso.getActividadesCompletadas(),
                progreso.isEvaluacionAprobada()
        );
        vista.jProgressBarUNIDAD4.setValue(progresoTotal);
        
        // Sincronizar con la barra de progreso del panel de unidades
        if (controladorUnidades != null) {
            controladorUnidades.actualizarProgresoUnidad(4, progresoTotal);
        }

        // Habilitar botones según progreso - LÓGICA SECUENCIAL COMO OTRAS UNIDADES
        int leccionesCompletadas = progreso.getLeccionesCompletadas();
        int actividadesCompletadas = progreso.getActividadesCompletadas();
        boolean evaluacionAprobada = progreso.isEvaluacionAprobada();
        
        // Inicialmente deshabilitamos todos los botones
        vista.jButtonFamilia.setEnabled(false);
        vista.jButtonActFamilia.setEnabled(false);
        vista.jButtonVestimenta.setEnabled(false);
        vista.jButtonActVestimenta.setEnabled(false);
        vista.jButtonEvaluacion.setEnabled(false);
        vista.jButtonFINALIZARUNIDAD1.setEnabled(false);
        
        // Paso 1: Lección Familia (Colores) - Siempre disponible
        vista.jButtonFamilia.setEnabled(true);
        
        // Paso 2: Actividad Familia - Requiere Lección Familia completada
        if (leccionesCompletadas >= 1) {
            vista.jButtonActFamilia.setEnabled(true);
        }
        
        // Paso 3: Lección Vestimenta (Partes del Cuerpo) - Requiere Actividad Familia completada
        if (actividadesCompletadas >= 1) {
            vista.jButtonVestimenta.setEnabled(true);
        }
        
        // Paso 4: Actividad Vestimenta - Requiere Lección Vestimenta completada
        if (leccionesCompletadas >= 2) {
            vista.jButtonActVestimenta.setEnabled(true);
        }
        
        // Paso 5: Evaluación - Requiere Actividad Vestimenta completada
        if (actividadesCompletadas >= 2) {
            vista.jButtonEvaluacion.setEnabled(true);
        }
        
        // Paso 6: Finalizar Unidad - Requiere Evaluación aprobada
        if (evaluacionAprobada) {
            vista.jButtonFINALIZARUNIDAD1.setEnabled(true);
        }
        
        // Logging detallado para debugging
        System.out.println("[Unidad4] Progreso actual - Lecciones: " + leccionesCompletadas + ", Actividades: " + actividadesCompletadas + ", Evaluación: " + evaluacionAprobada);
        System.out.println("[Unidad4] Flujo de desbloqueo:");
        System.out.println("  1. Familia (Colores): " + vista.jButtonFamilia.isEnabled() + " (siempre disponible)");
        System.out.println("  2. ActFamilia: " + vista.jButtonActFamilia.isEnabled() + " (requiere 1 lección)");
        System.out.println("  3. Vestimenta (Partes): " + vista.jButtonVestimenta.isEnabled() + " (requiere 1 actividad)");
        System.out.println("  4. ActVestimenta: " + vista.jButtonActVestimenta.isEnabled() + " (requiere 2 lecciones)");
        System.out.println("  5. Evaluación: " + vista.jButtonEvaluacion.isEnabled() + " (requiere 2 actividades)");
        System.out.println("  6. Finalizar: " + vista.jButtonFINALIZARUNIDAD1.isEnabled() + " (requiere evaluación aprobada)");
    }

    private void agregarListeners() {
        vista.jButtonFamilia.addActionListener(e -> abrirLeccionFamilia());
        vista.jButtonActFamilia.addActionListener(e -> abrirActividadFamilia());
        vista.jButtonVestimenta.addActionListener(e -> abrirLeccionVestimenta());
        vista.jButtonActVestimenta.addActionListener(e -> abrirActividadVestimenta());
        vista.jButtonEvaluacion.addActionListener(e -> abrirEvaluacion());
        vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgresoUnidad4());

        vista.jButtonBack.addActionListener(e -> {
            dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
        });

        vista.jButtonFINALIZARUNIDAD1.addActionListener(e -> {
            dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
        });
    }

   private int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
     // Usar la lógica específica para Unidad 4 (2 lecciones + 2 actividades + evaluación)
     return CalculadorProgreso.calcularProgresoUnidad4(lecciones, actividades, evaluacion);
 }

    private void abrirLeccionFamilia() {
        Vista_Leccion_Colores vistaLeccionColores = new Vista_Leccion_Colores();
        new ControladorLeccionColores(
                vistaLeccionColores,
                dashboard, // Tu instancia de Dashboard
                conn, // Conexión a la base de datos
                controladorDashboard, // Instancia de ControladorDashboard
                correo // Correo del usuario actual
        );
        dashboard.mostrarVista(vistaLeccionColores);
    }

    private void abrirActividadFamilia() {
        Vista_Actividad_Colores vistaActividad = new Vista_Actividad_Colores();
        new ControladorActividadColores(
                vistaActividad,
                dashboard,
                conn,
                controladorDashboard,
                correo
        );
        dashboard.mostrarVista(vistaActividad);
    }

    private void abrirLeccionVestimenta() {
        Vista_Leccion_PartesCuerpo vistaLeccion = new Vista_Leccion_PartesCuerpo();
        new ControladorLeccionPartesCuerpo(
                vistaLeccion,
                dashboard,
                conn,
                controladorDashboard,
                correo
        );
        dashboard.mostrarVista(vistaLeccion);
    }

    private void abrirActividadVestimenta() {
        Vista_Actividad_PartesCuerpo vistaActividad = new Vista_Actividad_PartesCuerpo();
        new ControladorActividadPartesCuerpo(
                vistaActividad,
                dashboard,
                conn,
                controladorDashboard,
                correo
        );
        dashboard.mostrarVista(vistaActividad);
    }

    private void abrirEvaluacion() {
        try {
            System.out.println("[DEBUG] Iniciando apertura de evaluación Unidad 4");
            
            // Verificar que el botón esté habilitado
            if (!vista.jButtonEvaluacion.isEnabled()) {
                System.err.println("[ERROR] El botón de evaluación no está habilitado");
                JOptionPane.showMessageDialog(vista, 
                    "<html><body style='width: 300px; text-align: center;'>" +
                    "<h3>🚫 Evaluación no disponible</h3>" +
                    "<p>Debes completar todas las lecciones y actividades antes de acceder a la evaluación.</p>" +
                    "</body></html>", 
                    "Acceso denegado", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Verificar progreso del usuario
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
            if (progreso == null) {
                System.err.println("[ERROR] No se pudo obtener el progreso del usuario");
                JOptionPane.showMessageDialog(vista, 
                    "Error al verificar el progreso. Intente nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            System.out.println("[DEBUG] Progreso verificado - Lecciones: " + progreso.getLeccionesCompletadas() + 
                             ", Actividades: " + progreso.getActividadesCompletadas());
            
            // Crear la vista de evaluación
            System.out.println("[DEBUG] Creando vista de evaluación U4");
            Vista_EvaluacionU4 vistaEvaluacion = new Vista_EvaluacionU4();
            
            if (vistaEvaluacion == null) {
                System.err.println("[ERROR] No se pudo crear la vista de evaluación");
                JOptionPane.showMessageDialog(vista, 
                    "Error al cargar la evaluación. Intente nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el controlador de evaluación
            System.out.println("[DEBUG] Creando controlador de evaluación U4");
            Controlador_EvaluacionU4 controladorEvaluacion = new Controlador_EvaluacionU4(
                vistaEvaluacion, controladorDashboard, conn, correo, controladorUnidades
            );
            
            if (controladorEvaluacion == null) {
                System.err.println("[ERROR] No se pudo crear el controlador de evaluación");
                JOptionPane.showMessageDialog(vista, 
                    "Error al inicializar la evaluación. Intente nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Mostrar la vista en el dashboard
            System.out.println("[DEBUG] Mostrando vista de evaluación en dashboard");
            if (dashboard != null) {
                dashboard.mostrarVista(vistaEvaluacion);
                System.out.println("[DEBUG] Evaluación U4 abierta exitosamente");
            } else {
                System.err.println("[ERROR] Dashboard es null, no se puede mostrar la vista");
                JOptionPane.showMessageDialog(vista, 
                    "Error en la navegación. Intente nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Excepción al abrir evaluación U4: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, 
                "<html><body style='width: 300px; text-align: center;'>" +
                "<h3>❌ Error inesperado</h3>" +
                "<p>Ocurrió un error al abrir la evaluación.</p>" +
                "<p><b>Detalles:</b> " + e.getMessage() + "</p>" +
                "<p>Por favor, intente nuevamente o contacte al administrador.</p>" +
                "</body></html>", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reiniciarProgresoUnidad4() {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas reiniciar tu progreso en la Unidad 4?\nEsta acción no se puede deshacer.",
                "Confirmar reinicio",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
            if (progreso != null) {
                progreso.setLeccionesCompletadas(0);
                progreso.setActividadesCompletadas(0);
                progreso.setEvaluacionAprobada(false);
                progreso.setCalificacion(0);
                progreso.setFechaActualizacion(LocalDateTime.now());
                Modelo_Progreso_Usuario.actualizarProgreso(progreso);
                inicializarVista();
                JOptionPane.showMessageDialog(vista, "Progreso reiniciado correctamente.");
            }
        }
    }

    /**
     * Método para actualizar la vista cuando se completa una actividad o lección
     */
    public void actualizarVista() {
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        if (progreso != null) {
            // Habilitar botones según progreso - LÓGICA SECUENCIAL UNIFICADA CON inicializarVista()
            int leccionesCompletadas = progreso.getLeccionesCompletadas();
            int actividadesCompletadas = progreso.getActividadesCompletadas();
            boolean evaluacionAprobada = progreso.isEvaluacionAprobada();
            
            // Inicialmente deshabilitamos todos los botones
            vista.jButtonFamilia.setEnabled(false);
            vista.jButtonActFamilia.setEnabled(false);
            vista.jButtonVestimenta.setEnabled(false);
            vista.jButtonActVestimenta.setEnabled(false);
            vista.jButtonEvaluacion.setEnabled(false);
            vista.jButtonFINALIZARUNIDAD1.setEnabled(false);
            
            // Paso 1: Lección Familia (Colores) - Siempre disponible
            vista.jButtonFamilia.setEnabled(true);
            
            // Paso 2: Actividad Familia - Requiere Lección Familia completada
            if (leccionesCompletadas >= 1) {
                vista.jButtonActFamilia.setEnabled(true);
            }
            
            // Paso 3: Lección Vestimenta (Partes del Cuerpo) - Requiere Actividad Familia completada
            if (actividadesCompletadas >= 1) {
                vista.jButtonVestimenta.setEnabled(true);
            }
            
            // Paso 4: Actividad Vestimenta - Requiere Lección Vestimenta completada
            if (leccionesCompletadas >= 2) {
                vista.jButtonActVestimenta.setEnabled(true);
            }
            
            // Paso 5: Evaluación - Requiere Actividad Vestimenta completada
            if (actividadesCompletadas >= 2) {
                vista.jButtonEvaluacion.setEnabled(true);
            }
            
            // Paso 6: Finalizar Unidad - Requiere Evaluación aprobada
            if (evaluacionAprobada) {
                vista.jButtonFINALIZARUNIDAD1.setEnabled(true);
            }
            
            // Logging para debugging en actualización
            System.out.println("[Unidad4-Update] Progreso actualizado - Lecciones: " + leccionesCompletadas + ", Actividades: " + actividadesCompletadas + ", Evaluación: " + evaluacionAprobada);
            System.out.println("[Unidad4-Update] Botones actualizados según flujo secuencial");
            
            int progresoTotal = calcularProgreso(
                progreso.getLeccionesCompletadas(),
                progreso.getActividadesCompletadas(),
                progreso.isEvaluacionAprobada()
            );
            configurarBarraProgreso(progresoTotal);
        }
    }
    
    /**
     * Configura la barra de progreso con el porcentaje especificado
     * @param porcentaje Porcentaje de progreso (0-100)
     */
    private void configurarBarraProgreso(int porcentaje) {
        if (vista != null && vista.jProgressBarUNIDAD4 != null) {
            vista.jProgressBarUNIDAD4.setValue(porcentaje);
            vista.jProgressBarUNIDAD4.setString(porcentaje + "%");
            
            // Cambiar color según el progreso
            if (porcentaje < 30) {
                vista.jProgressBarUNIDAD4.setForeground(new java.awt.Color(220, 53, 69)); // Rojo
            } else if (porcentaje < 70) {
                vista.jProgressBarUNIDAD4.setForeground(new java.awt.Color(255, 193, 7)); // Amarillo
            } else {
                vista.jProgressBarUNIDAD4.setForeground(new java.awt.Color(40, 167, 69)); // Verde
            }
            
            // Sincronizar con la barra de progreso del panel de unidades
            // Usar el valor EXACTO de la barra, no el parámetro calculado
            if (controladorUnidades != null) {
                int valorExactoBarra = vista.jProgressBarUNIDAD4.getValue();
                controladorUnidades.actualizarProgresoUnidad(4, valorExactoBarra);
                System.out.println("Unidad 4: Sincronizando valor exacto de barra: " + valorExactoBarra + "%");
            }
        }
    }
}
