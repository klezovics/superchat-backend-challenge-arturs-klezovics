package com.klezovich.superchat.util;

import com.klezovich.superchat.domain.Contact;

public class ContactMother {

    public static Contact.ContactBuilder valid() {
        return Contact.builder()
                .email("jd@gmail.com")
                .name("John Dow")
                .phoneNumber("+8 111 222 333")
                .owner(UserMother.spring().build());
    }
}
