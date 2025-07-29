
package ControladoresU2;

import Controlador.ControladorDashboard;
import Controlador.Controlador_Unidades;
import ControladoresU2.ControladorUnidad2;
import Modelo.Modelo_ProgresoUnidad2;
import Vista.Estudiante.Dashboard;
import Vista.Vista_ActividadMorfemasUnidad2;
import Vista.Vista_Unidad2;
import java.awt.Color;
import java.sql.Connection;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Controlador_ActividadMorfemasU2 {

    private final Vista_ActividadMorfemasUnidad2 vista;
    private final Dashboard dashboard;
    private final Connection conexion;
    private final ControladorDashboard controladorDashboard;
    private final Controlador_Unidades controladorUnidades;

    private final Map<JTextField, String> respuestas = new LinkedHashMap<>();
    private final Map<JTextField, String> traducciones = new LinkedHashMap<>();
    private final Map<JTextField, String> etiquetas = new LinkedHashMap<>();

    public Controlador_ActividadMorfemasU2(Vista_ActividadMorfemasUnidad2 vista, Dashboard dashboard,
                                         Connection conexion, ControladorDashboard controladorDashboard,
                                         Controlador_Unidades controladorUnidades) {
        this.vista = vista;
        this.dashboard = dashboard;
        this.conexion = conexion;
        this.controladorDashboard = controladorDashboard;
        this.controladorUnidades = controladorUnidades;
        
        inicializarFrases();
        inicializarOpciones();
        agregarEventos();
    }

    private void inicializarFrases() {
        // Oración 1
        vista.getjTextField1TextoMorfemas1().setText("Ñuka");
        vista.getjTextField1TextoMorfemas2().setText("mikuani");
        vista.getjTextField1TextoMorfemas1().setEditable(false);
        vista.getjTextField1TextoMorfemas2().setEditable(false);
        respuestas.put(vista.getjTextField1arrastraropcion2(), "mama");
        etiquetas.put(vista.getjTextField1arrastraropcion2(), "Oración 1");
        traducciones.put(vista.getjTextField1arrastraropcion2(), "Yo como con mi mamá");

        // Oración 2
        vista.getjTextField1TextoMorfemas5().setText("Ñukapak");
        vista.getjTextField1TextoMorfemas4().setText("Diego-mi kan");
        vista.getjTextField1TextoMorfemas5().setEditable(false);
        vista.getjTextField1TextoMorfemas4().setEditable(false);
        respuestas.put(vista.getjTextField1arrastraropcion3(), "shutika");
        etiquetas.put(vista.getjTextField1arrastraropcion3(), "Oración 2");
        traducciones.put(vista.getjTextField1arrastraropcion3(), "Mi nombre es Diego");

        // Oración 3
        vista.getjTextField1TextoMorfemas7().setText("Ñuka ishkay waminsi");
        vista.getjTextField1TextoMorfemas6().setText("rantishkani");
        vista.getjTextField1TextoMorfemas7().setEditable(false);
        vista.getjTextField1TextoMorfemas6().setEditable(false);
        respuestas.put(vista.getjTextField1arrastraropcion4(), "anaku");
        etiquetas.put(vista.getjTextField1arrastraropcion4(), "Oración 3");
        traducciones.put(vista.getjTextField1arrastraropcion4(), "Yo voy a comprar dos faldas rosadas");

        // Oración 4
        vista.getjTextField1TextoMorfemas8().setText("Ñukanchik");
        vista.getjTextField1TextoMorfemas9().setText("ushutawan purirkanchik");
        vista.getjTextField1TextoMorfemas8().setEditable(false);
        vista.getjTextField1TextoMorfemas9().setEditable(false);
        respuestas.put(vista.getjTextField1arrastraropcion5(), "kimsa");
        etiquetas.put(vista.getjTextField1arrastraropcion5(), "Oración 4");
        traducciones.put(vista.getjTextField1arrastraropcion5(), "Nosotros caminamos con tres zapatos");

        // Oración 5 (dos campos a llenar)
        vista.getjTextField1TextoMorfemas10().setText("Ñukanchik kimsa yana ushutawan,");
        vista.getjTextField1TextoMorfemas3().setText("ankas kushmami rantirkanchik");
        vista.getjTextField1TextoMorfemas10().setEditable(false);
        vista.getjTextField1TextoMorfemas3().setEditable(false);
        respuestas.put(vista.getjTextField1arrastraropcion6(), "shuk");
        respuestas.put(vista.getjTextField1arrastraropcion7(), "kushmami");
        etiquetas.put(vista.getjTextField1arrastraropcion6(), "Oración 5 - parte 1");
        etiquetas.put(vista.getjTextField1arrastraropcion7(), "Oración 5 - parte 2");
        traducciones.put(vista.getjTextField1arrastraropcion6(), "Compramos tres zapatos negros y una chompa azul");
        traducciones.put(vista.getjTextField1arrastraropcion7(), "Compramos tres zapatos negros y una chompa azul");

        // Oración 6
        vista.getjTextField1TextoMorfemas9().setText("Kikinka");
        vista.getjTextField1TextoMorfemas10().setText("kanki");
        vista.getjTextField1TextoMorfemas9().setEditable(false);
        vista.getjTextField1TextoMorfemas10().setEditable(false);
        respuestas.put(vista.getjTextField1arrastraropcion1(), "maymantatak");
        etiquetas.put(vista.getjTextField1arrastraropcion1(), "Oración 6");
        traducciones.put(vista.getjTextField1arrastraropcion1(), "¿De dónde eres tú?");
    }

    private void inicializarOpciones() {
        Set<String> usadas = new LinkedHashSet<>(respuestas.values());
        List<String> opciones = new ArrayList<>(usadas);
        Collections.shuffle(opciones);

        List<JTextField> camposOpciones = Arrays.asList(
            vista.getjTextField1OPCCIONAARRASTRAR1(),
            vista.getjTextField1OPCCIONAARRASTRAR2(),
            vista.getjTextField1OPCCIONAARRASTRAR3(),
            vista.getjTextField1OPCCIONAARRASTRAR4(),
            vista.getjTextField1OPCCIONAARRASTRAR5(),
            vista.getjTextField1OPCCIONAARRASTRAR6(),
            vista.getjTextField1OPCCIONAARRASTRAR7()
        );

        for (int i = 0; i < camposOpciones.size(); i++) {
            if (i < opciones.size()) {
                camposOpciones.get(i).setText(opciones.get(i));
            } else {
                camposOpciones.get(i).setText("");
            }
        }
    }

    private void agregarEventos() {
        vista.getjButton1ComprobarAudios().addActionListener(e -> verificarRespuestas());

        vista.getjButton1().addActionListener(e -> {
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

    private void verificarRespuestas() {
        boolean todoCorrecto = true;
        StringBuilder errores = new StringBuilder("❌ Respuestas incorrectas:\n");
        StringBuilder traduccionCorrecta = new StringBuilder("✅ ¡Correcto!\n");

        for (Map.Entry<JTextField, String> entry : respuestas.entrySet()) {
            JTextField campo = entry.getKey();
            String userResp = campo.getText().trim().toLowerCase();
            String correcta = entry.getValue().trim().toLowerCase();

            if (userResp.equals(correcta)) {
                campo.setBackground(Color.GREEN);
                traduccionCorrecta.append("🔸 ").append(traducciones.get(campo)).append("\n");
            } else {
                campo.setBackground(Color.RED);
                todoCorrecto = false;
                errores.append("🔹 ").append(etiquetas.get(campo))
                        .append(": escribiste '").append(userResp.isEmpty() ? "(vacío)" : userResp)
                        .append("', debía ser '").append(correcta).append("'\n");
            }
        }

        if (todoCorrecto) {
            JOptionPane.showMessageDialog(vista, traduccionCorrecta.toString());
            vista.getjButton1().setEnabled(true);
        } else {
            JOptionPane.showMessageDialog(vista, errores.toString());
        }
    }
}