package modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Audios {
    private Connection conn;

    public Audios(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<HashMap<String, String>> obtenerRecursos() {
        ArrayList<HashMap<String, String>> lista = new ArrayList<>();
        String sql = "SELECT nombre, ruta_recurso FROM recursos";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HashMap<String, String> recurso = new HashMap<>();
                recurso.put("nombre", rs.getString("nombre"));
                recurso.put("ruta_recurso", rs.getString("ruta_recurso"));
                lista.add(recurso);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    public HashMap<String, String> obtenerRecursoPorLeccion(int idLeccion) {
    HashMap<String, String> recurso = null;
    try {
        String sql = "SELECT nombre, ruta_recurso FROM recursos WHERE id_leccion = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, idLeccion);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            recurso = new HashMap<>();
            recurso.put("nombre", rs.getString("nombre"));
            recurso.put("ruta_recurso", rs.getString("ruta_recurso"));
        }

        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return recurso;
}

}




