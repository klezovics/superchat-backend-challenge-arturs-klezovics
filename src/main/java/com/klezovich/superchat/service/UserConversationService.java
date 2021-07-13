package com.klezovich.superchat.service;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.Conversation;
import com.klezovich.superchat.domain.message.Message;
import com.klezovich.superchat.repository.MessageRepository;
import com.klezovich.superchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserConversationService {

    private UserRepository userRepository;
    private MessageRepository messageRepository;

    @Autowired
    public UserConversationService(UserRepository userRepository, MessageRepository messageRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
    }

    public List<Conversation> getAllConversations(String username) {
        var user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Unknown user"));
        var contacts = user.getContacts();

        var conversations = new ArrayList<Conversation>();
        for(var contact : contacts ) {
            for( var channel : Channel.values() ) {
                var msgs = messageRepository.findByContactIdAndChannelAndUser_Username(contact.getId(), channel, username);
                if(msgs.size() == 0 ) {
                    continue;
                }

                msgs = msgs.stream()
                        .sorted(Comparator.comparing(Message::getCreatedAt).reversed())
                        .collect(Collectors.toList());

                conversations.add(Conversation.builder().messages(msgs).channel(channel).contact(contact).build());
            }
        }


        return conversations;
    }
}
