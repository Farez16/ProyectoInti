package Controlador;

import Vista.Estudiante.Dashboard;
import VistasUnidad3.Vista_Actividad_Familia;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Objects;
import VistasUnidad3.Vista_Actividad_Vestimenta;
import VistasUnidad3.Vista_EvaluacionU3;
import VistasUnidad3.Vista_Leccion_Familia;
import VistasUnidad3.Vista_Leccion_Vestimenta;
import VistasUnidad3.Vista_Leccion_Vestimenta1;
import VistasUnidad3.Vista_Unidad3;
import javax.swing.JOptionPane;

public class Controlador_Unidad3 {

    private final Vista_Unidad3 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 3;
    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;
    private final Controlador_Unidades controladorUnidades;

    public Controlador_Unidad3(Vista_Unidad3 vista, Connection conn,
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
        vista.jProgressBarUNIDAD3.setValue(progresoTotal);
        
        // Sincronizar con la barra de progreso del panel de unidades
        if (controladorUnidades != null) {
            controladorUnidades.actualizarProgresoUnidad(3, progresoTotal);
        }

        // Habilitar botones según progreso
        vista.jButtonFamilia.setEnabled(true); // Siempre disponible
        vista.jButtonActFamilia.setEnabled(progreso.getLeccionesCompletadas() >= 1);
        vista.jButtonVestimenta.setEnabled(progreso.getActividadesCompletadas() >= 1);
        vista.jButtonVestimenta1.setEnabled(progreso.getLeccionesCompletadas() >= 2);
        vista.jButtonActVestimenta.setEnabled(progreso.getLeccionesCompletadas() >= 3);
        vista.jButtonEvaluacion.setEnabled(progreso.getActividadesCompletadas() >= 2);
        vista.jButtonFINALIZARUNIDAD1.setEnabled(progreso.isEvaluacionAprobada());
    }

    private void agregarListeners() {
        vista.jButtonFamilia.addActionListener(e -> abrirLeccionFamilia());
        vista.jButtonActFamilia.addActionListener(e -> abrirActividadFamilia());
        vista.jButtonVestimenta.addActionListener(e -> abrirLeccionVestimenta());
        vista.jButtonVestimenta1.addActionListener(e -> abrirLeccionVestimenta1());
        vista.jButtonActVestimenta.addActionListener(e -> abrirActividadVestimenta());
        vista.jButtonEvaluacion.addActionListener(e -> abrirEvaluacion());
        vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgresoUnidad3());

        vista.jButtonBack.addActionListener(e -> {
            dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
            
        });

        vista.jButtonFINALIZARUNIDAD1.addActionListener(e -> {
            dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
        });
    }

    private int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
        // Usar la clase centralizada CalculadorProgreso para consistencia
        return CalculadorProgreso.calcularProgreso(lecciones, actividades, evaluacion);
    }

    private void abrirLeccionFamilia() {
        Vista_Leccion_Familia vistaLeccionFamilia = new Vista_Leccion_Familia();
        new ControladorLeccionFamiliaU3(
                vistaLeccionFamilia,
                dashboard, // Tu instancia de Dashboard
                conn, // Conexión a la base de datos
                controladorDashboard, // Instancia de ControladorDashboard
                correo // Correo del usuario actual
        );
        dashboard.mostrarVista(vistaLeccionFamilia);
    }

    private void abrirActividadFamilia() {
        Vista_Actividad_Familia vistaActividad = new Vista_Actividad_Familia();
        new ControladorActividadFamiliaU3(
                vistaActividad,
                dashboard,
                conn,
                controladorDashboard,
                correo
        );
        dashboard.mostrarVista(vistaActividad);
    }

    private void abrirLeccionVestimenta() {
        Vista_Leccion_Vestimenta vistaLeccion = new Vista_Leccion_Vestimenta();
        new ControladorLeccionVestimentaU3(
                vistaLeccion,
                dashboard,
                conn,
                controladorDashboard,
                correo
        );
        dashboard.mostrarVista(vistaLeccion);
    }

    private void abrirLeccionVestimenta1() {
        Vista_Leccion_Vestimenta1 vistaLeccion = new Vista_Leccion_Vestimenta1();
        new ControladorLeccionVestimenta1U3(
                vistaLeccion,
                dashboard,
                conn,
                controladorDashboard,
                correo
        );
        dashboard.mostrarVista(vistaLeccion);
    }

    private void abrirActividadVestimenta() {
        Vista_Actividad_Vestimenta vistaActividad = new Vista_Actividad_Vestimenta();
        new ControladorActividadVestimentaU3(
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
            System.out.println("[DEBUG] Iniciando apertura de evaluación Unidad 3");
            
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
            System.out.println("[DEBUG] Creando vista de evaluación U3");
            Vista_EvaluacionU3 vistaEvaluacion = new Vista_EvaluacionU3();
            
            if (vistaEvaluacion == null) {
                System.err.println("[ERROR] No se pudo crear la vista de evaluación");
                JOptionPane.showMessageDialog(vista, 
                    "Error al cargar la evaluación. Intente nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Crear el controlador de evaluación
            System.out.println("[DEBUG] Creando controlador de evaluación U3");
            Controlador_EvaluacionU3 controladorEvaluacion = new Controlador_EvaluacionU3(
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
                System.out.println("[DEBUG] Evaluación U3 abierta exitosamente");
            } else {
                System.err.println("[ERROR] Dashboard es null, no se puede mostrar la vista");
                JOptionPane.showMessageDialog(vista, 
                    "Error en la navegación. Intente nuevamente.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Excepción al abrir evaluación U3: " + e.getMessage());
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

    private void reiniciarProgresoUnidad3() {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas reiniciar tu progreso en la Unidad 3?\nEsta acción no se puede deshacer.",
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
            // Habilitar botones según progreso
            vista.jButtonFamilia.setEnabled(true); // Siempre disponible
            vista.jButtonActFamilia.setEnabled(progreso.getLeccionesCompletadas() >= 1);
            vista.jButtonVestimenta.setEnabled(progreso.getActividadesCompletadas() >= 1);
            vista.jButtonVestimenta1.setEnabled(progreso.getLeccionesCompletadas() >= 2);
            vista.jButtonActVestimenta.setEnabled(progreso.getLeccionesCompletadas() >= 3);
            vista.jButtonEvaluacion.setEnabled(progreso.getActividadesCompletadas() >= 2);
            vista.jButtonFINALIZARUNIDAD1.setEnabled(progreso.isEvaluacionAprobada());
            
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
        if (vista != null && vista.jProgressBarUNIDAD3 != null) {
            vista.jProgressBarUNIDAD3.setValue(porcentaje);
            vista.jProgressBarUNIDAD3.setString(porcentaje + "%");
            
            // Cambiar color según el progreso
            if (porcentaje < 30) {
                vista.jProgressBarUNIDAD3.setForeground(new java.awt.Color(220, 53, 69)); // Rojo
            } else if (porcentaje < 70) {
                vista.jProgressBarUNIDAD3.setForeground(new java.awt.Color(255, 193, 7)); // Amarillo
            } else {
                vista.jProgressBarUNIDAD3.setForeground(new java.awt.Color(40, 167, 69)); // Verde
            }
            
            // Sincronizar con la barra de progreso del panel de unidades
            // Usar el valor EXACTO de la barra, no el parámetro calculado
            if (controladorUnidades != null) {
                int valorExactoBarra = vista.jProgressBarUNIDAD3.getValue();
                controladorUnidades.actualizarProgresoUnidad(3, valorExactoBarra);
                System.out.println("Unidad 3: Sincronizando valor exacto de barra: " + valorExactoBarra + "%");
            }
        }
    }
}
