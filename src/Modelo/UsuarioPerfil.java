package Modelo;

import java.sql.Timestamp;

public class UsuarioPerfil {
    private String correo;
    private String rutaImagen;
    private Timestamp fechaRegistro;
    private Timestamp ultimaSesion;

    public UsuarioPerfil(String correo, String rutaImagen, Timestamp fechaRegistro, Timestamp ultimaSesion) {
        this.correo = correo;
        this.rutaImagen = rutaImagen;
        this.fechaRegistro = fechaRegistro;
        this.ultimaSesion = ultimaSesion;
    }

    public String getCorreo() { return correo; }
    public String getRutaImagen() { return rutaImagen; }
    public Timestamp getFechaRegistro() { return fechaRegistro; }
    public Timestamp getUltimaSesion() { return ultimaSesion; }

    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public void setUltimaSesion(Timestamp ultimaSesion) { this.ultimaSesion = ultimaSesion; }
}
