package com.klezovich.superchat.service;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.domain.message.Message;
import com.klezovich.superchat.domain.message.MessageStatus;
import com.klezovich.superchat.domain.token.ReplaceTokensRequest;
import com.klezovich.superchat.repository.ContactRepository;
import com.klezovich.superchat.repository.MessageRepository;
import com.klezovich.superchat.repository.UserRepository;
import com.klezovich.superchat.service.delivery.MessageDeliveryService;
import com.klezovich.superchat.service.tokens.TokenReplacementService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.klezovich.superchat.domain.message.MessageDirection.FROM_USER;
import static com.klezovich.superchat.domain.message.MessageDirection.TO_USER;

@Service
public class MessagingService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final TokenReplacementService tokenReplacementService;
    private final MessageDeliveryService deliveryService;

    @Autowired
    public MessagingService(
            ContactRepository contactRepository,
            UserRepository userRepository,
            MessageRepository messageRepository, TokenReplacementService tokenReplacementService, MessageDeliveryService deliveryService) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.tokenReplacementService = tokenReplacementService;
        this.deliveryService = deliveryService;
    }

    @NonNull
    public void sendMessageToContactViaChannel(
            @NonNull String username,
            @NonNull Long contactId,
            @NonNull Channel channel,
            @NonNull String text) {

        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Unknown username"));
        var contact = user.getContacts().stream()
                .filter(c -> c.getId().equals(contactId))
                .findAny()
                .orElseThrow(() -> new RuntimeException("Contact not in user's contact list"));

        var message = Message.builder()
                .text(replaceTextTokens(text, contact))
                .status(MessageStatus.UNSENT)
                .direction(FROM_USER)
                .channel(channel)
                .user(user)
                .contact(contact)
                .build();

        messageRepository.save(message);
    }

    public void receiveSmsFromContactByUser(String userPhoneNumber, String contactPhoneNumber, String messageText) {
        var user = userRepository.findByUserDetails_PhoneNumber(userPhoneNumber)
                .orElseThrow(() -> new RuntimeException("No user with such phone number"));

        var contact = user.getContacts().stream()
                .filter(c -> c.getPhoneNumber().equals(contactPhoneNumber))
                .findAny()
                .orElseGet(() -> {
                    var newContact = Contact.builder()
                            .name("Unknown contact")
                            .owner(user)
                            .phoneNumber(contactPhoneNumber)
                            .build();

                    contactRepository.save(newContact);
                    user.getContacts().add(newContact);
                    userRepository.save(user);

                    return newContact;
                });

        var msg = Message.builder()
                .text(messageText)
                .status(MessageStatus.SENT)
                .direction(TO_USER)
                .channel(Channel.SMS)
                .contact(contact)
                .user(user)
                .build();

        messageRepository.save(msg);
        deliveryService.deliverMessage(msg);
    }

    public void receiveEmailFromContactByUser(String userEmail, String contactEmail, String emailText) {

        var user = userRepository.findByUserDetails_PhoneNumber(userEmail)
                .orElseThrow(() -> new RuntimeException("No user with such email"));

        var contact = user.getContacts().stream()
                .filter(c -> c.getEmail().equals(contactEmail))
                .findAny()
                .orElseGet(() -> {
                    var newContact = Contact.builder()
                            .name("Unknown contact")
                            .owner(user)
                            .name(contactEmail)
                            .build();

                    contactRepository.save(newContact);
                    user.getContacts().add(newContact);
                    userRepository.save(user);

                    return newContact;
                });

        var msg = Message.builder()
                .text(emailText)
                .status(MessageStatus.SENT)
                .direction(TO_USER)
                .channel(Channel.EMAIL)
                .contact(contact)
                .user(user)
                .build();

        messageRepository.save(msg);
    }


    private String replaceTextTokens(String originalText, Contact toContact) {
        return tokenReplacementService.replaceTokens(
                ReplaceTokensRequest.builder()
                        .messageText(originalText)
                        .toContact(toContact)
                        .build())
                .getMessageText();
    }
}
