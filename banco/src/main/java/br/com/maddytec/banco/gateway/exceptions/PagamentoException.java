package br.com.maddytec.banco.gateway.exceptions;

public class PagamentoException extends RuntimeException {

    public PagamentoException(String mensagem){
        super(mensagem);
    }
}
