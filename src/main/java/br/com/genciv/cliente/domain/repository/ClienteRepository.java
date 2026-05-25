package br.com.genciv.cliente.domain.repository;

import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.valueobject.CNPJ;
import br.com.genciv.cliente.domain.valueobject.CPF;

import java.util.Optional;
import java.util.UUID;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(UUID id);

    Optional<Cliente> buscarPorCpf(CPF cpf);

    Optional<Cliente> buscarPorCnpj(CNPJ cnpj);

    boolean existePorCpf(CPF cpf);

    boolean existePorCnpj(CNPJ cnpj);

    void remover(UUID id);

}
