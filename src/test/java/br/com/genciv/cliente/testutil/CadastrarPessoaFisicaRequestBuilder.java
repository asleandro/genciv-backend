package br.com.genciv.cliente.testutil;

import br.com.genciv.cliente.application.dto.CadastrarPessoaFisicaRequest;
import br.com.genciv.cliente.application.dto.EnderecoRequest;

public class CadastrarPessoaFisicaRequestBuilder {

    private String nomeCompleto = "João Moscada";
    private String email = "joao@email.com";
    private String ddd = "21";
    private String telefone = "912345678";
    private String cpf = "12345678909";
    private String dataNascimento = "01/02/1980";

    private EnderecoRequest endereco = new EnderecoRequest(
            "12345-678",
            "Rua Inventada",
            "100",
            null,
            "Madureira",
            "Rio de Janeiro",
            "RJ"
    );

    public static CadastrarPessoaFisicaRequestBuilder defaultBuilder() {
        return new CadastrarPessoaFisicaRequestBuilder();
    }

    public CadastrarPessoaFisicaRequestBuilder comNome(String nome) {
        this.nomeCompleto = nome;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder comTelefone(String ddd, String numero) {
        this.ddd = ddd;
        this.telefone = numero;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder comCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder semCpf() {
        this.cpf = null;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder comDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder semDataNascimento() {
        this.dataNascimento = null;
        return this;
    }

    public CadastrarPessoaFisicaRequestBuilder comEndereco(EnderecoRequest endereco) {
        this.endereco = endereco;
        return this;
    }

    public CadastrarPessoaFisicaRequest build() {
        return new CadastrarPessoaFisicaRequest(
                nomeCompleto,
                email,
                ddd,
                telefone,
                endereco,
                cpf,
                dataNascimento
        );
    }

}
