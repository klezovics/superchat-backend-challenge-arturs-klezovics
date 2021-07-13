package com.klezovich.superchat;

import com.klezovich.superchat.domain.UserDetails;
import com.klezovich.superchat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer implements ApplicationRunner {

    @Autowired
    private UserRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var user = repository.findByUsername("spring").get();
        var userDetails = UserDetails.builder().user(user).phoneNumber("+17209034257").email("klezovich@gmail.com").build();
        user.setUserDetails(userDetails);
        repository.save(user);
    }
}
