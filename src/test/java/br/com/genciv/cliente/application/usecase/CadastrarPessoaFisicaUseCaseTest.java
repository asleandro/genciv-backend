package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaFisicaRequest;
import br.com.genciv.cliente.application.dto.EnderecoRequest;
import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.infrastructure.persistence.memory.ClienteRepositoryEmMemoria;
import br.com.genciv.shared.application.ClockProvider;
import br.com.genciv.shared.testutil.FakeClockProvider;
import br.com.genciv.shared.testutil.TestClocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CadastrarPessoaFisicaUseCaseTest {

    private ClienteRepository repository;
    private ClockProvider clockProvider;
    private CadastrarPessoaFisicaUseCase useCase;
    private EnderecoRequest endereco;
    private CadastrarPessoaFisicaRequest request;

    @BeforeEach
    void setup() {
        repository = new ClienteRepositoryEmMemoria();

        clockProvider = new FakeClockProvider(TestClocks.fixed());

        useCase = new CadastrarPessoaFisicaUseCase(repository, clockProvider);

        endereco = new EnderecoRequest(
                "12345-789",
                "Rua Inventada",
                "100",
                null,
                "Madureira",
                "Rio de Janeiro",
                "RJ"
        );

        request = new CadastrarPessoaFisicaRequest(
                "João Silva",
                "joao@silva.com",
                "21",
                "91234-5678",
                endereco,
                "12345678909",
                "01/02/1980"
        );
    }

    @Test
    public void deveCadastrarPessoaFisicaComSucesso() {

        Cliente cliente = useCase.executar(request);

        assertNotNull(cliente);
        assertNotNull(cliente.getId());
        assertTrue(repository.existePorCpf(new CPF("12345678909")));
    }

    @Test
    public void deveSalvarClienteNoRepository() {

        useCase.executar(request);

        assertTrue(repository.existePorCpf(new CPF("12345678909")));
    }

    @Test
    void deveCadastrarPessoaFisicaSemCpf() {

        CadastrarPessoaFisicaRequest request =
                new CadastrarPessoaFisicaRequest(
                        "João Silva",
                        "joao@email.com",
                        "11",
                        "999999999",
                        endereco,
                        null,
                        "01/01/1990"
                );

        Cliente cliente = useCase.executar(request);

        assertNotNull(cliente);
    }

    @Test
    void deveCadastrarPessoaFisicaSemDataNascimento() {

        CadastrarPessoaFisicaRequest request =
                new CadastrarPessoaFisicaRequest(
                        "João Silva",
                        "joao@email.com",
                        "11",
                        "999999999",
                        endereco,
                        null,
                        null
                );

        Cliente cliente = useCase.executar(request);

        assertNotNull(cliente);
    }

    @Test
    public void deveLancarExcecaoQuandoCpfJaExistir() {

        useCase.executar(request);

        RegraNegocioException exception = assertThrows(RegraNegocioException.class,
                () -> useCase.executar(request));

        assertThat(exception.getMessage()).isEqualTo("CPF já cadastrado");

    }


}

