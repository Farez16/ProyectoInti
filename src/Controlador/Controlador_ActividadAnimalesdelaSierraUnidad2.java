/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.Estudiante.Dashboard;
import Vista.*;
import Modelo.Modelo_AnimacionTextoUnidad2;
import Modelo.Modelo_ProgresoUnidad2;
import Modelo.Modelo_AnimalesUnidad2;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controlador_ActividadAnimalesdelaSierraUnidad2 {

    private final Dashboard dashboard;
    private final Vista_AnimalesdelaSierraUnidad2 vistaIntro;
    private final Vista_AnimalesdelaSierrainformacion2Unidad2 vistaInfo;
    private final Vista_ActividadAnimalesdelaSierraUnidad2 vistaActividad;

    private final Map<JLabel, JTextField> asignacionesCorrectas = new HashMap<>();
    private final Map<JLabel, Point> posicionesIniciales = new HashMap<>();
    private final Map<JLabel, String> nombresKichwa = new HashMap<>();

    public Controlador_ActividadAnimalesdelaSierraUnidad2(Dashboard dashboard) {
        this.dashboard = dashboard;
        this.vistaIntro = new Vista_AnimalesdelaSierraUnidad2();
        this.vistaInfo = new Vista_AnimalesdelaSierrainformacion2Unidad2();
        this.vistaActividad = new Vista_ActividadAnimalesdelaSierraUnidad2();

        iniciarAnimacionEntrada();
        dashboard.mostrarVista(vistaIntro);

        vistaIntro.getjButton1().addActionListener(e -> dashboard.mostrarVista(vistaInfo));
        vistaActividad.getjButton1ComprobarAudios().addActionListener(e -> verificarRespuestas());

        configurarDragAndDrop();
    }

    private void iniciarAnimacionEntrada() {
        JLabel[] labels = {
            vistaIntro.getjLabel1debeaparecercdecimo(),
            vistaIntro.getjLabel1debeaparecercquinto1(),
            vistaIntro.getjLabel1debeaparecercseptimo1(),
            vistaIntro.getjLabel1debeaparecercsexto1(),
            vistaIntro.getjLabel1debeaparecercsoctavo1(),
            vistaIntro.getjLabel1debeaparecercsonoveno(),
            vistaIntro.getjLabel1debeaparecercuarto(),
            vistaIntro.getjLabel1debeaparecertercero(),
            vistaIntro.getjLabel1debeaparecertercero2(),
            vistaIntro.getjLabel2debeaparecer1(),
            vistaIntro.getjLabel3debeaparecersegundo()
        };
        Modelo_AnimacionTextoUnidad2 anim = new Modelo_AnimacionTextoUnidad2(labels);
        anim.iniciarAnimacion();
    }

    private void configurarDragAndDrop() {
        List<Modelo_AnimalesUnidad2.Animal> silvestres = Modelo_AnimalesUnidad2.obtenerAnimalesAleatorios("silvestre", 3);
        List<Modelo_AnimalesUnidad2.Animal> domesticos = Modelo_AnimalesUnidad2.obtenerAnimalesAleatorios("domestico", 3);

        JLabel[] labelsSilvestres = {
            vistaActividad.getjLabel1AnimalesSilvestresaarrastar1(),
            vistaActividad.getjLabel1AnimalesSilvestresaarrastar2(),
            vistaActividad.getjLabel1AnimalesSilvestresaarrastar3()
        };
        JTextField[] camposSilvestres = {
            vistaActividad.getjTextField1AnimalesSilvestresaarrastar1(),
            vistaActividad.getjTextField1AnimalesSilvestresaarrastar2(),
            vistaActividad.getjTextField1AnimalesSilvestresaarrastar3()
        };

        JLabel[] labelsDomesticos = {
            vistaActividad.getjLabel1Animalesdosmesticosimagearrastar1(),
            vistaActividad.getjLabel1Animalesdosmesticosimagearrastar2(),
            vistaActividad.getjLabel1Animalesdosmesticosimagearrastar3()
        };
        JTextField[] camposDomesticos = {
            vistaActividad.getjTextField1Animalesdosmesticosimagearrastar1(),
            vistaActividad.getjTextField1Animalesdosmesticosimagearrastar2(),
            vistaActividad.getjTextField1Animalesdosmesticosimagearrastar3()
        };

        for (int i = 0; i < 3; i++) {
            ImageIcon icon1 = new ImageIcon("src/Imagenes/ImagenesUnidad2/" + silvestres.get(i).rutaImagen);
            labelsSilvestres[i].setIcon(icon1);
            asignar(labelsSilvestres[i], camposSilvestres[i], silvestres.get(i).nombreEsp, silvestres.get(i).nombreKichwa);

            ImageIcon icon2 = new ImageIcon("src/Imagenes/ImagenesUnidad2/" + domesticos.get(i).rutaImagen);
            labelsDomesticos[i].setIcon(icon2);
            asignar(labelsDomesticos[i], camposDomesticos[i], domesticos.get(i).nombreEsp, domesticos.get(i).nombreKichwa);
        }

        for (JLabel label : asignacionesCorrectas.keySet()) {
            posicionesIniciales.put(label, label.getLocation());
            habilitarDrag(label);
        }
    }

    private void asignar(JLabel label, JTextField campo, String esp, String kichwa) {
        asignacionesCorrectas.put(label, campo);
        nombresKichwa.put(label, kichwa);
    }

    private void habilitarDrag(JLabel label) {
        label.setTransferHandler(new TransferHandler("icon"));
        label.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JComponent comp = (JComponent) e.getSource();
                TransferHandler handler = comp.getTransferHandler();
                handler.exportAsDrag(comp, e, TransferHandler.COPY);
            }
        });
    }

    private void mostrarNombreTemporal(JLabel label, String nombre) {
        JLabel etiqueta = new JLabel(nombre);
        etiqueta.setForeground(Color.BLUE);
        etiqueta.setBounds(label.getX(), label.getY() - 20, 100, 20);
        vistaActividad.add(etiqueta);
        vistaActividad.repaint();

        new Timer(2000, e -> {
            vistaActividad.remove(etiqueta);
            vistaActividad.repaint();
        }).start();
    }

    private void verificarRespuestas() {
        boolean todoCorrecto = true;
        for (Map.Entry<JLabel, JTextField> entry : asignacionesCorrectas.entrySet()) {
            JLabel label = entry.getKey();
            JTextField destino = entry.getValue();
            Point posicionCorrecta = destino.getLocation();

            if (label.getX() >= posicionCorrecta.getX() - 10 && label.getX() <= posicionCorrecta.getX() + 10 &&
                label.getY() >= posicionCorrecta.getY() - 10 && label.getY() <= posicionCorrecta.getY() + 10) {
                destino.setBackground(Color.GREEN);
            } else {
                destino.setBackground(Color.RED);
                label.setLocation(posicionesIniciales.get(label));
                todoCorrecto = false;
            }
        }

        if (todoCorrecto) {
            StringBuilder mensaje = new StringBuilder("\u00a1Excelente! Todos los animales est\u00e1n bien clasificados.\n\n");
            for (Map.Entry<JLabel, String> entry : nombresKichwa.entrySet()) {
                mensaje.append("\u2714 ").append(entry.getKey().getName()).append(" = ").append(entry.getValue()).append("\n");
            }
            JOptionPane.showMessageDialog(dashboard, mensaje.toString());
            vistaActividad.getjButton1CONTINUAR().setEnabled(true);
            Modelo_ProgresoUnidad2.guardarProgreso(dashboard.getCorreoUsuario(), 25);
        } else {
            JOptionPane.showMessageDialog(dashboard, "Algunos animales no est\u00e1n bien colocados. Int\u00e9ntalo de nuevo.");
        }
    }
}