package cliente.domain.exception;

public class ValueObjectInvalidoException extends DomainException{

    public ValueObjectInvalidoException(String message){
        super(
                "VALUE_OBJECT_INVALIDO",
                message);
    }
}
