package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Modelo_Actividades {
    private int idActividad;
    private int idUnidad;
    private String tipo; // crucigrama, audio_test, drag_drop, pregunta_respuesta
    private String opcionA;
    private String opcionB;
    private String opcionC;
    private char respuestaCorrecta;
    private String pregunta;
    private String recursoUrl;

    public Modelo_Actividades() {}

    public Modelo_Actividades(int idActividad, int idUnidad, String tipo, String opcionA,
                             String opcionB, String opcionC, char respuestaCorrecta,
                             String pregunta, String recursoUrl) {
        this.idActividad = idActividad;
        this.idUnidad = idUnidad;
        this.tipo = tipo;
        this.opcionA = opcionA;
        this.opcionB = opcionB;
        this.opcionC = opcionC;
        this.respuestaCorrecta = respuestaCorrecta;
        this.pregunta = pregunta;
        this.recursoUrl = recursoUrl;
    }

    // Getters y setters

    public int getIdActividad() { return idActividad; }
    public void setIdActividad(int idActividad) { this.idActividad = idActividad; }

    public int getIdUnidad() { return idUnidad; }
    public void setIdUnidad(int idUnidad) { this.idUnidad = idUnidad; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getOpcionA() { return opcionA; }
    public void setOpcionA(String opcionA) { this.opcionA = opcionA; }

    public String getOpcionB() { return opcionB; }
    public void setOpcionB(String opcionB) { this.opcionB = opcionB; }

    public String getOpcionC() { return opcionC; }
    public void setOpcionC(String opcionC) { this.opcionC = opcionC; }

    public char getRespuestaCorrecta() { return respuestaCorrecta; }
    public void setRespuestaCorrecta(char respuestaCorrecta) { this.respuestaCorrecta = respuestaCorrecta; }

    public String getPregunta() { return pregunta; }
    public void setPregunta(String pregunta) { this.pregunta = pregunta; }

    public String getRecursoUrl() { return recursoUrl; }
    public void setRecursoUrl(String recursoUrl) { this.recursoUrl = recursoUrl; }

    // Método para cargar actividad desde base por id
    public static Modelo_Actividades obtenerPorId(Connection conn, int idActividad) {
        Modelo_Actividades actividad = null;
        String sql = "SELECT * FROM actividades WHERE id_actividad = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idActividad);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                actividad = new Modelo_Actividades(
                    rs.getInt("id_actividad"),
                    rs.getInt("id_unidad"),
                    rs.getString("tipo"),
                    rs.getString("opcion_a"),
                    rs.getString("opcion_b"),
                    rs.getString("opcion_c"),
                    rs.getString("respuesta_correcta").charAt(0),
                    rs.getString("pregunta"),
                    rs.getString("recurso_url")
                );
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return actividad;
    }
}
