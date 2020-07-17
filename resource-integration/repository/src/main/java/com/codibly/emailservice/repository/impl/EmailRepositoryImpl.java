package com.codibly.emailservice.repository.impl;

import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import com.codibly.emailservice.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final Map<Long, Email> emailStore;

    private Long currentId = 0L;

    @Autowired
    public EmailRepositoryImpl() {
        this.emailStore = new HashMap<>();
    }

    @Override
    public void save(Email email) {
        email.setId(currentId);
        emailStore.put(email.getId(), email);
        currentId += 1;
    }

    @Override
    public void updateStatus(Long id, EmailStatus status) {
        emailStore.get(id).setStatus(status);
    }

    @Override
    public Optional<Email> getById(Long id) {
        return Optional.ofNullable(emailStore.get(id));
    }

    @Override
    public Collection<Email> getPending() {
        return emailStore.values().stream().filter(email -> email.getStatus() == EmailStatus.PENDING).collect(Collectors.toList());
    }

    @Override
    public Collection<Email> getAll() {
        return emailStore.values();
    }
}
