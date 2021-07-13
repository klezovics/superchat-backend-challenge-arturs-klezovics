package com.klezovich.superchat.domain;

import com.klezovich.superchat.domain.message.Message;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Conversation {

    private Channel channel;
    private Contact contact;
    private List<Message> messages;
}
