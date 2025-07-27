package Modelo;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reportes {

    // Constructor si necesitas inyectar conexión (no lo estás usando en métodos static)
    private Connection conn;

    public Reportes(Connection conn) {
        this.conn = conn;
    }

    // Reporte 1: Progreso General de los Estudiantes
    public ResultSet obtenerProgresoGeneral(Date fechaInicio, Date fechaFin) throws SQLException {
        String sql = "SELECT u.nombre AS nombre_usuario, un.nombre_unidad, "
                + "pu.lecciones_completadas, pu.actividades_completadas, "
                + "pu.calificacion, pu.fecha_actualizacion "
                + "FROM progreso_usuario pu "
                + "JOIN usuarios u ON pu.id_usuario = u.id_usuario "
                + "JOIN unidades un ON pu.id_unidad = un.id_unidad "
                + "WHERE pu.fecha_actualizacion BETWEEN ? AND ? "
                + "ORDER BY u.nombre, un.nombre_unidad";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
        stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));

        return stmt.executeQuery();
    }

    // Reporte 2: Estadísticas de Rendimiento por Unidad
    public ResultSet obtenerEstadisticasUnidad(Date fechaInicio, Date fechaFin) throws SQLException {
        String sql = "SELECT un.nombre_unidad, "
                + "COUNT(DISTINCT pu.id_usuario) AS cantidad_estudiantes, "
                + "AVG(pu.calificacion) AS promedio_calificaciones, "
                + "MAX(pu.fecha_actualizacion) AS ultima_actividad "
                + "FROM progreso_usuario pu "
                + "JOIN unidades un ON pu.id_unidad = un.id_unidad "
                + "WHERE pu.fecha_actualizacion BETWEEN ? AND ? "
                + "GROUP BY un.nombre_unidad";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
        stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));

        return stmt.executeQuery();
    }

    // Reporte 3: Listado de Usuarios con Última Actividad
    public ResultSet obtenerUsuariosUltimaActividad(Date fechaInicio, Date fechaFin) throws SQLException {
        String sql = "SELECT u.nombre, u.correo, r.nombre_rol AS rol, "
                + "MAX(pu.fecha_actualizacion) AS ultima_actividad, "
                + "u.fecha_registro "
                + "FROM usuarios u "
                + "JOIN roles r ON u.id_rol = r.id_rol "
                + "LEFT JOIN progreso_usuario pu ON u.id_usuario = pu.id_usuario "
                + "WHERE (pu.fecha_actualizacion BETWEEN ? AND ? OR pu.fecha_actualizacion IS NULL) "
                + "GROUP BY u.id_usuario "
                + "ORDER BY ultima_actividad DESC";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDate(1, new java.sql.Date(fechaInicio.getTime()));
        stmt.setDate(2, new java.sql.Date(fechaFin.getTime()));

        return stmt.executeQuery();
    }
}
