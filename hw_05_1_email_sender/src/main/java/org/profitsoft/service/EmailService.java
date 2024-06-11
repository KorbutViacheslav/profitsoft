package org.profitsoft.service;

import lombok.AllArgsConstructor;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.repository.EmailMessageRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailMessageRepository emailMessageRepository;

    public void sendEmail(EmailMessage emailMessage) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailMessage.getRecipient());
            message.setSubject(emailMessage.getContent());
            message.setText(emailMessage.getContent());

            javaMailSender.send(message);

            emailMessage.setStatus("SENT");
            emailMessageRepository.save(emailMessage);
        } catch (Exception e) {
            emailMessage.setStatus("ERROR");
            emailMessage.setErrorMessage(e.getMessage());
            emailMessageRepository.save(emailMessage);
        }
    }
}
