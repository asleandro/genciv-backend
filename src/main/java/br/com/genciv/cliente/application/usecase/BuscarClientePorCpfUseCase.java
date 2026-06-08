package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.exception.ClienteNaoEncontradoException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CPF;

public class BuscarClientePorCpfUseCase {

    private final ClienteRepository repository;

    public BuscarClientePorCpfUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public PessoaFisica executar(String cpf) {

        return repository.buscarPorCpf(new CPF(cpf))
                .orElseThrow(() ->
                        new ClienteNaoEncontradoException("Cliente não encontrado")
                );

    }

}
