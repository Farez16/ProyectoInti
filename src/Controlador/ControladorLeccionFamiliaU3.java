/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Controlador_LeccionNumerosU2.java
package Controlador;

import Modelo.Modelo_Progreso_Usuario;
import Modelo.Modelo_Unidades;
import Modelo.Usuario;
import Vista.*;
import Vista.Estudiante.Dashboard;
import VistasUnidad3.Vista_Actividad_Familia;
import VistasUnidad3.Vista_Leccion_Familia;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class ControladorLeccionFamiliaU3 {

    private final Vista_Leccion_Familia vista;
    private final Dashboard dashboard;
    private final int idUnidad = 3;
    private final int idUsuario;
    private final String correo;

    public ControladorLeccionFamiliaU3(Vista_Leccion_Familia vista, Dashboard dashboard) {
        this.vista = vista;
        this.dashboard = dashboard;

        vista.getCompletarLeccion().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirVistaActividad();
            }
        });
        
        this.idUsuario = Usuario.obtenerIdPorCorreo(this.correo);
    }

    private void abrirVistaActividad() {
        Vista_Actividad_Familia actividad = new Vista_Actividad_Familia();
        new Controlador_FamiliaUnidad3(actividad, dashboard);
        dashboard.mostrarVista(actividad);
    }
    private void completarLeccion() {
        try {

            // Obtener o crear progreso para el usuario
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, idUnidad);

            // Verificar si esta lección ya fue completada
            if (progreso.getLeccionesCompletadas() >= numeroLeccion) {
                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "Ya has completado esta lección anteriormente. El progreso no se incrementará nuevamente.",
                        "Lección ya completada",
                        JOptionPane.INFORMATION_MESSAGE
                );
                
                // Mostrar mensaje de éxito pero no actualizar progreso
                mostrarMensajeExito();
                liberarRecursos();
                actualizarInterfaz();
                return;
            }

            // Actualizar progreso
            boolean actualizado = ControladorProgresoUsuario.actualizarLeccion(progreso, numeroLeccion);

            if (actualizado || progreso.getLeccionesCompletadas() >= numeroLeccion) {
                mostrarMensajeExito();
                liberarRecursos();
                actualizarInterfaz();
            } else {
                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "Error al actualizar el progreso",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (Exception e) {
            System.err.println("Error al completar lección: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    vistaLeccion,
                    "Error inesperado al completar la lección: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

}
