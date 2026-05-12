package cliente.domain.valueobject;

import cliente.domain.exception.ValueObjectInvalidoException;

import java.util.Objects;
import java.util.regex.Pattern;

public class Email {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final String valor;

    public Email(String valor) {

        if (valor == null || !EMAIL_PATTERN.matcher(valor).matches()) {
            throw new ValueObjectInvalidoException("Email inválido");
        }
        this.valor = valor.toLowerCase();
    }

    public String getValor() {
        return valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Email email)) {
            return false;
        }

        return Objects.equals(valor, email.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
