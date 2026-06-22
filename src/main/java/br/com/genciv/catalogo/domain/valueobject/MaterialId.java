package br.com.genciv.catalogo.domain.valueobject;

import java.util.Objects;
import java.util.UUID;

public class MaterialId {

    private final UUID valor;

    public MaterialId(UUID valor) {

        if (valor == null) {
            throw new IllegalArgumentException("MaterialId não pode ser nulo");
        }

        this.valor = valor;
    }

    public static MaterialId novo() {
        return new MaterialId(UUID.randomUUID());
    }

    public UUID getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof MaterialId that)) return false;

        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

}
