package cliente.domain.valueobject;

import cliente.domain.exception.DocumentoInvalidoException;

public class CEP {

    private final String valor;

    public CEP(String valor){

        String valorLimpo = valor.replaceAll("[^0-9]", "");

        if (valorLimpo.length() != 8){
            throw new DocumentoInvalidoException("CEP inválido");
        }
        this.valor = valorLimpo;
    }

    public String getValor() {
        return valor;
    }
}
