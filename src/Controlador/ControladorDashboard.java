package Controlador;

import Conexion.Conexion;
import Vista.VistaJuego;
import java.awt.event.ActionEvent;
import Vista.Dashboard;
import Modelo.Juego;
import Modelo.Saludo;
import Modelo.TextoBotones;
import Modelo.Usuario; // ← IMPORTANTE
import Vista.Cuenta;
import Vista.Logins.Login;
import Vista.Vista_PanelUnidades;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.sql.Connection;


public class ControladorDashboard {

    private Dashboard vista;
    private Login loginFrame;
    private ControladorSaludo controladorSaludo;

    private Vista_PanelUnidades panelUnidades;
    private Controlador_Unidades controladorUnidades;
     private Connection conn;

    public ControladorDashboard(Dashboard vista, Login loginFrame) {
        this.vista = vista;
        this.loginFrame = loginFrame;

        // Inicializar la conexión aquí:
        
        this.conn = Conexion.conectar();
        if (this.conn == null) {
            System.err.println("Error: No se pudo establecer conexión con la base de datos");
            // Podrías lanzar una excepción o manejar el error
        }
        agregarEventos();

        // CORREGIR: Pasar los 4 parámetros correctos
        panelUnidades = new Vista_PanelUnidades();
        controladorUnidades = new Controlador_Unidades(panelUnidades, vista, this, vista.getCorreoUsuario());

        // Mostrar panel unidades por defecto
        vista.mostrarVista(panelUnidades);

        // Cargar datos del usuario
        cargarDatosUsuario(vista.getCorreoUsuario());
        //Cargar Imagen del usuario
        cargarImagenUsuario();
        //Mostrar Botones Animados
        configurarBotonesAnimados();

    }

    public Connection getConnection() {
        return conn;
    }

    private void agregarEventos() {
        vista.btnJuegos.addActionListener((ActionEvent e) -> {
            abrirVistaJuego();
        });
        vista.btnDashboard.addActionListener(e -> abrirPanelUnidades());

        vista.btnCuenta.addActionListener(e -> abrirCuenta());

        vista.btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null, "¿Deseas salir?", "Cerrar sesión", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                // Detener el timer del saludo antes de salir
                if (controladorSaludo != null) {
                    controladorSaludo.detener();
                }
                loginFrame.mostrarPanelEnPanel1(loginFrame.getPanelLoginOriginal());
                loginFrame.limpiarCampos();
            }
        });
    }

    private void abrirPanelUnidades() {
    // Usar la instancia existente que ya tiene su controlador
    vista.mostrarVista(panelUnidades);
}

    private void abrirCuenta() {
        System.out.println("Intentando abrir panel de cuenta...");
        try {
            Cuenta cuentaPanel = new Cuenta();
            System.out.println("Componentes en cuentaPanel:");
            System.out.println("btnSubirImagenURL: " + (cuentaPanel.btnSubirImagenURL != null ? "Existe" : "NULL"));
            System.out.println("jButton1SubirImagen: " + (cuentaPanel.jButton1SubirImagen != null ? "Existe" : "NULL"));

            new ControladorCuenta(cuentaPanel, vista.getCorreoUsuario(), vista);
            vista.mostrarVista(cuentaPanel);
            System.out.println("Panel de cuenta mostrado correctamente");
        } catch (Exception e) {
            System.err.println("Error al abrir cuenta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirVistaJuego() {
        VistaJuego va  = new VistaJuego();
        Conexion conexion = new Conexion();
        Juego modeloJuego = new Juego(conexion.getConexion());
        ControladorJuego controlador = new ControladorJuego(va, modeloJuego);
        vista.mostrarVista(va);
    }

    public void limpiarControladorSaludo() {
        if (controladorSaludo != null) {
            controladorSaludo.detener();
            controladorSaludo = null;
        }
    }

    private void cargarDatosUsuario(String correo) {
        System.out.println("Correo recibido: " + correo); // Verificación
        String[] datos = Usuario.obtenerNombreYRol(correo);
        if (datos[0] != null && datos[1] != null) {
            vista.setNombreUsuario(datos[0]);
            System.out.println("Nombre recibido para etiqueta: '" + datos[0] + "'");
            vista.setRolUsuario(datos[1]);

        }
        if (controladorSaludo != null) {
            controladorSaludo.detener(); // Detener timer anterior si existe
        }

        Saludo saludo = new Saludo("Bienvenido");
        controladorSaludo = new ControladorSaludo(
                vista.getLblSaludo(),
                vista.getLblNombre(),
                saludo
        );
        controladorSaludo.inicializarVista();
    }

    private void cargarImagenUsuario() {
        String rutaImagen = Usuario.obtenerImagenUsuario(vista.getCorreoUsuario());
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            actualizarImagenPerfil(rutaImagen);
        }
    }

    public void actualizarImagenPerfil(String ruta) {
        try {
            BufferedImage img;
            if (ruta.startsWith("http")) {
                img = ImageIO.read(new URL(ruta));
            } else {
                img = ImageIO.read(new File(ruta));
            }

            int size = 90;
            BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = circleBuffer.createGraphics();
            Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
            g2.setClip(circle);
            g2.drawImage(img, 0, 0, size, size, null);
            g2.dispose();

            vista.LblimagenPrincipal.setIcon(new ImageIcon(circleBuffer));
            vista.LblimagenPrincipal.revalidate();
            vista.LblimagenPrincipal.repaint();

        } catch (Exception ex) {
            System.err.println("Error cargando imagen en Dashboard: " + ex.getMessage());
        }
    }
    //  //Todos los botones del estudiante

    private void configurarBotonesAnimados() {
        Map<String, JButton> botonesEstudiante = new HashMap<>();
        botonesEstudiante.put("btnDashboard", vista.getBtnDashboard());
        botonesEstudiante.put("btnCuenta", vista.getBtnCuenta());
        botonesEstudiante.put("btnCertificado", vista.getBtnCertificado());
        botonesEstudiante.put("btnJuegos", vista.getBtnJuegos());
        botonesEstudiante.put("btnSalir", vista.getBtnSalir());
        //Todos los botones del estudiante

        TextoBotones textos = new TextoBotones(); // Tu modelo con los textos
        ControladorBotones controlBotones = new ControladorBotones(botonesEstudiante, textos);
        controlBotones.iniciar();
    }
    // Getters para usar desde otros controladores

    public Vista_PanelUnidades getPanelUnidades() {
        return panelUnidades;
    }

    public Controlador_Unidades getControladorUnidades() {
        return controladorUnidades;
    }

    public Dashboard getVista() {
        return vista;
    }
}
