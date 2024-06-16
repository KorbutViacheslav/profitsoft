package org.profitsoft.template;

import org.profitsoft.model.BookReceivedMessage;

public class EmailTemplate {
    public static final String ADMIN_EMAIL = "korbutjava@gmail.com";

    public static String createSubject(BookReceivedMessage message) {
        return "New Book Received: " + message.getName();
    }

    public static String createContent(BookReceivedMessage message) {
        return "Book Details:\n" +
               "Name: " + message.getName() + "\n" +
               "Genre: " + message.getGenre() + "\n" +
               "Year Published: " + message.getYearPublished() + "\n" +
               "Author: " + message.getAuthorFirstName() + " " + message.getAuthorLastName();
    }

    public static String[] getRecipients() {
        return new String[]{ADMIN_EMAIL};
    }
}
