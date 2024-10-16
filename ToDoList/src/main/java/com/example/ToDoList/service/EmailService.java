package com.example.ToDoList.service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public void sendResetEmail(String toEmail, String resetToken) {
        // Configura las propiedades del correo
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com"); // Cambia esto según tu servidor SMTP
        properties.put("mail.smtp.port", "587"); // Cambia esto según tu servidor SMTP

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("your_email@gmail.com", "your_password"); // Cambia por tu email y contraseña
            }
        });

        try {
            // Crea el mensaje
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress("your_email@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Restablecimiento de Contraseña");
            message.setText("Para restablecer tu contraseña, haz clic en el siguiente enlace: "
                    + "http://localhost:8080/reset-password?token=" + resetToken); // Cambia esto según tu aplicación

            // Envía el mensaje
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
