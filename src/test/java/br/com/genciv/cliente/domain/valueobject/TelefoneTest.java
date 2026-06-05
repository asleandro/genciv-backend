package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TelefoneTest {
    private final String DDD_VALIDO = "21";

    @Test
    public void deveCriarUmTelefoneValido() {

        String numero = "99999-9999";

        Telefone telefone = new Telefone(DDD_VALIDO, numero);

        assertThat(telefone.toString())
                .isEqualTo("(21) 99999-9999");
    }

    @Test
    @DisplayName("Deve formatar telefone fixo e celular corretamente")
    public void deveFormatarTelefoneFixoECelularCorretamente() {

        String numeroFixo = "2222-2222";
        String numeroCelular = "99999-9999";

        Telefone fixo = new Telefone(DDD_VALIDO, numeroFixo);
        Telefone celular = new Telefone(DDD_VALIDO, numeroCelular);

        assertThat(fixo.formatado()).isEqualTo("(21) 2222-2222");
        assertThat(celular.formatado()).isEqualTo("(21) 99999-9999");
    }

    @Test
    public void deveRetornarNumeroComDDDSemFormatacao() {

        String numero = "2222-2222";

        Telefone fixo = new Telefone(DDD_VALIDO, numero);

        assertThat(fixo.getValor()).isEqualTo("2122222222");

    }

    @Test
    @DisplayName("Deve considerar números iguais mesmo com formatação diferente")
    public void deveConsiderarNumerosIguaisMesmoComFormatacaoDiferente() {
        String numero = "22222222";
        String numero_formatado = "2222-2222";

        Telefone telefone1 = new Telefone(DDD_VALIDO, numero);
        Telefone telefone2 = new Telefone(DDD_VALIDO, numero_formatado);

        assertThat(telefone1).isEqualTo(telefone2);
    }

    @Test
    @DisplayName("Deve possuir mesma hash code quando números forem iguais")
    public void devePossuirMesmaHashCodeQuandoNumerosForemIguais() {
        String numero = "22222222";
        String numero_formatado = "2222-2222";

        Telefone telefone1 = new Telefone(DDD_VALIDO, numero);
        Telefone telefone2 = new Telefone(DDD_VALIDO, numero_formatado);

        assertThat(telefone1.hashCode())
                .isEqualTo(telefone2.hashCode());
    }

    @Test
    @DisplayName("Deve identificar telefone fixo corretamente")
    public void deveIdentificarTelefoneFixoCorretamente() {

        Telefone telefone = new Telefone("21", "2222-2222");

        assertThat(telefone.ehFixo()).isTrue();
        assertThat(telefone.ehCelular()).isFalse();

    }

    @Test
    @DisplayName("Deve identificar telefone celular corretamente")
    public void deveIdentificarTelefoneCelularCorretamente() {

        Telefone telefone = new Telefone("21", "99999-9999");

        assertThat(telefone.ehCelular()).isTrue();
        assertThat(telefone.ehFixo()).isFalse();

    }

    @Test
    @DisplayName("Deve lançar exceção quando DDD ou número for vazio")
    public void deveLancarExcecaoQuandoDDDOuNumeroForVazio() {

        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(" ", "22222222")
        );

        assertThat(exception.getMessage())
                .isEqualTo("DDD não pode ser vazio");

        ValueObjectInvalidoException exception2 = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, " ")
        );

        assertThat(exception2.getMessage())
                .isEqualTo("Número não pode ser vazio");

    }

    @Test
    @DisplayName("Deve lançar exceção quando DDD ou número for nulo")
    public void deveLancarExcecaoQuandoDDDOuNumeroForNulo() {

        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(null, "22222222")
        );

        assertThat(exception.getMessage())
                .isEqualTo("DDD não pode ser nulo");

        ValueObjectInvalidoException exception2 = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, null)
        );

        assertThat(exception2.getMessage())
                .isEqualTo("Número não pode ser nulo");

    }


    @ParameterizedTest(name = "[{index}] número inválido: [{0}]")

    @ValueSource(strings = {
            "",
            "#99999-9999",
            "2222*2222",
            "2222=2222",
            "99a99-9999",
            "2222@2222",
            "９9999-8888",
            "99999ç888",
            "99999á888"
    })
    @DisplayName("Deve lançar exceção quando número possuir caracteres inválidos")
    public void deveLancarExcecaoQuandoNumeroPossuirCaracteresInvalidos(String numero) {
        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, numero)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Número possui caracteres inválidos");
    }

    @ParameterizedTest(name = "[{index}] DDD inválido: [{0}]")
    @ValueSource(strings = {
            "",
            "#1",
            "AB",
            "2a",
    })
    @DisplayName("Deve lançar exceção quando DDD possuir caracteres inválidos")
    public void deveLancarExcecaoQuandoDDDPossuirCaracteresInvalidos(String ddd) {
        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(ddd, "22222222")
        );

        assertThat(exception.getMessage())
                .isEqualTo("DDD possui caracteres inválidos");
    }

    @ParameterizedTest(name = "[{index}] tamanho inválido: [{0}]")
    @ValueSource(strings = {
            "123",
            "1234567",
            "1234567890"

    })
    @DisplayName("Deve lançar exceção quando número possuir tamanho inválido")
    public void deveLancarExcecaoQuandoNumeroPossuirTamanhoInvalido(String valor) {

        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, valor)
        );

        assertThat(exception.getMessage())
                .isEqualTo("Número com tamanho inválido");

    }


}
