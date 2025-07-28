package Controlador;

import Modelo.Juego;
import VistaJuegos.VistaJuego;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ControladorJuego {

    private VistaJuego vista;
    private Juego modelo;
    private List<Juego> juegosActuales;
    private final Map<JLabel, String> asociaciones = new HashMap<>();
    private JLabel imagenSeleccionada = null;

    public ControladorJuego(VistaJuego vista, Juego modelo) {
        this.vista = vista;
        this.modelo = modelo;
        cargarElementosJuego();
        agregarEventos();
    }

    private void agregarEventos() {
    JLabel[] labels = {vista.getLblimagen1(), vista.getLblimagen2(), vista.getLblimagen3()};
    JButton[] botones = {vista.getBtn1(), vista.getBtn2(), vista.getBtn3()};

    for (JLabel lbl : labels) {
        lbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Evitar que se seleccione una imagen ya asociada
                if (asociaciones.containsKey(lbl)) {
                    vista.mostrarMensaje("Esta imagen ya tiene una palabra asignada.");
                    return;
                }

                // Limpiar bordes anteriores
                for (JLabel l : labels) {
                    l.setBorder(null);
                }

                imagenSeleccionada = lbl;
                lbl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLUE, 3)); // destacar imagen
                vista.mostrarMensaje("Imagen seleccionada. Ahora elige una palabra.");
            }
        });
    }

    for (JButton btn : botones) {
        btn.addActionListener(e -> {
            if (imagenSeleccionada != null) {
                // Asociar palabra
                asociaciones.put(imagenSeleccionada, btn.getText());

                // Cambiar color del botón y desactivarlo
                btn.setBackground(java.awt.Color.GREEN);
                btn.setEnabled(false);

                // Limpiar selección de imagen
                imagenSeleccionada.setBorder(null);
                imagenSeleccionada = null;
            } else {
                vista.mostrarMensaje("Primero selecciona una imagen.");
            }
        });
    }

    vista.getBtnVerificar().addActionListener(e -> verificarResultados());
}

    public void cargarElementosJuego() {
    asociaciones.clear();
    juegosActuales = modelo.obtenerTodoAleatorio(3);

    JLabel[] labels = {vista.getLblimagen1(), vista.getLblimagen2(), vista.getLblimagen3()};
    JButton[] botones = {vista.getBtn1(), vista.getBtn2(), vista.getBtn3()};

    for (int i = 0; i < juegosActuales.size(); i++) {
        Juego juego = juegosActuales.get(i);
        String rutaCompleta = "/" + juego.getRutaImagen();
        URL urlImagen = getClass().getResource(rutaCompleta);
        if (urlImagen == null) {
            System.out.println("No se encontró la imagen: " + rutaCompleta);
        } else {
            ImageIcon icon = new ImageIcon(urlImagen);
            labels[i].setIcon(icon);
            labels[i].setBorder(null); // quitar bordes previos
            labels[i].putClientProperty("palabra", juego.getPalabra());
        }
    }

    List<String> palabras = new ArrayList<>();
    for (Juego juego : juegosActuales) {
        palabras.add(juego.getPalabra());
    }
    Collections.shuffle(palabras);

    for (int i = 0; i < botones.length; i++) {
        botones[i].setText(palabras.get(i));
        botones[i].setEnabled(true); // habilitar botones de nuevo
        botones[i].setBackground(null); // quitar color de selección
    }
}

    private void verificarResultados() {
        if (asociaciones.size() < juegosActuales.size()) {
            vista.mostrarMensaje("Debes asociar todas las imágenes antes de verificar.");
            return;
        }

        int correctos = 0;
        StringBuilder resultado = new StringBuilder();

        for (Map.Entry<JLabel, String> entry : asociaciones.entrySet()) {
            JLabel label = entry.getKey();
            String seleccion = entry.getValue();
            String correcta = (String) label.getClientProperty("palabra");

            if (seleccion.equalsIgnoreCase(correcta)) {
                correctos++;
                resultado.append("✅ Correcto: ").append(correcta).append("\n");
            } else {
                resultado.append("❌ Incorrecto: elegiste ").append(seleccion)
                        .append(", era ").append(correcta).append("\n");
            }
        }

        resultado.append("\nTotal correctos: ").append(correctos).append(" de ").append(juegosActuales.size());

        int opcion = JOptionPane.showOptionDialog(
                vista,
                resultado.toString(),
                "Resultados",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"🔁 Repetir", "❌ Cancelar"},
                "🔁 Repetir"
        );

        if (opcion == JOptionPane.YES_OPTION) {
            cargarElementosJuego();
        } else {
            vista.mostrarMensaje("Gracias por jugar.");
        }
    }
} 
