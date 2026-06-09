package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaJuridicaRequest;
import br.com.genciv.cliente.application.mapper.ContatoMapper;
import br.com.genciv.cliente.application.mapper.EnderecoMapper;
import br.com.genciv.cliente.domain.entity.Contato;
import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.*;
import br.com.genciv.shared.application.ClockProvider;

import java.time.LocalDateTime;
import java.util.List;

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

        CNPJ cnpj = validarCnpj(request);

        String nomeFantasia = request.nomeFantasia();

        if (isBlank(nomeFantasia)) {
            nomeFantasia = request.razaoSocial();
        }

        List<Contato> contatos = request.contatos() == null
                ? List.of()
                : request.contatos()
                .stream()
                .map(ContatoMapper::toDomain)
                .toList();

        PessoaJuridica cliente = new PessoaJuridica(
                ClienteId.novo(),
                new Email(request.email()),
                new Telefone(
                        request.ddd(),
                        request.telefone()
                ),
                contatos,
                EnderecoMapper.toDomain(request.endereco()),
                nomeFantasia,
                new RazaoSocial(request.razaoSocial()),
                cnpj,
                new InscricaoEstadual(request.inscricaoEstadual()),
                new InscricaoMunicipal(request.inscricaoMunicipal()),
                dataCadastro
        );

        repository.salvar(cliente);
        return cliente;
    }

    private CNPJ validarCnpj(CadastrarPessoaJuridicaRequest request) {

        if (request.cnpj() == null) return null;

        CNPJ cnpj = new CNPJ(request.cnpj());

        if (repository.existePorCnpj(cnpj)) {
            throw new RegraNegocioException("Já existe cliente com o CNPJ informado");
        }

        return cnpj;
    }

    private void validarNomeEmpresa(CadastrarPessoaJuridicaRequest request) {

        if (isBlank(request.nomeFantasia())
                && isBlank(request.razaoSocial())) {

            throw new RegraNegocioException(
                    "Informe o nome fantasia ou a Razão Social"
            );
        }

        if (isBlank(request.razaoSocial())) {
            return;
        }

        RazaoSocial razaoSocial = new RazaoSocial(request.razaoSocial());

        if (repository.existePorRazaoSocial(razaoSocial)) {
            throw new RegraNegocioException(
                    "Já existe cliente com a razão social informada"
            );

        }
    }

}

