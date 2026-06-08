package br.com.genciv.cliente.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ContatoId {

    private final UUID valor;

    public ContatoId(UUID valor) {
        if (valor == null) {
            throw new IllegalArgumentException("ContatoId não pode ser nulo");
        }
        this.valor = valor;
    }

    public static ContatoId novo() {
        return new ContatoId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof ContatoId that)) return false;

        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }


}
