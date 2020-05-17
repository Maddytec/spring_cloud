package br.com.maddytec.passagem.gateway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraJson {

    @NotNull(message = "Código de passagem obrigatório.")
    private Long codigoPassagem;

    @NotNull(message = "Número do cartão é obrigatório.")
    private Long numeroCartao;

    @NotNull(message = "Código de segurança do cartão é obrigatório.")
    private Long codigoSegurancaCartao;

    @NotNull(message = "Valor da passagem é obrigatório.")
    private BigDecimal valorPassagem;

}
