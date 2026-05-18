package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.InscricaoEstadualInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InscricaoEstadualTest {

    @Test
    @DisplayName("Deve criar inscrição estadual válida removendo caracteres especiais")
    void deveCriarInscricaoEstadualValida() {

        InscricaoEstadual ie = new InscricaoEstadual("12.345.678-9");

        assertEquals("123456789", ie.getValor());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual for nula")
    void deveLancarExcecaoQuandoValorForNulo() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual(null)
        );

        assertEquals("Inscrição estadual inválida: Inscrição estadual é obrigatória", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual ficar vazia após limpeza")
    void deveLancarExcecaoQuandoValorFicarVazio() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual("...")
        );

        assertEquals("Inscrição estadual inválida: Valor informado não pode ser vazio", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual tiver menos que o tamanho mínimo")
    void deveLancarExcecaoQuandoTamanhoForMenorQueOMinimo() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual("1")
        );

        assertEquals("Inscrição estadual inválida: IE deve possuir entre 2 e 20 dígitos", exception.getMessage());
    }

    @Test
    @DisplayName("Deve lançar exceção quando inscrição estadual tiver mais que o tamanho máximo")
    void deveLancarExcecaoQuandoTamanhoForMaiorQueOMaximo() {

        InscricaoEstadualInvalidaException exception = assertThrows(
                InscricaoEstadualInvalidaException.class,
                () -> new InscricaoEstadual("123456789012345678901")
        );

        assertEquals("Inscrição estadual inválida: IE deve possuir entre 2 e 20 dígitos", exception.getMessage());
    }

    @Test
    @DisplayName("Deve considerar inscrições estaduais iguais")
    void deveConsiderarInscricoesIguais() {

        InscricaoEstadual ie1 = new InscricaoEstadual("12.345.678-9");
        InscricaoEstadual ie2 = new InscricaoEstadual("123456789");

        assertEquals(ie1, ie2);
        assertEquals(ie1.hashCode(), ie2.hashCode());
    }

    @Test
    @DisplayName("Deve considerar inscrições estaduais diferentes")
    void deveConsiderarInscricoesDiferentes() {

        InscricaoEstadual ie1 = new InscricaoEstadual("123456789");
        InscricaoEstadual ie2 = new InscricaoEstadual("987654321");

        assertNotEquals(ie1, ie2);
    }

    @Test
    @DisplayName("Deve retornar valor no toString")
    void deveRetornarValorNoToString() {

        InscricaoEstadual ie = new InscricaoEstadual("12.345.678-9");

        assertEquals("123456789", ie.toString());
    }
}
