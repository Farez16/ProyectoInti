package Modelo;

public class Saludo {
private String mensaje;

    public Saludo(String mensajeInicial) {
        this.mensaje = mensajeInicial;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}