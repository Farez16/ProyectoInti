package Controlador;

import Modelo.FadeEfectoBotones;
import Modelo.TextoBotones;
import Vista.Dashboard;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;

public class ControladorBotones {

    private TextoBotones modelo;
    private Timer timer;
    private Map<String, Boolean> estadosBotones; // Para alternar entre los textos
    private Map<String, JButton> botonesMap; // Mapa de botones dinámico

    public ControladorBotones(Map<String, JButton> botonesMap, TextoBotones modelo) {
        this.botonesMap = botonesMap;
        this.modelo = modelo;
        this.estadosBotones = new HashMap<>();

        // Inicializar estados (todos comienzan con el primer texto)
        botonesMap.keySet().forEach(key -> estadosBotones.put(key, true));

    }

    public void iniciar() {
        // Mostrar textos iniciales
        actualizarTextosBotones(false); // Sin animación la primera vez

        // Configurar timer para cambio cada 10 segundos
        timer = new Timer(10000, e -> {
            // Alternar estados
            estadosBotones.replaceAll((k, v) -> !v); // Alternar estados
            actualizarTextosBotones(true); // Con animación
        });
        timer.start();
    }

private void actualizarTextosBotones(boolean conAnimacion) {
    if (modelo == null) {
        throw new IllegalStateException("El modelo TextoBotones no puede ser null");
    }
    
    for (Map.Entry<String, JButton> entry : botonesMap.entrySet()) {
        String nombreBoton = entry.getKey();
        JButton boton = entry.getValue();
        
        String[] textos = modelo.getTextos(nombreBoton);
        if (textos == null) {
            System.err.println("No hay textos para: " + nombreBoton);
            continue;
        }
            boolean mostrarPrimerTexto = estadosBotones.get(nombreBoton);
            String nuevoTexto = mostrarPrimerTexto ? textos[0] : textos[1];
            
            if (conAnimacion) {
                FadeEfectoBotones.applyFadeEffect(boton, nuevoTexto);
            } else {
                boton.setText(nuevoTexto);
            }
        }
    }
    
    public void detener() {
        if (timer != null) {
            timer.stop();
        }
    }
}
