package br.com.genciv.catalogo.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ComposicaoServicoId {

    private final UUID valor;

    public ComposicaoServicoId(UUID valor) {

        if (valor == null) {
            throw new IllegalArgumentException("ComposicaoServicoId não pode ser nulo");
        }

        this.valor = valor;
    }

    public static ComposicaoServicoId novo() {
        return new ComposicaoServicoId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof ComposicaoServicoId that)) {
            return false;
        }

        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

}
