package Controlador;

import Modelo.EfectosVisuales;
import Modelo.Saludo;
import javax.swing.*;
import Vista.Estudiante.Dashboard;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorSaludo {

    private JLabel lblSaludo;  // Label donde se muestra el mensaje
    private JLabel lblNombre;  // Label con el nombre del usuario
    private Saludo modelo;
    private boolean mostrandoBienvenido = true;
    private Timer timer;

    public ControladorSaludo(JLabel lblSaludo, JLabel lblNombre, Saludo modelo) {
        this.lblSaludo = lblSaludo;
        this.lblNombre = lblNombre;
        this.modelo = modelo;
        this.modelo.setMensaje("Bienvenido"); // Mensaje inicial
    }

    private void iniciarTimerSaludo() {
        timer = new Timer(10000, e -> {
            // Alternar entre mensajes
            String mensaje = mostrandoBienvenido ? "Alli Shamushka" : "Bienvenido";
            modelo.setMensaje(mensaje);
            mostrandoBienvenido = !mostrandoBienvenido;

            // Construir mensaje completo
            String mensajeCompleto = modelo.getMensaje() + " " + lblNombre.getText();
            
            // Actualizar label con efecto
            lblSaludo.setText(mensajeCompleto);
            EfectosVisuales.aplicarFadeLabel(lblSaludo, mensajeCompleto, 1000);
        });
        timer.start();
    }

    public void inicializarVista() {
        // Mostrar primer mensaje
        String mensajeInicial = modelo.getMensaje() + " " + lblNombre.getText();
        lblSaludo.setText(mensajeInicial);
        iniciarTimerSaludo();
    }
    
    public void detener() {
        if (timer != null) {
            timer.stop();
        }
    }
}