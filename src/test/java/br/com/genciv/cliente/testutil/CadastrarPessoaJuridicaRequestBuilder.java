package br.com.genciv.cliente.testutil;

import br.com.genciv.cliente.application.dto.CadastrarPessoaJuridicaRequest;
import br.com.genciv.cliente.application.dto.ContatoRequest;
import br.com.genciv.cliente.application.dto.EnderecoRequest;
import br.com.genciv.cliente.domain.enums.TipoContato;

import java.util.List;

public class CadastrarPessoaJuridicaRequestBuilder {

    private String nomeFantasia = "Genciv Sistemas";
    private String email = "genciv@email.com";
    private String ddd = "21";
    private String telefone = "24503496";
    private String razaoSocial = "genciv sistemas ltda";
    private String cnpj = "11222333000181";
    private String inscricaoEstadual = "85687085";
    private String inscricaoMunicipal = "1234567";
    private EnderecoRequest endereco = new EnderecoRequest(
            "12345-789",
            "Rua Inventada",
            "100",
            null,
            "Madureira",
            "Rio de Janeiro",
            "RJ"
    );
    private List<ContatoRequest> contatos = List.of(
            new ContatoRequest(
                    "Leco Moscardo",
                    "21",
                    "98989-7676",
                    "joao@moscardo.com.br",
                    TipoContato.COMERCIAL
            ),
            new ContatoRequest(
                    "Didi Efigênica",
                    "21",
                    "99999-1010",
                    "didi@sereinha.com.br",
                    TipoContato.VENDAS
            )
    );

    public static CadastrarPessoaJuridicaRequestBuilder defaultBuilder() {
        return new CadastrarPessoaJuridicaRequestBuilder();
    }

    public CadastrarPessoaJuridicaRequestBuilder comNomeFantasia(String nome) {
        this.nomeFantasia = nome;
        return this;
    }

    public CadastrarPessoaJuridicaRequestBuilder semNomeFantasia() {
        this.nomeFantasia = null;
        return this;
    }

    public CadastrarPessoaJuridicaRequestBuilder comRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
        return this;
    }

    public CadastrarPessoaJuridicaRequestBuilder semRazaoSocial() {
        this.razaoSocial = null;
        return this;
    }

    public CadastrarPessoaJuridicaRequestBuilder comCnpj(String cnpj) {
        this.cnpj = cnpj;
        return this;
    }

    public CadastrarPessoaJuridicaRequestBuilder comContatos(List<ContatoRequest> contatos) {
        this.contatos = contatos;
        return this;
    }

    public CadastrarPessoaJuridicaRequestBuilder semContatos() {
        this.contatos = List.of();
        return this;
    }

    public CadastrarPessoaJuridicaRequest build() {
        return new CadastrarPessoaJuridicaRequest(
                nomeFantasia,
                email,
                ddd,
                telefone,
                razaoSocial,
                cnpj,
                inscricaoEstadual,
                inscricaoMunicipal,
                endereco,
                contatos
        );
    }
}