package Modelo;

import java.sql.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico {

    private Connection conn;

    public Grafico(Connection conn) {
        this.conn = conn;
    }

    public DefaultCategoryDataset obtenerPromedioPorUnidad() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT unidad, AVG(nota) AS promedio FROM evaluaciones GROUP BY unidad";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String unidad = rs.getString("unidad");
                double promedio = rs.getDouble("promedio");

                dataset.addValue(promedio, "Promedio", unidad);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener promedios: " + e.getMessage());
        }

        return dataset;
    }

    public DefaultCategoryDataset obtenerUsuariosPorUnidad() {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String sql = "SELECT u.nombre_unidad, COUNT(pu.id_usuario) AS total " +
                 "FROM progreso_usuario pu " +
                 "JOIN unidades u ON pu.id_unidad = u.id_unidad " +
                 "GROUP BY u.nombre_unidad";

    try {
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String nombreUnidad = rs.getString("nombre_unidad");
            int total = rs.getInt("total");

            dataset.addValue(total, "Usuarios", nombreUnidad);
        }

    } catch (SQLException e) {
        System.out.println("Error al obtener usuarios por unidad: " + e.getMessage());
    }

    return dataset;
}

}
