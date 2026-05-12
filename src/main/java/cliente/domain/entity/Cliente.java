package cliente.domain.entity;

import cliente.domain.enums.StatusCliente;
import cliente.domain.exception.ClienteInativoException;
import cliente.domain.valueobject.ClienteId;
import cliente.domain.valueobject.Email;
import cliente.domain.valueobject.Endereco;
import cliente.domain.valueobject.Telefone;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public abstract class Cliente {

    private final ClienteId id;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;
    private StatusCliente status;
    private final LocalDateTime dataCadastro;

    protected Cliente(
            ClienteId id,
            Email email,
            Telefone telefone,
            Endereco endereco
    ){
        this.id = Objects.requireNonNull(id);
        this.email = Objects.requireNonNull(email);
        this.telefone = Objects.requireNonNull(telefone);
        this.endereco = Objects.requireNonNull(endereco);
        this.status = StatusCliente.ATIVO;
        this.dataCadastro = LocalDateTime.now();
    }

    public void alterarEndereco(Endereco endereco){
        validarClienteAtivo();
        this.endereco = Objects.requireNonNull(endereco);
    }

    public void alterarTelefone(Telefone telefone){
        validarClienteAtivo();
        this.telefone = Objects.requireNonNull(telefone);
    }

    public void desativar(){

        if (this.status == StatusCliente.INATIVO) {
            return;
        }
        this.status = StatusCliente.INATIVO;
    }

    protected void validarClienteAtivo(){
        if(this.status == StatusCliente.INATIVO){
            throw new ClienteInativoException(this.id.getValor());
        }
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
