package br.com.franca.netflix.interfaces.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponseDTO {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}