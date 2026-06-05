package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.DocumentoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CPFTest {

    private static final String CPF_VALIDO = "52998224725";
    private static final String CPF_VALIDO_FORMATADO = "529.982.247-25";

    @Test
    @DisplayName("Deve criar CPF válido removendo caracteres especiais")
    public void deveCriarCpfValido() {

        CPF cpf = new CPF(CPF_VALIDO_FORMATADO);

        assertThat(cpf).isNotNull();
        assertThat(cpf.getNumero()).isEqualTo(CPF_VALIDO);
        assertThat(cpf.getNumero()).hasSize(11).containsOnlyDigits();
    }

    @ParameterizedTest(name =  "CPF inválido: [{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {
            " ",
            "123",
            "123.6667.411-771",
    })
    public void deveLancarExcecaoParaCpfsInvalidos(String cpfInvalido){

        assertThrows(DocumentoInvalidoException.class,
                () -> new CPF(cpfInvalido)
        );
    }

    @Test
    @DisplayName(("Deve criar CPFs iguais memo com com entradas em formatações diferentes"))
    public void deveConsiderarCpfsIguaisMesmoComFormatacoesDiferentes() {

        CPF cpfComMascara = new CPF(CPF_VALIDO_FORMATADO);
        CPF cpfSemMascara = new CPF(CPF_VALIDO);

        assertThat(cpfComMascara).isEqualTo(cpfSemMascara);
    }

    @Test
    @DisplayName("Deve possuir mesmo hash code quando CPFs forem iguais")
    public void devePossuirMesmoHashCodeQuandoCpfsForemIguais() {

        CPF cpfComMascara = new CPF(CPF_VALIDO_FORMATADO);
        CPF cpfSemMascara = new CPF(CPF_VALIDO);

        assertThat(cpfComMascara.hashCode()).isEqualTo(cpfSemMascara.hashCode());
    }

    @Test
    public void deveFormatarCpfCorretamente() {
        CPF cpf = new CPF(CPF_VALIDO);
        assertThat(cpf.formatar()).isEqualTo(CPF_VALIDO_FORMATADO);
    }

    @Test
    @DisplayName("Não deve alterar estado interno ao formatar CPF")
    public void naoDeveAlterarEstadoInternoAoFormatarCpf() {
        CPF cpf = new CPF(CPF_VALIDO);

        String numeroOriginal = cpf.getNumero();
        cpf.formatar();
        assertThat(cpf.getNumero()).isEqualTo(numeroOriginal);
    }

    @Test
    @DisplayName("Deve retornar CPF formatado no toString()")
    public void deveRetornarCpfFormatadoNoToString(){
        CPF cpf = new CPF(CPF_VALIDO);

        assertThat(cpf.toString())
                .isEqualTo(CPF_VALIDO_FORMATADO);
    }

}
