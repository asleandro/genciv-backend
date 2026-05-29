package br.com.genciv.cliente.application.dto;

import br.com.genciv.cliente.domain.enums.UnidadeFederativa;

public record CadastrarPessoaFisicaRequest(

    String nomeCompleto,
    String email,
    String ddd,
    String telefone,

    String cep,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String uf,

    String cpf,
    String dataNascimento
) {
}
