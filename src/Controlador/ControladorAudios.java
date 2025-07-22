package Controlador;

import Vista.Vista_LeccionFONOLOGIA;
import javazoom.jl.player.Player;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import modelo.Audios;

public class ControladorAudios {

    private final Vista_LeccionFONOLOGIA vista;
    private final Audios modelo;
    private ArrayList<HashMap<String, String>> Audios;

    public ControladorAudios(Vista_LeccionFONOLOGIA vista, Audios modelo) {
        this.vista = vista;
        this.modelo = modelo;

        inicializar();
        configurarEventos();
    }

    private void inicializar() {
        // Puedes cambiar este ID según de dónde quieras obtener el recurso
        HashMap<String, String> recurso = modelo.obtenerRecursoPorLeccion(2); // Ejemplo: lección 2

        if (recurso != null) {
            String ruta = recurso.get("ruta_recurso");
            vista.setRutaAudio(ruta); // Guardamos la ruta en la vista para poder usarla al hacer clic
        } else {
            JOptionPane.showMessageDialog(vista, "No se encontró el recurso de audio.");
        }
    }

    private void configurarEventos() {
        vista.getBtnAudio().addActionListener(e -> {
            String ruta = vista.getRutaAudio();
            if (ruta != null && !ruta.isEmpty()) {
                reproducirAudio(ruta);
            } else {
                JOptionPane.showMessageDialog(vista, "No hay ruta de audio cargada.");
            }
        });
    }

    private void reproducirAudio(String nombreArchivo) {
        new Thread(() -> {
            try {
                InputStream is = getClass().getClassLoader().getResourceAsStream("audios/" + nombreArchivo);
                if (is == null) {
                    JOptionPane.showMessageDialog(vista, "Archivo no encontrado: " + nombreArchivo);
                    return;
                }
                BufferedInputStream bis = new BufferedInputStream(is);
                Player player = new Player(bis);
                player.play();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(vista, "Error al reproducir audio: " + ex.getMessage());
            }
        }).start();
    }

}
