package cliente.domain.valueobject;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import cliente.domain.exception.DocumentoInvalidoException;

import java.util.Objects;

public class CNPJ {

    private static final CNPJValidator validator = new CNPJValidator();

    private final String valor;

    public CNPJ(String valor) {

        if (valor == null) {
            throw new DocumentoInvalidoException("CNPJ não pode ser nulo");
        }

        String cnpjLimpo = valor.replaceAll("[^0-9]", "");

        try {
            validator.assertValid(cnpjLimpo);
        } catch (InvalidStateException e) {
            throw new DocumentoInvalidoException("CNPJ inválido");
        }
        this.valor = cnpjLimpo;
    }

    public String getValor() {
        return valor;
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
}
