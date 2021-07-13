package com.klezovich.superchat.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDetails {

    @Id
    @GeneratedValue
    private Long id;

    private String phoneNumber;
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
}
