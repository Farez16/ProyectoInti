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

        // Habilitar botones según progreso
        vista.jButtonFamilia.setEnabled(true); // Siempre disponible
        vista.jButtonActFamilia.setEnabled(progreso.getLeccionesCompletadas() >= 1);
        vista.jButtonVestimenta.setEnabled(progreso.getActividadesCompletadas() >= 1);
        vista.jButtonActVestimenta.setEnabled(progreso.getLeccionesCompletadas() >= 2 && progreso.getActividadesCompletadas() >= 1);
        vista.jButtonEvaluacion.setEnabled(progreso.getActividadesCompletadas() >= 2 && progreso.getLeccionesCompletadas() >=2);
        vista.jButtonFINALIZARUNIDAD1.setEnabled(progreso.isEvaluacionAprobada());
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
        // Usar la clase centralizada CalculadorProgreso para consistencia
        return CalculadorProgreso.calcularProgreso(lecciones, actividades, evaluacion);
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
        Vista_EvaluacionU4 vistaEvaluacion = new Vista_EvaluacionU4();
        new Controlador_EvaluacionU4(vistaEvaluacion, controladorDashboard, conn, correo, controladorUnidades);
        dashboard.mostrarVista(vistaEvaluacion);
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
            // Habilitar botones según progreso
            vista.jButtonFamilia.setEnabled(true); // Siempre disponible
            vista.jButtonActFamilia.setEnabled(progreso.getLeccionesCompletadas() >= 1);
            vista.jButtonVestimenta.setEnabled(progreso.getActividadesCompletadas() >= 1);
            vista.jButtonActVestimenta.setEnabled(progreso.getLeccionesCompletadas() >= 1);
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
