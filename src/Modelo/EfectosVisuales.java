package Modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EfectosVisuales {

    public static void aplicarFadeLabel(JLabel label, String nuevoTexto, int duracionMs) {
        Color colorOriginal = label.getForeground();
        Timer fadeOutTimer = new Timer(30, null);

        fadeOutTimer.addActionListener(new ActionListener() {
            private float opacity = 1.0f;

            @Override
            public void actionPerformed(ActionEvent e) {
                opacity -= 0.05f;

                if (opacity <= 0) {
                    opacity = 0;
                    label.setText(nuevoTexto);
                    fadeOutTimer.stop();

                    // Fade in
                    Timer fadeInTimer = new Timer(30, null);
                    fadeInTimer.addActionListener(new ActionListener() {
                        private float fadeInOpacity = 0.0f;

                        @Override
                        public void actionPerformed(ActionEvent evt) {
                            fadeInOpacity += 0.05f;

                            if (fadeInOpacity >= 1) {
                                fadeInOpacity = 1;
                                fadeInTimer.stop();
                            }

                            label.setForeground(new Color(
                                    colorOriginal.getRed(),
                                    colorOriginal.getGreen(),
                                    colorOriginal.getBlue(),
                                    (int) (fadeInOpacity * 255)
                            ));
                        }
                    });
                    fadeInTimer.start();
                } else {
                    label.setForeground(new Color(
                            colorOriginal.getRed(),
                            colorOriginal.getGreen(),
                            colorOriginal.getBlue(),
                            (int) (opacity * 255)
                    ));
                }
            }
        });
        fadeOutTimer.start();
    }
}
