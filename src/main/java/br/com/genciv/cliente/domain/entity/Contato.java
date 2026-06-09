package br.com.genciv.cliente.domain.entity;

import br.com.genciv.cliente.domain.enums.TipoContato;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.valueobject.ContatoId;
import br.com.genciv.cliente.domain.valueobject.Email;
import br.com.genciv.cliente.domain.valueobject.Telefone;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Contato {

    private final ContatoId id;
    private String nome;
    private Telefone telefone;
    private Email email;
    private TipoContato tipo;

    private Contato(
            ContatoId id,
            String nome,
            Telefone telefone,
            Email email,
            TipoContato tipo
    ) {
        this.id = Objects.requireNonNull(id);
        this.nome = validarNome(nome);
        this.telefone = Objects.requireNonNull(
                telefone,
                "Telefone é obrigatório"
        );
        this.email = email;
        this.tipo = Objects.requireNonNull(
                tipo,
                "Tipo de contato é obrigatório"
        );
    }

    public static Contato criar(
            String nome,
            Telefone telefone,
            Email email,
            TipoContato tipo
    ) {
        return new Contato(
                ContatoId.novo(),
                nome,
                telefone,
                email,
                tipo
        );
    }

    public void alterarNome(String nome) {
        this.nome = validarNome(nome);
    }

    public void alterarTelefone(Telefone telefone) {
        this.telefone = Objects.requireNonNull(telefone, "Telefone é obrigatório");
    }

    public void alterarEmail(Email email) {
        this.email = email;
    }

    public void alterarTipo(TipoContato tipo) {
        this.tipo = Objects.requireNonNull(tipo, "Tipo de contato é obrigatório");
    }

    private String validarNome(String nome) {

        Objects.requireNonNull(nome, "Nome do contato é obrigatório");

        if (nome.isBlank()) {
            throw new RegraNegocioException(
                    "Nome do contato é obrigatório"
            );
        }

        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Contato contato)) {
            return false;
        }
        return Objects.equals(id, contato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
