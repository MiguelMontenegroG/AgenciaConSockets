package Proyecto.model;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;


public class Correos {
    public static String generarCombinacion() {
        // Caracteres disponibles para la combinación
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        // Generar una combinación de 4 caracteres
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(index));
        }
        codigo = String.valueOf(sb);
        return sb.toString();
    }

    static String codigo;

    public static void recolectorEnCorreos (String aux){;
    }

    public static void EnviarCorreoComprobacion(String correo, String codigo){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("agenciaviajes056@gmail.com", "ccawlcnxpcngmngj");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo, true));
            message.setSubject("Codigo de verificación");
            message.setText("Hola, su código de verificación es: " + codigo);
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException me) {
            System.out.println("Exception: " + me);
        }
    }

    public static void EnviarCorreoReserva(String correo){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("agenciaviajes056@gmail.com", "ccawlcnxpcngmngj");
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo, true));
            message.setSubject("Prueba 3");
            message.setText("Su registro fue exitoso");
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        }catch (MessagingException me){
            System.out.println("Exception: "+me);

        }
    }

    public static void EnviarCorreoCuentaCreada(String correo, String nombre){
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("agenciaviajes056@gmail.com", "ccawlcnxpcngmngj");
            }
        });

        try{
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo, true));
            message.setSubject("Cuenta Creada");
            message.setText("¡Hola "+ nombre + ", su cuenta a sido creada con exito!");
            System.out.println("sending...");
            Transport.send(message);
            System.out.println("Sent message successfully....");

        }catch (MessagingException me){
            System.out.println("Exception: "+me);

        }
    }
}
