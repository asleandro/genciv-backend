package br.com.genciv.catalogo.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class ItemComposicaoServicoId {

    private final UUID valor;

    public ItemComposicaoServicoId(UUID valor) {
        if (valor == null) {
            throw new IllegalArgumentException("Id não pode ser nulo");
        }

        this.valor = valor;
    }

    public static ItemComposicaoServicoId novo() {
        return new ItemComposicaoServicoId(UUID.randomUUID());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ItemComposicaoServicoId that)) return false;

        return Objects.equals(valor, that.valor);

    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
