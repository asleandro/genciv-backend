package cliente.domain.entity;

import cliente.domain.valueobject.*;
import lombok.Getter;

import java.time.LocalDate;

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
            CPF cpf,
            String nomeCompleto,
            LocalDate dataNascimento
    ){
        super(id, email, telefone, endereco);

        this.cpf = cpf;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
    }

}
