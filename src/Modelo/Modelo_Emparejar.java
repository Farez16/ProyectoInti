package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Modelo_Emparejar {

    private final Connection conn;

    public Modelo_Emparejar(Connection conn) {
        this.conn = conn;
    }

    public List<EmparejarItem> obtenerOpcionesPorActividad(int idActividad) {
        List<EmparejarItem> items = new ArrayList<>();
        String sql = "SELECT * FROM emparejar_opciones WHERE id_actividad = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idActividad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                EmparejarItem item = new EmparejarItem(
                    rs.getInt("id_emparejar"),
                    rs.getInt("id_actividad"),
                    rs.getString("texto_origen"),
                    rs.getString("recurso_origen_url"),
                    rs.getString("texto_destino"),
                    rs.getString("recurso_destino_url")
                );
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static class EmparejarItem {
        public int id;
        public int idActividad;
        public String textoOrigen;
        public String recursoOrigenUrl;
        public String textoDestino;
        public String recursoDestinoUrl;

        public EmparejarItem(int id, int idActividad, String textoOrigen, String recursoOrigenUrl, String textoDestino, String recursoDestinoUrl) {
            this.id = id;
            this.idActividad = idActividad;
            this.textoOrigen = textoOrigen;
            this.recursoOrigenUrl = recursoOrigenUrl;
            this.textoDestino = textoDestino;
            this.recursoDestinoUrl = recursoDestinoUrl;
        }
    }
}
