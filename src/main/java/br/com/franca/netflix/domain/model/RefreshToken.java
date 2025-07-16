package br.com.franca.netflix.domain.model;


import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Data
public class RefreshToken {

    private Long id;
    private String token;
    private String email;
    private Date dataExpiracao;

}
