package Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEfectoBotones {
    private static Timer fadeOutTimer;
    private static Timer fadeInTimer;
    
    public static void applyFadeEffect(JButton button, String newText) {
        // Cancelar animaciones previas si existen
        if (fadeOutTimer != null && fadeOutTimer.isRunning()) {
            fadeOutTimer.stop();
        }
        if (fadeInTimer != null && fadeInTimer.isRunning()) {
            fadeInTimer.stop();
        }
        
        // Guardar los valores originales
        Color originalForeground = button.getForeground();
        Font originalFont = button.getFont();
        
        // Crear timer para el fade out
        fadeOutTimer = new Timer(40, new ActionListener() {
            private float opacity = 1.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                
                if (opacity <= 0) {
                    opacity = 0;
                    button.setText(newText);
                    fadeOutTimer.stop();
                    
                    // Iniciar el fade in
                    fadeInTimer = new Timer(40, new ActionListener() {
                        private float fadeInOpacity = 0.0f;
                        
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            fadeInOpacity += 0.05f;
                            
                            if (fadeInOpacity >= 1) {
                                fadeInOpacity = 1;
                                fadeInTimer.stop();
                                // Restaurar color original completamente
                                button.setForeground(originalForeground);
                                return;
                            }
                            
                            button.setForeground(new Color(
                                originalForeground.getRed(),
                                originalForeground.getGreen(),
                                originalForeground.getBlue(),
                                (int)(fadeInOpacity * 255)
                            ));
                        }
                    });
                    fadeInTimer.start();
                } else {
                    button.setForeground(new Color(
                        originalForeground.getRed(),
                        originalForeground.getGreen(),
                        originalForeground.getBlue(),
                        (int)(opacity * 255)
                    ));
                }
            }
        });
        fadeOutTimer.start();
    }
    
    public static void cancelAllAnimations() {
        if (fadeOutTimer != null) fadeOutTimer.stop();
        if (fadeInTimer != null) fadeInTimer.stop();
    }
}
