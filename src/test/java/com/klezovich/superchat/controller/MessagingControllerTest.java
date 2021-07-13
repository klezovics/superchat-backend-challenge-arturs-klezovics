package com.klezovich.superchat.controller;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.service.MessagingService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {MessagingController.class})
@AutoConfigureTestDatabase
class MessagingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MessagingService service;

    @Captor
    private ArgumentCaptor<Long> longCaptor;

    @Captor
    private ArgumentCaptor<String> stringCaptor1;

    @Captor
    private ArgumentCaptor<String> stringCaptor2;

    @Captor
    private ArgumentCaptor<Channel> channelCaptor;

    @Test
    @WithMockUser("spring")
    void testCanSendMessageToUser() throws Exception {

        mvc.perform(post("/message")
                .param("contactId", "1")
                .param("channel", "SMS")
                .param("text", "Hey"))
                .andExpect(status().isOk());

        verify(service).sendMessageToContactViaChannel(
                stringCaptor1.capture(),
                longCaptor.capture(),
                channelCaptor.capture(),
                stringCaptor2.capture()
        );

        assertEquals("spring", stringCaptor1.getValue());
        assertEquals(1L, longCaptor.getValue());
        assertEquals(Channel.SMS, channelCaptor.getValue());
        assertEquals("Hey", stringCaptor2.getValue());
    }
}