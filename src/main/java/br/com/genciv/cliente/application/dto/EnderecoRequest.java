package br.com.genciv.cliente.application.dto;

public record EnderecoRequest(

        String cep,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cidade,
        String uf
) {

}
