package ua.profitsoft.messaging;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@JsonSerialize
public class BookReceivedMessage {
    private String name;
    private String genre;
    private Integer yearPublished;
    private String authorFirstName;
    private String authorLastName;
}
