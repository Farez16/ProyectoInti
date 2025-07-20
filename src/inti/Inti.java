package inti;

import Controlador.ControladorLogin;
import Vista.Login;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public class Inti {

    public static void main(String[] args) {
        // Aplicar FlatLaf antes de mostrar la interfaz
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
        } catch (Exception ex) {
            System.err.println("No se pudo aplicar FlatLaf: " + ex);
        }

        // Ejecutar en el Event Dispatch Thread
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crear la vista
                Login vistaLogin = new Login();
                // Crear el controlador
                ControladorLogin controladorLogin = new ControladorLogin(vistaLogin);
                // Mostrar la vista
                vistaLogin.setVisible(true);
            }
        });
    }
}