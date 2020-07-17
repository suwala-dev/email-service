package com.codibly.emailservice.api;

import com.codibly.emailservice.EmailHandler;
import com.codibly.emailservice.dto.EmailDto;
import com.codibly.emailservice.mapper.EmailDtoMapper;
import com.codibly.emailservice.model.Email;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Api(value = "Email service")
@RestController
@RequestMapping("/email")
public class EmailController {

    private final EmailHandler emailHandler;

    @Autowired
    public EmailController(EmailHandler emailHandler) {
        this.emailHandler = emailHandler;
    }

    @ApiOperation(value = "Create a new email message.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEmail(@RequestBody EmailDto dto) {
        emailHandler.createEmail(dto.getRecipients(),
                dto.getSender(),
                dto.getSubject(),
                dto.getPriority(),
                dto.getBody());
    }

    @ApiOperation(value = "Send all pending email messages.")
    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> sendPending() {
        Collection<Email> failed = emailHandler.sendPending();
        if (!failed.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Could not send some messages");
            response.put("messages", failed);
            return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT).body(response);
        }
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Get details of email message with specified id.")
    @GetMapping(path = "/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmailDto getDetails(@RequestParam("id") Long id) {
        return EmailDtoMapper.mapToDto(emailHandler.getById(id));
    }

    @ApiOperation(value = "Get status of email message with specified id.")
    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getStatus(@RequestParam("id") Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", emailHandler.getStatus(id));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get details of all email messages.")
    @GetMapping(path = "/details/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<EmailDto> getAll() {
        return emailHandler.getAll().stream().map(EmailDtoMapper::mapToDto).collect(Collectors.toList());
    }
}


