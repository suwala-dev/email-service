package com.codibly.emailservice.api;

import com.codibly.emailservice.EmailHandler;
import com.codibly.emailservice.dto.EmailDto;
import com.codibly.emailservice.mapper.EmailDtoMapper;
import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmailControllerTest {

    private EmailController controller;

    private EmailHandler handler;

    @BeforeEach
    void setUp() {
        handler = Mockito.mock(EmailHandler.class);
        controller = new EmailController(handler);
    }

    @Test
    void createEmail() {
        EmailDto dto = new EmailDto();
        dto.setSubject("TEST");
        dto.setSender("SENDER");
        dto.setPriority(9);
        dto.setRecipients(Collections.singletonList("RECIPIENT"));
        dto.setBody("BODY");
        controller.createEmail(dto);
        verify(handler, times(1))
                .createEmail(dto.getRecipients(), dto.getSender(), dto.getSubject(), dto.getPriority(), dto.getBody());
    }

    @Test
    void sendPending() {
        controller.sendPending();
        verify(handler, times(1)).sendPending();
    }

    @Test
    void getDetails() {
        long id = 1L;
        Email email = new Email(id, Collections.emptyList(), "SENDER", "SUBJECT", 9, "BODY", EmailStatus.SENT);
        when(handler.getById(anyLong())).thenReturn(email);
        EmailDto dto = controller.getDetails(id);
        verify(handler, times(1)).getById(id);
        verifyDtoEquals(email, dto);
    }

    @Test
    void getStatus() {
        long id = 1L;
        when(handler.getStatus(anyLong())).thenReturn(EmailStatus.SENT.name());
        ResponseEntity<Map<String, Object>> status = controller.getStatus(id);
        verify(handler, times(1)).getStatus(id);
        assertEquals(status.getStatusCode(), HttpStatus.OK);
        assertEquals(status.getBody().get("status"), EmailStatus.SENT.name());
    }

    @Test
    void getAll() {
        Email email = new Email(0L, Collections.emptyList(), "SENDER", "SUBJECT", 9, "BODY", EmailStatus.SENT);
        Email email2 = new Email(1L, Collections.emptyList(), "SENDER", "SUBJECT", 9, "BODY", EmailStatus.PENDING);
        when(handler.getAll()).thenReturn(List.of(email, email2));
        Collection<EmailDto> all = controller.getAll();
        Assertions.assertThat(all).containsExactlyInAnyOrder(EmailDtoMapper.mapToDto(email), EmailDtoMapper.mapToDto(email2));
    }

    private static void verifyDtoEquals(Email expected, EmailDto actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getRecipients(), actual.getRecipients());
        assertEquals(expected.getBody(), actual.getBody());
        assertEquals(expected.getSender(), actual.getSender());
        assertEquals(expected.getPriority(), actual.getPriority());
        assertEquals(expected.getStatus().name(), actual.getStatus());
        assertEquals(expected.getSubject(), actual.getSubject());
    }
}