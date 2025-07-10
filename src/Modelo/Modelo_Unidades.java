package Modelo;
public class Modelo_Unidades {
    private int idUnidad;
    private String nombreUnidad;
    private String descripcion;

    // Constructor
    public Modelo_Unidades(int idUnidad, String nombreUnidad, String descripcion) {
        this.idUnidad = idUnidad;
        this.nombreUnidad = nombreUnidad;
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return nombreUnidad; // util para mostrar en JComboBox o JList
    }
}