package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.InscricaoEstadualInvalidaException;
import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public final class InscricaoMunicipal {

    private final String valor;

    public InscricaoMunicipal(String valor) {

        if (valor == null || valor.isBlank()) {
            throw new ValueObjectInvalidoException(
                    "Inscrição municipal inválida"
            );
        }

        this.valor = valor.trim();
    }

    @Override
    public int hashCode(){
        return Objects.hash(valor);
    }

    @Override
    public String toString(){
        return valor;
    }

    @Override
    public boolean equals(Object o){

        if (this == o) return true;

        if (!(o instanceof InscricaoMunicipal inscricaoEstadual)) return false;

        return Objects.equals(valor, inscricaoEstadual.valor);
    }


}
