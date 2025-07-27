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
import java.awt.Image;

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
    private List<Modelo_Emparejar.EmparejarItem> paresParaMostrar; // Los 3 pares que se muestran en el juego
    private final Map<JLabel, String> asociaciones = new HashMap<>();
    private JLabel imagenSeleccionada = null;
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

        // Instrucciones eliminadas - solo se muestran en la bienvenida de la unidad

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

            // 2. Cargar datos separando pares correctos y distractores
            cargarDatosEmparejarCompletos(idActividadEmparejar);
            
            if (paresCorrectos == null || paresCorrectos.isEmpty()) {
                mostrarError("No se encontraron pares correctos para la actividad de emparejar.");
                return;
            }

            // 5. Validar que hay suficientes datos para ejecutar la actividad
            if (!validarDatosActividad()) {
                LOGGER.severe("Validación de datos fallida - no se puede ejecutar la actividad");
                return; // Salir si no hay datos suficientes
            }

            // 6. Configurar elementos visuales del juego
            configurarElementosJuego();

            // 7. Agregar eventos del juego
            agregarEventosJuego();

            LOGGER.info("Juego de asociación cargado exitosamente desde base de datos");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar el juego de asociación", e);
            mostrarError("Error al cargar el juego: " + e.getMessage());
        }
    }

    /**
     * Obtiene el ID de la actividad de emparejar para la Actividad 3.
     * Busca directamente el ID 6 que corresponde a los datos existentes.
     */
    private int obtenerIdActividadEmparejar(int idUnidad) {
        // Primero intentar con la consulta estándar
        String sql = "SELECT id_actividad FROM actividades WHERE tipo = 'emparejar' AND id_unidad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUnidad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idActividad = rs.getInt("id_actividad");
                LOGGER.info("Encontrada actividad de emparejar con ID: " + idActividad + " para unidad: " + idUnidad);
                return idActividad;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error al consultar actividades por tipo", e);
        }
        
        // Si no encuentra por tipo, buscar directamente los datos conocidos (ID 6)
        String sqlDirecto = "SELECT DISTINCT id_actividad FROM emparejar_opciones WHERE id_actividad = 6";
        try (PreparedStatement ps = conn.prepareStatement(sqlDirecto)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int idActividad = rs.getInt("id_actividad");
                LOGGER.info("Encontrados datos de emparejar con ID: " + idActividad + " (búsqueda directa)");
                return idActividad;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar datos de emparejar", e);
        }
        
        LOGGER.warning("No se encontraron datos de emparejar para la actividad 3");
        mostrarError("No se encontraron datos para la actividad de emparejar.\n" +
                    "Verifica que existan registros en la tabla 'emparejar_opciones'.");
        return -1;
    }
    
    /**
     * Carga los datos de emparejar desde la base de datos separando pares correctos y distractores.
     * Pares correctos: tienen texto_destino (ruta imagen)
     * Distractores: tienen texto_destino = NULL
     */
    private void cargarDatosEmparejarCompletos(int idActividad) {
        paresCorrectos = new ArrayList<>();
        distractores = new ArrayList<>();
        
        String sql = "SELECT texto_origen, texto_destino FROM emparejar_opciones WHERE id_actividad = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idActividad);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String palabra = rs.getString("texto_origen");
                String rutaImagen = rs.getString("texto_destino");
                
                Modelo_Emparejar.EmparejarItem item = new Modelo_Emparejar.EmparejarItem(
                    0,                    // id
                    idActividad,          // idActividad
                    palabra,              // textoOrigen - La palabra
                    null,                 // recursoOrigenUrl
                    palabra,              // textoDestino - La palabra para botones
                    rutaImagen            // recursoDestinoUrl - La ruta de la imagen (puede ser null)
                );
                
                // Separar entre pares correctos (con imagen) y distractores (sin imagen)
                if (rutaImagen != null && !rutaImagen.trim().isEmpty()) {
                    paresCorrectos.add(item);
                    LOGGER.info("Par correcto: " + palabra + " -> " + rutaImagen);
                } else {
                    distractores.add(item);
                    LOGGER.info("Distractor: " + palabra);
                }
            }
            
            LOGGER.info("Cargados " + paresCorrectos.size() + " pares correctos y " + 
                       distractores.size() + " distractores para actividad " + idActividad);
            
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al cargar datos de emparejar", e);
            mostrarError("Error al cargar los datos de la actividad: " + e.getMessage());
        }
    }
    
    /**
     * Valida que hay suficientes datos en la base de datos para ejecutar la actividad.
     */
    private boolean validarDatosActividad() {
        if (paresCorrectos == null || paresCorrectos.isEmpty()) {
            mostrarError("No hay pares correctos configurados en la base de datos.\n" +
                        "La tabla 'emparejar_opciones' debe tener registros para la actividad.");
            return false;
        }
        
        // Para la actividad de pronombres, necesitamos al menos 3 pares para mostrar
        if (paresCorrectos.size() < 3) {
            mostrarError("Se necesitan al menos 3 pares palabra-imagen para la actividad.\n" +
                        "Actualmente hay: " + paresCorrectos.size() + " pares configurados.");
            return false;
        }
        
        // Verificar que las imágenes tienen rutas válidas
        int paresConImagenes = 0;
        for (Modelo_Emparejar.EmparejarItem item : paresCorrectos) {
            if (item.recursoDestinoUrl != null && !item.recursoDestinoUrl.trim().isEmpty()) {
                paresConImagenes++;
            }
        }
        
        if (paresConImagenes == 0) {
            mostrarError("Ningún par tiene imágenes configuradas.\n" +
                        "Verifica que las rutas de imágenes estén correctas en la base de datos.");
            return false;
        }
        
        LOGGER.info("Validación exitosa: " + paresCorrectos.size() + " pares totales, " + 
                   paresConImagenes + " con imágenes válidas");
        return true;
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

        // Seleccionar solo 3 pares correctos para mostrar como imágenes
        Collections.shuffle(paresCorrectos);
        paresParaMostrar = new ArrayList<>(paresCorrectos.subList(0, Math.min(3, paresCorrectos.size())));
        
        // Configurar imágenes (solo 3)
        for (int i = 0; i < Math.min(paresParaMostrar.size(), labels.length); i++) {
            Modelo_Emparejar.EmparejarItem item = paresParaMostrar.get(i);
            
            try {
                // Configurar imagen usando getClass().getResource()
                String rutaImagen = item.recursoDestinoUrl; // Ruta desde BD
                System.out.println("[DEBUG] Intentando cargar imagen: " + rutaImagen);
                
                // Corregir ruta para usar la carpeta correcta
                String rutaCorregida = rutaImagen;
                if (!rutaImagen.startsWith("Imagenes/ImagenesUnidad1/")) {
                    // Si la ruta no tiene el prefijo correcto, agregarlo
                    rutaCorregida = "Imagenes/ImagenesUnidad1/" + rutaImagen.substring(rutaImagen.lastIndexOf("/") + 1);
                }
                
                URL imageUrl = getClass().getResource("/" + rutaCorregida);
                if (imageUrl != null) {
                    ImageIcon icon = new ImageIcon(imageUrl);
                    // Escalar imagen al tamaño del JLabel
                    if (labels[i].getWidth() > 0 && labels[i].getHeight() > 0) {
                        Image img = icon.getImage().getScaledInstance(
                            labels[i].getWidth(), labels[i].getHeight(), Image.SCALE_SMOOTH);
                        labels[i].setIcon(new ImageIcon(img));
                    } else {
                        labels[i].setIcon(icon);
                    }
                    System.out.println("[DEBUG] Imagen cargada exitosamente: " + rutaCorregida);
                } else {
                    System.err.println("[ERROR] No se pudo cargar la imagen: " + rutaCorregida);
                    // Intentar con ruta original como fallback
                    URL fallbackUrl = getClass().getResource("/" + rutaImagen);
                    if (fallbackUrl != null) {
                        ImageIcon icon = new ImageIcon(fallbackUrl);
                        if (labels[i].getWidth() > 0 && labels[i].getHeight() > 0) {
                            Image img = icon.getImage().getScaledInstance(
                                labels[i].getWidth(), labels[i].getHeight(), Image.SCALE_SMOOTH);
                            labels[i].setIcon(new ImageIcon(img));
                        } else {
                            labels[i].setIcon(icon);
                        }
                        System.out.println("[DEBUG] Imagen cargada con ruta fallback: " + rutaImagen);
                    } else {
                        labels[i].setText("Imagen no encontrada");
                        System.err.println("[ERROR] Imagen no encontrada en ninguna ruta: " + rutaImagen);
                    }
                }
                
                labels[i].setBorder(null); // quitar bordes previos
                labels[i].putClientProperty("palabra", item.textoDestino);
                
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error al cargar imagen para " + item.textoDestino, e);
                labels[i].setText("Error: " + item.textoDestino);
                labels[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            }
        }

        // Configurar botones con las palabras de los 3 pares seleccionados + 3 distractores
        List<String> palabras = new ArrayList<>();
        
        // Agregar las 3 palabras correctas (de los pares que se muestran como imágenes)
        for (Modelo_Emparejar.EmparejarItem item : paresParaMostrar) {
            palabras.add(item.textoOrigen);
        }
        
        // Agregar 3 distractores aleatorios
        Collections.shuffle(distractores);
        int distractoresAgregar = Math.min(3, distractores.size());
        for (int i = 0; i < distractoresAgregar; i++) {
            palabras.add(distractores.get(i).textoOrigen);
        }
        
        // Si no hay suficientes distractores, agregar palabras de otros pares correctos
        if (palabras.size() < 6) {
            for (Modelo_Emparejar.EmparejarItem item : paresCorrectos) {
                if (!palabras.contains(item.textoOrigen) && palabras.size() < 6) {
                    palabras.add(item.textoOrigen);
                }
            }
        }
        
        // Mezclar todas las palabras
        Collections.shuffle(palabras);

        // Configurar todos los botones
        for (int i = 0; i < botones.length && i < palabras.size(); i++) {
            botones[i].setText(palabras.get(i));
            botones[i].setEnabled(true);
            botones[i].setVisible(true);
            botones[i].setBackground(null); // quitar color de selección
        }
        
        // Ocultar botones extra si hay menos de 6 palabras
        for (int i = palabras.size(); i < botones.length; i++) {
            botones[i].setText("");
            botones[i].setEnabled(false);
            botones[i].setVisible(false);
        }
        
        LOGGER.info("Configurados " + botones.length + " botones con " + palabras.size() + " palabras");
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
        // Validar que se hayan asociado todas las imágenes mostradas (3)
        if (asociaciones.size() < paresParaMostrar.size()) {
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

        resultado.append("\nTotal correctos: ").append(correctos).append(" de ").append(paresParaMostrar.size());

        // Determinar si la actividad se completó exitosamente - DEBE SER 100% CORRECTO
        boolean aprobado = correctos == paresParaMostrar.size(); // Todas las respuestas deben ser correctas

        if (aprobado) {
            resultado.append("\n\n🎉 ¡Perfecto! Todas las respuestas son correctas.");
            resultado.append("\n✅ Has completado la actividad exitosamente.");

            // Actualizar progreso
            actualizarProgresoActividad();
        } else {
            resultado.append("\n\n💪 Debes tener TODAS las respuestas correctas para aprobar.");
            resultado.append("\nTienes ").append(correctos).append(" correctas de ").append(paresParaMostrar.size()).append(" necesarias.");
            resultado.append("\n🔁 Intenta nuevamente para corregir los errores.");
        }

        if (aprobado) {
            // Si aprobó, mostrar mensaje de éxito y permitir continuar
            int opcion = JOptionPane.showOptionDialog(
                    vista,
                    resultado.toString(),
                    "¡Actividad Completada!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"✅ Continuar", "🔁 Repetir"},
                    "✅ Continuar"
            );
            
            if (opcion == JOptionPane.NO_OPTION) {
                // Usuario quiere repetir aunque haya aprobado
                reiniciarEstadosJuego();
                cargarJuegoActividad3();
            } else {
                // Continuar - regresar a la unidad
                navegarAUnidad1();
            }
        } else {
            // Si no aprobó, solo permitir repetir
            int opcion = JOptionPane.showOptionDialog(
                    vista,
                    resultado.toString(),
                    "Resultado - Necesitas mejorar",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE,
                    null,
                    new String[]{"🔁 Intentar de nuevo", "❌ Salir"},
                    "🔁 Intentar de nuevo"
            );
            
            if (opcion == JOptionPane.YES_OPTION) {
                // Repetir el juego
                reiniciarEstadosJuego();
                cargarJuegoActividad3();
            } else {
                // Salir sin completar - regresar a la unidad sin actualizar progreso
                navegarAUnidad1();
            }
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
                    // JOptionPane eliminado para evitar diálogos excesivos
                    LOGGER.info("Actividad ya completada, no se actualiza progreso");
                    mostrarMensajeExito("¡Actividad ya completada anteriormente!");
                    
                    // Regresar a la unidad sin actualizar progreso
                    SwingUtilities.invokeLater(() -> {
                        navegarAUnidad1();
                    });
                    return;
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
        // JOptionPane eliminado para evitar diálogos excesivos
        LOGGER.severe("Error en Actividad 3: " + mensaje);
        // El error se muestra en los logs, no en diálogos
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     *
     * @param mensaje Mensaje de éxito a mostrar
     */
    private void mostrarMensajeExito(String mensaje) {
        // JOptionPane eliminado para evitar diálogos excesivos
        LOGGER.info("Éxito en Actividad 3: " + mensaje);
        // El éxito se refleja en la interfaz, no en diálogos
    }
    
    // Método de instrucciones eliminado - las instrucciones se muestran solo en la bienvenida de la unidad
}
