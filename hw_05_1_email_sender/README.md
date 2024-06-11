# Email Sender

This is a microservice built with Java and Spring Boot for sending email messages. It uses Elasticsearch for data storage and Kafka for asynchronous message processing.

## Features

- Receive email message data (subject, content, recipient) from a Kafka topic
- Store email messages in an Elasticsearch index
- Send email messages using SMTP protocol
- Update email message status (SENT, ERROR) in Elasticsearch
- Retry failed email messages periodically (every 5 minutes)

## Technologies Used

- Java 17
- Spring Boot
- Elasticsearch
- Kafka
- JavaMailSender

## Prerequisites

- Docker
- Docker Compose