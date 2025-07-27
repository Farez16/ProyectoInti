package inti;

import Controlador.ControladorLogin;
import Vista.Login.Login;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Inti {

    public static void main(String[] args) {
        // Configurar propiedades del sistema antes de iniciar la UI
        System.setProperty("flatlaf.uiScale", "1.0");
        System.setProperty("flatlaf.useWindowDecorations", "true");
        
        // Configurar el Look and Feel en el hilo de eventos
        java.awt.EventQueue.invokeLater(() -> {
            try {
                // Configurar FlatLaf
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
                
                // Configuración adicional para componentes específicos
                UIManager.put("ProgressBar.arc", 999);
                UIManager.put("Button.arc", 8);
                UIManager.put("TextComponent.arc", 5);
                
                // Forzar la actualización de la UI
                UIManager.getLookAndFeelDefaults().put("ClassLoader", Inti.class.getClassLoader());
                
                // Crear y mostrar la interfaz
                Login vistaLogin = new Login();
                new ControladorLogin(vistaLogin);
                vistaLogin.setVisible(true);
                
            } catch (UnsupportedLookAndFeelException ex) {
                System.err.println("Error: Look and Feel no soportado - " + ex.getMessage());
                // Fallback al Look and Feel por defecto
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    // Reintentar crear la interfaz
                    Login vistaLogin = new Login();
                    new ControladorLogin(vistaLogin);
                    vistaLogin.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error crítico al cargar el Look and Feel: " + e.getMessage());
                    e.printStackTrace();
                }
            } catch (Exception ex) {
                System.err.println("Error inesperado al inicializar la aplicación: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }
}