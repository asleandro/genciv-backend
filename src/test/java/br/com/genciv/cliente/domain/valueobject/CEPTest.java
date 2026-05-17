package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.plaf.PanelUI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CEPTest {

    private final String CEP_VALIDO = "20040020";
    private final String CEP_VALIDO_FORMATADO = "20040-020";

    @Test
    public void deveCriarUmCepValidoRemovendoCaracteresEspeciais() {
        CEP cep = new CEP(CEP_VALIDO_FORMATADO);

        assertThat(cep).isNotNull();
        assertThat(cep.getNumero()).isEqualTo(CEP_VALIDO);
        assertThat(cep.getNumero()).hasSize(8).containsOnlyDigits();
    }

    @ParameterizedTest(name = "CEP inválido [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {" ", "123", "1234567891011", "123--225.74", "456888-945"})
    public void deveLancarExcecaoParaCepsInvalidos(String cepInvalido) {

        assertThrows(ValueObjectInvalidoException.class, () -> new CEP(cepInvalido));
    }

    @Test
    @DisplayName("Deve criar CEPs iguais mesmo com entradas com formatações diferentes")
    public void deveCriarCepsIguaisMesmoComEntradasComFormatacoesDirerentes() {

        CEP cepSemMascara = new CEP(CEP_VALIDO);
        CEP cepComMascara = new CEP(CEP_VALIDO_FORMATADO);

        assertThat(cepSemMascara).isEqualTo(cepComMascara);
    }

    @Test
    @DisplayName("Deve possuir mesmo hash code se os CEPs forem iguais")
    public void devePossuirMesmoHashCodeSeOsCepsForemIguais() {

        CEP cepSemMascara = new CEP(CEP_VALIDO);
        CEP cepComMascara = new CEP(CEP_VALIDO_FORMATADO);

        assertThat(cepSemMascara.hashCode()).isEqualTo(cepComMascara.hashCode());
    }

    @Test
    public void deveFormatarCepCorretamente() {

        CEP cep = new CEP(CEP_VALIDO);

        assertThat(cep.formatar()).isEqualTo(CEP_VALIDO_FORMATADO);
    }

    @Test
    public void naoDeveAlterarOEstadoInternoAoFormatarCep() {

        CEP cep = new CEP(CEP_VALIDO);

        String numeroOriginal = cep.getNumero();
        cep.formatar();

        assertThat(cep.getNumero()).isEqualTo(numeroOriginal);
    }

    @Test
    @DisplayName("Deve retornar CEP formatado no método toString()")
    public void deveRetornarCepFormatadoNoToString() {

        CEP cep = new CEP(CEP_VALIDO);

        assertThat(cep.toString()).isEqualTo(CEP_VALIDO_FORMATADO);
    }

}
