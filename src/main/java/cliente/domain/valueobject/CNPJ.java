package cliente.domain.valueobject;

import cliente.domain.exception.DocumentoInvalidoException;

public class CNPJ {

    private final String valor;

    public CNPJ(String valor){
        String cnpjLimpo = valor.replaceAll("[^0-9]", "");

        if(cnpjLimpo.length() != 14){
            throw new DocumentoInvalidoException("CNPJ inválido");
        }
        this.valor = cnpjLimpo;
    }

    public String getValor() {
        return valor;
    }
}
