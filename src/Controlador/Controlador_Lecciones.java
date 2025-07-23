package Controlador;

import Vista.Vistas_Unidad1.Vista_LeccionFONOLOGIA;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Vista.Vistas_Unidad1.Vista_LeccionPRONOMBRES;
import Vista.Vistas_Unidad1.Vista_LeccionSALUDOS;
import Modelo.Modelo_Lecciones;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import Vista.*;
import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador actualizado para manejar el flujo de 3 páginas de lecciones
 */
public class Controlador_Lecciones {

    private final JPanel vistaLeccion;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int numeroLeccion;
    private final int idUsuario;
    private final int ID_UNIDAD = 1;
    private ImageIcon imagen1Original;
    private ImageIcon imagen2Original;
    private final Controlador_Unidades controladorUnidades;

    // Controlador de video
    private Controlador_Video controladorVideo;
    private boolean sonidoActivado = true;

    // Control de páginas
    private int paginaActual = 1;
    private final int TOTAL_PAGINAS = 3;

    // Estados de las páginas
    private boolean videoCompletado = false;
    private boolean contenidoVisualizadoPag2 = false;
    private boolean contenidoVisualizadoPag3 = false;

    // Tipos de lecciones
    public static final int LECCION_SALUDOS = 1;
    public static final int LECCION_FONOLOGIA = 2;
    public static final int LECCION_PRONOMBRES = 3;

    public Controlador_Lecciones(JPanel vistaLeccion, ControladorDashboard controladorDashboard,
            Connection conn, String correo, int numeroLeccion) {
        this.vistaLeccion = vistaLeccion;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }
        this.correo = correo.trim();

        this.numeroLeccion = numeroLeccion;

        // Obtener idUsuario a partir del correo
        this.idUsuario = Usuario.obtenerIdPorCorreo(this.correo);

        if (this.idUsuario <= 0) {
            throw new IllegalArgumentException("Usuario no encontrado con el correo: " + this.correo);
        }

        System.out.println("=== Inicializando Controlador_Lecciones ===");
        System.out.println("Lección: " + numeroLeccion + " | Usuario (correo): " + this.correo);

        this.controladorUnidades = null;

        configurarVistaLeccion();
        agregarListeners();
        inicializarPagina1();
    }

    private void configurarVistaLeccion() {
        System.out.println("Configurando vista de lección: " + numeroLeccion);
        configurarSegunTipoLeccion();
    }

    private void configurarSegunTipoLeccion() {
        switch (numeroLeccion) {
            case LECCION_SALUDOS:
                configurarLeccionSaludos();
                break;
            case LECCION_FONOLOGIA:
                configurarLeccionFonologia();
                break;
            case LECCION_PRONOMBRES:
                configurarLeccionPronombres();
                break;
        }
    }

    private void configurarLeccionSaludos() {
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

            // Configurar el CardLayout para navegación entre páginas
            vista.jPanelContenedor.setLayout(new CardLayout());
            vista.jPanelContenedor.add(vista.jPanelPag1, "PAGINA1");
            vista.jPanelContenedor.add(vista.jPanelPag2, "PAGINA2");
            vista.jPanelContenedor.add(vista.jPanelPag3, "PAGINA3");

            // Configurar botones iniciales
            vista.jButtonCOMPLETOSALUDOS.setText("COMPLETAR LECCIÓN DE SALUDOS");
            vista.jButtonSiguientePag.setText("Siguiente página");
            vista.jButtonSiguientePag2.setText("Siguiente Pag");

            // Inicializar controlador de video y controles de video
            if (controladorVideo == null) {
                controladorVideo = new Controlador_Video(vista.jPanelVideo);
                configurarEventosVideo();
                configurarControlesVideo(vista);
            }
        }
    }

    /**
     * Configura listeners y lógica para los controles de video (play, pausa,
     * volumen, progreso)
     */
   

    private void configurarControlesVideo(Vista_LeccionSALUDOS vista) {
        final boolean[] isSliderAdjusting = {false};

        // Play
        vista.jButtonPlay.addActionListener(e -> controladorVideo.reproducir());

        // Pausa
        vista.jButtonPausa.addActionListener(e -> controladorVideo.pausar());

        // Mute / Unmute
        vista.jButtonVoliumen.addActionListener(e -> {
            if (sonidoActivado) {
                controladorVideo.setVolume(0);
                sonidoActivado = false;
                vista.jButtonVoliumen.setText("Unmute");
            } else {
                controladorVideo.setVolume(1);
                sonidoActivado = true;
                vista.jButtonVoliumen.setText("Mute");
            }
        });

        // Control volumen con slider (valor entre 0 y 100)
        vista.jSliderSubirBajarVolumen.addChangeListener(e -> {
            int valor = vista.jSliderSubirBajarVolumen.getValue();
            double volumen = valor / 100.0;
            controladorVideo.setVolume(volumen);
            sonidoActivado = volumen > 0;
            vista.jButtonVoliumen.setText(sonidoActivado ? "Mute" : "Unmute");
        });

        // Detectar cuando usuario empieza y termina de arrastrar el slider de progreso
        vista.jSliderProgresoVideo.addChangeListener(e -> {
            if (vista.jSliderProgresoVideo.getValueIsAdjusting()) {
                isSliderAdjusting[0] = true;
            } else {
                if (isSliderAdjusting[0]) {
                    // Usuario terminó de mover el slider, hacer seek
                    int valor = vista.jSliderProgresoVideo.getValue();
                    double duracion = controladorVideo.getDurationSeconds();
                    if (duracion > 0) {
                        double nuevoTiempo = (valor / 100.0) * duracion;
                        controladorVideo.seek(nuevoTiempo);
                    }
                    isSliderAdjusting[0] = false;
                }
            }
        });

        // Actualizar slider y label de tiempo conforme avanza el video, solo si no se está arrastrando
        new Timer(500, e -> {
            if (controladorVideo != null && !isSliderAdjusting[0]) {
                double duracion = controladorVideo.getDurationSeconds();
                double actual = controladorVideo.getCurrentTimeSeconds();
                if (duracion > 0) {
                    int progreso = (int) ((actual / duracion) * 100);
                    vista.jSliderProgresoVideo.setValue(progreso);

                    String tiempoActual = formatoTiempo((int) actual);
                    String tiempoTotal = formatoTiempo((int) duracion);

                    vista.jLabelTiempoV.setText(tiempoActual + " / " + tiempoTotal);
                }
            }
        }).start();
    }

// Método auxiliar para convertir segundos a formato mm:ss
    private String formatoTiempo(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }

    private void configurarLeccionFonologia() {
        if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
            vista.jButtonCOMPLETOFONOLOGIA.setText("COMPLETAR LECCIÓN DE FONOLOGÍA");
        }
        
    }

    private void configurarLeccionPronombres() {
        if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
            vista.jButtonCOMPLETOPRONOMBRES.setText("COMPLETAR LECCIÓN DE PRONOMBRES");
        }
    }

    private void agregarListeners() {
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

            // Listener para botón "Siguiente página"
            vista.jButtonSiguientePag.addActionListener(e -> avanzarPagina());
            // ✅ Nuevo listener para avanzar desde página 2
            vista.jButtonSiguientePag2.addActionListener(e -> avanzarPagina());

            // Listener para botón "Completar lección"
            vista.jButtonCOMPLETOSALUDOS.addActionListener(e -> completarLeccion());

            // Listeners para redimensionar imágenes
            vista.jLabelImagen1Saludos.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    escalarYMostrarImagen(vista.jLabelImagen1Saludos, imagen1Original);
                }
            });

            vista.jLabelImagen2Saludos.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    escalarYMostrarImagen(vista.jLabelImagen2Saludos, imagen2Original);
                }
            });

        } else if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
            vista.jButtonCOMPLETOFONOLOGIA.addActionListener(e -> completarLeccion());

        } else if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
            vista.jButtonCOMPLETOPRONOMBRES.addActionListener(e -> completarLeccion());
        }
    }

    /**
     * PÁGINA 1: Inicializar la primera página con video
     */
    private void inicializarPagina1() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Mostrar mensaje inicial (puedes quitarlo si molesta)
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Para continuar con la lección, debes ver el video completo.",
                "Instrucciones - Página 1",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Deshabilitar botón hasta que termine el video
        vista.jButtonSiguientePag.setEnabled(false);

        // Mostrar página 1
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        cardLayout.show(vista.jPanelContenedor, "PAGINA1");

        // Liberar recursos previos del video (si existieran)
        if (controladorVideo != null) {
            controladorVideo.liberarRecursos();
            controladorVideo = null;
        }

        // Crear nuevo controladorVideo con el panel del video
        controladorVideo = new Controlador_Video(vista.jPanelVideo);

        // Configurar callback para cuando termine el video
        controladorVideo.setOnVideoCompletedCallback(() -> {
            videoCompletado = true;
            SwingUtilities.invokeLater(() -> {
                vista.jButtonSiguientePag.setEnabled(true);
                JOptionPane.showMessageDialog(vistaLeccion,
                        "¡Excelente! Has completado el video. Ahora puedes continuar a la siguiente página.",
                        "Video Completado",
                        JOptionPane.INFORMATION_MESSAGE);
            });
        });

        // Cargar video desde la base de datos y reproducir
        cargarVideoDesdeDB();
    }

private void cargarVideoDesdeDB() {
    try {
        List<Modelo_Lecciones> videos = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "video");

        if (!videos.isEmpty()) {
            String rutaRelativaBD = videos.get(0).getUrlMultimedia(); // "VideosU1/Wawa kichwa - Saludos y despedidas.mp4"
            
            // Opción 1: Para desarrollo (rutas de archivo)
            String rutaCompleta = "src/" + rutaRelativaBD; // Convierte a "src/VideosU1/Wawa kichwa - Saludos y despedidas.mp4"
            File archivoVideo = new File(rutaCompleta);
            
            // Opción 2: Para producción (cuando esté en un JAR)
            // URL videoUrl = getClass().getResource("/" + rutaRelativaBD);
            
            if (archivoVideo.exists()) {
                System.out.println("Cargando video desde: " + archivoVideo.getCanonicalPath());
                
                if (controladorVideo != null) {
                    controladorVideo.cargarVideo(archivoVideo.getAbsolutePath());
                }
            } else {
                System.err.println("El video no existe en la ruta: " + archivoVideo.getAbsolutePath());
                System.err.println("Buscando en: " + new File(".").getCanonicalPath()); // Muestra dónde está buscando
            }
        } else {
            System.out.println("No se encontraron videos en la BD");
            // Habilitar botón si es necesario
        }
    } catch (Exception e) {
        System.err.println("Error al cargar video: " + e.getMessage());
        e.printStackTrace();
    }
}
    private void configurarEventosVideo() {
        if (controladorVideo != null) {
            controladorVideo.setOnVideoCompletedCallback(() -> {
                videoCompletado = true;

                if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
                    Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;
                    // Habilitar botón "Siguiente página"
                    vista.jButtonSiguientePag.setEnabled(true);
                }

                // Mostrar mensaje de video completado (esto se ejecuta en el hilo Swing)
                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "¡Excelente! Has completado el video. Ahora puedes continuar a la siguiente página.",
                        "Video Completado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
        }
    }

    /**
     * PÁGINA 2: Mostrar contenido textual e imágenes
     */
    private void inicializarPagina2() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Mostrar mensaje inicial
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Lee y visualiza el contenido cuidadosamente antes de avanzar a la siguiente página.",
                "Instrucciones - Página 2",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Cargar contenido desde base de datos
        cargarContenidoPagina2();

        // Mostrar página 2
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        cardLayout.show(vista.jPanelContenedor, "PAGINA2");

        // Cambiar texto del botón
        vista.jButtonSiguientePag.setText("Siguiente página");
        vista.jButtonSiguientePag.setEnabled(true);

        // Marcar como visualizado
        contenidoVisualizadoPag2 = true;
    }

    private void cargarContenidoPagina2() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Obtener textos e imágenes
        List<Modelo_Lecciones> textos = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "texto");
        List<Modelo_Lecciones> imagenes = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "imagen");

        System.out.println("Cargando contenido Página 2:");
        System.out.println("Textos encontrados: " + textos.size());
        System.out.println("Imágenes encontradas: " + imagenes.size());

        // Cargar textos
        if (textos.size() > 0) {
            vista.jTextAreaTexto1.setText(textos.get(0).getContenido());
            vista.jTextAreaTexto1.setCaretPosition(0);
        }
        if (textos.size() > 1) {
            vista.jTextAreaTexto2Saludos.setText(textos.get(1).getContenido());
            vista.jTextAreaTexto2Saludos.setCaretPosition(0);
        }

        // Cargar imágenes
        cargarImagenes(vista);
    }

    private void cargarImagenes(Vista_LeccionSALUDOS vista) {
        // Cargar imagen 1
        String rutaImagen1 = "Imagenes/Lecciones/Imagen1Saludos.png";
        java.net.URL url1 = getClass().getClassLoader().getResource(rutaImagen1);
        if (url1 != null) {
            imagen1Original = new ImageIcon(url1);
            escalarYMostrarImagen(vista.jLabelImagen1Saludos, imagen1Original);
        }

        // Cargar imagen 2
        String rutaImagen2 = "Imagenes/Lecciones/Imagen2Saludos.png";
        java.net.URL url2 = getClass().getClassLoader().getResource(rutaImagen2);
        if (url2 != null) {
            imagen2Original = new ImageIcon(url2);
            escalarYMostrarImagen(vista.jLabelImagen2Saludos, imagen2Original);
        }
    }

    /**
     * PÁGINA 3: Actividad final y completar lección
     */
    private void inicializarPagina3() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Mostrar mensaje final
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Esta es la parte final de la lección. Revisa el contenido o realiza la actividad complementaria presentada.",
                "Instrucciones - Página 3",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Mostrar página 3
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        cardLayout.show(vista.jPanelContenedor, "PAGINA3");

        // Ocultar botón "Siguiente página"
        vista.jButtonSiguientePag.setVisible(false);

        // Mostrar botón "Completar lección"
        vista.jButtonCOMPLETOSALUDOS.setVisible(true);

        // Marcar como visualizado
        contenidoVisualizadoPag3 = true;
    }

    /**
     * Método para avanzar entre páginas
     */
    private void avanzarPagina() {
        System.out.println("Avanzando de página " + paginaActual + " a " + (paginaActual + 1));

        // Validar que se puede avanzar
        if (!puedeAvanzarPagina()) {
            return;
        }

        paginaActual++;

        switch (paginaActual) {
            case 2:
                inicializarPagina2();
                break;
            case 3:
                inicializarPagina3();
                break;
        }
    }

    private boolean puedeAvanzarPagina() {
        switch (paginaActual) {
            case 1:
                if (!videoCompletado) {
                    JOptionPane.showMessageDialog(
                            vistaLeccion,
                            "Debes completar el video antes de continuar.",
                            "Video no completado",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return false;
                }
                break;
            case 2:
                if (!contenidoVisualizadoPag2) {
                    JOptionPane.showMessageDialog(
                            vistaLeccion,
                            "Debes revisar el contenido antes de continuar.",
                            "Contenido no revisado",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return false;
                }
                break;
        }
        return true;
    }

    private void completarLeccion() {
        try {
            System.out.println("Completando lección " + numeroLeccion);

            // Obtener o crear progreso para el usuario
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);

            // Verificar si esta lección ya fue completada
            if (progreso.getLeccionesCompletadas() >= numeroLeccion) {
                int respuesta = JOptionPane.showConfirmDialog(
                        vistaLeccion,
                        "Ya has completado esta lección. ¿Quieres marcarla como completada nuevamente?",
                        "Lección ya completada",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Actualizar progreso
            boolean actualizado = ControladorProgresoUsuario.actualizarLeccion(progreso, numeroLeccion);

            if (actualizado || progreso.getLeccionesCompletadas() >= numeroLeccion) {
                mostrarMensajeExito();
                liberarRecursos();
                actualizarInterfaz();
            } else {
                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "Error al actualizar el progreso",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {
            System.err.println("Error al completar lección: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    vistaLeccion,
                    "Error inesperado al completar la lección: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarMensajeExito() {
        String nombreLeccion = obtenerNombreLeccion();
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "¡Felicitaciones! Has completado la " + nombreLeccion + " exitosamente.",
                "Lección Completada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String obtenerNombreLeccion() {
        switch (numeroLeccion) {
            case LECCION_SALUDOS:
                return "Lección de Saludos";
            case LECCION_FONOLOGIA:
                return "Lección de Fonología";
            case LECCION_PRONOMBRES:
                return "Lección de Pronombres";
            default:
                return "Lección " + numeroLeccion;
        }
    }

    private void liberarRecursos() {
        if (controladorVideo != null) {
            controladorVideo.liberarRecursos();
        }
    }

    private void actualizarInterfaz() {
        System.out.println("Actualizando interfaz - regresando a Unidad 1");

        // Crear nueva instancia de la vista de unidad
        Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
        new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo, controladorUnidades);
        controladorDashboard.getVista().mostrarVista(vistaUnidad1);
    }

    private void escalarYMostrarImagen(JLabel label, ImageIcon imagenOriginal) {
        int ancho = label.getWidth();
        int alto = label.getHeight();

        if (ancho > 0 && alto > 0 && imagenOriginal != null) {
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagenEscalada));
        }
    }

    // Método para obtener estadísticas de progreso
    public String obtenerEstadisticasProgreso() {
        Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);

        int totalLecciones = 3;
        int totalActividades = 2;

        double porcentajeLecciones = (double) progreso.getLeccionesCompletadas() / totalLecciones * 100;
        double porcentajeActividades = (double) progreso.getActividadesCompletadas() / totalActividades * 100;
        double porcentajeTotal = (porcentajeLecciones + porcentajeActividades) / 2;

        StringBuilder estadisticas = new StringBuilder();
        estadisticas.append("=== ESTADÍSTICAS DE PROGRESO ===\n");
        estadisticas.append("Usuario: ").append(correo).append("\n");
        estadisticas.append("Unidad: ").append(ID_UNIDAD).append("\n\n");
        estadisticas.append("Lecciones completadas: ").append(progreso.getLeccionesCompletadas())
                .append("/").append(totalLecciones).append(" (")
                .append(String.format("%.1f", porcentajeLecciones)).append("%)\n");
        estadisticas.append("Actividades completadas: ").append(progreso.getActividadesCompletadas())
                .append("/").append(totalActividades).append(" (")
                .append(String.format("%.1f", porcentajeActividades)).append("%)\n");
        estadisticas.append("Progreso total: ").append(String.format("%.1f", porcentajeTotal)).append("%\n\n");

        // Información adicional
        // Reemplaza con:
        estadisticas.append("Fecha última actividad: ").append(progreso.getFechaActualizacion()).append("\n");
// estadisticas.append("Tiempo total dedicado: ").append(progreso.getTiempoTotalMinutos()).append(" minutos\n"); // Comentada
        // estadisticas.append("Fecha última actividad: ").append(progreso.getFechaUltimaActividad()).append("\n");
        //estadisticas.append("Tiempo total dedicado: ").append(progreso.getTiempoTotalMinutos()).append(" minutos\n");

        return estadisticas.toString();
    }

    // Método para verificar si una lección específica está completada
    public boolean isLeccionCompletada(int numeroLeccion) {
        Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        return progreso.getLeccionesCompletadas() >= numeroLeccion;
    }

    // Método para obtener el progreso actual del usuario
    public Modelo_Progreso_Usuario obtenerProgresoActual() {
        return ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);
    }

    // Método para reiniciar el progreso de la lección actual
    public void reiniciarProgreso() {
        try {
            int respuesta = JOptionPane.showConfirmDialog(
                    vistaLeccion,
                    "¿Estás seguro de que quieres reiniciar el progreso de esta lección?",
                    "Confirmar reinicio",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                // Resetear estados
                videoCompletado = false;
                contenidoVisualizadoPag2 = false;
                contenidoVisualizadoPag3 = false;
                paginaActual = 1;

                // Reinicializar página 1
                inicializarPagina1();

                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "El progreso de la lección ha sido reiniciado.",
                        "Progreso reiniciado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception e) {
            System.err.println("Error al reiniciar progreso: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    vistaLeccion,
                    "Error al reiniciar el progreso: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Método para validar que todos los componentes están listos
    private boolean validarComponentes() {
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        if (vistaLeccion == null) {
            errores.append("- Vista de lección no inicializada\n");
            valido = false;
        }

        if (conn == null) {
            errores.append("- Conexión a base de datos no disponible\n");
            valido = false;
        }

        if (correo == null || correo.trim().isEmpty()) {
            errores.append("- Correo de usuario no válido\n");
            valido = false;
        }

        if (idUsuario <= 0) {
            errores.append("- ID de usuario no válido\n");
            valido = false;
        }

        if (!valido) {
            System.err.println("Errores de validación:\n" + errores.toString());
        }

        return valido;
    }

    // Método para limpiar recursos al cerrar
    public void limpiarRecursos() {
        try {
            if (controladorVideo != null) {
                controladorVideo.liberarRecursos();
                controladorVideo = null;
            }

            // Limpiar imágenes
            if (imagen1Original != null) {
                imagen1Original.getImage().flush();
                imagen1Original = null;
            }

            if (imagen2Original != null) {
                imagen2Original.getImage().flush();
                imagen2Original = null;
            }

            System.out.println("Recursos limpiados correctamente");
        } catch (Exception e) {
            System.err.println("Error al limpiar recursos: " + e.getMessage());
        }
    }

    // Getters para información del controlador
    public int getNumeroLeccion() {
        return numeroLeccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public boolean isVideoCompletado() {
        return videoCompletado;
    }

    public boolean isContenidoVisualizadoPag2() {
        return contenidoVisualizadoPag2;
    }

    public boolean isContenidoVisualizadoPag3() {
        return contenidoVisualizadoPag3;
    }

}
