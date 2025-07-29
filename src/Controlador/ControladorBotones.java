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

        System.out.println("\n=== INICIALIZANDO ControladorBotones para " + tipoVista + " ===");
        System.out.println("Botones recibidos: " + botonesMap.size());
        
        // Inicialización robusta de estados
        botonesMap.forEach((nombreBoton, boton) -> {
            System.out.println("Procesando botón: " + nombreBoton + " -> " + (boton != null ? boton.getClass().getSimpleName() : "NULL"));
            if (boton == null) {
                System.err.println("ERROR CRÍTICO: El botón " + nombreBoton + " es NULL!");
                return;
            }
            
            String[] textos = modelo.getTextos(nombreBoton);
            if (textos != null && textos.length >= 2) {
                estadosBotones.put(nombreBoton, true);
                System.out.println("✓ Inicializado estado para " + nombreBoton + ": true (textos: [" + textos[0] + ", " + textos[1] + "])");
            } else {
                System.err.println("✗ ERROR: No se encontraron textos válidos para " + nombreBoton + " (textos: " + java.util.Arrays.toString(textos) + ")");
            }
        });
        
        System.out.println("Estados inicializados: " + estadosBotones.size());
        System.out.println("=== FIN INICIALIZACIÓN ===");
    }

    public void iniciar() {
        System.out.println("\n=== INICIANDO ControladorBotones para " + tipoVista + " ===");
        System.out.println("Botones a procesar: " + botonesMap.size());
        
        // Mostrar textos iniciales
        System.out.println("Mostrando textos iniciales...");
        actualizarTextosBotones(false);

        // Configurar timer para cambio cada 10 segundos
        System.out.println("Configurando timer para cambio cada 10 segundos...");
        timer = new Timer(10000, e -> {
            System.out.println("\n🔄 TIMER EJECUTÁNDOSE - " + tipoVista + " (animación en curso: " + animacionEnCurso + ")");
            if (!animacionEnCurso) {
                System.out.println("Cambiando estados de botones...");
                estadosBotones.replaceAll((k, v) -> {
                    boolean nuevoEstado = !v;
                    System.out.println("Botón " + k + ": " + v + " -> " + nuevoEstado);
                    return nuevoEstado;
                });
                actualizarTextosBotones(true);
            } else {
                System.out.println("⚠️ Saltando cambio - animación en curso");
            }
        });
        timer.start();
        System.out.println("✓ Timer iniciado correctamente");
        System.out.println("=== FIN INICIO ===");
    }

    private void actualizarTextosBotones(boolean conAnimacion) {
        animacionEnCurso = conAnimacion;

        System.out.println("\n=== Actualizando textos (" + tipoVista + ") - Animación: " + conAnimacion + " ===");
        System.out.println("Botones en mapa: " + botonesMap.size());
        System.out.println("Estados disponibles: " + estadosBotones.size());

        for (Map.Entry<String, JButton> entry : botonesMap.entrySet()) {
            String nombreBoton = entry.getKey();
            JButton boton = entry.getValue();

            System.out.println("\n--- Procesando botón: " + nombreBoton + " ---");
            
            // Verificar si el botón es null
            if (boton == null) {
                System.err.println("❌ ERROR CRÍTICO: El botón " + nombreBoton + " es NULL!");
                continue;
            }
            
            System.out.println("Botón válido: " + boton.getClass().getSimpleName());
            System.out.println("Texto actual: '" + boton.getText() + "'");

            String[] textos = modelo.getTextos(nombreBoton);
            if (textos == null || textos.length < 2) {
                System.err.println("❌ ERROR: No hay textos válidos para " + nombreBoton + " (textos: " + java.util.Arrays.toString(textos) + ")");
                continue;
            }
            
            System.out.println("Textos disponibles: [" + textos[0] + ", " + textos[1] + "]");

            // Verificar que el estado existe para este botón
            if (!estadosBotones.containsKey(nombreBoton)) {
                System.err.println("❌ ERROR: No hay estado definido para " + nombreBoton);
                estadosBotones.put(nombreBoton, true); // Inicializar si falta
                continue;
            }

            boolean mostrarPrimerTexto = estadosBotones.get(nombreBoton);
            String nuevoTexto = mostrarPrimerTexto ? textos[0] : textos[1];
            
            System.out.println("Estado actual: " + mostrarPrimerTexto + " -> Texto a mostrar: '" + nuevoTexto + "'");

            try {
                if (conAnimacion) {
                    System.out.println("Aplicando efecto fade...");
                    FadeEfectoBotones.applyFadeEffect(boton, nuevoTexto);
                } else {
                    System.out.println("Cambiando texto directamente...");
                    boton.setText(nuevoTexto);
                    System.out.println("Texto después del cambio: '" + boton.getText() + "'");
                }
                System.out.println("✅ Cambio exitoso para " + nombreBoton);
            } catch (Exception ex) {
                System.err.println("❌ ERROR al cambiar texto de " + nombreBoton + ": " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        if (conAnimacion) {
            System.out.println("Configurando timer para finalizar animación en 1 segundo...");
            new Timer(1000, evt -> {
                animacionEnCurso = false;
                System.out.println("Animación finalizada para " + tipoVista);
                ((Timer) evt.getSource()).stop();
            }).start();
        }
        
        System.out.println("=== FIN Actualización textos (" + tipoVista + ") ===");
    }

    public void detener() {
        if (timer != null) {
            timer.stop();
        }
        FadeEfectoBotones.cancelAllAnimations();
    }
}
