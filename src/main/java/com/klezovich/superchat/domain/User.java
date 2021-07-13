package com.klezovich.superchat.domain;

import lombok.*;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Subselect("select * from users")
public class User {

    @Id
    private String username;
    private String password;
    private Boolean enabled;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserDetails userDetails;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Contact> contacts = new ArrayList<>();
}