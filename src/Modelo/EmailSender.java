package Modelo;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailSender {
    public static void enviarCodigo(String destinatario, String codigo) {
        final String remitente = "proyectointi1@gmail.com";
        final String password = "rbld qjyt lfop ynrl";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Tu código de acceso");
            message.setText("Tu código de acceso es: " + codigo);

            Transport.send(message);
            System.out.println("Código enviado exitosamente");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    // Nuevo: enviar correo de confirmación
    public static void enviarConfirmacion(String destinatario) {
        final String remitente = "proyectointi1@gmail.com";
        final String password = "luoh gkcn aitb gzft";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject("Inicio de sesión exitoso");
            message.setText("¡Has iniciado sesión correctamente en la aplicación!");

            Transport.send(message);
            System.out.println("Correo de confirmación enviado");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) {
    final String remitente = "proyectointi1@gmail.com";
    final String password = "rbld qjyt lfop ynrl";

    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

    try {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(cuerpo);

        Transport.send(message);
        System.out.println("Correo enviado exitosamente");

    } catch (MessagingException e) {
        e.printStackTrace();
    }
}
public static boolean enviarCorreoConAdjunto(String destinatario, String asunto, String cuerpo, String rutaAdjunto) {
    final String remitente = "proyectointi1@gmail.com";
    final String password = "rbld qjyt lfop ynrl"; // Usar contraseña real

    Properties props = new Properties();
    props.put("mail.smtp.host", "smtp.gmail.com");
    props.put("mail.smtp.port", "587");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, password);
            }
        });

    try {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(remitente));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
        message.setSubject(asunto);

        // Crear parte multipart
        Multipart multipart = new MimeMultipart();

        // Parte de texto
        MimeBodyPart textoPart = new MimeBodyPart();
        textoPart.setText(cuerpo);
        multipart.addBodyPart(textoPart);

        // Parte adjunta
        MimeBodyPart adjuntoPart = new MimeBodyPart();
        FileDataSource fuente = new FileDataSource(rutaAdjunto);
        adjuntoPart.setDataHandler(new DataHandler(fuente));
        adjuntoPart.setFileName("Certificado_Kwichua.pdf");
        multipart.addBodyPart(adjuntoPart);

        message.setContent(multipart);
        Transport.send(message);
        return true;

    } catch (MessagingException e) {
        e.printStackTrace();
        return false;
    }
}
}