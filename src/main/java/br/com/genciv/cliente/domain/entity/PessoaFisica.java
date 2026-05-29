package br.com.genciv.cliente.domain.entity;

import br.com.genciv.cliente.domain.valueobject.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Getter
public class PessoaFisica extends Cliente{

    private CPF cpf;
    private String nomeCompleto;
    private LocalDate dataNascimento;

    public PessoaFisica(
            ClienteId id,
            Email email,
            Telefone telefone,
            Endereco endereco,
            String nomeCompleto,
            CPF cpf,
            LocalDate dataNascimento
    ){
        super(id, email, telefone, endereco);

        this.cpf = cpf;
        this.nomeCompleto = Objects.requireNonNull(nomeCompleto);
        this.dataNascimento = dataNascimento;
    }

    public Optional<CPF> getCPF(){
        return Optional.ofNullable(cpf);
    }



}
