package com.example.dishplanet.servicios;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

/**
 * Servicio para el envío de correos electrónicos.
 */
@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Value("${spring.mail.password}")
    private String password;

    /**
     * Envía un correo electrónico simple sin adjunto.
     *
     * @param to      El destinatario del correo.
     * @param subject El asunto del correo.
     * @param text    El cuerpo del correo.
     */
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    /**
     * Envía un correo electrónico con un archivo adjunto en formato PDF.
     *
     * @param to      El destinatario del correo.
     * @param subject El asunto del correo.
     * @param text    El cuerpo del correo.
     * @param baos    El ByteArrayOutputStream que contiene los datos del archivo adjunto.
     */
    public void sendEmailWithAttachment(String to, String subject, String text, ByteArrayOutputStream baos) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.addAttachment("informe_pedidos.pdf", new ByteArrayResource(baos.toByteArray()));
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}