package com.codibly.emailservice.dto;

import java.util.Collection;
import java.util.Objects;

public class EmailDto {

    private long id;

    private Collection<String> recipients;

    private String sender;

    private String subject;

    private int priority;

    private String body;

    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmailDto emailDto = (EmailDto) o;
        return id == emailDto.id &&
                priority == emailDto.priority &&
                Objects.equals(recipients, emailDto.recipients) &&
                Objects.equals(sender, emailDto.sender) &&
                Objects.equals(subject, emailDto.subject) &&
                Objects.equals(body, emailDto.body) &&
                Objects.equals(status, emailDto.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipients, sender, subject, priority, body, status);
    }

    @Override
    public String toString() {
        return "EmailDto{" +
                "id=" + id +
                ", recipients=" + recipients +
                ", sender='" + sender + '\'' +
                ", subject='" + subject + '\'' +
                ", priority=" + priority +
                ", body='" + body + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
