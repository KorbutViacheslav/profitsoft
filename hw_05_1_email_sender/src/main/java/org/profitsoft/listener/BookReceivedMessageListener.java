package org.profitsoft.listener;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.profitsoft.factory.EmailMessageFactory;
import org.profitsoft.model.BookReceivedMessage;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.service.EmailService;

/**
 * Listener for handling Kafka messages related to book receipts.
 * This listener listens to messages from the specified Kafka topic,
 * creates an email message, and sends it using the EmailService.
 */
@Component
@RequiredArgsConstructor
public class BookReceivedMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(BookReceivedMessageListener.class);

    private final EmailService emailService;
    private final EmailMessageFactory emailMessageFactory;

    /**
     * Listens for messages on the configured Kafka topic and processes them.
     *
     * @param message the received BookReceivedMessage payload
     */
    @KafkaListener(topics = "${kafka.topic.bookReceived}", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(@Payload BookReceivedMessage message) {
        logger.info("Received message: {}", message);
        try {
            EmailMessage emailMessage = emailMessageFactory.createEmailMessage(message);
            emailService.sendEmail(emailMessage);
            logger.info("Email message created and sent for BookReceivedMessage: {}", message);
        } catch (Exception e) {
            logger.error("Failed to process BookReceivedMessage: {}", message, e);
        }
    }
}
