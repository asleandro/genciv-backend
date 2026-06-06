package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaFisicaRequest;
import br.com.genciv.cliente.domain.entity.PessoaFisica;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CEP;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.domain.valueobject.Email;
import br.com.genciv.cliente.domain.valueobject.Telefone;
import br.com.genciv.cliente.infrastructure.persistence.memory.ClienteRepositoryEmMemoria;
import br.com.genciv.cliente.testutil.CadastrarPessoaFisicaRequestBuilder;
import br.com.genciv.shared.application.ClockProvider;
import br.com.genciv.shared.testutil.FakeClockProvider;
import br.com.genciv.shared.testutil.TestClocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class CadastrarPessoaFisicaUseCaseTest {

    private ClienteRepository repository;
    private ClockProvider clockProvider;
    private CadastrarPessoaFisicaUseCase useCase;
    private CadastrarPessoaFisicaRequest request;

    @BeforeEach
    void setup() {
        repository = new ClienteRepositoryEmMemoria();
        clockProvider = new FakeClockProvider(TestClocks.fixed());
        useCase = new CadastrarPessoaFisicaUseCase(repository, clockProvider);
        request = CadastrarPessoaFisicaRequestBuilder.defaultBuilder().build();
    }

    @Test
    public void deveCadastrarPessoaFisicaComSucesso() {

        PessoaFisica cliente = useCase.executar(request);

        assertThat(cliente).isNotNull();
        assertThat(cliente.getId()).isNotNull();

        assertThat(
                repository.existePorCpf(
                        new CPF("123.456.789-09"))
        ).isTrue();

        assertThat(cliente.getNomeCompleto())
                .isEqualTo("João Moscada");

        assertThat(cliente.getDataNascimento())
                .isEqualTo(LocalDate.of(1980, 2, 1));

        assertThat(cliente.getCPF())
                .hasValue(new CPF("123.456.789-09"));

        assertThat(cliente.getEmail())
                .isEqualTo(new Email("JOAO@EMAIL.COM"));

        assertThat(cliente.getTelefone())
                .isEqualTo(new Telefone("(21)", "91234-5678"));

        assertThat(cliente.getEndereco().getCep())
                .isEqualTo(new CEP("12345678"));

        assertThat(cliente.getDataCadastro())
                .isEqualTo(TestClocks.fixed());

    }

    @Test
    void deveCadastrarPessoaFisicaSemCpf() {

        CadastrarPessoaFisicaRequest requestSemCpf =
                CadastrarPessoaFisicaRequestBuilder
                        .defaultBuilder()
                        .semCpf()
                        .build();

        PessoaFisica cliente = useCase.executar(requestSemCpf);

        assertThat(cliente).isNotNull();
    }

    @Test
    void deveCadastrarPessoaFisicaSemDataNascimento() {

        CadastrarPessoaFisicaRequest requestSemDataNascimento =
                CadastrarPessoaFisicaRequestBuilder
                        .defaultBuilder()
                        .semDataNascimento()
                        .build();

        PessoaFisica cliente = useCase.executar(requestSemDataNascimento);

        assertThat(cliente).isNotNull();
    }

    @Test
    public void deveLancarExcecaoQuandoCpfJaExistir() {

        useCase.executar(request);

        CadastrarPessoaFisicaRequest requestComCpfDuplicado =
                CadastrarPessoaFisicaRequestBuilder.defaultBuilder().build();

        RegraNegocioException exception = assertThrows(RegraNegocioException.class,
                () -> useCase.executar(requestComCpfDuplicado));

        assertThat(exception).hasMessageContaining("CPF");

    }


}

