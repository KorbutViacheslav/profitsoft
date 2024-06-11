package org.profitsoft.repository;

import org.profitsoft.model.EmailMessage;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmailMessageRepository extends ElasticsearchRepository<EmailMessage, String> {

    List<EmailMessage> findByStatus(String status);
}