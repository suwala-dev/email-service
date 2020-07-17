package com.codibly.emailservice.mapper;

import com.codibly.emailservice.dto.EmailDto;
import com.codibly.emailservice.model.Email;

public class EmailDtoMapper {

    public static EmailDto mapToDto(Email email) {
        EmailDto emailDto = new EmailDto();
        emailDto.setStatus(email.getStatus().name());
        emailDto.setBody(email.getBody());
        emailDto.setId(email.getId());
        emailDto.setSubject(email.getSubject());
        emailDto.setRecipients(email.getRecipients());
        emailDto.setPriority(email.getPriority());
        emailDto.setSender(email.getSender());
        return emailDto;
    }
}
