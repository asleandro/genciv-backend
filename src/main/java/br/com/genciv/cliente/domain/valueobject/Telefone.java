package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;

import java.util.Objects;

public final class Telefone {

    private final String ddd;
    private final String numero;

    private static final int PREFIXO_FIXO = 4;
    private static final int PREFIXO_CELULAR = 5;

    public Telefone(String ddd, String numero) {

        this.ddd = normalizar("DDD", ddd);
        this.numero = normalizar("número", numero);

        validar();
    }

    private void validar() {

        if (ddd.isBlank()) {
            throw new ValueObjectInvalidoException("DDD não pode ser vazio");
        }

        if (ddd.length() != 2) {
            throw new ValueObjectInvalidoException("DDD inválido");
        }

        if (numero.isBlank()) {
            throw new ValueObjectInvalidoException("número não pode ser vazio");
        }

        if (numero.length() != 8 && numero.length() != 9) {
            throw new ValueObjectInvalidoException("Número inválido");
        }

    }

    private String normalizar(String campo, String valor) {

        if (valor == null) {
            throw new ValueObjectInvalidoException(campo + " não pode ser nulo");
        }

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
