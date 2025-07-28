/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// ControladorMemorama.java (nuevo)
package Controlador;

import Modelo.Memorama;
import Vista.Estudiante.Dashboard;
import VistaJuegos.Vista_JuegoMemorama;
import VistaJuegos.Vista_SelecciondeJuegos;
import java.awt.Color;
import java.io.File;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ControladorMemorama {

    private Vista_JuegoMemorama vista;
    private Dashboard dashboard;
    private Memorama modelo;
    private int primeraSeleccion = -1;
    private int segundaSeleccion = -1;
    private int paresEncontrados = 0;
    private final int TOTAL_PARES = 6;
    private boolean juegoCompletado = false;

    public ControladorMemorama(Vista_JuegoMemorama vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;
        this.modelo = new Memorama();
        inicializarJuego();
        agregarEventos();
    }

    private ImageIcon cargarImagen(String nombreArchivo) {
        String rutaBase = "src/Imagenes/ImagenesUnidad2/";
        String rutaCompleta = rutaBase + nombreArchivo;
        try {
            return new ImageIcon(rutaCompleta);
        } catch (Exception e) {
            System.err.println("Error cargando imagen: " + rutaCompleta);
            // Imagen de placeholder en caso de error
            return new ImageIcon();
        }
    }

    private void inicializarJuego() {
        List<String> imagenes = modelo.obtenerImagenesAnimales();
        imagenes.addAll(imagenes); // Duplicar para hacer pares
        Collections.shuffle(imagenes);
        
        JLabel[] labels = getLabels();
        
        // Cargar reverso para todas las cartas
        ImageIcon reverso = new ImageIcon("src/Imagenes/Juegos/reverso.png");
        
        for (int i = 0; i < 12; i++) {
            labels[i].setIcon(reverso);
            labels[i].putClientProperty("imagen", imagenes.get(i));
            labels[i].putClientProperty("indice", i);
            labels[i].putClientProperty("volteada", false);
            labels[i].putClientProperty("emparejado", false);
            labels[i].setBackground(null);
            labels[i].setOpaque(true); // Para que se vea el color de fondo
        }
        
        juegoCompletado = false;
        paresEncontrados = 0;
    }

    private JLabel[] getLabels() {
        return new JLabel[]{
            vista.getjLabelImagen1(), vista.getjLabelImagen2(), vista.getjLabelImagen3(),
            vista.getjLabelImagen4(), vista.getjLabelImagen5(), vista.getjLabelImagen6(),
            vista.getjLabel2ImagenIgual1(), vista.getjLabel2ImagenIgual2(), vista.getjLabel2ImagenIgual3(),
            vista.getjLabel2ImagenIgual4(), vista.getjLabel2ImagenIgual5(), vista.getjLabel2ImagenIgual6()
        };
    }

    private void agregarEventos() {
        JLabel[] labels = getLabels();
        
        for (JLabel label : labels) {
            label.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (!juegoCompletado) {
                        manejarClicImagen(label);
                    }
                }
            });
        }
        
        vista.getjButton1ComprobarRespuestas().addActionListener(e -> comprobarRespuestas());
        
        vista.getjButton1Regresaralavistaselecciondejuegos().addActionListener(e -> {
            regresarASeleccionJuegos();
        });
    }

    private void manejarClicImagen(JLabel label) {
        if ((Boolean) label.getClientProperty("volteada") || 
            (Boolean) label.getClientProperty("emparejado")) return;
            
        if (primeraSeleccion != -1 && segundaSeleccion != -1) return;

        String nombreImagen = (String) label.getClientProperty("imagen");
        label.setIcon(cargarImagen(nombreImagen));
        label.putClientProperty("volteada", true);

        if (primeraSeleccion == -1) {
            primeraSeleccion = (Integer) label.getClientProperty("indice");
        } else {
            segundaSeleccion = (Integer) label.getClientProperty("indice");
            new Thread(this::comprobarPareja).start();
        }
    }

    private void comprobarPareja() {
        if (primeraSeleccion == -1 || segundaSeleccion == -1) return;

        JLabel[] labels = getLabels();
        JLabel primera = labels[primeraSeleccion];
        JLabel segunda = labels[segundaSeleccion];
        
        String img1 = (String) primera.getClientProperty("imagen");
        String img2 = (String) segunda.getClientProperty("imagen");

        if (img1.equals(img2)) {
            SwingUtilities.invokeLater(() -> {
                primera.setBackground(Color.GREEN);
                segunda.setBackground(Color.GREEN);
                primera.putClientProperty("emparejado", true);
                segunda.putClientProperty("emparejado", true);
            });
            
            paresEncontrados++;
            primeraSeleccion = -1;
            segundaSeleccion = -1;
            
            if (paresEncontrados == TOTAL_PARES) {
                juegoCompletado = true;
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(vista, "¡Felicidades! Has completado el memorama");
                });
            }
        } else {
            try {
                Thread.sleep(1000); // Pausa para que el jugador vea las cartas
            } catch (InterruptedException ex) {}
            
            SwingUtilities.invokeLater(() -> {
                // Volver a mostrar el reverso
                ImageIcon reverso = new ImageIcon("src/Imagenes/Juegos/reverso.png");
                primera.setIcon(reverso);
                segunda.setIcon(reverso);
                primera.putClientProperty("volteada", false);
                segunda.putClientProperty("volteada", false);
                primeraSeleccion = -1;
                segundaSeleccion = -1;
            });
        }
    }
    
    private void comprobarRespuestas() {
        JLabel[] labels = getLabels();
        int cartasEmparejadas = 0;
        
        for (JLabel label : labels) {
            if ((Boolean) label.getClientProperty("emparejado")) {
                cartasEmparejadas++;
            }
        }
        
        int paresCompletos = cartasEmparejadas / 2;
        JOptionPane.showMessageDialog(vista, 
            "Pares encontrados: " + paresCompletos + " de " + TOTAL_PARES,
            "Progreso del juego", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void regresarASeleccionJuegos() {
        Vista_SelecciondeJuegos seleccion = new Vista_SelecciondeJuegos();
        new ControladorSeleccionJuegos(seleccion, dashboard);
        dashboard.mostrarVista(seleccion);
    }
}