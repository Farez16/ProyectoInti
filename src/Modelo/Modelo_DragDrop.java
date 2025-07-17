package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Modelo_DragDrop {
    
    private final Connection conn;

    public Modelo_DragDrop(Connection conn) {
        this.conn = conn;
    }

    public List<DragDropItem> obtenerItemsPorActividad(int idActividad) {
        List<DragDropItem> items = new ArrayList<>();
        String sql = "SELECT * FROM drag_drop_items WHERE id_actividad = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idActividad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                DragDropItem item = new DragDropItem(
                    rs.getInt("id_drag_drop"),
                    rs.getInt("id_actividad"),
                    rs.getString("texto"),
                    rs.getString("recurso_url"),
                    rs.getString("posicion_destino")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    // Clase interna o pública para representar el item
    public static class DragDropItem {
        public int id;
        public int idActividad;
        public String texto;
        public String recursoUrl;
        public String posicionDestino;

        public DragDropItem(int id, int idActividad, String texto, String recursoUrl, String posicionDestino) {
            this.id = id;
            this.idActividad = idActividad;
            this.texto = texto;
            this.recursoUrl = recursoUrl;
            this.posicionDestino = posicionDestino;
        }
    }
}
