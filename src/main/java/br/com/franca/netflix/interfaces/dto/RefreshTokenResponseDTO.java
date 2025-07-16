package br.com.franca.netflix.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResponseDTO {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
