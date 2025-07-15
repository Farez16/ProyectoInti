package Controlador;

import Conexion.Conexion;
import Modelo.EmailSender;
import Modelo.OTPService;
import Modelo.Usuario;
import Vista.RegistrarAdmin;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ControladorRegistrarAdmin {

    private RegistrarAdmin vista;
    private Usuario modelo;
    private String correoActual;
    private boolean codigoVerificado = false;

    public ControladorRegistrarAdmin(RegistrarAdmin vista, Usuario modelo) {
        this.vista = vista;
        this.modelo = modelo;

        // Configurar acción del botón registrar
        this.vista.getBtnRegistrarAd().addActionListener(e -> registrarAdministrador());
        // Configurar acción del botón Enviar Codigo OTP
        this.vista.getBtnEnviarCodigo().addActionListener(e -> enviarCodigoOTP());
        // Configurar acción del botón Verificar Codigo OTP
        this.vista.getBtnVerificarCodigo().addActionListener(e -> verificarCodigoOTP());
    }
private void enviarCodigoOTP() {
        correoActual = vista.getTxtCorreo().getText().trim();
        
        if (correoActual.isEmpty() || !correoActual.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(vista, "Ingrese un correo electrónico válido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar si el correo ya está registrado
        if (Usuario.existeUsuario(correoActual)) {
            JOptionPane.showMessageDialog(vista, "Este correo ya está registrado", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Enviar código OTP
        String codigo = OTPService.generarYEnviarCodigo(correoActual);
        EmailSender.enviarCodigo(correoActual, codigo);
        
        JOptionPane.showMessageDialog(vista, "Código OTP enviado al correo electrónico", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void verificarCodigoOTP() {
        if (correoActual == null || correoActual.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Primero envíe un código OTP", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String codigoIngresado = vista.getTxtCodigoOTP().getText().trim();
        
        if (codigoIngresado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el código OTP recibido", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (OTPService.validarCodigo(correoActual, codigoIngresado)) {
            codigoVerificado = true;
            vista.getTxtNombre().setEnabled(true);
            vista.getjPassword().setEnabled(true);
            vista.getBtnRegistrarAd().setEnabled(true);
            JOptionPane.showMessageDialog(vista, "Código verificado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(vista, "Código incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void registrarAdministrador() {
        if (!codigoVerificado) {
            JOptionPane.showMessageDialog(vista, "Debe verificar el código OTP primero", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String nombre = vista.getTxtNombre().getText().trim();
        String contrasena = new String(vista.getjPassword().getPassword()).trim();
        
        // Validaciones (igual que antes)
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (contrasena.length() < 6) {
            JOptionPane.showMessageDialog(vista, "La contraseña debe tener al menos 6 caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear el administrador
        if (crearAdministrador(nombre, correoActual, contrasena)) {
            JOptionPane.showMessageDialog(vista, "Administrador registrado con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            OTPService.borrarCodigo(correoActual);
            
            // Enviar correo de confirmación
            String asunto = "¡Bienvenido como Administrador!";
            String mensaje = "Estimado " + nombre + ",\n\n"
                + "Has sido registrado como administrador en nuestro sistema.\n"
                + "Puedes iniciar sesión con:\n"
                + "Correo: " + correoActual + "\n"
                + "Contraseña: " + contrasena + "\n\n"
                + "Por seguridad, cambia tu contraseña después del primer inicio de sesión.";
            
            EmailSender.enviarCorreo(correoActual, asunto, mensaje);
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar administrador", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // En ControladorRegistrarAdmin
private boolean crearAdministrador(String nombre, String correo, String contrasena) {
    String sql = "INSERT INTO usuarios (nombre, correo, contrasena, id_rol) VALUES (?, ?, ?, 2)";
    
    try (Connection conn = Conexion.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, nombre);
        ps.setString(2, correo);
        ps.setString(3, contrasena); // Deberías hashear la contraseña aquí
        
        return ps.executeUpdate() > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

private void limpiarCampos() {
    vista.getTxtCorreo().setText("");
    vista.getTxtCodigoOTP().setText("");
    vista.getTxtNombre().setText("");
    vista.getjPassword().setText("");
    // Restablecer estados
    vista.getTxtNombre().setEnabled(false);
    vista.getjPassword().setEnabled(false);
    vista.getBtnRegistrarAd().setEnabled(false);
    codigoVerificado = false;
}
}