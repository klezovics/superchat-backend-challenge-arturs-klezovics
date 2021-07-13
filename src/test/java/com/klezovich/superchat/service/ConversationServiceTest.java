package com.klezovich.superchat.service;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.domain.Conversation;
import com.klezovich.superchat.repository.ContactRepository;
import com.klezovich.superchat.repository.UserRepository;
import com.klezovich.superchat.util.ContactMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConversationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MessagingService messagingService;

    @Autowired
    private UserConversationService service;

    @Test
    @Transactional
    void testCanGetAllConverstationsForUser() {
        var user = userRepository.findByUsername("spring").get();
        var contact1 = ContactMother.valid().owner(user).build();
        contact1 = contactRepository.save(contact1);
        var contact2 = ContactMother.valid().name("Jane Dow").owner(user).build();
        contact2 = contactRepository.save(contact2);
        user.getContacts().addAll(List.of(contact1,contact2));
        userRepository.save(user);

        messagingService.sendMessageToContactViaChannel("spring", contact1.getId(), Channel.SMS, "sms-hello");
        messagingService.sendMessageToContactViaChannel("spring", contact2.getId(), Channel.SMS, "sms-hello");

        var conversations = service.getAllConversations("spring");
        assertEquals(2, conversations.size());

        var contactNames = conversations.stream()
                .map(Conversation::getContact)
                .map(Contact::getName)
                .collect(Collectors.toList());

        assertThat(contactNames, containsInAnyOrder("John Dow", "Jane Dow"));
    }
}
