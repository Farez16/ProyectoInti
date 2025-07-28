package Controlador;

import Vista.Estudiante.Vista_PanelUnidades;
import Vista.Estudiante.Dashboard;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Vista.*;
import Modelo.Usuario;
import Modelo.Modelo_Unidades;
import Conexion.Conexion;
import VistasUnidad3.Vista_Actividad_Familia;
import VistasUnidad3.Vista_Unidad3;
import VistasUnidad4.Vista_Unidad4;
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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Controlador para gestionar la vista del panel de unidades Maneja la
 * interacción entre la vista y el modelo de datos
 */
public class Controlador_Unidades {

    // Constantes para totales de contenido por unidad
    private static final int TOTAL_LECCIONES_POR_UNIDAD = 5;
    private static final int TOTAL_ACTIVIDADES_POR_UNIDAD = 3;
    private static final int TOTAL_UNIDADES = 4;

    // Atributos principales
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
     */
    public Controlador_Unidades(Vista_PanelUnidades vista, Dashboard dashboard,
            ControladorDashboard controladorDashboard, String correo) {

        // Validaciones
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser null");
        }
        if (controladorDashboard == null) {
            throw new IllegalArgumentException("El controlador dashboard no puede ser null");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser null o vacío");
        }

        this.vista = vista;
        this.dashboard = dashboard;
        this.controladorDashboard = controladorDashboard;
        this.correo = correo;
        this.unidadesDisponibles = new boolean[TOTAL_UNIDADES + 1]; // índice 0 no usado, 1-4 para unidades

        inicializar();
    }

    /**
     * Inicializa el controlador cargando datos y configurando eventos
     */
    private void inicializar() {
        try {
            cargarUnidades();
            agregarEventos();
        } catch (Exception e) {
            System.err.println("Error al inicializar controlador de unidades: " + e.getMessage());
            mostrarError("Error al inicializar el panel de unidades");
        }
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

            // Actualizar vista para cada unidad
            for (Modelo_Unidades unidad : unidades) {
                actualizarVistaUnidad(unidad);
            }

            // Actualizar progreso general
            int progresoGeneral = obtenerProgresoGeneral();
            actualizarProgresoGeneral(progresoGeneral);

        } catch (Exception e) {
            System.err.println("Error al cargar unidades: " + e.getMessage());
            mostrarError("Error al cargar las unidades. Por favor, intenta de nuevo.");
        }
    }

    /**
     * Actualiza la vista de una unidad específica
     *
     * @param unidad Modelo de la unidad a actualizar
     */
    private void actualizarVistaUnidad(Modelo_Unidades unidad) {
        try {
            int idUnidad = unidad.getIdUnidad();

            // Validar que la unidad esté en el rango válido
            if (idUnidad < 1 || idUnidad > TOTAL_UNIDADES) {
                System.err.println("ID de unidad inválido: " + idUnidad);
                return;
            }

            boolean disponible = unidadesDisponibles[idUnidad];

            // Obtener componentes de la vista
            JButton btnUnidad = obtenerBotonUnidad(idUnidad);
            JProgressBar progresoUnidades = obtenerJProgressProgreso(idUnidad);

            if (btnUnidad != null && progresoUnidades != null) {
                unidad.setDisponible(disponible);

                // Configurar colores y estado
                String colorEstado = unidad.getColorEstado();

                // Calcular y mostrar progreso
                int progresoUnidad = unidad.calcularProgresoTotal(
                        TOTAL_LECCIONES_POR_UNIDAD, TOTAL_ACTIVIDADES_POR_UNIDAD);

                progresoUnidades.setValue(progresoUnidad); // Valor del progreso
                progresoUnidades.setString(progresoUnidad + "%"); // Texto mostrado
                progresoUnidades.setStringPainted(true); // Mostrar texto
                progresoUnidades.setForeground(Color.decode(colorEstado)); // Color del texto

                // Configurar botón
                btnUnidad.setForeground(Color.decode(colorEstado));

                if (disponible) {
                    btnUnidad.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    btnUnidad.setEnabled(true);
                } else {
                    btnUnidad.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    btnUnidad.setEnabled(false);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar vista de unidad " + unidad.getIdUnidad() + ": " + e.getMessage());
        }
    }

    /**
     * Actualiza el progreso general en la vista
     *
     * @param progresoGeneral Porcentaje de progreso (0-100)
     */
    private void actualizarProgresoGeneral(int progresoGeneral) {
        try {
            if (vista.ProgresoTotal != null) {
                vista.ProgresoTotal.setValue(progresoGeneral); // Establece el valor numérico
                vista.ProgresoTotal.setString(progresoGeneral + "%"); // Muestra el texto encima de la barra
                vista.ProgresoTotal.setStringPainted(true); // Activa la visualización del texto
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso general: " + e.getMessage());
        }
    }

    /**
     * Obtiene el label de una unidad específica
     *
     * @param idUnidad ID de la unidad
     * @return JLabel correspondiente o null si no existe
     */
    private JButton obtenerBotonUnidad(int idUnidad) {
        switch (idUnidad) {
            case 1:
                return vista.btnUnidad1;
            case 2:
                return vista.btnUnidad2;
            case 3:
                return vista.btnUnidad3;
            case 4:
                return vista.btnUnidad4;
            default:
                return null;
        }
    }

    /**
     * Obtiene el label de progreso de una unidad específica
     *
     * @param idUnidad ID de la unidad
     * @return JLabel correspondiente al progreso o null si no existe
     */
    private JProgressBar obtenerJProgressProgreso(int idUnidad) {
        switch (idUnidad) {
            case 1:
                return vista.ProgresoU1;
            case 2:
                return vista.ProgresoU2;
            case 3:
                return vista.ProgresoU3;
            case 4:
                return vista.ProgresoU4;
            default:
                return null;
        }
    }

    /**
     * Agrega los eventos de mouse a las unidades
     */
    private void agregarEventos() {
        configurarEventosUnidad(vista.btnUnidad1, 1, "Saludos y Presentaciones");
        configurarEventosUnidad(vista.btnUnidad2, 2, "Familia y Hogar");
        configurarEventosUnidad(vista.btnUnidad3, 3, "Naturaleza y Animales");
        configurarEventosUnidad(vista.btnUnidad4, 4, "Números y Colores");
    }

    /**
     * Configura los eventos para un label de unidad específico
     *
     * @param labelUnidad Label de la unidad
     * @param idUnidad ID de la unidad
     * @param nombreUnidad Nombre de la unidad
     */
    private void configurarEventosUnidad(JButton botonUnidad, int idUnidad, String nombreUnidad) {
        if (botonUnidad == null) {
            return;
        }

        botonUnidad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (idUnidad < unidadesDisponibles.length && unidadesDisponibles[idUnidad]) {
                    botonUnidad.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonUnidad.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Click detectado en Unidad " + idUnidad);
                abrirUnidad(idUnidad, nombreUnidad);
            }
        });
    }

    /**
     * Abre una unidad específica
     *
     * @param idUnidad El ID de la unidad a abrir
     * @param nombreUnidad El nombre de la unidad
     */
    private void abrirUnidad(int idUnidad, String nombreUnidad) {
        try {
            // Verificar disponibilidad
//            if (idUnidad >= unidadesDisponibles.length || !unidadesDisponibles[idUnidad]) {
//                JOptionPane.showMessageDialog(vista,
//                        "Esta unidad no está disponible aún.\nCompleta la unidad anterior para desbloquearla.",
//                        "Unidad no disponible",
//                        JOptionPane.WARNING_MESSAGE);
//                return;
//            }

            // Registrar visualización
            Modelo_Unidades.registrarVisualizacionUnidad(idUnidad, correo);

            // Configurar estilo para el mensaje
            configurarEstiloMensaje();

            // Mostrar mensaje de bienvenida
            mostrarMensajeBienvenida(nombreUnidad);

            // Restaurar estilo por defecto
            restaurarEstiloDefecto();

            // Crear vista según la unidad
            switch (idUnidad) {
                case 1:
                    abrirUnidad1();
                    break;
                case 2:
                    abrirUnidad2();
                    break;
                case 3:
                    abrirunidad3();
                case 4:
                    abrirUnidad4();
                    break;
                default:
                    System.err.println("ID de unidad no válido: " + idUnidad);
                    break;
            }

        } catch (Exception e) {
            System.err.println("Error al abrir unidad " + idUnidad + ": " + e.getMessage());
            mostrarError("Error al abrir la unidad. Por favor, intenta de nuevo.");
        }
    }

    /**
     * Abre la unidad 1 específicamente
     */
    private void abrirUnidad1() {
        try {
            System.out.println("=== DEBUG: Iniciando abrirUnidad1() ===");

            // 1. Crear la vista de la unidad 1
            Vista_Unidad1 unidad1 = new Vista_Unidad1();
            System.out.println("Vista_Unidad1 creada: " + (unidad1 != null));

            // 2. Obtener la conexión a la base de datos
            Connection conn = (Connection) controladorDashboard.getConnection();
            System.out.println("Connection obtenida: " + (conn != null));

            // 3. Validar referencias
            if (controladorDashboard == null) {
                throw new IllegalStateException("ControladorDashboard no puede ser nulo");
            }

            if (dashboard == null) {
                throw new IllegalStateException("Dashboard no puede ser nulo");
            }

            // 4. Configurar la vista en el dashboard ANTES de crear el controlador
            dashboard.mostrarVista(unidad1);

            // 5. Forzar la actualización de la interfaz
            unidad1.revalidate();
            unidad1.repaint();

            // 6. Crear el controlador (esto inicializará la vista)
            Controlador_Unidad1 controlador = new Controlador_Unidad1(
                    unidad1,
                    conn,
                    controladorDashboard,
                    correo,
                    this
            );

            System.out.println("Controlador_Unidad1 creado: " + (controlador != null));
            System.out.println("=== DEBUG: abrirUnidad1() completado ===");

        } catch (Exception e) {
            System.err.println("Error al abrir unidad 1: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al abrir la unidad 1. Por favor, intenta de nuevo.");

            // Intentar regresar al dashboard en caso de error
            try {
                if (dashboard != null) {
                    dashboard.mostrarVista(dashboard);
                }
            } catch (Exception ex) {
                System.err.println("Error al regresar al dashboard: " + ex.getMessage());
            }
        }
    }
    // In Controlador_Unidades.java

    private void abrirUnidad2() {
        Vista_Unidad2 unidad2 = new Vista_Unidad2();
        new ControladorUnidad2(unidad2, dashboard);
        dashboard.mostrarVista(unidad2);

    }

    private void abrirunidad3() {
        try {
            Vista_Unidad3 unidad3 = new Vista_Unidad3();
            Connection conn = (Connection) controladorDashboard.getConnection();

            // Crear el controlador pasando todos los parámetros necesarios
            Controlador_Unidad3 controlador = new Controlador_Unidad3(
                    unidad3,
                    conn,
                    controladorDashboard,
                    correo,
                    this // Pasar esta instancia de Controlador_Unidades
            );

            dashboard.mostrarVista(unidad3);
        } catch (Exception e) {
            System.err.println("Error al abrir unidad 3: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al abrir la unidad 3. Por favor, intenta de nuevo.");
        }

    }

    private void abrirUnidad4() {
        try {
            Vista_Unidad4 unidad4 = new Vista_Unidad4();
            Connection conn = (Connection) controladorDashboard.getConnection();

            // Crear el controlador pasando todos los parámetros necesarios
            Controlador_Unidad4 controlador = new Controlador_Unidad4(
                    unidad4,
                    conn,
                    controladorDashboard,
                    correo,
                    this // Pasar esta instancia de Controlador_Unidades
            );

            dashboard.mostrarVista(unidad4);
        } catch (Exception e) {
            System.err.println("Error al abrir unidad 3: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al abrir la unidad 3. Por favor, intenta de nuevo.");
        }

    }

    /**
     * Muestra mensaje de bienvenida a la unidad
     *
     * @param nombreUnidad Nombre de la unidad
     */
    private void mostrarMensajeBienvenida(String nombreUnidad) {
        String mensaje = "¡Bienvenido a la unidad: " + nombreUnidad + "!\n\n"
                + "Aquí aprenderás nuevas palabras y conceptos.\n"
                + "¡Diviértete aprendiendo!";

        JOptionPane.showMessageDialog(vista, mensaje,
                "Bienvenido",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Configura el estilo personalizado para los mensajes
     */
    private void configurarEstiloMensaje() {
        try {
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 12));
            UIManager.put("OptionPane.messageForeground", new Color(51, 51, 51));
        } catch (Exception e) {
            System.err.println("Error al configurar estilo de mensaje: " + e.getMessage());
        }
    }

    /**
     * Restaura el estilo por defecto de la UI
     */
    private void restaurarEstiloDefecto() {
        try {
            UIManager.put("OptionPane.messageFont", null);
            UIManager.put("OptionPane.buttonFont", null);
            UIManager.put("OptionPane.messageForeground", null);
        } catch (Exception e) {
            System.err.println("Error al restaurar estilo por defecto: " + e.getMessage());
        }
    }

    /**
     * Obtiene el progreso general del usuario
     *
     * @return Porcentaje de progreso general (0-100)
     */
    private int obtenerProgresoGeneral() {
        try {
            return Modelo_Unidades.obtenerProgresoGeneral(correo);
        } catch (Exception e) {
            System.err.println("Error al obtener progreso general: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Muestra un mensaje de error al usuario
     *
     * @param mensaje Mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Actualiza el progreso de las unidades después de completar actividades
     * Este método es llamado desde otros controladores
     */
    public void actualizarProgreso() {
        try {
            cargarUnidades();
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso: " + e.getMessage());
        }
    }

    /**
     * Actualiza la vista completa del panel de unidades Este método es llamado
     * desde otros controladores para refrescar la vista
     */
    public void actualizarVista() {
        try {
            cargarUnidades();
        } catch (Exception e) {
            System.err.println("Error al actualizar vista: " + e.getMessage());
            mostrarError("Error al actualizar la vista de unidades");
        }
    }

    /**
     * Actualiza el progreso de lecciones para una unidad
     *
     * @param idUnidad ID de la unidad
     * @param leccionesCompletadas Número de lecciones completadas
     */
    public void actualizarProgresoLecciones(int idUnidad, int leccionesCompletadas) {
        try {
            if (Modelo_Unidades.actualizarProgresoLecciones(idUnidad, correo, leccionesCompletadas)) {
                actualizarVista(); // Refrescar la vista
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso de lecciones: " + e.getMessage());
        }
    }

    /**
     * Actualiza el progreso de actividades para una unidad
     *
     * @param idUnidad ID de la unidad
     * @param actividadesCompletadas Número de actividades completadas
     */
    public void actualizarProgresoActividades(int idUnidad, int actividadesCompletadas) {
        try {
            if (Modelo_Unidades.actualizarProgresoActividades(idUnidad, correo, actividadesCompletadas)) {
                actualizarVista(); // Refrescar la vista
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso de actividades: " + e.getMessage());
        }
    }

    /**
     * Registra la aprobación de una evaluación
     *
     * @param idUnidad ID de la unidad
     * @param calificacion Calificación obtenida
     */
    public void registrarEvaluacionAprobada(int idUnidad, int calificacion) {
        try {
            if (Modelo_Unidades.registrarEvaluacionAprobada(idUnidad, correo, calificacion)) {
                actualizarVista(); // Refrescar la vista
                JOptionPane.showMessageDialog(vista,
                        "¡Felicitaciones! Has completado la unidad " + idUnidad
                        + " con una calificación de " + calificacion + "%",
                        "Unidad Completada", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Error al registrar evaluación aprobada: " + e.getMessage());
        }
    }

    /**
     * Verifica si una unidad está disponible para el usuario
     *
     * @param idUnidad El ID de la unidad a verificar
     * @return true si está disponible, false caso contrario
     */
    private boolean verificarDisponibilidadUnidad(int idUnidad) {
        try {
            return Modelo_Unidades.verificarDisponibilidadUnidad(idUnidad, correo);
        } catch (Exception e) {
            System.err.println("Error al verificar disponibilidad de unidad: " + e.getMessage());
            return false;
        }
    }

    // Getters
    public Vista_PanelUnidades getVista() {
        return vista;
    }

    public String getCorreo() {
        return correo;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public ControladorDashboard getControladorDashboard() {
        return controladorDashboard;
    }

    /**
     * Verifica si una unidad está disponible
     *
     * @param idUnidad ID de la unidad a verificar
     * @return true si la unidad está disponible, false en caso contrario
     */
    public boolean isUnidadDisponible(int idUnidad) {
        if (idUnidad < 1 || idUnidad >= unidadesDisponibles.length) {
            return false;
        }
        return unidadesDisponibles[idUnidad];
    }

    /**
     * Fuerza la recarga de todas las unidades Útil cuando se necesita
     * actualizar el estado después de cambios externos
     */
    public void recargarUnidades() {
        try {
            cargarUnidades();
        } catch (Exception e) {
            System.err.println("Error al recargar unidades: " + e.getMessage());
            mostrarError("Error al recargar las unidades");
        }
    }
}
