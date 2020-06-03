package br.com.maddytec.banco.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagamentoJson {

    @NotNull(message = "Número do cartão é obrigatório.")
    private Long numeroCartao;

    @NotNull(message = "Código de segurança do cartão é obrigatório.")
    private Long codigoSegurancaCartao;

    @NotNull(message = "Valor da compra é obrigatório.")
    private BigDecimal valorCompra;

}
