# Email Sender

* [Task issued](https://docs.google.com/document/d/1xWXnHHHnh-27W0EIVCrb97Kg8cd3cJSwO-Fzzy0vruo/edit) (Only for users
  who have access to the internship)
* A short video about how the application works [YouTube](https://www.youtube.com/watch?v=XXwX7WDxVpg)

---

## Application Description

This is a microservice built with Java and Spring Boot for sending email messages.
It uses Elasticsearch for data storage and Kafka for asynchronous message processing.

---

## Features

- Receive email message data (subject, content, recipient) from a Kafka topic
- Store email messages in an Elasticsearch index
- Send email messages using SMTP protocol
- Update email message status (SENT, ERROR) in Elasticsearch
- Retry failed email messages periodically (every 5 minutes)

---

### Usage

1. **Start project from [hw_02_book_management_application](../hw_02_book_management_application/README.md)**
2. Create a `.env` file in the project root and add the following variables:
   ```env
   MAIL_HOST=smtp.gmail.com
   MAIL_PORT=465
   MAIL_USERNAME=<your_email@gmail.com>
   MAIL_PASSWORD=<your_password>
   ```
3. Open project in the terminal within the folder containing docker-compose.yaml file and execute the following
   commands:

      ````
      docker-compose build
      docker-compose up
      ````
4. Run [app](../hw_05_1_email_sender/src/main/java/org/profitsoft/EmailSender.java).

---

## Technologies Used

- Java 17
- Spring Boot
- Elasticsearch
- Kafka
- JavaMailSender
- Docker

---

## Summary
This Java and Spring Boot-built microservice provides a robust and scalable solution for sending emails.
It utilizes Kafka for asynchronous processing, Elasticsearch for message storage and indexing, SMTP for reliable delivery, 
and a retry mechanism for automatic resending of failed messages. 
This service offers a comprehensive solution for handling email sending within your system architecture.