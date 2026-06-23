package br.com.genciv.catalogo.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ServicoId {

    private final UUID valor;

    public ServicoId(UUID valor) {

        if (valor == null) {
            throw new IllegalArgumentException("ServicoId não pode ser nulo");
        }

        this.valor = valor;
    }

    public static ServicoId novo() {
        return new ServicoId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof ServicoId that)) return false;

        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

}
