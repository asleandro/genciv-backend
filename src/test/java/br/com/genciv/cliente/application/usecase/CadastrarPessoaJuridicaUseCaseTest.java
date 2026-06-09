package br.com.genciv.cliente.application.usecase;

import br.com.genciv.cliente.application.dto.CadastrarPessoaJuridicaRequest;
import br.com.genciv.cliente.domain.entity.Contato;
import br.com.genciv.cliente.domain.entity.PessoaJuridica;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.repository.ClienteRepository;
import br.com.genciv.cliente.domain.valueobject.*;
import br.com.genciv.cliente.infrastructure.persistence.memory.ClienteRepositoryEmMemoria;
import br.com.genciv.cliente.testutil.CadastrarPessoaJuridicaRequestBuilder;
import br.com.genciv.shared.application.ClockProvider;
import br.com.genciv.shared.testutil.FakeClockProvider;
import br.com.genciv.shared.testutil.TestClocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CadastrarPessoaJuridicaUseCaseTest {

    private ClienteRepository repository;
    private ClockProvider clockProvider;
    private CadastrarPessoaJuridicaUseCase useCase;
    private CadastrarPessoaJuridicaRequest request;


    @BeforeEach
    void setup() {
        repository = new ClienteRepositoryEmMemoria();
        clockProvider = new FakeClockProvider(TestClocks.fixed());
        useCase = new CadastrarPessoaJuridicaUseCase(repository, clockProvider);
        request = CadastrarPessoaJuridicaRequestBuilder.defaultBuilder().build();
    }

    @Test
    public void deveCadastrarPessoaJuridicaComSucesso() {

        PessoaJuridica cliente = useCase.executar(request);

        assertThat(cliente).isNotNull();
        assertThat(cliente.getId()).isNotNull();

        assertThat(
                repository.existePorCnpj(
                        new CNPJ("11222333000181")
                )
        ).isTrue();

        assertThat(cliente.getCnpj())
                .hasValue(new CNPJ("11.222.333/0001-81"));

        assertThat(cliente.getNomeFantasia())
                .isEqualTo("Genciv Sistemas");

        assertThat(cliente.getRazaoSocial())
                .isEqualTo(new RazaoSocial("GENCIV SISTEMAS LTDA"));


        assertThat(cliente.getTelefone())
                .isEqualTo(new Telefone("(21)", "2450-3496"));

        assertThat(cliente.getEmail())
                .isEqualTo(new Email("GENCIV@EMAIL.COM"));


        assertThat(cliente.getEndereco().getCep())
                .isEqualTo(new CEP("12345-789"));

        assertThat(cliente.getDataCadastro())
                .isEqualTo(TestClocks.fixed());

        assertThat(cliente.getContatos())
                .hasSize(2);

        assertThat(cliente.getContatos())
                .extracting(Contato::getNome)
                .containsExactly(
                        "Leco Moscardo",
                        "Didi Efigênica"
                );

    }

    @Test
    @DisplayName("deve atribuir razão social ao nome fantasia quando o nome fantasia não for informado")
    public void deveAtribuirRazaoSocialAoNomeFantasiaQuandoNomeFantasiaNaoForInformado() {

        CadastrarPessoaJuridicaRequest requestSemNomeFantasia =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .semNomeFantasia()
                        .build();

        PessoaJuridica cliente = useCase.executar(requestSemNomeFantasia);

        assertThat(cliente.getNomeFantasia())
                .isEqualTo("genciv sistemas ltda");

    }

    @Test
    @DisplayName("deve lançar exceção quando nome fantasia e razão social não existirem")
    public void deveLancarExcecaoQuandoNomeFantasiaERazaoSocialNaoExistirem() {

        CadastrarPessoaJuridicaRequest requestSemNomeFantasiaERazaoSocial =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .semRazaoSocial()
                        .semNomeFantasia()
                        .build();

        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class,
                        () -> useCase.executar(requestSemNomeFantasiaERazaoSocial)
                );

        assertThat(exception.getMessage())
                .isEqualTo("Informe o nome fantasia ou a Razão Social");

    }

    @Test
    @DisplayName("deve cadastrar pessoa juridica sem CNPJ")
    public void deveCadastrarPessoaJuridicaSemCnpj() {

        CadastrarPessoaJuridicaRequest requestSemCnpj =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .comCnpj(null)
                        .build();

        PessoaJuridica cliente = useCase.executar(requestSemCnpj);

        assertThat(cliente).isNotNull();
        assertThat(cliente.getId()).isNotNull();
        assertThat(cliente.getCnpj()).isEmpty();

    }

    @Test
    @DisplayName("deve lançar exceção quando CNPJ já existir")
    public void deveLancarExcecaoQuandoCnpjJaExistir() {

        useCase.executar(request);

        CadastrarPessoaJuridicaRequest requestComCnpjDuplicado =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .semRazaoSocial()
                        .build();

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> useCase.executar(requestComCnpjDuplicado)
        );

        assertThat(exception)
                .hasMessageContaining("CNPJ");

    }

    @Test
    @DisplayName("deve lançar exceção quando razão social já existir")
    public void deveLancarExcecaoQuandoRazaoSocialJaExistir() {

        useCase.executar(request);

        CadastrarPessoaJuridicaRequest requestComRazaoSocialDuplicada =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .semNomeFantasia()
                        .comCnpj(null)
                        .build();

        RegraNegocioException exception = assertThrows(
                RegraNegocioException.class,
                () -> useCase.executar(requestComRazaoSocialDuplicada)
        );

        assertThat(exception)
                .hasMessageContaining("razão social");

    }

    @Test
    @DisplayName("deve considerar razão social duplicada ignorando maiúsculas e minúsculas")
    public void deveConsiderarRazaoSocialDuplicadaIgnorandoCapitalizacao() {

        useCase.executar(request);

        CadastrarPessoaJuridicaRequest requestComRazaoSocialDeDiferenteCapitalizacao =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .comRazaoSocial("GENCIV SISTEMAS LTDA")
                        .build();

        RegraNegocioException exception =
                assertThrows(RegraNegocioException.class,
                        () -> useCase.executar(requestComRazaoSocialDeDiferenteCapitalizacao)
                );

        assertThat(exception)
                .hasMessageContaining("razão social");
    }

    @Test
    @DisplayName("deve cadastrar pessoa jurídica sem contatos")
    public void deveCadastrarPessoaJuridicaSemContatos() {

        CadastrarPessoaJuridicaRequest requestSemContatos =
                CadastrarPessoaJuridicaRequestBuilder
                        .defaultBuilder()
                        .semContatos()
                        .build();

        PessoaJuridica cliente = useCase.executar(requestSemContatos);

        assertThat(cliente).isNotNull();
        assertThat(cliente.getContatos()).isEmpty();

    }

}
