package com.codibly.emailsender;

import com.codibly.emailservice.model.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderServiceImpl.class);

    private final JavaMailSender mailSender;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(Email email) throws MessagingException {
        LOGGER.info("Sending [{}].", email);
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), false);
        for (String s : email.getRecipients()) {
            messageHelper.addTo(s);
        }
        messageHelper.setFrom(email.getSender());
        messageHelper.setPriority(email.getPriority());
        messageHelper.setSubject(email.getSubject());
        messageHelper.setText(email.getBody());
        mailSender.send(messageHelper.getMimeMessage());
    }
}
