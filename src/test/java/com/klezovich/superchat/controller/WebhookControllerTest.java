package com.klezovich.superchat.controller;

import com.klezovich.superchat.service.MessagingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {WebhookController.class})
@AutoConfigureTestDatabase
class WebhookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessagingService service;

    @Test
    void testCanReceiveWebhookEmailNotification() throws Exception {
        mvc.perform(post("/webhook/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"emailFrom\": \"c1@gmail.com\", " +
                        "\"emailTo\": \"klezovich@gmail.com\", " +
                        "\"text\":\"Hey\"," +
                        "\"date\": \"2021-02-02\" }"))
                .andExpect(status().isOk());

        verify(service).receiveEmailFromContactByUser(
                "klezovich@gmail.com",
                "c1@gmail.com",
                "Hey");
    }

    @Test
    void testCanReceiveTwilioWebhookNotificaiton() throws Exception {
        mvc.perform(post("/webhook/twilio")
                .param("To", "%2B111")
                .param("From", "%2B222")
                .param("Body", "Hey"))
                .andExpect(status().isOk());

        verify(service).receiveSmsFromContactByUser(
                "+111",
                "+222",
                "Hey");
    }
}