package br.com.genciv.cliente.infrastructure.persistence.memory;

import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CNPJ;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.domain.valueobject.ClienteId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ClienteRepositoryEmMemoria implements ClienteRepository {

    private final Map<ClienteId, Cliente> banco = new HashMap<>();

    @Override
    public Cliente salvar(Cliente cliente) {

        banco.put(cliente.getId(), cliente);

        return cliente;
    }

    @Override
    public Optional<Cliente> buscarPorId(ClienteId id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public Optional<Cliente> buscarPorCpf(CPF cpf) {
        return banco.values()
                .stream()
                .filter(PessoaFisica.class::isInstance)
                .map(PessoaFisica.class::cast)
                .filter(cliente ->
                        cliente.getCPF()
                                .map(cpf::equals)
                                .orElse(false)
                )
                .map(Cliente.class::cast)
                .findFirst();
    }

    @Override
    public Optional<Cliente> buscarPorCnpj(CNPJ cnpj) {
        return banco.values()
                .stream()
                .filter(PessoaJuridica.class::isInstance)
                .map(PessoaJuridica.class::cast)
                .filter(cliente ->
                        cliente.getCnpj()
                                .map(cnpj::equals)
                                .orElse(false)
                )
                .map(Cliente.class::cast)
                .findFirst();
    }

    @Override
    public boolean existePorCpf(CPF cpf) {
        return buscarPorCpf(cpf).isPresent();
    }

    @Override
    public boolean existePorCnpj(CNPJ cnpj) {
        return buscarPorCnpj(cnpj).isPresent();
    }

    @Override
    public void remover(ClienteId id) {
        banco.remove(id);
    }


}
