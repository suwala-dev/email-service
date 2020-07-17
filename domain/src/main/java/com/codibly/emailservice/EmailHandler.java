package com.codibly.emailservice;

import com.codibly.emailservice.model.Email;

import java.util.Collection;

public interface EmailHandler {

    void createEmail(Collection<String> recipients, String sender, String subject, int priority, String body);

    Collection<Email> sendPending();

    Email getById(Long id);

    String getStatus(Long id);

    Collection<Email> getAll();
}
