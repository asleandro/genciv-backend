package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.exception.ClienteNaoEncontradoException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.ClienteId;

public class BuscarClientePorIdUseCase {

    private final ClienteRepository repository;

    public BuscarClientePorIdUseCase(ClienteRepository repository){
        this.repository = repository;
    }

    public Cliente executar(ClienteId id){

        return repository.buscarPorId(id)
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não econtrado"));

    }

}
