package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.RazaoSocialInvalidaException;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.shouldHaveThrown;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RazaoSocialTest {


    @Test
    @DisplayName("Deve criar razão social e formatando o texto para caixa alta")
    public void deveCriarRazaoSocialFormatandoEmCaixaAlta() {

        RazaoSocial razaoSocial = new RazaoSocial("Genciv Gestão Digital de Obras Ltda");

        assertThat(razaoSocial.getValor()).isEqualTo("GENCIV GESTÃO DIGITAL DE OBRAS LTDA");

    }

    @Test
    @DisplayName("Deve remover espaços indevidos na razão social")
    public void deveRemoverespacosIndevidosNaRazaoSocial(){

        RazaoSocial razaoSocial = new RazaoSocial(" Genciv Gestão   Digital   de Obras Ltda ");

        assertThat(razaoSocial.getValor()).isEqualTo("GENCIV GESTÃO DIGITAL DE OBRAS LTDA");
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "132445 33443",
            "Genciv$$ Gestão de Obras",
            "_Genciv Gestão=Obras",
            "@Genciv LTDA",
            "Genciv #1",
            "Genciv|Tecnologia",
            "Genciv (Ltda)"
    })
    @DisplayName("Deve lançar exceção se a razão social possuir caracteres inválidos")
    public void deveLancarExcecaoSeRazaoSocialPossuirCaracteresInvalidos(String valor){

        assertThrows(RazaoSocialInvalidaException.class,
                () -> new RazaoSocial(valor));

    }
}
