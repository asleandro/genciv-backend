package cliente.domain.valueobject;

import java.util.UUID;

public class ClienteId {

    private final UUID valor;

    public ClienteId(UUID valor){
        this.valor = valor;
    }

    public UUID getValor() {
        return valor;
    }
}
