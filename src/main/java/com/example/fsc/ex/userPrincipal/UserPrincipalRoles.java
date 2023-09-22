package com.example.fsc.ex.userPrincipal;

import com.example.fsc.ex.roles.Roles;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserPrincipalRoles {
    USER("ROLE_USER", "일반 사용자 권한"),
    NONE("NONE", "권한 없음");
    private final String code;
    private final String name;

    public static UserPrincipalRoles of(String code) {
        return Arrays.stream(UserPrincipalRoles.values())
                .filter(r -> r.getCode().equals(code))
                .findAny()
                .orElse(NONE);
    }
}
