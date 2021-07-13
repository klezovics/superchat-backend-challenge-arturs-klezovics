package com.klezovich.superchat.service.delivery;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.message.Message;
import com.klezovich.superchat.domain.message.MessageStatus;
import com.klezovich.superchat.repository.MessageRepository;
import com.klezovich.superchat.service.delivery.client.DeliveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class MessageDeliveryService {

    private MessageRepository repository;
    private Map<Channel, DeliveryClient> channelDeliveryClientMap = new HashMap<>();

    public MessageDeliveryService(MessageRepository repository) {
        this.repository = repository;
    }

    //Runs every five minutes to retry and deliver undelivered messages
    @Scheduled(fixedDelay = 300000)
    public void deliverMessages() {
        var undeliveredMessages = repository.findByStatus(MessageStatus.UNSENT);
        for(var message : undeliveredMessages) {
            deliverMessage(message);
        }
        repository.saveAll(undeliveredMessages);
    }

    @Autowired
    public void setDeliveryClients(List<DeliveryClient> clients) {
        for(var client : clients) {
            channelDeliveryClientMap.put(client.getChannel(),client);
        }
    }

    public void deliverMessage(Message message) {
        try {
            var client = channelDeliveryClientMap.get(message.getChannel());
            client.deliverMessage(message);
            message.setStatus(MessageStatus.SENT);
        }catch (Exception e) {
            log.error("Failed to delivery message {}. Exception {}", message.getId(), e.getMessage());
        }
    }
}
