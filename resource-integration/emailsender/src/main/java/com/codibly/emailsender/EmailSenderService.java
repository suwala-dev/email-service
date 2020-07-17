package com.codibly.emailsender;

import com.codibly.emailservice.model.Email;

import javax.mail.MessagingException;

/**
 * Service providing email sending abilities.
 */
public interface EmailSenderService {
    /**
     * Sends given message using credentials provided in application properties.
     *
     * @param email message to send
     * @throws MessagingException
     */
    void sendEmail(Email email) throws MessagingException;
}
