package org.profitsoft.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;
import org.profitsoft.repository.EmailRepository;

import java.time.Instant;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private final EmailRepository emailRepository;

    public void sendEmail(EmailMessage emailMessage) {
        emailMessage.setAttemptCount(emailMessage.getAttemptCount() + 1);
        emailMessage.setLastAttemptTime(Instant.now());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.getRecipients());
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getContent());
            mailSender.send(message);
            System.out.println("StatusMessage.SENT");
            emailMessage.setErrorMessage(null);
            emailMessage.setStatus(StatusMessage.SENT);
        } catch (Exception e) {
            emailMessage.setStatus(StatusMessage.ERROR);
            emailMessage.setErrorMessage(e.getClass().getName() + ": " + e.getMessage());
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            System.out.println(emailMessage);
            emailRepository.save(emailMessage);
        }


    }
}
