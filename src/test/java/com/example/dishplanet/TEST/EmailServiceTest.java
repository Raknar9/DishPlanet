package com.example.dishplanet.TEST;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;

import com.example.dishplanet.servicios.EmailService;
import jakarta.mail.internet.MimeMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;


public class EmailServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @Test
    public void testSendEmailWithAttachment() throws Exception {
        // Datos de prueba
        String to = "test@ejemplo.com";
        String subject = "asunto de prueba";
        String text = "texto de prueba";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write("PDF content".getBytes());

        // Se ejecuta la funcion para probarlo
        emailService.sendEmailWithAttachment(to, subject, text, baos);


        verify(mailSender).createMimeMessage();
        verify(mailSender).send(mimeMessage);


    }
}



