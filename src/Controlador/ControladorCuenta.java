package Controlador;

import Modelo.Usuario;
import Modelo.UsuarioPerfil;
import Vista.Cuenta;
import Conexion.Conexion;
import Modelo.EmailSender;
import Vista.Dashboard;
import Vista.DashboardAdmin;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class ControladorCuenta {

    private final Cuenta vista;
    private final Connection conn;
    private UsuarioPerfil usuario;
    private final String correoUsuario;
    private final Dashboard dashboard; //Para vista estudiante
    private DashboardAdmin dashboardAdmin; // Para vista admin

    //Constructor para vista estudiante dashboard normal
    public ControladorCuenta(Cuenta vista, String correo, Dashboard dashboard) {
        this.vista = vista;
        this.conn = Conexion.conectar();
        this.correoUsuario = correo;
        this.dashboard = dashboard;
        this.dashboardAdmin = null; // No se usa en este caso
        cargarDatosUsuario(correo);
        inicializarEventos();
    }
// Constructor para DashboardAdmin

    public ControladorCuenta(Cuenta vista, String correo, DashboardAdmin dashboardAdmin) {
        this.vista = vista;
        this.conn = Conexion.conectar();
        this.correoUsuario = correo;
        this.dashboardAdmin = dashboardAdmin;
        this.dashboard = null; // No se usa en este caso
        cargarDatosUsuario(correo);
        inicializarEventos();
    }

    private void cargarDatosUsuario(String correo) {
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT correo, ruta_imagen, fecha_registro, ultima_sesion "
                    + "FROM usuarios WHERE correo = ?"
            );
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario = new UsuarioPerfil(
                        rs.getString("correo"),
                        rs.getString("ruta_imagen"),
                        rs.getTimestamp("fecha_registro"),
                        rs.getTimestamp("ultima_sesion")
                );

                if (vista.jTextField1MostrarUsuario != null) {
                    vista.jTextField1MostrarUsuario.setText(usuario.getCorreo());
                    vista.jTextField1MostrarUsuario.setEditable(false);
                }

                if (vista.jTextField1Contraseña1 != null) {
                    vista.jTextField1Contraseña1.setText("********");
                    vista.jTextField1Contraseña1.setEditable(false);
                }

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

                if (vista.jLabel1FechayHora1 != null) {
                    vista.jLabel1FechayHora1.setText(dtf.format(LocalDateTime.now()));
                }

                Timestamp registro = usuario.getFechaRegistro();

                if (vista.jLabel1loginactivity != null) {
                    vista.jLabel1loginactivity.setText(new SimpleDateFormat("dd/MM/yyyy").format(registro));
                }

                Timestamp ultimoAcceso = usuario.getUltimaSesion();
                if (ultimoAcceso != null && vista.jLabel1loginactivity != null) {
                    LocalDateTime ldt = ultimoAcceso.toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime();
                    vista.jLabel1loginactivity.setText(vista.jLabel1loginactivity.getText()
                            + " | Último acceso: " + dtf.format(ldt));
                }

                mostrarImagen(usuario.getRutaImagen());
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al cargar datos: " + e.getMessage());
        }
    }

    private void mostrarImagen(String ruta) {
        try {
            // Usar Lblimagen en lugar de labelImagenPerfil
            if (vista.Lblimagen == null) {
                System.out.println("Lblimagen es null");
                return;
            }

            if (ruta == null || ruta.isEmpty()) {
                cargarImagenPorDefecto();
                return;
            }

            BufferedImage img;
            if (ruta.startsWith("http")) {
                img = ImageIO.read(new URL(ruta));
            } else {
                img = ImageIO.read(new File(ruta));
            }

            BufferedImage circleBuffer = crearImagenCircular(img);
            vista.Lblimagen.setIcon(new ImageIcon(circleBuffer));
            System.out.println("Imagen mostrada en Lblimagen");

        } catch (Exception ex) {
            System.err.println("Error al mostrar imagen: " + ex.getMessage());
            cargarImagenPorDefecto();
        }
    }

    private BufferedImage crearImagenCircular(BufferedImage img) {
        int size = 120;
        BufferedImage circleBuffer = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = circleBuffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D.Double circle = new Ellipse2D.Double(0, 0, size, size);
        g2.setClip(circle);
        g2.drawImage(img, 0, 0, size, size, null);
        g2.dispose();
        return circleBuffer;
    }

    private void cargarImagenPorDefecto() {
        try {
            // Usar Lblimagen en lugar de labelImagenPerfil
            if (vista.Lblimagen == null) {
                System.out.println("Lblimagen es null en cargarImagenPorDefecto");
                return;
            }

            ImageIcon defaultIcon = new ImageIcon(getClass().getResource("/Imagenes/Users.png"));
            // Verificar si la imagen se cargó correctamente
            if (defaultIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                System.err.println("No se pudo cargar la imagen por defecto");
                return;
            }

            BufferedImage buffered = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = buffered.createGraphics();
            defaultIcon.paintIcon(null, g2, 0, 0);
            g2.dispose();

            BufferedImage circleBuffer = crearImagenCircular(buffered);
            vista.Lblimagen.setIcon(new ImageIcon(circleBuffer));
            System.out.println("Imagen por defecto cargada en Lblimagen");
        } catch (Exception e) {
            System.err.println("Error cargando imagen por defecto: " + e.getMessage());
        }
    }

    private void inicializarEventos() {
        if (vista.jButton1SubirImagen != null) {
            vista.jButton1SubirImagen.addActionListener(e -> mostrarMenuSubirImagen());
        }

        if (vista.jButton1CambiarContraseña != null) {
            vista.jButton1CambiarContraseña.addActionListener(e -> cambiarContraseña());
        }
    }

    private void mostrarMenuSubirImagen() {
        Object[] opciones = {"Subir archivo local", "Ingresar URL de imagen"};
        int eleccion = JOptionPane.showOptionDialog(
                vista,
                "Seleccione cómo desea subir la imagen",
                "Subir imagen de perfil",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (eleccion == 0) {
            seleccionarImagenArchivo();
        } else if (eleccion == 1) {
            ingresarURLImagen();
        }
    }

    private void seleccionarImagenArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.addChoosableFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (*.jpg, *.jpeg, *.png)", "jpg", "jpeg", "png"));

        int resultado = fileChooser.showOpenDialog(vista);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            actualizarImagen(file.getAbsolutePath());
        }
    }

    private void ingresarURLImagen() {
        String url = JOptionPane.showInputDialog(vista, "Ingrese la URL de la imagen:");
        if (url != null && !url.trim().isEmpty()) {
            actualizarImagen(url);
        }
    }

    private void actualizarImagen(String nuevaRuta) {
        try {
            Usuario.actualizarImagenUsuario(correoUsuario, nuevaRuta);
            usuario.setRutaImagen(nuevaRuta);
            mostrarImagen(nuevaRuta);

            if (dashboard != null) {
                actualizarImagenEnDashboard(nuevaRuta);
            }

            JOptionPane.showMessageDialog(vista,
                    "¡Imagen actualizada correctamente en todos los paneles!",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista,
                    "Error al actualizar imagen: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarImagenEnDashboard(String ruta) {
    try {
        if (dashboard != null && dashboard.LblimagenPrincipal != null) {
            // Lógica para Dashboard normal
            BufferedImage img = cargarImagenDesdeRuta(ruta);
            BufferedImage circleBuffer = crearImagenCircular(img);
            dashboard.LblimagenPrincipal.setIcon(new ImageIcon(circleBuffer));
        } 
        else if (dashboardAdmin != null && dashboardAdmin.LblimagenPrincipal != null) {
            // Lógica para DashboardAdmin
            BufferedImage img = cargarImagenDesdeRuta(ruta);
            BufferedImage circleBuffer = crearImagenCircular(img);
            dashboardAdmin.LblimagenPrincipal.setIcon(new ImageIcon(circleBuffer));
        }
    } catch (Exception ex) {
        System.err.println("Error al actualizar imagen en Dashboard: " + ex.getMessage());
    }
}

private BufferedImage cargarImagenDesdeRuta(String ruta) throws Exception {
    if (ruta.startsWith("http")) {
        return ImageIO.read(new URL(ruta));
    } else {
        return ImageIO.read(new File(ruta));
    }
}

    private void cambiarContraseña() {
        String nueva = JOptionPane.showInputDialog(vista, "Ingrese nueva contraseña (mínimo 6 caracteres):");
        if (nueva != null) {
            if (nueva.length() >= 6) {
                try {
                    String actual = Usuario.getContrasenaActual(correoUsuario);
                    if (nueva.equals(actual)) {
                        JOptionPane.showMessageDialog(vista,
                                "La nueva contraseña no puede ser igual a la actual",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    PreparedStatement ps = conn.prepareStatement(
                            "UPDATE usuarios SET contrasena = ? WHERE correo = ?"
                    );
                    ps.setString(1, nueva);
                    ps.setString(2, correoUsuario);

                    int resultado = ps.executeUpdate();
                    if (resultado > 0) {
                        String asunto = "Contraseña actualizada";
                        String mensaje = "Su nueva contraseña es: " + nueva;
                        EmailSender.enviarCorreo(correoUsuario, asunto, mensaje);

                        JOptionPane.showMessageDialog(vista,
                                "¡Contraseña actualizada y enviada a su correo!",
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        if (vista.jTextField1Contraseña1 != null) {
                            vista.jTextField1Contraseña1.setText("********");
                        }
                    } else {
                        JOptionPane.showMessageDialog(vista,
                                "No se pudo actualizar la contraseña",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(vista,
                            "Error al cambiar contraseña: " + e.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(vista,
                        "La contraseña debe tener al menos 6 caracteres",
                        "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
