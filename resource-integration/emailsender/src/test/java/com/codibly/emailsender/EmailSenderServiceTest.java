package com.codibly.emailsender;

import com.codibly.emailservice.model.Email;
import com.codibly.emailservice.model.EmailStatus;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;

import static org.mockito.Mockito.*;

class EmailSenderServiceTest {

    @Test
    void sendEmail() throws MessagingException {
        JavaMailSender sender = mock(JavaMailSender.class);
        MimeMessage message = mock(MimeMessage.class);
        when(sender.createMimeMessage()).thenReturn(message);
        Email first = new Email(0L, Collections.singletonList("TEST1"), "SENDER1", "SUBJECT1", 9, "BODY1", EmailStatus.PENDING);
        EmailSenderService senderService = new EmailSenderServiceImpl(sender);
        when(sender.createMimeMessage()).thenReturn(message);
        senderService.sendEmail(first);
        verify(message, times(1)).setFrom(InternetAddress.parse(first.getSender())[0]);
        verify(message, times(1)).setHeader("X-Priority", Integer.toString(first.getPriority()));
        verify(message, times(1)).setSubject(first.getSubject());
        verify(message, times(1)).setText(first.getBody());
        verify(message, times(1)).addRecipient(Message.RecipientType.TO, InternetAddress.parse("TEST1")[0]);
        verify(sender, times(1)).send(message);
    }
}