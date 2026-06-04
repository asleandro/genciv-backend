package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaFisicaRequest;
import br.com.genciv.cliente.application.mapper.EnderecoMapper;
import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.domain.valueobject.ClienteId;
import br.com.genciv.cliente.domain.valueobject.Email;
import br.com.genciv.cliente.domain.valueobject.Telefone;
import br.com.genciv.shared.application.ClockProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CadastrarPessoaFisicaUseCase {

    private final ClienteRepository repository;
    private final ClockProvider clockProvider;

    public CadastrarPessoaFisicaUseCase(ClienteRepository repository, ClockProvider clockProvider) {
        this.repository = repository;
        this.clockProvider = clockProvider;
    }

    public PessoaFisica executar(CadastrarPessoaFisicaRequest request) {

        LocalDateTime dataCadastro = clockProvider.now();

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
                        EnderecoMapper.toDomain(request.endereco()),
                        request.nomeCompleto(),
                        cpf,
                        dataNascimento,
                        dataCadastro
                );

        repository.salvar(cliente);
        return cliente;
    }

}
