package Controlador;

import Modelo.Juego_Emparejar;
import Modelo.Juego_Emparejar.DatosJuego;
import Modelo.Juego_Emparejar.ElementoAsociacion;
import Modelo.Juego_Emparejar;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vistas_Unidad1.Vista_Actividad3U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import java.sql.Connection;
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
 * Versión actualizada que usa Modelo_JuegoAsociacion.
 *
 * @author ProyectoInti
 * @version 2.0 - Refactorizado para usar modelo específico
 */
public class Controlador_Actv3U1 {

    private static final Logger LOGGER = Logger.getLogger(Controlador_Actv3U1.class.getName());
    private static final int PARES_A_MOSTRAR = 3;
    private static final int TOTAL_BOTONES = 6;

    // Componentes principales
    private final Vista_Actividad3U1 vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idActividad;
    private final Controlador_Unidad1 controladorUnidad1;

    // Variables para el juego de asociación
    private DatosJuego datosJuego;
    private List<ElementoAsociacion> elementosParaMostrar;
    private final Map<JLabel, String> asociaciones = new HashMap<>();
    private JLabel imagenSeleccionada = null;
    private boolean actividadInicializada = false;

    /**
     * Constructor del controlador de la actividad 3.
     */
    public Controlador_Actv3U1(Vista_Actividad3U1 vista, ControladorDashboard controladorDashboard,
            Connection conn, String correo, int idActividad, Controlador_Unidad1 controladorUnidad1) {
        
        validarParametros(vista, controladorDashboard, conn, correo, idActividad);

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
     * Valida los parámetros del constructor.
     */
    private void validarParametros(Vista_Actividad3U1 vista, ControladorDashboard controladorDashboard,
            Connection conn, String correo, int idActividad) {
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

            // Reiniciar estados del juego
            reiniciarEstadosJuego();

            // Cargar datos usando el nuevo modelo
            cargarDatosJuego();

            // Configurar el juego si los datos son válidos
            if (datosJuego != null && datosJuego.esValido()) {
                configurarJuego();
                LOGGER.info("Actividad 3 cargada exitosamente");
            } else {
                mostrarError("No hay suficientes datos para ejecutar la actividad. Se necesitan al menos 3 elementos.");
            }

        } catch (Exception e) {
            String errorMsg = "Error al cargar la actividad 3: " + e.getMessage();
            LOGGER.log(Level.SEVERE, errorMsg, e);
            mostrarError(errorMsg);
        }
    }

    /**
     * Carga los datos del juego usando el modelo específico.
     */
    private void cargarDatosJuego() {
        try {
            LOGGER.info("Cargando datos del juego desde la base de datos...");
            
            datosJuego = Juego_Emparejar.cargarDatosJuego(conn, 1); // Unidad 1
            
            if (datosJuego == null) {
                throw new RuntimeException("No se pudieron cargar los datos del juego");
            }

            LOGGER.info("Datos del juego cargados: " + datosJuego.getTotalElementos() + 
                       " elementos correctos, " + datosJuego.getTotalDistractores() + " distractores");

            // Log de información detallada para debugging
            LOGGER.info(Juego_Emparejar.obtenerInformacionDatos(conn, 1));

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al cargar datos del juego", e);
            throw new RuntimeException("Error al cargar datos del juego: " + e.getMessage(), e);
        }
    }

    /**
     * Configura todos los elementos del juego.
     */
    private void configurarJuego() {
        seleccionarElementosParaMostrar();
        configurarImagenes();
        configurarBotones();
        agregarEventosJuego();
    }

    /**
     * Selecciona aleatoriamente los elementos que se mostrarán en el juego.
     */
    private void seleccionarElementosParaMostrar() {
        List<ElementoAsociacion> elementosDisponibles = new ArrayList<>(datosJuego.getElementosCorrectos());
        Collections.shuffle(elementosDisponibles);
        
        int elementosAMostrar = Math.min(PARES_A_MOSTRAR, elementosDisponibles.size());
        elementosParaMostrar = new ArrayList<>(elementosDisponibles.subList(0, elementosAMostrar));
        
        LOGGER.info("Seleccionados " + elementosParaMostrar.size() + " elementos para mostrar");
    }

    /**
     * Configura las imágenes en los JLabels.
     */
    private void configurarImagenes() {
        JLabel[] labels = {
            vista.getLblimagen1(), 
            vista.getLblimagen2(), 
            vista.getLblimagen3()
        };

        for (int i = 0; i < elementosParaMostrar.size() && i < labels.length; i++) {
            ElementoAsociacion elemento = elementosParaMostrar.get(i);
            JLabel label = labels[i];
            
            configurarImagenEnLabel(label, elemento);
        }
    }

    /**
     * Configura una imagen individual en un JLabel.
     */
    private void configurarImagenEnLabel(JLabel label, ElementoAsociacion elemento) {
        try {
            String rutaImagen = elemento.getRutaImagen();
            LOGGER.info("Cargando imagen: " + rutaImagen);
            
            ImageIcon icon = cargarImagenConRutasAlternativas(rutaImagen);
            
            if (icon != null) {
                // Escalar imagen al tamaño del JLabel si es necesario
                if (label.getWidth() > 0 && label.getHeight() > 0) {
                    Image img = icon.getImage().getScaledInstance(
                        label.getWidth(), label.getHeight(), Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(img));
                } else {
                    label.setIcon(icon);
                }
                LOGGER.info("Imagen cargada exitosamente: " + rutaImagen);
            } else {
                label.setText("Imagen no encontrada");
                label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                LOGGER.warning("No se pudo cargar imagen: " + rutaImagen);
            }
            
            // Configurar propiedades del label
            label.setBorder(null);
            label.putClientProperty("palabra", elemento.getPalabraCorrecta());
            
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error al configurar imagen para " + elemento.getPalabraCorrecta(), e);
            label.setText("Error: " + elemento.getPalabraCorrecta());
            label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }
    }

    /**
     * Configura los botones con las palabras (correctas e incorrectas).
     */
    private void configurarBotones() {
        JButton[] botones = {
            vista.getBtn1(), vista.getBtn2(), vista.getBtn3(),
            vista.getBtn4(), vista.getBtn5(), vista.getBtn6()
        };

        List<String> palabras = generarListaPalabras();
        Collections.shuffle(palabras);

        // Configurar botones con las palabras
        for (int i = 0; i < botones.length; i++) {
            JButton boton = botones[i];
            
            if (i < palabras.size()) {
                boton.setText(palabras.get(i));
                boton.setEnabled(true);
                boton.setVisible(true);
                boton.setBackground(null);
            } else {
                // Ocultar botones extra
                boton.setText("");
                boton.setEnabled(false);
                boton.setVisible(false);
            }
        }
        
        LOGGER.info("Configurados " + palabras.size() + " botones para " + elementosParaMostrar.size() + " imágenes");
    }

    /**
     * Genera la lista de palabras que aparecerán en los botones.
     */
    private List<String> generarListaPalabras() {
        List<String> palabras = new ArrayList<>();
        
        // Agregar las palabras correctas de los elementos mostrados
        for (ElementoAsociacion elemento : elementosParaMostrar) {
            palabras.add(elemento.getPalabraCorrecta());
        }
        
        // Agregar distractores hasta completar 6 botones
        List<String> distractoresDisponibles = new ArrayList<>(datosJuego.getDistractores());
        Collections.shuffle(distractoresDisponibles);
        
        int espaciosRestantes = TOTAL_BOTONES - palabras.size();
        int distractoresAgregar = Math.min(espaciosRestantes, distractoresDisponibles.size());
        
        for (int i = 0; i < distractoresAgregar; i++) {
            String distractor = distractoresDisponibles.get(i);
            if (!palabras.contains(distractor)) {
                palabras.add(distractor);
            }
        }
        
        // Si aún faltan palabras, usar otras palabras correctas no mostradas
        if (palabras.size() < TOTAL_BOTONES) {
            for (ElementoAsociacion elemento : datosJuego.getElementosCorrectos()) {
                if (!palabras.contains(elemento.getPalabraCorrecta()) && palabras.size() < TOTAL_BOTONES) {
                    palabras.add(elemento.getPalabraCorrecta());
                }
            }
        }
        
        return palabras;
    }

    /**
     * Intenta cargar una imagen probando diferentes rutas posibles.
     */
    private ImageIcon cargarImagenConRutasAlternativas(String rutaOriginal) {
        if (rutaOriginal == null || rutaOriginal.trim().isEmpty()) {
            return null;
        }
        
        String nombreArchivo = extraerNombreArchivo(rutaOriginal);
        String[] rutasAProbrar = {
            rutaOriginal,                                    // Ruta original completa
            "/" + rutaOriginal,                              // Con barra inicial
            rutaOriginal.replace("src/", "/"),               // Quitar src/ y agregar /
            "/Imagenes/ImagenesUnidad1/" + nombreArchivo,    // Ruta estándar sin src/
            "Imagenes/ImagenesUnidad1/" + nombreArchivo      // Ruta estándar sin barras
        };
        
        for (String ruta : rutasAProbrar) {
            try {
                URL imageUrl = getClass().getResource(ruta);
                if (imageUrl != null) {
                    LOGGER.info("Imagen encontrada en: " + ruta);
                    return new ImageIcon(imageUrl);
                }
            } catch (Exception e) {
                LOGGER.log(Level.FINE, "Error al probar ruta: " + ruta, e);
            }
        }
        
        LOGGER.warning("No se pudo encontrar la imagen en ninguna ruta: " + rutaOriginal);
        return null;
    }
    
    /**
     * Extrae solo el nombre del archivo de una ruta completa.
     */
    private String extraerNombreArchivo(String ruta) {
        if (ruta == null) return "";
        int ultimaBarra = Math.max(ruta.lastIndexOf('/'), ruta.lastIndexOf('\\'));
        return ultimaBarra >= 0 ? ruta.substring(ultimaBarra + 1) : ruta;
    }

    /**
     * Reinicia los estados del juego de asociación.
     */
    private void reiniciarEstadosJuego() {
        asociaciones.clear();
        imagenSeleccionada = null;
        elementosParaMostrar = null;
        datosJuego = null;
        LOGGER.info("Estados del juego reiniciados");
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
                    manejarSeleccionImagen(lbl, labels);
                }
            });
        }

        // Eventos para los botones
        for (JButton btn : botones) {
            btn.addActionListener(e -> manejarSeleccionPalabra(btn));
        }
    }

    /**
     * Maneja la selección de una imagen.
     */
    private void manejarSeleccionImagen(JLabel labelSeleccionado, JLabel[] todosLosLabels) {
        // Evitar que se seleccione una imagen ya asociada
        if (asociaciones.containsKey(labelSeleccionado)) {
            vista.mostrarMensaje("Esta imagen ya tiene una palabra asignada.");
            return;
        }

        // Limpiar bordes anteriores
        for (JLabel lbl : todosLosLabels) {
            lbl.setBorder(null);
        }

        imagenSeleccionada = labelSeleccionado;
        labelSeleccionado.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLUE, 3));
        vista.mostrarMensaje("Imagen seleccionada. Ahora elige una palabra.");
    }

    /**
     * Maneja la selección de una palabra.
     */
    private void manejarSeleccionPalabra(JButton boton) {
        if (imagenSeleccionada != null) {
            // Asociar palabra
            asociaciones.put(imagenSeleccionada, boton.getText());

            // Cambiar color del botón y desactivarlo
            boton.setBackground(java.awt.Color.GREEN);
            boton.setEnabled(false);

            // Limpiar selección de imagen
            imagenSeleccionada.setBorder(null);
            imagenSeleccionada = null;
            vista.mostrarMensaje("Palabra asociada correctamente.");
        } else {
            vista.mostrarMensaje("Primero selecciona una imagen.");
        }
    }

    /**
     * Agrega los eventos principales del controlador.
     */
    private void agregarEventos() {
        vista.getBtnVerificar().addActionListener(e -> verificarResultadosJuego());
    }

    /**
     * Verifica los resultados del juego y muestra la puntuación.
     */
    private void verificarResultadosJuego() {
        if (!validarAsociacionesCompletas()) {
            return;
        }

        ResultadoJuego resultado = calcularResultados();
        mostrarResultados(resultado);
    }

    /**
     * Valida que todas las imágenes mostradas tengan una palabra asociada.
     */
    private boolean validarAsociacionesCompletas() {
        if (asociaciones.size() < elementosParaMostrar.size()) {
            vista.mostrarMensaje("Debes asociar todas las imágenes antes de verificar.");
            return false;
        }
        return true;
    }

    /**
     * Calcula los resultados del juego.
     */
    private ResultadoJuego calcularResultados() {
        int correctos = 0;
        StringBuilder detalles = new StringBuilder();

        for (Map.Entry<JLabel, String> entry : asociaciones.entrySet()) {
            JLabel label = entry.getKey();
            String seleccion = entry.getValue();
            String correcta = (String) label.getClientProperty("palabra");

            if (seleccion.equalsIgnoreCase(correcta)) {
                correctos++;
                detalles.append("✅ Correcto: ").append(correcta).append("\n");
            } else {
                detalles.append("❌ Incorrecto: seleccionaste ").append(seleccion)
                        .append(", era ").append(correcta).append("\n");
            }
        }

        int total = elementosParaMostrar.size();
        boolean aprobado = correctos == total; // Debe ser 100% correcto

        return new ResultadoJuego(correctos, total, aprobado, detalles.toString());
    }

    /**
     * Muestra los resultados del juego al usuario.
     */
    private void mostrarResultados(ResultadoJuego resultado) {
        StringBuilder mensaje = new StringBuilder(resultado.detalles);
        mensaje.append("\nTotal correctos: ").append(resultado.correctos)
               .append(" de ").append(resultado.total);

        if (resultado.aprobado) {
            mensaje.append("\n\n🎉 ¡Perfecto! Todas las respuestas son correctas.");
            mensaje.append("\n✅ Has completado la actividad exitosamente.");
            
            mostrarDialogoAprobado(mensaje.toString());
            actualizarProgresoActividad();
        } else {
            mensaje.append("\n\n💪 Debes tener TODAS las respuestas correctas para aprobar.");
            mensaje.append("\nTienes ").append(resultado.correctos)
                   .append(" correctas de ").append(resultado.total).append(" necesarias.");
            mensaje.append("\n🔁 Intenta nuevamente para corregir los errores.");
            
            mostrarDialogoNoAprobado(mensaje.toString());
        }
    }

    /**
     * Muestra el diálogo cuando el usuario aprueba la actividad.
     */
    private void mostrarDialogoAprobado(String mensaje) {
        int opcion = JOptionPane.showOptionDialog(
                vista,
                mensaje,
                "¡Actividad Completada!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"✅ Continuar", "🔁 Repetir"},
                "✅ Continuar"
        );
        
        if (opcion == JOptionPane.NO_OPTION) {
            // Usuario quiere repetir aunque haya aprobado
            reiniciarYRecargarJuego();
        } else {
            // Continuar - regresar a la unidad
            navegarAUnidad1();
        }
    }

    /**
     * Muestra el diálogo cuando el usuario no aprueba la actividad.
     */
    private void mostrarDialogoNoAprobado(String mensaje) {
        int opcion = JOptionPane.showOptionDialog(
                vista,
                mensaje,
                "Resultado - Necesitas mejorar",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{"🔁 Intentar de nuevo", "❌ Salir"},
                "🔁 Intentar de nuevo"
        );
        
        if (opcion == JOptionPane.YES_OPTION) {
            // Repetir el juego
            reiniciarYRecargarJuego();
        } else {
            // Salir sin completar - regresar a la unidad sin actualizar progreso
            navegarAUnidad1();
        }
    }

    /**
     * Reinicia el juego y lo recarga con nuevos datos.
     */
    private void reiniciarYRecargarJuego() {
        reiniciarEstadosJuego();
        cargarActividad();
    }

    /**
     * Actualiza el progreso de la actividad en la base de datos.
     */
    private void actualizarProgresoActividad() {
        try {
            LOGGER.info("Actualizando progreso de actividad " + idActividad);

            int idUsuario = Usuario.obtenerIdPorCorreo(correo);
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, 1); // Unidad 1

            if (progreso != null) {
                // Verificar si esta actividad ya fue completada
                if (progreso.getActividadesCompletadas() >= idActividad) {
                    LOGGER.info("Actividad ya completada, no se actualiza progreso");
                    mostrarMensajeExito("¡Actividad ya completada anteriormente!");
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
     */
    private void mostrarError(String mensaje) {
        LOGGER.severe("Error en Actividad 3: " + mensaje);
        // El error se muestra en los logs para evitar diálogos excesivos
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     */
    private void mostrarMensajeExito(String mensaje) {
        LOGGER.info("Éxito en Actividad 3: " + mensaje);
        // El éxito se refleja en la interfaz, no en diálogos
    }

    /**
     * Clase interna para encapsular los resultados del juego.
     */
    private static class ResultadoJuego {
        final int correctos;
        final int total;
        final boolean aprobado;
        final String detalles;

        ResultadoJuego(int correctos, int total, boolean aprobado, String detalles) {
            this.correctos = correctos;
            this.total = total;
            this.aprobado = aprobado;
            this.detalles = detalles;
        }
    }
}