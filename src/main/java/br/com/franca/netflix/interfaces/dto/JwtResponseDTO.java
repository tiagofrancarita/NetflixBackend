package br.com.franca.netflix.interfaces.dto;

import lombok.Data;

import java.util.Date;

@Data
public class JwtResponseDTO {

    private String tokenType;
    private long expiresIn;     // em milissegundos
    private Date consentedOn;   // data da emissão
    private String accessToken; // token JWT
    private String encryptKey;
    private String signKey;

    // Construtor completo
    public JwtResponseDTO(String tokenType, long expiresIn, Date consentedOn, String accessToken, String encryptKey, String signKey) {
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.consentedOn = consentedOn;
        this.accessToken = accessToken;
        this.encryptKey = encryptKey;
        this.signKey = signKey;
    }

}
