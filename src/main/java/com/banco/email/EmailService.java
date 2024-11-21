package com.banco.email;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(EmailDTO emailDTO) {
        String subject = "Depósito realizado: " + emailDTO.fechaDeDeposito();
        String message = String.format(
            "<html>" +
            "<body style='font-family: Arial, sans-serif;'>" +
            "<h2>Banco Fachero</h2>" +
            "<p>Estimado/a %s,</p>" +
            "<p>Nos complace informarle que se ha realizado un depósito de <strong>%.2f</strong> en su cuenta <strong>%s</strong>.</p>" +
            "<p>Su nuevo saldo es <strong>%.2f</strong>.</p>" +
            "<p>Si tiene alguna pregunta, no dude en ponerse en contacto con nosotros.</p>" +
            "<p>Atentamente,</p>" +
            "<p>Bryam Diaz - CEO de Banco Fachero</p>" +
            "<br>" +
            "<img src='cid:logoImage' width='250' height='250'>" +
            "</body>" +
            "</html>",
            emailDTO.nombre(), emailDTO.monto(), emailDTO.cedula(), emailDTO.saldoAnterior()
        );

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(emailDTO.correo());
            helper.setSubject(subject);
            helper.setText(message, true);

            // Incluir la imagen en el cuerpo del mensaje
            ClassPathResource resource = new ClassPathResource("Logo.png");
            helper.addInline("logoImage", resource);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}