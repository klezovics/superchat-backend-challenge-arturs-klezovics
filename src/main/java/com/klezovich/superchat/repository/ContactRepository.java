package com.klezovich.superchat.repository;

import com.klezovich.superchat.domain.Contact;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

    List<Contact> findByOwner_Username(String username);
}
