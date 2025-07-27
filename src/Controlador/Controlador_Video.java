package Controlador;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Controlador_Video {

    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private final JPanel contenedorVideo;
    private Scene placeholderScene;
    private StackPane videoRoot;
    private boolean isInitialized = false;
    private boolean isDisposed = false;
    private String currentVideoPath;
    private Runnable onVideoCompletedCallback;
    
    // Estados para evitar conflictos de recarga
    private volatile boolean isLoadingVideo = false;
    private volatile boolean needsReload = false;

    public Controlador_Video(JPanel contenedorVideo) {
        this.contenedorVideo = contenedorVideo;
        this.fxPanel = new JFXPanel();
        configurarContenedor();
        Platform.runLater(this::initializeFX);
        
        // Registrar en el gestor global de videos
        VideoManager.getInstance().registrarControlador(this);
    }

    private void configurarContenedor() {
        try {
            // Asegurar que el contenedor tenga un tamaño mínimo
            contenedorVideo.setPreferredSize(new Dimension(640, 480));
            contenedorVideo.setMinimumSize(new Dimension(320, 240));
            
            // Configurar layout y agregar el panel FX
            contenedorVideo.setLayout(new BorderLayout());
            contenedorVideo.removeAll();
            
            // Configurar el JFXPanel
            fxPanel.setPreferredSize(new Dimension(640, 480));
            fxPanel.setMinimumSize(new Dimension(320, 240));
            
            contenedorVideo.add(fxPanel, BorderLayout.CENTER);
            contenedorVideo.revalidate();
            contenedorVideo.repaint();
            
            System.out.println("Contenedor de video configurado - Tamaño: " + contenedorVideo.getPreferredSize());
            
        } catch (Exception e) {
            System.err.println("Error al configurar contenedor de video: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initializeFX() {
        try {
            // Crear placeholder con tamaño fijo
            Label placeholderLabel = new Label("Cargando video...");
            placeholderLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
            
            StackPane placeholderRoot = new StackPane(placeholderLabel);
            placeholderRoot.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #cccccc;");
            
            placeholderScene = new Scene(placeholderRoot, 640, 480);
            fxPanel.setScene(placeholderScene);
            
            isInitialized = true;
            System.out.println("JavaFX inicializado correctamente para video");
            
        } catch (Exception e) {
            System.err.println("Error al inicializar JavaFX: " + e.getMessage());
            e.printStackTrace();
            isInitialized = true; // Marcar como inicializado para evitar bloqueos
        }
    }

    /**
     * Carga un video de forma segura, evitando conflictos de recarga.
     * 
     * @param rutaVideo Ruta del archivo de video a cargar
     */
    public void cargarVideo(String rutaVideo) {
        if (isDisposed) {
            System.err.println("Controlador de video ya fue liberado, no se puede cargar video");
            return;
        }
        
        if (isLoadingVideo) {
            needsReload = true;
            currentVideoPath = rutaVideo;
            return;
        }
        
        if (!isInitialized) {
            Platform.runLater(() -> cargarVideo(rutaVideo));
            return;
        }

        isLoadingVideo = true;
        currentVideoPath = rutaVideo;
        
        Platform.runLater(() -> {
            try {
                File videoFile = new File(rutaVideo);
                if (!videoFile.exists()) {
                    mostrarError("Archivo no encontrado: " + rutaVideo);
                    isLoadingVideo = false;
                    return;
                }

                // Liberar recursos anteriores de forma segura
                liberarRecursosMediaPlayerSeguro();

                // Crear nuevo reproductor
                Media media = new Media(videoFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                configurarEventosReproductor();

                // Configurar vista
                mediaView = new MediaView(mediaPlayer);
                videoRoot = new StackPane(mediaView);
                configurarMediaView();

                // Establecer escena
                Scene videoScene = new Scene(videoRoot);
                fxPanel.setScene(videoScene);
                
                // Forzar actualización visual
                forzarActualizacionVisual();
                
                isLoadingVideo = false;
                
                // Si hay una recarga pendiente, ejecutarla
                if (needsReload && !currentVideoPath.equals(rutaVideo)) {
                    needsReload = false;
                    cargarVideo(currentVideoPath);
                }
                
            } catch (Exception e) {
                isLoadingVideo = false;
                mostrarError("Error al cargar video: " + e.getMessage());
            }
        });
    }

    private void configurarMediaView() {
        try {
            // Configuración básica del MediaView
            mediaView.setPreserveRatio(true);
            mediaView.setSmooth(true);
            
            // Configurar tamaño fijo inicial para evitar problemas de binding
            mediaView.setFitWidth(640);
            mediaView.setFitHeight(480);
            
            // Asegurar que el MediaView sea visible
            mediaView.setVisible(true);
            mediaView.setManaged(true);
            
            // Configurar el StackPane contenedor
            videoRoot.setPrefSize(640, 480);
            videoRoot.setMinSize(320, 240);
            
            // Binding dinámico solo después de la configuración inicial
            Platform.runLater(() -> {
                try {
                    if (videoRoot != null && mediaView != null) {
                        mediaView.fitWidthProperty().bind(videoRoot.widthProperty());
                        mediaView.fitHeightProperty().bind(videoRoot.heightProperty());
                    }
                } catch (Exception e) {
                    System.err.println("Error en binding dinámico: " + e.getMessage());
                }
            });
            
            System.out.println("MediaView configurado correctamente - Tamaño: " + mediaView.getFitWidth() + "x" + mediaView.getFitHeight());
            
        } catch (Exception e) {
            System.err.println("Error al configurar MediaView: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Fuerza la actualización visual del video para corregir problemas de renderizado
     */
    private void forzarActualizacionVisual() {
        try {
            if (videoRoot != null && mediaView != null) {
                // Forzar repaint del contenedor Swing
                SwingUtilities.invokeLater(() -> {
                    contenedorVideo.revalidate();
                    contenedorVideo.repaint();
                });
                
                // Forzar actualización del MediaView en JavaFX
                Platform.runLater(() -> {
                    try {
                        if (mediaView != null) {
                            mediaView.setVisible(false);
                            mediaView.setVisible(true);
                            
                            // Forzar recalculo de layout
                            if (videoRoot != null) {
                                videoRoot.requestLayout();
                                videoRoot.applyCss();
                                videoRoot.layout();
                            }
                        }
                    } catch (Exception e) {
                        System.err.println("Error en actualización visual JavaFX: " + e.getMessage());
                    }
                });
                
                System.out.println("Actualización visual forzada para corregir renderizado de video");
            }
        } catch (Exception e) {
            System.err.println("Error al forzar actualización visual: " + e.getMessage());
        }
    }

    /**
     * Reinicia completamente el controlador y carga un nuevo video.
     * Útil cuando hay problemas de estado o conflictos.
     * 
     * @param rutaVideo Ruta del video a cargar
     */
    public void reiniciarYCargarVideo(String rutaVideo) {
        if (isDisposed) {
            System.err.println("No se puede reiniciar un controlador liberado");
            return;
        }
        
        // Resetear estados
        isLoadingVideo = false;
        needsReload = false;
        
        Platform.runLater(() -> {
            try {
                liberarRecursosMediaPlayerSeguro();
                mostrarPlaceholder();
                
                // Pequeña pausa para asegurar limpieza completa
                Platform.runLater(() -> {
                    cargarVideo(rutaVideo);
                });
            } catch (Exception e) {
                System.err.println("Error al reiniciar video: " + e.getMessage());
                mostrarError("Error al reiniciar el video: " + e.getMessage());
            }
        });
    }

    /**
     * Libera los recursos del MediaPlayer de forma segura.
     */
    private void liberarRecursosMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            } catch (Exception e) {
                System.err.println("Error al liberar MediaPlayer: " + e.getMessage());
            } finally {
                mediaPlayer = null;
            }
        }
        if (mediaView != null) {
            mediaView = null;
        }
    }
    
    /**
     * Versión segura de liberación de recursos que maneja estados de carga.
     */
    private void liberarRecursosMediaPlayerSeguro() {
        if (isLoadingVideo) {
            // Esperar un momento si está cargando
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        liberarRecursosMediaPlayer();
    }

    /**
     * Libera todos los recursos del controlador de video.
     */
    public void liberarRecursos() {
        isDisposed = true;
        isLoadingVideo = false;
        needsReload = false;
        
        Platform.runLater(() -> {
            try {
                liberarRecursosMediaPlayer();
                mostrarPlaceholder();
            } catch (Exception e) {
                System.err.println("Error al liberar recursos: " + e.getMessage());
            }
        });
    }

    /**
     * Prepara el controlador para ser reutilizado sin liberar completamente los recursos.
     */
    public void prepararParaReuso() {
        if (isDisposed) {
            return;
        }
        
        isLoadingVideo = false;
        needsReload = false;
        
        Platform.runLater(() -> {
            try {
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.seek(Duration.ZERO);
                }
                mostrarPlaceholder();
            } catch (Exception e) {
                System.err.println("Error al preparar para reuso: " + e.getMessage());
            }
        });
    }

    private void mostrarPlaceholder() {
        if (placeholderScene != null) {
            fxPanel.setScene(placeholderScene);
        }
    }

    private void configurarEventosReproductor() {
        // Configurar evento de listo para reproducir
        mediaPlayer.setOnReady(() -> {
            System.out.println("Video listo - Duración: " + mediaPlayer.getTotalDuration().toSeconds() + " segundos");
            mediaPlayer.play();
        });
        
        // Configurar evento de fin de media
        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Video completado - Ejecutando callback");
            if (onVideoCompletedCallback != null) {
                SwingUtilities.invokeLater(onVideoCompletedCallback);
            }
        });
        
        // Configurar evento de error
        mediaPlayer.setOnError(() -> {
            String errorMsg = mediaPlayer.getError() != null ? 
                mediaPlayer.getError().getMessage() : "Error desconocido";
            System.err.println("Error en MediaPlayer: " + errorMsg);
            mostrarError("Error en video: " + errorMsg);
        });
        
        // Configurar evento de pausa (para debugging)
        mediaPlayer.setOnPaused(() -> {
            System.out.println("Video pausado en: " + mediaPlayer.getCurrentTime().toSeconds() + " segundos");
        });
        
        // Configurar evento de reproducción (para debugging)
        mediaPlayer.setOnPlaying(() -> {
            System.out.println("Video reproduciéndose - Tiempo actual: " + mediaPlayer.getCurrentTime().toSeconds() + " segundos");
        });
        
        // Configurar evento de detención (para debugging)
        mediaPlayer.setOnStopped(() -> {
            System.out.println("Video detenido");
        });
    }

    public void runWhenInitialized(Runnable task) {
        if (isInitialized) {
            Platform.runLater(task);
        } else {
            new Thread(() -> {
                while (!isInitialized) {
                    try { Thread.sleep(50); } 
                    catch (InterruptedException e) { return; }
                }
                Platform.runLater(task);
            }).start();
        }
    }

    // Métodos de control básico
    /**
     * Reproduce el video actual si está cargado.
     */
    public void reproducir() {
        if (mediaPlayer != null && !isDisposed && !isLoadingVideo) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.play();
                } catch (Exception e) {
                    System.err.println("Error al reproducir video: " + e.getMessage());
                }
            });
        }
    }

    /**
     * Pausa el video actual si está reproduciéndose.
     */
    public void pausar() {
        if (mediaPlayer != null && !isDisposed && !isLoadingVideo) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.pause();
                } catch (Exception e) {
                    System.err.println("Error al pausar video: " + e.getMessage());
                }
            });
        }
    }

    /**
     * Establece el volumen del video.
     * 
     * @param volume Volumen entre 0.0 y 1.0
     */
    public void setVolume(double volume) {
        if (mediaPlayer != null && !isDisposed && !isLoadingVideo) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.setVolume(Math.max(0.0, Math.min(1.0, volume)));
                } catch (Exception e) {
                    System.err.println("Error al establecer volumen: " + e.getMessage());
                }
            });
        }
    }

    public void setOnVideoCompletedCallback(Runnable callback) {
        this.onVideoCompletedCallback = callback;
    }

    /**
     * Obtiene la duración total del video en segundos.
     * 
     * @return Duración en segundos, 0 si no hay video cargado
     */
    public double getDurationSeconds() {
        if (mediaPlayer != null && !isDisposed && mediaPlayer.getTotalDuration() != null) {
            try {
                return mediaPlayer.getTotalDuration().toSeconds();
            } catch (Exception e) {
                System.err.println("Error al obtener duración: " + e.getMessage());
            }
        }
        return 0;
    }

    /**
     * Obtiene el tiempo actual de reproducción en segundos.
     * 
     * @return Tiempo actual en segundos, 0 si no hay video reproduciéndose
     */
    public double getCurrentTimeSeconds() {
        if (mediaPlayer != null && !isDisposed && mediaPlayer.getCurrentTime() != null) {
            try {
                return mediaPlayer.getCurrentTime().toSeconds();
            } catch (Exception e) {
                System.err.println("Error al obtener tiempo actual: " + e.getMessage());
            }
        }
        return 0;
    }

    /**
     * Busca una posición específica en el video.
     * 
     * @param seconds Segundos desde el inicio del video
     */
    public void seek(double seconds) {
        if (mediaPlayer != null && !isDisposed && !isLoadingVideo) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.seek(Duration.seconds(Math.max(0, seconds)));
                } catch (Exception e) {
                    System.err.println("Error al buscar posición en video: " + e.getMessage());
                }
            });
        }
    }
    /**
     * Detiene completamente el video y libera recursos para evitar audio en segundo plano.
     */
    public void detenerVideo() {
        if (mediaPlayer != null && !isDisposed) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.stop();
                    mediaPlayer.setVolume(0.0); // Silenciar completamente
                    mediaPlayer.seek(Duration.ZERO); // Volver al inicio
                } catch (Exception e) {
                    System.err.println("Error al detener video: " + e.getMessage());
                }
            });
        }
    }
    
    /**
     * Detiene completamente el video y libera todos los recursos de audio/video.
     * Método más robusto para evitar audio en segundo plano.
     */
    public void detenerYLiberarRecursos() {
        if (isDisposed) {
            System.out.println("Controlador de video ya fue liberado");
            return;
        }
        
        System.out.println("=== INICIANDO LIBERACIÓN COMPLETA DE RECURSOS DE VIDEO ===");
        
        // Marcar como disposed inmediatamente para evitar operaciones concurrentes
        isDisposed = true;
        
        // Desregistrar del gestor global de videos
        try {
            VideoManager.getInstance().desregistrarControlador(this);
            System.out.println("Controlador desregistrado del VideoManager");
        } catch (Exception e) {
            System.err.println("Error al desregistrar del VideoManager: " + e.getMessage());
        }
        
        Platform.runLater(() -> {
            try {
                if (mediaPlayer != null) {
                    System.out.println("Deteniendo MediaPlayer...");
                    
                    // Detener inmediatamente
                    mediaPlayer.stop();
                    
                    // Silenciar completamente
                    mediaPlayer.setVolume(0.0);
                    
                    // Volver al inicio
                    mediaPlayer.seek(Duration.ZERO);
                    
                    // Liberar recursos del MediaPlayer
                    mediaPlayer.dispose();
                    mediaPlayer = null;
                    
                    System.out.println("MediaPlayer detenido y liberado");
                }
                
                if (mediaView != null) {
                    mediaView.setMediaPlayer(null);
                    mediaView = null;
                    System.out.println("MediaView liberado");
                }
                
                // Limpiar la escena y mostrar placeholder
                if (videoRoot != null) {
                    videoRoot.getChildren().clear();
                    videoRoot = null;
                }
                
                mostrarPlaceholder();
                
                // Limpiar referencias
                currentVideoPath = null;
                onVideoCompletedCallback = null;
                
                System.out.println("=== RECURSOS DE VIDEO LIBERADOS COMPLETAMENTE ===");
                
            } catch (Exception e) {
                System.err.println("Error al detener y liberar recursos: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
    
    /**
     * Configura el callback que se ejecutará cuando el video termine.
     * 
     * @param callback Runnable que se ejecutará al finalizar el video
     */
    public void setOnVideoCompleted(Runnable callback) {
        this.onVideoCompletedCallback = callback;
        System.out.println("Callback de video completado configurado");
    }

    private void mostrarError(String mensaje) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(contenedorVideo, mensaje, 
                "Error de Video", JOptionPane.ERROR_MESSAGE));
    }
}
