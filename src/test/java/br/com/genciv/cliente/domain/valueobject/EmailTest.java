package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {

    @ParameterizedTest(name = "[{index}] Email válido:[{0}]")
    @ValueSource(strings = {
            "genciv@genciv.com",
            "genciv@genciv.com.br",
            "genciv_mail@gmail.com",
            "genciv-mail@hotmail.com",
            "genciv.mail@yahoo.com.br"
    })
    public void deveCriarEmailValido(String email) {

        Email emailValido = new Email(email);

        assertThat(emailValido.getValor()).isEqualTo(email);
    }

    @ParameterizedTest(name = "[{index}] Email inválido:[{0}]")
    @NullAndEmptySource
    @ValueSource(strings = {
            " ",
            "teste.mail.com",
            "teste@@mail.com.br",
            "teste@mail-com",
            ".teste@mail.com",
            "teste.@mail.com",
            "teste..email@mail.com",
            "teste&mail@mail.com",
            "teste mail@mail.com",
            "@yahoo.com.br",
            "teste@"
    })
    public void deveLancarExcecaoParaEmailInvalido(String email) {

        assertThrows(ValueObjectInvalidoException.class, () -> new Email(email));
    }

    @Test
    @DisplayName("Deve possuir mesma hash code quando emails forem iguais")
    public void devePossuirMesmaHashCodeQuandoEmailsForemIguais() {

        Email email1 = new Email("teste1@mail.com");
        Email email2 = new Email("TESTE1@MAIL.COM");

        assertThat(email1.hashCode()).isEqualTo(email2.hashCode());
    }

    @Test
    @DisplayName("Deve normalizar email para letras minúsculas")
    public void deveNormalizarEmailParaLowerCase() {

        Email email = new Email("TESTE@MAIL.COM");

        assertThat(email.getValor()).isEqualTo("teste@mail.com");
    }

    @Test
    @DisplayName("Deve considerar emails iguais mesmo com case diferente")
    public void deveConsiderarEmailsIguaisMesmoComCaseDiferente() {

        Email email1 = new Email("TESTE@mail.com");
        Email email2 = new Email("teste@mail.com");

        assertThat(email1).isEqualTo(email2);
    }

    @Test
    public void naoDeveAlterarEstadoInternoAoNormalizarEmail() {

        Email email = new Email("TESTE@MAIL.COM");

        assertThat(email.getValor())
                .isEqualTo("teste@mail.com");
    }

}
