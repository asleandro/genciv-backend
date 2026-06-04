package br.com.genciv.cliente.application.dto;

public record CadastrarPessoaJuridicaRequest(

        String nomeFantasia,
        String email,
        String ddd,
        String telefone,

        String razaoSocial,
        String cnpj,
        String inscricaoEstadual,
        String inscricaoMunicipal,

        EnderecoRequest endereco

) {
}
