package Controlador;

import Modelo.FadeEfectoBotones;
import Modelo.TextoBotones;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ControladorBotones {

    private TextoBotones modelo;
    private Timer timer;
    private Map<String, Boolean> estadosBotones;
    private Map<String, JButton> botonesMap;
    private boolean animacionEnCurso = false;
    private String tipoVista; // "ADMIN" o "ESTUDIANTE"

    public ControladorBotones(Map<String, JButton> botonesMap, TextoBotones modelo, String tipoVista) {
        this.botonesMap = botonesMap;
        this.modelo = modelo;
        this.tipoVista = tipoVista;
        this.estadosBotones = new HashMap<>();

        // Inicialización robusta de estados
        botonesMap.forEach((nombreBoton, boton) -> {
            if (modelo.getTextos(nombreBoton) != null) {
                estadosBotones.put(nombreBoton, true);
                System.out.println("Inicializado estado para " + nombreBoton + ": true");
            } else {
                System.err.println("Advertencia: No se encontraron textos para " + nombreBoton);
            }
        });
    }

    public void iniciar() {
        // Mostrar textos iniciales
        actualizarTextosBotones(false);

        // Configurar timer para cambio cada 10 segundos
        timer = new Timer(10000, e -> {
            if (!animacionEnCurso) {
                estadosBotones.replaceAll((k, v) -> !v);
                actualizarTextosBotones(true);
            }
        });
        timer.start();
    }

    private void actualizarTextosBotones(boolean conAnimacion) {
        animacionEnCurso = conAnimacion;

        System.out.println("\n=== Actualizando textos (" + tipoVista + ") ===");

        for (Map.Entry<String, JButton> entry : botonesMap.entrySet()) {
            String nombreBoton = entry.getKey();
            JButton boton = entry.getValue();

            String[] textos = modelo.getTextos(nombreBoton);
            if (textos == null || textos.length < 2) {
                System.err.println("ERROR: No hay textos válidos para " + nombreBoton);
                continue;
            }

            // Verificar que el estado existe para este botón
            if (!estadosBotones.containsKey(nombreBoton)) {
                System.err.println("ERROR: No hay estado definido para " + nombreBoton);
                estadosBotones.put(nombreBoton, true); // Inicializar si falta
                continue;
            }

            boolean mostrarPrimerTexto = estadosBotones.get(nombreBoton);
            String nuevoTexto = mostrarPrimerTexto ? textos[0] : textos[1];

            System.out.printf("%-15s: %-10s → %-10s (Estado: %s)%n",
                    nombreBoton, boton.getText(), nuevoTexto, mostrarPrimerTexto);

            if (conAnimacion) {
                FadeEfectoBotones.applyFadeEffect(boton, nuevoTexto);
            } else {
                boton.setText(nuevoTexto);
            }
        }

        if (conAnimacion) {
            new Timer(1000, evt -> {
                animacionEnCurso = false;
                ((Timer) evt.getSource()).stop();
            }).start();
        }
    }

    public void detener() {
        if (timer != null) {
            timer.stop();
        }
        FadeEfectoBotones.cancelAllAnimations();
    }
}
