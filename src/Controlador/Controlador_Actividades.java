package Controlador;

import Modelo.*;
import Vista.Vista_Actividad2U1;
import Vista.Vista_Unidad1;
import java.awt.CardLayout;
import java.sql.Connection;
import java.util.List;

public class Controlador_Actividades {

    private final javax.swing.JPanel vista;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int idActividad;
    private Modelo_Actividades actividad;
    private final Controlador_Unidad1 controladorUnidad1;

    private List<Modelo_DragDrop.DragDropItem> itemsDragDrop;
    private List<Modelo_Emparejar.EmparejarItem> itemsEmparejar;

    private boolean dragDropCompletado = false;
    private boolean emparejarCompletado = false;

    public Controlador_Actividades(javax.swing.JPanel vista, ControladorDashboard controladorDashboard,
                                   Connection conn, String correo, int idActividad, Controlador_Unidad1 controladorUnidad1) {
        this.vista = vista;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;
        this.correo = correo.trim();
        this.idActividad = idActividad;
        this.controladorUnidad1 = controladorUnidad1;

        agregarEventos();
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

        dragDropCompletado = false;
        emparejarCompletado = false;

        mostrarSubPanel("Emparejar");
        cargarDatosEmparejar(act2);
        cargarDatosDragDrop(act2); // preparamos también dragdrop para que esté listo

        act2.jLabelPregunta.setText(actividad.getPregunta());
        act2.jLabelPista.setText("");
        act2.jButtonCOMPLETOACTV2.setEnabled(false);
    }

    private void mostrarSubPanel(String nombrePanel) {
        if (!(vista instanceof Vista_Actividad2U1 act2)) return;

        CardLayout cl = (CardLayout) act2.jPanelContenedor.getLayout();
        cl.show(act2.jPanelContenedor, nombrePanel);
    }

    private void agregarEventos() {
        if (!(vista instanceof Vista_Actividad2U1 act2)) return;

        act2.jButtonValidarEmparejar.addActionListener(e -> validarEmparejar(act2));
        act2.jButtonValidarDragDrop.addActionListener(e -> validarDragDrop(act2));

        int idUsuario = Usuario.obtenerIdPorCorreo(correo);
        act2.jButtonCOMPLETOACTV2.addActionListener(e -> completarActividad(idUsuario));
    }

    private void cargarDatosDragDrop(Vista_Actividad2U1 act2) {
        Modelo_DragDrop modeloDragDrop = new Modelo_DragDrop(conn);
        itemsDragDrop = modeloDragDrop.obtenerItemsPorActividad(idActividad);

        if (itemsDragDrop.size() >= 3) {
            act2.jLabelDragDropOpcionA.setText(itemsDragDrop.get(0).texto);
            act2.jLabelDragDropOpcionB.setText(itemsDragDrop.get(1).texto);
            act2.jLabelDragDropOpcionC.setText(itemsDragDrop.get(2).texto);
              act2.jLabelPregunta.setText(actividad.getPregunta());  // <-- refresca la pregunta
        } else {
            act2.jLabelPista.setText("No hay suficientes ítems de drag drop.");
        }
    }

    private void validarDragDrop(Vista_Actividad2U1 act2) {
    String textoDestino = act2.jLabelDestino.getText().trim();

    boolean esCorrecto = false;
    for (Modelo_DragDrop.DragDropItem item : itemsDragDrop) {
        if (item.texto.equals(textoDestino) && item.posicionDestino.equalsIgnoreCase("mañana")) {
            esCorrecto = true;
            break;
        }
    }

    if (esCorrecto) {
        act2.jLabelPista.setText("¡Correcto!");
        dragDropCompletado = true;
    } else {
        act2.jLabelPista.setText("Incorrecto, intenta otra vez.");
    }

    actualizarEstadoBotonCompleto(act2);
}


    private void cargarDatosEmparejar(Vista_Actividad2U1 act2) {
        Modelo_Emparejar modeloEmparejar = new Modelo_Emparejar(conn);
        itemsEmparejar = modeloEmparejar.obtenerOpcionesPorActividad(idActividad);

        if (itemsEmparejar.size() < 3) {
            act2.jLabelPista.setText("No hay suficientes ítems de emparejar.");
            return;
        }

        act2.jLabelEmaprejar1.setText(itemsEmparejar.get(0).textoOrigen);
        act2.jLabelEmaprejar2.setText(itemsEmparejar.get(1).textoOrigen);
        act2.jLabelEmaprejar3.setText(itemsEmparejar.get(2).textoOrigen);

        act2.jComboBoxEmparejarOpcionA.removeAllItems();
        act2.jComboBoxEmparejarOpcionB.removeAllItems();
        act2.jComboBoxEmparejarOpcionC.removeAllItems();

        for (Modelo_Emparejar.EmparejarItem item : itemsEmparejar) {
            act2.jComboBoxEmparejarOpcionA.addItem(item.textoDestino);
            act2.jComboBoxEmparejarOpcionB.addItem(item.textoDestino);
            act2.jComboBoxEmparejarOpcionC.addItem(item.textoDestino);
        }
    }

    private void validarEmparejar(Vista_Actividad2U1 act2) {
    String palabraA = act2.jLabelEmaprejar1.getText();
    String palabraB = act2.jLabelEmaprejar2.getText();
    String palabraC = act2.jLabelEmaprejar3.getText();

    String respuestaA = (String) act2.jComboBoxEmparejarOpcionA.getSelectedItem();
    String respuestaB = (String) act2.jComboBoxEmparejarOpcionB.getSelectedItem();
    String respuestaC = (String) act2.jComboBoxEmparejarOpcionC.getSelectedItem();

    if (respuestaA == null || respuestaB == null || respuestaC == null) {
        act2.jLabelPista.setText("Selecciona todas las opciones.");
        return;
    }

    boolean correctoA = esEmparejamientoCorrecto(palabraA, respuestaA);
    boolean correctoB = esEmparejamientoCorrecto(palabraB, respuestaB);
    boolean correctoC = esEmparejamientoCorrecto(palabraC, respuestaC);

    if (correctoA && correctoB && correctoC) {
        emparejarCompletado = true;
        act2.jLabelPista.setText("¡Correcto en Emparejar! Ahora completa el DragDrop.");

        // Cargar los datos para el DragDrop
        cargarDatosDragDrop(act2);
        // Cambiar de subpanel
        mostrarSubPanel("DragDrop");

    } else {
        emparejarCompletado = false;
        act2.jLabelPista.setText("Incorrecto en emparejar, revisa tus selecciones.");
    }
}


    private boolean esEmparejamientoCorrecto(String textoOrigen, String textoDestino) {
        for (Modelo_Emparejar.EmparejarItem item : itemsEmparejar) {
            if (item.textoOrigen.equals(textoOrigen) && item.textoDestino.equals(textoDestino)) {
                return true;
            }
        }
        return false;
    }

    private void actualizarEstadoBotonCompleto(Vista_Actividad2U1 act2) {
        if (emparejarCompletado && dragDropCompletado) {
            act2.jButtonCOMPLETOACTV2.setEnabled(true);
            act2.jLabelPista.setText("¡Has completado todas las actividades!");
        } else {
            act2.jButtonCOMPLETOACTV2.setEnabled(false);
        }
    }

    private void completarActividad(int idUsuario) {
        Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, actividad.getIdUnidad());
        boolean actualizado = ControladorProgresoUsuario.actualizarActividad(progreso, idActividad);
        if (actualizado) {
            System.out.println("Actividad " + idActividad + " completada");
        }

        Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
        new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo,
                controladorUnidad1 != null ? controladorUnidad1.getControladorUnidades() : null);

        controladorDashboard.getVista().mostrarVista(vistaUnidad1);
    }
}
