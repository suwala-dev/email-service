package com.codibly.emailservice.repository.impl;

import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import com.codibly.emailservice.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailRepositoryImpl.class);

    private final Map<Long, Email> emailStore;

    private Long currentId = 0L;

    @Autowired
    public EmailRepositoryImpl() {
        this.emailStore = new HashMap<>();
    }

    EmailRepositoryImpl(Map<Long, Email> emailStore) {
        this.emailStore = emailStore;
    }

    @Override
    public void save(Email email) {
        LOGGER.info("Saving email message [{}].", email);
        email.setId(currentId);
        emailStore.put(email.getId(), email);
        LOGGER.info("Message saved with [id={}].", currentId);
        currentId += 1;
    }

    @Override
    public void updateStatus(Long id, EmailStatus status) {
        LOGGER.info("Updating status of message with [id={}] to [status={}].", id, status);
        emailStore.get(id).setStatus(status);
    }

    @Override
    public Optional<Email> getById(Long id) {
        LOGGER.info("Retrieving message with [id={}].", id);
        return Optional.ofNullable(emailStore.get(id));
    }

    @Override
    public Collection<Email> getPending() {
        LOGGER.info("Retrieving all pending messages.");
        return emailStore.values().stream().filter(email -> email.getStatus() == EmailStatus.PENDING).collect(Collectors.toList());
    }

    @Override
    public Collection<Email> getAll() {
        LOGGER.info("Retrieving all messages.");
        return emailStore.values();
    }
}
