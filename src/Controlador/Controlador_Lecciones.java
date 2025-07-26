package Controlador;

import Vista.Vistas_Unidad1.Vista_LeccionFONOLOGIA;
import Vista.Vistas_Unidad1.Vista_Unidad1;
import Vista.Vistas_Unidad1.Vista_LeccionPRONOMBRES;
import Vista.Vistas_Unidad1.Vista_LeccionSALUDOS;
import Modelo.Modelo_Lecciones;
import Modelo.Usuario;
import Modelo.Modelo_Progreso_Usuario;
import VistasUnidad3.Vista_Leccion_Familia;
import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.sql.Connection;
import java.util.List;

/**
 * Controlador actualizado para manejar el flujo de 3 páginas de lecciones
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
    private final Controlador_Unidades controladorUnidades;

    // Controlador de video
    private Controlador_Video controladorVideo;
    private boolean sonidoActivado = true;
    
    // Timers para controles de video
    private Timer timerVideoSaludos;
    private Timer timerVideoFonologia;
    private Timer timerVideoPronombres;

    // Control de páginas
    private int paginaActual = 1;

    // Estados de las páginas
    private boolean videoCompletado = false;
    private boolean contenidoVisualizadoPag2 = false;
    private boolean contenidoVisualizadoPag3 = false;

    // Tipos de lecciones
    public static final int LECCION_SALUDOS = 1;
    public static final int LECCION_FONOLOGIA = 2;
    public static final int LECCION_PRONOMBRES = 3;
    
    // Tipos de lecciones
    public static final int LECCION_FAMILIA = 31;
    public static final int LECCION_VESTIMENTA = 32;
    public static final int LECCION_VESTIMENTA1 = 33;
    
    // Constantes para CardLayout
    private static final String PAGINA_1 = "pagina1";
    private static final String PAGINA_2 = "pagina2";
    private static final String PAGINA_3 = "pagina3";
    
    // Constante para timer de video
    private static final int VIDEO_TIMER_DELAY = 100;

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

        this.controladorUnidades = null;

        configurarVistaLeccion();
        agregarListeners();
        inicializarPagina1();
    }

    private void configurarVistaLeccion() {
        System.out.println("Configurando vista de lección: " + numeroLeccion);
        configurarSegunTipoLeccion();
    }

    private void configurarSegunTipoLeccion() {
        switch (numeroLeccion) {
            case LECCION_SALUDOS:
                configurarLeccionSaludos();
                break;
            case LECCION_FONOLOGIA:
                configurarLeccionFonologia();
                break;
            case LECCION_PRONOMBRES:
                configurarLeccionPronombres();
                break;
            case LECCION_FAMILIA:
                configurarLeccionFamilia();
                break;
        }
    }
    
    

    private void configurarLeccionSaludos() {
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;
            
            // Mostrar instrucciones de la lección
            mostrarInstruccionesLeccion("Saludos", 
                "Aprenderás las expresiones básicas de saludo y despedida en kichwa ecuatoriano.",
                "• Observa el video completo sobre saludos en kichwa\n" +
                "• Escucha atentamente la pronunciación de cada saludo\n" +
                "• Usa los controles de video para pausar y repetir si es necesario\n" +
                "• Navega por las 3 páginas de contenido\n" +
                "• Al completar el video, se habilitará el botón 'Siguiente página'\n" +
                "• Completa todas las páginas para terminar la lección");

            // Configurar el CardLayout para navegación entre páginas
            vista.jPanelContenedor.setLayout(new CardLayout());
            vista.jPanelContenedor.add(vista.jPanelPag1, PAGINA_1);
            vista.jPanelContenedor.add(vista.jPanelPag2, PAGINA_2);
            vista.jPanelContenedor.add(vista.jPanelPag3, PAGINA_3);

            // Configurar botones iniciales
            vista.jButtonCOMPLETOSALUDOS.setText("COMPLETAR LECCIÓN DE SALUDOS");
            vista.jButtonSiguientePag.setText("Siguiente página");
            vista.jButtonSiguientePag2.setText("Siguiente Pag");
            
            // Asegurar que los botones estén visibles y habilitados correctamente
            vista.jButtonSiguientePag.setVisible(true);
            vista.jButtonSiguientePag2.setVisible(true);
            vista.jButtonCOMPLETOSALUDOS.setVisible(true);
            
            // Deshabilitar botones hasta que se complete el video
            vista.jButtonSiguientePag.setEnabled(false);
            vista.jButtonSiguientePag2.setEnabled(true); // Habilitado para navegación en página 2

            // Limpiar recursos anteriores si existen
            limpiarRecursosVideo();
            
            // Inicializar controlador de video y controles de video
            try {
                controladorVideo = new Controlador_Video(vista.jPanelVideo);
                configurarEventosVideo();
                configurarControlesVideo(vista);
                System.out.println("Controlador de video inicializado correctamente para saludos");
            } catch (Exception e) {
                System.err.println("Error al inicializar controlador de video: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Configura listeners y lógica para los controles de video (play, pausa,
     * volumen, progreso)
     */
   

    private void configurarControlesVideo(Vista_LeccionSALUDOS vista) {
        if (controladorVideo == null) {
            System.err.println("Error: controladorVideo es null al configurar controles");
            return;
        }
        
        final boolean[] isSliderAdjusting = {false};

        // Play
        vista.jButtonPlay.addActionListener(e -> {
            try {
                if (controladorVideo != null) {
                    controladorVideo.reproducir();
                }
            } catch (Exception ex) {
                System.err.println("Error al reproducir video: " + ex.getMessage());
            }
        });

        // Pausa
        vista.jButtonPausa.addActionListener(e -> {
            try {
                if (controladorVideo != null) {
                    controladorVideo.pausar();
                }
            } catch (Exception ex) {
                System.err.println("Error al pausar video: " + ex.getMessage());
            }
        });

        // Mute / Unmute
        vista.jButtonVoliumen.addActionListener(e -> {
            try {
                if (controladorVideo != null) {
                    if (sonidoActivado) {
                        controladorVideo.setVolume(0);
                        sonidoActivado = false;
                        vista.jButtonVoliumen.setText("Unmute");
                    } else {
                        controladorVideo.setVolume(1);
                        sonidoActivado = true;
                        vista.jButtonVoliumen.setText("Mute");
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error al cambiar volumen: " + ex.getMessage());
            }
        });

        // Control volumen con slider (valor entre 0 y 100)
        vista.jSliderSubirBajarVolumen.addChangeListener(e -> {
            try {
                if (controladorVideo != null) {
                    int valor = vista.jSliderSubirBajarVolumen.getValue();
                    double volumen = valor / 100.0;
                    controladorVideo.setVolume(volumen);
                    sonidoActivado = volumen > 0;
                    vista.jButtonVoliumen.setText(sonidoActivado ? "Mute" : "Unmute");
                }
            } catch (Exception ex) {
                System.err.println("Error al ajustar volumen con slider: " + ex.getMessage());
            }
        });

        // Detectar cuando usuario empieza y termina de arrastrar el slider de progreso
        vista.jSliderProgresoVideo.addChangeListener(e -> {
            try {
                if (vista.jSliderProgresoVideo.getValueIsAdjusting()) {
                    isSliderAdjusting[0] = true;
                } else {
                    if (isSliderAdjusting[0] && controladorVideo != null) {
                        // Usuario terminó de mover el slider, hacer seek
                        int valor = vista.jSliderProgresoVideo.getValue();
                        double duracion = controladorVideo.getDurationSeconds();
                        if (duracion > 0) {
                            double nuevoTiempo = (valor / 100.0) * duracion;
                            controladorVideo.seek(nuevoTiempo);
                        }
                        isSliderAdjusting[0] = false;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error al manejar slider de progreso: " + ex.getMessage());
            }
        });

        // Detener timer anterior si existe
        if (timerVideoSaludos != null) {
            timerVideoSaludos.stop();
            timerVideoSaludos = null;
        }
        
        // Actualizar slider y label de tiempo conforme avanza el video, solo si no se está arrastrando
        timerVideoSaludos = new Timer(VIDEO_TIMER_DELAY, e -> {
            try {
                if (controladorVideo != null && !isSliderAdjusting[0]) {
                    double duracion = controladorVideo.getDurationSeconds();
                    double actual = controladorVideo.getCurrentTimeSeconds();
                    if (duracion > 0) {
                        int progreso = (int) ((actual / duracion) * 100);
                        vista.jSliderProgresoVideo.setValue(progreso);

                        String tiempoActual = formatoTiempo((int) actual);
                        String tiempoTotal = formatoTiempo((int) duracion);

                        vista.jLabelTiempoV.setText(tiempoActual + " / " + tiempoTotal);
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error en timer de video: " + ex.getMessage());
            }
        });
        timerVideoSaludos.start();
        System.out.println("Controles de video configurados correctamente");
    }

// Método auxiliar para convertir segundos a formato mm:ss
    private String formatoTiempo(int segundos) {
        int min = segundos / 60;
        int seg = segundos % 60;
        return String.format("%02d:%02d", min, seg);
    }

    private void configurarLeccionFonologia() {
        if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
            
            // Mostrar instrucciones de la lección
            mostrarInstruccionesLeccion("Fonología", 
                "Aprenderás los sonidos fundamentales del kichwa ecuatoriano y su correcta pronunciación.",
                "• Navega por las 3 páginas de contenido sobre fonología\n" +
                "• Estudia los diferentes sonidos y fonemas del kichwa\n" +
                "• Presta atención a las reglas de pronunciación\n" +
                "• Tómate tu tiempo para comprender cada concepto\n" +
                "• Usa los botones de navegación para moverte entre páginas\n" +
                "• Completa todas las páginas para terminar la lección");

            // Configurar el CardLayout para navegación entre páginas
            vista.jPanelContenedorF.setLayout(new CardLayout());
            vista.jPanelContenedorF.add(vista.jPanelPag1F, PAGINA_1);
            vista.jPanelContenedorF.add(vista.jPanelPag2F, PAGINA_2);
            vista.jPanelContenedorF.add(vista.jPanelPag3F, PAGINA_3);

            // Configurar botones iniciales
            vista.jButtonCOMPLETOFONOLOGIA.setText("COMPLETAR LECCIÓN DE FONOLOGÍA");
            vista.jButtonSiguientePag2F.setText("Siguiente página");
            vista.jButtonSiguientePag3F.setText("Siguiente Pag");
            
            // Habilitar navegación directa sin video
            vista.jButtonSiguientePag2F.setEnabled(true);
        }
    }



    private void configurarLeccionPronombres() {
        if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
            
            // Mostrar instrucciones de la lección
            mostrarInstruccionesLeccion("Pronombres", 
                "Aprenderás los pronombres personales en kichwa y su uso en diferentes contextos.",
                "• Observa el video completo sobre pronombres en kichwa\n" +
                "• Aprende los pronombres: Ñuka (yo), Kan (tú), Pay (él/ella)\n" +
                "• Presta atención a la pronunciación correcta\n" +
                "• El video debe completarse para habilitar la navegación\n" +
                "• Navega por las 3 páginas de contenido\n" +
                "• Completa todas las páginas para terminar la lección");
            
            // Configurar el CardLayout para navegación entre páginas
            vista.jPanelContenedorP.setLayout(new CardLayout());
            vista.jPanelContenedorP.add(vista.jPanelPag1P, PAGINA_1);
            vista.jPanelContenedorP.add(vista.jPanelPag2P, PAGINA_2);
            vista.jPanelContenedorP.add(vista.jPanelPag3P, PAGINA_3);
            
            // Configurar botones iniciales
            vista.jButtonCOMPLETOPRONOMBRES.setText("COMPLETAR LECCIÓN DE PRONOMBRES");
            vista.jButtonSiguientePag2P.setText("Siguiente página");
            vista.jButtonSiguientePag3.setText("Siguiente Pag");
            
            // Asegurar que los botones estén visibles y habilitados correctamente
            vista.jButtonSiguientePag2P.setVisible(true);
            vista.jButtonSiguientePag3.setVisible(true);
            vista.jButtonCOMPLETOPRONOMBRES.setVisible(true);
            
            // Deshabilitar botón hasta que se complete el video
            vista.jButtonSiguientePag2P.setEnabled(false);
            
            // Limpiar recursos anteriores si existen
            limpiarRecursosVideo();
            
            // Inicializar controlador de video y controles de video
            try {
                controladorVideo = new Controlador_Video(vista.jPanelVideoP);
                configurarEventosVideoPronombres();
                configurarControlesVideoPronombres(vista);
                System.out.println("Controlador de video inicializado correctamente para pronombres");
            } catch (Exception e) {
                System.err.println("Error al inicializar controlador de video para pronombres: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    private void configurarLeccionFamilia() {
        if (vistaLeccion instanceof Vista_Leccion_Familia) {
            Vista_Leccion_Familia vista = (Vista_Leccion_Familia) vistaLeccion;
            vista.CompletarLeccion.setText("COMPLETAR LECCIÓN DE FONOLOGÍA");
        }
        
    }

    /**
     * Configura listeners y lógica para los controles de video de la lección de pronombres
     */
    private void configurarControlesVideoPronombres(Vista_LeccionPRONOMBRES vista) {
        if (controladorVideo == null) {
            System.err.println("Error: controladorVideo es null al configurar controles de pronombres");
            return;
        }
        
        final boolean[] isSliderAdjusting = {false};
        
        // Configurar slider de progreso inicial
        vista.jSliderProgresoVideoP.setMinimum(0);
        vista.jSliderProgresoVideoP.setMaximum(100);
        vista.jSliderProgresoVideoP.setValue(0);
        
        // Configurar slider de volumen inicial
        vista.jSliderSubirBajarVolumenP.setMinimum(0);
        vista.jSliderSubirBajarVolumenP.setMaximum(100);
        vista.jSliderSubirBajarVolumenP.setValue(50);

        // Play
        vista.jButtonPlay.addActionListener(e -> {
            try {
                if (controladorVideo != null) {
                    controladorVideo.reproducir();
                }
            } catch (Exception ex) {
                System.err.println("Error al reproducir video en pronombres: " + ex.getMessage());
            }
        });

        // Pausa
        vista.jButtonPausa.addActionListener(e -> {
            try {
                if (controladorVideo != null) {
                    controladorVideo.pausar();
                }
            } catch (Exception ex) {
                System.err.println("Error al pausar video en pronombres: " + ex.getMessage());
            }
        });

        // Mute / Unmute
        vista.jButtonVolumenP.addActionListener(e -> {
            try {
                if (controladorVideo != null) {
                    if (sonidoActivado) {
                        controladorVideo.setVolume(0);
                        sonidoActivado = false;
                        vista.jButtonVolumenP.setText("Unmute");
                    } else {
                        controladorVideo.setVolume(1);
                        sonidoActivado = true;
                        vista.jButtonVolumenP.setText("Mute");
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error al cambiar volumen en pronombres: " + ex.getMessage());
            }
        });

        // Control volumen con slider (valor entre 0 y 100)
        vista.jSliderSubirBajarVolumenP.addChangeListener(e -> {
            try {
                if (controladorVideo != null) {
                    int valor = vista.jSliderSubirBajarVolumenP.getValue();
                    double volumen = valor / 100.0;
                    controladorVideo.setVolume(volumen);
                    sonidoActivado = volumen > 0;
                    vista.jButtonVolumenP.setText(sonidoActivado ? "Mute" : "Unmute");
                }
            } catch (Exception ex) {
                System.err.println("Error al ajustar volumen con slider en pronombres: " + ex.getMessage());
            }
        });

        // Detectar cuando usuario empieza y termina de arrastrar el slider de progreso
        vista.jSliderProgresoVideoP.addChangeListener(e -> {
            try {
                if (vista.jSliderProgresoVideoP.getValueIsAdjusting()) {
                    isSliderAdjusting[0] = true;
                } else {
                    if (isSliderAdjusting[0] && controladorVideo != null) {
                        // Usuario terminó de mover el slider, hacer seek
                        int valor = vista.jSliderProgresoVideoP.getValue();
                        double duracion = controladorVideo.getDurationSeconds();
                        if (duracion > 0) {
                            double nuevoTiempo = (valor / 100.0) * duracion;
                            controladorVideo.seek(nuevoTiempo);
                        }
                        isSliderAdjusting[0] = false;
                    }
                }
            } catch (Exception ex) {
                System.err.println("Error al manejar slider de progreso en pronombres: " + ex.getMessage());
            }
        });

        // Detener timer anterior si existe (usar timer específico para pronombres)
        if (timerVideoPronombres != null) {
            timerVideoPronombres.stop();
            timerVideoPronombres = null;
        }
        
        // Actualizar slider y label de tiempo conforme avanza el video, solo si no se está arrastrando
        // Usar timer específico para pronombres con delay apropiado
        timerVideoPronombres = new Timer(VIDEO_TIMER_DELAY, e -> {
            try {
                if (controladorVideo != null && !isSliderAdjusting[0]) {
                    double duracion = controladorVideo.getDurationSeconds();
                    double actual = controladorVideo.getCurrentTimeSeconds();
                    
                    // Logging detallado para debugging
                    if (duracion > 0 && actual > 0) {
                        System.out.println(String.format("Timer Pronombres - Actual: %.2fs, Duración: %.2fs, Progreso: %.1f%%", 
                            actual, duracion, (actual / duracion) * 100));
                    }
                    
                    if (duracion > 0) {
                        int progreso = (int) ((actual / duracion) * 100);
                        
                        // Validar que el progreso esté en rango válido
                        progreso = Math.max(0, Math.min(100, progreso));
                        
                        vista.jSliderProgresoVideoP.setValue(progreso);

                        String tiempoActual = formatoTiempo((int) actual);
                        String tiempoTotal = formatoTiempo((int) duracion);

                        vista.jLabelTiempoVP.setText(tiempoActual + " / " + tiempoTotal);
                        
                        // Verificar si el video se ha completado naturalmente
                        if (actual >= duracion - 0.5 && !videoCompletado) {
                            System.out.println("Video de pronombres completado por timer - habilitando botón");
                            videoCompletado = true;
                            SwingUtilities.invokeLater(() -> {
                                vista.jButtonSiguientePag2P.setEnabled(true);
                            });
                        }
                    } else {
                        System.out.println("Timer Pronombres - Esperando duración del video...");
                    }
                } else if (controladorVideo == null) {
                    System.err.println("Timer Pronombres - Controlador de video es null");
                }
            } catch (Exception ex) {
                System.err.println("Error en timer de video de pronombres: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        timerVideoPronombres.start();
        System.out.println("Controles de video de pronombres configurados correctamente");
    }
    
    /**
     * Configura los eventos específicos para el video de pronombres.
     */
    private void configurarEventosVideoPronombres() {
        if (controladorVideo != null) {
            // Configurar callback cuando el video se complete
            controladorVideo.setOnVideoCompleted(() -> {
                try {
                    System.out.println("Video de pronombres completado");
                    videoCompletado = true;
                    
                    if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
                        Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
                        
                        // Habilitar el botón "Siguiente página" cuando termine el video
                        vista.jButtonSiguientePag2P.setEnabled(true);
                        
                        // Mostrar mensaje de completación
                        javax.swing.JOptionPane.showMessageDialog(
                            vista,
                            "¡Video completado! Ahora puedes continuar con la siguiente página.",
                            "Video Completado",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                        );
                        
                        System.out.println("Botón siguiente página habilitado para pronombres");
                    }
                } catch (Exception e) {
                    System.err.println("Error en callback de video completado para pronombres: " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            System.out.println("Eventos de video configurados para pronombres");
        } else {
            System.err.println("Error: controladorVideo es null al configurar eventos de pronombres");
        }
    }

    private void agregarListeners() {
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

            // Listener para botón "Siguiente página"
            vista.jButtonSiguientePag.addActionListener(e -> avanzarPagina());
            // ✅ Nuevo listener para avanzar desde página 2
            vista.jButtonSiguientePag2.addActionListener(e -> avanzarPagina());

            // Listener para botón "Completar lección"
            vista.jButtonCOMPLETOSALUDOS.addActionListener(e -> completarLeccion());

            // Listeners para redimensionar imágenes
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
            
            // Listener para botón "Siguiente página" desde página 1
            vista.jButtonSiguientePag2F.addActionListener(e -> avanzarPagina());
            // Listener para botón "Siguiente página" desde página 2
            vista.jButtonSiguientePag3F.addActionListener(e -> avanzarPagina());
            
            // Listener para botón "Completar lección"
            vista.jButtonCOMPLETOFONOLOGIA.addActionListener(e -> completarLeccion());

        } else if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
            
            // Listener para botón "Siguiente página" desde página 1
            vista.jButtonSiguientePag2P.addActionListener(e -> avanzarPagina());
            // Listener para botón "Siguiente página" desde página 2
            vista.jButtonSiguientePag3.addActionListener(e -> avanzarPagina());
            
            // Listener para botón "Completar lección"
            vista.jButtonCOMPLETOPRONOMBRES.addActionListener(e -> completarLeccion());
        }
    }

    /**
     * PÁGINA 1: Inicializar la primera página con video
     */
    private void inicializarPagina1() {
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            inicializarPagina1Saludos();
        } else if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            inicializarPagina1Fonologia();
        } else if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            inicializarPagina1Pronombres();
        }
    }

    private void inicializarPagina1Saludos() {
        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Mostrar mensaje inicial (puedes quitarlo si molesta)
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Para continuar con la lección, debes ver el video completo.",
                "Instrucciones - Página 1",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Deshabilitar botón hasta que termine el video
        vista.jButtonSiguientePag.setEnabled(false);
        
        // Resetear estado del video
        videoCompletado = false;

        // Mostrar página 1
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        cardLayout.show(vista.jPanelContenedor, PAGINA_1);

        // Liberar recursos previos del video (si existieran)
        if (controladorVideo != null) {
            try {
                controladorVideo.detenerYLiberarRecursos();
            } catch (Exception e) {
                System.err.println("Error al liberar recursos previos: " + e.getMessage());
            }
            controladorVideo = null;
        }

        // Crear nuevo controladorVideo con el panel del video
        try {
            controladorVideo = new Controlador_Video(vista.jPanelVideo);
            
            // Configurar callback para cuando termine el video
            controladorVideo.setOnVideoCompletedCallback(() -> {
                videoCompletado = true;
                SwingUtilities.invokeLater(() -> {
                    vista.jButtonSiguientePag.setEnabled(true);
                    System.out.println("Video completado - Botón habilitado");
                    JOptionPane.showMessageDialog(vistaLeccion,
                            "¡Excelente! Has completado el video. Ahora puedes continuar a la siguiente página.",
                            "Video Completado",
                            JOptionPane.INFORMATION_MESSAGE);
                });
            });
            
            // Cargar video desde la base de datos y reproducir
            cargarVideoDesdeDB();
            
        } catch (Exception e) {
            System.err.println("Error al inicializar video en página 1: " + e.getMessage());
            e.printStackTrace();
            // Habilitar botón en caso de error para no bloquear la navegación
            vista.jButtonSiguientePag.setEnabled(true);
        }
    }

    private void inicializarPagina1Fonologia() {
        Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;

        // Mostrar mensaje inicial
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Bienvenido a la lección de fonología. Puedes navegar libremente por las páginas.",
                "Lección de Fonología",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Habilitar navegación directa (sin video)
        vista.jButtonSiguientePag2F.setEnabled(true);

        // Mostrar página 1
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedorF.getLayout();
        cardLayout.show(vista.jPanelContenedorF, "PAGINA1");
    }

    private void inicializarPagina1Pronombres() {
        Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;

        // Mostrar instrucciones completas de la lección
        String instrucciones = "<html><div style='width: 400px;'>" +
                "<h2>Lección de Pronombres - Instrucciones</h2>" +
                "<p><b>Bienvenido a la lección de pronombres en Kichwa.</b></p>" +
                "<br>" +
                "<p><b>Instrucciones para completar la lección:</b></p>" +
                "<ul>" +
                "<li>1. Debes ver el video completo para continuar</li>" +
                "<li>2. Usa los controles de video (play, pausa, volumen)</li>" +
                "<li>3. El botón 'Siguiente página' se habilitará al terminar el video</li>" +
                "<li>4. Navega por las páginas usando los botones de navegación</li>" +
                "<li>5. Completa la lección usando el botón 'Completar Lección'</li>" +
                "</ul>" +
                "<br>" +
                "<p><b>Nota:</b> El progreso solo se guardará la primera vez que completes la lección.</p>" +
                "</div></html>";
        
        JOptionPane.showMessageDialog(
                vistaLeccion,
                instrucciones,
                "Instrucciones de la Lección de Pronombres",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Deshabilitar botón hasta que termine el video
        vista.jButtonSiguientePag2P.setEnabled(false);
        
        // Resetear estado del video
        videoCompletado = false;

        // Mostrar página 1
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedorP.getLayout();
        cardLayout.show(vista.jPanelContenedorP, PAGINA_1);

        // Liberar recursos previos del video (si existieran)
        if (controladorVideo != null) {
            try {
                controladorVideo.detenerYLiberarRecursos();
            } catch (Exception e) {
                System.err.println("Error al liberar recursos previos en pronombres: " + e.getMessage());
            }
            controladorVideo = null;
        }

        // Crear nuevo controladorVideo con el panel del video
        try {
            controladorVideo = new Controlador_Video(vista.jPanelVideoP);
            
            // Configurar callback para cuando termine el video
            controladorVideo.setOnVideoCompleted(() -> {
                videoCompletado = true;
                SwingUtilities.invokeLater(() -> {
                    vista.jButtonSiguientePag2P.setEnabled(true);
                    System.out.println("Video de pronombres completado - Botón habilitado");
                    JOptionPane.showMessageDialog(vistaLeccion,
                            "¡Excelente! Has completado el video. Ahora puedes continuar a la siguiente página.",
                            "Video Completado",
                            JOptionPane.INFORMATION_MESSAGE);
                });
            });
            
            // Cargar video desde la base de datos y reproducir
            cargarVideoDesdeDBPronombres();
            
        } catch (Exception e) {
            System.err.println("Error al inicializar video en página 1 de pronombres: " + e.getMessage());
            e.printStackTrace();
            // Habilitar botón en caso de error para no bloquear la navegación
            vista.jButtonSiguientePag2P.setEnabled(true);
        }
    }

    private void cargarVideoDesdeDBPronombres() {
        try {
            // Usar la ruta específica para el video de pronombres
            String rutaRelativaBD = "/VideosU1/pronombres.mp4";
            
            // Para desarrollo (rutas de archivo)
            String rutaCompleta = "src" + rutaRelativaBD; // Convierte a "src/VideosU1/pronombres.mp4"
            File archivoVideo = new File(rutaCompleta);
            
            if (archivoVideo.exists()) {
                System.out.println("Cargando video de pronombres desde: " + archivoVideo.getCanonicalPath());
                
                if (controladorVideo != null) {
                    controladorVideo.cargarVideo(archivoVideo.getAbsolutePath());
                }
            } else {
                System.err.println("El video de pronombres no existe en la ruta: " + archivoVideo.getAbsolutePath());
                System.err.println("Buscando en: " + new File(".").getCanonicalPath());
            }
        } catch (Exception e) {
            System.err.println("Error al cargar video de pronombres: " + e.getMessage());
            e.printStackTrace();
        }
    }

private void cargarVideoDesdeDB() {
    try {
        List<Modelo_Lecciones> videos = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "video");

        if (!videos.isEmpty()) {
            String rutaRelativaBD = videos.get(0).getUrlMultimedia(); // "VideosU1/Wawa kichwa - Saludos y despedidas.mp4"
            
            // Opción 1: Para desarrollo (rutas de archivo)
            String rutaCompleta = "src/" + rutaRelativaBD; // Convierte a "src/VideosU1/Wawa kichwa - Saludos y despedidas.mp4"
            File archivoVideo = new File(rutaCompleta);
            
            // Opción 2: Para producción (cuando esté en un JAR)
            // URL videoUrl = getClass().getResource("/" + rutaRelativaBD);
            
            if (archivoVideo.exists()) {
                System.out.println("Cargando video desde: " + archivoVideo.getCanonicalPath());
                
                if (controladorVideo != null) {
                    controladorVideo.cargarVideo(archivoVideo.getAbsolutePath());
                }
            } else {
                System.err.println("El video no existe en la ruta: " + archivoVideo.getAbsolutePath());
                System.err.println("Buscando en: " + new File(".").getCanonicalPath()); // Muestra dónde está buscando
            }
        } else {
            System.out.println("No se encontraron videos en la BD");
            // Habilitar botón si es necesario
        }
    } catch (Exception e) {
        System.err.println("Error al cargar video: " + e.getMessage());
        e.printStackTrace();
    }
}
    private void configurarEventosVideo() {
        if (controladorVideo != null) {
            controladorVideo.setOnVideoCompletedCallback(() -> {
                videoCompletado = true;

                if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
                    Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;
                    // Habilitar botón "Siguiente página"
                    vista.jButtonSiguientePag.setEnabled(true);
                } else if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
                    Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
                    // Habilitar botón "Siguiente página"
                    vista.jButtonSiguientePag2F.setEnabled(true);
                }

                // Mostrar mensaje de video completado (esto se ejecuta en el hilo Swing)
                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "¡Excelente! Has completado el video. Ahora puedes continuar a la siguiente página.",
                        "Video Completado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            });
        }
    }

    /**
     * PÁGINA 2: Mostrar contenido textual e imágenes
     */
    private void inicializarPagina2() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Mostrar mensaje inicial
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Lee y visualiza el contenido cuidadosamente antes de avanzar a la siguiente página.",
                "Instrucciones - Página 2",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Cargar contenido desde base de datos
        cargarContenidoPagina2();

        // Mostrar página 2
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        cardLayout.show(vista.jPanelContenedor, PAGINA_2);

        // Configurar botones para página 2
        vista.jButtonSiguientePag.setVisible(false); // Ocultar botón de página 1
        vista.jButtonSiguientePag2.setVisible(true); // Mostrar botón de página 2
        vista.jButtonSiguientePag2.setEnabled(true);
        vista.jButtonSiguientePag2.setText("Siguiente página");
        
        System.out.println("Página 2 inicializada - Botón jButtonSiguientePag2 habilitado");

        // Marcar como visualizado
        contenidoVisualizadoPag2 = true;
    }

    private void cargarContenidoPagina2() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Obtener textos e imágenes
        List<Modelo_Lecciones> textos = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "texto");
        List<Modelo_Lecciones> imagenes = Modelo_Lecciones.obtenerLeccionesPorUnidadYTipo(conn, ID_UNIDAD, "imagen");

        System.out.println("Cargando contenido Página 2:");
        System.out.println("Textos encontrados: " + textos.size());
        System.out.println("Imágenes encontradas: " + imagenes.size());

        // Cargar textos
        if (textos.size() > 0) {
            vista.jTextAreaTexto1.setText(textos.get(0).getContenido());
            vista.jTextAreaTexto1.setCaretPosition(0);
        }
        if (textos.size() > 1) {
            vista.jTextAreaTexto2Saludos.setText(textos.get(1).getContenido());
            vista.jTextAreaTexto2Saludos.setCaretPosition(0);
        }

        // Cargar imágenes
        cargarImagenes(vista);
    }

    private void cargarImagenes(Vista_LeccionSALUDOS vista) {
        // Cargar imagen 1
        String rutaImagen1 = "Imagenes/Lecciones/Imagen1Saludos.png";
        java.net.URL url1 = getClass().getClassLoader().getResource(rutaImagen1);
        if (url1 != null) {
            imagen1Original = new ImageIcon(url1);
            escalarYMostrarImagen(vista.jLabelImagen1Saludos, imagen1Original);
        }

        // Cargar imagen 2
        String rutaImagen2 = "Imagenes/Lecciones/Imagen2Saludos.png";
        java.net.URL url2 = getClass().getClassLoader().getResource(rutaImagen2);
        if (url2 != null) {
            imagen2Original = new ImageIcon(url2);
            escalarYMostrarImagen(vista.jLabelImagen2Saludos, imagen2Original);
        }
    }

    /**
     * PÁGINA 3: Actividad final y completar lección
     */
    private void inicializarPagina3() {
        if (!(vistaLeccion instanceof Vista_LeccionSALUDOS)) {
            return;
        }

        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;

        // Mostrar mensaje final
        JOptionPane.showMessageDialog(
                vistaLeccion,
                "Esta es la parte final de la lección. Revisa el contenido o realiza la actividad complementaria presentada.",
                "Instrucciones - Página 3",
                JOptionPane.INFORMATION_MESSAGE
        );

        // Mostrar página 3
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        cardLayout.show(vista.jPanelContenedor, PAGINA_3);

        // Ocultar botones de navegación
        vista.jButtonSiguientePag.setVisible(false);
        vista.jButtonSiguientePag2.setVisible(false);

        // Mostrar botón "Completar lección"
        vista.jButtonCOMPLETOSALUDOS.setVisible(true);
        vista.jButtonCOMPLETOSALUDOS.setEnabled(true);
        
        System.out.println("Página 3 inicializada - Botón completar lección habilitado");

        // Marcar como visualizado
        contenidoVisualizadoPag3 = true;
    }



    private void completarLeccion() {
        try {
            System.out.println("Completando lección " + numeroLeccion);

            // Obtener o crear progreso para el usuario
            Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);

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

    /**
     * Limpia todos los recursos de video para evitar audio en segundo plano
     */
    private void limpiarRecursosVideo() {
        try {
            System.out.println("Iniciando limpieza de recursos de video...");
            
            // Detener timers de video
            if (timerVideoSaludos != null) {
                timerVideoSaludos.stop();
                timerVideoSaludos = null;
                System.out.println("Timer de video saludos detenido");
            }
            if (timerVideoFonologia != null) {
                timerVideoFonologia.stop();
                timerVideoFonologia = null;
                System.out.println("Timer de video fonología detenido");
            }
            if (timerVideoPronombres != null) {
                timerVideoPronombres.stop();
                timerVideoPronombres = null;
                System.out.println("Timer de video pronombres detenido");
            }
            
            // Detener y liberar recursos de video completamente
            if (controladorVideo != null) {
                try {
                    controladorVideo.detenerYLiberarRecursos();
                    System.out.println("Recursos de controlador de video liberados");
                } catch (Exception e) {
                    System.err.println("Error al liberar controlador de video: " + e.getMessage());
                } finally {
                    controladorVideo = null;
                }
            }
            
            // Resetear estados
            videoCompletado = false;
            sonidoActivado = true;
            
            System.out.println("Limpieza de recursos de video completada");
            
        } catch (Exception e) {
            System.err.println("Error durante la limpieza de recursos de video: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void liberarRecursos() {
        limpiarRecursosVideo();
    }

    private void actualizarInterfaz() {
        System.out.println("Actualizando interfaz - regresando a Unidad 1");

        // Crear nueva instancia de la vista de unidad
        Vista_Unidad1 vistaUnidad1 = new Vista_Unidad1();
        new Controlador_Unidad1(vistaUnidad1, conn, controladorDashboard, correo, controladorUnidades);
        controladorDashboard.getVista().mostrarVista(vistaUnidad1);
    }

    /**
     * Método para avanzar entre páginas de las lecciones
     */
    private void avanzarPagina() {
        paginaActual++;
        
        if (vistaLeccion instanceof Vista_LeccionSALUDOS) {
            avanzarPaginaSaludos();
        } else if (vistaLeccion instanceof Vista_LeccionFONOLOGIA) {
            avanzarPaginaFonologia();
        } else if (vistaLeccion instanceof Vista_LeccionPRONOMBRES) {
            avanzarPaginaPronombres();
        }
    }
    
    private void avanzarPaginaSaludos() {
        Vista_LeccionSALUDOS vista = (Vista_LeccionSALUDOS) vistaLeccion;
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedor.getLayout();
        
        switch (paginaActual) {
            case 2:
                inicializarPagina2();
                break;
            case 3:
                inicializarPagina3();
                break;
            default:
                System.out.println("Página no válida: " + paginaActual);
                break;
        }
    }
    
    private void avanzarPaginaFonologia() {
        Vista_LeccionFONOLOGIA vista = (Vista_LeccionFONOLOGIA) vistaLeccion;
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedorF.getLayout();
        
        switch (paginaActual) {
            case 2:
                // Mostrar página 2 de fonología
                cardLayout.show(vista.jPanelContenedorF, PAGINA_2);
                vista.jButtonSiguientePag3F.setEnabled(true);
                break;
            case 3:
                // Mostrar página 3 de fonología
                cardLayout.show(vista.jPanelContenedorF, PAGINA_3);
                vista.jButtonCOMPLETOFONOLOGIA.setEnabled(true);
                break;
            default:
                System.out.println("Página no válida para fonología: " + paginaActual);
                break;
        }
    }
    
    private void avanzarPaginaPronombres() {
        Vista_LeccionPRONOMBRES vista = (Vista_LeccionPRONOMBRES) vistaLeccion;
        CardLayout cardLayout = (CardLayout) vista.jPanelContenedorP.getLayout();
        
        switch (paginaActual) {
            case 2:
                // Mostrar página 2 de pronombres
                cardLayout.show(vista.jPanelContenedorP, PAGINA_2);
                vista.jButtonSiguientePag3.setEnabled(true);
                break;
            case 3:
                // Mostrar página 3 de pronombres
                cardLayout.show(vista.jPanelContenedorP, PAGINA_3);
                vista.jButtonCOMPLETOPRONOMBRES.setEnabled(true);
                break;
            default:
                System.out.println("Página no válida para pronombres: " + paginaActual);
                break;
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

        int totalLecciones = 3;
        int totalActividades = 2;

        double porcentajeLecciones = (double) progreso.getLeccionesCompletadas() / totalLecciones * 100;
        double porcentajeActividades = (double) progreso.getActividadesCompletadas() / totalActividades * 100;
        double porcentajeTotal = (porcentajeLecciones + porcentajeActividades) / 2;

        StringBuilder estadisticas = new StringBuilder();
        estadisticas.append("=== ESTADÍSTICAS DE PROGRESO ===\n");
        estadisticas.append("Usuario: ").append(correo).append("\n");
        estadisticas.append("Unidad: ").append(ID_UNIDAD).append("\n\n");
        estadisticas.append("Lecciones completadas: ").append(progreso.getLeccionesCompletadas())
                .append("/").append(totalLecciones).append(" (")
                .append(String.format("%.1f", porcentajeLecciones)).append("%)\n");
        estadisticas.append("Actividades completadas: ").append(progreso.getActividadesCompletadas())
                .append("/").append(totalActividades).append(" (")
                .append(String.format("%.1f", porcentajeActividades)).append("%)\n");
        estadisticas.append("Progreso total: ").append(String.format("%.1f", porcentajeTotal)).append("%\n\n");

        // Información adicional
        // Reemplaza con:
        estadisticas.append("Fecha última actividad: ").append(progreso.getFechaActualizacion()).append("\n");
// estadisticas.append("Tiempo total dedicado: ").append(progreso.getTiempoTotalMinutos()).append(" minutos\n"); // Comentada
        // estadisticas.append("Fecha última actividad: ").append(progreso.getFechaUltimaActividad()).append("\n");
        //estadisticas.append("Tiempo total dedicado: ").append(progreso.getTiempoTotalMinutos()).append(" minutos\n");

        return estadisticas.toString();
    }

    // Método para verificar si una lección específica está completada
    public boolean isLeccionCompletada(int numeroLeccion) {
        Modelo_Progreso_Usuario progreso = ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);
        return progreso.getLeccionesCompletadas() >= numeroLeccion;
    }

    // Método para obtener el progreso actual del usuario
    public Modelo_Progreso_Usuario obtenerProgresoActual() {
        return ControladorProgresoUsuario.obtenerProgreso(idUsuario, ID_UNIDAD);
    }

    // Método para reiniciar el progreso de la lección actual
    public void reiniciarProgreso() {
        try {
            int respuesta = JOptionPane.showConfirmDialog(
                    vistaLeccion,
                    "¿Estás seguro de que quieres reiniciar el progreso de esta lección?",
                    "Confirmar reinicio",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (respuesta == JOptionPane.YES_OPTION) {
                // Resetear estados
                videoCompletado = false;
                contenidoVisualizadoPag2 = false;
                contenidoVisualizadoPag3 = false;
                paginaActual = 1;

                // Reinicializar página 1
                inicializarPagina1();

                JOptionPane.showMessageDialog(
                        vistaLeccion,
                        "El progreso de la lección ha sido reiniciado.",
                        "Progreso reiniciado",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        } catch (Exception e) {
            System.err.println("Error al reiniciar progreso: " + e.getMessage());
            JOptionPane.showMessageDialog(
                    vistaLeccion,
                    "Error al reiniciar el progreso: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Método para validar que todos los componentes están listos
    private boolean validarComponentes() {
        boolean valido = true;
        StringBuilder errores = new StringBuilder();

        if (vistaLeccion == null) {
            errores.append("- Vista de lección no inicializada\n");
            valido = false;
        }

        if (conn == null) {
            errores.append("- Conexión a base de datos no disponible\n");
            valido = false;
        }

        if (correo == null || correo.trim().isEmpty()) {
            errores.append("- Correo de usuario no válido\n");
            valido = false;
        }

        if (idUsuario <= 0) {
            errores.append("- ID de usuario no válido\n");
            valido = false;
        }

        if (!valido) {
            System.err.println("Errores de validación:\n" + errores.toString());
        }

        return valido;
    }

    /**
     * Método público para limpiar recursos al cerrar la lección.
     * Debe ser llamado por el controlador padre cuando se sale de la vista.
     */
    public void limpiarRecursos() {
        try {
            System.out.println("Limpiando recursos de la lección " + numeroLeccion);
            
            // Limpiar recursos de video (incluye timers y controlador)
            limpiarRecursosVideo();

            // Limpiar imágenes
            if (imagen1Original != null) {
                imagen1Original.getImage().flush();
                imagen1Original = null;
            }

            if (imagen2Original != null) {
                imagen2Original.getImage().flush();
                imagen2Original = null;
            }

            System.out.println("Recursos de lección " + numeroLeccion + " limpiados correctamente");
        } catch (Exception e) {
            System.err.println("Error al limpiar recursos de lección: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Método para forzar la limpieza de recursos de video cuando se sale de la vista.
     * Útil para evitar audio en segundo plano.
     */
    public void detenerVideoYLimpiarRecursos() {
        try {
            System.out.println("Deteniendo video y limpiando recursos de emergencia");
            limpiarRecursosVideo();
        } catch (Exception e) {
            System.err.println("Error en limpieza de emergencia: " + e.getMessage());
        }
    }

    // Getters para información del controlador
    public int getNumeroLeccion() {
        return numeroLeccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public boolean isVideoCompletado() {
        return videoCompletado;
    }

    public boolean isContenidoVisualizadoPag2() {
        return contenidoVisualizadoPag2;
    }

    public boolean isContenidoVisualizadoPag3() {
        return contenidoVisualizadoPag3;
    }
    

    /**
     * Método helper para mostrar instrucciones de lecciones con formato consistente
     */
    private void mostrarInstruccionesLeccion(String nombreLeccion, String descripcion, String instrucciones) {
        String mensaje = "<html><div style='width: 450px; padding: 20px; font-family: Arial, sans-serif;'>" +
                "<h2 style='color: #2E8B57; text-align: center; margin-bottom: 15px;'>" +
                "📖 Lección: " + nombreLeccion + "</h2>" +
                "<div style='background-color: #F0F8F0; padding: 15px; border-radius: 8px; margin-bottom: 15px;'>" +
                "<p style='font-size: 14px; line-height: 1.6; margin: 0; color: #1E6B3E;'>" +
                descripcion + "</p>" +
                "</div>" +
                "<h4 style='color: #2E8B57; margin-bottom: 10px;'>📋 Instrucciones:</h4>" +
                "<div style='font-size: 13px; line-height: 1.8; margin-left: 10px;'>" +
                instrucciones.replace("\n", "<br>") + "</div>" +
                "<div style='background-color: #E8F5E8; padding: 10px; border-radius: 8px; margin-top: 15px;'>" +
                "<p style='font-size: 12px; margin: 0; color: #1E6B3E; text-align: center;'>" +
                "💡 <b>Tip:</b> Tómate tu tiempo para absorber bien el contenido</p>" +
                "</div>" +
                "</div></html>";
        
        JOptionPane.showMessageDialog(
            vistaLeccion,
            mensaje,
            "Instrucciones - " + nombreLeccion,
            JOptionPane.INFORMATION_MESSAGE
        );
    }

}
