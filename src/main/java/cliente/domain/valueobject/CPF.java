package cliente.domain.valueobject;

import cliente.domain.exception.DocumentoInvalidoException;

public class CPF {

    private final String valor;

    public CPF(String valor){

        String cpfLimpo = valor.replaceAll("[^0-9]", "");

        if(cpfLimpo.length() != 11){
            throw new DocumentoInvalidoException("CPF inválido");
        }

        this.valor = cpfLimpo;
    }

    public String getValor(){
        return valor;
    }
}
