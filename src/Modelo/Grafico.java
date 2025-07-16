package Modelo;

import java.sql.*;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico {

    private Connection conn;

    public Grafico(Connection conn) {
        this.conn = conn;
    }

    public DefaultCategoryDataset obtenerUsuariosPorUnidad() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT u.nombre_unidad, COUNT(pu.id_usuario) AS total "
                + "FROM progreso_usuario pu "
                + "JOIN unidades u ON pu.id_unidad = u.id_unidad "
                + "GROUP BY u.nombre_unidad";

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

    public DefaultCategoryDataset obtenerPromedioActividadesPorUnidad() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT u.nombre_unidad, AVG(sub.cantidad) AS promedio_actividades "
                + "FROM (SELECT id_unidad, COUNT(*) AS cantidad FROM actividades GROUP BY id_unidad) sub "
                + "JOIN unidades u ON sub.id_unidad = u.id_unidad "
                + "GROUP BY u.nombre_unidad";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nombreUnidad = rs.getString("nombre_unidad");
                double promedio = rs.getDouble("promedio_actividades");
                dataset.addValue(promedio, "Promedio de Actividades", nombreUnidad);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener promedio de actividades por unidad: " + e.getMessage());
        }

        return dataset;
    }

    public DefaultCategoryDataset obtenerUsuariosSinProgreso() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT u.nombre, COUNT(pu.id_usuario) AS progreso "
                + "FROM usuarios u "
                + "LEFT JOIN progreso_usuario pu ON u.id_usuario = pu.id_usuario "
                + "GROUP BY u.id_usuario, u.nombre "
                + "HAVING COUNT(pu.id_usuario) = 0";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String nombreUsuario = rs.getString("nombre");
                dataset.addValue(1, "Sin Progreso", nombreUsuario); // valor fijo
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener usuarios sin progreso: " + e.getMessage());
        }

        return dataset;
    }

    public DefaultCategoryDataset obtenerContenidoCompletado() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String sql = "SELECT "
                + "SUM(lecciones_completadas) AS total_lecciones, "
                + "SUM(actividades_completadas) AS total_actividades, "
                + "SUM(CASE WHEN evaluacion_aprobada = 1 THEN 1 ELSE 0 END) AS total_evaluaciones "
                + "FROM progreso_usuario";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int lecciones = rs.getInt("total_lecciones");
                int actividades = rs.getInt("total_actividades");
                int evaluaciones = rs.getInt("total_evaluaciones");

                dataset.addValue(lecciones, "Contenido", "Lecciones Completadas");
                dataset.addValue(actividades, "Contenido", "Actividades Completadas");
                dataset.addValue(evaluaciones, "Contenido", "Evaluaciones Aprobadas");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener contenido completado: " + e.getMessage());
        }

        return dataset;
    }
}
