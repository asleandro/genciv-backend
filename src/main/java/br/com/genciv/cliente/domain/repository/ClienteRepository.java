package br.com.genciv.cliente.domain.repository;

import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.valueobject.CNPJ;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.domain.valueobject.ClienteId;
import br.com.genciv.cliente.domain.valueobject.RazaoSocial;

import java.util.Optional;

public interface ClienteRepository {

    Cliente salvar(Cliente cliente);

    Optional<Cliente> buscarPorId(ClienteId id);

    Optional<PessoaFisica> buscarPorCpf(CPF cpf);

    Optional<PessoaJuridica> buscarPorCnpj(CNPJ cnpj);

    Optional<PessoaJuridica> buscarPorRazaoSocial(RazaoSocial razaoSocial);

    boolean existePorCpf(CPF cpf);

    boolean existePorCnpj(CNPJ cnpj);

    boolean existePorRazaoSocial(RazaoSocial razaoSocial);

    void remover(ClienteId id);

}
