package com.codibly.emailservice;

import com.codibly.emailservice.model.Email;

import java.util.Collection;

/**
 * Service providing all required business logic related to email creation, manipulation.
 */
public interface EmailHandler {

    /**
     * Creates new email with provided values
     *
     * @param recipients collection of email addresses of recipients
     * @param sender     email address of sender
     * @param subject    subject of email
     * @param priority   value for X-Priority header
     * @param body       text of the message
     */
    void createEmail(Collection<String> recipients, String sender, String subject, int priority, String body);

    /**
     * Sends all emails with PENDING status
     *
     * @return collection of email that could not be sent
     */
    Collection<Email> sendPending();

    /**
     * Returns email with given id
     *
     * @param id id of email
     * @return email message if found
     * @throws java.util.NoSuchElementException if no email with given id was found
     */
    Email getById(Long id);

    /**
     * Returns status of email with given id
     *
     * @param id id of email
     * @return text representation of email status. Either SENT or PENDING
     */
    String getStatus(Long id);

    /**
     * Returns all emails.
     *
     * @return collection of emails stored in repository
     */
    Collection<Email> getAll();
}
