package br.com.genciv.cliente.testutil;

import br.com.genciv.cliente.application.dto.ContatoRequest;
import br.com.genciv.cliente.domain.enums.TipoContato;

public class ContatoRequestBuilder {

    private String nome = "Leco Moscardo";
    private String ddd = "21";
    private String telefone = "77777-7777";
    private String email = "leco@moscardo.com.br";
    private TipoContato tipo = TipoContato.SUPORTE;

    public static ContatoRequestBuilder defaultBuild() {
        return new ContatoRequestBuilder();
    }

    public ContatoRequestBuilder comNome(String nome) {
        this.nome = nome;
        return this;
    }

    public ContatoRequestBuilder semNome() {
        this.nome = null;
        return this;
    }

    public ContatoRequestBuilder comTelefone(String ddd, String telefone) {
        this.ddd = ddd;
        this.telefone = telefone;
        return this;
    }

    public ContatoRequestBuilder semTelefone() {
        this.ddd = null;
        this.telefone = null;
        return this;
    }

    public ContatoRequestBuilder comEmail(String email) {
        this.email = email;
        return this;
    }

    public ContatoRequestBuilder semEmail() {
        this.email = null;
        return this;
    }

    public ContatoRequestBuilder comTipo(TipoContato tipo) {
        this.tipo = tipo;
        return this;
    }

    public ContatoRequestBuilder semTipo() {
        this.tipo = null;
        return this;
    }

    public ContatoRequest build() {
        return new ContatoRequest(
                nome,
                ddd,
                telefone,
                email,
                tipo
        );
    }

}
