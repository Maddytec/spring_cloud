package br.com.maddytec.efetivacao.gateway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown=true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraJson {

    private Long codigoPassagem;
    private Long numeroCartao;
    private Long codigoSegurancaCartao;
    private BigDecimal valorPassagem;

}
