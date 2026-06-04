package br.com.genciv.cliente.domain.entity;

import br.com.genciv.cliente.domain.valueobject.*;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class PessoaJuridica extends Cliente {

    private CNPJ cnpj;
    private RazaoSocial razaoSocial;
    private String nomeFantasia;
    private InscricaoEstadual inscricaoEstadual;
    private InscricaoMunicipal inscricaoMunicipal;

    public PessoaJuridica(
            ClienteId id,
            Email email,
            Telefone telefone,
            Endereco endereco,
            String nomeFantasia,
            RazaoSocial razaoSocial,
            CNPJ cnpj,
            InscricaoEstadual inscricaoEstadual,
            InscricaoMunicipal inscricaoMunicipal
    ) {
        super(id, email, telefone, endereco);

        this.cnpj = cnpj;
        this.razaoSocial = razaoSocial;
        this.nomeFantasia = Objects.requireNonNull(nomeFantasia);
        this.inscricaoEstadual = inscricaoEstadual;
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public boolean possuiInscricaoEstadual() {
        return inscricaoEstadual != null;
    }

    public boolean possuiInscricaoMunicipal() {
        return inscricaoMunicipal != null;
    }

    public Optional<InscricaoEstadual> getInscricaoEstadual() {
        return Optional.ofNullable(inscricaoEstadual);
    }

    public Optional<InscricaoMunicipal> getInscricaoMunicipal() {
        return Optional.ofNullable(inscricaoMunicipal);
    }

    public Optional<CNPJ> getCnpj() {
        return Optional.ofNullable(cnpj);
    }

    public void alterarNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = Objects.requireNonNull(nomeFantasia, "Preencha o campo com o nome");
        registrarHistorico("NOME_FANTASIA_ALTERADO");
    }

    public void alterarInscricaoEstadual(InscricaoEstadual inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
        registrarHistorico("INSCRICAO_ESTADUAL_ALTERADA");
    }

}
