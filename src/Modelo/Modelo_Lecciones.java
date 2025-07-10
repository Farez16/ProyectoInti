/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.sql.Connection;
import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Jhony Espinoza
 */
public class Modelo_Lecciones {
    private int idLeccion;
    private int idUnidad;
    private String titulo;
    private String contenido;
    private String tipoContenido; // 'texto', 'imagen', 'audio'
    private String urlMultimedia;

    public Modelo_Lecciones() {
    }

    public Modelo_Lecciones(int idLeccion, int idUnidad, String titulo, String contenido, String tipoContenido, String urlMultimedia) {
        this.idLeccion = idLeccion;
        this.idUnidad = idUnidad;
        this.titulo = titulo;
        this.contenido = contenido;
        this.tipoContenido = tipoContenido;
        this.urlMultimedia = urlMultimedia;
    }
    
     public static List<Modelo_Lecciones> obtenerLeccionesPorUnidadYTipo(Connection conn, int idUnidad, String tipoContenido) {
        List<Modelo_Lecciones> lista = new ArrayList<>();
        String sql = "SELECT * FROM lecciones WHERE id_unidad = ? AND tipo_contenido = ? ORDER BY id_leccion";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUnidad);
            ps.setString(2, tipoContenido);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Modelo_Lecciones leccion = new Modelo_Lecciones();
                leccion.setIdLeccion(rs.getInt("id_leccion"));
                leccion.setIdUnidad(rs.getInt("id_unidad"));
                leccion.setTitulo(rs.getString("titulo"));
                leccion.setContenido(rs.getString("contenido"));
                leccion.setTipoContenido(rs.getString("tipo_contenido"));
                leccion.setUrlMultimedia(rs.getString("url_multimedia"));
                
                lista.add(leccion);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }

    public int getIdLeccion() {
        return idLeccion;
    }

    public void setIdLeccion(int idLeccion) {
        this.idLeccion = idLeccion;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(String tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getUrlMultimedia() {
        return urlMultimedia;
    }

    public void setUrlMultimedia(String urlMultimedia) {
        this.urlMultimedia = urlMultimedia;
    }
}
