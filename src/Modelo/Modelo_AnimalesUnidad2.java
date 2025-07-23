/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Conexion.Conexion;
import java.sql.*;
import java.util.*;

public class Modelo_AnimalesUnidad2 {

    public static class Animal {
        public String nombreEsp;
        public String nombreKichwa;
        public String rutaImagen;
        public String tipo;

        public Animal(String nombreEsp, String nombreKichwa, String rutaImagen, String tipo) {
            this.nombreEsp = nombreEsp;
            this.nombreKichwa = nombreKichwa;
            this.rutaImagen = rutaImagen;
            this.tipo = tipo;
        }
    }

    public static List<Animal> obtenerAnimalesAleatorios(String tipo, int cantidad) {
        List<Animal> lista = new ArrayList<>();
        try (Connection con = Conexion.conectar()) {
            PreparedStatement pst = con.prepareStatement(
                "SELECT nombre_espanol, nombre_kichwa, ruta_imagen, tipo FROM animales_unidad2 WHERE tipo = ? ORDER BY RAND() LIMIT ?"
            );
            pst.setString(1, tipo);
            pst.setInt(2, cantidad);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(new Animal(
                    rs.getString("nombre_espanol"),
                    rs.getString("nombre_kichwa"),
                    rs.getString("ruta_imagen"),
                    rs.getString("tipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
