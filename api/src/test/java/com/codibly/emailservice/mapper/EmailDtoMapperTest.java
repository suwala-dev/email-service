package com.codibly.emailservice.mapper;

import com.codibly.emailservice.dto.EmailDto;
import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailDtoMapperTest {

    @Test
    void mapToDto() {
        Email expected = new Email(0L, Collections.emptyList(), "SENDER", "SUBJECT", 9, "BODY", EmailStatus.SENT);
        EmailDto actual = EmailDtoMapper.mapToDto(expected);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getRecipients(), actual.getRecipients());
        assertEquals(expected.getBody(), actual.getBody());
        assertEquals(expected.getSender(), actual.getSender());
        assertEquals(expected.getPriority(), actual.getPriority());
        assertEquals(expected.getStatus().name(), actual.getStatus());
        assertEquals(expected.getSubject(), actual.getSubject());
    }
}