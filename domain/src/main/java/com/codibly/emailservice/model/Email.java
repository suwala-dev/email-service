package com.codibly.emailservice.model;

import java.util.Collection;
import java.util.Objects;

public class Email {

    private Long id;

    private Collection<String> recipients;

    private String sender;

    private String subject;

    private int priority;

    private String body;

    private EmailStatus status;

    public Email() {
    }

    public Email(Long id, Collection<String> recipients, String sender, String subject, int priority, String body, EmailStatus status) {
        this.id = id;
        this.recipients = recipients;
        this.sender = sender;
        this.subject = subject;
        this.priority = priority;
        this.body = body;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(Collection<String> recipients) {
        this.recipients = recipients;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public EmailStatus getStatus() {
        return status;
    }

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Email{" +
                "id=" + id +
                ", recipients=" + recipients +
                ", sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", priority=" + priority +
                ", body='" + body + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return priority == email.priority &&
                Objects.equals(id, email.id) &&
                Objects.equals(recipients, email.recipients) &&
                Objects.equals(sender, email.sender) &&
                Objects.equals(subject, email.subject) &&
                Objects.equals(body, email.body) &&
                status == email.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipients, sender, subject, priority, body, status);
    }
}
