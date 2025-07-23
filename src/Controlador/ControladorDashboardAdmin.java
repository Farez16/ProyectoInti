package Controlador;

import Modelo.Saludo;
import Vista.Admin.DashboardAdmin;
import Vista.Login.Login;
import Modelo.Usuario;
import Conexion.Conexion;
import javax.swing.JOptionPane;
import Controlador.ControladorSaludo;
import Modelo.Grafico;
import Modelo.MostrarDatosAdmin;
import Modelo.TextoBotones;
import Vista.Admin.Cuenta;
import Vista.Admin.Graficos;
import Vista.Admin.RegistrarAdmin;
import Vista.Admin.VistaMostrarAdmin;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ControladorDashboardAdmin {

    private DashboardAdmin vista;
    private Login loginFrame;
    private ControladorSaludo controladorSaludo;

    public ControladorDashboardAdmin(DashboardAdmin vista, Login loginFrame) {
        this.vista = vista;
        this.loginFrame = loginFrame;
        agregarEventos();
        cargarDatosUsuario(vista.getCorreoUsuario()); // Carga nombre y rol
        //Cargar Imagen del usuario
        cargarImagenUsuario();
        //Mostrar Botones Animados
        configurarBotonesAnimados();
        // Mostrar VistaMostrarAdmin al iniciar
        mostrarVistaInicial();

        // Configuración inicial
        vista.getPanelVistas().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        // Carga diferida
        javax.swing.SwingUtilities.invokeLater(() -> {
            mostrarVistaInicial();
        });
    }

    private void agregarEventos() {
        vista.btnGraficos1.addActionListener(e -> AbrirVistaGraficos());
        //Boton para abrir el panel de inicio
        vista.btnMenu1.addActionListener(e -> AbrirVistaInicio());
        //Boton para abrir el panel de Registrar Administradores
        vista.btnCrearAdmin.addActionListener(e -> AbrirVistaRegistrar());
        //Boton para abrir el panel de la Cuenta
        vista.btnCuenta1.addActionListener(e -> abrirCuenta());
        // Botón Salir (igual que en Dashboard)
        vista.btnSalir1.addActionListener(e -> {
            
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "¿Deseas cerrar sesión?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                limpiarControladorSaludo();
                loginFrame.mostrarPanelEnPanel1(loginFrame.getPanelLoginOriginal());
                loginFrame.limpiarCampos();
            }
        });
    }
private void AbrirVistaGraficos(){
        Graficos graficos= new Graficos();
        Conexion conexion = new Conexion();
        Grafico grafico = new Grafico(conexion.getConexion());
        ControladorGrafico controlador = new ControladorGrafico(graficos, grafico);
        vista.mostrarVista(graficos);
    }
    private void mostrarVistaInicial() {
        VistaMostrarAdmin vistaInicial = new VistaMostrarAdmin();
        Conexion conexion = new Conexion();
        MostrarDatosAdmin modelo = new MostrarDatosAdmin(conexion.getConexion());
        ControladorMostrar controlador = new ControladorMostrar(vistaInicial, modelo);
        vista.mostrarVista(vistaInicial);
    }

    private void AbrirVistaInicio() {
        VistaMostrarAdmin vistaInicio = new VistaMostrarAdmin();
        Conexion conexion = new Conexion();
        MostrarDatosAdmin modelo = new MostrarDatosAdmin(conexion.getConexion());
        ControladorMostrar controlador = new ControladorMostrar(vistaInicio, modelo);
        vista.mostrarVista(vistaInicio);
    }

    private void AbrirVistaRegistrar() {
        RegistrarAdmin va  = new RegistrarAdmin();
        Conexion conexion = new Conexion();
        Usuario modeloUsuario = new Usuario(conexion.getConexion());
        ControladorRegistrarAdmin controlador = new ControladorRegistrarAdmin(va, modeloUsuario);
        vista.mostrarVista(va);
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

    private void cargarDatosUsuario(String correo) {
        String[] datos = Usuario.obtenerNombreYRol(correo);
        if (datos[0] != null && datos[1] != null) {
            vista.setNombreUsuario(datos[0]); // Asigna nombre al JLabel
            vista.setRolUsuario(datos[1]);

            // Configurar saludo
            if (controladorSaludo != null) {
                controladorSaludo.detener();
            }
            Saludo saludo = new Saludo("Bienvenido, Administrador"); // Mensaje personalizado
            controladorSaludo = new ControladorSaludo(
                    vista.getLblSaludo(),
                    vista.getLblNombre(),
                    saludo
            );
            controladorSaludo.inicializarVista();
        }
    }

    public void limpiarControladorSaludo() {
        if (controladorSaludo != null) {
            controladorSaludo.detener();
            controladorSaludo = null;
        }
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
    // //Agregar todos los botones del admin

    private void configurarBotonesAnimados() {
        Map<String, JButton> botonesAdmin = new HashMap<>();
        botonesAdmin.put("btnMenu1", vista.getBtnCuenta1());
        botonesAdmin.put("btnCuenta1", vista.getBtnCuenta1());
        botonesAdmin.put("btnGraficos1", vista.getBtnGraficos1());
        botonesAdmin.put("btnCrearAdmin", vista.getBtnCrearAdmin());
        botonesAdmin.put("btnSalir1", vista.getBtnSalir1());

        TextoBotones textos = new TextoBotones();
        ControladorBotones controlBotones = new ControladorBotones(botonesAdmin, textos);
        controlBotones.iniciar();
    }
}
