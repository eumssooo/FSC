package com.example.fsc.ex.dto.jwt;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String grantType;
    private String accessToken;
    private Date accessTokenExpireDate;
}
