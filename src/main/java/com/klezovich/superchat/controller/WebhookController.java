package com.klezovich.superchat.controller;

import com.klezovich.superchat.controller.dto.EmailDto;
import com.klezovich.superchat.service.MessagingService;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/webhook")
@Slf4j
public class WebhookController {

    private MessagingService service;

    @Autowired
    public WebhookController(MessagingService service) {
        this.service = service;
    }

    @PostMapping("/twilio")
    public void receiveMessageFromTwilio(
            @RequestParam("To") String userPhoneNumber,
            @RequestParam("From") String contactPhoneNumber,
            @RequestParam("Body") String messageText
    ) {

        userPhoneNumber = decode(userPhoneNumber);
        contactPhoneNumber = decode(contactPhoneNumber);
        messageText = decode(messageText);

        service.receiveSmsFromContactByUser(userPhoneNumber, contactPhoneNumber, messageText);
    }

    @PostMapping("/email")
    public void receiveEmail(@RequestBody EmailDto email) {
        service.receiveEmailFromContactByUser(email.getEmailTo(), email.getEmailFrom(), email.getText());
    }

    @SneakyThrows
    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    }
}
