package com.example.fsc.entity.memberEntity;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name="member")
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="email_id")
    private Long emailId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    public void setPassword(String rawPassword, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(rawPassword);
    }
}
