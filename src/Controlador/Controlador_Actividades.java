package Controlador;

import Modelo.*;
import Vista.Vistas_Unidad1.Vista_Actividad2U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import java.awt.CardLayout;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.SwingUtilities;

/**
 * Controlador optimizado para gestionar las actividades de la Unidad 1.
 * Maneja tanto ejercicios de emparejar como de drag & drop de forma secuencial.
 * 
 * @author ProyectoInti
 * @version 2.0
 */
public class Controlador_Actividades {
    
    private static final Logger LOGGER = Logger.getLogger(Controlador_Actividades.class.getName());
    
    // Constantes para los nombres de paneles
    private static final String PANEL_EMPAREJAR = "Emparejar";
    private static final String PANEL_DRAGDROP = "DragDrop";
    
    // Componentes principales
    private final javax.swing.JPanel vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idActividad;
    private Modelo_Actividades actividad;
    private final Controlador_Unidad1 controladorUnidad1;

    // Datos de las actividades
    private List<Modelo_DragDrop.DragDropItem> itemsDragDrop;
    private List<Modelo_Emparejar.EmparejarItem> itemsEmparejar;

    // Estados de completación
    private boolean dragDropCompletado = false;
    private boolean emparejarCompletado = false;
    private boolean actividadInicializada = false;

    /**
     * Constructor del controlador de actividades.
     * 
     * @param vista Panel de la vista de actividad
     * @param controladorDashboard Controlador del dashboard principal
     * @param conn Conexión a la base de datos
     * @param correo Correo del usuario
     * @param idActividad ID de la actividad a cargar
     * @param controladorUnidad1 Controlador de la unidad 1
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public Controlador_Actividades(javax.swing.JPanel vista, ControladorDashboard controladorDashboard,
                                   Connection conn, String correo, int idActividad, Controlador_Unidad1 controladorUnidad1) {
        // Validaciones de parámetros
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser null");
        }
        if (controladorDashboard == null) {
            throw new IllegalArgumentException("El controlador dashboard no puede ser null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser null o vacío");
        }
        if (idActividad <= 0) {
            throw new IllegalArgumentException("El ID de actividad debe ser mayor a 0");
        }
        
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;
        this.correo = correo.trim();
        this.idActividad = idActividad;
        this.controladorUnidad1 = controladorUnidad1;

        LOGGER.info(String.format("Inicializando controlador de actividades para usuario: %s, actividad: %d", 
                                 this.correo, this.idActividad));
        
        inicializarControlador();
    }
    
    /**
     * Inicializa el controlador configurando eventos y estado inicial.
     */
    private void inicializarControlador() {
        try {
            agregarEventos();
            actividadInicializada = true;
            LOGGER.info("Controlador de actividades inicializado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el controlador de actividades", e);
            mostrarError("Error al inicializar la actividad: " + e.getMessage());
        }
    }

    /**
     * Carga la actividad desde la base de datos e inicializa la interfaz.
     * 
     * @throws RuntimeException si hay error al cargar la actividad
     */
    public void cargarActividad() {
        if (!actividadInicializada) {
            LOGGER.warning("Intentando cargar actividad antes de inicializar el controlador");
            return;
        }
        
        try {
            LOGGER.info(String.format("Cargando actividad con ID: %d", idActividad));
            
            actividad = Modelo_Actividades.obtenerPorId(conn, idActividad);
            if (actividad == null) {
                String errorMsg = "No se encontró la actividad con ID " + idActividad;
                LOGGER.warning(errorMsg);
                mostrarError(errorMsg);
                return;
            }

            if (!(vista instanceof Vista_Actividad2U1 act2)) {
                String errorMsg = "La vista no es compatible con Vista_Actividad2U1";
                LOGGER.severe(errorMsg);
                mostrarError(errorMsg);
                return;
            }

            // Reiniciar estados
            reiniciarEstados();

            // Configurar interfaz inicial
            configurarInterfazInicial(act2);
            
            // Cargar datos de ambas actividades
            cargarDatosActividades(act2);
            
            LOGGER.info("Actividad cargada exitosamente");
            
        } catch (Exception e) {
            String errorMsg = "Error al cargar la actividad: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            mostrarError(errorMsg);
        }
    }
    
    /**
     * Reinicia los estados de completación de las actividades.
     */
    private void reiniciarEstados() {
        dragDropCompletado = false;
        emparejarCompletado = false;
        LOGGER.fine("Estados de actividad reiniciados");
    }
    
    /**
     * Configura la interfaz inicial de la actividad.
     */
    private void configurarInterfazInicial(Vista_Actividad2U1 act2) {
        SwingUtilities.invokeLater(() -> {
            mostrarSubPanel(PANEL_EMPAREJAR);
            act2.jLabelPregunta.setText(actividad.getPregunta());
            act2.jLabelPista.setText("Completa el ejercicio de emparejar para continuar.");
            act2.jButtonCOMPLETOACTV2.setEnabled(false);
        });
    }
    
    /**
     * Carga los datos de ambas actividades (emparejar y drag & drop).
     */
    private void cargarDatosActividades(Vista_Actividad2U1 act2) {
        cargarDatosEmparejar(act2);
        cargarDatosDragDrop(act2);
    }

    /**
     * Muestra un subpanel específico usando CardLayout.
     * 
     * @param nombrePanel Nombre del panel a mostrar
     */
    private void mostrarSubPanel(String nombrePanel) {
        if (!(vista instanceof Vista_Actividad2U1 act2)) {
            LOGGER.warning("No se puede cambiar de panel: vista no es Vista_Actividad2U1");
            return;
        }
        
        try {
            SwingUtilities.invokeLater(() -> {
                CardLayout cl = (CardLayout) act2.jPanelContenedor.getLayout();
                cl.show(act2.jPanelContenedor, nombrePanel);
                LOGGER.fine(String.format("Cambiado a panel: %s", nombrePanel));
            });
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al cambiar de panel", e);
        }
    }

    private void agregarEventos() {
        if (!(vista instanceof Vista_Actividad2U1 act2)) return;

        act2.jButtonValidarEmparejar.addActionListener(e -> validarEmparejar(act2));
        act2.jButtonValidarDragDrop.addActionListener(e -> validarDragDrop(act2));

        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        act2.jButtonCOMPLETOACTV2.addActionListener(e -> completarActividad(idUsuario));
    }

    private void cargarDatosDragDrop(Vista_Actividad2U1 act2) {
        Modelo_DragDrop modeloDragDrop = new Modelo_DragDrop(conn);
        itemsDragDrop = modeloDragDrop.obtenerItemsPorActividad(idActividad);

        if (itemsDragDrop.size() >= 3) {
            act2.jLabelDragDropOpcionA.setText(itemsDragDrop.get(0).texto);
            act2.jLabelDragDropOpcionB.setText(itemsDragDrop.get(1).texto);
            act2.jLabelDragDropOpcionC.setText(itemsDragDrop.get(2).texto);
              act2.jLabelPregunta.setText(actividad.getPregunta());  // <-- refresca la pregunta
        } else {
            act2.jLabelPista.setText("No hay suficientes ítems de drag drop.");
        }
    }

    private void validarDragDrop(Vista_Actividad2U1 act2) {
    String textoDestino = act2.jLabelDestino.getText().trim();

    boolean esCorrecto = false;
    for (Modelo_DragDrop.DragDropItem item : itemsDragDrop) {
        if (item.texto.equals(textoDestino) && item.posicionDestino.equalsIgnoreCase("mañana")) {
            esCorrecto = true;
            break;
        }
    }

    if (esCorrecto) {
        act2.jLabelPista.setText("¡Correcto!");
        dragDropCompletado = true;
    } else {
        act2.jLabelPista.setText("Incorrecto, intenta otra vez.");
    }

    actualizarEstadoBotonCompleto(act2);
}


    private void cargarDatosEmparejar(Vista_Actividad2U1 act2) {
        Modelo_Emparejar modeloEmparejar = new Modelo_Emparejar(conn);
        itemsEmparejar = modeloEmparejar.obtenerOpcionesPorActividad(idActividad);

        if (itemsEmparejar.size() < 3) {
            act2.jLabelPista.setText("No hay suficientes ítems de emparejar.");
            return;
        }

        act2.jLabelEmaprejar1.setText(itemsEmparejar.get(0).textoOrigen);
        act2.jLabelEmaprejar2.setText(itemsEmparejar.get(1).textoOrigen);
        act2.jLabelEmaprejar3.setText(itemsEmparejar.get(2).textoOrigen);

        act2.jComboBoxEmparejarOpcionA.removeAllItems();
        act2.jComboBoxEmparejarOpcionB.removeAllItems();
        act2.jComboBoxEmparejarOpcionC.removeAllItems();

        for (Modelo_Emparejar.EmparejarItem item : itemsEmparejar) {
            act2.jComboBoxEmparejarOpcionA.addItem(item.textoDestino);
            act2.jComboBoxEmparejarOpcionB.addItem(item.textoDestino);
            act2.jComboBoxEmparejarOpcionC.addItem(item.textoDestino);
        }
    }

    private void validarEmparejar(Vista_Actividad2U1 act2) {
    String palabraA = act2.jLabelEmaprejar1.getText();
    String palabraB = act2.jLabelEmaprejar2.getText();
    String palabraC = act2.jLabelEmaprejar3.getText();

    String respuestaA = (String) act2.jComboBoxEmparejarOpcionA.getSelectedItem();
    String respuestaB = (String) act2.jComboBoxEmparejarOpcionB.getSelectedItem();
    String respuestaC = (String) act2.jComboBoxEmparejarOpcionC.getSelectedItem();

    if (respuestaA == null || respuestaB == null || respuestaC == null) {
        act2.jLabelPista.setText("Selecciona todas las opciones.");
        return;
    }

    boolean correctoA = esEmparejamientoCorrecto(palabraA, respuestaA);
    boolean correctoB = esEmparejamientoCorrecto(palabraB, respuestaB);
    boolean correctoC = esEmparejamientoCorrecto(palabraC, respuestaC);

    if (correctoA && correctoB && correctoC) {
        emparejarCompletado = true;
        act2.jLabelPista.setText("¡Correcto en Emparejar! Ahora completa el DragDrop.");

        // Cargar los datos para el DragDrop
        cargarDatosDragDrop(act2);
        // Cambiar de subpanel
        mostrarSubPanel(PANEL_DRAGDROP);

    } else {
        emparejarCompletado = false;
        act2.jLabelPista.setText("Incorrecto en emparejar, revisa tus selecciones.");
    }
}


    private boolean esEmparejamientoCorrecto(String textoOrigen, String textoDestino) {
        for (Modelo_Emparejar.EmparejarItem item : itemsEmparejar) {
            if (item.textoOrigen.equals(textoOrigen) && item.textoDestino.equals(textoDestino)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarEstadoBotonCompleto(Vista_Actividad2U1 act2) {
        if (emparejarCompletado && dragDropCompletado) {
            act2.jButtonCOMPLETOACTV2.setEnabled(true);
            act2.jLabelPista.setText("¡Has completado todas las actividades!");
        } else {
            act2.jButtonCOMPLETOACTV2.setEnabled(false);
        }
    }

    /**
     * Completa la actividad actual y actualiza el progreso del usuario.
     * 
     * @param idUsuario ID del usuario que completa la actividad
     */
    private void completarActividad(int idUsuario) {
        try {
            LOGGER.info(String.format("Completando actividad %d para usuario %d", idActividad, idUsuario));
            
            // Actualizar progreso en la base de datos
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, actividad.getIdUnidad());
            boolean actualizado = ControladorProgresoUsuario.actualizarActividad(progreso, idActividad);
            
            if (actualizado) {
                LOGGER.info(String.format("Actividad %d completada exitosamente", idActividad));
                mostrarMensajeExito("¡Actividad completada exitosamente!");
            } else {
                LOGGER.warning("No se pudo actualizar el progreso de la actividad");
                mostrarError("Error al guardar el progreso. Intenta nuevamente.");
                return;
            }

            // Navegar de vuelta a la unidad
            navegarAUnidad1();
            
        } catch (Exception e) {
            String errorMsg = "Error al completar la actividad: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            mostrarError(errorMsg);
        }
    }
    
    /**
     * Navega de vuelta a la vista de la Unidad 1.
     */
    private void navegarAUnidad1() {
        SwingUtilities.invokeLater(() -> {
            try {
                Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
                new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo,
                        controladorUnidad1 != null ? controladorUnidad1.getControladorUnidades() : null);
                
                controladorDashboard.getVista().mostrarVista(vistaUnidad1);
                LOGGER.info("Navegación a Unidad 1 completada");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Error al navegar a la Unidad 1", e);
                mostrarError("Error al regresar a la unidad. Intenta recargar la aplicación.");
            }
        });
    }
    
    /**
     * Muestra un mensaje de error al usuario.
     * 
     * @param mensaje Mensaje de error a mostrar
     */
    private void mostrarError(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(
                vista, 
                mensaje, 
                "Error", 
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        });
    }
    
    /**
     * Muestra un mensaje de éxito al usuario.
     * 
     * @param mensaje Mensaje de éxito a mostrar
     */
    private void mostrarMensajeExito(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            javax.swing.JOptionPane.showMessageDialog(
                vista, 
                mensaje, 
                "Éxito", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
