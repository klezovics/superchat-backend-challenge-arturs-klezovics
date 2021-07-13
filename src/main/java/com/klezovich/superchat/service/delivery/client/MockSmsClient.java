package com.klezovich.superchat.service.delivery.client;

import com.klezovich.superchat.domain.Channel;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
@Profile("!production")
public class MockSmsClient implements DeliveryClient {

    @Override
    public Channel getChannel() {
        return Channel.SMS;
    }

    @Override
    public void deliverMessage(com.klezovich.superchat.domain.message.Message m) {
        log.info("Message id={} delivered");
    }
}
