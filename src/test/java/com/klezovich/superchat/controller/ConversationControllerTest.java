package com.klezovich.superchat.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.Conversation;
import com.klezovich.superchat.service.UserConversationService;
import com.klezovich.superchat.util.ContactMother;
import com.klezovich.superchat.util.MessageMother;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ConversationController.class})
@AutoConfigureTestDatabase
class ConversationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserConversationService service;

    private ObjectMapper mapper = new ObjectMapper();

    @WithMockUser("spring")
    @Test
    void testCanGetUserConversations() throws Exception {
        var conversation = Conversation.builder()
                .channel(Channel.EMAIL)
                .contact(ContactMother.valid().build())
                .messages(List.of(MessageMother.valid().build()))
                .build();

        when(service.getAllConversations(any())).thenReturn(List.of(conversation));

        var result = mvc.perform(get("/conversation")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hey, there!")))
                .andReturn();

        var json = result.getResponse().getContentAsString();

        var conversations = mapper.readValue(json,  new TypeReference<List<Conversation>>() {});
        assertEquals(1, conversations.size());

        var conv = conversations.get(0);
        assertEquals("John Dow", conv.getContact().getName());
    }
}