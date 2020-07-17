package com.codibly.emailservice;

import com.codibly.emailsender.EmailSenderService;
import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import com.codibly.emailservice.orchestrator.impl.EmailHandlerImpl;
import com.codibly.emailservice.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.mail.MessagingException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class EmailHandlerTest {

    private EmailHandler handler;

    private EmailRepository repository;

    private EmailSenderService senderService;

    @BeforeEach
    void setUp() {
        repository = mock(EmailRepository.class);
        senderService = mock(EmailSenderService.class);
        handler = new EmailHandlerImpl(repository, senderService);
    }

    @Test
    void createEmail() {
        List<String> recipients = Collections.singletonList("TEST_EMAIL");
        handler.createEmail(recipients, "SENDER", "SUBJECT", 9, "BODY");
        verify(repository, times(1)).save(new Email(null, recipients, "SENDER", "SUBJECT", 9, "BODY", EmailStatus.PENDING));
    }

    @Test
    void sendPending() throws MessagingException {
        Email first = new Email(0L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.PENDING);
        Email second = new Email(1L, Collections.singletonList("TEST2"), "SENDER2", "SUBJECT2", 9, "BODY2", EmailStatus.PENDING);
        when(repository.getPending()).thenReturn(List.of(first, second));
        doThrow(new MessagingException()).when(senderService).sendEmail(second);
        Collection<Email> emails = handler.sendPending();
        verify(repository, times(1)).getPending();
        verify(senderService, times(2)).sendEmail(any());
        assertThat(emails.size()).isEqualTo(1);
        assertThat(emails).containsExactly(second);
    }

    @Test
    void getById() {
        Email first = new Email(0L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.PENDING);
        when(repository.getById(0L)).thenReturn(Optional.of(first));
        Email actual = handler.getById(0L);
        assertThat(actual).isEqualTo(first);
        verify(repository, times(1)).getById(0L);
    }

    @Test
    void getStatus() {
        Email first = new Email(0L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.PENDING);
        when(repository.getById(0L)).thenReturn(Optional.of(first));
        String actual = handler.getStatus(0L);
        assertThat(actual).isEqualTo(first.getStatus().name());
        verify(repository, times(1)).getById(0L);
    }

    @Test
    void getAll() {
        Email first = new Email(0L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.PENDING);
        Email second = new Email(1L, Collections.singletonList("TEST2"), "SENDER2", "SUBJECT2", 9, "BODY2", EmailStatus.PENDING);
        when(repository.getAll()).thenReturn(List.of(first, second));
        Collection<Email> actual = handler.getAll();
        assertThat(actual).containsExactly(first, second);
        verify(repository, times(1)).getAll();
    }
}