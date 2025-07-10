package Controlador;

import Vista.*;
import Modelo.Usuario;
import Conexion.Conexion;

public class Controlador_Unidades {

    private final Vista_PanelUnidades vista;
    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;  // CAMBIADO: antes cedula

    public Controlador_Unidades(Vista_PanelUnidades vista, ControladorDashboard controladorDashboard, String correo) {
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.dashboard = controladorDashboard.getVista();
        this.correo = correo;  // GUARDAR correo directamente
        agregarEventos();
    }

    private void agregarEventos() {
        vista.jLabelUNIDAD1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Vista_Unidad1 unidad1 = new Vista_Unidad1();
                // PASAR correo, no cedula
                new Controlador_Unidad1(unidad1, Conexion.conectar(), controladorDashboard, correo);
                dashboard.mostrarVista(unidad1);
            }
        });

        // Si tienes más unidades, aplica lo mismo
    }
}


