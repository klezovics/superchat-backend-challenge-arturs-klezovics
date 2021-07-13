package com.klezovich.superchat.repository;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.message.Message;
import com.klezovich.superchat.domain.message.MessageStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByStatus(MessageStatus status);
    List<Message> findByContactIdAndChannelAndUser_Username(Long contactId, Channel channel, String username);
}
