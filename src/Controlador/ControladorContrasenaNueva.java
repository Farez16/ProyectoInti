package Controlador;

import Modelo.OTPService;
import Modelo.Usuario;
import Vista.ContrasenaNueva;
import Vista.Dashboard;
import Vista.Login;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ControladorContrasenaNueva {

    private final ContrasenaNueva vista;
    private final String correoUsuario;
    private final JDialog dialog;
    private final Login login;

    public ControladorContrasenaNueva(ContrasenaNueva vista, String correoUsuario, JDialog dialog, Login login) {
        this.vista = vista;
        this.correoUsuario = correoUsuario.trim().toLowerCase();  // Aseguramos formato uniforme
        this.dialog = dialog;
        this.login = login;

        this.vista.getBtnGuardarContraseña().addActionListener(e -> guardarContrasena());
    }

    private void guardarContrasena() {
        try {
            String codigoIngresado = vista.getTextFieldIngresarCodigoRecibido().getText().trim();
            String nuevaContrasena = vista.getTextFieldIngresarNuevaContraseña().getText().trim();
            String confirmarContrasena = vista.getTextFieldConfirmarContraseña().getText().trim();
            String nombre = vista.getTxtNombre().getText().trim(); // Asegúrate de tenerlo también

            if (codigoIngresado.isEmpty() || nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!nuevaContrasena.equals(confirmarContrasena)) {
                JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            if (!OTPService.validarCodigo(correoUsuario, codigoIngresado)) {
                JOptionPane.showMessageDialog(vista, "El código ingresado no es correcto.", "Código incorrecto", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean actualizada = Usuario.actualizarContrasena(correoUsuario, nuevaContrasena, nombre);
            if (actualizada) {
                OTPService.borrarCodigo(correoUsuario);
                JOptionPane.showMessageDialog(vista, "Contraseña actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                Dashboard dashPanel = new Dashboard(correoUsuario);
                ControladorDashboard controladorDashboard = new ControladorDashboard(dashPanel, login);
                login.mostrarPanelEnPanel1(dashPanel);

            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error en el cambio");
        }
    }
}
