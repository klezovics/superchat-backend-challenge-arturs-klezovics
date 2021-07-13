package com.klezovich.superchat.util;

import com.klezovich.superchat.domain.User;

public class UserMother {

    public static User.UserBuilder spring() {
        return User.builder()
                .enabled(true)
                .username("spring")
                .password("secret");
    }
}
