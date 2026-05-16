package br.com.genciv.cliente.domain.valueobject;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.genciv.cliente.domain.exception.DocumentoInvalidoException;

import java.io.Serializable;
import java.util.Objects;

public final class CNPJ implements Serializable {

    private static final CNPJValidator validator = new CNPJValidator();

    private final String valor;

    public CNPJ(String valor) {

        if (valor == null) {
            throw new DocumentoInvalidoException("CNPJ não pode ser nulo");
        }

        if (valor.isBlank()) {
            throw new DocumentoInvalidoException("CNPJ não pode ser vazio");
        }

        String cnpjLimpo = valor.replaceAll("[^0-9]", "");

        try {
            validator.assertValid(cnpjLimpo);
        } catch (InvalidStateException e) {
            throw new DocumentoInvalidoException("CNPJ informado é inválido");
        }
        this.valor = cnpjLimpo;
    }

    public String getNumero() {
        return valor;
    }

    public String formatar() {
        return valor.substring(0, 2)
                + "."
                + valor.substring(2, 5)
                + "."
                + valor.substring(5, 8)
                + "/"
                + valor.substring(8, 12)
                + "-"
                + valor.substring(12);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof CNPJ cnpj)) return false;

        return Objects.equals(valor, cnpj.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return formatar();
    }
}
