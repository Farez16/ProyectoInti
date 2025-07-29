package Controlador;

import Modelo.Modelo_Actividades;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vistas_Unidad1.Vista_Actividad2U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import java.awt.CardLayout;
import java.sql.Connection;
import java.util.logging.Logger;
import java.util.logging.Level;
import javax.swing.SwingUtilities;

/**
 * Controlador optimizado para gestionar las actividades de la Unidad 1. Maneja
 * tanto ejercicios de emparejar como de drag & drop de forma secuencial.
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

    // Datos de las actividades (ahora usando datos hardcodeados)

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

        // Instrucciones eliminadas - solo se muestran en la bienvenida de la unidad
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
            // Configurar pregunta específica para emparejar
            if (actividad != null && actividad.getPregunta() != null) {
                act2.jLabelPreguntaEmparejar.setText(actividad.getPregunta());
            } else {
                act2.jLabelPreguntaEmparejar.setText("Empareja las palabras con sus significados correctos:");
            }
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
        if (!(vista instanceof Vista_Actividad2U1 act2)) {
            return;
        }

        act2.jButtonValidarEmparejar.addActionListener(e -> validarEmparejar(act2));
        act2.jButtonValidarDragDrop.addActionListener(e -> validarDragDrop(act2));

        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        act2.jButtonCOMPLETOACTV2.addActionListener(e -> completarActividad(idUsuario));
    }

    private void cargarDatosDragDrop(Vista_Actividad2U1 act2) {
        try {
            LOGGER.info("Iniciando carga de datos de drag & drop con saludos en kichwa ecuatoriano");
            
            // Configurar la pregunta en el label correspondiente
            act2.jLabelPreguntaDragDrop.setText("Arrastra la expresión correcta para 'Buenos días' en kichwa:");
            
            // Configurar las opciones de drag & drop con saludos en kichwa
            act2.jLabelDragDropOpcionA.setText("Alli puncha");     // Buenos días (CORRECTO)
            act2.jLabelDragDropOpcionB.setText("Alli chishi");     // Buenas tardes (distractor)
            act2.jLabelDragDropOpcionC.setText("Alli tuta");       // Buenas noches (distractor)
            
            // Configurar el área de destino
            act2.jLabelDestino.setText("Arrastra aquí la respuesta correcta");
            
            // Limpiar mensaje de respuesta
            act2.jLabelMensajeRespuesta.setText("");
            
            LOGGER.info("Datos de drag & drop cargados correctamente con saludos hardcodeados");
            LOGGER.info("Pregunta: Buenos días en kichwa. Respuesta correcta: Alli puncha");
            
        } catch (Exception e) {
            String errorMsg = "Error al cargar datos de drag & drop: " + e.getMessage();
            act2.jLabelMensajeRespuesta.setText("Error al cargar datos de drag & drop.");
            LOGGER.log(Level.SEVERE, errorMsg, e);
        }
    }


    private void validarDragDrop(Vista_Actividad2U1 act2) {
        if (act2 == null) {
            LOGGER.warning("La vista de actividad es nula en validarDragDrop");
            return;
        }
        
        LOGGER.info("Iniciando validación de drag & drop con datos hardcodeados");
        
        String textoDestino = act2.jLabelDestino.getText().trim();
        LOGGER.info(String.format("Texto en destino: '%s'", textoDestino));
        
        // Validar si la respuesta correcta fue arrastrada al destino
        // La respuesta correcta para "Buenos días" en kichwa es "Alli puncha"
        boolean esCorrecto = textoDestino.equals("Alli puncha");
        
        if (esCorrecto) {
            dragDropCompletado = true;
            act2.jLabelMensajeRespuesta.setText("¡Excelente! Drag & Drop completado correctamente. ¡Has terminado todas las actividades!");
            LOGGER.info("Drag & Drop completado exitosamente");
        } else {
            dragDropCompletado = false;
            if (textoDestino.equals("Arrastra aquí la respuesta correcta")) {
                act2.jLabelMensajeRespuesta.setText("Arrastra una de las opciones al área de destino.");
            } else {
                act2.jLabelMensajeRespuesta.setText(String.format("Incorrecto. '%s' no es la traducción correcta de 'Buenos días'. Intenta otra vez.", textoDestino));
            }
            LOGGER.info(String.format("Respuesta incorrecta: '%s'. Se esperaba: 'Alli puncha'", textoDestino));
        }
        
        actualizarEstadoBotonCompleto(act2);
    }

    private void cargarDatosEmparejar(Vista_Actividad2U1 act2) {
        try {
            LOGGER.info("Iniciando carga de datos de emparejar con saludos en kichwa ecuatoriano");
            
            // Configurar la pregunta principal
            act2.jLabelPreguntaEmparejar.setText("Empareja los saludos en español con su equivalente en kichwa ecuatoriano:");
            
            // Limpiar las opciones actuales de los ComboBox
            act2.jComboBoxEmparejarOpcionA.removeAllItems();
            act2.jComboBoxEmparejarOpcionB.removeAllItems();
            act2.jComboBoxEmparejarOpcionC.removeAllItems();
            
            // Configurar los saludos en español en los JLabels
            act2.jLabelEmaprejar1.setText("Buenos días");
            act2.jLabelEmaprejar2.setText("Buenas tardes");
            act2.jLabelEmaprejar3.setText("¿Cómo estás?");
            
            // Agregar opción por defecto a todos los ComboBox
            act2.jComboBoxEmparejarOpcionA.addItem("Selecciona una opción...");
            act2.jComboBoxEmparejarOpcionB.addItem("Selecciona una opción...");
            act2.jComboBoxEmparejarOpcionC.addItem("Selecciona una opción...");
            
            // Opciones en kichwa ecuatoriano (mezcladas para mayor dificultad)
            String[] opcionesKichwa = {
                "Alli puncha",      // Buenos días
                "Alli chishi",      // Buenas tardes  
                "Imashinalla kanki", // ¿Cómo estás?
                "Napaykuyki",       // Hola (distractor)
                "Alli tuta",        // Buenas noches (distractor)
                "Tupanakama"         // Hasta luego (distractor)
            };
            
            // Agregar todas las opciones a cada ComboBox (mezcladas)
            for (String opcion : opcionesKichwa) {
                act2.jComboBoxEmparejarOpcionA.addItem(opcion);
                act2.jComboBoxEmparejarOpcionB.addItem(opcion);
                act2.jComboBoxEmparejarOpcionC.addItem(opcion);
            }
            
            // Limpiar mensaje de respuesta
            act2.jLabelMensajeRespuesta.setText("");
            
            LOGGER.info("Carga de datos de emparejar completada exitosamente con saludos hardcodeados");
            LOGGER.info("Configurado: Buenos días -> Alli puncha, Buenas tardes -> Alli chishi, ¿Cómo estás? -> Imashinalla kanki");
            
        } catch (Exception e) {
            String errorMsg = "Error al cargar datos de emparejar: " + e.getMessage();
            act2.jLabelMensajeRespuesta.setText("Error al cargar datos de emparejar.");
            LOGGER.log(Level.SEVERE, errorMsg, e);
        }
    }

private void validarEmparejar(Vista_Actividad2U1 act2) {
        if (act2 == null) {
            LOGGER.warning("La vista de actividad es nula en validarEmparejar");
            return;
        }
        
        LOGGER.info("Iniciando validación de emparejar con datos hardcodeados");

        String palabraA = act2.jLabelEmaprejar1.getText();
        String palabraB = act2.jLabelEmaprejar2.getText();
        String palabraC = act2.jLabelEmaprejar3.getText();

        String respuestaA = (String) act2.jComboBoxEmparejarOpcionA.getSelectedItem();
        String respuestaB = (String) act2.jComboBoxEmparejarOpcionB.getSelectedItem();
        String respuestaC = (String) act2.jComboBoxEmparejarOpcionC.getSelectedItem();

        // Validar que se hayan seleccionado opciones válidas (no la opción por defecto)
        if (respuestaA == null || respuestaB == null || respuestaC == null ||
            respuestaA.equals("Selecciona una opción...") ||
            respuestaB.equals("Selecciona una opción...") ||
            respuestaC.equals("Selecciona una opción...")) {
            act2.jLabelMensajeRespuesta.setText("Selecciona todas las opciones correctamente.");
            return;
        }

        LOGGER.info(String.format("Validando emparejamientos: A='%s'->'%s', B='%s'->'%s', C='%s'->'%s'", 
                palabraA, respuestaA, palabraB, respuestaB, palabraC, respuestaC));

        boolean correctoA = esEmparejamientoCorrecto(palabraA, respuestaA);
        boolean correctoB = esEmparejamientoCorrecto(palabraB, respuestaB);
        boolean correctoC = esEmparejamientoCorrecto(palabraC, respuestaC);
        
        int aciertos = 0;
        if (correctoA) aciertos++;
        if (correctoB) aciertos++;
        if (correctoC) aciertos++;
        
        LOGGER.info(String.format("Resultados de validación: A=%b, B=%b, C=%b. Aciertos: %d/3", 
                correctoA, correctoB, correctoC, aciertos));

        if (correctoA && correctoB && correctoC) {
            emparejarCompletado = true;
            act2.jLabelMensajeRespuesta.setText("¡Excelente! Emparejar completado correctamente. Ahora completa el Drag & Drop.");
            LOGGER.info("Actividad de emparejar completada exitosamente");
            cargarDatosDragDrop(act2);
            mostrarSubPanel(PANEL_DRAGDROP);
        } else {
            emparejarCompletado = false;
            act2.jLabelMensajeRespuesta.setText(String.format("Tienes %d/3 respuestas correctas. Revisa tus selecciones e intenta nuevamente.", aciertos));
            LOGGER.info(String.format("Actividad de emparejar incompleta: %d/3 aciertos", aciertos));
        }
    }
private boolean esEmparejamientoCorrecto(String palabra, String respuesta) {
        // Validar emparejamientos con datos hardcodeados de saludos en kichwa ecuatoriano
        boolean esCorrecta = false;
        String respuestaEsperada = "";
        
        // Definir las correspondencias correctas
        switch (palabra) {
            case "Buenos días":
                respuestaEsperada = "Alli puncha";
                esCorrecta = respuesta.equals("Alli puncha");
                break;
            case "Buenas tardes":
                respuestaEsperada = "Alli chishi";
                esCorrecta = respuesta.equals("Alli chishi");
                break;
            case "¿Cómo estás?":
                respuestaEsperada = "Imashinalla kanki";
                esCorrecta = respuesta.equals("Imashinalla kanki");
                break;
            default:
                LOGGER.warning(String.format("Palabra no reconocida: '%s'", palabra));
                return false;
        }
        
        LOGGER.info(String.format("Validando '%s' -> '%s': %s (esperado: '%s')", 
                palabra, respuesta, esCorrecta ? "CORRECTO" : "INCORRECTO", respuestaEsperada));
        
        return esCorrecta;
    }





    private void actualizarEstadoBotonCompleto(Vista_Actividad2U1 act2) {
        if (emparejarCompletado && dragDropCompletado) {
            act2.jButtonCOMPLETOACTV2.setEnabled(true);
            act2.jLabelMensajeRespuesta.setText("¡Has completado todas las actividades!");
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

            // Obtener progreso actual del usuario
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, actividad.getIdUnidad());

            if (progreso == null) {
                LOGGER.severe("No se pudo obtener el progreso del usuario");
                mostrarError("Error al obtener el progreso del usuario");
                return;
            }

            // Verificar si esta actividad ya fue completada
            if (progreso.getActividadesCompletadas() >= idActividad) {
                int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                        vista,
                        "Ya has completado esta actividad. ¿Quieres marcarla como completada nuevamente?",
                        "Actividad ya completada",
                        javax.swing.JOptionPane.YES_NO_OPTION,
                        javax.swing.JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
                    LOGGER.info("Usuario canceló la recompletación de la actividad");
                    return;
                }
            }

            // Actualizar progreso solo si es necesario
            boolean actualizado = false;
            if (progreso.getActividadesCompletadas() < idActividad) {
                actualizado = ControladorProgresoUsuario.actualizarActividad(progreso, idActividad);
                if (actualizado) {
                    LOGGER.info("Progreso actualizado en base de datos");
                }
            } else {
                // Si ya estaba completada pero el usuario confirmó, consideramos como exitoso
                actualizado = true;
                LOGGER.info("Actividad ya completada, usuario confirmó recompletación");
            }

            if (actualizado) {
                mostrarMensajeExito("¡Actividad completada exitosamente!");

                // Notificar al controlador de la unidad para actualizar la interfaz
                if (controladorUnidad1 != null) {
                    SwingUtilities.invokeLater(() -> {
                        try {
                            controladorUnidad1.actualizarVista();
                            LOGGER.info("Vista de unidad actualizada");
                        } catch (Exception ex) {
                            LOGGER.log(Level.WARNING, "Error al actualizar vista de unidad", ex);
                        }
                    });
                }

                // Navegar de vuelta a la unidad
                navegarAUnidad1();
            } else {
                LOGGER.warning("No se pudo actualizar el progreso de la actividad");
                mostrarError("Error al guardar el progreso. Intenta nuevamente.");
            }

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
        if (controladorDashboard == null) {
            LOGGER.severe("No se puede navegar: controladorDashboard es nulo");
            mostrarError("Error de navegación: No se puede cargar la unidad.");
            return;
        }

        SwingUtilities.invokeLater(() -> {
            try {
                Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
                // El controlador se instancia pero no se guarda en una variable ya que se asocia directamente con la vista
                new Controlador_Unidad1(
                        vistaUnidad1,
                        conn,
                        controladorDashboard,
                        correo,
                        controladorUnidad1 != null ? controladorUnidad1.getControladorUnidades() : null
                );

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
     * Configura los elementos de interfaz para la actividad drag-drop.
     */
    private void configurarElementosDragDrop(Vista_Actividad2U1 act2) {
        try {
            // Configurar elementos visuales para drag-drop
            // Los elementos específicos se configuran según la vista disponible

            LOGGER.info("Elementos de drag-drop configurados correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al configurar elementos de drag-drop", e);
        }
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

    // Método de instrucciones eliminado - las instrucciones se muestran solo en la bienvenida de la unidad
}
