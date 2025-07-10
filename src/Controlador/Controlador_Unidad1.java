package Controlador;

import Vista.*;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import java.sql.Connection;
import java.time.LocalDateTime;

public class Controlador_Unidad1 {

    private final Vista_Unidad1 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 1;

    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;  // Cambié de cedula a correo

    public Controlador_Unidad1(Vista_Unidad1 vista, Connection conn, ControladorDashboard controladorDashboard, String correo) {
        this.vista = vista;
        this.conn = conn;
        this.controladorDashboard = controladorDashboard;
        this.dashboard = controladorDashboard.getVista();
        this.correo = correo;
        this.idUsuario = Usuario.obtenerIdPorCorreo(correo);
        if (correo == null || correo.trim().isEmpty()) {
            System.err.println("ERROR: El correo recibido es nulo o vacío");
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }

        if (idUsuario <= 0) {
            System.err.println("ERROR: No se encontró usuario con correo: " + correo);
            throw new IllegalArgumentException("Usuario no encontrado con el correo: " + correo);
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
        vista.jProgressBarUNIDAD1.setValue(progresoTotal);

        vista.jButtonLECCIONSALUDOS.setEnabled(progresoTotal >= 0);
        vista.jButtonLECCIONFONOLOGIA.setEnabled(progresoTotal >= 15);
        vista.jButtonLECCIONPRONOMBRES.setEnabled(progresoTotal >= 30);
        vista.jButtonACTIIVIDAD1.setEnabled(progresoTotal >= 45);
        vista.jButtonACTIVIDAD2.setEnabled(progresoTotal >= 60);
        vista.jButtonEVALUACION.setEnabled(progresoTotal >= 75);
        vista.jButtonFINALIZARUNIDAD1.setEnabled(progresoTotal == 100);
    }

    private void agregarListeners() {
        vista.jButtonLECCIONSALUDOS.addActionListener(e -> abrirLeccionSaludos());
        vista.jButtonLECCIONFONOLOGIA.addActionListener(e -> abrirLeccionFonetica());
        vista.jButtonLECCIONPRONOMBRES.addActionListener(e -> abrirLeccionPronombres());
        vista.jButtonACTIIVIDAD1.addActionListener(e -> abrirActividad1());
        vista.jButtonACTIVIDAD2.addActionListener(e -> abrirActividad2());
        vista.jButtonEVALUACION.addActionListener(e -> abrirEvaluacion());
        vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgresoUnidad1());
        vista.jButtonBack.addActionListener(e -> {
            controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
        });

        vista.jButtonFINALIZARUNIDAD1.addActionListener(e -> {
            controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
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
        int idActividad = 1;
        Vista_Actividad1U1 vistaActividad = new Vista_Actividad1U1();
        Controlador_Actividades controladorAct = new Controlador_Actividades(vistaActividad, controladorDashboard, conn, correo, idActividad);
        controladorAct.cargarActividad();
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirActividad2() {
        int idActividad = 2;
        Vista_Actividad2U1 vistaActividad = new Vista_Actividad2U1();
        Controlador_Actividades controladorAct = new Controlador_Actividades(vistaActividad, controladorDashboard, conn, correo, idActividad);
        controladorAct.cargarActividad();
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirEvaluacion() {
        Vista_EvaluacionU1 vistaEvaluacion = new Vista_EvaluacionU1();
        new Controlador_Evaluaciones(vistaEvaluacion, controladorDashboard, conn, correo, ID_UNIDAD);
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
}
