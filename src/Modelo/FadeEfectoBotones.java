package Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FadeEfectoBotones {
public static void applyFadeEffect(JButton button, String newText) {
        // Guardar los valores originales
        Color originalForeground = button.getForeground();
        Font originalFont = button.getFont();
        
        // Crear timer para el fade out
        Timer fadeOutTimer = new Timer(30, new ActionListener() {
            private float opacity = 1.0f;
            
            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;
                
                if (opacity <= 0) {
                    opacity = 0;
                    button.setText(newText); // Cambiar el texto cuando está completamente transparente
                    ((Timer)e.getSource()).stop();
                    
                    // Iniciar el fade in
                    Timer fadeInTimer = new Timer(30, new ActionListener() {
                        private float fadeInOpacity = 0.0f;
                        
                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            fadeInOpacity += 0.05f;
                            
                            if (fadeInOpacity >= 1) {
                                fadeInOpacity = 1;
                                ((Timer)evt.getSource()).stop();
                            }
                            
                            // Aplicar la nueva opacidad
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
                    // Aplicar la opacidad decreciente
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
}

