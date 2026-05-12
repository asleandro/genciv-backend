package cliente.domain.exception;

public class DocumentoInvalidoException extends RuntimeException{

    public DocumentoInvalidoException(String message){
        super(message);
    }
}
