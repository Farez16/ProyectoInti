/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Modelo_ProgresoUnidad2.java
package Modelo;

import Conexion.Conexion;
import java.sql.*;

public class Modelo_ProgresoUnidad2 {

   public static void guardarProgreso(String correoUsuario, int porcentaje) {
    try (Connection con = Conexion.conectar()) {
        // Verificar si ya existe registro
        PreparedStatement check = con.prepareStatement(
            "SELECT COUNT(*) FROM progreso_usuario WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) AND id_unidad = 2"
        );
        check.setString(1, correoUsuario);
        ResultSet rs = check.executeQuery();
        rs.next();
        boolean existe = rs.getInt(1) > 0;

        if (existe) {
            PreparedStatement pst = con.prepareStatement(
                "UPDATE progreso_usuario SET actividades_completadas = ?, fecha_actualizacion = NOW() WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) AND id_unidad = 2"
            );
            pst.setInt(1, porcentaje);
            pst.setString(2, correoUsuario);
            pst.executeUpdate();
        } else {
            PreparedStatement insert = con.prepareStatement(
                "INSERT INTO progreso_usuario (id_usuario, id_unidad, actividades_completadas) VALUES ((SELECT id_usuario FROM usuarios WHERE correo = ?), 2, ?)"
            );
            insert.setString(1, correoUsuario);
            insert.setInt(2, porcentaje);
            insert.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
