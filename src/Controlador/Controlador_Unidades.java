package Controlador;

import ControladoresU2.ControladorUnidad2;
import Vista.Estudiante.Vista_PanelUnidades;
import Vista.Estudiante.Dashboard;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Modelo.Modelo_Unidades;
import Vista.Vista_Unidad2;
import VistasUnidad3.Vista_Unidad3;
import VistasUnidad4.Vista_Unidad4;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.sql.Connection;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.UIManager;

public class Controlador_Unidades {

    private static final int TOTAL_UNIDADES = 4;
    private final Vista_PanelUnidades vista;
    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;
    private boolean[] unidadesDisponibles;

    public Controlador_Unidades(Vista_PanelUnidades vista, Dashboard dashboard,
            ControladorDashboard controladorDashboard, String correo) {

        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser null");
        }
        if (controladorDashboard == null) {
            throw new IllegalArgumentException("El controlador dashboard no puede ser null");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser null o vacío");
        }

        this.vista = vista;
        this.dashboard = dashboard;
        this.controladorDashboard = controladorDashboard;
        this.correo = correo;
        this.unidadesDisponibles = new boolean[TOTAL_UNIDADES + 1];

        // Listener para actualizar al mostrar la vista
        vista.addHierarchyListener(new HierarchyListener() {
            @Override
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                    if (vista.isShowing()) {
                        actualizarVistaCompleta();
                    }
                }
            }
        });

        inicializar();
    }

    private void inicializar() {
        try {
            cargarUnidades();
            agregarEventos();
        } catch (Exception e) {
            System.err.println("Error al inicializar controlador de unidades: " + e.getMessage());
            mostrarError("Error al inicializar el panel de unidades");
        }
    }

    /**
     * Actualiza toda la vista incluyendo el progreso
     */
    public void actualizarVistaCompleta() {
        try {
            System.out.println("Actualizando vista completa del panel de unidades...");
            cargarUnidades();
            sincronizarBarrasDirectamente();
            int progresoGeneral = obtenerProgresoGeneral();
            actualizarProgresoGeneral(progresoGeneral);
            System.out.println("Vista completa actualizada exitosamente");
        } catch (Exception e) {
            System.err.println("Error al actualizar vista completa: " + e.getMessage());
            mostrarError("Error al actualizar el progreso. Por favor, intenta de nuevo.");
        }
    }

    private void cargarUnidades() {
        try {
            unidadesDisponibles = Modelo_Unidades.obtenerDisponibilidadUnidades(correo);
            List<Modelo_Unidades> unidades = Modelo_Unidades.obtenerUnidadesConProgreso(correo);

            for (Modelo_Unidades unidad : unidades) {
                actualizarVistaUnidad(unidad);
            }

            sincronizarBarrasProgreso();
        } catch (Exception e) {
            System.err.println("Error al cargar unidades: " + e.getMessage());
            mostrarError("Error al cargar las unidades. Por favor, intenta de nuevo.");
        }
    }

    private void actualizarVistaUnidad(Modelo_Unidades unidad) {
        try {
            int idUnidad = unidad.getIdUnidad();
            if (idUnidad < 1 || idUnidad > TOTAL_UNIDADES) {
                System.err.println("ID de unidad inválido: " + idUnidad);
                return;
            }

            boolean disponible = unidadesDisponibles[idUnidad];
            JButton btnUnidad = obtenerBotonUnidad(idUnidad);
            JProgressBar progresoUnidades = obtenerJProgressProgreso(idUnidad);

            if (btnUnidad != null && progresoUnidades != null) {
                unidad.setDisponible(disponible);
                String colorEstado = unidad.getColorEstado();
                int progresoUnidad = CalculadorProgreso.calcularProgreso(
                        unidad.getProgresoLecciones(),
                        unidad.getProgresoActividades(),
                        unidad.isEvaluacionAprobada());

                progresoUnidades.setValue(progresoUnidad);
                progresoUnidades.setString(progresoUnidad + "%");
                progresoUnidades.setStringPainted(true);
                progresoUnidades.setForeground(Color.decode(colorEstado));

                btnUnidad.setForeground(Color.decode(colorEstado));
                btnUnidad.setCursor(disponible ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR));
                btnUnidad.setEnabled(disponible);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar vista de unidad " + unidad.getIdUnidad() + ": " + e.getMessage());
        }
    }

    private void actualizarProgresoGeneral(int progresoGeneral) {
        try {
            if (vista.ProgresoTotal != null) {
                vista.ProgresoTotal.setValue(progresoGeneral);
                vista.ProgresoTotal.setString(progresoGeneral + "%");
                vista.ProgresoTotal.setStringPainted(true);
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso general: " + e.getMessage());
        }
    }

    private JButton obtenerBotonUnidad(int idUnidad) {
        switch (idUnidad) {
            case 1: return vista.btnUnidad1;
            case 2: return vista.btnUnidad2;
            case 3: return vista.btnUnidad3;
            case 4: return vista.btnUnidad4;
            default: return null;
        }
    }

    private JProgressBar obtenerJProgressProgreso(int idUnidad) {
        switch (idUnidad) {
            case 1: return vista.ProgresoU1;
            case 2: return vista.ProgresoU2;
            case 3: return vista.ProgresoU3;
            case 4: return vista.ProgresoU4;
            default: return null;
        }
    }

    private void agregarEventos() {
        configurarEventosUnidad(vista.btnUnidad1, 1, "Saludos y Presentaciones");
        configurarEventosUnidad(vista.btnUnidad2, 2, "Familia y Hogar");
        configurarEventosUnidad(vista.btnUnidad3, 3, "Naturaleza y Animales");
        configurarEventosUnidad(vista.btnUnidad4, 4, "Números y Colores");
    }

    private void configurarEventosUnidad(JButton botonUnidad, int idUnidad, String nombreUnidad) {
        if (botonUnidad == null) return;

        botonUnidad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (idUnidad < unidadesDisponibles.length && unidadesDisponibles[idUnidad]) {
                    botonUnidad.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botonUnidad.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                abrirUnidad(idUnidad, nombreUnidad);
            }
        });
    }

    private void abrirUnidad(int idUnidad, String nombreUnidad) {
        try {
            Modelo_Unidades.registrarVisualizacionUnidad(idUnidad, correo);

            configurarEstiloMensaje();
            mostrarMensajeBienvenida(nombreUnidad);
            restaurarEstiloDefecto();

            switch (idUnidad) {
                case 1:
                    abrirUnidad1();
                    break;
                case 2:
                    abrirUnidad2();
                    break;
                case 3:
                    abrirUnidad3();
                    break;
                case 4:
                    abrirUnidad4();
                    break;
                default:
                    System.err.println("ID de unidad no válido: " + idUnidad);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error al abrir unidad " + idUnidad + ": " + e.getMessage());
            mostrarError("Error al abrir la unidad. Por favor, intenta de nuevo.");
        }
    }

    private void abrirUnidad1() {
        try {
            System.out.println("[DEBUG] Iniciando apertura de Unidad 1");
            
            // 1. Crear la vista de la Unidad 1
            Vista_Unidad1 unidad1 = new Vista_Unidad1();
            if (unidad1 == null) {
                throw new RuntimeException("No se pudo crear la vista de la Unidad 1");
            }
            
            // 2. Obtener la conexión
            Connection conn = (Connection) controladorDashboard.getConnection();
            if (conn == null) {
                throw new RuntimeException("No se pudo obtener la conexión a la base de datos");
            }
            
            System.out.println("[DEBUG] Vista y conexión creadas exitosamente");
            
            // 3. IMPORTANTE: Crear el controlador ANTES de mostrar la vista
            // Esto asegura que la vista esté completamente inicializada
            Controlador_Unidad1 controlador = new Controlador_Unidad1(
                    unidad1,
                    conn,
                    controladorDashboard,
                    correo,
                    this
            );
            
            if (controlador == null) {
                throw new RuntimeException("No se pudo crear el controlador de la Unidad 1");
            }
            
            System.out.println("[DEBUG] Controlador creado exitosamente");
            
            // 4. Ahora mostrar la vista ya inicializada
            if (dashboard != null) {
                dashboard.mostrarVista(unidad1);
                System.out.println("[DEBUG] Vista mostrada en dashboard");
                
                // 5. Forzar actualización de la interfaz
                unidad1.revalidate();
                unidad1.repaint();
                
                System.out.println("[DEBUG] Unidad 1 abierta exitosamente");
            } else {
                throw new RuntimeException("Dashboard es null, no se puede mostrar la vista");
            }
            
        } catch (Exception e) {
            System.err.println("[ERROR] Error al abrir unidad 1: " + e.getMessage());
            e.printStackTrace();
            mostrarError("Error al abrir la unidad 1. Por favor, intenta de nuevo.\nDetalles: " + e.getMessage());
            
            // Intentar regresar al dashboard principal en caso de error
            if (dashboard != null) {
                try {
                    dashboard.mostrarVista(controladorDashboard.getPanelUnidades());
                } catch (Exception ex) {
                    System.err.println("[ERROR] No se pudo regresar al panel de unidades: " + ex.getMessage());
                }
            }
        }
    }

private void abrirUnidad2() {
    try {
        Vista_Unidad2 unidad2 = new Vista_Unidad2();
        Connection conn = (Connection) controladorDashboard.getConnection();

        // Crear controlador y pasar referencias
        ControladorUnidad2 controladorUnidad2 = new ControladorUnidad2(
            unidad2,
            conn,
            controladorDashboard,
            correo,
            this
        );

        // Calcular y enviar el progreso
        List<Modelo_Unidades> unidades = Modelo_Unidades.obtenerUnidadesConProgreso(correo);
        for (Modelo_Unidades unidad : unidades) {
            if (unidad.getIdUnidad() == 2) {
                int progreso = CalculadorProgreso.calcularProgreso(
                    unidad.getProgresoLecciones(),
                    unidad.getProgresoActividades(),
                    unidad.isEvaluacionAprobada()
                );
                controladorUnidad2.actualizarBarraProgresoUnidad(progreso);
                break;
            }
        }

        dashboard.mostrarVista(unidad2);
    } catch (Exception e) {
        System.err.println("Error al abrir unidad 2: " + e.getMessage());
        mostrarError("Error al abrir la unidad 2. Por favor, intenta de nuevo.");
    }
}



    private void abrirUnidad3() {
        try {
            Vista_Unidad3 unidad3 = new Vista_Unidad3();
            Connection conn = (Connection) controladorDashboard.getConnection();

            new Controlador_Unidad3(
                    unidad3,
                    conn,
                    controladorDashboard,
                    correo,
                    this
            );

            dashboard.mostrarVista(unidad3);
        } catch (Exception e) {
            System.err.println("Error al abrir unidad 3: " + e.getMessage());
            mostrarError("Error al abrir la unidad 3. Por favor, intenta de nuevo.");
        }
    }

    private void abrirUnidad4() {
        try {
            Vista_Unidad4 unidad4 = new Vista_Unidad4();
            Connection conn = (Connection) controladorDashboard.getConnection();

            new Controlador_Unidad4(
                    unidad4,
                    conn,
                    controladorDashboard,
                    correo,
                    this
            );

            dashboard.mostrarVista(unidad4);
        } catch (Exception e) {
            System.err.println("Error al abrir unidad 4: " + e.getMessage());
            mostrarError("Error al abrir la unidad 4. Por favor, intenta de nuevo.");
        }
    }

    private void mostrarMensajeBienvenida(String nombreUnidad) {
        String mensaje = "¡Bienvenido a la unidad: " + nombreUnidad + "!\n\n"
                + "Aquí aprenderás nuevas palabras y conceptos.\n"
                + "¡Diviértete aprendiendo!";

        JOptionPane.showMessageDialog(vista, mensaje,
                "Bienvenido",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void configurarEstiloMensaje() {
        try {
            UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 14));
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 12));
            UIManager.put("OptionPane.messageForeground", new Color(51, 51, 51));
        } catch (Exception e) {
            System.err.println("Error al configurar estilo de mensaje: " + e.getMessage());
        }
    }

    private void restaurarEstiloDefecto() {
        try {
            UIManager.put("OptionPane.messageFont", null);
            UIManager.put("OptionPane.buttonFont", null);
            UIManager.put("OptionPane.messageForeground", null);
        } catch (Exception e) {
            System.err.println("Error al restaurar estilo por defecto: " + e.getMessage());
        }
    }

    private int obtenerProgresoGeneral() {
        try {
            List<Modelo_Unidades> unidades = Modelo_Unidades.obtenerUnidadesConProgreso(correo);
            int[] progresosUnidades = new int[TOTAL_UNIDADES];
            
            for (Modelo_Unidades unidad : unidades) {
                int idUnidad = unidad.getIdUnidad();
                if (idUnidad >= 1 && idUnidad <= TOTAL_UNIDADES) {
                    progresosUnidades[idUnidad - 1] = CalculadorProgreso.calcularProgreso(
                        unidad.getProgresoLecciones(),
                        unidad.getProgresoActividades(),
                        unidad.isEvaluacionAprobada()
                    );
                }
            }
            
            return CalculadorProgreso.calcularProgresoTotal(progresosUnidades);
        } catch (Exception e) {
            System.err.println("Error al obtener progreso general: " + e.getMessage());
            return 0;
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public void actualizarProgreso() {
        try {
            cargarUnidades();
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso: " + e.getMessage());
        }
    }

    public void actualizarVista() {
        actualizarVistaCompleta();
    }

    public void actualizarProgresoUnidad(int idUnidad, int progreso) {
        try {
            JProgressBar barraUnidad = obtenerJProgressProgreso(idUnidad);
            if (barraUnidad != null) {
                barraUnidad.setValue(progreso);
                barraUnidad.setString(progreso + "%");
                barraUnidad.setStringPainted(true);
            }
            
            int progresoGeneral = obtenerProgresoGeneral();
            actualizarProgresoGeneral(progresoGeneral);
        } catch (Exception e) {
            System.err.println("Error al actualizar progreso de unidad " + idUnidad + ": " + e.getMessage());
        }
    }

    public void sincronizarBarrasProgreso() {
        try {
            sincronizarBarrasDirectamente();
        } catch (Exception e) {
            System.err.println("Error al sincronizar barras de progreso: " + e.getMessage());
        }
    }

    public void sincronizarBarrasDirectamente() {
        try {
            sincronizarBarraIndividual(1);
            sincronizarBarraIndividual(2);
            sincronizarBarraIndividual(3);
            sincronizarBarraIndividual(4);
            
            int progresoGeneral = obtenerProgresoGeneral();
            actualizarProgresoGeneral(progresoGeneral);
        } catch (Exception e) {
            System.err.println("Error en sincronización directa: " + e.getMessage());
        }
    }

    private void sincronizarBarraIndividual(int idUnidad) {
        try {
            int valorActual = obtenerValorBarraIndividual(idUnidad);
            if (valorActual >= 0) {
                actualizarProgresoUnidad(idUnidad, valorActual);
            }
        } catch (Exception e) {
            System.err.println("Error al sincronizar barra individual " + idUnidad + ": " + e.getMessage());
        }
    }

    private int obtenerValorBarraIndividual(int idUnidad) {
        try {
            int valorBarra = obtenerValorBarraDesdeVista(idUnidad);
            if (valorBarra >= 0) {
                return valorBarra;
            }
            
            List<Modelo_Unidades> unidades = Modelo_Unidades.obtenerUnidadesConProgreso(correo);
            for (Modelo_Unidades unidad : unidades) {
                if (unidad.getIdUnidad() == idUnidad) {
                    return CalculadorProgreso.calcularProgreso(
                        unidad.getProgresoLecciones(),
                        unidad.getProgresoActividades(),
                        unidad.isEvaluacionAprobada()
                    );
                }
            }
        } catch (Exception e) {
            System.err.println("Error al obtener valor de barra individual " + idUnidad + ": " + e.getMessage());
        }
        return -1;
    }

    private int obtenerValorBarraDesdeVista(int idUnidad) {
        try {
            JProgressBar barraPanel = obtenerJProgressProgreso(idUnidad);
            if (barraPanel != null) {
                return barraPanel.getValue();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener valor desde vista " + idUnidad + ": " + e.getMessage());
        }
        return -1;
    }

    private boolean verificarDisponibilidadUnidad(int idUnidad) {
        try {
            return Modelo_Unidades.verificarDisponibilidadUnidad(idUnidad, correo);
        } catch (Exception e) {
            System.err.println("Error al verificar disponibilidad de unidad: " + e.getMessage());
            return false;
        }
    }

    public Vista_PanelUnidades getVista() {
        return vista;
    }

    public String getCorreo() {
        return correo;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public ControladorDashboard getControladorDashboard() {
        return controladorDashboard;
    }

    public boolean isUnidadDisponible(int idUnidad) {
        if (idUnidad < 1 || idUnidad >= unidadesDisponibles.length) {
            return false;
        }
        return unidadesDisponibles[idUnidad];
    }

    public void recargarUnidades() {
        try {
            cargarUnidades();
        } catch (Exception e) {
            System.err.println("Error al recargar unidades: " + e.getMessage());
            mostrarError("Error al recargar las unidades");
        }
    }
    
    public void forzarSincronizacionCompleta() {
        try {
            sincronizarBarrasDirectamente();
        } catch (Exception e) {
            System.err.println("Error en sincronización completa: " + e.getMessage());
        }
    }
    // En la clase Controlador_Unidades
public void marcarUnidadComoCompletada(int idUnidad) {
    try {
        // 1. Actualizar en la base de datos
        Modelo_Unidades.marcarUnidadComoCompletada(idUnidad, this.correo);
        
        // 2. Actualizar la barra de progreso al 100%
        JProgressBar barraProgreso = obtenerJProgressProgreso(idUnidad);
        if (barraProgreso != null) {
            barraProgreso.setValue(100);
            barraProgreso.setString("100%");
            barraProgreso.setForeground(Color.GREEN);
        }
        
        // 3. Habilitar la siguiente unidad si existe
        if (idUnidad < TOTAL_UNIDADES) {
            unidadesDisponibles[idUnidad + 1] = true;
            JButton btnSiguienteUnidad = obtenerBotonUnidad(idUnidad + 1);
            if (btnSiguienteUnidad != null) {
                btnSiguienteUnidad.setEnabled(true);
                btnSiguienteUnidad.setForeground(new Color(0, 153, 51)); // Verde oscuro
            }
        }
        
        // 4. Actualizar progreso general
        int progresoGeneral = obtenerProgresoGeneral();
        actualizarProgresoGeneral(progresoGeneral);
        
        System.out.println("Unidad " + idUnidad + " marcada como completada para " + this.correo);
    } catch (Exception e) {
        System.err.println("Error al marcar unidad como completada: " + e.getMessage());
        mostrarError("Error al guardar el progreso. Intente nuevamente.");
    }
}
}