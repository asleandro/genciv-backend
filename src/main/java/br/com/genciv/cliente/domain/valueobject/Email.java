package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Email {

    private static final String EMAIL_REGEX =
            "^(?!\\.)(?!.*\\.\\.)(?!.*\\.@)" +
                    "[A-Za-z0-9._%+-]+@" +
                    "([A-Za-z0-9-]+\\.)+" +
                    "[A-Za-z]{2,}$";

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile(EMAIL_REGEX);

    private final String valor;

    public Email(String valor) {

        if (valor == null) {
            throw new ValueObjectInvalidoException("Email informado não pode ser null");
        }

        String emailNormalizado = valor.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(valor).matches()) {
            throw new ValueObjectInvalidoException("Email informado não é válido");
        }

        this.valor = emailNormalizado;
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

    @Override
    public String toString() {
        return valor;
    }
}
