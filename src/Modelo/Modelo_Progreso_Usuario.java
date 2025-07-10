/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Conexion.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author Jhony Espinoza
 */
public class Modelo_Progreso_Usuario {
   private int idProgreso;
    private int idUsuario;
    private int idUnidad;
    private int leccionesCompletadas;
    private int actividadesCompletadas;
    private boolean evaluacionAprobada;
    private int calificacion;
    private LocalDateTime fechaActualizacion;

    public Modelo_Progreso_Usuario() {
    }

    public Modelo_Progreso_Usuario(int idProgreso, int idUsuario, int idUnidad, int leccionesCompletadas,
                           int actividadesCompletadas, boolean evaluacionAprobada, int calificacion,
                           LocalDateTime fechaActualizacion) {
        this.idProgreso = idProgreso;
        this.idUsuario = idUsuario;
        this.idUnidad = idUnidad;
        this.leccionesCompletadas = leccionesCompletadas;
        this.actividadesCompletadas = actividadesCompletadas;
        this.evaluacionAprobada = evaluacionAprobada;
        this.calificacion = calificacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    public int getIdProgreso() {
        return idProgreso;
    }

    public void setIdProgreso(int idProgreso) {
        this.idProgreso = idProgreso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public int getLeccionesCompletadas() {
        return leccionesCompletadas;
    }

    public void setLeccionesCompletadas(int leccionesCompletadas) {
        this.leccionesCompletadas = leccionesCompletadas;
    }

    public int getActividadesCompletadas() {
        return actividadesCompletadas;
    }

    public void setActividadesCompletadas(int actividadesCompletadas) {
        this.actividadesCompletadas = actividadesCompletadas;
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

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }    
    // Método para obtener el progreso desde BD
   public static Modelo_Progreso_Usuario obtenerProgreso(int idUsuario, int idUnidad) {
       String sql = "SELECT * FROM progreso_usuario WHERE id_usuario = ? AND id_unidad = ?";
       try (Connection conn = Conexion.conectar();
            PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setInt(1, idUsuario);
           ps.setInt(2, idUnidad);
           try (ResultSet rs = ps.executeQuery()) {
               if (rs.next()) {
                   Modelo_Progreso_Usuario progreso = new Modelo_Progreso_Usuario();
                   progreso.setIdProgreso(rs.getInt("id_progreso"));
                   progreso.setIdUsuario(rs.getInt("id_usuario"));
                   progreso.setIdUnidad(rs.getInt("id_unidad"));
                   progreso.setLeccionesCompletadas(rs.getInt("lecciones_completadas"));
                   progreso.setActividadesCompletadas(rs.getInt("actividades_completadas"));
                   progreso.setEvaluacionAprobada(rs.getBoolean("evaluacion_aprobada"));
                   progreso.setCalificacion(rs.getInt("calificacion"));
                   Timestamp ts = rs.getTimestamp("fecha_actualizacion");
                   if (ts != null) {
                       progreso.setFechaActualizacion(ts.toLocalDateTime());
                   }
                   return progreso;
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
   }

   // Método para insertar nuevo progreso
   public static boolean guardarProgreso(Modelo_Progreso_Usuario progreso) {
    // Validaciones previas
    if (progreso.getIdUsuario() <= 0) {
        System.out.println("❌ ERROR: ID de usuario inválido: " + progreso.getIdUsuario());
        return false;
    }
    if (progreso.getIdUnidad() <= 0) {
        System.out.println("❌ ERROR: ID de unidad inválido: " + progreso.getIdUnidad());
        return false;
    }

    String sql = "INSERT INTO progreso_usuario (id_usuario, id_unidad, lecciones_completadas, actividades_completadas, evaluacion_aprobada, calificacion, fecha_actualizacion) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = Conexion.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Depuración
        System.out.println("✅ GUARDANDO PROGRESO → id_usuario=" + progreso.getIdUsuario() + ", id_unidad=" + progreso.getIdUnidad());

        // Asignación de valores
        ps.setInt(1, progreso.getIdUsuario());
        ps.setInt(2, progreso.getIdUnidad());
        ps.setInt(3, progreso.getLeccionesCompletadas());
        ps.setInt(4, progreso.getActividadesCompletadas());
        ps.setBoolean(5, progreso.isEvaluacionAprobada());
        ps.setInt(6, progreso.getCalificacion());
        ps.setTimestamp(7, Timestamp.valueOf(progreso.getFechaActualizacion()));

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("❌ ERROR al guardar progreso:");
        e.printStackTrace();
    }

    return false;
}


   // Método para actualizar progreso existente
  public static boolean actualizarProgreso(Modelo_Progreso_Usuario progreso) {
    // Validaciones previas
    if (progreso.getIdUsuario() <= 0) {
        System.out.println("❌ ERROR: ID de usuario inválido: " + progreso.getIdUsuario());
        return false;
    }
    if (progreso.getIdUnidad() <= 0) {
        System.out.println("❌ ERROR: ID de unidad inválido: " + progreso.getIdUnidad());
        return false;
    }

    String sql = "UPDATE progreso_usuario SET lecciones_completadas = ?, actividades_completadas = ?, evaluacion_aprobada = ?, calificacion = ?, fecha_actualizacion = ? WHERE id_usuario = ? AND id_unidad = ?";

    try (Connection conn = Conexion.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Depuración
        System.out.println("✏️ ACTUALIZANDO PROGRESO → id_usuario=" + progreso.getIdUsuario() + ", id_unidad=" + progreso.getIdUnidad());

        // Asignación de valores
        ps.setInt(1, progreso.getLeccionesCompletadas());
        ps.setInt(2, progreso.getActividadesCompletadas());
        ps.setBoolean(3, progreso.isEvaluacionAprobada());
        ps.setInt(4, progreso.getCalificacion());
        ps.setTimestamp(5, Timestamp.valueOf(progreso.getFechaActualizacion()));
        ps.setInt(6, progreso.getIdUsuario());
        ps.setInt(7, progreso.getIdUnidad());

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("❌ ERROR al actualizar progreso:");
        e.printStackTrace();
    }

    return false;
}

}
