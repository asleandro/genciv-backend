package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.enums.UnidadeMedida;
import br.com.genciv.catalogo.domain.exception.MaterialDominioException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MaterialTest {

    private Material material;

    @BeforeEach
    void setup() {
        material = Material.criar(
                "MAT01",
                "Descrição do material",
                UnidadeMedida.CARTELA,
                true
        );
    }

    @Test
    public void deveCriarMaterialValido() {

        assertThat(material).isNotNull();

        assertThat(material.getCodigo())
                .isEqualTo("MAT01");

        assertThat(material.getDescricao())
                .isEqualTo("Descrição do material");

        assertThat(material.getUnidadeMedida())
                .isEqualTo(UnidadeMedida.CARTELA);

        assertThat(material.isAtivo())
                .isTrue();

    }

    @Test
    public void deveDesativarMaterialAtivo() {

        material.desativar();

        assertThat(material.isAtivo())
                .isFalse();
    }

    @Test
    public void deveAtivarMaterialInativo() {

        Material materialInativo = Material.criar(
                "MAT02",
                "Material inativo",
                UnidadeMedida.CJ,
                false
        );

        materialInativo.ativar();

        assertThat(materialInativo.isAtivo())
                .isTrue();
    }

    @Test
    public void deveAlterarCodigoDoMaterial() {
        material.alterarCodigo("COD-NOVO");

        assertThat(material.getCodigo())
                .isEqualTo("COD-NOVO");
    }

    @Test
    public void deveAlterarDescricaoDoMaterial() {

        material.alterarDescricao("Nova descrição");

        assertThat(material.getDescricao())
                .isEqualTo("Nova descrição");
    }

    @Test
    public void deveAlterarUnidadeMedidaDoMaterial() {

        material.alterarUnidadeMedida(UnidadeMedida.BOBINA);

        assertThat(material.getUnidadeMedida())
                .isEqualTo(UnidadeMedida.BOBINA);
    }

    @Test
    void deveRemoverEspacosDaDescricao() {

        Material material = Material.criar(
                "MAT01",
                "  Material Teste  ",
                UnidadeMedida.CJ,
                true
        );

        assertThat(material.getDescricao())
                .isEqualTo("Material Teste");
    }

    @Test
    public void deveLancarExcecaoAoCriarMaterialComCodigoVazioOuNulo() {

        MaterialDominioException nuloException =
                assertThrows(MaterialDominioException.class,
                        () -> Material.criar(
                                null,
                                "Material com codigo null",
                                UnidadeMedida.CJ,
                                true)
                );

        assertThat(nuloException).hasMessageContaining("Código");

        MaterialDominioException vazioException =
                assertThrows(MaterialDominioException.class,
                        () -> Material.criar(
                                "",
                                "Material com codigo vazio",
                                UnidadeMedida.CJ,
                                true)
                );

        assertThat(vazioException).hasMessageContaining("Código");

    }

    @Test
    public void deveLancarExcecaoAoCriarMaterialComDescricaoVaziaOuNula() {

        MaterialDominioException nuloException =
                assertThrows(MaterialDominioException.class,
                        () -> Material.criar(
                                "MAT02",
                                null,
                                UnidadeMedida.CJ,
                                true)
                );

        assertThat(nuloException).hasMessageContaining("Descrição");

        MaterialDominioException vazioException =
                assertThrows(MaterialDominioException.class,
                        () -> Material.criar(
                                "MAT03",
                                " ",
                                UnidadeMedida.CJ,
                                true)
                );

        assertThat(vazioException).hasMessageContaining("Descrição");

    }

    @Test
    public void deveLancarExcecaoAoCriarMaterialComUnidadeMedidaNula() {

        NullPointerException nuloException =
                assertThrows(NullPointerException.class,
                        () -> Material.criar(
                                "MAT04",
                                "Material com unidade de medida nula",
                                null,
                                true)
                );

        assertThat(nuloException).hasMessageContaining("UnidadeMedida");

    }

    @Test
    void deveLancarExcecaoAoAlterarDescricaoParaNula() {

        MaterialDominioException exception =
                assertThrows(
                        MaterialDominioException.class,
                        () -> material.alterarDescricao(null)
                );

        assertThat(exception)
                .hasMessageContaining("Descrição");
    }

    @Test
    void deveLancarExcecaoAoAlterarDescricaoParaVazia() {

        MaterialDominioException exception =
                assertThrows(
                        MaterialDominioException.class,
                        () -> material.alterarDescricao(" ")
                );

        assertThat(exception)
                .hasMessageContaining("Descrição");
    }


}
