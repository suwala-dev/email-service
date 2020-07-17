package com.codibly.emailservice.orchestrator.impl;

import com.codibly.emailsender.EmailSenderService;
import com.codibly.emailservice.EmailHandler;
import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import com.codibly.emailservice.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

@Service
public class EmailHandlerImpl implements EmailHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailHandlerImpl.class);

    private final EmailRepository repository;

    private final EmailSenderService senderService;

    @Autowired
    public EmailHandlerImpl(EmailRepository repository, EmailSenderService senderService) {
        this.repository = repository;
        this.senderService = senderService;
    }

    @Override
    public void createEmail(Collection<String> recipients, String sender, String subject, int priority, String body) {
        LOGGER.info("Creating new email with [recipients={}, sender={}, subject={}, priority={}, body={}].", recipients, sender, subject, priority, body);
        Email email = new Email(null, recipients, sender, subject, priority, body, EmailStatus.PENDING);
        repository.save(email);
    }

    @Override
    public Collection<Email> sendPending() {
        LOGGER.info("Sending pending messages.");
        Collection<Email> pending = repository.getPending();
        Collection<Email> failed = new ArrayList<>(pending.size());
        for (Email email : pending) {
            boolean sent = true;
            try {
                senderService.sendEmail(email);
            } catch (MessagingException e) {
                LOGGER.error("Failed to send [{}].", email);
                sent = false;
                failed.add(email);
            } finally {
                if (sent) {
                    repository.updateStatus(email.getId(), EmailStatus.SENT);
                }
            }
        }
        return failed;
    }

    @Override
    public Email getById(Long id) {
        LOGGER.info("Retrieving message with [id={}].", id);
        return repository.getById(id).orElseThrow(() -> new NoSuchElementException(String.format("No email with [id=%s].", id)));
    }

    @Override
    public String getStatus(Long id) {
        LOGGER.info("Retrieving status of message with [id={}].", id);
        return repository.getById(id).orElseThrow(() -> new NoSuchElementException(String.format("No email with [id=%s].", id))).getStatus().name();
    }

    @Override
    public Collection<Email> getAll() {
        LOGGER.info("Retrieving all messages");
        return repository.getAll();
    }
}
