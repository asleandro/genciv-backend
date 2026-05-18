package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.InscricaoEstadualInvalidaException;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public final class InscricaoEstadual {

    private static final int TAMANHO_MINIMO = 2;
    private static final int TAMANHO_MAXIMO = 20;
    private static final Pattern APENAS_NUMEROS = Pattern.compile("[^0-9]");
    private final String valor;

    //TODO: Incluir validação real para Inscrição estadual

    public InscricaoEstadual(String valor){

        if (valor == null) {
            throw new InscricaoEstadualInvalidaException("Inscrição estadual é obrigatória");
        }

        String valorLimpo = APENAS_NUMEROS
                .matcher(valor.trim())
                .replaceAll("");

        validar(valorLimpo);

        this.valor = valorLimpo;
    }

    private void validar(String valor){
        if(valor.isEmpty()){
            throw new InscricaoEstadualInvalidaException("Valor informado não pode ser vazio");
        }

        if(valor.length() < TAMANHO_MINIMO || valor.length() > TAMANHO_MAXIMO){
            throw new InscricaoEstadualInvalidaException("IE deve possuir entre 2 e 20 dígitos");
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
