package Modelo;

import Conexion.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Modelo_EvaluacionUnidad2 {
    
    public static class Pregunta {
        public int id;
        public String tipo;
        public String texto;
        public String opcionA;
        public String opcionB;
        public String opcionC;
        public String respuesta;
    }
    
    public static List<Pregunta> obtenerPreguntasAleatorias() {
        List<Pregunta> preguntas = new ArrayList<>();
        String sql = "SELECT * FROM evaluacion_unidad2 ORDER BY RAND() LIMIT 5";
        
        try (Connection conn = Conexion.conectar();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            while (rs.next()) {
                Pregunta p = new Pregunta();
                p.id = rs.getInt("id_pregunta");
                p.tipo = rs.getString("tipo");
                p.texto = rs.getString("pregunta");
                p.opcionA = rs.getString("opcion_a");
                p.opcionB = rs.getString("opcion_b");
                p.opcionC = rs.getString("opcion_c");
                p.respuesta = rs.getString("respuesta_correcta");
                preguntas.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        Collections.shuffle(preguntas);
        return preguntas;
    }
}