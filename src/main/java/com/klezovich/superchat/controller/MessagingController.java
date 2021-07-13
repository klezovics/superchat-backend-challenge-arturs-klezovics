package com.klezovich.superchat.controller;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/message")
@PreAuthorize("hasRole('USER')")
public class MessagingController {

    private MessagingService service;

    @Autowired
    public MessagingController(MessagingService service) {
        this.service = service;
    }

    @PostMapping
    public void sendMessage(
            Principal p,
            @RequestParam("contactId") Long contactId,
            @RequestParam("channel") String channel,
            @RequestParam("text") String text
    ) {

        service.sendMessageToContactViaChannel(p.getName(), contactId, Channel.valueOf(channel), text);
    }
}
