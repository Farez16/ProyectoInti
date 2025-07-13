package Controlador;

import Modelo.EmailSender;
import Modelo.Usuario;
import Vista.ContrasenaNueva;
import Vista.Dashboard;
import Vista.DashboardAdmin;
import Vista.Login;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class ControladorLogin implements ActionListener {

    private Login vista;

    public ControladorLogin(Login vista) {
        this.vista = vista;
        this.vista.btnInicar.addActionListener(this);
        this.vista.btnCodigo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnInicar) {
            iniciarSesion();
        } else if (e.getSource() == vista.btnCodigo) {
            abrirPanelRegistro();
        }
    }

    private void iniciarSesion() {
        String correo = vista.TxtUsuario.getText().trim();
        String contrasena = vista.contrasenaReal.trim();

        if (correo.isEmpty() || contrasena.isEmpty() || correo.equals("Usuario") || contrasena.equals("Contraseña")) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos");
            return;
        }

        if (Usuario.estaRegistrado(correo)) {
            if (Usuario.verificarContrasena(correo, contrasena)) {
                JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso");
                EmailSender.enviarConfirmacion(correo);
                abrirDashboard(correo);
            } else {
                JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrecta");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "No existe una cuenta con ese correo. Por favor, crea una cuenta.");
            resaltarBotonCrearCuenta();
        }
    }

    private void resaltarBotonCrearCuenta() {
        JButton btnCrearCuenta = vista.getBtnCodigo();
        Color original = btnCrearCuenta.getBackground();
        Timer timer = new Timer(200, null);
        timer.addActionListener(new ActionListener() {
            int count = 0;
            boolean highlight = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count >= 6) {
                    btnCrearCuenta.setBackground(original);
                    timer.stop();
                    return;
                }
                btnCrearCuenta.setBackground(highlight ? Color.WHITE : original);
                highlight = !highlight;
                count++;
            }
        });
        timer.start();
        btnCrearCuenta.requestFocus();
    }

    private void abrirPanelRegistro() {
        String correo = vista.TxtUsuario.getText().trim();
        ContrasenaNueva panelCambio = new ContrasenaNueva(correo);
        JDialog dialog = new JDialog(vista, "Registrar nueva cuenta", true);
        dialog.setContentPane(panelCambio);
        dialog.pack();
        dialog.setLocationRelativeTo(vista);

        new ControladorContrasenaNueva(panelCambio, correo, dialog, vista);

        dialog.setVisible(true);
    }

    private void abrirDashboard(String correo) {
        int rol = Usuario.obtenerRolUsuario(correo);
        if (rol == 2) {
            DashboardAdmin dashAdmin = new DashboardAdmin(correo);
            ControladorDashboardAdmin controlador = new ControladorDashboardAdmin(dashAdmin, vista);
            vista.mostrarPanelEnPanel1(dashAdmin);
        } else {
            Dashboard dash = new Dashboard(correo);
            ControladorDashboard controlador = new ControladorDashboard(dash, vista);
            vista.mostrarPanelEnPanel1(dash);
        }
    }
}
