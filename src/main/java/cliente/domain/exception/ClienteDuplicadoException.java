package cliente.domain.exception;

public class ClienteDuplicadoException extends ClienteException{

    public ClienteDuplicadoException(String documento){
        super("Cliente já cadastrado para o documento: " + documento);
    }
}
