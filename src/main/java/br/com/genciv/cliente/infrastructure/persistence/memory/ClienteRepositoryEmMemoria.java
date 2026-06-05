package br.com.genciv.cliente.infrastructure.persistence.memory;

import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CNPJ;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.domain.valueobject.ClienteId;
import br.com.genciv.cliente.domain.valueobject.RazaoSocial;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

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
    public Optional<PessoaFisica> buscarPorCpf(CPF cpf) {
        return pessoasFisicas()
                .filter(pf ->
                        pf.getCPF()
                                .map(cpf::equals)
                                .orElse(false)
                )
                .findFirst();
    }

    @Override
    public Optional<PessoaJuridica> buscarPorCnpj(CNPJ cnpj) {
        return pessoasJuridicas()
                .map(PessoaJuridica.class::cast)
                .filter(pj ->
                        pj.getCnpj()
                                .map(cnpj::equals)
                                .orElse(false)
                )
                .findFirst();
    }

    @Override
    public Optional<PessoaJuridica> buscarPorRazaoSocial(RazaoSocial razaoSocial) {
        return pessoasJuridicas()
                .filter(pj ->
                        pj.getRazaoSocial()
                                .equals(razaoSocial)
                )
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
    public boolean existePorRazaoSocial(RazaoSocial razaoSocial) {
        return buscarPorRazaoSocial(razaoSocial).isPresent();
    }

    @Override
    public void remover(ClienteId id) {
        banco.remove(id);
    }

    private Stream<PessoaJuridica> pessoasJuridicas() {
        return banco.values()
                .stream()
                .filter(PessoaJuridica.class::isInstance)
                .map(PessoaJuridica.class::cast);

    }

    private Stream<PessoaFisica> pessoasFisicas() {
        return banco.values()
                .stream()
                .filter(PessoaFisica.class::isInstance)
                .map(PessoaFisica.class::cast);

    }

}
