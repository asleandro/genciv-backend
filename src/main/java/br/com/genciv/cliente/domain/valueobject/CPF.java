package br.com.genciv.cliente.domain.valueobject;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.genciv.cliente.domain.exception.DocumentoInvalidoException;

import java.io.Serializable;
import java.util.Objects;

public final class CPF implements Serializable {

    private static final CPFValidator VALIDATOR = new CPFValidator();

    private final String valor;

    public CPF(String valor) {

        if (valor == null) {
            throw new DocumentoInvalidoException("CPF não pode ser nulo");
        }

        if(valor.isBlank()){
            throw new DocumentoInvalidoException("CPF não pode ser vazio");
        }

        String cpfLimpo = valor.replaceAll("[^0-9]", "");

        try {
            VALIDATOR.assertValid(cpfLimpo);
        } catch (InvalidStateException e) {
            throw new DocumentoInvalidoException("CPF informado é inválido");
        }

        this.valor = cpfLimpo;
    }

    public String getNumero() {
        return valor;
    }

    public String formatar() {
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
        return formatar();
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
