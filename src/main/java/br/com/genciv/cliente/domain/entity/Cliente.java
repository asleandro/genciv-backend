package br.com.genciv.cliente.domain.entity;

import br.com.genciv.cliente.domain.valueobject.ClienteId;
import br.com.genciv.cliente.domain.valueobject.Email;
import br.com.genciv.cliente.domain.valueobject.Endereco;
import br.com.genciv.cliente.domain.valueobject.Telefone;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public abstract class Cliente {

    private final ClienteId id;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;
    private final LocalDateTime dataCadastro;

    protected Cliente(
            ClienteId id,
            Email email,
            Telefone telefone,
            Endereco endereco
    ){
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = LocalDateTime.now();
    }

    public void alterarEndereco(Endereco endereco){
        this.endereco = endereco;

        registrarHistorico("ENDERECO_ALTERADO");
    }

    public void alterarTelefone(Telefone telefone){
        this.telefone = telefone;

        registrarHistorico("TELEFONE_ALTERADO");
    }

    protected void registrarHistorico(String evento) {
        //TODO: implementar futuramente: domain events, audit trail, outbox

        /* placeholder para futuramente evoluir para:
        * domain events
        * audit trail
        * outbox pattern
        * event sourcing parcial
        * logs operacionais
         */
    }

    @Override
    public boolean equals(Object o){

        if (this == o) return true;

        if (!(o instanceof Cliente cliente)){
            return false;
        }
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }

}
