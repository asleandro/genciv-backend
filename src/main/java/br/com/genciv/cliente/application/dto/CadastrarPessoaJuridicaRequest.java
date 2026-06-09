package br.com.genciv.cliente.application.dto;

import java.util.List;

public record CadastrarPessoaJuridicaRequest(

        String nomeFantasia,
        String email,
        String ddd,
        String telefone,
        String razaoSocial,
        String cnpj,
        String inscricaoEstadual,
        String inscricaoMunicipal,
        EnderecoRequest endereco,
        List<ContatoRequest> contatos

) {
}
