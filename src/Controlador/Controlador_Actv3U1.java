package Controlador;

import Modelo.Modelo_Actividades;
import Modelo.Modelo_Emparejar;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vistas_Unidad1.Vista_Actividad3U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.net.URL;

/**
 * Controlador dedicado para la Actividad 3 de la Unidad 1.
 * Maneja exclusivamente el juego de asociación palabra-imagen.
 *
 * @author ProyectoInti
 * @version 1.0 - Controlador modular para Actividad 3
 */
public class Controlador_Actv3U1 {

    private static final Logger LOGGER = Logger.getLogger(Controlador_Actv3U1.class.getName());

    // Componentes principales
    private final Vista_Actividad3U1 vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idActividad;
    private Modelo_Actividades actividad;
    private final Controlador_Unidad1 controladorUnidad1;

    // Variables para el juego de asociación
    private List<Modelo_Emparejar.EmparejarItem> paresCorrectos;
    private List<Modelo_Emparejar.EmparejarItem> distractores;
    private final Map<JLabel, String> asociaciones = new HashMap<>();
    private JLabel imagenSeleccionada = null;
    private boolean juegoCompletado = false;
    private boolean actividadInicializada = false;

    /**
     * Constructor del controlador de la actividad 3.
     *
     * @param vista Vista de la actividad 3
     * @param controladorDashboard Controlador del dashboard principal
     * @param conn Conexión a la base de datos
     * @param correo Correo del usuario
     * @param idActividad ID de la actividad (debe ser 3)
     * @param controladorUnidad1 Controlador de la unidad 1
     */
    public Controlador_Actv3U1(Vista_Actividad3U1 vista, ControladorDashboard controladorDashboard,
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
        if (idActividad != 3) {
            throw new IllegalArgumentException("Este controlador es exclusivo para la actividad 3");
        }

        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;
        this.correo = correo.trim();
        this.idActividad = idActividad;
        this.controladorUnidad1 = controladorUnidad1;

        LOGGER.info(String.format("Inicializando controlador de actividad 3 para usuario: %s", this.correo));

        inicializarControlador();
    }

    /**
     * Inicializa el controlador configurando eventos y estado inicial.
     */
    private void inicializarControlador() {
        try {
            agregarEventos();
            actividadInicializada = true;
            LOGGER.info("Controlador de actividad 3 inicializado correctamente");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al inicializar el controlador de actividad 3", e);
            mostrarError("Error al inicializar la actividad: " + e.getMessage());
        }
    }

    /**
     * Carga la actividad 3 desde la base de datos e inicializa el juego.
     */
    public void cargarActividad() {
        if (!actividadInicializada) {
            LOGGER.warning("Intentando cargar actividad antes de inicializar el controlador");
            return;
        }

        try {
            LOGGER.info("Cargando actividad 3 - Juego de asociación");

            actividad = Modelo_Actividades.obtenerPorId(conn, idActividad);
            if (actividad == null) {
                String errorMsg = "No se encontró la actividad con ID " + idActividad;
                LOGGER.warning(errorMsg);
                mostrarError(errorMsg);
                return;
            }

            // Reiniciar estados del juego
            reiniciarEstadosJuego();

            // Cargar y configurar el juego
            cargarJuegoActividad3();

            LOGGER.info("Actividad 3 cargada exitosamente");

        } catch (Exception e) {
            String errorMsg = "Error al cargar la actividad 3: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            mostrarError(errorMsg);
        }
    }

    /**
     * Reinicia los estados del juego de asociación.
     */
    private void reiniciarEstadosJuego() {
        if (asociaciones != null) {
            asociaciones.clear();
        }
        imagenSeleccionada = null;
        juegoCompletado = false;
        LOGGER.info("Estados del juego reiniciados");
    }

    /**
     * Carga y configura el juego de asociación para la actividad 3.
     * Consulta datos desde la base de datos (tabla emparejar_opciones).
     */
    private void cargarJuegoActividad3() {
        try {
            LOGGER.info("Cargando juego de asociación desde base de datos...");

            // 1. Consultar id_actividad donde tipo = 'emparejar' e id_unidad = 1
            int idActividadEmparejar = obtenerIdActividadEmparejar(1);
            if (idActividadEmparejar == -1) {
                mostrarError("No se encontró actividad de emparejar para la unidad 1");
                return;
            }

            // 2. Obtener datos de emparejar desde la base de datos
            Modelo_Emparejar modeloEmparejar = new Modelo_Emparejar(conn);
            List<Modelo_Emparejar.EmparejarItem> todasLasOpciones = modeloEmparejar.obtenerOpcionesPorActividad(idActividadEmparejar);

            if (todasLasOpciones == null || todasLasOpciones.size() < 6) {
                mostrarError("No hay suficientes datos para el juego (mínimo 6 opciones)");
                return;
            }

            // 3. Separar opciones con imagen (pares correctos) y sin imagen (distractores)
            List<Modelo_Emparejar.EmparejarItem> paresConImagen = new ArrayList<>();
            List<Modelo_Emparejar.EmparejarItem> palabrasDistractoras = new ArrayList<>();

            for (Modelo_Emparejar.EmparejarItem item : todasLasOpciones) {
                if (item.recursoDestinoUrl != null && !item.recursoDestinoUrl.trim().isEmpty()) {
                    paresConImagen.add(item);
                } else {
                    palabrasDistractoras.add(item);
                }
            }

            // 4. Seleccionar 3 pares correctos y 3 distractores
            Collections.shuffle(paresConImagen);
            Collections.shuffle(palabrasDistractoras);

            paresCorrectos = paresConImagen.subList(0, Math.min(3, paresConImagen.size()));
            distractores = palabrasDistractoras.subList(0, Math.min(3, palabrasDistractoras.size()));

            // 5. Configurar elementos visuales del juego
            configurarElementosJuego();

            // 6. Agregar eventos del juego
            agregarEventosJuego();

            LOGGER.info("Juego de asociación cargado exitosamente desde base de datos");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el juego de asociación", e);
            mostrarError("Error al cargar el juego: " + e.getMessage());
        }
    }

    /**
     * Obtiene el ID de la actividad de tipo 'emparejar' para una unidad específica.
     */
    private int obtenerIdActividadEmparejar(int idUnidad) {
        String sql = "SELECT id_actividad FROM actividades WHERE tipo = 'emparejar' AND id_unidad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUnidad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_actividad");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al consultar actividad de emparejar", e);
        }
        return -1;
    }

    /**
     * Configura los elementos visuales del juego (imágenes y botones).
     */
    private void configurarElementosJuego() {
        JLabel[] labels = {
            vista.getLblimagen1(), vista.getLblimagen2(), vista.getLblimagen3()
        };

        JButton[] botones = {
            vista.getBtn1(), vista.getBtn2(), vista.getBtn3(),
            vista.getBtn4(), vista.getBtn5(), vista.getBtn6()
        };

        // Configurar imágenes
        for (int i = 0; i < Math.min(paresCorrectos.size(), labels.length); i++) {
            Modelo_Emparejar.EmparejarItem item = paresCorrectos.get(i);
            String rutaCompleta = "/" + item.recursoDestinoUrl;
            URL urlImagen = getClass().getResource(rutaCompleta);

            if (urlImagen == null) {
                LOGGER.warning("No se encontró la imagen: " + rutaCompleta);
            } else {
                ImageIcon icon = new ImageIcon(urlImagen);
                labels[i].setIcon(icon);
                labels[i].setBorder(null); // quitar bordes previos
                labels[i].putClientProperty("palabra", item.textoDestino);
            }
        }

        // Configurar botones con palabras mezcladas (correctas + distractores)
        List<String> palabras = new ArrayList<>();
        for (Modelo_Emparejar.EmparejarItem item : paresCorrectos) {
            palabras.add(item.textoDestino);
        }
        for (Modelo_Emparejar.EmparejarItem item : distractores) {
            palabras.add(item.textoDestino);
        }
        Collections.shuffle(palabras);

        for (int i = 0; i < Math.min(palabras.size(), botones.length); i++) {
            botones[i].setText(palabras.get(i));
            botones[i].setEnabled(true);
            botones[i].setBackground(null); // quitar color de selección
        }
    }

    /**
     * Agrega los eventos de mouse y acción a los elementos del juego.
     */
    private void agregarEventosJuego() {
        JLabel[] labels = {
            vista.getLblimagen1(), vista.getLblimagen2(), vista.getLblimagen3()
        };

        JButton[] botones = {
            vista.getBtn1(), vista.getBtn2(), vista.getBtn3(),
            vista.getBtn4(), vista.getBtn5(), vista.getBtn6()
        };

        // Eventos para las imágenes
        for (JLabel lbl : labels) {
            lbl.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    // Evitar que se seleccione una imagen ya asociada
                    if (asociaciones.containsKey(lbl)) {
                        vista.mostrarMensaje("Esta imagen ya tiene una palabra asignada.");
                        return;
                    }

                    // Limpiar bordes anteriores
                    for (JLabel l : labels) {
                        l.setBorder(null);
                    }

                    imagenSeleccionada = lbl;
                    lbl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLUE, 3));
                    vista.mostrarMensaje("Imagen seleccionada. Ahora elige una palabra.");
                }
            });
        }

        // Eventos para los botones
        for (JButton btn : botones) {
            btn.addActionListener(e -> {
                if (imagenSeleccionada != null) {
                    // Asociar palabra
                    asociaciones.put(imagenSeleccionada, btn.getText());

                    // Cambiar color del botón y desactivarlo
                    btn.setBackground(java.awt.Color.GREEN);
                    btn.setEnabled(false);

                    // Limpiar selección de imagen
                    imagenSeleccionada.setBorder(null);
                    imagenSeleccionada = null;
                    vista.mostrarMensaje("Palabra asociada correctamente.");
                } else {
                    vista.mostrarMensaje("Primero selecciona una imagen.");
                }
            });
        }
    }

    /**
     * Agrega los eventos principales del controlador.
     */
    private void agregarEventos() {
        // Evento para el botón verificar
        vista.getBtnVerificar().addActionListener(e -> verificarResultadosJuego());
    }

    /**
     * Verifica los resultados del juego y muestra la puntuación.
     */
    private void verificarResultadosJuego() {
        if (asociaciones.size() < paresCorrectos.size()) {
            vista.mostrarMensaje("Debes asociar todas las imágenes antes de verificar.");
            return;
        }

        int correctos = 0;
        StringBuilder resultado = new StringBuilder();

        for (Map.Entry<JLabel, String> entry : asociaciones.entrySet()) {
            JLabel label = entry.getKey();
            String seleccion = entry.getValue();
            String correcta = (String) label.getClientProperty("palabra");

            if (seleccion.equalsIgnoreCase(correcta)) {
                correctos++;
                resultado.append("✅ Correcto: ").append(correcta).append("\n");
            } else {
                resultado.append("❌ Incorrecto: seleccionaste ").append(seleccion)
                        .append(", era ").append(correcta).append("\n");
            }
        }

        resultado.append("\nTotal correctos: ").append(correctos).append(" de ").append(paresCorrectos.size());

        // Determinar si la actividad se completó exitosamente
        boolean aprobado = correctos >= (paresCorrectos.size() * 0.7); // 70% para aprobar

        if (aprobado) {
            juegoCompletado = true;
            resultado.append("\n\n🎉 ¡Felicitaciones! Has completado la actividad.");

            // Actualizar progreso
            actualizarProgresoActividad();
        } else {
            resultado.append("\n\n💪 Necesitas al menos ").append((int) (paresCorrectos.size() * 0.7))
                    .append(" respuestas correctas para aprobar.");
        }

        int opcion = JOptionPane.showOptionDialog(
                vista,
                resultado.toString(),
                aprobado ? "¡Actividad Completada!" : "Resultado",
                JOptionPane.YES_NO_OPTION,
                aprobado ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{"🔁 Repetir", aprobado ? "✅ Continuar" : "❌ Cancelar"},
                aprobado ? "✅ Continuar" : "🔁 Repetir"
        );

        if (opcion == JOptionPane.YES_OPTION && !aprobado) {
            // Repetir el juego
            reiniciarEstadosJuego();
            cargarJuegoActividad3();
        } else if (aprobado) {
            // Regresar a la unidad
            navegarAUnidad1();
        }
    }

    /**
     * Actualiza el progreso de la actividad en la base de datos.
     */
    private void actualizarProgresoActividad() {
        try {
            LOGGER.info("Actualizando progreso de actividad " + idActividad);

            // Obtener progreso actual del usuario
            int idUsuario = Usuario.obtenerIdPorCorreo(correo);
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, 1); // Unidad 1

            if (progreso != null) {
                // Verificar si esta actividad ya fue completada
                if (progreso.getActividadesCompletadas() >= idActividad) {
                    int respuesta = JOptionPane.showConfirmDialog(
                            vista,
                            "Ya has completado esta actividad. ¿Quieres marcarla como completada nuevamente?",
                            "Actividad ya completada",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (respuesta != JOptionPane.YES_OPTION) {
                        return;
                    }
                }

                // Actualizar progreso solo si es necesario
                if (progreso.getActividadesCompletadas() < idActividad) {
                    progreso.setActividadesCompletadas(idActividad);
                    progreso.setFechaActualizacion(java.time.LocalDateTime.now());

                    boolean actualizado = Modelo_Progreso_Usuario.actualizarProgreso(progreso);

                    if (actualizado) {
                        LOGGER.info("Progreso actualizado exitosamente");
                        mostrarMensajeExito("¡Actividad completada! Tu progreso ha sido guardado.");

                        // Notificar al controlador de la unidad para actualizar la interfaz
                        if (controladorUnidad1 != null) {
                            SwingUtilities.invokeLater(() -> {
                                try {
                                    controladorUnidad1.actualizarVista();
                                } catch (Exception e) {
                                    LOGGER.log(Level.WARNING, "Error al actualizar vista de unidad", e);
                                }
                            });
                        }
                    } else {
                        LOGGER.warning("No se pudo actualizar el progreso");
                        mostrarError("No se pudo guardar el progreso. Intenta nuevamente.");
                    }
                } else {
                    LOGGER.info("Actividad ya estaba completada, no se actualiza progreso");
                }
            } else {
                LOGGER.warning("No se encontró progreso para el usuario");
                mostrarError("Error al obtener el progreso del usuario");
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar progreso de actividad", e);
            mostrarError("Error al guardar el progreso: " + e.getMessage());
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
            JOptionPane.showMessageDialog(
                    vista,
                    mensaje,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
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
            JOptionPane.showMessageDialog(
                    vista,
                    mensaje,
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }
}
