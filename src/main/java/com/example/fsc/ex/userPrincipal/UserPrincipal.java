package com.example.fsc.ex.userPrincipal;


//import com.example.fsc.entity.memberEntity.MemberEntity;
//import com.example.fsc.ex.users.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "member")
public class UserPrincipal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long userPrincipalId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

}
