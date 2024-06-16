package org.profitsoft.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.profitsoft.factory.EmailMessageFactory;
import org.profitsoft.model.BookReceivedMessage;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.service.EmailService;

@Component
@RequiredArgsConstructor
public class BookReceivedMessageListener {
    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    @KafkaListener(topics = "${kafka.topic.bookReceived}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload BookReceivedMessage message) {
        System.out.println("Received message: " + message);
        EmailMessage emailMessage = emailMessageFactory.createEmailMessage(message);
        emailService.sendEmail(emailMessage);

    }
}
