package ControladoresU2;

import Controlador.ControladorDashboard;
import Controlador.Controlador_Unidades;
import ControladoresU2.ControladorUnidad2;
import Modelo.Modelo_AnimacionTextoUnidad2;
import Modelo.Modelo_AnimalesUnidad2;
import Modelo.Modelo_ProgresoUnidad2;
import Vista.*;
import Vista.Estudiante.Dashboard;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Controlador_ActividadAnimalesdelaSierraUnidad2 {

    private final Dashboard dashboard;
    private final Vista_AnimalesdelaSierraUnidad2 vistaIntro;
    private final Vista_AnimalesdelaSierrainformacion2Unidad2 vistaInfo;
    private final Vista_ActividadAnimalesdelaSierraUnidad2 vistaActividad;
    private final Map<JLabel, Modelo_AnimalesUnidad2.Animal> asignaciones = new HashMap<>();
    private final Connection conexion;
    private final ControladorDashboard controladorDashboard;
    private final Controlador_Unidades controladorUnidades;
    private JButton botonSeleccionado = null;

    // Rutas corregidas para imágenes
    private static final String RUTA_BASE_PROYECTO = "C:/Users/diego/Desktop/ProyectoInti/";
    private static final String DIRECTORIO_IMAGENES = "src/Imagenes/ImagenesUnidad2/";
    private static final String RUTA_IMAGENES = RUTA_BASE_PROYECTO + DIRECTORIO_IMAGENES;
    private static final String IMAGEN_DEFAULT = "default.png";

    public Controlador_ActividadAnimalesdelaSierraUnidad2(Dashboard dashboard, boolean ignorar, 
            Connection conexion, ControladorDashboard controladorDashboard,
            Controlador_Unidades controladorUnidades) {
        this.dashboard = dashboard;
        this.conexion = conexion;
        this.controladorDashboard = controladorDashboard;
        this.controladorUnidades = controladorUnidades;
        this.vistaIntro = new Vista_AnimalesdelaSierraUnidad2();
        this.vistaInfo = new Vista_AnimalesdelaSierrainformacion2Unidad2();
        this.vistaActividad = new Vista_ActividadAnimalesdelaSierraUnidad2();

        iniciarAnimacionEntrada();
        dashboard.mostrarVista(vistaIntro);

        configurarEventos();
        configurarActividad();
    }

    private void configurarEventos() {
        vistaIntro.getjButton1().addActionListener(e -> dashboard.mostrarVista(vistaInfo));
        vistaInfo.getjButton1Continuar().addActionListener(e -> dashboard.mostrarVista(vistaActividad));
        vistaActividad.getjButton1ComprobarAudios().addActionListener(e -> verificarRespuestas());
        
        vistaActividad.getjButton1CONTINUAR().addActionListener(e -> {
            try {
                // Marcar actividad como completada
                Modelo_ProgresoUnidad2.incrementarProgreso(dashboard.getCorreoUsuario(), 20);
                
                // Crear y mostrar la vista de la unidad 2
                Vista_Unidad2 vistaUnidad2 = new Vista_Unidad2();
                new ControladorUnidad2(
                    vistaUnidad2,
                    conexion,
                    controladorDashboard,
                    dashboard.getCorreoUsuario(),
                    controladorUnidades
                );
                
                dashboard.mostrarVista(vistaUnidad2);
                JOptionPane.showMessageDialog(dashboard, "¡Actividad completada con éxito!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dashboard, 
                    "Error al completar la actividad: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void iniciarAnimacionEntrada() {
        JLabel[] labels = {
                vistaIntro.getjLabel2debeaparecer1(),
                vistaIntro.getjLabel3debeaparecersegundo(),
                vistaIntro.getjLabel1debeaparecertercero2(),
                vistaIntro.getjLabel1debeaparecertercero(),
                vistaIntro.getjLabel1debeaparecercuarto(),
                vistaIntro.getjLabel1debeaparecercquinto1(),
                vistaIntro.getjLabel1debeaparecercsexto1(),
                vistaIntro.getjLabel1debeaparecercseptimo1(),
                vistaIntro.getjLabel1debeaparecercsoctavo1(),
                vistaIntro.getjLabel1debeaparecercsonoveno(),
                vistaIntro.getjLabel1debeaparecercdecimo()
        };
        new Modelo_AnimacionTextoUnidad2(labels).iniciarAnimacion();
    }

    private void configurarActividad() {
        List<Modelo_AnimalesUnidad2.Animal> animalesDom = Modelo_AnimalesUnidad2.obtenerTodosAnimalesPorTipo("domestico");
        List<Modelo_AnimalesUnidad2.Animal> animalesSilv = Modelo_AnimalesUnidad2.obtenerTodosAnimalesPorTipo("silvestre");

        Collections.shuffle(animalesDom);
        Collections.shuffle(animalesSilv);

        List<Modelo_AnimalesUnidad2.Animal> seleccionados = new ArrayList<>();
        seleccionados.addAll(animalesDom.subList(0, Math.min(3, animalesDom.size())));
        seleccionados.addAll(animalesSilv.subList(0, Math.min(3, animalesSilv.size())));
        Collections.shuffle(seleccionados);

        // Configuración de labels y botones
        List<JLabel> labels = Arrays.asList(
            vistaActividad.getjLabel1animalesdomesticosimagen1(),
            vistaActividad.getjLabel1animalesdomesticosimagen3(),
            vistaActividad.getjLabel1animalesdomesticosimagen7(),
            vistaActividad.getjLabel1animalessilvestres2(),
            vistaActividad.getjLabel1animalessilvestres4(),
            vistaActividad.getjLabel1animalessilvestres6()
        );

        List<JButton> botones = Arrays.asList(
            vistaActividad.getjButton1animaldomestico1(),
            vistaActividad.getjButton1animaldomestico2(),
            vistaActividad.getjButton1animaldomestico3(),
            vistaActividad.getjButton1animaldomestico4(),
            vistaActividad.getjButton1animaldomestico5(),
            vistaActividad.getjButton1animaldomestico6()
        );

        // Desactivar botones no utilizados
        vistaActividad.getjButton1animaldomestico7().setEnabled(false);
        vistaActividad.getjButton1animalessilvestres1().setEnabled(false);
        vistaActividad.getjButton1animalessilvestres2().setEnabled(false);
        vistaActividad.getjButton1animalessilvestres3().setEnabled(false);

        // Configurar cada par label-botón
        for (int i = 0; i < 6; i++) {
            Modelo_AnimalesUnidad2.Animal animal = seleccionados.get(i);
            JLabel label = labels.get(i);
            JButton boton = botones.get(i);

            configurarImagenAnimal(label, animal);
            configurarBotonAnimal(boton, animal, label);
        }
    }

    private void configurarImagenAnimal(JLabel label, Modelo_AnimalesUnidad2.Animal animal) {
        String rutaCompleta = RUTA_IMAGENES + animal.rutaImagen;
        File archivo = new File(rutaCompleta);
        
        ImageIcon icon;
        if (archivo.exists()) {
            icon = new ImageIcon(rutaCompleta);
        } else {
            String rutaDefault = RUTA_IMAGENES + IMAGEN_DEFAULT;
            System.err.println("⚠️ Imagen no encontrada: " + rutaCompleta);
            System.err.println("   Usando imagen por defecto: " + rutaDefault);
            icon = new ImageIcon(rutaDefault);
        }
        
        Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(img));
        label.setText("");
        label.setName(animal.nombreEsp);
        asignaciones.put(label, animal);
    }

    private void configurarBotonAnimal(JButton boton, Modelo_AnimalesUnidad2.Animal animal, JLabel label) {
        boton.setText(animal.nombreKichwa);
        boton.setEnabled(true);

        boton.addActionListener(e -> botonSeleccionado = boton);

        label.setBorder(BorderFactory.createEmptyBorder());
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (botonSeleccionado != null) {
                    if (botonSeleccionado.getText().equalsIgnoreCase(animal.nombreKichwa)) {
                        label.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
                    } else {
                        label.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    }
                    botonSeleccionado = null;
                }
            }
        });
    }

    private void verificarRespuestas() {
        boolean correcto = true;
        StringBuilder traducciones = new StringBuilder("✔ Traducciones:\n");

        for (Map.Entry<JLabel, Modelo_AnimalesUnidad2.Animal> entry : asignaciones.entrySet()) {
            JLabel label = entry.getKey();
            Modelo_AnimalesUnidad2.Animal animal = entry.getValue();

            Border borde = label.getBorder();
            if (borde == null || 
                !(borde instanceof javax.swing.border.LineBorder) || 
                !((javax.swing.border.LineBorder) borde).getLineColor().equals(Color.GREEN)) {
                correcto = false;
            }

            traducciones.append("✔ ")
                       .append(animal.nombreEsp)
                       .append(" = ")
                       .append(animal.nombreKichwa)
                       .append("\n");
        }

        if (correcto) {
            JOptionPane.showMessageDialog(dashboard, traducciones.toString());
            vistaActividad.getjButton1CONTINUAR().setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(dashboard, 
                "❌ Algunos animales están incorrectos. Intenta de nuevo.\n" +
                traducciones.toString());
        }
    }
}