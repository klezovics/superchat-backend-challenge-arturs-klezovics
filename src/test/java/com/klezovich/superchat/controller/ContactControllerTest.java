package com.klezovich.superchat.controller;

import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.service.ContactService;
import com.klezovich.superchat.util.ContactMother;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = {ContactController.class})
@AutoConfigureTestDatabase
class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactService service;

    @Captor
    private ArgumentCaptor<Contact> contactCaptor;

    @Captor
    private ArgumentCaptor<String> stringCaptor;

    @Test
    @WithMockUser(value = "spring")
    void testCanAddContact() throws Exception {
        mvc.perform(post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John Dow\", " +
                        "\"email\": \"jd@gmail.com\", " +
                        "\"phoneNumber\":\"+111\"}"))
                .andExpect(status().isOk());

        verify(service).addContactForUser(contactCaptor.capture(), stringCaptor.capture());

        var contact = contactCaptor.getValue();
        assertEquals("John Dow", contact.getName());
        assertEquals("jd@gmail.com", contact.getEmail());
        assertEquals("+111", contact.getPhoneNumber());

        assertEquals("spring", stringCaptor.getValue());
    }

    @Test
    @WithMockUser(value = "spring")
    void testCanGetContacts() throws Exception {

        var contact = ContactMother.valid().build();
        when(service.getAllContactsForUser(any())).thenReturn(List.of(contact));

        mvc.perform(get("/contact")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("John Dow")));
    }
}