package org.profitsoft.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@ToString
@Jacksonized
public class BookReceivedMessage {
    private String name;
    private String genre;
    private Integer yearPublished;
    private String authorFirstName;
    private String authorLastName;
}
