package Controlador;

import Vista.Estudiante.Dashboard;
import Vista.Vistas_Unidad1.Vista_EvaluacionU1;
import Vista.Vistas_Unidad1.Vista_Actividad1U1;
import Vista.Vistas_Unidad1.Vista_LeccionFONOLOGIA;
import Vista.Vistas_Unidad1.Vista_Actividad2U1;
import Vista.Vistas_Unidad1.Vista_Actividad3U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Vista.Vistas_Unidad1.Vista_LeccionPRONOMBRES;
import Vista.Vistas_Unidad1.Vista_LeccionSALUDOS;
import Vista.*;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import java.sql.Connection;
import java.time.LocalDateTime;
import Vista.Estudiante.Dashboard;
import Vista.Vistas_Unidad1.Vista_Actividad1U1;
import Vista.Vistas_Unidad1.Vista_Actividad2U1;
import Vista.Vistas_Unidad1.Vista_EvaluacionU1;
import Vista.Vistas_Unidad1.Vista_LeccionPRONOMBRES;
import Vista.Vistas_Unidad1.Vista_LeccionSALUDOS;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Controlador.Controlador_Unidades;

public class Controlador_Unidad1 {

    private final Vista_Unidad1 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 1;

    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;  // Cambié de cedula a correo
    private final Controlador_Unidades controladorUnidades;

    // Modificar la validación en el constructor de Controlador_Unidad1:
    public Controlador_Unidad1(Vista_Unidad1 vista, Connection conn, ControladorDashboard controladorDashboard, String correo, Controlador_Unidades controladorUnidades) {
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
        System.out.println("[DEBUG Unidad1] Constructor ejecutado. Inicializando vista y listeners...");

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

        // Nuevo flujo: fonología, actividad 1, saludos, actividad 2, pronombres, actividad 3, evaluación
        vista.jButtonLECCIONFONOLOGIA.setEnabled(progresoTotal >= 0);  // Fonología primero
        vista.jButtonACTIIVIDAD1.setEnabled(progresoTotal >= 15);       // Actividad 1
        vista.jButtonLECCIONSALUDOS.setEnabled(progresoTotal >= 30);    // Saludos
        vista.jButtonACTIVIDAD2.setEnabled(progresoTotal >= 45);        // Actividad 2
        vista.jButtonLECCIONPRONOMBRES.setEnabled(progresoTotal >= 60); // Pronombres
        vista.jButtonAtividad3.setEnabled(progresoTotal >= 75);         // Actividad 3
        vista.jButtonEVALUACION.setEnabled(progresoTotal >= 90);        // Evaluación
        vista.jButtonFINALIZARUNIDAD1.setEnabled(progresoTotal == 100);
    }

    // Reemplazar los listeners en agregarListeners() para manejar null:
    private void agregarListeners() {
        System.out.println("[DEBUG Unidad1] Registrando listeners para los botones de la unidad 1...");
        vista.jButtonLECCIONSALUDOS.addActionListener(e -> abrirLeccionSaludos());
        vista.jButtonLECCIONFONOLOGIA.addActionListener(e -> abrirLeccionFonetica());
        vista.jButtonLECCIONPRONOMBRES.addActionListener(e -> abrirLeccionPronombres());
        vista.jButtonACTIIVIDAD1.addActionListener(e -> abrirActividad1());
        vista.jButtonACTIVIDAD2.addActionListener(e -> abrirActividad2());
        vista.jButtonAtividad3.addActionListener(e -> abrirActividad3());
        vista.jButtonEVALUACION.addActionListener(e -> abrirEvaluacion());
        vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgresoUnidad1());

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
        
        // Nuevo flujo: fonología(15), actividad1(30), saludos(45), actividad2(60), pronombres(75), actividad3(90), evaluación(100)
        if (lecciones >= 1) {  // Fonología completada
            progreso = 15;
        }
        if (actividades >= 1) {  // Actividad 1 completada
            progreso = 30;
        }
        if (lecciones >= 2) {  // Saludos completados
            progreso = 45;
        }
        if (actividades >= 2) {  // Actividad 2 completada
            progreso = 60;
        }
        if (lecciones >= 3) {  // Pronombres completados
            progreso = 75;
        }
        if (actividades >= 3) {  // Actividad 3 completada
            progreso = 90;
        }
        if (evaluacion) {  // Evaluación completada
            progreso = 100;
        }
        
        return progreso;
    }

    private void abrirLeccionSaludos() {
        System.out.println("=== Abriendo lección de saludos ===");
        Vista_LeccionSALUDOS vistaLeccionSaludos = new Vista_LeccionSALUDOS();
        new Controlador_Lecciones(vistaLeccionSaludos, controladorDashboard, conn, correo,
                Controlador_Lecciones.LECCION_SALUDOS);
        controladorDashboard.getVista().mostrarVista(vistaLeccionSaludos);
        System.out.println("=== Lección de saludos abierta ===");
    }

    private void abrirLeccionFonetica() {
        Vista_LeccionFONOLOGIA vistaLeccion = new Vista_LeccionFONOLOGIA();
        new Controlador_Lecciones(vistaLeccion, controladorDashboard, conn, correo, 2);
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
    }

    private void abrirLeccionPronombres() {
        Vista_LeccionPRONOMBRES vistaLeccion = new Vista_LeccionPRONOMBRES();
        new Controlador_Lecciones(vistaLeccion, controladorDashboard, conn, correo, 3);
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
    }

private void abrirActividad1() {
    Vista_Actividad1U1 vistaActividad = new Vista_Actividad1U1();
    Controlador_Actividad_PreguntaRespuesta controlador = new Controlador_Actividad_PreguntaRespuesta(
            vistaActividad,
            conn,
            controladorDashboard,
            this, // 'this' es el Controlador_Unidad1 actual
            correo
    );
    controladorDashboard.getVista().mostrarVista(vistaActividad);
}

    private void abrirActividad2() {
        int idActividad = 2;
        Vista_Actividad2U1 vistaActividad = new Vista_Actividad2U1();
        Controlador_Actividades controladorAct = new Controlador_Actividades(vistaActividad, controladorDashboard, conn, correo, idActividad, this); // <-- PASA "this"
        controladorAct.cargarActividad();
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirActividad3() {
        int idActividad = 3;
        Vista_Actividad3U1 vistaActividad = new Vista_Actividad3U1();
        Controlador_Actv3U1 controladorAct3 = new Controlador_Actv3U1(vistaActividad, controladorDashboard, conn, correo, idActividad, this);
        controladorAct3.cargarActividad();
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirEvaluacion() {
        Vista_EvaluacionU1 vistaEvaluacion = new Vista_EvaluacionU1();
        new Controlador_Evaluaciones(vistaEvaluacion, controladorDashboard, conn, correo, ID_UNIDAD, controladorUnidades);
        controladorDashboard.getVista().mostrarVista(vistaEvaluacion);
    }

  

    private void reiniciarProgresoUnidad1() {
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
