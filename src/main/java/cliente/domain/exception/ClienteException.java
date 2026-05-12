package cliente.domain.exception;

public class ClienteException extends DomainException{

    protected ClienteException(
            String errorCode,
            String message
    ){
        super(errorCode, message);
    }
}
