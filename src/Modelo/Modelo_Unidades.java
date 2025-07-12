package Modelo;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo/DAO para gestionar las unidades y su progreso
 * Maneja todas las operaciones de base de datos relacionadas con unidades
 */
public class Modelo_Unidades {
    private int idUnidad;
    private String nombreUnidad;
    private String descripcion;
    private boolean disponible;
    private int progresoLecciones;
    private int progresoActividades;
    private boolean evaluacionAprobada;
    private int calificacion;

    // Constructores
    public Modelo_Unidades() {
        this.disponible = false;
        this.progresoLecciones = 0;
        this.progresoActividades = 0;
        this.evaluacionAprobada = false;
        this.calificacion = 0;
    }

    public Modelo_Unidades(int idUnidad, String nombreUnidad, String descripcion) {
        this();
        this.idUnidad = idUnidad;
        this.nombreUnidad = nombreUnidad;
        this.descripcion = descripcion;
    }

    public Modelo_Unidades(int idUnidad, String nombreUnidad, String descripcion, 
                          boolean disponible, int progresoLecciones, int progresoActividades,
                          boolean evaluacionAprobada, int calificacion) {
        this.idUnidad = idUnidad;
        this.nombreUnidad = nombreUnidad;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.progresoLecciones = progresoLecciones;
        this.progresoActividades = progresoActividades;
        this.evaluacionAprobada = evaluacionAprobada;
        this.calificacion = calificacion;
    }

    // Getters y Setters
    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getProgresoLecciones() {
        return progresoLecciones;
    }

    public void setProgresoLecciones(int progresoLecciones) {
        this.progresoLecciones = progresoLecciones;
    }

    public int getProgresoActividades() {
        return progresoActividades;
    }

    public void setProgresoActividades(int progresoActividades) {
        this.progresoActividades = progresoActividades;
    }

    public boolean isEvaluacionAprobada() {
        return evaluacionAprobada;
    }

    public void setEvaluacionAprobada(boolean evaluacionAprobada) {
        this.evaluacionAprobada = evaluacionAprobada;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    // ========================= MÉTODOS DAO =========================

    /**
     * Obtiene todas las unidades disponibles
     * @return Lista de unidades
     */
    public static List<Modelo_Unidades> obtenerTodasLasUnidades() {
        List<Modelo_Unidades> unidades = new ArrayList<>();
        
        try {
            Connection conn = Conexion.conectar();
            String sql = "SELECT id_unidad, nombre_unidad, descripcion FROM unidades ORDER BY id_unidad";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Modelo_Unidades unidad = new Modelo_Unidades(
                    rs.getInt("id_unidad"),
                    rs.getString("nombre_unidad"),
                    rs.getString("descripcion")
                );
                unidades.add(unidad);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener unidades: " + e.getMessage());
        }
        
        return unidades;
    }

    /**
     * Obtiene el progreso de un usuario para todas las unidades
     * @param correo Correo del usuario
     * @return Lista de unidades con progreso
     */
    public static List<Modelo_Unidades> obtenerUnidadesConProgreso(String correo) {
        List<Modelo_Unidades> unidades = new ArrayList<>();
        
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                SELECT u.id_unidad, u.nombre_unidad, u.descripcion,
                       COALESCE(pu.lecciones_completadas, 0) as lecciones_completadas,
                       COALESCE(pu.actividades_completadas, 0) as actividades_completadas,
                       COALESCE(pu.evaluacion_aprobada, 0) as evaluacion_aprobada,
                       COALESCE(pu.calificacion, 0) as calificacion
                FROM unidades u
                LEFT JOIN progreso_usuario pu ON u.id_unidad = pu.id_unidad 
                    AND pu.id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?)
                ORDER BY u.id_unidad
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                Modelo_Unidades unidad = new Modelo_Unidades(
                    rs.getInt("id_unidad"),
                    rs.getString("nombre_unidad"),
                    rs.getString("descripcion"),
                    false, // Se calcula después
                    rs.getInt("lecciones_completadas"),
                    rs.getInt("actividades_completadas"),
                    rs.getBoolean("evaluacion_aprobada"),
                    rs.getInt("calificacion")
                );
                unidades.add(unidad);
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener unidades con progreso: " + e.getMessage());
        }
        
        return unidades;
    }

    /**
     * Verifica si una unidad está disponible para el usuario
     * @param idUnidad ID de la unidad
     * @param correo Correo del usuario
     * @return true si está disponible
     */
    public static boolean verificarDisponibilidadUnidad(int idUnidad, String correo) {
        if (idUnidad == 1) return true; // Unidad 1 siempre disponible
        
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                SELECT evaluacion_aprobada 
                FROM progreso_usuario pu
                JOIN usuarios u ON pu.id_usuario = u.id_usuario
                WHERE u.correo = ? AND pu.id_unidad = ?
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, correo);
            pst.setInt(2, idUnidad - 1); // Verificar unidad anterior
            ResultSet rs = pst.executeQuery();
            
            boolean disponible = false;
            if (rs.next()) {
                disponible = rs.getBoolean("evaluacion_aprobada");
            }
            
            rs.close();
            pst.close();
            conn.close();
            
            return disponible;
            
        } catch (SQLException e) {
            System.err.println("Error al verificar disponibilidad: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el array de disponibilidad de todas las unidades para un usuario
     * @param correo Correo del usuario
     * @return Array boolean con disponibilidad [0=no usado, 1-4=unidades]
     */
    public static boolean[] obtenerDisponibilidadUnidades(String correo) {
        boolean[] disponibles = new boolean[5]; // índice 0 no usado, 1-4 para unidades
        disponibles[1] = true; // Unidad 1 siempre disponible
        
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                SELECT u.id_unidad, 
                       COALESCE(pu.evaluacion_aprobada, 0) as evaluacion_aprobada
                FROM unidades u
                LEFT JOIN progreso_usuario pu ON u.id_unidad = pu.id_unidad 
                    AND pu.id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?)
                ORDER BY u.id_unidad
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int idUnidad = rs.getInt("id_unidad");
                boolean evaluacionAprobada = rs.getBoolean("evaluacion_aprobada");
                
                if (evaluacionAprobada && idUnidad < 4) {
                    disponibles[idUnidad + 1] = true; // Habilitar siguiente unidad
                }
            }
            
            rs.close();
            pst.close();
            conn.close();
            
        } catch (SQLException e) {
            System.err.println("Error al obtener disponibilidad: " + e.getMessage());
        }
        
        return disponibles;
    }

    /**
     * Registra o actualiza el progreso de visualización de una unidad
     * @param idUnidad ID de la unidad
     * @param correo Correo del usuario
     * @return true si se registró correctamente
     */
    public static boolean registrarVisualizacionUnidad(int idUnidad, String correo) {
        try {
            Connection conn = Conexion.conectar();
            
            // Verificar si ya existe un registro de progreso
            String sqlVerificar = """
                SELECT COUNT(*) as existe 
                FROM progreso_usuario pu
                JOIN usuarios u ON pu.id_usuario = u.id_usuario
                WHERE u.correo = ? AND pu.id_unidad = ?
            """;
            
            PreparedStatement pstVerificar = conn.prepareStatement(sqlVerificar);
            pstVerificar.setString(1, correo);
            pstVerificar.setInt(2, idUnidad);
            ResultSet rsVerificar = pstVerificar.executeQuery();
            
            boolean existe = false;
            if (rsVerificar.next()) {
                existe = rsVerificar.getInt("existe") > 0;
            }
            
            rsVerificar.close();
            pstVerificar.close();
            
            if (!existe) {
                // Crear nuevo registro de progreso
                String sqlInsertar = """
                    INSERT INTO progreso_usuario (id_usuario, id_unidad, lecciones_completadas, 
                                                actividades_completadas, evaluacion_aprobada, calificacion)
                    SELECT u.id_usuario, ?, 0, 0, 0, 0
                    FROM usuarios u WHERE u.correo = ?
                """;
                
                PreparedStatement pstInsertar = conn.prepareStatement(sqlInsertar);
                pstInsertar.setInt(1, idUnidad);
                pstInsertar.setString(2, correo);
                int filasAfectadas = pstInsertar.executeUpdate();
                pstInsertar.close();
                
                conn.close();
                return filasAfectadas > 0;
            }
            
            conn.close();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar visualización: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene el progreso general del usuario (porcentaje de unidades completadas)
     * @param correo Correo del usuario
     * @return Porcentaje de progreso (0-100)
     */
    public static int obtenerProgresoGeneral(String correo) {
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                SELECT 
                    COUNT(*) as total_unidades,
                    SUM(CASE WHEN pu.evaluacion_aprobada = 1 THEN 1 ELSE 0 END) as unidades_completadas
                FROM unidades u
                LEFT JOIN progreso_usuario pu ON u.id_unidad = pu.id_unidad
                    AND pu.id_usuario = (SELECT id_usuario FROM usuarios WHERE correo = ?)
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, correo);
            ResultSet rs = pst.executeQuery();
            
            int progreso = 0;
            if (rs.next()) {
                int total = rs.getInt("total_unidades");
                int completadas = rs.getInt("unidades_completadas");
                if (total > 0) {
                    progreso = (completadas * 100) / total;
                }
            }
            
            rs.close();
            pst.close();
            conn.close();
            
            return progreso;
            
        } catch (SQLException e) {
            System.err.println("Error al obtener progreso general: " + e.getMessage());
            return 0;
        }
    }

    /**
     * Actualiza el progreso de lecciones completadas
     * @param idUnidad ID de la unidad
     * @param correo Correo del usuario
     * @param leccionesCompletadas Número de lecciones completadas
     * @return true si se actualizó correctamente
     */
    public static boolean actualizarProgresoLecciones(int idUnidad, String correo, int leccionesCompletadas) {
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                UPDATE progreso_usuario pu
                JOIN usuarios u ON pu.id_usuario = u.id_usuario
                SET pu.lecciones_completadas = ?
                WHERE u.correo = ? AND pu.id_unidad = ?
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, leccionesCompletadas);
            pst.setString(2, correo);
            pst.setInt(3, idUnidad);
            
            int filasAfectadas = pst.executeUpdate();
            
            pst.close();
            conn.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar progreso lecciones: " + e.getMessage());
            return false;
        }
    }

    /**
     * Actualiza el progreso de actividades completadas
     * @param idUnidad ID de la unidad
     * @param correo Correo del usuario
     * @param actividadesCompletadas Número de actividades completadas
     * @return true si se actualizó correctamente
     */
    public static boolean actualizarProgresoActividades(int idUnidad, String correo, int actividadesCompletadas) {
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                UPDATE progreso_usuario pu
                JOIN usuarios u ON pu.id_usuario = u.id_usuario
                SET pu.actividades_completadas = ?
                WHERE u.correo = ? AND pu.id_unidad = ?
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, actividadesCompletadas);
            pst.setString(2, correo);
            pst.setInt(3, idUnidad);
            
            int filasAfectadas = pst.executeUpdate();
            
            pst.close();
            conn.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar progreso actividades: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra la aprobación de una evaluación
     * @param idUnidad ID de la unidad
     * @param correo Correo del usuario
     * @param calificacion Calificación obtenida
     * @return true si se registró correctamente
     */
    public static boolean registrarEvaluacionAprobada(int idUnidad, String correo, int calificacion) {
        try {
            Connection conn = Conexion.conectar();
            String sql = """
                UPDATE progreso_usuario pu
                JOIN usuarios u ON pu.id_usuario = u.id_usuario
                SET pu.evaluacion_aprobada = 1, pu.calificacion = ?
                WHERE u.correo = ? AND pu.id_unidad = ?
            """;
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, calificacion);
            pst.setString(2, correo);
            pst.setInt(3, idUnidad);
            
            int filasAfectadas = pst.executeUpdate();
            
            pst.close();
            conn.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar evaluación aprobada: " + e.getMessage());
            return false;
        }
    }

    // ========================= MÉTODOS UTILITARIOS =========================

    /**
     * Calcula el progreso total de la unidad como porcentaje
     * @param totalLecciones Total de lecciones en la unidad
     * @param totalActividades Total de actividades en la unidad
     * @return Porcentaje de progreso (0-100)
     */
    public int calcularProgresoTotal(int totalLecciones, int totalActividades) {
        if (totalLecciones == 0 && totalActividades == 0) {
            return 0;
        }
        
        int total = totalLecciones + totalActividades;
        int completadas = progresoLecciones + progresoActividades;
        
        return (completadas * 100) / total;
    }

    /**
     * Verifica si la unidad está completamente terminada
     * @param totalLecciones Total de lecciones en la unidad
     * @param totalActividades Total de actividades en la unidad
     * @return true si está completamente terminada
     */
    public boolean estaCompletada(int totalLecciones, int totalActividades) {
        return progresoLecciones >= totalLecciones && 
               progresoActividades >= totalActividades && 
               evaluacionAprobada;
    }

    /**
     * Obtiene el estado de la unidad como texto
     * @return Estado de la unidad
     */
    public String getEstadoTexto() {
        if (!disponible) {
            return "No disponible";
        } else if (evaluacionAprobada) {
            return "Completada";
        } else if (progresoLecciones > 0 || progresoActividades > 0) {
            return "En progreso";
        } else {
            return "Disponible";
        }
    }

    /**
     * Obtiene el color asociado al estado de la unidad
     * @return Color como string hexadecimal
     */
    public String getColorEstado() {
        if (!disponible) {
            return "#95A5A6"; // Gris
        } else if (evaluacionAprobada) {
            return "#27AE60"; // Verde
        } else if (progresoLecciones > 0 || progresoActividades > 0) {
            return "#F39C12"; // Naranja
        } else {
            return "#3498DB"; // Azul
        }
    }

    @Override
    public String toString() {
        return nombreUnidad; // util para mostrar en JComboBox o JList
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Modelo_Unidades unidad = (Modelo_Unidades) obj;
        return idUnidad == unidad.idUnidad;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idUnidad);
    }
}