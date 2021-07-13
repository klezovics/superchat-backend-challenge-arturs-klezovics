package com.klezovich.superchat.repository;

import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByUsername(String username);
    Optional<User> findByUserDetails_PhoneNumber(String phoneNumber);
    Optional<User> findByUserDetails_Email(String email);

}
