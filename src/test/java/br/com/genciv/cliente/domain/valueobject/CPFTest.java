package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.DocumentoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CPFTest {

    private static final String CPF_VALIDO = "52998224725";
    private static final String CPF_VALIDO_FORMATADO = "529.982.247-25";

    @Test
    @DisplayName("Deve criar CPF válido removendo caracteres especiais")
    public void deveCriarCpfValido() {

        CPF cpf = new CPF(CPF_VALIDO);

        assertThat(cpf).isNotNull();
        assertThat(cpf.getNumero()).isEqualTo("52998224725");
        assertThat(cpf.getNumero()).hasSize(11).containsOnlyDigits();
    }

    @Test
    public void deveCriarExcecaoQuandoCpfPossuirQuantidadeInvalidaDeDigitos() {
        String cpfInvalido = "123.6667.741-711";

        assertThrows(
                DocumentoInvalidoException.class, () -> new CPF(cpfInvalido)
        );
    }

    @Test
    public void deveLancarExcecaoQuandoCpfForNulo() {

        DocumentoInvalidoException exception =
                assertThrows(
                        DocumentoInvalidoException.class, () -> new CPF(null)
                );

        assertThat(exception.getMessage()).isEqualTo("CPF não pode ser nulo");
    }

    @Test
    public void deveLancarExcecaoQuandoCpfForBlank() {

        DocumentoInvalidoException exception =
                assertThrows(
                        DocumentoInvalidoException.class, () -> new CPF(" ")
                );
        assertThat(exception.getMessage()).isEqualTo("CPF não pode ser vazio");
    }

    @Test
    public void deveRemoverCaracteresEspeciaisDoCpf() {

        CPF cpf = new CPF(CPF_VALIDO_FORMATADO);

        assertThat("52998224725").isEqualTo(cpf.getNumero());
    }

    @Test
    public void deveConsiderarCpfsIguaisMesmoComFormatacoesDiferentes() {

        CPF cpfComMascara = new CPF(CPF_VALIDO_FORMATADO);
        CPF cpfSemMascara = new CPF(CPF_VALIDO);

        assertThat(cpfComMascara).isEqualTo(cpfSemMascara);
    }

    @Test
    public void devePossuirMesmoHashCodeQuandoCpfsForemIguais() {

        CPF cpfComMascara = new CPF(CPF_VALIDO_FORMATADO);
        CPF cpfSemMascara = new CPF(CPF_VALIDO);

        assertThat(cpfComMascara.hashCode()).isEqualTo(cpfSemMascara.hashCode());
    }

    @Test
    public void deveFormatarCpfCorretamente() {
        CPF cpf = new CPF(CPF_VALIDO);
        assertThat(cpf.formatar()).isEqualTo("529.982.247-25");
    }

    @Test
    public void naoDeveAlterarEstadoInternoAoFormatarCpf() {
        CPF cpf = new CPF(CPF_VALIDO);

        String valorOriginal = cpf.getNumero();
        cpf.formatar();
        assertThat(cpf.getNumero()).isEqualTo(valorOriginal);
    }

}
