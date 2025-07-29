package ControladoresU2;

import Controlador.Controlador_EvaluacionU2;
import Controlador.Controlador_Unidades;
import Controlador.ControladorDashboard;
import Modelo.Modelo_AnimacionEntradaUnidad2;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vista_EvaluacionU2;
import Vista.Estudiante.Vista_PanelUnidades;
import Vista.Estudiante.Dashboard;
import Vista.Vista_ActividadMorfemasUnidad2;
import Vista.Vista_InicioNumerosUnidad2;
import Vista.Vista_InicioOperacionesUnidad2;
import Vista.Vista_NumerosActividadUnidad2;
import Vista.Vista_Unidad2;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;

public class ControladorUnidad2 {

    private final Vista_Unidad2 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 2;
    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;
    private final Controlador_Unidades controladorUnidades;

    public ControladorUnidad2(Vista_Unidad2 vista, Connection conn,
            ControladorDashboard controladorDashboard,
            String correo, Controlador_Unidades controladorUnidades) {
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser null");
        this.conn = Objects.requireNonNull(conn, "La conexión no puede ser null");
        this.controladorDashboard = Objects.requireNonNull(controladorDashboard, "El controladorDashboard no puede ser null");
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
        configurarBarraProgreso(progresoTotal);

        vista.getjButtonNumeros().setEnabled(true); // Siempre disponible
        vista.getjButtonOperaciones().setEnabled(progreso.getLeccionesCompletadas() >= 1);
        vista.getjButtonMorfemas().setEnabled(progreso.getActividadesCompletadas() >= 2 && progreso.getLeccionesCompletadas() >= 2);
        vista.getjButtonAnimales().setEnabled(progreso.getLeccionesCompletadas() >= 2 && progreso.getActividadesCompletadas() >= 1);
        vista.getjButtonEvaluacion().setEnabled(progreso.getActividadesCompletadas() >= 2);
        vista.getjButtonFINALIZARUNIDAD1().setEnabled(progreso.isEvaluacionAprobada());
    }

    private void agregarListeners() {
        vista.getjButtonNumeros().addActionListener(e -> abrirLeccionNumeros());
        vista.getjButtonOperaciones().addActionListener(e -> abrirLeccionOperaciones());
        vista.getjButtonMorfemas().addActionListener(e -> abrirActividadMorfemas());
        vista.getjButtonAnimales().addActionListener(e -> abrirActividadAnimales());
        vista.getjButtonEvaluacion().addActionListener(e -> abrirEvaluacion());
        vista.getjButtonREINICIARU1().addActionListener(e -> reiniciarProgresoUnidad2());

        vista.getjButtonBack().addActionListener(e -> {
            dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
        });

        vista.getjButtonFINALIZARUNIDAD1().addActionListener(e -> finalizarUnidad());
    }

    private int calcularProgreso(int lecciones, int actividades, boolean evaluacion) {
        int progreso = 0;
        progreso += Math.min(lecciones * 20, 40); // Máximo 40% por lecciones
        progreso += Math.min(actividades * 20, 40); // Máximo 40% por actividades
        if (evaluacion) {
            progreso += 20; // Evaluación
        }
        return Math.min(progreso, 100);
    }

    private void abrirLeccionNumeros() {
        Vista_InicioNumerosUnidad2 vistaLeccion = new Vista_InicioNumerosUnidad2();
        new Controlador_InicioNumerosU2(
                vistaLeccion,
                dashboard,
                this,
                controladorUnidades,
                correo,
                conn,
                controladorDashboard
        );
        dashboard.mostrarVista(vistaLeccion);
    }

    private void abrirLeccionOperaciones() {
        Vista_InicioOperacionesUnidad2 vistaLeccion = new Vista_InicioOperacionesUnidad2();
        new Controlador_InicioOperacionesU2(
                vistaLeccion,
                dashboard,
                this,
                conn,
                controladorDashboard,
                controladorUnidades
        );
        Modelo_AnimacionEntradaUnidad2 anim = new Modelo_AnimacionEntradaUnidad2(vistaLeccion);
        anim.mostrarConAnimacion();
        dashboard.mostrarVista(vistaLeccion);
    }

    private void abrirActividadMorfemas() {
        Vista_ActividadMorfemasUnidad2 vistaActividad = new Vista_ActividadMorfemasUnidad2();
        new Controlador_ActividadMorfemasU2(
                vistaActividad,
                dashboard,
                conn,
                controladorDashboard,
                controladorUnidades
        );
        dashboard.mostrarVista(vistaActividad);
    }

    private void abrirActividadAnimales() {
        new Controlador_ActividadAnimalesdelaSierraUnidad2(
                dashboard,
                true,
                conn,
                controladorDashboard,
                controladorUnidades
        );
    }

    private void abrirEvaluacion() {
        Vista_EvaluacionU2 vistaEvaluacion = new Vista_EvaluacionU2();
        new Controlador_EvaluacionU2(
                vistaEvaluacion,
                dashboard
        );
        dashboard.mostrarVista(vistaEvaluacion);
    }

    private void reiniciarProgresoUnidad2() {
        int confirmacion = JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas reiniciar tu progreso en la Unidad 2?\nEsta acción no se puede deshacer.",
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

    private void finalizarUnidad() {
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        if (progreso != null && progreso.isEvaluacionAprobada()) {
            JOptionPane.showMessageDialog(vista, "🎉 ¡Felicidades! Completaste la Unidad 2");
            if (controladorUnidades != null) {
                controladorUnidades.marcarUnidadComoCompletada(ID_UNIDAD);
            }
            dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
        } else {
            JOptionPane.showMessageDialog(vista, "⚠️ Debes aprobar la evaluación para finalizar la unidad");
        }
    }

    public void marcarLeccionComoCompletada() throws SQLException {
        Modelo_Progreso_Usuario.incrementarLecciones(idUsuario, ID_UNIDAD);
        actualizarVista();
    }

    public void marcarActividadComoCompletada() throws SQLException {
        Modelo_Progreso_Usuario.incrementarActividades(idUsuario, ID_UNIDAD);
        actualizarVista();
    }

    public void marcarEvaluacionComoAprobada(int calificacion) throws SQLException {
        Modelo_Progreso_Usuario.marcarEvaluacionAprobada(idUsuario, ID_UNIDAD, calificacion);
        actualizarVista();
    }

    public void actualizarVista() {
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        if (progreso != null) {
            vista.getjButtonNumeros().setEnabled(true);
            vista.getjButtonOperaciones().setEnabled(progreso.getLeccionesCompletadas() >= 1);
            vista.getjButtonMorfemas().setEnabled(progreso.getActividadesCompletadas() >= 1);
            vista.getjButtonAnimales().setEnabled(progreso.getLeccionesCompletadas() >= 2);
            vista.getjButtonEvaluacion().setEnabled(progreso.getActividadesCompletadas() >= 2);
            vista.getjButtonFINALIZARUNIDAD1().setEnabled(progreso.isEvaluacionAprobada());

            int progresoTotal = calcularProgreso(
                progreso.getLeccionesCompletadas(),
                progreso.getActividadesCompletadas(),
                progreso.isEvaluacionAprobada()
            );
            configurarBarraProgreso(progresoTotal);
        }
    }

    private void configurarBarraProgreso(int porcentaje) {
        if (vista != null) {
            vista.jProgressBarUNIDAD2.setValue(porcentaje);
            vista.jProgressBarUNIDAD2.setString(porcentaje + "%");
            vista.jProgressBarUNIDAD2.setStringPainted(true);

            Color color;
            if (porcentaje < 30) {
                color = new Color(220, 53, 69); // Rojo
            } else if (porcentaje < 70) {
                color = new Color(255, 193, 7); // Amarillo
            } else {
                color = new Color(40, 167, 69); // Verde
            }
            vista.jProgressBarUNIDAD2.setForeground(color);

            if (controladorUnidades != null) {
                controladorUnidades.actualizarProgresoUnidad(ID_UNIDAD, porcentaje);
                if (porcentaje >= 100) {
                    controladorUnidades.marcarUnidadComoCompletada(ID_UNIDAD);
                }
            }
        }
    }

    public void actualizarBarraProgresoUnidad(int porcentaje) {
        configurarBarraProgreso(porcentaje);
    }

    public Vista_Unidad2 getVista() {
        return this.vista;
    }
}

