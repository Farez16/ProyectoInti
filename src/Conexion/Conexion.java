package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private final String url = "jdbc:mysql://127.0.0.1:3306/proyecto_kiwchaa";
    private final String usuario = "root";
    private String contraseña = "1234";

    public Connection getConexion() {
        Connection conexion = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return conexion;
    }

    // MÉTODO ESTÁTICO para obtener conexión directamente
    public static Connection conectar() {
        return new Conexion().getConexion();
    }
}

