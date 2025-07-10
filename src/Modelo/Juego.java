package Modelo;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Juego {

    private String palabra;
    private String rutaImagen;
    
     private Connection conn;

    public Juego(Connection conn) {
        this.conn = conn;
    }

    public Juego() {
    }

    public Juego(String palabra, String rutaImagen) {
        this.palabra = palabra;
        this.rutaImagen = rutaImagen;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    // 👉 Método para traer datos aleatorios desde la base de datos
    public List<Juego> obtenerTodoAleatorio(int cantidad) {
        List<Juego> lista = new ArrayList<>();
        try {
            String sql = "SELECT ruta_imagen, palabra_kichwa FROM palabras_imagenes ORDER BY RAND() LIMIT ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, cantidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Juego(rs.getString("palabra_kichwa"), rs.getString("ruta_imagen")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}

