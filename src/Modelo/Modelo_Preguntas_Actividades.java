package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Modelo_Preguntas_Actividades {

    private int idPregunta;
    private int idActividad;
    private String tipoPregunta; // ejemplo: "verdadero_falso", "completar", "emparejar", "drag_drop", "audio_test"
    private String textoPregunta;
    private String opcionA;  // Opcionales, pueden ser null si no aplican
    private String opcionB;
    private String opcionC;
    private String opcionD;  // Por si quieres 4 opciones, opcional
    private String respuestaCorrecta;  // Puede ser "A", "B", "verdadero", "palabra correcta", etc.
    private String recursoUrl;  // Para audio, video o imágenes, si la pregunta lo requiere

    public Modelo_Preguntas_Actividades() {
    }

    public Modelo_Preguntas_Actividades(int idPregunta, int idActividad, String tipoPregunta, String textoPregunta,
            String opcionA, String opcionB, String opcionC, String opcionD,
            String respuestaCorrecta, String recursoUrl) {
        this.idPregunta = idPregunta;
        this.idActividad = idActividad;
        this.tipoPregunta = tipoPregunta;
        this.textoPregunta = textoPregunta;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.opcionD = opcionD;
        this.respuestaCorrecta = respuestaCorrecta;
        this.recursoUrl = recursoUrl;
    }

    // Getters y setters
    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getTipoPregunta() {
        return tipoPregunta;
    }

    public void setTipoPregunta(String tipoPregunta) {
        this.tipoPregunta = tipoPregunta;
    }

    public String getTextoPregunta() {
        return textoPregunta;
    }

    public void setTextoPregunta(String textoPregunta) {
        this.textoPregunta = textoPregunta;
    }

    public String getOpcionA() {
        return opcionA;
    }

    public void setOpcionA(String opcionA) {
        this.opcionA = opcionA;
    }

    public String getOpcionB() {
        return opcionB;
    }

    public void setOpcionB(String opcionB) {
        this.opcionB = opcionB;
    }

    public String getOpcionC() {
        return opcionC;
    }

    public void setOpcionC(String opcionC) {
        this.opcionC = opcionC;
    }

    public String getOpcionD() {
        return opcionD;
    }

    public void setOpcionD(String opcionD) {
        this.opcionD = opcionD;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }

    public void setRespuestaCorrecta(String respuestaCorrecta) {
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getRecursoUrl() {
        return recursoUrl;
    }

    public void setRecursoUrl(String recursoUrl) {
        this.recursoUrl = recursoUrl;
    }

    public static List<Modelo_Preguntas_Actividades> obtenerPreguntasPorActividad(Connection conn, int idActividad) {
        List<Modelo_Preguntas_Actividades> listaPreguntas = new ArrayList<>();
        String sql = "SELECT * FROM preguntas_actividades WHERE id_actividad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idActividad);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Modelo_Preguntas_Actividades pregunta = new Modelo_Preguntas_Actividades(
                        rs.getInt("id_pregunta"),
                        rs.getInt("id_actividad"),
                        rs.getString("tipo_pregunta"),
                        rs.getString("texto_pregunta"),
                        rs.getString("opcion_a"),
                        rs.getString("opcion_b"),
                        rs.getString("opcion_c"),
                        rs.getString("opcion_d"),
                        rs.getString("respuesta_correcta"),
                        rs.getString("recurso_url")
                );
                listaPreguntas.add(pregunta);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPreguntas;
    }
}
