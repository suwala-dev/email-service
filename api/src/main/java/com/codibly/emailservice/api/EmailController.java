package com.codibly.emailservice.api;

import com.codibly.emailservice.EmailHandler;
import com.codibly.emailservice.dto.EmailDto;
import com.codibly.emailservice.mapper.EmailDtoMapper;
import com.codibly.emailservice.model.Email;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailController.class);

    private final EmailHandler emailHandler;

    @Autowired
    public EmailController(EmailHandler emailHandler) {
        this.emailHandler = emailHandler;
    }

    @ApiOperation(value = "Create a new email message.")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEmail(@RequestBody EmailDto dto) {
        LOGGER.info("Storing email message [{}].", dto);
        emailHandler.createEmail(dto.getRecipients(),
                dto.getSender(),
                dto.getSubject(),
                dto.getPriority(),
                dto.getBody());
    }

    @ApiOperation(value = "Send all pending email messages.")
    @PostMapping(path = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> sendPending() {
        LOGGER.info("Retrieving all pending messages.");
        Collection<Email> failed = emailHandler.sendPending();
        if (!failed.isEmpty()) {
            LOGGER.error("Some messages could not be sent. [{}]", failed);
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
        LOGGER.info("Retrieving details for message with [id={}].", id);
        return EmailDtoMapper.mapToDto(emailHandler.getById(id));
    }

    @ApiOperation(value = "Get status of email message with specified id.")
    @GetMapping(path = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getStatus(@RequestParam("id") Long id) {
        LOGGER.info("Retrieving status for message with [id={}].", id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", emailHandler.getStatus(id));
        return ResponseEntity.ok(response);
    }

    @ApiOperation(value = "Get details of all email messages.")
    @GetMapping(path = "/details/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<EmailDto> getAll() {
        LOGGER.info("Retrieving all messages.");
        return emailHandler.getAll().stream().map(EmailDtoMapper::mapToDto).collect(Collectors.toList());
    }
}


