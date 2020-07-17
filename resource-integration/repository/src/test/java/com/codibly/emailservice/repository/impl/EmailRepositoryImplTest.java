package com.codibly.emailservice.repository.impl;

import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import com.codibly.emailservice.repository.EmailRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EmailRepositoryImplTest {

    private EmailRepository repository;
    private HashMap<Long, Email> emailStore;
    private final Email first = new Email(0L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.PENDING);
    private final Email second = new Email(1L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.SENT);

    @BeforeEach
    void setUp() {
        emailStore = new HashMap<>();
        repository = new EmailRepositoryImpl(emailStore);
    }

    @Test
    void save() {
        repository.save(first);
        assertThat(emailStore.size()).isEqualTo(1);
        assertThat(emailStore.get(0L)).isEqualTo(first);
    }

    @Test
    void updateStatus() {
        emailStore.put(first.getId(), first);
        repository.updateStatus(0L, EmailStatus.SENT);
        assertThat(emailStore.get(0L).getStatus()).isEqualTo(EmailStatus.SENT);
    }

    @Test
    void getById() {
        emailStore.put(first.getId(), first);
        Optional<Email> actual = repository.getById(0L);
        assertThat(actual.isPresent());
        assertThat(actual.get()).isEqualTo(first);
    }

    @Test
    void getPending() {
        emailStore.put(first.getId(), first);
        emailStore.put(second.getId(), second);
        Collection<Email> actual = repository.getPending();
        assertThat(actual.size()).isEqualTo(1);
        assertThat(actual).containsExactly(first);
    }

    @Test
    void getAll() {
        emailStore.put(first.getId(), first);
        emailStore.put(second.getId(), second);
        Collection<Email> actual = repository.getAll();
        assertThat(actual.size()).isEqualTo(2);
        assertThat(actual).containsExactly(first, second);
    }
}