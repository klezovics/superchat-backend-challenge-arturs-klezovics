package com.klezovich.superchat.domain.message;

import com.klezovich.superchat.domain.Channel;
import com.klezovich.superchat.domain.Contact;
import com.klezovich.superchat.domain.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue
    private BigInteger id;
    private String text;
    @Enumerated(EnumType.STRING)
    private MessageStatus status;
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;
    @Enumerated(EnumType.STRING)
    private Channel channel;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
