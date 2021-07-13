package com.klezovich.superchat.repository;

import com.klezovich.superchat.domain.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
}
