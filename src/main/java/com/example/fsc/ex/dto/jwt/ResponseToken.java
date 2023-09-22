package com.example.fsc.ex.dto.jwt;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ResponseToken {
    Long memberId;
    String email;
    String accessToken;
    Date accessTokenExpireDate;


    @Builder
    public ResponseToken(String accessToken, Date accessTokenExpireDate, String email, Long memberId ) {
        this.memberId = memberId;
        this.email = email;
        this.accessToken = accessToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
    }
}
