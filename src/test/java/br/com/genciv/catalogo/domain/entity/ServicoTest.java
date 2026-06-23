package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.enums.UnidadeMedida;
import br.com.genciv.catalogo.domain.exception.ServicoDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicoTest {

    private Servico servico;

    @BeforeEach
    void setup() {
        servico = Servico.criar(
                "SERV01",
                "Descrição do serviço",
                UnidadeMedida.M2,
                true
        );
    }

    @Test
    public void deveCriarServicoValido() {

        assertThat(servico).isNotNull();

        assertThat(servico.getCodigo())
                .isEqualTo("SERV01");

        assertThat(servico.getDescricao())
                .isEqualTo("Descrição do serviço");

        assertThat(servico.getUnidadeMedida())
                .isEqualTo(UnidadeMedida.M2);

        assertThat(servico.isAtivo())
                .isTrue();

    }

    @Test
    public void deveDesativarServicoAtivo() {

        servico.desativar();

        assertThat(servico.isAtivo())
                .isFalse();
    }

    @Test
    public void deveAtivarServicoInativo() {

        Servico servicoInativo = Servico.criar(
                "SERV02",
                "Serviço inativo",
                UnidadeMedida.CJ,
                false
        );

        servicoInativo.ativar();

        assertThat(servicoInativo.isAtivo())
                .isTrue();
    }

    @Test
    public void deveAlterarDescricaoDoServico() {

        servico.alterarDescricao("Nova descrição");

        assertThat(servico.getDescricao())
                .isEqualTo("Nova descrição");
    }

    @Test
    void deveRemoverEspacosDaDescricao() {

        Servico servico2 = Servico.criar(
                "SERV03",
                "  Serviço Teste  ",
                UnidadeMedida.CJ,
                true
        );

        assertThat(servico2.getDescricao())
                .isEqualTo("Serviço Teste");
    }

    @Test
    public void deveLancarExcecaoAoCriarServicoComCodigoVazioOuNulo() {

        ServicoDominioException nuloException =
                assertThrows(ServicoDominioException.class,
                        () -> Servico.criar(
                                null,
                                "Serviço com código null",
                                UnidadeMedida.M,
                                true)
                );

        assertThat(nuloException).hasMessageContaining("Código");

        ServicoDominioException vazioException =
                assertThrows(ServicoDominioException.class,
                        () -> Servico.criar(
                                "",
                                "Serviço com código vazio",
                                UnidadeMedida.M,
                                true)
                );

        assertThat(vazioException).hasMessageContaining("Código");

    }

    @Test
    public void deveLancarExcecaoAoCriarServicoComDescricaoVaziaOuNula() {

        ServicoDominioException nuloException =
                assertThrows(ServicoDominioException.class,
                        () -> Servico.criar(
                                "SERV2",
                                null,
                                UnidadeMedida.CJ,
                                true)
                );

        assertThat(nuloException).hasMessageContaining("Descrição");

        ServicoDominioException vazioException =
                assertThrows(ServicoDominioException.class,
                        () -> Servico.criar(
                                "SERV03",
                                " ",
                                UnidadeMedida.CJ,
                                true)
                );

        assertThat(vazioException).hasMessageContaining("Descrição");

    }

    @Test
    public void deveLancarExcecaoAoCriarServicoComUnidadeMedidaNula() {

        NullPointerException nuloException =
                assertThrows(NullPointerException.class,
                        () -> Servico.criar(
                                "SERV04",
                                "Servico com unidade de medida nula",
                                null,
                                true)
                );

        assertThat(nuloException).hasMessageContaining("UnidadeMedida");

    }

    @Test
    void deveLancarExcecaoAoAlterarDescricaoParaNula() {

        ServicoDominioException exception =
                assertThrows(
                        ServicoDominioException.class,
                        () -> servico.alterarDescricao(null)
                );

        assertThat(exception)
                .hasMessageContaining("Descrição");
    }

    @Test
    void deveLancarExcecaoAoAlterarDescricaoParaVazia() {

        ServicoDominioException exception =
                assertThrows(
                        ServicoDominioException.class,
                        () -> servico.alterarDescricao(" ")
                );

        assertThat(exception)
                .hasMessageContaining("Descrição");
    }

}
