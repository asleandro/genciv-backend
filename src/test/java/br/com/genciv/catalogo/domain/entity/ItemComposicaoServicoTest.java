package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.enums.UnidadeMedida;
import br.com.genciv.catalogo.domain.exception.CatalogoDominioException;
import br.com.genciv.catalogo.domain.valueobject.ItemComposicaoServicoId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ItemComposicaoServicoTest {

    private Material material;
    private ItemComposicaoServicoId id;

    @BeforeEach
    void setup() {

        material = Material.criar(
                "MAT01",
                "Material Teste",
                UnidadeMedida.CJ,
                true
        );

        id = ItemComposicaoServicoId.novo();
    }

    @Test
    void deveCriarItemComposicaoServicoValido() {

        ItemComposicaoServico item =
                ItemComposicaoServico.criar(
                        material,
                        BigDecimal.valueOf(10)
                );

        assertThat(item.getId()).isNotNull();
        assertThat(item.getMaterial()).isEqualTo(material);
        assertThat(item.getQuantidade())
                .isEqualByComparingTo(BigDecimal.TEN);
    }

    @Test
    void deveLancarExcecaoQuandoMaterialForNulo() {

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> ItemComposicaoServico.criar(
                                null,
                                BigDecimal.ONE
                        )
                );

        assertThat(exception)
                .hasMessageContaining("Material");
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForNula() {

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> ItemComposicaoServico.criar(
                                material,
                                null
                        )
                );

        assertThat(exception)
                .hasMessageContaining("Quantidade");
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForZero() {

        CatalogoDominioException exception =
                assertThrows(
                        CatalogoDominioException.class,
                        () -> ItemComposicaoServico.criar(
                                material,
                                BigDecimal.ZERO
                        )
                );

        assertThat(exception)
                .hasMessageContaining("Quantidade deve ser maior que zero");
    }

    @Test
    void deveLancarExcecaoQuandoQuantidadeForNegativa() {

        CatalogoDominioException exception =
                assertThrows(
                        CatalogoDominioException.class,
                        () -> ItemComposicaoServico.criar(
                                material,
                                BigDecimal.valueOf(-1)
                        )
                );

        assertThat(exception)
                .hasMessageContaining("Quantidade deve ser maior que zero");
    }

    @Test
    void deveAlterarQuantidade() {

        ItemComposicaoServico item =
                ItemComposicaoServico.criar(
                        material,
                        BigDecimal.ONE
                );

        item.alterarQuantidade(BigDecimal.valueOf(5));

        assertThat(item.getQuantidade())
                .isEqualByComparingTo(BigDecimal.valueOf(5));
    }

    @Test
    void deveLancarExcecaoAoAlterarQuantidadeParaNula() {

        ItemComposicaoServico item =
                ItemComposicaoServico.criar(
                        material,
                        BigDecimal.ONE
                );

        NullPointerException exception =
                assertThrows(
                        NullPointerException.class,
                        () -> item.alterarQuantidade(null)
                );

        assertThat(exception)
                .hasMessageContaining("Quantidade");
    }

    @Test
    void deveLancarExcecaoAoAlterarQuantidadeParaZero() {

        ItemComposicaoServico item =
                ItemComposicaoServico.criar(
                        material,
                        BigDecimal.ONE
                );

        CatalogoDominioException exception =
                assertThrows(
                        CatalogoDominioException.class,
                        () -> item.alterarQuantidade(BigDecimal.ZERO)
                );

        assertThat(exception)
                .hasMessageContaining("Quantidade deve ser maior que zero");
    }

    @Test
    void deveLancarExcecaoAoAlterarQuantidadeParaNegativa() {

        ItemComposicaoServico item =
                ItemComposicaoServico.criar(
                        material,
                        BigDecimal.ONE
                );

        CatalogoDominioException exception =
                assertThrows(
                        CatalogoDominioException.class,
                        () -> item.alterarQuantidade(BigDecimal.valueOf(-10))
                );

        assertThat(exception)
                .hasMessageContaining("Quantidade deve ser maior que zero");
    }
}
