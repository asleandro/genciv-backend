package br.com.genciv.cliente.domain.exception;

public class RazaoSocialInvalidaException extends ClienteException {
    private static final String ERROR_CODE = "RAZAO_SOCIAL_INVALIDA";

    public RazaoSocialInvalidaException(String message){
        super(ERROR_CODE, message);
    }
}
