package com.codibly.emailservice.repository;

import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;

import java.util.Collection;
import java.util.Optional;

public interface EmailRepository {
    void save(Email email);

    void updateStatus(Long id, EmailStatus status);

    Optional<Email> getById(Long id);

    Collection<Email> getPending();

    Collection<Email> getAll();
}
