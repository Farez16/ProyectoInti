package Controlador;

import Modelo.Modelo_Lecciones;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import Vista.*;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Controlador simplificado para manejar todas las lecciones
 */
public class Controlador_Lecciones {

    private final JPanel vistaLeccion;
    private final ControladorDashboard controladorDashboard;
    private final Connection conn;
    private final String correo;
    private final int numeroLeccion;
    private final int idUsuario;
    private final int ID_UNIDAD = 1;
    private ImageIcon imagen1Original;
    private ImageIcon imagen2Original;

    // Tipos de lecciones
    public static final int LECCION_SALUDOS = 1;
    public static final int LECCION_FONOLOGIA = 2;
    public static final int LECCION_PRONOMBRES = 3;

    public Controlador_Lecciones(JPanel vistaLeccion, ControladorDashboard controladorDashboard,
            Connection conn, String correo, int numeroLeccion) {
        this.vistaLeccion = vistaLeccion;
        this.controladorDashboard = controladorDashboard;
        this.conn = conn;

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo no puede ser nulo o vacío");
        }
        this.correo = correo.trim();

        this.numeroLeccion = numeroLeccion;

        // Obtener idUsuario a partir del correo
        this.idUsuario = Usuario.obtenerIdPorCorreo(this.correo);

        if (this.idUsuario <= 0) {
            throw new IllegalArgumentException("Usuario no encontrado con el correo: " + this.correo);
        }

        System.out.println("=== Inicializando Controlador_Lecciones ===");
        System.out.println("Lección: " + numeroLeccion + " | Usuario (correo): " + this.correo);

        configurarVistaLeccion();
        agregarListeners();
    }

    private void configurarVistaLeccion() {
        System.out.println("Configurando vista de lección: " + numeroLeccion);

        // Configurar según el tipo de lección
        configurarSegunTipoLeccion();

        // Para lecciones con WebView, dar tiempo adicional
        if (numeroLeccion == LECCION_SALUDOS) {
            SwingUtilities.invokeLater(() -> {
                // Esperar a que la vista esté completamente visible
                Timer timer = new Timer(200, e -> {
                    if (vistaLeccion.isShowing()) {
                        System.out.println("Vista de saludos visible, WebView debería inicializarse automáticamente");
                        ((Timer) e.getSource()).stop();
                    }
                });
                timer.setRepeats(false);
                timer.start();
            });
        }
    }

    private void configurarSegunTipoLeccion() {
        switch (numeroLeccion) {
            case LECCION_SALUDOS:
                configurarLeccionSaludos();
                configurarVistaLeccionSaludos();
                break;
            case LECCION_FONOLOGIA:
                configurarLeccionFonologia();
                break;
            case LECCION_PRONOMBRES:
                configurarLeccionPronombres();
                break;
        }
    }

    private void configurarLeccionSaludos() {
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;
            vista.jButtonCOMPLETOSALUDOS.setText("COMPLETAR LECCIÓN DE SALUDOS");
        }
    }

    private void configurarLeccionFonologia() {
        if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
            vista.jButtonCOMPLETOFONOLOGIA.setText("COMPLETAR LECCIÓN DE FONOLOGÍA");
        }
    }

    private void configurarLeccionPronombres() {
        if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
            vista.jButtonCOMPLETOPRONOMBRES.setText("COMPLETAR LECCIÓN DE PRONOMBRES");
        }
    }

    private void agregarListeners() {
        // Agregar listeners según el tipo de vista
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;
            vista.jButtonCOMPLETOSALUDOS.addActionListener(e -> completarLeccion());

            vista.jLabelImagen1Saludos.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    escalarYMostrarImagen(vista.jLabelImagen1Saludos, imagen1Original);
                }
            });

            vista.jLabelImagen2Saludos.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    escalarYMostrarImagen(vista.jLabelImagen2Saludos, imagen2Original);
                }
            });

        } else if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
            vista.jButtonCOMPLETOFONOLOGIA.addActionListener(e -> completarLeccion());

        } else if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
            vista.jButtonCOMPLETOPRONOMBRES.addActionListener(e -> completarLeccion());
        }
    }

    private void actualizarImagen(JLabel label, ImageIcon imagenOriginal) {
        int ancho = label.getWidth();
        int alto = label.getHeight();

        if (ancho > 0 && alto > 0 && imagenOriginal != null) {
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagenEscalada));
        }
    }

    private void completarLeccion() {
        try {
            System.out.println("Completando lección " + numeroLeccion);

            // Obtener o crear progreso para el usuario
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);

            // Verificar si esta lección ya fue completada
            if (progreso.getLeccionesCompletadas() >= numeroLeccion) {
                int respuesta = JOptionPane.showConfirmDialog(
                        vistaLeccion,
                        "Ya has completado esta lección. ¿Quieres marcarla como completada nuevamente?",
                        "Lección ya completada",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                if (respuesta != JOptionPane.YES_OPTION) {
                    return;
                }
            }

            // Actualizar progreso de lecciones usando el controlador
            boolean actualizado = ControladorProgresoUsuario.actualizarLeccion(progreso, numeroLeccion);

            if (actualizado || progreso.getLeccionesCompletadas() >= numeroLeccion) {
                mostrarMensajeExito();
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

    private void mostrarMensajeExito() {
        String nombreLeccion = obtenerNombreLeccion();
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "¡Felicitaciones! Has completado la " + nombreLeccion + " exitosamente.",
                "Lección Completada",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private String obtenerNombreLeccion() {
        switch (numeroLeccion) {
            case LECCION_SALUDOS:
                return "Lección de Saludos";
            case LECCION_FONOLOGIA:
                return "Lección de Fonología";
            case LECCION_PRONOMBRES:
                return "Lección de Pronombres";
            default:
                return "Lección " + numeroLeccion;
        }
    }

    private void actualizarInterfaz() {
        System.out.println("Actualizando interfaz - regresando a Unidad 1");

        // Crear nueva instancia de la vista de unidad
        Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
        new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo);
        controladorDashboard.getVista().mostrarVista(vistaUnidad1);
    }

    private void configurarVistaLeccionSaludos() {
        System.out.println(">> Entrando a configurarVistaLeccionSaludos()");
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

            // Obtener textos
            List<Modelo_Lecciones> textos = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "texto");
            List<Modelo_Lecciones> imagenes = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "imagen");

            System.out.println("Textos encontrados: " + textos.size());
            System.out.println("Imágenes encontradas: " + imagenes.size());

            // Mostrar textos
            if (textos.size() > 0) {
                vista.jTextAreaTexto1Saludos.setText(textos.get(0).getContenido());
            }
            if (textos.size() > 1) {
                vista.jTextAreaTexto2Saludos.setText(textos.get(1).getContenido());
            }

            // Cargar y mostrar imagen 1
            String rutaImagen1 = "Imagenes/Lecciones/Imagen1Saludos.png";
            java.net.URL url1 = getClass().getClassLoader().getResource(rutaImagen1);
            if (url1 != null) {
                System.out.println("Ruta imagen 1: " + url1);
                imagen1Original = new ImageIcon(url1);
                escalarYMostrarImagen(vista.jLabelImagen1Saludos, imagen1Original);

                vista.jLabelImagen1Saludos.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        escalarYMostrarImagen(vista.jLabelImagen1Saludos, imagen1Original);
                    }
                });
            } else {
                System.out.println("Error: imagen1 no pudo cargarse desde la ruta");
            }

            // Cargar y mostrar imagen 2
            String rutaImagen2 = "Imagenes/Lecciones/Imagen2Saludos.png";
            java.net.URL url2 = getClass().getClassLoader().getResource(rutaImagen2);
            if (url2 != null) {
                System.out.println("Ruta imagen 2: " + url2);
                imagen2Original = new ImageIcon(url2);
                escalarYMostrarImagen(vista.jLabelImagen2Saludos, imagen2Original);

                vista.jLabelImagen2Saludos.addComponentListener(new ComponentAdapter() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        escalarYMostrarImagen(vista.jLabelImagen2Saludos, imagen2Original);
                    }
                });
            } else {
                System.out.println("Error: imagen2 no pudo cargarse desde la ruta");
            }
        }
    }

    private void escalarYMostrarImagen(JLabel label, ImageIcon imagenOriginal) {
        int ancho = label.getWidth();
        int alto = label.getHeight();

        if (ancho > 0 && alto > 0 && imagenOriginal != null) {
            Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(imagenEscalada));
        }
    }

    // Método para obtener estadísticas de progreso
    public String obtenerEstadisticasProgreso() {
        Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);

        int totalLecciones = 3; // Saludos, Fonología, Pronombres
        int totalActividades = 2; // Ajusta según tu unidad

        double porcentajeLecciones = (double) progreso.getLeccionesCompletadas() / totalLecciones * 100;
        double porcentajeActividades = (double) progreso.getActividadesCompletadas() / totalActividades * 100;

        return String.format(
                "Lecciones: %d/%d (%.1f%%) | Actividades: %d/%d (%.1f%%) | Evaluación: %s | Calificación: %d",
                progreso.getLeccionesCompletadas(), totalLecciones, porcentajeLecciones,
                progreso.getActividadesCompletadas(), totalActividades, porcentajeActividades,
                progreso.isEvaluacionAprobada() ? "Aprobada" : "Pendiente",
                progreso.getCalificacion()
        );
    }

}
