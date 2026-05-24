package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.ValueObjectInvalidoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TelefoneTest {
    private final String DDD_VALIDO = "21";

    @Test
    public void deveCriarUmTelefoneVálido() {

        String numero = "99999-9999";

        Telefone telefone = new Telefone(DDD_VALIDO, numero);

        assertThat(telefone.toString()).isEqualTo("(21) 99999-9999");
    }

    @Test
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
    public void deveConsiderarNumerosIguaisMesmoComFormatacaoDiferente(){
        String numero = "22222222";
        String numero_formatado = "2222-2222";

        Telefone telefone1 = new Telefone(DDD_VALIDO, numero);
        Telefone telefone2 = new Telefone(DDD_VALIDO, numero_formatado);

        assertThat(telefone1).isEqualTo(telefone2);
    }

    @Test
    public void devePossuirMesmaHashCodeQuandoNumerosForemIguais(){
        String numero = "22222222";
        String numero_formatado = "2222-2222";

        Telefone telefone1 = new Telefone(DDD_VALIDO, numero);
        Telefone telefone2 = new Telefone(DDD_VALIDO, numero_formatado);

        assertThat(telefone1.hashCode()).isEqualTo(telefone2.hashCode());
    }

    @Test
    public void deveLancarExcecaoQuandoDDDOuNumeroForVazio(){

        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(" ", "22222222")
        );

        assertThat(exception.getMessage()).isEqualTo("DDD não pode ser vazio");

        ValueObjectInvalidoException exception2 = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, " ")
        );

        assertThat(exception2.getMessage()).isEqualTo("Número não pode ser vazio");

    }

    @Test
    public void deveLancarExcecaoQuandoDDDOuNumeroForNulo(){

        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(null, "22222222")
        );

        assertThat(exception.getMessage()).isEqualTo("DDD não pode ser nulo");

        ValueObjectInvalidoException exception2 = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, null)
        );

        assertThat(exception2.getMessage()).isEqualTo("Número não pode ser nulo");

    }

    @ParameterizedTest
    @ValueSource(strings = {
            "#999-9999",
            "#$%%9999-9999"
    })
    public void deveLancarExcecaoQuandoNumeroPossuirCaracteresInvalidos(String numero){
        ValueObjectInvalidoException exception = assertThrows(
                ValueObjectInvalidoException.class,
                () -> new Telefone(DDD_VALIDO, numero)
        );

        assertThat(exception.getMessage()).isEqualTo("Número possui caracteres inválidos");
    }





}
