package Controlador;

// Imports de Vista
import Vista.Estudiante.Dashboard;
import Vista.Vistas_Unidad1.Vista_EvaluacionU1;
import Vista.Vistas_Unidad1.Vista_Actividad1U1;
import Vista.Vistas_Unidad1.Vista_LeccionFONOLOGIA;
import Vista.Vistas_Unidad1.Vista_Actividad2U1;
import Vista.Vistas_Unidad1.Vista_Actividad3U1;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Vista.Vistas_Unidad1.Vista_LeccionPRONOMBRES;
import Vista.Vistas_Unidad1.Vista_LeccionSALUDOS;

// Imports de Modelo
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;

// Imports de Java
import java.sql.Connection;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color; // Importar Color para la barra de progreso

public class Controlador_Unidad1 {

    private final Vista_Unidad1 vista;
    private final Connection conn;
    private final int idUsuario;
    private final int ID_UNIDAD = 1;

    // Control para mostrar instrucciones solo una vez por sesión
    private static boolean instruccionesMostradas = false;
    
    // Controlador de lección activo para gestión de recursos de video
    private Controlador_Lecciones controladorLeccionActivo = null;

    private final ControladorDashboard controladorDashboard;
    private final Dashboard dashboard;
    private final String correo;
    private final Controlador_Unidades controladorUnidades;

    public Controlador_Unidad1(Vista_Unidad1 vista, Connection conn, ControladorDashboard controladorDashboard, String correo, Controlador_Unidades controladorUnidades) {
        // Validaciones más detalladas
        if (vista == null) {
            throw new IllegalArgumentException("La vista no puede ser null");
        }
        if (conn == null) {
            throw new IllegalArgumentException("La conexión no puede ser null");
        }
        if (controladorDashboard == null) {
            throw new IllegalArgumentException("El controladorDashboard no puede ser null");
        }
        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser null o vacío");
        }

        // CAMBIO: Permitir null temporalmente pero advertir
        if (controladorUnidades == null) {
            System.err.println("ADVERTENCIA: El controladorUnidades es null");
            System.err.println("Esto puede causar problemas de navegación al usar los botones Back y Finalizar");
            System.err.println("Stack trace para debug:");
            Thread.dumpStack();
        }

        System.out.println("=== DEBUG: Todos los parámetros del constructor son válidos ===");
        System.out.println("[DEBUG Unidad1] Constructor ejecutado. Inicializando vista y listeners...");

        this.vista = vista;
        this.conn = conn;
        this.controladorDashboard = controladorDashboard;
        this.correo = correo;
        this.controladorUnidades = controladorUnidades; // Puede ser null temporalmente
        this.dashboard = controladorDashboard.getVista();
        this.idUsuario = Usuario.obtenerIdPorCorreo(correo);
        inicializarVista();
        agregarListeners();

        // Mostrar mensaje de bienvenida e instrucciones solo la primera vez
        if (!instruccionesMostradas) {
            mostrarInstruccionesUnidad1();
            instruccionesMostradas = true;
        }
    }

    private void inicializarVista() {
        try {
            System.out.println("[DEBUG] Iniciando inicializarVista() para Unidad 1");
            
            // 1. Obtener o crear el progreso del usuario
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
            if (progreso == null) {
                progreso = new Modelo_Progreso_Usuario(0, idUsuario, ID_UNIDAD, 0, 0, false, 0, LocalDateTime.now());
                Modelo_Progreso_Usuario.guardarProgreso(progreso);
            }

            // 2. Calcular el progreso total
            int leccionesCompletadas = progreso.getLeccionesCompletadas();
            int actividadesCompletadas = progreso.getActividadesCompletadas();
            boolean evaluacionAprobada = progreso.isEvaluacionAprobada();
            
            // Usar la clase centralizada CalculadorProgreso para consistencia
            int progresoTotal = CalculadorProgreso.calcularProgreso(leccionesCompletadas, actividadesCompletadas, evaluacionAprobada);
            
            // 3. Configurar la barra de progreso
            configurarBarraProgreso(progresoTotal);
            
            // 4. Configurar los botones según el progreso
            configurarBotonesSegunProgreso(progreso);
            
            // 5. Configurar el botón de reinicio
            vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgreso());
            
            // 6. Configurar el botón de finalizar unidad
            vista.jButtonFINALIZARUNIDAD1.addActionListener(e -> finalizarUnidad());
            
            // El resto de la lógica de habilitación secuencial ya está cubierta en configurarBotonesSegunProgreso
            
            System.out.println("[DEBUG] Componentes configurados:");
            System.out.println("  - jButtonLECCIONFONOLOGIA habilitado: " + vista.jButtonLECCIONFONOLOGIA.isEnabled());
            System.out.println("  - jButtonACTIIVIDAD1 habilitado: " + vista.jButtonACTIIVIDAD1.isEnabled());
            System.out.println("  - jButtonLECCIONSALUDOS habilitado: " + vista.jButtonLECCIONSALUDOS.isEnabled());
            System.out.println("  - Progreso total: " + progresoTotal + "%");
            System.out.println("  - Vista visible: " + vista.isVisible());
            System.out.println("  - Vista tamaño: " + vista.getSize());
            
            // Asegurar que la vista esté visible y configurada
            vista.setVisible(true);
            vista.setOpaque(true);
            
            // Forzar actualización de la interfaz
            vista.revalidate();
            vista.repaint();
            
            System.out.println("[DEBUG] inicializarVista() completado exitosamente");
            
        } catch (Exception e) {
            System.err.println("Error al inicializar la vista de la Unidad 1: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, 
                "Error al cargar la Unidad 1. Por favor, intente nuevamente.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
                
            // Intentar regresar al dashboard en caso de error
            if (controladorDashboard != null && controladorDashboard.getVista() != null) {
                controladorDashboard.getVista().setVisible(true);
            }
        }
    }

    private void agregarListeners() {
        System.out.println("[DEBUG Unidad1] Registrando listeners para los botones de la unidad 1...");
        vista.jButtonLECCIONSALUDOS.addActionListener(e -> abrirLeccionSaludos());
        vista.jButtonLECCIONFONOLOGIA.addActionListener(e -> abrirLeccionFonetica());
        vista.jButtonLECCIONPRONOMBRES.addActionListener(e -> abrirLeccionPronombres());
        vista.jButtonACTIIVIDAD1.addActionListener(e -> abrirActividad1());
        vista.jButtonACTIVIDAD2.addActionListener(e -> abrirActividad2());
        vista.jButtonAtividad3.addActionListener(e -> abrirActividad3());
        vista.jButtonEVALUACION.addActionListener(e -> abrirEvaluacion());
        vista.jButtonREINICIARU1.addActionListener(e -> reiniciarProgresoUnidad1());

        vista.jLabelFinalizar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controladorUnidades != null) {
                    controladorUnidades.actualizarVista();
                    controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
                } else {
                    System.err.println("ERROR: controladorUnidades es null en jLabelFinalizar");
                    System.err.println("Navegando directamente al panel de unidades...");
                    controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
                }
            }
        });
    }

    /**
     * Muestra las instrucciones de bienvenida e información sobre el flujo de
     * la Unidad 1
     */
    private void mostrarInstruccionesUnidad1() {
        String mensaje = "<html><div style='width: 400px; padding: 20px; font-family: Arial, sans-serif;'>"
                + "<h2 style='color: #2E8B57; text-align: center; margin-bottom: 15px;'>"
                + "🎓 ¡Bienvenido a la Unidad 1! 🎓</h2>"
                + "<h3 style='color: #1E6B3E; margin-bottom: 10px;'>Aprendizaje de Kichwa Ecuatoriano</h3>"
                + "<p style='font-size: 14px; line-height: 1.6; margin-bottom: 15px;'>"
                + "En esta unidad aprenderás los fundamentos del kichwa a través de un sistema "
                + "<b>secuencial y estructurado</b>.</p>"
                + "<h4 style='color: #2E8B57; margin-bottom: 8px;'>📚 Orden de Completación:</h4>"
                + "<ol style='font-size: 13px; line-height: 1.8; margin-left: 20px;'>"
                + "<li><b>Lección de Fonología</b> - Sonidos y pronunciación</li>"
                + "<li><b>Actividad 1</b> - Práctica de fonología</li>"
                + "<li><b>Lección de Saludos</b> - Expresiones básicas</li>"
                + "<li><b>Actividad 2</b> - Práctica de saludos</li>"
                + "<li><b>Lección de Pronombres</b> - Estructura gramatical</li>"
                + "<li><b>Actividad 3</b> - Juego de asociación</li>"
                + "<li><b>Evaluación Final</b> - Demostrar conocimientos</li>"
                + "</ol>"
                + "<div style='background-color: #E8F5E8; padding: 12px; border-radius: 8px; margin-top: 15px;'>"
                + "<p style='font-size: 13px; margin: 0; color: #1E6B3E;'>"
                + "<b>⚠️ Importante:</b> Debes completar cada paso en orden para desbloquear el siguiente. "
                + "Solo cuando completes toda la unidad (100%) podrás avanzar a la Unidad 2.</p>"
                + "</div>"
                + "<p style='text-align: center; margin-top: 15px; font-size: 14px; color: #2E8B57;'>"
                + "<b>¡Comencemos tu aventura de aprendizaje!</b></p>"
                + "</div></html>";

        JOptionPane.showMessageDialog(
                vista,
                mensaje,
                "Instrucciones - Unidad 1: Fundamentos del Kichwa",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void abrirLeccionFonetica() {
        System.out.println("=== Abriendo lección de fonología (lección 1) ===");
        
        // 🔧 SOLUCIÓN: Limpiar recursos de video anteriores antes de crear nueva instancia
        limpiarRecursosLeccionActiva();
        
        Vista_LeccionFONOLOGIA vistaLeccion = new Vista_LeccionFONOLOGIA();
        
        // Crear y guardar referencia del nuevo controlador
        controladorLeccionActivo = new Controlador_Lecciones(
            vistaLeccion, 
            controladorDashboard, 
            conn, 
            correo, 
            Controlador_Lecciones.LECCION_FONOLOGIA
        );
        
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
        System.out.println("=== Lección de fonología abierta - Recursos anteriores limpiados ===");
    }
    
    /**
     * 🔧 SOLUCIÓN PARA BUG DE VIDEO MÚLTIPLE:
     * Limpia los recursos de la lección activa antes de crear una nueva instancia.
     * Esto previene que múltiples videos se reproduzcan simultáneamente.
     */
    private void limpiarRecursosLeccionActiva() {
        try {
            if (controladorLeccionActivo != null) {
                System.out.println("[DEBUG] Limpiando recursos de lección activa...");
                
                // Llamar al método de limpieza del controlador de lecciones
                controladorLeccionActivo.limpiarRecursos();
                
                // Limpiar la referencia
                controladorLeccionActivo = null;
                
                System.out.println("[DEBUG] Recursos de lección limpiados correctamente");
            } else {
                System.out.println("[DEBUG] No hay lección activa que limpiar");
            }
        } catch (Exception e) {
            System.err.println("Error al limpiar recursos de lección: " + e.getMessage());
            e.printStackTrace();
            // Asegurar que la referencia se limpie incluso si hay error
            controladorLeccionActivo = null;
        }
    }

    private void abrirLeccionSaludos() {
        System.out.println("=== Abriendo lección de saludos (lección 2) ===");
        
        // 🔧 Limpiar recursos de video anteriores
        limpiarRecursosLeccionActiva();
        
        Vista_LeccionSALUDOS vistaLeccion = new Vista_LeccionSALUDOS();
        
        // Crear y guardar referencia del nuevo controlador
        controladorLeccionActivo = new Controlador_Lecciones(
            vistaLeccion, 
            controladorDashboard, 
            conn, 
            correo, 
            Controlador_Lecciones.LECCION_SALUDOS
        );
        
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
        System.out.println("=== Lección de saludos abierta - Recursos anteriores limpiados ===");
    }

    private void abrirLeccionPronombres() {
        System.out.println("=== Abriendo lección de pronombres (lección 3) ===");
        
        // 🔧 Limpiar recursos de video anteriores
        limpiarRecursosLeccionActiva();
        
        Vista_LeccionPRONOMBRES vistaLeccion = new Vista_LeccionPRONOMBRES();
        
        // Crear y guardar referencia del nuevo controlador
        controladorLeccionActivo = new Controlador_Lecciones(
            vistaLeccion, 
            controladorDashboard, 
            conn, 
            correo, 
            Controlador_Lecciones.LECCION_PRONOMBRES
        );
        
        controladorDashboard.getVista().mostrarVista(vistaLeccion);
        System.out.println("=== Lección de pronombres abierta ===");
    }

    private void abrirActividad1() {
        Vista_Actividad1U1 vistaActividad = new Vista_Actividad1U1();
        Controlador_Actividad_PreguntaRespuesta controlador = new Controlador_Actividad_PreguntaRespuesta(
                vistaActividad,
                conn,
                controladorDashboard,
                this, // 'this' es el Controlador_Unidad1 actual
                correo
        );
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirActividad2() {
        int idActividad = 2;
        Vista_Actividad2U1 vistaActividad = new Vista_Actividad2U1();
        Controlador_Actividades controladorAct = new Controlador_Actividades(vistaActividad, controladorDashboard, conn, correo, idActividad, this); // <-- PASA "this"
        controladorAct.cargarActividad();
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirActividad3() {
        int idActividad = 3;
        Vista_Actividad3U1 vistaActividad = new Vista_Actividad3U1();
        Controlador_Actv3U1 controladorAct3 = new Controlador_Actv3U1(vistaActividad, controladorDashboard, conn, correo, idActividad, this);
        controladorAct3.cargarActividad();
        controladorDashboard.getVista().mostrarVista(vistaActividad);
    }

    private void abrirEvaluacion() {
        Vista_EvaluacionU1 vistaEvaluacion = new Vista_EvaluacionU1();
        new Controlador_Evaluaciones(vistaEvaluacion, controladorDashboard, conn, correo, ID_UNIDAD, controladorUnidades);
        controladorDashboard.getVista().mostrarVista(vistaEvaluacion);
    }

    private void reiniciarProgresoUnidad1() {
        int confirmacion = javax.swing.JOptionPane.showConfirmDialog(
                vista,
                "¿Estás seguro de que deseas reiniciar tu progreso en la Unidad 1?\nEsta acción no se puede deshacer.",
                "Confirmar reinicio",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (confirmacion == javax.swing.JOptionPane.YES_OPTION) {
            Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
            if (progreso != null) {
                progreso.setLeccionesCompletadas(0);
                progreso.setActividadesCompletadas(0);
                progreso.setEvaluacionAprobada(false);
                progreso.setCalificacion(0);
                progreso.setFechaActualizacion(LocalDateTime.now());
                Modelo_Progreso_Usuario.actualizarProgreso(progreso);
                inicializarVista();
                javax.swing.JOptionPane.showMessageDialog(vista, "Progreso reiniciado correctamente.");
            }
        }
    }

    /**
     * Método para actualizar la vista cuando se completa una actividad o lección
     */
    public void actualizarVista() {
        Modelo_Progreso_Usuario progreso = Modelo_Progreso_Usuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        if (progreso != null) {
            configurarBotonesSegunProgreso(progreso);
            int progresoTotal = CalculadorProgreso.calcularProgreso(
                progreso.getLeccionesCompletadas(),
                progreso.getActividadesCompletadas(),
                progreso.isEvaluacionAprobada()
            );
            configurarBarraProgreso(progresoTotal);
        }
    }

    /**
     * Configura la barra de progreso con el porcentaje especificado
     * @param porcentaje Porcentaje de progreso (0-100)
     */
    private void configurarBarraProgreso(int porcentaje) {
        if (vista != null && vista.jProgressBarUNIDAD1 != null) {
            vista.jProgressBarUNIDAD1.setValue(porcentaje);
            vista.jProgressBarUNIDAD1.setString(porcentaje + "%");
            vista.jProgressBarUNIDAD1.setStringPainted(true); // Asegúrate de que el texto se muestre

            // Cambiar color según el progreso
            if (porcentaje < 30) {
                vista.jProgressBarUNIDAD1.setForeground(new Color(220, 53, 69)); // Rojo
            } else if (porcentaje < 70) {
                vista.jProgressBarUNIDAD1.setForeground(new Color(255, 193, 7)); // Amarillo
            } else {
                vista.jProgressBarUNIDAD1.setForeground(new Color(40, 167, 69)); // Verde
            }
            
            // Sincronizar con la barra de progreso del panel de unidades
            if (controladorUnidades != null) {
                controladorUnidades.actualizarProgresoUnidad(ID_UNIDAD, porcentaje); // Pasa el ID_UNIDAD correcto
                System.out.println("Unidad 1: Sincronizando valor exacto de barra: " + porcentaje + "%");
            }
        }
    }

    /**
     * Reinicia el progreso de la unidad
     */
    private void reiniciarProgreso() {
        reiniciarProgresoUnidad1();
    }

    /**
     * Finaliza la unidad y actualiza el progreso
     */
    private void finalizarUnidad() {
        if (controladorUnidades != null) {
            controladorUnidades.actualizarVista();
            controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
        } else {
            controladorDashboard.getVista().mostrarVista(controladorDashboard.getPanelUnidades());
        }
    }
    
    /**
     * Getter para obtener el controlador de unidades
     *
     * @return Controlador_Unidades
     */
    public Controlador_Unidades getControladorUnidades() {
        return controladorUnidades;
    }

    /**
     * Configura los botones según el progreso del usuario
     * @param progreso objeto Modelo_Progreso_Usuario con el progreso actual
     */
    private void configurarBotonesSegunProgreso(Modelo_Progreso_Usuario progreso) {
        if (vista == null || progreso == null) {
            return;
        }

        try {
            // Inicialmente deshabilitamos todos los botones
            vista.jButtonLECCIONFONOLOGIA.setEnabled(false);
            vista.jButtonLECCIONSALUDOS.setEnabled(false);
            vista.jButtonLECCIONPRONOMBRES.setEnabled(false);
            vista.jButtonACTIIVIDAD1.setEnabled(false);
            vista.jButtonACTIVIDAD2.setEnabled(false);
            vista.jButtonAtividad3.setEnabled(false);
            vista.jButtonEVALUACION.setEnabled(false);
            vista.jButtonFINALIZARUNIDAD1.setEnabled(false); // Deshabilitar por defecto

            // Habilitar siempre la primera lección
            vista.jButtonLECCIONFONOLOGIA.setEnabled(true);

            // Obtener el progreso actual
            int leccionesCompletadas = progreso.getLeccionesCompletadas();
            int actividadesCompletadas = progreso.getActividadesCompletadas();
            boolean evaluacionAprobada = progreso.isEvaluacionAprobada();

            // Lógica de habilitación secuencial: Fonología → Actividad 1 → Saludos → Actividad 2 → Pronombres → Actividad 3 → Evaluación
            
            // Paso 2: Actividad 1 - Requiere Fonología completada (lección 1)
            if (leccionesCompletadas >= 1) {
                vista.jButtonACTIIVIDAD1.setEnabled(true);
            }

            // Paso 3: Lección Saludos - Requiere Actividad 1 completada
            if (actividadesCompletadas >= 1) {
                vista.jButtonLECCIONSALUDOS.setEnabled(true);
            }

            // Paso 4: Actividad 2 - Requiere Saludos completada (lección 2)
            if (leccionesCompletadas >= 2) {
                vista.jButtonACTIVIDAD2.setEnabled(true);
            }

            // Paso 5: Lección Pronombres - Requiere Actividad 2 completada
            if (actividadesCompletadas >= 2) {
                vista.jButtonLECCIONPRONOMBRES.setEnabled(true);
            }

            // Paso 6: Actividad 3 - Requiere Pronombres completada (lección 3)
            if (leccionesCompletadas >= 3) {
                vista.jButtonAtividad3.setEnabled(true);
            }

            // Paso 7: Evaluación - Requiere Actividad 3 completada
            if (actividadesCompletadas >= 3) {
                vista.jButtonEVALUACION.setEnabled(true);
            }
            
            // Habilitar el botón de finalizar unidad si la evaluación está aprobada (unidad 100% completa)
            if (evaluacionAprobada) {
                vista.jButtonFINALIZARUNIDAD1.setEnabled(true);
            }

        } catch (Exception e) {
            System.err.println("Error al configurar botones: " + e.getMessage());
            e.printStackTrace();
        }
        
        // También actualizar el controlador de unidades si es necesario
        if (controladorUnidades != null) {
            controladorUnidades.actualizarVista();
        }
    }
}
