package br.com.maddytec.feedback.exceptions;

public class NaoFinalizadoException extends RuntimeException {

    public NaoFinalizadoException() {
    }
    public NaoFinalizadoException(String mensagem){
        super(mensagem);
    }
}
