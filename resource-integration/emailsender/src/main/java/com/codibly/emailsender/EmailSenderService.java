package com.codibly.emailsender;

import com.codibly.emailservice.model.Email;

import javax.mail.MessagingException;

public interface EmailSenderService {
    void sendEmail(Email email) throws MessagingException;
}
