package com.banco.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmail(EmailDTO emailDTO) {
        String subject = "Depósito realizado:" + emailDTO.fechaDeDeposito();
        String message = String.format(
            "Estimado/a %s, se ha realizado un depósito de %.2f en su cuenta %s. Su nuevo saldo es %.2f.",
            emailDTO.nombre(), emailDTO.monto(), emailDTO.cedula(), emailDTO.saldoAnterior() + emailDTO.monto()
        );

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailDTO.correo());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}