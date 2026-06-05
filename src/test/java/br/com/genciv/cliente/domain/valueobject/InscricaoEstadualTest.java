package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.InscricaoEstadualInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InscricaoEstadualTest {

    @Test
    @DisplayName("Deve criar inscrição estadual válida removendo caracteres especiais")
    void deveCriarInscricaoEstadualValida() {

        InscricaoEstadual ie = new InscricaoEstadual("12.345.678-9");

        assertThat("123456789").isEqualTo(ie.getValor());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual for nula")
    void deveLancarExcecaoQuandoValorForNulo() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual(null)
        );

        assertThat("Inscrição estadual inválida: Inscrição estadual é obrigatória")
                .isEqualTo(exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual ficar vazia após limpeza")
    void deveLancarExcecaoQuandoValorFicarVazio() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual("...")
        );

        assertThat("Inscrição estadual inválida: Valor informado não pode ser vazio")
                .isEqualTo(exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual tiver menos que o tamanho mínimo")
    void deveLancarExcecaoQuandoTamanhoForMenorQueOMinimo() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual("1")
        );

        assertThat("Inscrição estadual inválida: IE deve possuir entre 2 e 20 dígitos")
                .isEqualTo(exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual tiver mais que o tamanho máximo")
    void deveLancarExcecaoQuandoTamanhoForMaiorQueOMaximo() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual("123456789012345678901")
        );

        assertThat("Inscrição estadual inválida: IE deve possuir entre 2 e 20 dígitos")
                .isEqualTo(exception.getMessage());
    }

    @Test
    @DisplayName("Deve considerar inscrições estaduais iguais")
    void deveConsiderarInscricoesIguais() {

        InscricaoEstadual ie1 = new InscricaoEstadual("12.345.678-9");
        InscricaoEstadual ie2 = new InscricaoEstadual("123456789");

        assertThat(ie1).isEqualTo(ie2);
        assertThat(ie1.hashCode()).isEqualTo(ie2.hashCode());
    }

    @Test
    @DisplayName("Deve considerar inscrições estaduais diferentes")
    void deveConsiderarInscricoesDiferentes() {

        InscricaoEstadual ie1 = new InscricaoEstadual("123456789");
        InscricaoEstadual ie2 = new InscricaoEstadual("987654321");

        assertThat(ie1).isNotEqualTo(ie2);
    }

    @Test
    @DisplayName("Deve retornar valor no toString")
    void deveRetornarValorNoToString() {

        InscricaoEstadual ie = new InscricaoEstadual("12.345.678-9");

        assertThat("123456789").isEqualTo(ie.toString());
    }

}
