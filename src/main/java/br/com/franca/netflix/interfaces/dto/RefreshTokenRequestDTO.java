package br.com.franca.netflix.interfaces.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDTO {

    private String refreshToken;
}