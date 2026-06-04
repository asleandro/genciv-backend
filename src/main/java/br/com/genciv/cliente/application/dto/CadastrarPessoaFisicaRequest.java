package br.com.genciv.cliente.application.dto;

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
