package Modelo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class MostrarDatosAdmin {
    
    private Connection conn;

    public MostrarDatosAdmin(Connection conn) {
        this.conn = conn;
    }

// Método para obtener todos los usuarios con rol 2
public ResultSet obtenerUsuariosRol1() {
    String sql = "SELECT id_usuario, nombre, correo, fecha_registro FROM usuarios WHERE id_rol = 1";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        return ps.executeQuery();
    } catch (SQLException e) {
        System.out.println("Error al obtener usuarios con rol 1: " + e.getMessage());
        return null;
    }
}

// Método para buscar usuarios por correo con rol 2
public ResultSet buscarUsuariosRol1PorCorreo(String correo) {
    String sql = "SELECT id_usuario, nombre, correo, fecha_registro FROM usuarios WHERE correo = ? AND id_rol = 1";
    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, correo);
        return ps.executeQuery();
    } catch (SQLException e) {
        System.out.println("Error al buscar usuarios con rol 1: " + e.getMessage());
        return null;
    }
}
}
