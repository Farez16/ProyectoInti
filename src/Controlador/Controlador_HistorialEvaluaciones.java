package Controlador;

import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vista_Historial_Evaluaciones;
import java.sql.Connection;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Controlador_HistorialEvaluaciones {

    private final Vista_Historial_Evaluaciones vista;
    private final String correo;
    private final Connection conn;

    public Controlador_HistorialEvaluaciones(Vista_Historial_Evaluaciones vista, Connection conn, String correo) {
        this.vista = vista;
        this.conn = conn;
        this.correo = correo;

        cargarHistorial();
    }

    private void cargarHistorial() {
        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        List<Modelo_Progreso_Usuario> historial = Modelo_Progreso_Usuario.obtenerHistorialPorUsuario(idUsuario);

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"Unidad", "Calificación", "Aprobada", "Fecha"});

        for (Modelo_Progreso_Usuario progreso : historial) {
            modelo.addRow(new Object[]{
                progreso.getNombreUnidad(),
                progreso.getCalificacion(),
                progreso.isEvaluacionAprobada() ? "Sí" : "No",
                progreso.getFechaActualizacion()
            });
        }

        vista.getTablaHistorial().setModel(modelo);
    }
}
