package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaFisicaRequest;
import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.enums.UnidadeFederativa;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CadastrarClienteUseCase {

    private final ClienteRepository repository;

    public CadastrarClienteUseCase(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente executar(CadastrarPessoaFisicaRequest request) {

        CPF cpf = request.cpf() != null
                ? new CPF(request.cpf())
                : null;

        if (cpf != null && repository.existePorCpf(cpf)) {
            throw new RegraNegocioException("CPF já cadastrado");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        LocalDate dataNascimento = request.dataNascimento() != null
                ? LocalDate.parse(
                request.dataNascimento(),
                formatter
                )
                : null;

        PessoaFisica cliente =
                new PessoaFisica(
                        ClienteId.novo(),
                        new Email(request.email()),
                        new Telefone(
                                request.ddd(),
                                request.telefone()
                        ),
                        criarEndereco(request),
                        request.nomeCompleto(),
                        cpf,
                        dataNascimento
                );

        return repository.salvar(cliente);
    }

    private Endereco criarEndereco(CadastrarPessoaFisicaRequest request) {

        UnidadeFederativa uf = request.uf() != null
                ? UnidadeFederativa.valueOf(request.uf().trim().toUpperCase())
                : null;

        CEP cep = request.cep() != null ? new CEP(request.cep()) : null;

        return new Endereco(
                request.logradouro(),
                request.numero(),
                request.complemento(),
                request.bairro(),
                request.cidade(),
                uf,
                cep);
    }

}
