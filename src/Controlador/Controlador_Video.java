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
    private Runnable onVideoCompletedCallback;

    public Controlador_Video(JPanel contenedorVideo) {
        this.contenedorVideo = contenedorVideo;
        this.fxPanel = new JFXPanel();
        configurarContenedor();
        Platform.runLater(this::initializeFX);
    }

    private void configurarContenedor() {
        contenedorVideo.setLayout(new BorderLayout());
        contenedorVideo.removeAll();
        contenedorVideo.add(fxPanel, BorderLayout.CENTER);
        contenedorVideo.revalidate();
        contenedorVideo.repaint();
    }

    private void initializeFX() {
        StackPane placeholderRoot = new StackPane(new Label("Cargando video..."));
        placeholderScene = new Scene(placeholderRoot, 640, 480);
        fxPanel.setScene(placeholderScene);
        isInitialized = true;
    }

    public void cargarVideo(String rutaVideo) {
        if (mediaPlayer != null) {
            mediaPlayer.dispose();
        }
        if (!isInitialized) {
            Platform.runLater(() -> cargarVideo(rutaVideo));
            return;
        }

        Platform.runLater(() -> {
            try {
                File videoFile = new File(rutaVideo);
                if (!videoFile.exists()) {
                    mostrarError("Archivo no encontrado: " + rutaVideo);
                    return;
                }

                liberarRecursosMediaPlayer();

                Media media = new Media(videoFile.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                configurarEventosReproductor();

                mediaView = new MediaView(mediaPlayer);
                videoRoot = new StackPane(mediaView);
                configurarMediaView();

                Scene videoScene = new Scene(videoRoot);
                fxPanel.setScene(videoScene);
            } catch (Exception e) {
                mostrarError("Error al cargar video: " + e.getMessage());
            }
        });
    }

    private void configurarMediaView() {
        mediaView.fitWidthProperty().bind(videoRoot.widthProperty());
        mediaView.fitHeightProperty().bind(videoRoot.heightProperty());
        mediaView.setPreserveRatio(true);
        mediaView.setSmooth(true);
    }

    public void reiniciarYCargarVideo(String rutaVideo) {
        Platform.runLater(() -> {
            liberarRecursosMediaPlayer();
            mostrarPlaceholder();
            cargarVideo(rutaVideo);
        });
    }

    private void liberarRecursosMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
            mediaPlayer = null;
        }
        if (mediaView != null) {
            mediaView = null;
        }
    }

    public void liberarRecursos() {
        Platform.runLater(() -> {
            liberarRecursosMediaPlayer();
            mostrarPlaceholder();
        });
    }

    public void prepararParaReuso() {
        Platform.runLater(() -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.seek(Duration.ZERO);
            }
            mostrarPlaceholder();
        });
    }

    private void mostrarPlaceholder() {
        if (placeholderScene != null) {
            fxPanel.setScene(placeholderScene);
        }
    }

    private void configurarEventosReproductor() {
        mediaPlayer.setOnReady(() -> mediaPlayer.play());
        mediaPlayer.setOnEndOfMedia(() -> {
            if (onVideoCompletedCallback != null) {
                SwingUtilities.invokeLater(onVideoCompletedCallback);
            }
        });
        mediaPlayer.setOnError(() -> {
            String errorMsg = mediaPlayer.getError() != null ? 
                mediaPlayer.getError().getMessage() : "Error desconocido";
            mostrarError("Error en video: " + errorMsg);
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
    public void reproducir() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> mediaPlayer.play());
        }
    }

    public void pausar() {
        if (mediaPlayer != null) {
            Platform.runLater(() -> mediaPlayer.pause());
        }
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null) {
            Platform.runLater(() -> mediaPlayer.setVolume(volume));
        }
    }

    public void setOnVideoCompletedCallback(Runnable callback) {
        this.onVideoCompletedCallback = callback;
    }

    public double getDurationSeconds() {
        return mediaPlayer != null && mediaPlayer.getTotalDuration() != null ? 
            mediaPlayer.getTotalDuration().toSeconds() : 0;
    }

    public double getCurrentTimeSeconds() {
        return mediaPlayer != null && mediaPlayer.getCurrentTime() != null ? 
            mediaPlayer.getCurrentTime().toSeconds() : 0;
    }

    public void seek(double seconds) {
        if (mediaPlayer != null) {
            Platform.runLater(() -> mediaPlayer.seek(Duration.seconds(seconds)));
        }
    }
     public void detenerVideo() {
        // Implementa la lógica para detener el video
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
    
   

    private void mostrarError(String mensaje) {
        SwingUtilities.invokeLater(() -> 
            JOptionPane.showMessageDialog(contenedorVideo, mensaje, 
                "Error de Video", JOptionPane.ERROR_MESSAGE));
    }
}