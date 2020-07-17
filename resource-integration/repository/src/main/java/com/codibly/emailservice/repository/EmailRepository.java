package com.codibly.emailservice.repository;

import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;

import java.util.Collection;
import java.util.Optional;

/**
 * Repository providing required email storing functionality.
 */
public interface EmailRepository {
    /**
     * Persists given email message in data store.
     *
     * @param email message to save
     */
    void save(Email email);

    /**
     * Updates status of given message.
     *
     * @param id     id of message
     * @param status new status of message
     */
    void updateStatus(Long id, EmailStatus status);

    /**
     * Retrieves from data store message with given id.
     *
     * @param id id of message
     * @return optional containing message if it was present or null if not found
     */
    Optional<Email> getById(Long id);

    /**
     * Retrieves from data store messages with status equal to PENDING
     *
     * @return collection of email messages
     */
    Collection<Email> getPending();

    /**
     * Retrieves all messages stored in data store.
     *
     * @return collection of email messages
     */
    Collection<Email> getAll();
}
