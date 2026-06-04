package br.com.genciv.cliente.application.dto;

import br.com.genciv.cliente.domain.enums.UnidadeFederativa;

public record CadastrarPessoaFisicaRequest(

    String nomeCompleto,
    String email,
    String ddd,
    String telefone,

    EnderecoRequest endereco,

    String cpf,
    String dataNascimento
) {
}
