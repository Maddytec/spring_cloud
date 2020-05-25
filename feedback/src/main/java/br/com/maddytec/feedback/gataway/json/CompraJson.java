package br.com.maddytec.feedback.gataway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraJson {

    private Long codigoPassagem;
    private Long numeroCartao;
    private Long codigoSegurancaCartao;
    private BigDecimal valorPassagem;
}
