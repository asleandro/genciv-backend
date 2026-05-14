package br.com.genciv.cliente.domain.exception;

public class ClienteDuplicadoException extends ClienteException{

    private static final String ERROR_CODE = "CLIENTE_DUPLICADO";

    public ClienteDuplicadoException(String documento){
        super(ERROR_CODE, "Cliente já cadastrado para o documento: " + documento);
    }
}
