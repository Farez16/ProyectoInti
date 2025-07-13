package Controlador;

import Modelo.MostrarDatosAdmin;
import Vista.VistaMostrarAdmin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorMostrar {
    private VistaMostrarAdmin vista;
    private MostrarDatosAdmin modelo;

    public ControladorMostrar(VistaMostrarAdmin vista, MostrarDatosAdmin modelo) {
        this.vista = vista;
        this.modelo = modelo;
        
        // Mostrar todos los usuarios al iniciar
        cargarUsuariosEnTabla("");
            this.vista.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String correo = vista.TxtBuscar.getText().trim();
                cargarUsuariosEnTabla(correo);
            }
        });
    }

   public void cargarUsuariosEnTabla(String correo) {
    try {
        ResultSet rs;
        
        if (correo.isEmpty()) {
            // Obtener solo usuarios con rol 2
            rs = modelo.obtenerUsuariosRol1();
        } else {
            // Buscar por correo y que tenga rol 2
            rs = modelo.buscarUsuariosRol1PorCorreo(correo);
        }
        
        String[] columnas = {"Nombre", "ID", "Correo", "Registro"};
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);
        vista.getTablaDatos().setModel(modeloTabla);

        while (rs != null && rs.next()) {
            Object[] fila = {
                rs.getString("nombre"),
                rs.getString("id_usuario"),
                rs.getString("correo"),
                rs.getString("fecha_registro")
            };
            modeloTabla.addRow(fila);
        }
        
        if (rs != null) {
            rs.close();
        }
    } catch (SQLException e) {
        System.out.println("Error al llenar tabla: " + e.getMessage());
        e.printStackTrace();
    }
}
}

