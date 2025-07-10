package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import Conexion.Conexion;

public class OTPService {

    // Genera el código y lo guarda en la base de datos
    public static String generarYEnviarCodigo(String correo) {
        correo = correo.trim().toLowerCase();  // Unificar formato
        String codigo = String.format("%06d", new Random().nextInt(1000000));

        guardarCodigoEnBaseDeDatos(correo, codigo);
        System.out.println("DEBUG: Código OTP para " + correo + ": " + codigo);
        return codigo;
    }

    // Guarda o actualiza el código OTP en la tabla `otp`
    private static void guardarCodigoEnBaseDeDatos(String correo, String codigo) {
        correo = correo.trim().toLowerCase();
        try (Connection conn = Conexion.conectar()) {
            String sql = "REPLACE INTO otp (correo, codigo) VALUES (?, ?)"; // REPLACE: inserta o actualiza
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, codigo);
            ps.executeUpdate();
            System.out.println("DEBUG: Código guardado en base de datos para " + correo + ": " + codigo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verifica si el código ingresado es correcto
    public static boolean validarCodigo(String correo, String codigoIngresado) {
        correo = correo.trim().toLowerCase();
        String codigoGuardado = getCodigo(correo);
        return codigoGuardado != null && codigoGuardado.equals(codigoIngresado);
    }

    // Obtiene el código desde la base de datos
    public static String getCodigo(String correo) {
        correo = correo.trim().toLowerCase();
        try (Connection conn = Conexion.conectar()) {
            String sql = "SELECT codigo FROM otp WHERE correo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String codigo = rs.getString("codigo");
                System.out.println("DEBUG: Código obtenido desde BD para " + correo + ": " + codigo);
                return codigo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Elimina el código una vez usado
    public static void borrarCodigo(String correo) {
        correo = correo.trim().toLowerCase();
        try (Connection conn = Conexion.conectar()) {
            String sql = "DELETE FROM otp WHERE correo = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, correo);
            ps.executeUpdate();
            System.out.println("DEBUG: Código eliminado para " + correo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Verifica si ya se envió un código antes
    public static boolean codigoEnviado(String correo) {
        return getCodigo(correo) != null;
    }
}
