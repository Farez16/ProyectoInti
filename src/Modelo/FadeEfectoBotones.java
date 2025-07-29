package Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FadeEfectoBotones {
    // Usar mapas para manejar timers individuales por botón
    private static final Map<JButton, Timer> fadeOutTimers = new ConcurrentHashMap<>();
    private static final Map<JButton, Timer> fadeInTimers = new ConcurrentHashMap<>();
    
    public static void applyFadeEffect(JButton button, String newText) {
        System.out.println("[FADE] Iniciando efecto para botón: " + button.getText() + " -> " + newText);
        
        // Cancelar animaciones previas para este botón específico
        cancelAnimationsForButton(button);
        
        // Guardar los valores originales
        Color originalForeground = button.getForeground();
        
        // Crear timer individual para el fade out de este botón
        Timer fadeOutTimer = new Timer(50, new ActionListener() {
            private float opacity = 1.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.1f; // Más rápido para mejor visibilidad
                
                if (opacity <= 0) {
                    opacity = 0;
                    // Cambiar el texto cuando la opacidad llega a 0
                    SwingUtilities.invokeLater(() -> {
                        button.setText(newText);
                        System.out.println("[FADE] Texto cambiado a: " + newText);
                    });
                    
                    // Detener fade out y remover del mapa
                    Timer currentTimer = fadeOutTimers.remove(button);
                    if (currentTimer != null) {
                        currentTimer.stop();
                    }
                    
                    // Iniciar el fade in
                    startFadeIn(button, originalForeground);
                } else {
                    // Aplicar transparencia
                    SwingUtilities.invokeLater(() -> {
                        try {
                            button.setForeground(new Color(
                                originalForeground.getRed(),
                                originalForeground.getGreen(),
                                originalForeground.getBlue(),
                                Math.max(0, Math.min(255, (int)(opacity * 255)))
                            ));
                        } catch (Exception ex) {
                            System.err.println("[FADE] Error aplicando transparencia: " + ex.getMessage());
                        }
                    });
                }
            }
        });
        
        // Guardar el timer en el mapa y iniciarlo
        fadeOutTimers.put(button, fadeOutTimer);
        fadeOutTimer.start();
        System.out.println("[FADE] Timer fade-out iniciado");
    }
    
    private static void startFadeIn(JButton button, Color originalForeground) {
        Timer fadeInTimer = new Timer(50, new ActionListener() {
            private float fadeInOpacity = 0.0f;
            
            @Override
            public void actionPerformed(ActionEvent evt) {
                fadeInOpacity += 0.1f; // Más rápido
                
                if (fadeInOpacity >= 1) {
                    fadeInOpacity = 1;
                    // Restaurar color original completamente
                    SwingUtilities.invokeLater(() -> {
                        button.setForeground(originalForeground);
                        System.out.println("[FADE] Efecto completado para: " + button.getText());
                    });
                    
                    // Detener fade in y remover del mapa
                    Timer currentTimer = fadeInTimers.remove(button);
                    if (currentTimer != null) {
                        currentTimer.stop();
                    }
                } else {
                    // Aplicar transparencia creciente
                    SwingUtilities.invokeLater(() -> {
                        try {
                            button.setForeground(new Color(
                                originalForeground.getRed(),
                                originalForeground.getGreen(),
                                originalForeground.getBlue(),
                                Math.max(0, Math.min(255, (int)(fadeInOpacity * 255)))
                            ));
                        } catch (Exception ex) {
                            System.err.println("[FADE] Error aplicando fade-in: " + ex.getMessage());
                        }
                    });
                }
            }
        });
        
        // Guardar el timer en el mapa y iniciarlo
        fadeInTimers.put(button, fadeInTimer);
        fadeInTimer.start();
        System.out.println("[FADE] Timer fade-in iniciado");
    }
    
    private static void cancelAnimationsForButton(JButton button) {
        // Cancelar fade out para este botón
        Timer fadeOut = fadeOutTimers.remove(button);
        if (fadeOut != null && fadeOut.isRunning()) {
            fadeOut.stop();
            System.out.println("[FADE] Cancelado fade-out previo");
        }
        
        // Cancelar fade in para este botón
        Timer fadeIn = fadeInTimers.remove(button);
        if (fadeIn != null && fadeIn.isRunning()) {
            fadeIn.stop();
            System.out.println("[FADE] Cancelado fade-in previo");
        }
    }
    
    public static void cancelAllAnimations() {
        System.out.println("[FADE] Cancelando todas las animaciones");
        
        // Detener todos los fade out timers
        fadeOutTimers.values().forEach(timer -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
        });
        fadeOutTimers.clear();
        
        // Detener todos los fade in timers
        fadeInTimers.values().forEach(timer -> {
            if (timer != null && timer.isRunning()) {
                timer.stop();
            }
        });
        fadeInTimers.clear();
    }
}
