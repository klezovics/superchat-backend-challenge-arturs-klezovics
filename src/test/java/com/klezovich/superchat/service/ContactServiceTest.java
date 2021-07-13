package com.klezovich.superchat.service;

import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.repository.ContactRepository;
import com.klezovich.superchat.repository.UserRepository;
import com.klezovich.superchat.util.ContactMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ContactServiceTest {

    @Autowired
    private ContactService service;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    void testCanCreateNewContactForExistingUser() {
        var contact = ContactMother.valid().owner(null).build();

        service.addContactForUser(contact, "spring");

        var contacts = contactRepository.findAll();
        assertEquals(1, contacts.spliterator().estimateSize());

        contact = contacts.iterator().next();
        assertEquals("John Dow", contact.getName());

        var owner = contact.getOwner();
        assertEquals("spring", owner.getUsername());
    }

    @Test
    @Transactional
    void testCanGetAllUserContacts() {

        var contact1 = ContactMother.valid().build();
        var contact2 = ContactMother.valid().name("Jane Dow").build();
        var contact3 = ContactMother.valid().name("Admin's friend").owner(null).build();

        contactRepository.saveAll(List.of(contact1,contact2,contact3));

        var contacts = service.getAllContactsForUser("spring");
        assertEquals(2, contacts.size());

        var contactNames = contacts.stream()
                .map(Contact::getName)
                .collect(toList());

        assertThat(contactNames, containsInAnyOrder("John Dow", "Jane Dow"));
    }
}