package com.klezovich.superchat.service;

import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.repository.ContactRepository;
import com.klezovich.superchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserRepository userRepository) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    public void addContactForUser(Contact contact, String username) {
        var user = userRepository.findById(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        contact.setOwner(user);
        contactRepository.save(contact);
        user.getContacts().add(contact);
        userRepository.save(user);
    }

    public List<Contact> getAllContactsForUser(String username) {
        return contactRepository.findByOwner_Username(username);
    }
}
