package br.com.genciv.cliente.domain.valueobject;

public class Telefone {

    private final String ddd;
    private final String numero;

    public Telefone(String ddd, String numero){

        this.ddd = ddd;
        this.numero = numero;
    }
}
