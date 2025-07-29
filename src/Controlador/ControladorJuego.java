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
import java.awt.Image;

public class ControladorJuego {

    private VistaJuego vista;
    private Juego modelo;
    private List<Juego> juegosActuales;
    private final Map<JLabel, String> asociaciones = new HashMap<>();
    private JLabel imagenSeleccionada = null;

    public ControladorJuego(VistaJuego vista, Juego modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        // Validar que la vista y modelo no sean null
        if (this.vista == null) {
            throw new IllegalArgumentException("La vista no puede ser null");
        }
        if (this.modelo == null) {
            throw new IllegalArgumentException("El modelo no puede ser null");
        }
        
        System.out.println("✅ ControladorJuego inicializado correctamente");
        
        try {
            cargarElementosJuego();
            agregarEventos();
            System.out.println("✅ Elementos del juego cargados y eventos configurados");
        } catch (Exception e) {
            System.err.println("❌ Error al inicializar el juego: " + e.getMessage());
            e.printStackTrace();
            vista.mostrarMensaje("Error al cargar el juego: " + e.getMessage());
        }
    }

    private void agregarEventos() {
    JLabel[] labels = {
        vista.getLblimagen1(), vista.getLblimagen2(), vista.getLblimagen3(), vista.getLblimagen4(),
        vista.getLblimagen5(), vista.getLblimagen6(), vista.getLblimagen7(), vista.getLblimagen8()
    };
    JButton[] botones = {
        vista.getBtn1(), vista.getBtn2(), vista.getBtn3(), vista.getBtn4(),
        vista.getBtn5(), vista.getBtn6(), vista.getBtn7(), vista.getBtn8()
    };

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
        System.out.println("🔄 Iniciando carga de elementos del juego...");
        
        try {
            asociaciones.clear();
            
            // Obtener datos del modelo (8 elementos para llenar todos los componentes)
            juegosActuales = modelo.obtenerTodoAleatorio(8);
            
            // Validar que se obtuvieron datos
            if (juegosActuales == null || juegosActuales.isEmpty()) {
                System.err.println("❌ No se pudieron cargar datos del juego desde la base de datos");
                vista.mostrarMensaje("<html><div style='text-align: center;'>" +
                    "<h3>🚫 Error de Datos</h3>" +
                    "<p>No se pudieron cargar los datos del juego.</p>" +
                    "<p>Verifica la conexión a la base de datos.</p>" +
                    "</div></html>");
                return;
            }
            
            System.out.println("✅ Se cargaron " + juegosActuales.size() + " elementos del juego");
            
            // Obtener componentes de la vista (8 labels y 8 botones)
            JLabel[] labels = {
                vista.getLblimagen1(), vista.getLblimagen2(), vista.getLblimagen3(), vista.getLblimagen4(),
                vista.getLblimagen5(), vista.getLblimagen6(), vista.getLblimagen7(), vista.getLblimagen8()
            };
            JButton[] botones = {
                vista.getBtn1(), vista.getBtn2(), vista.getBtn3(), vista.getBtn4(),
                vista.getBtn5(), vista.getBtn6(), vista.getBtn7(), vista.getBtn8()
            };
            
            // Validar que los componentes existen
            for (int i = 0; i < labels.length; i++) {
                if (labels[i] == null) {
                    System.err.println("❌ Label " + (i+1) + " es null");
                    return;
                }
                if (botones[i] == null) {
                    System.err.println("❌ Botón " + (i+1) + " es null");
                    return;
                }
            }
            
            // Cargar imágenes
            for (int i = 0; i < juegosActuales.size() && i < labels.length; i++) {
                Juego juego = juegosActuales.get(i);
                
                // Limpiar label primero
                labels[i].setIcon(null);
                labels[i].setBorder(null);
                labels[i].setText("");
                
                // Intentar cargar imagen
                String rutaImagen = juego.getRutaImagen();
                if (rutaImagen != null && !rutaImagen.trim().isEmpty()) {
                    // Asegurar que la ruta comience con /
                    String rutaCompleta = rutaImagen.startsWith("/") ? rutaImagen : "/" + rutaImagen;
                    
                    System.out.println("🔍 Buscando imagen: " + rutaCompleta);
                    
                    URL urlImagen = getClass().getResource(rutaCompleta);
                    if (urlImagen != null) {
                        try {
                            ImageIcon icon = new ImageIcon(urlImagen);
                            
                            // Redimensionar imagen si es necesario
                            if (icon.getIconWidth() > 200 || icon.getIconHeight() > 200) {
                                Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                                icon = new ImageIcon(img);
                            }
                            
                            labels[i].setIcon(icon);
                            labels[i].putClientProperty("palabra", juego.getPalabra());
                            System.out.println("✅ Imagen cargada: " + rutaCompleta + " -> " + juego.getPalabra());
                        } catch (Exception e) {
                            System.err.println("❌ Error al procesar imagen " + rutaCompleta + ": " + e.getMessage());
                            labels[i].setText("Imagen no disponible");
                            labels[i].putClientProperty("palabra", juego.getPalabra());
                        }
                    } else {
                        System.err.println("❌ No se encontró la imagen: " + rutaCompleta);
                        labels[i].setText("Imagen: " + juego.getPalabra());
                        labels[i].putClientProperty("palabra", juego.getPalabra());
                    }
                } else {
                    System.err.println("❌ Ruta de imagen vacía para: " + juego.getPalabra());
                    labels[i].setText("Sin imagen: " + juego.getPalabra());
                    labels[i].putClientProperty("palabra", juego.getPalabra());
                }
            }
            
            // Preparar palabras para los botones
            List<String> palabras = new ArrayList<>();
            for (Juego juego : juegosActuales) {
                if (juego.getPalabra() != null && !juego.getPalabra().trim().isEmpty()) {
                    palabras.add(juego.getPalabra());
                }
            }
            
            if (palabras.isEmpty()) {
                System.err.println("❌ No hay palabras válidas para los botones");
                vista.mostrarMensaje("Error: No se encontraron palabras válidas para el juego");
                return;
            }
            
            // Mezclar palabras
            Collections.shuffle(palabras);
            
            // Configurar botones
            for (int i = 0; i < botones.length; i++) {
                if (i < palabras.size()) {
                    botones[i].setText(palabras.get(i));
                    botones[i].setEnabled(true);
                    botones[i].setBackground(null);
                    botones[i].setVisible(true);
                    System.out.println("✅ Botón " + (i+1) + " configurado: " + palabras.get(i));
                } else {
                    botones[i].setText("");
                    botones[i].setEnabled(false);
                    botones[i].setVisible(false);
                }
            }
            
            System.out.println("✅ Elementos del juego cargados exitosamente");
            
        } catch (Exception e) {
            System.err.println("❌ Error al cargar elementos del juego: " + e.getMessage());
            e.printStackTrace();
            vista.mostrarMensaje("Error al cargar el juego: " + e.getMessage());
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
