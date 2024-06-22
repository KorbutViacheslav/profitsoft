package org.profitsoft.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;
import org.profitsoft.repository.EmailRepository;

import java.time.Instant;

/**
 * Service for handling email sending operations.
 * This service uses a JavaMailSender to send emails and logs the process.
 */
@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    /**
     * Sends an email message and updates its status in the repository.
     * The method logs each step of the process for monitoring and debugging purposes.
     *
     * @param emailMessage the email message to be sent
     */
    public void sendEmail(EmailMessage emailMessage) {
        emailMessage.setAttemptCount(emailMessage.getAttemptCount() + 1);
        emailMessage.setLastAttemptTime(Instant.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.getRecipients());
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getContent());
            mailSender.send(message);
            logger.info("Email sent successfully to {}", (Object) emailMessage.getRecipients());
            emailMessage.setErrorMessage(null);
            emailMessage.setStatus(StatusMessage.SENT);
        } catch (Exception e) {
            emailMessage.setStatus(StatusMessage.ERROR);
            emailMessage.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
            logger.error("Failed to send email to {}: {}", (Object) emailMessage.getRecipients(), e.getMessage());
        } finally {
            emailRepository.save(emailMessage);
            logger.info("Email message saved with status {}", emailMessage.getStatus());
        }
    }
}
