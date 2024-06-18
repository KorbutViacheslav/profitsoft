package org.profitsoft.listener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.profitsoft.factory.EmailMessageFactory;
import org.profitsoft.model.BookReceivedMessage;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;
import org.profitsoft.service.EmailService;

import java.time.Instant;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookReceivedMessageListenerTest {

    @Mock
    private EmailService emailService;

    @Mock
    private EmailMessageFactory emailMessageFactory;

    @InjectMocks
    private BookReceivedMessageListener listener;

    private BookReceivedMessage bookReceivedMessage;
    private EmailMessage emailMessage;

    @BeforeEach
    void setUp() {
        bookReceivedMessage = new BookReceivedMessage();
        bookReceivedMessage.setName("Test Book");
        bookReceivedMessage.setGenre("Fiction");
        bookReceivedMessage.setYearPublished(2022);
        bookReceivedMessage.setAuthorFirstName("John");
        bookReceivedMessage.setAuthorLastName("Doe");

        emailMessage = EmailMessage.builder()
                .subject("New Book Received")
                .content("A new book 'Test Book' by 'John Doe' has been received.")
                .recipients(new String[]{"test@example.com"})
                .status(StatusMessage.SENT)
                .attemptCount(0)
                .lastAttemptTime(Instant.now())
                .build();
    }

    @Test
    @DisplayName("Should send email when book received message is received")
    void testListenSuccessful() {
        when(emailMessageFactory.createEmailMessage(bookReceivedMessage)).thenReturn(emailMessage);

        listener.listen(bookReceivedMessage);

        verify(emailService, times(1)).sendEmail(emailMessage);
    }

    @Test
    @DisplayName("Should not send email when book received message is invalid")
    void testListenInvalidMessage() {
        bookReceivedMessage.setName(null);
        bookReceivedMessage.setAuthorFirstName(null);
        bookReceivedMessage.setAuthorLastName(null);

        listener.listen(bookReceivedMessage);

        verify(emailService, never()).sendEmail(any(EmailMessage.class));
    }
}