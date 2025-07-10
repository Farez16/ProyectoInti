package Controlador;

import Modelo.Usuario;
import Vista.RegistrarAdmin;

public class ControladorRegistrarAdmin {
    private RegistrarAdmin vista;
    private Usuario modelo;

    public ControladorRegistrarAdmin(RegistrarAdmin vista, Usuario modelo) {
        this.vista = vista;
        this.modelo = modelo;
    }
        
}
