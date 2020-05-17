package br.com.maddytec.efetivacao.gateway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PagamentoJson {

    private Long numeroCartao;
    private Long codigoSegurancaCartao;
    private BigDecimal valorCompra;

}
