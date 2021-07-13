package com.klezovich.superchat.repository;

import com.klezovich.superchat.domain.message.MessageStatus;
import com.klezovich.superchat.util.MessageMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository repository;

    @Test
    void testCanFindUndeliveredMessages() {
        var msg1 = MessageMother.valid().build();
        var msg2 = MessageMother.valid().status(MessageStatus.SENT).build();

        repository.saveAll(List.of(msg1, msg2));

        var msgs = repository.findByStatus(MessageStatus.UNSENT);
        assertEquals(1, msgs.size());
    }
}