package com.klezovich.superchat.controller;

import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.service.ContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/contact")
@PreAuthorize("hasRole('USER')")
public class ContactController {

    private ContactService service;

    public ContactController(ContactService service) {
        this.service = service;
    }

    @GetMapping
    public List<Contact> getUserContacts(Principal p) {
        var username = p.getName();
        return service.getAllContactsForUser(username);
    }

    @PostMapping
    public void createContact(Principal p, @RequestBody Contact contact) {
         service.addContactForUser(contact, p.getName());
    }
}
