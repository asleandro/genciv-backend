package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.exception.ClienteNaoEncontradoException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CNPJ;

public class BuscarClientePorCnpjUseCase {

    private final ClienteRepository repository;

    public BuscarClientePorCnpjUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public PessoaJuridica executar(String cnpj) {

        return repository.buscarPorCnpj(new CNPJ(cnpj))
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado")
                );
    }

}
