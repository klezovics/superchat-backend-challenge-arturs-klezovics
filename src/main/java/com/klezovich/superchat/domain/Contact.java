package com.klezovich.superchat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Contact {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private String email;
    private String phoneNumber;

    @OneToOne
    @JsonIgnore
    private User owner;
}
