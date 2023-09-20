package com.example.fsc.dto.memberDTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class TokenDTO {
    private String memberToken;
}
