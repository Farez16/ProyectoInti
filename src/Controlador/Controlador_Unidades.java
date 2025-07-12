package Controlador;

import Vista.*;
import Modelo.Usuario;
import Modelo.Modelo_Unidades;
import Conexion.Conexion;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import Vista.Dashboard;
import java.sql.Connection;
import Conexion.Conexion;

/**
 * Controlador para gestionar la vista del panel de unidades Maneja la
 * interacción entre la vista y el modelo de datos
 */
public class Controlador_Unidades {

    private final Vista_PanelUnidades vista;
    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;
    private boolean[] unidadesDisponibles;

    /**
     * Constructor del controlador
     *
     * @param vista Vista del panel de unidades
     * @param dashboard Vista del dashboard principal
     * @param controladorDashboard Controlador del dashboard
     * @param correo Correo del usuario
     * @param cedula Cédula del usuario
     */
    public Controlador_Unidades(Vista_PanelUnidades vista, Dashboard dashboard,
            ControladorDashboard controladorDashboard, String correo) {
        this.vista = vista;
        this.dashboard = dashboard;
        this.controladorDashboard = controladorDashboard;
        this.correo = correo;
        this.unidadesDisponibles = new boolean[5]; // índice 0 no usado, 1-4 para unidades

        inicializar();
    }

    /**
     * Inicializa el controlador
     */
    private void inicializar() {
        cargarUnidades();
        agregarEventos();
    }

    /**
     * Carga las unidades y actualiza la vista
     */
    private void cargarUnidades() {
        try {
            // Obtener disponibilidad de unidades
            unidadesDisponibles = Modelo_Unidades.obtenerDisponibilidadUnidades(correo);

            // Obtener unidades con progreso
            List<Modelo_Unidades> unidades = Modelo_Unidades.obtenerUnidadesConProgreso(correo);

            // Actualizar vista de cada unidad
            for (Modelo_Unidades unidad : unidades) {
                actualizarVistaUnidad(unidad);
            }

            // Actualizar progreso general
            int progresoGeneral = Modelo_Unidades.obtenerProgresoGeneral(correo);
            actualizarProgresoGeneral(progresoGeneral);

        } catch (Exception e) {
            System.err.println("Error al cargar unidades: " + e.getMessage());
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar las unidades. Por favor, intenta de nuevo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualiza la vista de una unidad específica
     *
     * @param unidad Modelo de la unidad
     */
    private void actualizarVistaUnidad(Modelo_Unidades unidad) {
        int idUnidad = unidad.getIdUnidad();
        boolean disponible = unidadesDisponibles[idUnidad];

        JLabel labelUnidad = obtenerLabelUnidad(idUnidad);
        JLabel labelEstado = obtenerLabelEstado(idUnidad);
        JLabel labelProgreso = obtenerLabelProgreso(idUnidad);

        if (labelUnidad != null && labelEstado != null) {
            // Configurar disponibilidad visual
            unidad.setDisponible(disponible);

            // Actualizar color y estado
            String colorEstado = unidad.getColorEstado();
            String estadoTexto = unidad.getEstadoTexto();

            labelUnidad.setForeground(Color.decode(colorEstado));
            labelEstado.setText(estadoTexto);
            labelEstado.setForeground(Color.decode(colorEstado));

            // Actualizar progreso si existe el label
            if (labelProgreso != null) {
                int progreso = unidad.calcularProgresoTotal(getTotalLecciones(idUnidad), getTotalActividades(idUnidad));
                labelProgreso.setText(progreso + "%");
                labelProgreso.setForeground(Color.decode(colorEstado));
            }

            // Configurar cursor y opacidad
            if (disponible) {
                labelUnidad.setCursor(new Cursor(Cursor.HAND_CURSOR));
                labelUnidad.setEnabled(true);
            } else {
                labelUnidad.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                labelUnidad.setEnabled(false);
            }
        }
    }

    /**
     * Obtiene el label de una unidad específica
     *
     * @param idUnidad ID de la unidad
     * @return JLabel correspondiente
     */
    private JLabel obtenerLabelUnidad(int idUnidad) {
        return switch (idUnidad) {
            case 1 ->
                vista.jLabelUNIDAD1;
            case 2 ->
                vista.jLabelUNIDAD2;
            case 3 ->
                vista.jLabelUNIDAD3;
            case 4 ->
                vista.jLabelUNIDAD4;
            default ->
                null;
        };
    }

    /**
     * Obtiene el label de estado de una unidad específica
     *
     * @param idUnidad ID de la unidad
     * @return JLabel correspondiente al estado
     */
    private JLabel obtenerLabelEstado(int idUnidad) {
        return switch (idUnidad) {
            case 1 ->
                vista.jLabelEstadoU1; // Asumiendo que existen estos labels
            case 2 ->
                vista.jLabelEstadoU2;
            case 3 ->
                vista.jLabelEstadoU3;
            case 4 ->
                vista.jLabelEstadoU4;
            default ->
                null;
        };
    }

    /**
     * Obtiene el label de progreso de una unidad específica
     *
     * @param idUnidad ID de la unidad
     * @return JLabel correspondiente al progreso
     */
    private JLabel obtenerLabelProgreso(int idUnidad) {
        return switch (idUnidad) {
            case 1 ->
                vista.jLabelProgresoU1; // Asumiendo que existen estos labels
            case 2 ->
                vista.jLabelProgresoU2;
            case 3 ->
                vista.jLabelProgresoU3;
            case 4 ->
                vista.jLabelProgresoU4;
            default ->
                null;
        };
    }

    /**
     * Obtiene el total de lecciones por unidad
     *
     * @param idUnidad ID de la unidad
     * @return Total de lecciones
     */
    private int getTotalLecciones(int idUnidad) {
        return switch (idUnidad) {
            case 1 ->
                5; // Ajustar según tu estructura
            case 2 ->
                5;
            case 3 ->
                5;
            case 4 ->
                5;
            default ->
                0;
        };
    }

    /**
     * Obtiene el total de actividades por unidad
     *
     * @param idUnidad ID de la unidad
     * @return Total de actividades
     */
    private int getTotalActividades(int idUnidad) {
        return switch (idUnidad) {
            case 1 ->
                3; // Ajustar según tu estructura
            case 2 ->
                3;
            case 3 ->
                3;
            case 4 ->
                3;
            default ->
                0;
        };
    }

    /**
     * Actualiza el progreso general en la vista
     *
     * @param progreso Porcentaje de progreso (0-100)
     */
    private void actualizarProgresoGeneral(int progreso) {
        if (vista.jLabelProgresoGeneral != null) {
            vista.jLabelProgresoGeneral.setText("Progreso General: " + progreso + "%");
        }
        if (vista.jProgressBarGeneral != null) {
            vista.jProgressBarGeneral.setValue(progreso);
        }
    }

    /**
     * Agrega los eventos de mouse a las unidades
     */
    private void agregarEventos() {
        // Configurar evento para Unidad 1
        vista.jLabelUNIDAD1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (unidadesDisponibles[1]) {
                    vista.jLabelUNIDAD1.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                vista.jLabelUNIDAD1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                abrirUnidad(1, "Saludos y Presentaciones");
            }
        });

        // Configurar evento para Unidad 2
        vista.jLabelUNIDAD2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (unidadesDisponibles[2]) {
                    vista.jLabelUNIDAD2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                vista.jLabelUNIDAD2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                abrirUnidad(2, "Familia y Hogar");
            }
        });

        // Configurar evento para Unidad 3
        vista.jLabelUNIDAD3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (unidadesDisponibles[3]) {
                    vista.jLabelUNIDAD3.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                vista.jLabelUNIDAD3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                abrirUnidad(3, "Naturaleza y Animales");
            }
        });

        // Configurar evento para Unidad 4
        vista.jLabelUNIDAD4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (unidadesDisponibles[4]) {
                    vista.jLabelUNIDAD4.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                vista.jLabelUNIDAD4.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                abrirUnidad(4, "Números y Colores");
            }
        });
    }

// En Controlador_Unidades - Método abrirUnidad, corregir el case 1:
    /**
     * Abre una unidad específica
     *
     * @param idUnidad El ID de la unidad a abrir
     * @param nombreUnidad El nombre de la unidad
     */
    // En Controlador_Unidades - Método abrirUnidad
private void abrirUnidad(int idUnidad, String nombreUnidad) {
    if (!unidadesDisponibles[idUnidad]) {
        JOptionPane.showMessageDialog(vista, 
            "Esta unidad no está disponible aún.\nCompleta la unidad anterior para desbloquearla.", 
            "Unidad no disponible", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        // Registrar visualización de la unidad
        Modelo_Unidades.registrarVisualizacionUnidad(idUnidad, correo);
        
        // Crear vista según la unidad
        switch (idUnidad) {
            case 1 -> {
                Vista_Unidad1 unidad1 = new Vista_Unidad1();
                // USAR LA CONEXIÓN DEL DASHBOARD EN LUGAR DE CREAR UNA NUEVA
                new Controlador_Unidad1(unidad1, (Connection) controladorDashboard.getConnection(), controladorDashboard, correo);
                dashboard.mostrarVista(unidad1);
            }
            case 2 -> {
                JOptionPane.showMessageDialog(vista, 
                    "Unidad 2: " + nombreUnidad + " en desarrollo", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            case 3 -> {
                JOptionPane.showMessageDialog(vista, 
                    "Unidad 3: " + nombreUnidad + " en desarrollo", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
            case 4 -> {
                JOptionPane.showMessageDialog(vista, 
                    "Unidad 4: " + nombreUnidad + " en desarrollo", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        
    } catch (Exception e) {
        System.err.println("Error al abrir unidad: " + e.getMessage());
        JOptionPane.showMessageDialog(vista, 
            "Error al abrir la unidad. Por favor, intenta de nuevo.", 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * Verifica si una unidad está disponible para el usuario
     *
     * @param idUnidad El ID de la unidad a verificar
     * @return true si está disponible, false caso contrario
     */
    private boolean verificarDisponibilidadUnidad(int idUnidad) {
        return Modelo_Unidades.verificarDisponibilidadUnidad(idUnidad, correo);
    }

    /**
     * Actualiza el progreso de visualización de una unidad
     *
     * @param idUnidad El ID de la unidad visualizada
     */
    private void actualizarProgresoVisualizacion(int idUnidad) {
        Modelo_Unidades.registrarVisualizacionUnidad(idUnidad, correo);
    }

    /**
     * Actualiza la vista del panel de unidades
     */
    public void actualizarVista() {
        cargarUnidades();
    }

    /**
     * Obtiene el progreso general del usuario
     *
     * @return Porcentaje de progreso (0-100)
     */
    public int obtenerProgresoGeneral() {
        return Modelo_Unidades.obtenerProgresoGeneral(correo);
    }

    /**
     * Actualiza el progreso de lecciones para una unidad
     *
     * @param idUnidad ID de la unidad
     * @param leccionesCompletadas Número de lecciones completadas
     */
    public void actualizarProgresoLecciones(int idUnidad, int leccionesCompletadas) {
        if (Modelo_Unidades.actualizarProgresoLecciones(idUnidad, correo, leccionesCompletadas)) {
            actualizarVista(); // Refrescar la vista
        }
    }

    /**
     * Actualiza el progreso de actividades para una unidad
     *
     * @param idUnidad ID de la unidad
     * @param actividadesCompletadas Número de actividades completadas
     */
    public void actualizarProgresoActividades(int idUnidad, int actividadesCompletadas) {
        if (Modelo_Unidades.actualizarProgresoActividades(idUnidad, correo, actividadesCompletadas)) {
            actualizarVista(); // Refrescar la vista
        }
    }

    /**
     * Registra la aprobación de una evaluación
     *
     * @param idUnidad ID de la unidad
     * @param calificacion Calificación obtenida
     */
    public void registrarEvaluacionAprobada(int idUnidad, int calificacion) {
        if (Modelo_Unidades.registrarEvaluacionAprobada(idUnidad, correo, calificacion)) {
            actualizarVista(); // Refrescar la vista
            JOptionPane.showMessageDialog(vista,
                    "¡Felicitaciones! Has completado la unidad " + idUnidad
                    + " con una calificación de " + calificacion + "%",
                    "Unidad Completada", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Obtiene el correo del usuario actual
     *
     * @return Correo del usuario
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene el controlador del dashboard
     *
     * @return Controlador del dashboard
     */
    public ControladorDashboard getControladorDashboard() {
        return controladorDashboard;
    }

    /**
     * Obtiene la vista del dashboard
     *
     * @return Vista del dashboard
     */
    public Dashboard getDashboard() {
        return dashboard;
    }
}
