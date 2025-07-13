package Controlador;

import Modelo.EmailSender;
import Modelo.OTPService;
import Modelo.Usuario;
import Vista.ContrasenaNueva;
import Vista.Dashboard;
import Vista.DashboardAdmin;
import Vista.Login;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class ControladorContrasenaNueva {

    private final ContrasenaNueva vista;
    private final JDialog dialog;
    private final Login login;

    public ControladorContrasenaNueva(ContrasenaNueva vista, String correoUsuario, JDialog dialog, Login login) {
        this.vista = vista;
        this.dialog = dialog;
        this.login = login;

        bloquearCamposRegistro();

        // BOTÓN PARA ENVIAR CÓDIGO
        this.vista.getBtnEnviarCodigo().addActionListener(e -> enviarCodigo());

        // BOTÓN PARA VERIFICAR CÓDIGO
        this.vista.getBtnVerificarCodigo().addActionListener(e -> verificarCodigo());

        // BOTÓN PARA GUARDAR CONTRASEÑA
        this.vista.getBtnGuardarContraseña().addActionListener(e -> guardarContrasena());
    }

    private void bloquearCamposRegistro() {
        vista.getTextFieldIngresarNuevaContraseña().setEnabled(false);
        vista.getTextFieldConfirmarContraseña().setEnabled(false);
        vista.getTxtNombre().setEnabled(false);
    }

    private void desbloquearCamposRegistro() {
        vista.getTextFieldIngresarNuevaContraseña().setEnabled(true);
        vista.getTextFieldConfirmarContraseña().setEnabled(true);
        vista.getTxtNombre().setEnabled(true);
    }

    // ENVÍA EL CÓDIGO AL CORREO
    private void enviarCodigo() {
    String correo = vista.getTextFieldIngresarCorreo().getText().trim().toLowerCase();
    if (correo.isEmpty() || correo.equals("correo")) {
        JOptionPane.showMessageDialog(vista, "Ingrese un correo válido para registrar.");
        return;
    }
    if (OTPService.codigoEnviado(correo)) {
        JOptionPane.showMessageDialog(vista, "Ya se ha enviado un código a este correo. Por favor, revise su correo o espere para solicitar otro.");
        return;
    }
    String codigo = OTPService.generarYEnviarCodigo(correo);
    EmailSender.enviarCodigo(correo, codigo);
    JOptionPane.showMessageDialog(vista, "Se ha enviado el código al correo.");
}



    // VERIFICA EL CÓDIGO
   private void verificarCodigo() {
    String correo = vista.getTextFieldIngresarCorreo().getText().trim().toLowerCase();
    String codigo = vista.getTextFieldIngresarCodigoRecibido().getText().trim().replaceAll("[^0-9]", "");

    if (correo.isEmpty() || correo.equalsIgnoreCase("correo")) {
        JOptionPane.showMessageDialog(vista, "Ingrese el correo.");
        return;
    }
    if (codigo.isEmpty() || codigo.equalsIgnoreCase("codigo recibido")) {
        JOptionPane.showMessageDialog(vista, "Ingrese el código recibido.");
        return;
    }

    System.out.println("Verificando código. Correo: '" + correo + "', Código: '" + codigo + "'");

    if (!OTPService.validarCodigo(correo, codigo)) {
        JOptionPane.showMessageDialog(vista, "El código ingresado no es correcto.", "Código incorrecto", JOptionPane.ERROR_MESSAGE);
        return;
    }
    JOptionPane.showMessageDialog(vista, "Código correcto. Ahora ingrese su nombre y contraseña.");
    desbloquearCamposRegistro();
}



    // GUARDA LA CONTRASEÑA Y ENVÍA EL CORREO DE BIENVENIDA
    private void guardarContrasena() {
        try {
            String correoUsuario = vista.getTextFieldIngresarCorreo().getText().trim();
            String nuevaContrasena = vista.getTextFieldIngresarNuevaContraseña().getText().trim();
            String confirmarContrasena = vista.getTextFieldConfirmarContraseña().getText().trim();
            String nombre = vista.getTxtNombre().getText().trim();

            if (nuevaContrasena.isEmpty() || confirmarContrasena.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!nuevaContrasena.equals(confirmarContrasena)) {
                JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean actualizada = Usuario.actualizarContrasena(correoUsuario, nuevaContrasena, nombre);
            if (actualizada) {
                OTPService.borrarCodigo(correoUsuario);

                String asunto = "¡Gracias por registrarte en nuestra app!";
                String mensaje = "Bienvenido/a " + nombre + ",\n\n"
                    + "Tu registro fue exitoso. Puedes iniciar sesión con:\n"
                    + "Usuario: " + nombre + "\n"
                    + "Correo: " + correoUsuario + "\n"
                    + "Contraseña: " + nuevaContrasena + "\n\n"
                    + "¡Gracias por registrarte en nuestra app!";
                EmailSender.enviarCorreo(correoUsuario, asunto, mensaje);

                JOptionPane.showMessageDialog(vista, "Registro exitoso. Revisa tu correo y disfruta nuestra app.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();

                int rol = Usuario.obtenerRolUsuario(correoUsuario);
                if (rol == 2) {
                    DashboardAdmin dashAdmin = new DashboardAdmin(correoUsuario);
                    ControladorDashboardAdmin controlador = new ControladorDashboardAdmin(dashAdmin, login);
                    login.mostrarPanelEnPanel1(dashAdmin);
                } else {
                    Dashboard dashPanel = new Dashboard(correoUsuario);
                    ControladorDashboard controladorDashboard = new ControladorDashboard(dashPanel, login);
                    login.mostrarPanelEnPanel1(dashPanel);
                }
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar la contraseña.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error en el cambio");
        }
    }
}
