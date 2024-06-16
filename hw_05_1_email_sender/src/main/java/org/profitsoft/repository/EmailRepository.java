package org.profitsoft.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.profitsoft.model.EmailMessage;
import org.profitsoft.model.StatusMessage;

import java.util.List;


public interface EmailRepository extends ElasticsearchRepository<EmailMessage, String> {
    List<EmailMessage> findByStatus(StatusMessage status);
}
