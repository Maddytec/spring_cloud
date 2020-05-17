package br.com.maddytec.efetivacao.gateway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompraFinalizadaJson {

    private CompraChaveJson compraChaveJson;
    private String mensagem;
    private Boolean pagamentoOK;

}
