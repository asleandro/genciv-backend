package cliente.domain.valueobject;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import cliente.domain.exception.DocumentoInvalidoException;

import java.util.Objects;

public class CPF {

    private static final CPFValidator validator = new CPFValidator();

    private final String valor;

    public CPF(String valor) {

        if (valor == null) {
            throw new DocumentoInvalidoException("CPF não pode ser nulo");
        }

        String cpfLimpo = valor.replaceAll("[^0-9]", "");

        try {
            validator.assertValid(cpfLimpo);
        } catch (InvalidStateException e) {
            throw new DocumentoInvalidoException("CPF inválido");
        }

        this.valor = cpfLimpo;
    }

    public String getValor() {
        return valor;
    }

    public String formatado() {
        return valor.substring(0, 3)
                + "."
                + valor.substring(3, 6)
                + "."
                + valor.substring(6, 9)
                + "-"
                + valor.substring(9);
    }

    @Override
    public String toString() {
        return formatado();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CPF cpf)) {
            return false;
        }
        return Objects.equals(valor, cpf.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }
}
