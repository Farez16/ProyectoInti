package Modelo;

import java.sql.*;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.Date;

public class Grafico {
    private Connection conn;

    public Grafico(Connection conn) {
        this.conn = conn;
    }

    /**
     * Obtiene nuevos registros de usuarios por rango de fechas
     * @param fechaDesde Fecha inicial
     * @param fechaHasta Fecha final
     * @return Dataset con fechas como categorías y cantidad de registros
     */
    public DefaultCategoryDataset obtenerRegistrosPorFecha(Date fechaDesde, Date fechaHasta) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT DATE(fecha_registro) AS fecha, COUNT(*) AS total "
                   + "FROM usuarios "
                   + "WHERE fecha_registro BETWEEN ? AND ? "
                   + "GROUP BY DATE(fecha_registro) "
                   + "ORDER BY fecha";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(fechaDesde.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(fechaHasta.getTime()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getInt("total"), "Registros", rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerRegistrosPorFecha: " + e.getMessage());
        }
        return dataset;
    }

    /**
     * Obtiene actividad de usuarios por unidad con filtro de fechas
     * @param fechaDesde Fecha inicial
     * @param fechaHasta Fecha final
     * @return Dataset con unidades como categorías y cantidad de usuarios
     */
    public DefaultCategoryDataset obtenerUsuariosActivosPorUnidad(Date fechaDesde, Date fechaHasta) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT u.nombre_unidad, COUNT(DISTINCT pu.id_usuario) AS total "
                   + "FROM progreso_usuario pu "
                   + "JOIN unidades u ON pu.id_unidad = u.id_unidad "
                   + "WHERE pu.fecha_actualizacion BETWEEN ? AND ? "
                   + "GROUP BY u.nombre_unidad";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(fechaDesde.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(fechaHasta.getTime()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getInt("total"), "Usuarios activos", rs.getString("nombre_unidad"));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerUsuariosActivosPorUnidad: " + e.getMessage());
        }
        return dataset;
    }

    /**
     * Obtiene el progreso completado (lecciones/actividades/evaluaciones) por fechas
     * @param fechaDesde Fecha inicial
     * @param fechaHasta Fecha final
     * @return Dataset con tipos de contenido como categorías
     */
    public DefaultCategoryDataset obtenerProgresoCompletado(Date fechaDesde, Date fechaHasta) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT "
                   + "SUM(lecciones_completadas) AS lecciones, "
                   + "SUM(actividades_completadas) AS actividades, "
                   + "SUM(CASE WHEN evaluacion_aprobada = 1 THEN 1 ELSE 0 END) AS evaluaciones "
                   + "FROM progreso_usuario "
                   + "WHERE fecha_actualizacion BETWEEN ? AND ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(fechaDesde.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(fechaHasta.getTime()));
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dataset.addValue(rs.getInt("lecciones"), "Completados", "Lecciones");
                dataset.addValue(rs.getInt("actividades"), "Completados", "Actividades");
                dataset.addValue(rs.getInt("evaluaciones"), "Completados", "Evaluaciones");
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerProgresoCompletado: " + e.getMessage());
        }
        return dataset;
    }

    /**
     * Obtiene métricas de evaluación por rango de fechas
     * @param fechaDesde Fecha inicial
     * @param fechaHasta Fecha final
     * @return Dataset con unidades y resultados de evaluaciones
     */
    public DefaultCategoryDataset obtenerMetricasEvaluaciones(Date fechaDesde, Date fechaHasta) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT u.nombre_unidad, "
                   + "SUM(CASE WHEN pu.evaluacion_aprobada = 1 THEN 1 ELSE 0 END) AS aprobados, "
                   + "SUM(CASE WHEN pu.evaluacion_aprobada = 0 THEN 1 ELSE 0 END) AS reprobados "
                   + "FROM progreso_usuario pu "
                   + "JOIN unidades u ON pu.id_unidad = u.id_unidad "
                   + "WHERE pu.fecha_actualizacion BETWEEN ? AND ? "
                   + "GROUP BY u.nombre_unidad";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(fechaDesde.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(fechaHasta.getTime()));
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset.addValue(rs.getInt("aprobados"), "Aprobados", rs.getString("nombre_unidad"));
                dataset.addValue(rs.getInt("reprobados"), "Reprobados", rs.getString("nombre_unidad"));
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerMetricasEvaluaciones: " + e.getMessage());
        }
        return dataset;
    }

    /**
     * Obtiene usuarios inactivos (sin actividad en el rango de fechas)
     * @param fechaDesde Fecha inicial
     * @param fechaHasta Fecha final
     * @return Dataset con cantidad de usuarios inactivos
     */
    public DefaultCategoryDataset obtenerUsuariosInactivos(Date fechaDesde, Date fechaHasta) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql = "SELECT COUNT(*) AS inactivos "
                   + "FROM usuarios "
                   + "WHERE id_usuario NOT IN ("
                   + "    SELECT DISTINCT id_usuario FROM progreso_usuario "
                   + "    WHERE fecha_actualizacion BETWEEN ? AND ?"
                   + ")";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, new java.sql.Timestamp(fechaDesde.getTime()));
            ps.setTimestamp(2, new java.sql.Timestamp(fechaHasta.getTime()));
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dataset.addValue(rs.getInt("inactivos"), "Usuarios", "Inactivos");
            }
        } catch (SQLException e) {
            System.err.println("Error en obtenerUsuariosInactivos: " + e.getMessage());
        }
        return dataset;
    }
}