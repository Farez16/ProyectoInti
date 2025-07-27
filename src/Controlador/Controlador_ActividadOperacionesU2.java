package Controlador;

import Modelo.*;
import Vista.Estudiante.Dashboard;
import Vista.Vista_ActividadOperacionesUnidad2;
import Vista.Vista_Unidad2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Controlador_ActividadOperacionesU2 {

    private final Vista_ActividadOperacionesUnidad2 vista;
    private final Dashboard dashboard;
    private final JTextField[] campoA;
    private final JTextField[] campoB;
    private final JTextField[] operadores;
    private final JTextField[] resultados;
    private final Map<Integer, String> mapaKichwa = new HashMap<>();
    private static final char[] OPERADORES = {'+', '-', '*', '/'};
    private final Random random = new Random();

    public Controlador_ActividadOperacionesU2(Vista_ActividadOperacionesUnidad2 vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;

        campoA = new JTextField[]{
            vista.getjTextField1PrimerNumero1(), vista.getjTextField1Numero2(), vista.getjTextField1Numero3(),
            vista.getjTextField1Numero4(), vista.getjTextField1Numero5(), vista.getjTextField1Numero6(),
            vista.getjTextField1Numero7(), vista.getjTextField1Numero8(), vista.getjTextField1Numero9(),
            vista.getjTextField1Numero10(), vista.getjTextField1Numero11(), vista.getjTextField1Numero12()
        };

        campoB = new JTextField[]{
            vista.getjTextField1Numero13(), vista.getjTextField1Numero14(), vista.getjTextField1Numero15(),
            vista.getjTextField1Numero16(), vista.getjTextField1Numero17(), vista.getjTextField1Numero18(),
            vista.getjTextField1Numero19(), vista.getjTextField1Numero20(), vista.getjTextField1Numero21(),
            vista.getjTextField1Numero22(), vista.getjTextField1Numero23(), vista.getjTextField1Numero24()
        };

        operadores = new JTextField[]{
            vista.getjTextFieldSumarestamultiplicaciondivision(), vista.getjTextFieldSumarestamultiplicaciondivision1(),
            vista.getjTextFieldSumarestamultiplicaciondivision2(), vista.getjTextFieldSumarestamultiplicaciondivision3(),
            vista.getjTextFieldSumarestamultiplicaciondivision4(), vista.getjTextFieldSumarestamultiplicaciondivision5(),
            vista.getjTextFieldSumarestamultiplicaciondivision6(), vista.getjTextFieldSumarestamultiplicaciondivision7(),
            vista.getjTextFieldSumarestamultiplicaciondivision8(), vista.getjTextFieldSumarestamultiplicaciondivision9(),
            vista.getjTextFieldSumarestamultiplicaciondivision10(), vista.getjTextFieldSumarestamultiplicaciondivision11()
        };

        resultados = new JTextField[]{
            vista.getjTextField1Resultado1(), vista.getjTextField1Resultado2(), vista.getjTextField1Resultado3(),
            vista.getjTextField1Resultado4(), vista.getjTextField1Resultado5(), vista.getjTextField1Resultado6(),
            vista.getjTextField1Resultado7(), vista.getjTextField1Resultado8(), vista.getjTextField1Resultado9(),
            vista.getjTextField1Resultado10(), vista.getjTextField1Resultado11(), vista.getjTextField1Resultado12()
        };

        inicializarMapaKichwa();
        generarOperaciones();

        vista.getjButton1ComprobarAudios().addActionListener(e -> verificarResultados());
        vista.getjButton1CONTINUAR().addActionListener(e -> irAUnidad2());
    }

    private void inicializarMapaKichwa() {
        mapaKichwa.put(0, "illak");
        mapaKichwa.put(1, "shuk");
        mapaKichwa.put(2, "ishkay");
        mapaKichwa.put(3, "kimsa");
        mapaKichwa.put(4, "chusku");
        mapaKichwa.put(5, "pichka");
        mapaKichwa.put(6, "sukta");
        mapaKichwa.put(7, "kanchis");
        mapaKichwa.put(8, "pusak");
        mapaKichwa.put(9, "iskun");
        mapaKichwa.put(10, "chunka");
    }

    private void generarOperaciones() {
        for (int i = 0; i < campoA.length; i++) {
            int num1, num2;
            char op;
            int resultado;
            
            do {
                op = OPERADORES[random.nextInt(OPERADORES.length)];
                
                switch(op) {
                    case '+':
                        num1 = random.nextInt(6); // 0-5
                        num2 = random.nextInt(11 - num1); // 0 a (10-num1)
                        resultado = num1 + num2;
                        break;
                    case '-':
                        num1 = random.nextInt(11); // 0-10
                        num2 = random.nextInt(num1 + 1); // 0 a num1
                        resultado = num1 - num2;
                        break;
                    case '*':
                        // Solo factores que den productos <= 10
                        int[] factores = {0, 1, 2, 5, 10};
                        num1 = factores[random.nextInt(factores.length)];
                        num2 = num1 == 0 ? random.nextInt(11) : random.nextInt(11 / num1 + 1);
                        resultado = num1 * num2;
                        break;
                    case '/':
                        // Solo divisiones exactas con resultados enteros
                        resultado = random.nextInt(6); // 0-5
                        num2 = random.nextInt(5) + 1; // 1-5 (evitar división por cero)
                        num1 = resultado * num2;
                        break;
                    default:
                        num1 = 0;
                        num2 = 0;
                        resultado = 0;
                }
            } while(resultado > 10); // Asegurar resultado <= 10

            campoA[i].setText(String.valueOf(num1));
            campoB[i].setText(String.valueOf(num2));
            operadores[i].setText(String.valueOf(op));
            
            campoA[i].setEditable(false);
            campoB[i].setEditable(false);
            operadores[i].setEditable(false);
        }
    }

    private void verificarResultados() {
        boolean todoCorrecto = true;
        StringBuilder errores = new StringBuilder("❌ Algunas respuestas son incorrectas:\n\n");

        for (int i = 0; i < campoA.length; i++) {
            try {
                int a = Integer.parseInt(campoA[i].getText());
                int b = Integer.parseInt(campoB[i].getText());
                char op = operadores[i].getText().charAt(0);
                double esperado = Modelo_OperacionesUnidad2.calcularResultado(a, b, op);
                int esperadoEntero = (int) Math.round(esperado);

                // Solo resultados entre 0-10
                if (esperadoEntero < 0) esperadoEntero = 0;
                if (esperadoEntero > 10) esperadoEntero = 10;

                String correctoKichwa = mapaKichwa.getOrDefault(esperadoEntero, String.valueOf(esperadoEntero));
                String respuesta = resultados[i].getText().trim().toLowerCase();

                if (respuesta.equals(correctoKichwa)) {
                    resultados[i].setBackground(Color.GREEN);
                } else {
                    resultados[i].setBackground(Color.RED);
                    resultados[i].setToolTipText("⚠️ Correcto: " + correctoKichwa);
                    errores.append("🔻 Operación ").append(i + 1).append(": ")
                          .append(a).append(" ").append(op).append(" ").append(b)
                          .append(" = ").append(correctoKichwa).append("\n");
                    todoCorrecto = false;
                }

            } catch (Exception ex) {
                resultados[i].setBackground(Color.ORANGE);
                errores.append("⚠️ Error en operación ").append(i + 1).append(": Formato inválido\n");
                todoCorrecto = false;
            }
        }

        if (todoCorrecto) {
            Modelo_ProgresoUnidad2.guardarProgreso(dashboard.getCorreoUsuario(), 55);
            JOptionPane.showMessageDialog(vista, "✅ ¡Correcto! Operaciones completadas.\n🌟 Puedes continuar.");

            // Activamos el botón continuar
            vista.getjButton1CONTINUAR().setEnabled(true);

        } else {
            JOptionPane.showMessageDialog(vista, errores.toString(), "Respuestas Incorrectas", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void irAUnidad2() {
        Vista_Unidad2 panelUnidad2 = new Vista_Unidad2();
        ControladorUnidad2 controladorUnidad2 = new ControladorUnidad2(panelUnidad2, dashboard);
        dashboard.mostrarVista(panelUnidad2);
    }
}