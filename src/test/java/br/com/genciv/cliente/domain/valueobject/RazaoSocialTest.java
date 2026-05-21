package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.RazaoSocialInvalidaException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RazaoSocialTest {


    @Test
    @DisplayName("Deve criar razão social e formatando o texto para caixa alta")
    public void deveCriarRazaoSocialFormatandoEmCaixaAlta() {

        RazaoSocial razaoSocial = new RazaoSocial("Genciv Gestão Digital de Obras Ltda");

        assertThat(razaoSocial.getValor()).isEqualTo("GENCIV GESTÃO DIGITAL DE OBRAS LTDA");

    }

    @Test
    @DisplayName("Deve remover espaços duplicados na razão social")
    public void deveRemoverEspacosDuplicadosNaRazaoSocial() {

        RazaoSocial razaoSocial = new RazaoSocial(" Genciv Gestão   Digital   de Obras Ltda ");

        assertThat(razaoSocial.getValor()).isEqualTo("GENCIV GESTÃO DIGITAL DE OBRAS LTDA");
    }

    @Test
    @DisplayName("Deve formatar razão social corretamente")
    void deveFormatarRazaoSocial() {

        RazaoSocial razaoSocial =
                new RazaoSocial("GENCIV GESTAO DIGITAL LTDA");

        assertThat(razaoSocial.formatado())
                .isEqualTo("Genciv Gestao Digital LTDA");
    }

    @Test
    @DisplayName("Deve remover caracteres de controle da razão social")
    void deveRemoverCaracteresDeControle() {

        RazaoSocial razaoSocial =
                new RazaoSocial("Genciv\tGestão\nDigital\rLtda");

        assertThat(razaoSocial.getValor())
                .isEqualTo("GENCIV GESTÃO DIGITAL LTDA");
    }

    @Test
    @DisplayName("Deve lançar exceção se a razão social for vazia")
    public void deveLancarExcecaoSeRazaoSocialForVazia(){

        RazaoSocialInvalidaException exception = assertThrows(
                RazaoSocialInvalidaException.class,
                () -> new RazaoSocial(" ")
        );

        assertThat(exception.getMessage()).isEqualTo("Razão social não pode ser vazia");
    }

    @Test
    @DisplayName("Deve lançar exceção se a razão social for nula")
    public void deveLancarExcecaoSeRazaoSocialForNula(){

        NullPointerException exception = assertThrows(
                NullPointerException.class,
                () -> new RazaoSocial(null)
        );

        assertThat(exception.getMessage()).isEqualTo("Razão Social é obrigatória");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Genciv$$ Gestão de Obras",
            "_Genciv Gestão=Obras",
            "@Genciv LTDA",
            "Genciv #1",
            "Genciv|Tecnologia"
    })
    @DisplayName("Deve lançar exceção se a razão social possuir caracteres inválidos")
    public void deveLancarExcecaoSeRazaoSocialPossuirCaracteresInvalidos(String valor) {

        assertThrows(RazaoSocialInvalidaException.class,
                () -> new RazaoSocial(valor));

    }

    @Test
    @DisplayName("Deve lançar exceção se a razão social não possuir caracteres suficientes")
    public void deveLancarExcecaoSeRazaoSocialNaoPossuirCaracteresSuficientes() {

        RazaoSocialInvalidaException exception = assertThrows(
                RazaoSocialInvalidaException.class,
                () -> new RazaoSocial("a"));

        assertThat(exception.getMessage())
                .isEqualTo("Razão social deve possuir no mínimo 3 caracteres");

    }

    @Test
    @DisplayName("Deve lançar exceção se a razão social exceder a quantidade máxima de caracteres")
    public void deveLancarExcecaoSeRazaoSocialExcederMaximoDeCaracteres() {

        RazaoSocialInvalidaException exception = assertThrows(
                RazaoSocialInvalidaException.class,
                () -> new RazaoSocial("GENCIV ENGENHARIA E GESTAO DE OBRAS LTDA GENCIV ENGENHARIA E" +
                        " GESTAO DE OBRAS LTDA GENCIV ENGENHARIA E GESTAO DE OBRAS LTDA GENCIV ENGENHARIA E" +
                        " GESTAO DE OBRAS LTDA GENCIV ENGENHARIA E GESTAO DE OBRAS LTDA GENCIV ENGENHARIA E" +
                        " GESTAO DE OBRAS LTDA GENCI"));

        assertThat(exception.getMessage())
                .isEqualTo("Razão social deve possuir no máximo 250 caracteres");

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "123 456",
            "0-1",
            "& / -",
            "123 & 0-1"
    })
    @DisplayName("Deve lançar exceção se a razão social não possuir caracteres alfabéticos")
    public void deveLancarExcecaoSeRazaoSocialNaoPossuirCaracteresAlfabeticos(String valor) {

        RazaoSocialInvalidaException exception = assertThrows(
                RazaoSocialInvalidaException.class,
                () -> new RazaoSocial(valor)
        );

        assertThat(exception.getMessage()).isEqualTo("Razão social deve possuir ao menos uma letra");
    }

    @Test
    @DisplayName("Deve verificar se razão social contém texto")
    void deveVerificarSeRazaoSocialContemTexto() {

        RazaoSocial razaoSocial =
                new RazaoSocial("Genciv Gestão Digital");

        assertThat(razaoSocial.contem("gestão"))
                .isTrue();

        assertThat(razaoSocial.contem("financeiro"))
                .isFalse();
    }

}
