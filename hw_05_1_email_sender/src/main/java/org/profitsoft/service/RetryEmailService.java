package org.profitsoft.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;
import org.profitsoft.repository.EmailRepository;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class RetryEmailService {
    private final EmailRepository emailRepository;
    private final EmailService emailService;

    private static final int MAX_ATTEMPTS = 5;

    @Scheduled(fixedRate = 300000)
    public void retryFailedEmails() {
        log.info("Start checking unsent emails.");
        List<EmailMessage> failedEmails = emailRepository.findByStatus(StatusMessage.ERROR);

        for (EmailMessage email : failedEmails) {
            if (email.getAttemptCount() < MAX_ATTEMPTS) {
                log.info("Attempting to resend a letter with ID: {}", email.getId());
                emailService.sendEmail(email);
            } else {
                log.warn("The email with ID: {} has reached the maximum number of attempts and will not be resent.", email.getId());
                email.setStatus(StatusMessage.FAILED);
                email.setLastAttemptTime(Instant.now());
                emailRepository.save(email);
            }
        }
        log.info("Finish checking unsent emails.");
    }

}
