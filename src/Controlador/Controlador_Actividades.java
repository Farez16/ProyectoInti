package Controlador;

import Modelo.Modelo_Actividades;
import Modelo.Modelo_Progreso_Usuario;
import Modelo.Usuario;
import Vista.Vista_Actividad2U1;
import Vista.Vista_Unidad1;

import java.awt.event.ActionListener;
import java.sql.Connection;

public class Controlador_Actividades {

    private final javax.swing.JPanel vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idActividad;
    private Modelo_Actividades actividad;
    private final Controlador_Unidad1 controladorUnidad1;

    public Controlador_Actividades(javax.swing.JPanel vista, ControladorDashboard controladorDashboard,
                                  Connection conn, String correo, int idActividad, Controlador_Unidad1 controladorUnidad1) {
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }
        this.correo = correo.trim();

        this.idActividad = idActividad;
        this.controladorUnidad1 = controladorUnidad1;

        agregarEventos();
    }

    public Controlador_Actividades(javax.swing.JPanel vista, ControladorDashboard controladorDashboard,
                                  Connection conn, String correo, int idActividad) {
        this(vista, controladorDashboard, conn, correo, idActividad, null);
    }

    public void cargarActividad() {
        actividad = Modelo_Actividades.obtenerPorId(conn, idActividad);

        if (actividad == null) {
            System.out.println("No se encontró la actividad con ID " + idActividad);
            return;
        }

        if (!(vista instanceof Vista_Actividad2U1 act2)) {
            System.out.println("Vista no es Vista_Actividad2U1");
            return;
        }

        // Aquí decides cuál panel mostrar.  
        // Por ejemplo: si actividad.id es par muestro emparejar, si es impar drag_drop (solo para ejemplo)
        if (idActividad % 2 == 0) {
            mostrarSubPanel(act2.jPanelEmparejar);
            cargarDatosEmparejar(act2);
        } else {
            mostrarSubPanel(act2.jPanelDragDrop);
            cargarDatosDragDrop(act2);
        }

        // Cargar pregunta
        act2.jLabelPregunta.setText(actividad.getPregunta());

        // Limpiar mensaje y desactivar botón completar
        act2.jLabelPista.setText("");
        act2.jButtonCOMPLETOACTV2.setEnabled(false);
    }

    private void mostrarSubPanel(javax.swing.JPanel panelAMostrar) {
        if (!(vista instanceof Vista_Actividad2U1 act2)) return;

        act2.jPanelEmparejar.setVisible(false);
        act2.jPanelDragDrop.setVisible(false);

        if (panelAMostrar != null) {
            panelAMostrar.setVisible(true);
        }
    }

    private void agregarEventos() {
        if (!(vista instanceof Vista_Actividad2U1 act2)) return;

        // Botón para validar emparejar
        act2.jButtonValidarEmparejar.addActionListener(e -> validarEmparejar(act2));

        // Botón para validar drag & drop
        act2.jButtonValidarDragDrop.addActionListener(e -> validarDragDrop(act2));

        // Botón para completar actividad
        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        act2.jButtonCOMPLETOACTV2.addActionListener(e -> completarActividad(idUsuario));
    }

    private void cargarDatosEmparejar(Vista_Actividad2U1 act2) {
        // Ejemplo: limpiar combos y agregar opciones simples
        act2.jComboBoxEmparejarOpcionA.removeAllItems();
        act2.jComboBoxEmparejarOpcionB.removeAllItems();
        act2.jComboBoxEmparejarOpcionC.removeAllItems();

        // Aquí deberías cargar desde modelo o BD tus datos reales
        act2.jComboBoxEmparejarOpcionA.addItem("Opción A1");
        act2.jComboBoxEmparejarOpcionA.addItem("Opción A2");
        act2.jComboBoxEmparejarOpcionB.addItem("Opción B1");
        act2.jComboBoxEmparejarOpcionB.addItem("Opción B2");
        act2.jComboBoxEmparejarOpcionC.addItem("Opción C1");
        act2.jComboBoxEmparejarOpcionC.addItem("Opción C2");
    }

    private void validarEmparejar(Vista_Actividad2U1 act2) {
        String selA = (String) act2.jComboBoxEmparejarOpcionA.getSelectedItem();
        String selB = (String) act2.jComboBoxEmparejarOpcionB.getSelectedItem();
        String selC = (String) act2.jComboBoxEmparejarOpcionC.getSelectedItem();

        if (selA == null || selB == null || selC == null) {
            act2.jLabelPista.setText("Selecciona todas las opciones.");
            act2.jButtonCOMPLETOACTV2.setEnabled(false);
            return;
        }

        // Aquí pones tu lógica real para validar, este es un ejemplo:
        boolean esCorrecto = selA.equals("Opción A1") && selB.equals("Opción B1") && selC.equals("Opción C1");

        if (esCorrecto) {
            act2.jLabelPista.setText("¡Correcto!");
            act2.jButtonCOMPLETOACTV2.setEnabled(true);
        } else {
            act2.jLabelPista.setText("Incorrecto, intenta otra vez.");
            act2.jButtonCOMPLETOACTV2.setEnabled(false);
        }
    }

    private void cargarDatosDragDrop(Vista_Actividad2U1 act2) {
        // Aquí debes cargar etiquetas o ítems para drag and drop, ejemplo:
        act2.jLabelDragDropOpcionA.setText("Drag A");
        act2.jLabelDragDropOpcionB.setText("Drag B");
        act2.jLabelDragDropOpcionC.setText("Drag C");

        // Resetea cualquier estado que necesites
    }

    private void validarDragDrop(Vista_Actividad2U1 act2) {
        // Implementa la validación real para drag and drop según tu lógica
        // Ejemplo rápido: asumimos que está correcto (haz la validación real tú)
        boolean esCorrecto = true;

        if (esCorrecto) {
            act2.jLabelPista.setText("¡Correcto!");
            act2.jButtonCOMPLETOACTV2.setEnabled(true);
        } else {
            act2.jLabelPista.setText("Incorrecto, intenta otra vez.");
            act2.jButtonCOMPLETOACTV2.setEnabled(false);
        }
    }

    private void completarActividad(int idUsuario) {
        Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, actividad.getIdUnidad());
        boolean actualizado = ControladorProgresoUsuario.actualizarActividad(progreso, idActividad);
        if (actualizado) {
            System.out.println("Actividad " + idActividad + " completada");
        }

        if (controladorUnidad1 != null) {
            controladorUnidad1.actualizarVista();

            Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
            new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo,
                    controladorUnidad1.getControladorUnidades());

            controladorDashboard.getVista().mostrarVista(vistaUnidad1);
        } else {
            Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
            controladorDashboard.getVista().mostrarVista(vistaUnidad1);
        }
    }
}
