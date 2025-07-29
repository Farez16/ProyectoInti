package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Modelo específico para el juego de asociación palabra-imagen de la Actividad 3.
 * Maneja la carga y gestión de datos desde la base de datos.
 *
 * @author ProyectoInti
 * @version 1.0
 */
public class Juego_Emparejar {

    private static final Logger LOGGER = Logger.getLogger(Juego_Emparejar.class.getName());

    /**
     * Clase interna que representa un elemento del juego de asociación.
     */
    public static class ElementoAsociacion {
        private final int idActividad;
        private final String palabraCorrecta;
        private final String rutaImagen;
        private final String[] opcionesIncorrectas;

        public ElementoAsociacion(int idActividad, String palabraCorrecta, String rutaImagen, String[] opcionesIncorrectas) {
            this.idActividad = idActividad;
            this.palabraCorrecta = palabraCorrecta;
            this.rutaImagen = rutaImagen;
            this.opcionesIncorrectas = opcionesIncorrectas != null ? opcionesIncorrectas : new String[0];
        }

        // Getters
        public int getIdActividad() {
            return idActividad;
        }

        public String getPalabraCorrecta() {
            return palabraCorrecta;
        }

        public String getRutaImagen() {
            return rutaImagen;
        }

        public String[] getOpcionesIncorrectas() {
            return opcionesIncorrectas.clone();
        }

        @Override
        public String toString() {
            return String.format("ElementoAsociacion{idActividad=%d, palabraCorrecta='%s', rutaImagen='%s', opcionesIncorrectas=%d}",
                    idActividad, palabraCorrecta, rutaImagen, opcionesIncorrectas.length);
        }
    }

    /**
     * Clase que contiene todos los datos necesarios para el juego.
     */
    public static class DatosJuego {
        private final List<ElementoAsociacion> elementosCorrectos;
        private final List<String> distractores;

        public DatosJuego(List<ElementoAsociacion> elementosCorrectos, List<String> distractores) {
            this.elementosCorrectos = elementosCorrectos != null ? elementosCorrectos : new ArrayList<>();
            this.distractores = distractores != null ? distractores : new ArrayList<>();
        }

        public List<ElementoAsociacion> getElementosCorrectos() {
            return new ArrayList<>(elementosCorrectos);
        }

        public List<String> getDistractores() {
            return new ArrayList<>(distractores);
        }

        public int getTotalElementos() {
            return elementosCorrectos.size();
        }

        public int getTotalDistractores() {
            return distractores.size();
        }

        public boolean esValido() {
            return !elementosCorrectos.isEmpty() && elementosCorrectos.size() >= 3;
        }
    }

    /**
     * Carga los datos del juego de asociación para una unidad específica.
     *
     * @param conn Conexión a la base de datos
     * @param idUnidad ID de la unidad (generalmente 1 para la primera unidad)
     * @return DatosJuego con todos los elementos necesarios para el juego
     * @throws SQLException Si hay error en la consulta a la base de datos
     */
    public static DatosJuego cargarDatosJuego(Connection conn, int idUnidad) throws SQLException {
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }

        List<ElementoAsociacion> elementosCorrectos = new ArrayList<>();
        List<String> distractores = new ArrayList<>();

        String sql = """
                SELECT id_actividad, pregunta, recurso_url, respuesta_correcta,
                       opcion_a, opcion_b, opcion_c
                FROM actividades 
                WHERE tipo = 'juego_emparejar' AND id_unidad = ?
                ORDER BY id_actividad
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idUnidad);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int idActividad = rs.getInt("id_actividad");
                    String pregunta = rs.getString("pregunta");
                    String recursoUrl = rs.getString("recurso_url");
                    String respuestaCorrecta = rs.getString("respuesta_correcta");
                    String opcionA = rs.getString("opcion_a");
                    String opcionB = rs.getString("opcion_b");
                    String opcionC = rs.getString("opcion_c");

                    // Validar datos básicos
                    if (pregunta == null || pregunta.trim().isEmpty() ||
                        recursoUrl == null || recursoUrl.trim().isEmpty()) {
                        LOGGER.warning("Actividad " + idActividad + " tiene datos incompletos, se omite");
                        continue;
                    }

                    // Determinar la palabra correcta
                    String[] opciones = {opcionA, opcionB, opcionC};
                    String palabraCorrecta = determinarPalabraCorrecta(respuestaCorrecta, opciones);

                    if (palabraCorrecta.isEmpty()) {
                        LOGGER.warning("No se pudo determinar palabra correcta para actividad " + idActividad);
                        continue;
                    }

                    // Crear elemento de asociación
                    String[] opcionesIncorrectas = obtenerOpcionesIncorrectas(respuestaCorrecta, opciones);
                    ElementoAsociacion elemento = new ElementoAsociacion(
                            idActividad, 
                            palabraCorrecta, 
                            recursoUrl.trim(),
                            opcionesIncorrectas
                    );

                    elementosCorrectos.add(elemento);

                    // Agregar opciones incorrectas como distractores
                    for (String opcionIncorrecta : opcionesIncorrectas) {
                        if (opcionIncorrecta != null && !opcionIncorrecta.trim().isEmpty()) {
                            distractores.add(opcionIncorrecta.trim());
                        }
                    }

                    LOGGER.info("Elemento cargado: " + elemento.toString());
                }
            }
        }

        LOGGER.info(String.format("Cargados %d elementos correctos y %d distractores para unidad %d",
                elementosCorrectos.size(), distractores.size(), idUnidad));

        return new DatosJuego(elementosCorrectos, distractores);
    }

    /**
     * Determina cuál es la palabra correcta basándose en respuesta_correcta (a, b, o c).
     */
    private static String determinarPalabraCorrecta(String respuestaCorrecta, String[] opciones) {
        if (respuestaCorrecta == null || opciones == null || opciones.length < 3) {
            return "";
        }

        switch (respuestaCorrecta.toLowerCase().trim()) {
            case "a":
                return opciones[0] != null ? opciones[0].trim() : "";
            case "b":
                return opciones[1] != null ? opciones[1].trim() : "";
            case "c":
                return opciones[2] != null ? opciones[2].trim() : "";
            default:
                LOGGER.warning("Respuesta correcta no válida: " + respuestaCorrecta);
                return "";
        }
    }

    /**
     * Obtiene las opciones incorrectas (distractores) excluyendo la respuesta correcta.
     */
    private static String[] obtenerOpcionesIncorrectas(String respuestaCorrecta, String[] opciones) {
        if (respuestaCorrecta == null || opciones == null || opciones.length < 3) {
            return new String[0];
        }

        List<String> incorrectas = new ArrayList<>();
        String respuesta = respuestaCorrecta.toLowerCase().trim();

        if (!"a".equals(respuesta) && opciones[0] != null) {
            incorrectas.add(opciones[0]);
        }
        if (!"b".equals(respuesta) && opciones[1] != null) {
            incorrectas.add(opciones[1]);
        }
        if (!"c".equals(respuesta) && opciones[2] != null) {
            incorrectas.add(opciones[2]);
        }

        return incorrectas.toArray(new String[0]);
    }

    /**
     * Verifica si existen datos suficientes en la base de datos para ejecutar el juego.
     *
     * @param conn Conexión a la base de datos
     * @param idUnidad ID de la unidad a verificar
     * @return true si hay al menos 3 elementos con datos completos
     */
    public static boolean verificarDatosDisponibles(Connection conn, int idUnidad) {
        try {
            DatosJuego datos = cargarDatosJuego(conn, idUnidad);
            return datos.esValido();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al verificar datos disponibles", e);
            return false;
        }
    }

    /**
     * Obtiene información detallada sobre los datos disponibles para debugging.
     *
     * @param conn Conexión a la base de datos
     * @param idUnidad ID de la unidad
     * @return String con información detallada
     */
    public static String obtenerInformacionDatos(Connection conn, int idUnidad) {
        try {
            DatosJuego datos = cargarDatosJuego(conn, idUnidad);
            StringBuilder info = new StringBuilder();
            
            info.append("=== INFORMACIÓN DEL JUEGO DE ASOCIACIÓN ===\n");
            info.append("Unidad: ").append(idUnidad).append("\n");
            info.append("Elementos correctos: ").append(datos.getTotalElementos()).append("\n");
            info.append("Distractores: ").append(datos.getTotalDistractores()).append("\n");
            info.append("¿Datos válidos?: ").append(datos.esValido()).append("\n\n");
            
            info.append("ELEMENTOS CORRECTOS:\n");
            for (ElementoAsociacion elemento : datos.getElementosCorrectos()) {
                info.append("- ID: ").append(elemento.getIdActividad())
                    .append(" | Palabra: ").append(elemento.getPalabraCorrecta())
                    .append(" | Imagen: ").append(elemento.getRutaImagen())
                    .append(" | Distractores: ").append(elemento.getOpcionesIncorrectas().length)
                    .append("\n");
            }
            
            return info.toString();
            
        } catch (SQLException e) {
            return "Error al obtener información: " + e.getMessage();
        }
    }
}