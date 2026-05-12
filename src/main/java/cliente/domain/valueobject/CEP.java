package cliente.domain.valueobject;

import cliente.domain.exception.DocumentoInvalidoException;
import cliente.domain.exception.ValueObjectInvalidoException;

import java.util.Objects;

public class CEP {

    private final String valor;

    public CEP(String valor) {

        if (valor == null) {
            throw new ValueObjectInvalidoException("CEP não pode ser nulo");
        }

        String valorLimpo = valor.replaceAll("[^0-9]", "");

        if (!valorLimpo.matches("^[0-9]{8}$")) {
            throw new ValueObjectInvalidoException(
                    "CEP inválido"
            );
        }
        this.valor = valorLimpo;
    }

    public String getValor() {
        return valor;
    }

    public String formatado() {
        return valor.substring(0, 5)
                + "-"
                + valor.substring(5);
    }

    @Override
    public String toString() {
        return formatado();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CEP cep)) return false;

        return Objects.equals(valor, cep.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

}
