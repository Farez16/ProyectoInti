package Controlador;

import Modelo.EmailSender;
import Modelo.OTPService;
import Modelo.Usuario;
import Modelo.UsuarioGoogleD;
import Vista.ContrasenaNueva;
import Vista.Dashboard;
import Vista.DashboardAdmin;
import Vista.Login;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStreamReader;
import java.util.Collections;
import javax.swing.JDialog;

import javax.swing.JOptionPane;

public class ControladorLogin implements ActionListener {

    private Login vista;

    public ControladorLogin(Login vista) {
        this.vista = vista;

        // Asignar eventos
        this.vista.btnInicar.addActionListener(this);
        this.vista.btnCodigo.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnInicar) {
            iniciarSesion();
        } else if (e.getSource() == vista.btnCodigo) {
            enviarCodigo();
        }
    }

    private void iniciarSesion() {
        String correo = vista.TxtUsuario.getText().trim();
        String contrasenaOCodigo = vista.TxtContraseña.getText().trim();

        if (correo.isEmpty() || contrasenaOCodigo.isEmpty() || correo.contentEquals("Usuario") || contrasenaOCodigo.contentEquals("Contraseña")) {
            JOptionPane.showMessageDialog(vista, "Por favor, complete todos los campos");
            return;
        }

        if (Usuario.estaRegistrado(correo)) {
            // Inicio de sesión normal
            if (Usuario.verificarContrasena(correo, contrasenaOCodigo)) {
                JOptionPane.showMessageDialog(vista, "Inicio de sesión exitoso");
                EmailSender.enviarConfirmacion(correo);
                abrirDashboard(correo);
            } else {
                JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrecta");
            }
        } else {
            // Flujo de verificación por código
            if (OTPService.validarCodigo(correo, contrasenaOCodigo)) {
                // No borrar código aquí
                JOptionPane.showMessageDialog(vista, "Código correcto. Cambia tu contraseña.");
                abrirCambioContrasena(correo);
            } else {
                JOptionPane.showMessageDialog(vista, "Código incorrecto o expirado");
            }
        }
    }

    private void iniciarConGoogle() {
        try {
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                    JacksonFactory.getDefaultInstance(),
                    new InputStreamReader(getClass().getResourceAsStream("/client_secret.json"))
            );

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                    new NetHttpTransport(), JacksonFactory.getDefaultInstance(),
                    clientSecrets, Collections.singletonList("email profile"))
                    .setAccessType("offline").build();

            String authUrl = flow.newAuthorizationUrl()
                    .setRedirectUri("urn:ietf:wg:oauth:2.0:oob").build();

            String code = JOptionPane.showInputDialog("Abre esta URL en el navegador:\n" + authUrl + "\n\nPega aquí el código:");

            if (code != null && !code.trim().isEmpty()) {
                GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(
                        new NetHttpTransport(),
                        JacksonFactory.getDefaultInstance(),
                        "https://oauth2.googleapis.com/token",
                        clientSecrets.getDetails().getClientId(),
                        clientSecrets.getDetails().getClientSecret(),
                        code.trim(),
                        "urn:ietf:wg:oauth:2.0:oob"
                ).execute();

                String idToken = response.getIdToken();
                GoogleIdToken.Payload payload = GoogleIdToken.parse(JacksonFactory.getDefaultInstance(), idToken).getPayload();

                String jsonIdentity = String.format("{\"email\":\"%s\", \"name\":\"%s\", \"id\":\"%s\"}",
                        payload.getEmail(), payload.get("name"), payload.getSubject());

                UsuarioGoogleD.registrarOIniciarSesion(jsonIdentity);
                abrirDashboard(payload.getEmail());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error en autenticación con Google");
        }
    }

    private void enviarCodigo() {
        String correo = vista.TxtUsuario.getText().trim();

        if (correo.isBlank() || correo.contentEquals("Usuario")) {
            JOptionPane.showMessageDialog(vista, "Ingrese su correo");
            return;
        }

        if (Usuario.estaRegistrado(correo)) {
            JOptionPane.showMessageDialog(vista, "Usuario ya registrado. Inicie sesión con su correo y contraseña.");
            vista.TxtContraseña.setEnabled(true);
            vista.TxtContraseña.setText("");
            vista.TxtContraseña.setForeground(new Color(51, 51, 51));
            vista.TxtContraseña.requestFocus();
            return;
        } else {
            String codigo = OTPService.generarYEnviarCodigo(correo);
            EmailSender.enviarCodigo(correo, codigo);
            JOptionPane.showMessageDialog(vista, "Se ha enviado el código al correo");

            vista.TxtContraseña.setEnabled(true);
            vista.TxtContraseña.setText("");
            vista.TxtContraseña.setForeground(Color.BLACK);
            vista.TxtContraseña.requestFocus();
        }

    }

    private void verificarCodigo() {
        String correo = vista.TxtUsuario.getText().trim();
        String codigo = vista.TxtContraseña.getText().trim();

        if (OTPService.validarCodigo(correo, codigo)) {
            OTPService.borrarCodigo(correo);
            JOptionPane.showMessageDialog(vista, "Código correcto. Cambia tu contraseña.");
            abrirCambioContrasena(correo);
        } else {
            JOptionPane.showMessageDialog(vista, "Código incorrecto o expirado");
        }
    }

    private void abrirDashboard(String correo) {
        int rol = Usuario.obtenerRolUsuario(correo);

        if (rol == 2) { // Admin
            DashboardAdmin dashAdmin = new DashboardAdmin(correo);
            ControladorDashboardAdmin controlador = new ControladorDashboardAdmin(dashAdmin, vista);
            vista.mostrarPanelEnPanel1(dashAdmin);
        } else { // Estudiante
            Dashboard dash = new Dashboard(correo);
            ControladorDashboard controlador = new ControladorDashboard(dash, vista); // Pasa la vista Login
            vista.mostrarPanelEnPanel1(dash);
        }

    }

    public void limpiarCampos() {
        vista.TxtUsuario.setText("");
        vista.TxtContraseña.setText("");
    }

    private void abrirCambioContrasena(String correo) {
        ContrasenaNueva panelCambio = new ContrasenaNueva(correo); // Este es el panel que necesitas pasar al controlador
        JDialog dialog = new JDialog(vista, "Cambiar contraseña", true); // 'vista' aquí es tu JFrame principal
        dialog.setContentPane(panelCambio);
        dialog.pack();
        dialog.setLocationRelativeTo(vista);

        new ControladorContrasenaNueva(panelCambio, correo, dialog, vista);

        dialog.setVisible(true);
    }

}
