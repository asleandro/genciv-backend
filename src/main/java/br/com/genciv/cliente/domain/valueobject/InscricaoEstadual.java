package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.InscricaoEstadualInvalidaException;
import lombok.Getter;

import java.util.Objects;

@Getter
public final class InscricaoEstadual {

    private static final int TAMANHO_MINIMO = 2;
    private static final int TAMANHO_MAXIMO = 20;
    private final String valor;

    public InscricaoEstadual(String valor){

        Objects.requireNonNull(valor, "Inscrição estadual é obrigatória");

        String valorLimpo = valor.replaceAll("[^0-9]", "");

        validar(valorLimpo);

        this.valor = valorLimpo;
    }

    private void validar(String valor){
        if(valor.isBlank()){
            throw new InscricaoEstadualInvalidaException("Insrição estadual inválida");
        }

        if(valor.length() < TAMANHO_MINIMO || valor.length() > TAMANHO_MAXIMO){
            throw new InscricaoEstadualInvalidaException("Inscrição estadual fora do padrão esperado");
        }
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

        if (!(o instanceof InscricaoEstadual inscricaoEstadual)) return false;

        return Objects.equals(valor, inscricaoEstadual.valor);
    }


}
