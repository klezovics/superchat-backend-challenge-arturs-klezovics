package com.klezovich.superchat.controller;

import com.klezovich.superchat.domain.Conversation;
import com.klezovich.superchat.service.UserConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/conversation")
@PreAuthorize("hasRole('USER')")
public class ConversationController {

    @Autowired
    private UserConversationService service;

    @GetMapping
    public List<Conversation> getUserConversations(Principal p) {
        return service.getAllConversations(p.getName());
    }
}
