/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Memorama.java (nuevo modelo)
package Modelo;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Memorama {

    public List<String> obtenerImagenesAnimales() {
        List<String> imagenes = new ArrayList<>();
        String sql = "SELECT ruta_imagen FROM animales_unidad2 LIMIT 6";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String ruta = rs.getString("ruta_imagen");
                imagenes.add(ruta.substring(ruta.lastIndexOf("/") + 1));
            }
        } catch (Exception e) {
            System.err.println("Error en Memorama: " + e.getMessage());
        }
        return imagenes;
    }
}