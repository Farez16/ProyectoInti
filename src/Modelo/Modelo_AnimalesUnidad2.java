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
    public int id;
    public String nombreEsp;
    public String nombreKichwa;
    public String rutaImagen;
    public String tipo;

    // Constructor con parámetros
    public Animal(int id, String nombreEsp, String nombreKichwa, String rutaImagen, String tipo) {
        this.id = id;
        this.nombreEsp = nombreEsp;
        this.nombreKichwa = nombreKichwa;
        this.rutaImagen = rutaImagen;
        this.tipo = tipo;
    }

    // Constructor vacío
    public Animal() {
    }
}

public static List<Animal> obtenerTodosAnimalesPorTipo(String tipo) {
    List<Animal> lista = new ArrayList<>();
    try (Connection conn = Conexion.conectar();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM animales_unidad2 WHERE tipo = ?")) {
        stmt.setString(1, tipo);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Animal a = new Animal();
            a.id = rs.getInt("id");
            a.nombreEsp = rs.getString("nombre_espanol");
            a.nombreKichwa = rs.getString("nombre_kichwa");
            a.rutaImagen = rs.getString("ruta_imagen");
            a.tipo = rs.getString("tipo");
            lista.add(a);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}
}

