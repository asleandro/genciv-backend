package cliente.domain.entity;

import cliente.domain.valueobject.*;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class PessoaJuridica extends Cliente {

    private CNPJ cnpj;
    private RazaoSocial razaoSocial;
    private String nomeFantasia;
    private InscricaoEstadual inscricaoEstadual;

    public PessoaJuridica(
            ClienteId id,
            Email email,
            Telefone telefone,
            Endereco endereco,
            CNPJ cnpj,
            RazaoSocial razaoSocial,
            String nomeFantasia,
            InscricaoEstadual inscricaoEstadual
    ) {
        super(id, email, telefone, endereco);

        this.cnpj = Objects.requireNonNull(cnpj);
        this.razaoSocial = Objects.requireNonNull(razaoSocial);
        this.nomeFantasia = Objects.requireNonNull(nomeFantasia);
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public boolean possuiInscricaoEstadual() {
        return inscricaoEstadual != null;
    }

    public Optional<InscricaoEstadual> getInscricaoEstadual() {
        return Optional.ofNullable(inscricaoEstadual);
    }

    public void alterarNomeFantasia(String nomeFantasia) {
        validarClienteAtivo();
        this.nomeFantasia = Objects.requireNonNull(nomeFantasia, "Preencha o campo com o nome");
    }

    public void alterarInscricaoEstadual(InscricaoEstadual inscricaoEstadual) {
        validarClienteAtivo();
        this.inscricaoEstadual = Objects.requireNonNull(inscricaoEstadual);
    }

}
