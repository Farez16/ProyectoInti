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
import java.io.File;

public class Controlador_Video {

    private JFXPanel fxPanel;
    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private JPanel contenedorVideo;
    private Scene placeholderScene; // Escena para mostrar cuando no hay video
    private StackPane videoRoot; // Root para el video actual
    private boolean isInitialized = false;

    private Runnable onVideoCompletedCallback;

    public Controlador_Video(JPanel contenedorVideo) {
        this.contenedorVideo = contenedorVideo;
        this.fxPanel = new JFXPanel();
        contenedorVideo.setLayout(new java.awt.BorderLayout());
        contenedorVideo.add(fxPanel, java.awt.BorderLayout.CENTER);

        Platform.runLater(this::initializeFX);
        System.out.println("JavaFX inicializado correctamente");
    }

    private void initializeFX() {
        // Crear la escena placeholder inicial
        StackPane placeholderRoot = new StackPane();
        Label placeholderLabel = new Label("Aquí debería verse el video");
        placeholderRoot.getChildren().add(placeholderLabel);
        placeholderScene = new Scene(placeholderRoot);
        fxPanel.setScene(placeholderScene);
        isInitialized = true;
        
        System.out.println("JavaFX inicializado correctamente");
    }

    public void cargarVideo(String rutaVideo) {
        if (!isInitialized) {
            System.out.println("JavaFX no está inicializado aún, esperando...");
            Platform.runLater(() -> {
                // Esperar un poco más y reintentar
                new Thread(() -> {
                    try {
                        Thread.sleep(100);
                        Platform.runLater(() -> cargarVideo(rutaVideo));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }).start();
            });
            return;
        }

        Platform.runLater(() -> {
            try {
                System.out.println("Intentando cargar video con ruta: " + rutaVideo);

                File videoFile = new File(rutaVideo);
                if (!videoFile.exists()) {
                    mostrarError("No se encontró el archivo de video: " + rutaVideo);
                    return;
                }

                // IMPORTANTE: Liberar recursos completamente ANTES de crear nuevos
                liberarRecursosMediaPlayer();

                // Crear nuevos objetos para el video
                Media media = new Media(videoFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                
                // Configurar eventos ANTES de crear MediaView
                configurarEventosReproductor();
                
                mediaView = new MediaView(mediaPlayer);
                videoRoot = new StackPane();
                videoRoot.getChildren().add(mediaView);

                // Bind para que el video se adapte al tamaño del contenedor
                mediaView.fitWidthProperty().bind(videoRoot.widthProperty());
                mediaView.fitHeightProperty().bind(videoRoot.heightProperty());
                mediaView.setPreserveRatio(true);
                mediaView.setSmooth(true);

                // Crear nueva escena para el video
                Scene videoScene = new Scene(videoRoot);
                fxPanel.setScene(videoScene);

                System.out.println("Video cargado exitosamente: " + rutaVideo);

            } catch (Exception e) {
                System.err.println("Error al cargar video: " + e.getMessage());
                e.printStackTrace();
                mostrarError("Error al cargar el video: " + e.getMessage());
                // Volver a la escena placeholder en caso de error
                mostrarPlaceholder();
            }
        });
    }
    


 public void reiniciarYCargarVideo(String rutaVideo) {
    Platform.runLater(() -> {
        try {
            System.out.println("Reiniciando Controlador de Video y cargando video...");

            // Liberar recursos del MediaPlayer actual
            liberarRecursosMediaPlayer();

            // Mostrar escena placeholder mientras carga el nuevo video
            if (fxPanel != null && placeholderScene != null) {
                fxPanel.setScene(placeholderScene);
            }

            // Verificar si el archivo de video existe
            File videoFile = new File(rutaVideo);
            if (!videoFile.exists()) {
                mostrarError("No se encontró el archivo de video: " + rutaVideo);
                return;
            }

            // Crear y configurar nuevo MediaPlayer y MediaView
            Media media = new Media(videoFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            configurarEventosReproductor();

            mediaView = new MediaView(mediaPlayer);
            videoRoot = new StackPane();
            videoRoot.getChildren().add(mediaView);

            mediaView.fitWidthProperty().bind(videoRoot.widthProperty());
            mediaView.fitHeightProperty().bind(videoRoot.heightProperty());
            mediaView.setPreserveRatio(true);
            mediaView.setSmooth(true);

            // Crear la nueva escena con el video y establecerla en el fxPanel
            Scene videoScene = new Scene(videoRoot);
            fxPanel.setScene(videoScene);

            System.out.println("Video cargado exitosamente: " + rutaVideo);

        } catch (Exception e) {
            System.err.println("Error al reiniciar y cargar video: " + e.getMessage());
            mostrarError("Error al reiniciar y cargar el video: " + e.getMessage());
            mostrarPlaceholder();
        }
    });
}
 public void runWhenInitialized(Runnable task) {
    if (isInitialized) {
        Platform.runLater(task);
    } else {
        // Intenta esperar o reintentar hasta que isInitialized sea true
        new Thread(() -> {
            while (!isInitialized) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Platform.runLater(task);
        }).start();
    }
}



    private void liberarRecursosMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null;
                System.out.println("MediaPlayer liberado correctamente");
            }
            if (mediaView != null) {
                mediaView = null;
                System.out.println("MediaView liberado correctamente");
            }
            if (videoRoot != null) {
                videoRoot.getChildren().clear();
                videoRoot = null;
                System.out.println("VideoRoot liberado correctamente");
            }
        } catch (Exception e) {
            System.err.println("Error al liberar recursos del MediaPlayer: " + e.getMessage());
        }
    }

    private void mostrarPlaceholder() {
        Platform.runLater(() -> {
            if (placeholderScene != null && fxPanel != null) {
                fxPanel.setScene(placeholderScene);
            }
        });
    }

    private void configurarEventosReproductor() {
        if (mediaPlayer == null) {
            return;
        }

        mediaPlayer.setOnReady(() -> {
            System.out.println("Video listo para reproducir");
            mediaPlayer.play();
        });

        mediaPlayer.setOnEndOfMedia(() -> {
            System.out.println("Video completado");
            if (onVideoCompletedCallback != null) {
                SwingUtilities.invokeLater(onVideoCompletedCallback);
            }
        });

        mediaPlayer.setOnError(() -> {
            String errorMsg = "Error desconocido";
            if (mediaPlayer.getError() != null) {
                errorMsg = mediaPlayer.getError().getMessage();
            }
            System.err.println("Error en el reproductor: " + errorMsg);
            mostrarError("Error al reproducir el video: " + errorMsg);
            mostrarPlaceholder();
        });

        // Agregar evento para detectar cuando el MediaPlayer se ha liberado
        mediaPlayer.setOnHalted(() -> {
            System.out.println("MediaPlayer se ha detenido completamente");
        });
    }

    // Métodos para control externo
    public void reproducir() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.play();
                } catch (Exception e) {
                    System.err.println("Error al reproducir: " + e.getMessage());
                }
            });
        }
    }

    public void pausar() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.pause();
                } catch (Exception e) {
                    System.err.println("Error al pausar: " + e.getMessage());
                }
            });
        }
    }

    public void detener() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.stop();
                } catch (Exception e) {
                    System.err.println("Error al detener: " + e.getMessage());
                }
            });
        }
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.setVolume(Math.max(0.0, Math.min(1.0, volume)));
                } catch (Exception e) {
                    System.err.println("Error al cambiar volumen: " + e.getMessage());
                }
            });
        }
    }

    public double getDurationSeconds() {
        if (mediaPlayer != null && mediaPlayer.getTotalDuration() != null) {
            return mediaPlayer.getTotalDuration().toSeconds();
        }
        return 0;
    }

    public double getCurrentTimeSeconds() {
        if (mediaPlayer != null && mediaPlayer.getCurrentTime() != null) {
            return mediaPlayer.getCurrentTime().toSeconds();
        }
        return 0;
    }

    public void seek(double seconds) {
        if (mediaPlayer != null) {
            Platform.runLater(() -> {
                try {
                    mediaPlayer.seek(Duration.seconds(seconds));
                } catch (Exception e) {
                    System.err.println("Error al hacer seek: " + e.getMessage());
                }
            });
        }
    }

    public void setOnVideoCompletedCallback(Runnable callback) {
        this.onVideoCompletedCallback = callback;
    }

  public void liberarRecursos() {
    Platform.runLater(() -> {
        try {
            liberarRecursosMediaPlayer();

            if (fxPanel != null && placeholderScene != null) {
                fxPanel.setScene(placeholderScene);
            }

            System.out.println("Recursos del video liberados y placeholder restaurado");
        } catch (Exception e) {
            System.err.println("Error al liberar recursos: " + e.getMessage());
        }
    });
}


    // Método para reiniciar completamente el controlador de video
public void reiniciarControladorVideo() {
    Platform.runLater(() -> {
        try {
            System.out.println("Reiniciando Controlador de Video...");
            liberarRecursosMediaPlayer();

            // Restaurar el placeholder por defecto
            if (fxPanel != null && placeholderScene != null) {
                fxPanel.setScene(placeholderScene);
            }

            System.out.println("Controlador de video reiniciado completamente");
        } catch (Exception e) {
            System.err.println("Error al reiniciar controlador de video: " + e.getMessage());
        }
    });
}



    // Método para verificar si el video está cargado y listo
    public boolean isVideoListo() {
        return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.READY;
    }

    // Método para obtener el estado actual del MediaPlayer
    public String getEstadoMediaPlayer() {
        if (mediaPlayer == null) {
            return "MediaPlayer no inicializado";
        }
        return "Estado: " + mediaPlayer.getStatus().toString();
    }

    private void mostrarError(String mensaje) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(contenedorVideo, mensaje, "Error de Video", JOptionPane.ERROR_MESSAGE);
        });
    }
}