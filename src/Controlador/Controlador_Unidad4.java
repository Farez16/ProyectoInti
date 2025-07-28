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
        vista.jProgressBarUNIDAD1.setValue(progresoTotal);

        // Habilitar botones según progreso
        vista.jButtonFamilia.setEnabled(true); // Siempre disponible
        vista.jButtonActFamilia.setEnabled(progreso.getLeccionesCompletadas() >= 1);
        vista.jButtonVestimenta.setEnabled(progreso.getActividadesCompletadas() >= 1);
        vista.jButtonActVestimenta.setEnabled(progreso.getLeccionesCompletadas() >= 3);
        vista.jButtonEvaluacion.setEnabled(progreso.getActividadesCompletadas() >= 2);
        vista.jButtonFINALIZARUNIDAD1.setEnabled(progreso.isEvaluacionAprobada());
    }

    private void agregarListeners() {
        vista.jButtonFamilia.addActionListener(e -> abrirLeccionFamilia());
        vista.jButtonActFamilia.addActionListener(e -> abrirActividadFamilia());
        vista.jButtonVestimenta.addActionListener(e -> abrirLeccionVestimenta());
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
        if (evaluacion) {
            return 100;
        }
        if (actividades >= 2) {
            return 80;
        }
        if (actividades >= 1) {
            return 60;
        }
        if (lecciones >= 1) {
            return 40;
        }
        return 0;
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
        Vista_EvaluacionU3 vistaEvaluacion = new Vista_EvaluacionU3();
        new Controlador_EvaluacionU3(vistaEvaluacion, controladorDashboard, conn, correo, controladorUnidades);
        dashboard.mostrarVista(vistaEvaluacion);
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

    public void actualizarVista() {
        inicializarVista();
    }
}
