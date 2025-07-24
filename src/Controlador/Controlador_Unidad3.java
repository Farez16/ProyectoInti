package Controlador;

import Vista.Estudiante.Dashboard;
import VistasUnidad3.Vista_Actividad_Familia;
import Vista.*;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import java.sql.Connection;
import java.time.LocalDateTime;
import Controlador.Controlador_Unidades;
import VistasUnidad3.Vista_Actividad_Vestimenta;
import VistasUnidad3.Vista_EvaluacionU3;
import VistasUnidad3.Vista_Leccion_Familia;
import VistasUnidad3.Vista_Leccion_Vestimenta;
import VistasUnidad3.Vista_Leccion_Vestimenta1;
import VistasUnidad3.Vista_Unidad3;

public class Controlador_Unidad3 {

    private final Vista_Unidad3 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 3;

    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;  // Cambié de cedula a correo
    private final Controlador_Unidades controladorUnidades;

    // Modificar la validación en el constructor de Controlador_Unidad1:
    public Controlador_Unidad3(Vista_Unidad3 vista, Connection conn, ControladorDashboard controladorDashboard, String correo, Controlador_Unidades controladorUnidades) {
        // Validaciones más detalladas
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        if (controladorDashboard == null) {
            throw new IllegalArgumentException("El controladorDashboard no puede ser null");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser null o vacío");
        }

        // CAMBIO: Permitir null temporalmente pero advertir
        if (controladorUnidades == null) {
            System.err.println("ADVERTENCIA: El controladorUnidades es null");
            System.err.println("Esto puede causar problemas de navegación al usar los botones Back y Finalizar");
            System.err.println("Stack trace para debug:");
            Thread.dumpStack();
        }

        System.out.println("=== DEBUG: Todos los parámetros del constructor son válidos ===");
        System.out.println("[DEBUG Unidad3] Constructor ejecutado. Inicializando vista y listeners...");

        this.vista = vista;
        this.conn = conn;
        this.controladorDashboard = controladorDashboard;
        this.correo = correo;
        this.controladorUnidades = controladorUnidades; // Puede ser null temporalmente
        this.dashboard = controladorDashboard.getVista();
        this.idUsuario = Usuario.obtenerIdPorCorreo(correo);
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
        vista.jProgressBarUNIDAD1.setValue(progresoTotal);

        vista.jButtonFamilia.setEnabled(progresoTotal >= 0);
        vista.jButtonActFamilia.setEnabled(progresoTotal >= 25);
        vista.jButtonVestimenta.setEnabled(progresoTotal >= 50);
        vista.jButtonActVestimenta.setEnabled(progresoTotal >= 70);
        vista.jButtonEvaluacion.setEnabled(progresoTotal >= 90);
        vista.jButtonFINALIZARUNIDAD1.setEnabled(progresoTotal == 100);
    }

    // Reemplazar los listeners en agregarListeners() para manejar null:
    private void agregarListeners() {
        System.out.println("[DEBUG Unidad1] Registrando listeners para los botones de la unidad 1...");
        vista.jButtonFamilia.addActionListener(e -> abrirLeccionFamilia());
        vista.jButtonActFamilia.addActionListener(e -> abrirActividadFamilia());
        vista.jButtonVestimenta.addActionListener(e -> abrirLeccionVestimenta());
        vista.jButtonVestimenta1.addActionListener(e -> abrirLeccionVestimenta1());
        vista.jButtonActVestimenta.addActionListener(e -> abrirActividadVestimenta());
        //vista.jButtonEvaluacion.addActionListener(e -> abrirEvaluacion());
        vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgresoUnidad3());
        vista.jButtonHistorial.addActionListener(e -> abrirHistorialEvaluaciones());

        vista.jButtonBack.addActionListener(e -> {
            if (controladorUnidades != null) {
                controladorUnidades.actualizarVista();
                controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
            } else {
                System.err.println("ERROR: controladorUnidades es null en jButtonBack");
                System.err.println("Navegando directamente al panel de unidades...");
                // Navegación de respaldo
                controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
            }
        });

        vista.jButtonFINALIZARUNIDAD1.addActionListener(e -> {
            if (controladorUnidades != null) {
                controladorUnidades.actualizarVista();
                controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
            } else {
                System.err.println("ERROR: controladorUnidades es null en jButtonFINALIZARUNIDAD1");
                System.err.println("Navegando directamente al panel de unidades...");
                // Navegación de respaldo
                controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
            }
        });
    }

    private int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
        int progreso = 0;
        if (lecciones >= 1) {
            progreso = 15;
        }
        if (lecciones >= 2) {
            progreso = 30;
        }
        if (lecciones >= 3) {
            progreso = 45;
        }
        if (actividades >= 1) {
            progreso = 60;
        }
        if (actividades >= 2) {
            progreso = 75;
        }
        if (evaluacion) {
            progreso = 100;
        }
        return progreso;
    }

    private void abrirLeccionFamilia() {
        System.out.println("=== Abriendo lección de saludos ===");
        Vista_Leccion_Familia vistaLeccionFamilia = new Vista_Leccion_Familia();
        dashboard.mostrarVista(vistaLeccionFamilia);
        System.out.println("=== Lección de saludos abierta ===");
    }

    private void abrirActividadFamilia() {
        Vista_Actividad_Familia vistaActividad = new Vista_Actividad_Familia();

        dashboard.mostrarVista(vistaActividad);
    }

    private void abrirLeccionVestimenta() {
        Vista_Leccion_Vestimenta vistaLeccion = new Vista_Leccion_Vestimenta();
        new Controlador_Lecciones(vistaLeccion, controladorDashboard, conn, correo, 3);
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
    }

    private void abrirLeccionVestimenta1() {
        Vista_Leccion_Vestimenta1 vistaLeccion = new Vista_Leccion_Vestimenta1();
        new Controlador_Lecciones(vistaLeccion, controladorDashboard, conn, correo, 3);
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
    }

    private void abrirActividadVestimenta() {
        Vista_Actividad_Vestimenta vistaActividad = new Vista_Actividad_Vestimenta();
        new Controlador_Lecciones(vistaActividad, controladorDashboard, conn, correo, 2);
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

//    private void abrirEvaluacion() {
//        Vista_EvaluacionU3 vistaEvaluacion = new Vista_EvaluacionU3();
//        new Controlador_Evaluaciones(vistaEvaluacion, controladorDashboard, conn, correo, ID_UNIDAD, controladorUnidades);
//        controladorDashboard.getVista().mostrarVista(vistaEvaluacion);
//    }
    private void abrirHistorialEvaluaciones() {
        Vista_Historial_Evaluaciones vistaHistorial = new Vista_Historial_Evaluaciones();
        Controlador_HistorialEvaluaciones controladorHistorial = new Controlador_HistorialEvaluaciones(vistaHistorial, conn, correo);
        controladorDashboard.getVista().mostrarVista(vistaHistorial);
    }

    private void reiniciarProgresoUnidad3() {
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas reiniciar tu progreso en la Unidad 1?\nEsta acción no se puede deshacer.",
                "Confirmar reinicio",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
            if (progreso != null) {
                progreso.setLeccionesCompletadas(0);
                progreso.setActividadesCompletadas(0);
                progreso.setEvaluacionAprobada(false);
                progreso.setCalificacion(0);
                progreso.setFechaActualizacion(LocalDateTime.now());
                Modelo_Progreso_Usuario.actualizarProgreso(progreso);
                inicializarVista();
                javax.swing.JOptionPane.showMessageDialog(vista, "Progreso reiniciado correctamente.");
            }
        }
    }

    /**
     * Getter para obtener el controlador de unidades
     *
     * @return Controlador_Unidades
     */
    public Controlador_Unidades getControladorUnidades() {
        return controladorUnidades;
    }

    /**
     * Actualiza la vista de la unidad1 para reflejar cambios de progreso
     */
    public void actualizarVista() {
        inicializarVista();
        // También actualizar el controlador de unidades si es necesario
        if (controladorUnidades != null) {
            controladorUnidades.actualizarVista();
        }
    }
}
