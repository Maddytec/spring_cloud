package br.com.maddytec.feedback.gataway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompraFinalizadaJson {

    private CompraChaveJson compraChaveJson;
    private String mensagem;
    private Boolean pagamentoOK;
}
