package org.profitsoft.listener;
import lombok.AllArgsConstructor;
import org.profitsoft.repository.EmailMessageRepository;
import org.profitsoft.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.profitsoft.model.EmailMessage;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class EmailMessageListener {

    private EmailService emailService;

    private EmailMessageRepository emailMessageRepository;

    @KafkaListener(topics = "email-topic")
    public void listenEmailMessages(EmailMessage emailMessage) {
        emailMessage.setStatus("PENDING");
        emailMessage.setAttemptCount(0);
        emailMessage.setLastAttemptTime(LocalDateTime.now());
        emailMessageRepository.save(emailMessage);

        emailService.sendEmail(emailMessage);
    }
}
