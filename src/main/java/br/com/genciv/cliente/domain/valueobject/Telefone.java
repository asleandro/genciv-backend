package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

public final class Telefone {

    private final String ddd;
    private final String numero;

    @Getter
    private final String valor;

    private static final int PREFIXO_FIXO = 4;
    private static final int PREFIXO_CELULAR = 5;

    private static final Pattern TELEFONE_VALIDO =
            Pattern.compile("[0-9()\\-\\s]+");

    public Telefone(String ddd, String numero) {

        if (ddd == null) {
            throw new ValueObjectInvalidoException("DDD não pode ser nulo");
        }

        if (numero == null) {
            throw new ValueObjectInvalidoException("Número não pode ser nulo");
        }

        validarCaracteresPermitidos("DDD", ddd);
        validarCaracteresPermitidos("Número", numero);


        this.ddd = normalizar("DDD", ddd);
        this.numero = normalizar("Número", numero);

        validar();

        this.valor = this.ddd + this.numero;
    }

    private void validarCaracteresPermitidos(String campo, String valor){
        if (!TELEFONE_VALIDO.matcher(valor).matches()) {
            throw new ValueObjectInvalidoException(campo + " possui caracteres inválidos");
        }
    }

    private void validar() {

        if (ddd.isBlank()) {
            throw new ValueObjectInvalidoException("DDD não pode ser vazio");
        }

        if (ddd.length() != 2) {
            throw new ValueObjectInvalidoException("DDD inválido");
        }

        if (numero.isBlank()) {
            throw new ValueObjectInvalidoException("Número não pode ser vazio");
        }

        if (numero.length() != 8 && numero.length() != 9) {
            throw new ValueObjectInvalidoException("Número inválido");
        }

    }

    private String normalizar(String campo, String valor) {

        return valor.replaceAll("\\D", "");

    }

    public String formatado() {
        int prefixo = ehCelular()
                ? PREFIXO_CELULAR
                : PREFIXO_FIXO;

        return "(" + ddd + ") "
                + numero.substring(0, prefixo)
                + "-"
                + numero.substring(prefixo);

    }

    public boolean ehFixo() {
        return numero.length() == 8;
    }

    public boolean ehCelular() {
        return numero.length() == 9;
    }

    @Override
    public String toString() {
        return formatado();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Telefone telefone)) return false;

        return Objects.equals(ddd, telefone.ddd) &&
                Objects.equals(numero, telefone.numero);

    }

    @Override
    public int hashCode() {
        return Objects.hash(ddd, numero);
    }
}
