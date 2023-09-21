package com.example.fsc.dto.memberDTO;

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
    String message;

    @Builder
    public ResponseToken(String accessToken, Date accessTokenExpireDate, String email, Long memberId , String message) {
        this.memberId = memberId;
        this.email = email;
        this.accessToken = accessToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
        this.message = message;
    }
}