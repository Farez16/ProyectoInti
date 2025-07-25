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
        PreparedStatement check = con.prepareStatement(
            "SELECT COUNT(*) FROM progreso_usuario WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) AND id_unidad = 2");
        check.setString(1, correoUsuario);
        ResultSet rs = check.executeQuery();
        rs.next();
        boolean existe = rs.getInt(1) > 0;

        if (existe) {
            PreparedStatement pst = con.prepareStatement(
                "UPDATE progreso_usuario SET actividades_completadas = ?, fecha_actualizacion = NOW() WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) AND id_unidad = 2");
            pst.setInt(1, porcentaje);
            pst.setString(2, correoUsuario);
            pst.executeUpdate();
        } else {
            PreparedStatement insert = con.prepareStatement(
                "INSERT INTO progreso_usuario (id_usuario, id_unidad, actividades_completadas) VALUES ((SELECT id_usuario FROM usuarios WHERE correo = ?), 2, ?)");
            insert.setString(1, correoUsuario);
            insert.setInt(2, porcentaje);
            insert.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public static void completarUnidad(String correoUsuario, int calificacion) {
        String sql = "UPDATE progreso_usuario SET evaluacion_aprobada = 1, calificacion = ?, actividades_completadas = 100 "
                   + "WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) AND id_unidad = 2";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, calificacion);
            pst.setString(2, correoUsuario);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


        public static int obtenerProgreso(String correoUsuario) {
        String sql = "SELECT actividades_completadas FROM progreso_usuario "
                   + "WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) "
                   + "AND id_unidad = 2";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, correoUsuario);
            ResultSet rs = pst.executeQuery();
            return rs.next() ? rs.getInt("actividades_completadas") : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static void actualizarProgreso(String correoUsuario, int progreso) {
        String sql = "UPDATE progreso_usuario SET actividades_completadas = ? "
                   + "WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) "
                   + "AND id_unidad = 2";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setInt(1, progreso);
            pst.setString(2, correoUsuario);
            pst.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void incrementarProgreso(String correoUsuario, int porcentajeAdicional) {
        try (Connection con = Conexion.conectar()) {
            // Obtener progreso actual
            int progresoActual = obtenerProgresoActual(con, correoUsuario);
            
            // Calcular nuevo progreso (no exceder 100%)
            int nuevoProgreso = Math.min(100, progresoActual + porcentajeAdicional);
            
            // Actualizar o insertar
            PreparedStatement pst;
            if (progresoActual == -1) {
                pst = con.prepareStatement(
                    "INSERT INTO progreso_usuario (id_usuario, id_unidad, actividades_completadas) "
                    + "VALUES ((SELECT id_usuario FROM usuarios WHERE correo = ?), 2, ?)");
                pst.setString(1, correoUsuario);
                pst.setInt(2, nuevoProgreso);
            } else {
                pst = con.prepareStatement(
                    "UPDATE progreso_usuario SET actividades_completadas = ? "
                    + "WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) "
                    + "AND id_unidad = 2");
                pst.setInt(1, nuevoProgreso);
                pst.setString(2, correoUsuario);
            }
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        private static int obtenerProgresoActual(Connection con, String correoUsuario) throws SQLException {
        PreparedStatement pst = con.prepareStatement(
            "SELECT actividades_completadas FROM progreso_usuario "
            + "WHERE id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?) "
            + "AND id_unidad = 2");
        pst.setString(1, correoUsuario);
        ResultSet rs = pst.executeQuery();
        return rs.next() ? rs.getInt("actividades_completadas") : -1;
    }


}
