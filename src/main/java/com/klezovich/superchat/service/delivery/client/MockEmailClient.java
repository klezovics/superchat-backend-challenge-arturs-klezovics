package com.klezovich.superchat.service.delivery.client;

import com.klezovich.superchat.domain.Channel;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
@Slf4j
@Profile("!production")
public class MockEmailClient implements DeliveryClient {

    @Override
    public Channel getChannel() {
        return Channel.EMAIL;
    }

    @Override
    public void deliverMessage(com.klezovich.superchat.domain.message.Message m) {
        log.info("Message id={} delivered", m.getId());
    }
}
