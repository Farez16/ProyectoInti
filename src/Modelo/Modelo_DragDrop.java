package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Modelo_DragDrop {
    
    private final Connection conn;

    public Modelo_DragDrop(Connection conn) {
        this.conn = conn;
    }

 public List<DragDropItem> obtenerItemsPorRangoActividad(int desdeId, int hastaId) {
    List<DragDropItem> items = new ArrayList<>();
    String sql = "SELECT * FROM actividades WHERE tipo = 'drag_drop' AND id_actividad BETWEEN ? AND ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, desdeId);
        ps.setInt(2, hastaId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int idActividad = rs.getInt("id_actividad");
            String opcionA = rs.getString("opcion_a");
            String opcionB = rs.getString("opcion_b");
            String opcionC = rs.getString("opcion_c");
            String correcta = rs.getString("respuesta_correcta"); // 'A','B','C'

            if (opcionA != null && !opcionA.isEmpty()) {
                items.add(new DragDropItem(0, idActividad, opcionA, null, correcta.equalsIgnoreCase("A") ? "correcta" : "incorrecta"));
            }
            if (opcionB != null && !opcionB.isEmpty()) {
                items.add(new DragDropItem(0, idActividad, opcionB, null, correcta.equalsIgnoreCase("B") ? "correcta" : "incorrecta"));
            }
            if (opcionC != null && !opcionC.isEmpty()) {
                items.add(new DragDropItem(0, idActividad, opcionC, null, correcta.equalsIgnoreCase("C") ? "correcta" : "incorrecta"));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return items;
}
 
 public String obtenerPreguntaPorId(int idActividad) {
    String pregunta = null;
    String sql = "SELECT pregunta FROM actividades WHERE id_actividad = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, idActividad);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            pregunta = rs.getString("pregunta");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return pregunta;
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
