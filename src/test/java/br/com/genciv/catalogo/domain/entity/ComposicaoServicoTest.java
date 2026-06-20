package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.enums.UnidadeMedida;
import br.com.genciv.catalogo.domain.exception.CatalogoDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComposicaoServicoTest {

    private Servico drywall;
    private Material parafuso;
    private Material placaST;
    private ComposicaoServico composicaoServico;

    @BeforeEach
    void setup() {
        drywall = Servico.criar(
                "DW01",
                "Parede em gesso acartonado",
                UnidadeMedida.M2,
                true
        );

        parafuso = Material.criar(
                "PARAF01",
                "Parafuso ponta gulha com cabeça chata",
                UnidadeMedida.CENTO,
                true
        );

        placaST = Material.criar(
                "CHST",
                "Placa de gesso Standard",
                UnidadeMedida.M2,
                true
        );

        composicaoServico = new ComposicaoServico(
                drywall,
                "Parede em gesso acartonado"
        );
    }


    @Test
    public void deveCriarComposicaoServico() {

        assertThat(composicaoServico).isNotNull();

        assertThat(composicaoServico.getServico()).isEqualTo(drywall);

        assertThat(composicaoServico.getDescricao()).isEqualTo("Parede em gesso acartonado");

        assertThat(composicaoServico.getItens()).hasSize(0);
    }

    @Test
    public void deveAdicionarMaterialNaComposicaoServico() {

        composicaoServico.adicionarItem(parafuso, BigDecimal.valueOf(5));

        assertThat(composicaoServico.getItens()).hasSize(1);

        ItemComposicaoServico item = composicaoServico.getItens().getFirst();

        assertThat(item.getMaterial()).isEqualTo(parafuso);

        assertThat(item.getQuantidade()).isEqualByComparingTo("5");

    }

    @Test
    public void deveRemoverMaterialdaComposicao() {

        composicaoServico.adicionarItem(parafuso, BigDecimal.valueOf(1));
        composicaoServico.adicionarItem(placaST, BigDecimal.valueOf(2.16));

        assertThat(composicaoServico.getItens())
                .hasSize(2);

        composicaoServico.removerItem(parafuso);

        assertThat(composicaoServico.getItens())
                .hasSize(1);

        assertThat(composicaoServico.getItens().getFirst().getMaterial())
                .isEqualTo(placaST);
    }

    @Test
    public void deveAlterarDescricao() {

        composicaoServico.alterarDescricao("Descrição alterada");

        assertThat(composicaoServico.getDescricao())
                .isEqualTo("Descrição alterada");

    }

    @Test
    public void deveAlterarQuantidadeDeMaterialEspecifico() {

        composicaoServico.adicionarItem(parafuso, BigDecimal.valueOf(1));

        composicaoServico.alterarQuantidade(parafuso, BigDecimal.valueOf(50));

        assertThat(composicaoServico.getItens().getFirst().getQuantidade())
                .isEqualByComparingTo("50");

    }

    @Test
    public void deveLancarExcecaoSeServicoforNulo() {

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new ComposicaoServico(null, "Descrição")
        );

        assertThat(exception)
                .hasMessage("Serviço é obrigatório");

    }

    @Test
    public void deveLancarExcecaoSeDescricaoForVaziaOuNula() {

        CatalogoDominioException vazioException = assertThrows(
                CatalogoDominioException.class,
                () -> new ComposicaoServico(drywall, "")
        );

        CatalogoDominioException nuloException = assertThrows(
                CatalogoDominioException.class,
                () -> new ComposicaoServico(drywall, null)
        );

        assertThat(vazioException)
                .hasMessage("Descrição é obrigatória");

        assertThat(nuloException)
                .hasMessage("Descrição é obrigatória");

    }

    @Test
    public void deveLancarExcecaoSeMaterialForNulo() {

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> composicaoServico.adicionarItem(null, BigDecimal.valueOf(1))
        );

        assertThat(exception)
                .hasMessage("Material é obrigatório");

    }

    @Test
    public void deveLancarExcecaoSeMaterialNaoExistirAoTentarAlterarOuRemover() {

        CatalogoDominioException exception1 = assertThrows(CatalogoDominioException.class,
                () -> composicaoServico.alterarQuantidade(placaST, BigDecimal.valueOf(5))
        );

        CatalogoDominioException exception2 = assertThrows(CatalogoDominioException.class,
                () -> composicaoServico.removerItem(placaST)
        );

        assertThat(exception1)
                .hasMessageContaining("Material");

        assertThat(exception2)
                .hasMessageContaining("Material");

    }

    @Test
    public void deveLancarExcecaoAoTentarAdicionarMaterialJaExistenteNaComposicao() {

        composicaoServico.adicionarItem(parafuso, BigDecimal.valueOf(1));

        CatalogoDominioException exception = assertThrows(
                CatalogoDominioException.class,
                () -> composicaoServico.adicionarItem(parafuso, BigDecimal.valueOf(1))
        );

        assertThat(exception)
                .hasMessageContaining("Material");
    }

    @Test
    public void deveLancarExcecaoSeQuantidadeForNulaAoAdicionarItem() {

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> composicaoServico.adicionarItem(parafuso, null)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Quantidade é obrigatória");
    }

    @Test
    public void deveLancarExcecaoSeQuantidadeForZeroAoAdicionarItem() {

        CatalogoDominioException exception = assertThrows(
                CatalogoDominioException.class,
                () -> composicaoServico.adicionarItem(
                        parafuso,
                        BigDecimal.ZERO
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("Quantidade deve ser maior que zero");
    }

    @Test
    public void deveLancarExcecaoSeQuantidadeForNegativaAoAdicionarItem() {

        CatalogoDominioException exception = assertThrows(
                CatalogoDominioException.class,
                () -> composicaoServico.adicionarItem(
                        parafuso,
                        BigDecimal.valueOf(-1)
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("Quantidade deve ser maior que zero");
    }

    @Test
    public void deveLancarExcecaoSeQuantidadeForNulaAoAlterarQuantidade() {

        composicaoServico.adicionarItem(
                parafuso,
                BigDecimal.ONE
        );

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> composicaoServico.alterarQuantidade(
                        parafuso,
                        null
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("Quantidade é obrigatória");
    }

    @Test
    public void deveLancarExcecaoSeQuantidadeForZeroAoAlterarQuantidade() {

        composicaoServico.adicionarItem(
                parafuso,
                BigDecimal.ONE
        );

        CatalogoDominioException exception = assertThrows(
                CatalogoDominioException.class,
                () -> composicaoServico.alterarQuantidade(
                        parafuso,
                        BigDecimal.ZERO
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("Quantidade deve ser maior que zero");
    }

    @Test
    public void deveLancarExcecaoSeQuantidadeForNegativaAoAlterarQuantidade() {

        composicaoServico.adicionarItem(
                parafuso,
                BigDecimal.ONE
        );

        CatalogoDominioException exception = assertThrows(
                CatalogoDominioException.class,
                () -> composicaoServico.alterarQuantidade(
                        parafuso,
                        BigDecimal.valueOf(-10)
                )
        );

        assertThat(exception.getMessage())
                .isEqualTo("Quantidade deve ser maior que zero");
    }


}
