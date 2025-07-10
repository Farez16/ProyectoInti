package Modelo;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import org.json.JSONObject;

public class UsuarioGoogleD {

    public static void registrarOIniciarSesion(String jsonIdentity) {
        JSONObject obj = new JSONObject(jsonIdentity);
        String email = obj.getString("email");
        String nombre = obj.getString("name");
        String googleId = obj.getString("id");

        String asunto;
        String cuerpo;

        try (Connection conn = Conexion.conectar()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM usuarios_google WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {

                System.out.println("Usuario ya existe, iniciar sesión");
                asunto = "Inicio de sesión exitoso";
                cuerpo = "Hola " + nombre + ",\n\nHas iniciado sesión correctamente en la aplicación.";
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso");
            } else {
                // Registrar nuevo usuario
                ps = conn.prepareStatement("INSERT INTO usuarios_google (email, nombre, google_id) VALUES (?, ?, ?)");
                ps.setString(1, email);
                ps.setString(2, nombre);
                ps.setString(3, googleId);
                ps.executeUpdate();
                System.out.println("Usuario registrado correctamente");
                asunto = "Registro exitoso";
                cuerpo = "Hola " + nombre + ",\n\nTe has registrado correctamente en la aplicación.";
                JOptionPane.showMessageDialog(null, "Registrado correctamente");
            }

            // Enviar correo en ambos casos
            EmailSender.enviarCorreo(email, asunto, cuerpo);
            if (!Usuario.estaRegistrado(email)) {
                Usuario.actualizarContrasena(email, "temp_password", nombre); // Esto debe usar rol 1 por defecto
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
