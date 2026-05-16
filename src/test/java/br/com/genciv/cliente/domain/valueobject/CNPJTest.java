package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.DocumentoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CNPJTest {

    private static String CNPJ_VALIDO = "11444777000161";
    private static String CNPJ_VALIDO_FORMATADO = "11.444.777/0001-61";


    @Test
    @DisplayName("Deve criar CNPJ válido removendo caracteres especiais")
    public void deveCriarCnpjValido() {
        CNPJ cnpj = new CNPJ(CNPJ_VALIDO_FORMATADO);

        assertThat(cnpj).isNotNull();
        assertThat(cnpj.getNumero()).isEqualTo(CNPJ_VALIDO);
        assertThat(cnpj.getNumero()).hasSize(14).containsOnlyDigits();
    }

    @ParameterizedTest(name = "CNPJ inválido: [{0}]")
    @DisplayName("Deve lançar exceção para CNPJs inválidos")
    @NullAndEmptySource
    @ValueSource(strings = {
            " ",
            "123",
            "123.4444.777/0001-999"
    })
    public void deveLancarExcecaoParaCNPJsInvalidos(String CNPJInvalido){

        assertThrows(DocumentoInvalidoException.class,
                () -> new CNPJ(CNPJInvalido)
        );
    }

    @Test
    @DisplayName("Deve possuir mesmo hash code quando CNPJs forem iguais")
    public void devePossuirMesmoHashCodeQuandoCNPJsForemIguais(){

        CNPJ cnpjComMascara = new CNPJ(CNPJ_VALIDO_FORMATADO);
        CNPJ cnpjSemMascara = new CNPJ(CNPJ_VALIDO);

        assertThat(cnpjComMascara.hashCode()).isEqualTo(cnpjSemMascara.hashCode());
    }

    @Test
    public void deveFormatarOCnpjCorretamente(){
        CNPJ cnpjSemMascara = new CNPJ(CNPJ_VALIDO);

        assertThat(cnpjSemMascara.formatar())
                .isEqualTo(CNPJ_VALIDO_FORMATADO);

    }

    @Test
    @DisplayName("Nâo deve alterar estado interno ao formatar CNPJ")
    public void naoDeveAlterarEstadoInternoAoFormatarCnpj(){

        CNPJ cnpj = new CNPJ(CNPJ_VALIDO);

        String numeroOriginal = cnpj.getNumero();
        cnpj.formatar();

        assertThat(cnpj.getNumero())
                .isEqualTo(numeroOriginal);
    }

    @Test
    @DisplayName("Deve retornar CNPJ formatado no toString()")
    public void deveRetornarCnpjFormatadoNoToString(){

        CNPJ cnpj = new CNPJ(CNPJ_VALIDO);

        assertThat(cnpj.toString())
                .isEqualTo(CNPJ_VALIDO_FORMATADO);
    }


}
