package com.banco.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@Component
public class Consumer {

    private final EmailService emailService;

    public Consumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = "deposit.events")
    public void receive(@Payload EmailDTO message) {
        try {
            emailService.enviarEmail(message);
        } catch (Exception e) {
            log.error("Error processing message: {}", message, e);
        }
    }
}