package br.com.genciv.catalogo.domain.enums;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnidadeMedidaTeste {

    @Test
    void todasAsUnidadesDevemPossuirSimbolo() {
        for (UnidadeMedida unidade : UnidadeMedida.values()) {
            assertThat(unidade.getSimbolo())
                    .isNotBlank();
        }
    }

    @Test
    void todasAsUnidadesDevemPossuirDescricao() {

        for (UnidadeMedida unidade : UnidadeMedida.values()) {
            assertThat(unidade.getDescricao())
                    .isNotBlank();
        }
    }

    @Test
    public void deveRetornarSimboloDaUnidadeConjunto() {

        assertThat(UnidadeMedida.CJ.getSimbolo())
                .isEqualTo("conjunto");
    }

    @Test
    public void deveRetornarDescricaoDaUnidadeMetroQuadrado() {

        assertThat(UnidadeMedida.M2.getDescricao())
                .isEqualTo("Metro Quadrado");

    }

    @Test
    public void deveRetornarDescricaoCompletaDaUnidadeBalde() {

        assertThat(UnidadeMedida.BD.getDescricaoCompleta())
                .isEqualTo("Balde (balde)");

    }

}
