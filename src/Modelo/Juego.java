package Modelo;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Juego {

    private String palabra;
    private String rutaImagen;
    
     private Connection conn;

    public Juego(Connection conn) {
        this.conn = conn;
    }

    public Juego() {
    }

    public Juego(String palabra, String rutaImagen) {
        this.palabra = palabra;
        this.rutaImagen = rutaImagen;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
    // 👉 Método para traer datos aleatorios desde la base de datos
    public List<Juego> obtenerTodoAleatorio(int cantidad) {
        List<Juego> lista = new ArrayList<>();
        
        if (conn == null) {
            System.err.println("❌ Conexión a la base de datos es null");
            return crearDatosPorDefecto(cantidad);
        }
        
        System.out.println("🔍 Intentando obtener " + cantidad + " elementos del juego desde la BD...");
        
        // Intentar primero con la tabla palabras_imagenes
        String[] consultasSQL = {
            "SELECT palabra_kichwa as palabra, ruta_imagen FROM palabras_imagenes ORDER BY RAND() LIMIT ?",
            "SELECT texto_origen as palabra, recurso_origen_url as ruta_imagen FROM emparejar_opciones WHERE id_actividad = 3 ORDER BY RAND() LIMIT ?",
            "SELECT palabra_kichwa as palabra, ruta_imagen FROM recursos WHERE tipo = 'imagen' ORDER BY RAND() LIMIT ?"
        };
        
        for (String sql : consultasSQL) {
            try {
                System.out.println("🔍 Probando consulta: " + sql.substring(0, Math.min(50, sql.length())) + "...");
                
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, cantidad);
                ResultSet rs = ps.executeQuery();
                
                while (rs.next()) {
                    String palabra = rs.getString("palabra");
                    String rutaImagen = rs.getString("ruta_imagen");
                    
                    if (palabra != null && !palabra.trim().isEmpty()) {
                        lista.add(new Juego(palabra.trim(), rutaImagen != null ? rutaImagen.trim() : ""));
                        System.out.println("✅ Cargado: " + palabra + " -> " + rutaImagen);
                    }
                }
                
                rs.close();
                ps.close();
                
                if (!lista.isEmpty()) {
                    System.out.println("✅ Se obtuvieron " + lista.size() + " elementos de la base de datos");
                    
                    // Si tenemos menos elementos de los solicitados, completar con datos por defecto
                    if (lista.size() < cantidad) {
                        System.out.println("⚠️ Completando con datos por defecto. Necesarios: " + cantidad + ", Obtenidos: " + lista.size());
                        List<Juego> datosDefecto = crearDatosPorDefecto(cantidad - lista.size());
                        lista.addAll(datosDefecto);
                    }
                    
                    return lista;
                }
                
            } catch (SQLException e) {
                System.err.println("⚠️ Error con consulta SQL: " + e.getMessage());
                // Continuar con la siguiente consulta
            }
        }
        
        System.err.println("❌ No se pudieron obtener datos de ninguna tabla. Usando datos por defecto.");
        List<Juego> datosDefecto = crearDatosPorDefecto(cantidad);
        
        // Asegurar que siempre devolvamos exactamente la cantidad solicitada
        if (datosDefecto.size() < cantidad) {
            System.out.println("⚠️ Duplicando datos por defecto para alcanzar cantidad solicitada: " + cantidad);
            while (datosDefecto.size() < cantidad) {
                for (int i = 0; i < Math.min(8, cantidad - datosDefecto.size()); i++) {
                    Juego original = datosDefecto.get(i % 8);
                    datosDefecto.add(new Juego(original.getPalabra() + "*", original.getRutaImagen()));
                }
            }
        }
        
        return datosDefecto;
    }
    
    /**
     * Crea datos por defecto cuando no se puede acceder a la base de datos
     */
    private List<Juego> crearDatosPorDefecto(int cantidad) {
        List<Juego> lista = new ArrayList<>();
        
        System.out.println("🔧 Creando datos por defecto para el juego...");
        
        // Datos de ejemplo para el juego
        String[][] datosDefecto = {
            {"Ñuka", "Imagenes/ImagenesUnidad1/yo.jpg"},
            {"Kan", "Imagenes/ImagenesUnidad1/tu.jpg"},
            {"Pay", "Imagenes/ImagenesUnidad1/el.jpg"},
            {"Mama", "Imagenes/ImagenesUnidad1/mama.jpg"},
            {"Tayta", "Imagenes/ImagenesUnidad1/papa.jpg"},
            {"Wawa", "Imagenes/ImagenesUnidad1/bebe.jpg"},
            {"Allku", "Imagenes/ImagenesUnidad1/perro.jpg"},
            {"Misi", "Imagenes/ImagenesUnidad1/gato.jpg"}
        };
        
        for (int i = 0; i < Math.min(cantidad, datosDefecto.length); i++) {
            lista.add(new Juego(datosDefecto[i][0], datosDefecto[i][1]));
            System.out.println("✅ Dato por defecto: " + datosDefecto[i][0] + " -> " + datosDefecto[i][1]);
        }
        
        return lista;
    }
}

