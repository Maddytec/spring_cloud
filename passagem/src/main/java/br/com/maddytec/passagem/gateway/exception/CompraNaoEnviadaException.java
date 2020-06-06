package br.com.maddytec.passagem.gateway.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompraNaoEnviadaException extends RuntimeException {

    public CompraNaoEnviadaException(String mensagem){
        super(mensagem);
    }

}
