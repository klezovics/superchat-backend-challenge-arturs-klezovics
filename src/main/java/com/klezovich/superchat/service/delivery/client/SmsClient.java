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
@Profile("production")
public class SmsClient implements DeliveryClient {

    @Value("${twilio.account_sid}")
    private String ACCOUNT_SID;

    @Value("${twilio.auth_token}")
    private String AUTH_TOKEN;

    @Value("${twilio.phone_number}")
    private String FROM_PHONE_NUMBER;

    public void sendSms(String phoneNumber, String text) {

        var message = Message.creator(
                new PhoneNumber(phoneNumber),
                new PhoneNumber(FROM_PHONE_NUMBER),
                text)
                .create();

        log.info("SMS message sent {}", message);
    }

    @PostConstruct
    void init() {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    @Override
    public Channel getChannel() {
        return Channel.SMS;
    }

    @Override
    public void deliverMessage(com.klezovich.superchat.domain.message.Message m) {

    }
}
