package org.profitsoft.factory;

import org.springframework.stereotype.Component;
import org.profitsoft.model.BookReceivedMessage;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;
import org.profitsoft.template.EmailTemplate;


@Component
public class EmailMessageFactory {
    public EmailMessage createEmailMessage(BookReceivedMessage bookMessage) {
        String subject = EmailTemplate.createSubject(bookMessage);
        String content = EmailTemplate.createContent(bookMessage);
        String[] recipients = EmailTemplate.getRecipients();

        return EmailMessage.builder()
                .subject(subject)
                .content(content)
                .recipients(recipients)
                .status(StatusMessage.SENT)
                .build();
    }
}
