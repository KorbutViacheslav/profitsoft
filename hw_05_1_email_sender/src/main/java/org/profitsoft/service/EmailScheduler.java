package org.profitsoft.service;

import lombok.AllArgsConstructor;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.repository.EmailMessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class EmailScheduler {

    private EmailService emailService;

    private EmailMessageRepository emailMessageRepository;

    @Scheduled(fixedRate = 300000)
    public void retryFailedEmails() {
        List<EmailMessage> failedMessages = emailMessageRepository.findByStatus("ERROR");

        for (EmailMessage message : failedMessages) {
            message.setAttemptCount(message.getAttemptCount() + 1);
            message.setLastAttemptTime(LocalDateTime.now());
            emailService.sendEmail(message);
        }
    }
}