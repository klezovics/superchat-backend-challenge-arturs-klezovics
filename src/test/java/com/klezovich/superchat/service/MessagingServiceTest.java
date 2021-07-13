package com.klezovich.superchat.service;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.message.MessageStatus;
import com.klezovich.superchat.repository.ContactRepository;
import com.klezovich.superchat.repository.MessageRepository;
import com.klezovich.superchat.repository.UserRepository;
import com.klezovich.superchat.util.ContactMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MessagingServiceTest {

    @Autowired
    private MessagingService service;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ContactService contactService;

    @Test
    @Transactional
    void testCanSendMessageToKnownContactInExistingConversation() {
        var user = userRepository.findByUsername("spring").get();
        var contact = ContactMother.valid().owner(user).build();
        contactService.addContactForUser(contact, user.getUsername());

        service.sendMessageToContactViaChannel("spring", contact.getId(), Channel.SMS, "sms-hello");

        var msgs = messageRepository.findAll();
        assertEquals(1, msgs.spliterator().estimateSize());

        var msg = msgs.iterator().next();

        assertNotNull(msg.getId());
        assertEquals(MessageStatus.UNSENT, msg.getStatus());
        assertEquals("sms-hello", msg.getText());
        assertEquals(Channel.SMS, msg.getChannel());
        assertEquals("spring", msg.getUser().getUsername());
        assertEquals(contact.getId(), msg.getContact().getId());
    }

    @Test
    @Transactional
    void testCanSendMessageToKnownContactInNewConversation() {
        var user = userRepository.findByUsername("spring").get();
        var contact = ContactMother.valid().owner(user).build();
        contactService.addContactForUser(contact, user.getUsername());

        service.sendMessageToContactViaChannel("spring", contact.getId(), Channel.SMS, "sms-hello");

        var msgs = messageRepository.findAll();
        assertEquals(1, msgs.spliterator().estimateSize());

        var msg = msgs.iterator().next();

        assertNotNull(msg.getId());
        assertEquals(MessageStatus.UNSENT, msg.getStatus());
        assertEquals("sms-hello", msg.getText());
        assertEquals(Channel.SMS, msg.getChannel());
        assertEquals("spring", msg.getUser().getUsername());
        assertEquals(contact.getId(), msg.getContact().getId());
        assertNotNull(msg.getCreatedAt());
        System.out.println(msg.getCreatedAt());
    }

    @Test
    @Transactional
    void testCanSendSeveralMessagesToKnownContact() {
        var user = userRepository.findByUsername("spring").get();
        var contact = ContactMother.valid().owner(user).build();
        contactService.addContactForUser(contact, user.getUsername());

        service.sendMessageToContactViaChannel("spring", contact.getId(), Channel.SMS, "sms-hello1");
        service.sendMessageToContactViaChannel("spring", contact.getId(), Channel.SMS, "sms-hello2");
        service.sendMessageToContactViaChannel("spring", contact.getId(), Channel.SMS, "sms-hello3");

        var msgs = messageRepository.findAll();
        assertEquals(3, msgs.spliterator().estimateSize());
    }

    @Test
    @Transactional
    void testCanReceiveSmsFromNewContact() {

        service.receiveSmsFromContactByUser("+17209034257", "+111222333", "Hey");

        var contacts = contactRepository.findAll();
        assertEquals(1, contacts.spliterator().estimateSize());
        var contact = contacts.iterator().next();
        assertEquals("+111222333", contact.getPhoneNumber());
        assertEquals("spring", contact.getOwner().getUsername());

        var msgs = messageRepository.findAll();
        assertEquals(1, msgs.spliterator().estimateSize());
        var msg = msgs.iterator().next();
        assertEquals("Hey", msg.getText());
    }

    @Test
    @Transactional
    void testCanReceiveSmsFromExistingContact() {

        var contact = ContactMother.valid().phoneNumber("+111222333").build();
        var user = userRepository.findByUsername("spring").get();
        contact.setOwner(user);
        user.getContacts().add(contact);
        contactRepository.save(contact);

        service.receiveSmsFromContactByUser("+17209034257", "+111222333", "Hey");

        var contacts = contactRepository.findAll();
        assertEquals(1, contacts.spliterator().estimateSize());
        contact = contacts.iterator().next();
        assertEquals("+111222333", contact.getPhoneNumber());
        assertEquals("spring", contact.getOwner().getUsername());

        var msgs = messageRepository.findAll();
        assertEquals(1, msgs.spliterator().estimateSize());
        var msg = msgs.iterator().next();
        assertEquals("Hey", msg.getText());
    }
}