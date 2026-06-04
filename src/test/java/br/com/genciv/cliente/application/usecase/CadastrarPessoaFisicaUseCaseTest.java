package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaFisicaRequest;
import br.com.genciv.cliente.domain.entity.Cliente;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.CPF;
import br.com.genciv.cliente.infrastructure.persistence.memory.ClienteRepositoryEmMemoria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class CadastrarPessoaFisicaUseCaseTest {

    ClienteRepository repository;
    CadastrarPessoaFisicaUseCase useCase;

    @BeforeEach
    void setup() {
        repository = new ClienteRepositoryEmMemoria();
        useCase = new CadastrarPessoaFisicaUseCase(repository);
    }

    CadastrarPessoaFisicaRequest request = new CadastrarPessoaFisicaRequest(
            "João Silva",
            "joao@silva.com",
            "21",
            "91234-5678",
            "12345-678",
            "Rua Inventada",
            "100",
            null,
            "Madureira",
            "Rio de Janeiro",
            "RJ",
            "12345678909",
            "01/02/1980"
    );

    @Test
    public void deveCadastrarClienteValido() {

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
                        "12345678",
                        "Rua A",
                        "100",
                        null,
                        "Centro",
                        "São Paulo",
                        "SP",
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
                        "12345678",
                        "Rua A",
                        "100",
                        null,
                        "Centro",
                        "São Paulo",
                        "SP",
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

