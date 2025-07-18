package Modelo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Modelo_Evaluaciones {
    private int idEvaluacion;
    private int idUnidad;
    private String pregunta;
    private String opcionA;
    private String opcionB;
    private String opcionC;
    private char respuestaCorrecta;
    private String rutaImagen;

    public Modelo_Evaluaciones() {}

    public Modelo_Evaluaciones(int idEvaluacion, int idUnidad, String pregunta, String opcionA, String opcionB, String opcionC, char respuestaCorrecta, String rutaImagen) {
        this.idEvaluacion = idEvaluacion;
        this.idUnidad = idUnidad;
        this.pregunta = pregunta;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.respuestaCorrecta = respuestaCorrecta;
        this.rutaImagen = rutaImagen;
    }

    public static List<Modelo_Evaluaciones> obtenerPreguntasPorUnidad(Connection conn, int idUnidad) {
        List<Modelo_Evaluaciones> preguntas = new ArrayList<>();
        String sql = "SELECT * FROM evaluaciones WHERE id_unidad = ? ORDER BY RAND() LIMIT 10";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUnidad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Modelo_Evaluaciones eval = new Modelo_Evaluaciones();
                eval.setIdEvaluacion(rs.getInt("id_evaluacion"));
                eval.setIdUnidad(rs.getInt("id_unidad"));
                eval.setPregunta(rs.getString("pregunta"));
                eval.setOpcionA(rs.getString("opcion_a"));
                eval.setOpcionB(rs.getString("opcion_b"));
                eval.setOpcionC(rs.getString("opcion_c"));
                eval.setRespuestaCorrecta(rs.getString("respuesta_correcta").charAt(0));
                eval.setRutaImagen(rs.getString("ruta_imagen"));

                preguntas.add(eval);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener preguntas: " + e.getMessage());
        }

        return preguntas;
    }

    // Getters y Setters
    public int getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(int idEvaluacion) { this.idEvaluacion = idEvaluacion; }

    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getOpcionA() { return opcionA; }
    public void setOpcionA(String opcionA) { this.opcionA = opcionA; }

    public String getOpcionB() { return opcionB; }
    public void setOpcionB(String opcionB) { this.opcionB = opcionB; }

    public String getOpcionC() { return opcionC; }
    public void setOpcionC(String opcionC) { this.opcionC = opcionC; }

    public char getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(char respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
}

