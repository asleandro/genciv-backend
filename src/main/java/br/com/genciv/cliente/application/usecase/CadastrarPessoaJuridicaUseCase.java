package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaJuridicaRequest;
import br.com.genciv.cliente.application.mapper.EnderecoMapper;
import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.*;
import br.com.genciv.shared.application.ClockProvider;

import java.time.LocalDateTime;

import static br.com.genciv.shared.util.StringUtils.isBlank;

public class CadastrarPessoaJuridicaUseCase {

    private final ClienteRepository repository;
    private final ClockProvider clockProvider;

    public CadastrarPessoaJuridicaUseCase(ClienteRepository repository, ClockProvider clockProvider) {
        this.repository = repository;
        this.clockProvider = clockProvider;
    }

    public PessoaJuridica executar(CadastrarPessoaJuridicaRequest request) {

        LocalDateTime dataCadastro = clockProvider.now();

        validarNomeEmpresa(request);

        String nomeFantasia = request.nomeFantasia();

        if (isBlank(nomeFantasia)) {
            nomeFantasia = request.razaoSocial();
        }

        PessoaJuridica cliente = new PessoaJuridica(
                ClienteId.novo(),
                new Email(request.email()),
                new Telefone(
                        request.ddd(),
                        request.telefone()
                ),
                EnderecoMapper.toDomain(request.endereco()),
                nomeFantasia,
                new RazaoSocial(request.razaoSocial()),
                new CNPJ(request.cnpj()),
                new InscricaoEstadual(request.inscricaoEstadual()),
                new InscricaoMunicipal(request.inscricaoMunicipal()),
                dataCadastro
        );

        repository.salvar(cliente);
        return cliente;
    }

    private void validarNomeEmpresa(CadastrarPessoaJuridicaRequest request) {

        if (isBlank(request.nomeFantasia())
                && isBlank(request.razaoSocial())) {

            throw new RegraNegocioException(
                    "Informe o nome fantasia ou a Razão Social"
            );
        }
    }

}

