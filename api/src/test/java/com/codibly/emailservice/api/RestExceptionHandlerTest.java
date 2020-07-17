package com.codibly.emailservice.api;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;

class RestExceptionHandlerTest {

    @Test
    void handleNoSuchElement() {
        RestExceptionHandler handler = new RestExceptionHandler();
        ResponseEntity<Object> response = handler.handleNoSuchElement(new NoSuchElementException("TEST"), null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        Map<String, Object> map = (Map<String, Object>) response.getBody();
        assertThat(map.get("error")).isEqualTo(NoSuchElementException.class);
        assertThat(map.get("message")).isEqualTo("TEST");
    }
}