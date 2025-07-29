package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Modelo_Emparejar {

    private final Connection conn;

    public Modelo_Emparejar(Connection conn) {
        this.conn = conn;
    }

    // Clase para representar una pregunta de emparejar con opciones y respuesta correcta (una letra)
    public static class EmparejarItem {
        public int idActividad;
        public String pregunta;
        public String opcionA;
        public String opcionB;
        public String opcionC;
        public char respuestaCorrecta;

        public EmparejarItem(int idActividad, String pregunta, String opcionA, String opcionB, String opcionC, char respuestaCorrecta) {
            this.idActividad = idActividad;
            this.pregunta = pregunta;
            this.opcionA = opcionA;
            this.opcionB = opcionB;
            this.opcionC = opcionC;
            this.respuestaCorrecta = respuestaCorrecta;
        }
    }

    // Método para obtener todas las preguntas emparejar de la unidad 1
    public List<EmparejarItem> obtenerPreguntasPorUnidad(int idUnidad) {
        List<EmparejarItem> preguntas = new ArrayList<>();
        String sql = "SELECT id_actividad, pregunta, opcion_a, opcion_b, opcion_c, respuesta_correcta FROM actividades WHERE id_unidad = ? AND tipo = 'emparejar'";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUnidad);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idActividad = rs.getInt("id_actividad");
                String pregunta = rs.getString("pregunta");
                String opcionA = rs.getString("opcion_a");
                String opcionB = rs.getString("opcion_b");
                String opcionC = rs.getString("opcion_c");
                String resp = rs.getString("respuesta_correcta");
                char respuestaCorrecta = (resp != null && !resp.isEmpty()) ? resp.charAt(0) : ' ';

                preguntas.add(new EmparejarItem(idActividad, pregunta, opcionA, opcionB, opcionC, respuestaCorrecta));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preguntas;
    }
}
