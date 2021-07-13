package com.klezovich.superchat.service.delivery.client;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.message.Message;

public interface DeliveryClient {

    Channel getChannel();
    void deliverMessage(Message m);
}
