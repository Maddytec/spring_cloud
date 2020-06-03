package br.com.maddytec.efetivacao.service.banco;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PagamentoRetorno {

    private String mensagem;
    private Boolean pagamentoOK;
}
