package org.profitsoft.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

@Document(indexName = "email-messages")
@Getter
@Setter
public class EmailMessage {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Text)
    private String recipient;

    @Field(type = FieldType.Text)
    private String status;

    @Field(type = FieldType.Text)
    private String errorMessage;

    @Field(type = FieldType.Date)
    private LocalDateTime lastAttemptTime;

    @Field(type = FieldType.Integer)
    private int attemptCount;

}