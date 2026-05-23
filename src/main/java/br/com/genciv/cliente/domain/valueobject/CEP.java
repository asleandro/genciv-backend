package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class CEP {

    private static final Pattern CEP_PATTERN =
            Pattern.compile("^\\d{8}$|\\d{5}-\\d{3}$");

    private final String valor;

    public CEP(String valor) {

        if (valor == null) {
            throw new ValueObjectInvalidoException("CEP não pode ser nulo");
        }

        if (valor.isBlank()) {
            throw new ValueObjectInvalidoException("CEP não pode ser vazio");
        }

        if (!CEP_PATTERN.matcher(valor).matches()){
            throw new ValueObjectInvalidoException("CEP inválido");
        }

        String valorLimpo = valor.replaceAll("[^0-9]", "");

        if (!valorLimpo.matches("^[0-9]{8}$")) {
            throw new ValueObjectInvalidoException("CEP inválido");
        }
        this.valor = valorLimpo;
    }

    public String getNumero() {
        return valor;
    }

    public String formatar() {
        return valor.substring(0, 5)
                + "-"
                + valor.substring(5);
    }

    @Override
    public String toString() {
        return formatar();
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
