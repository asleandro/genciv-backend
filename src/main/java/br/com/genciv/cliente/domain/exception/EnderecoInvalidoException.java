package br.com.genciv.cliente.domain.exception;

public class EnderecoInvalidoException extends ClienteException {

    private static final String ERROR_CODE = "ENDERECO_INVALIDO";

    public EnderecoInvalidoException(String message) {
        super(ERROR_CODE, message);
    }
}
