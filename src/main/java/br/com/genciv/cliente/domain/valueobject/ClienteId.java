package br.com.genciv.cliente.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public final class ClienteId {

    private final UUID valor;

    public ClienteId(UUID valor) {

        if (valor == null) {
            throw new IllegalArgumentException("ClienteId não pode ser nulo");
        }

        this.valor = valor;
    }

    public static ClienteId novo() {
        return new ClienteId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ClienteId that)) return false;

        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
