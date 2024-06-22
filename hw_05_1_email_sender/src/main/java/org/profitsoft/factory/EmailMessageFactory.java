package org.profitsoft.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.profitsoft.model.BookReceivedMessage;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;
import org.profitsoft.template.EmailTemplate;

/**
 * Factory for creating EmailMessage instances based on BookReceivedMessage.
 * This factory uses templates to generate the subject, content, and recipients of the email.
 */
@Component
public class EmailMessageFactory {

    private static final Logger logger = LoggerFactory.getLogger(EmailMessageFactory.class);

    /**
     * Creates an EmailMessage based on the provided BookReceivedMessage.
     *
     * @param bookMessage the message containing information about the received book
     * @return a new EmailMessage instance populated with subject, content, and recipients
     */
    public EmailMessage createEmailMessage(BookReceivedMessage bookMessage) {
        logger.info("Creating EmailMessage for BookReceivedMessage: {}", bookMessage);

        String subject = EmailTemplate.createSubject(bookMessage);
        String content = EmailTemplate.createContent(bookMessage);
        String[] recipients = EmailTemplate.getRecipients();

        EmailMessage emailMessage = EmailMessage.builder()
                .subject(subject)
                .content(content)
                .recipients(recipients)
                .status(StatusMessage.SENT)
                .build();

        logger.info("EmailMessage created with subject: {}, recipients: {}", subject, (Object) recipients);

        return emailMessage;
    }
}
