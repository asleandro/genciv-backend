package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.enums.UnidadeFederativa;
import br.com.genciv.cliente.domain.exception.EnderecoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnderecoTest {

    private static final String LOGRADOURO = "Rua das Flores";
    private static final String NUMERO = "123";
    private static final String COMPLEMENTO = "Apartamento 101";
    private static final String BAIRRO = "Centro";
    private static final String CIDADE = "Rio de Janeiro";
    private static final UnidadeFederativa UF = UnidadeFederativa.RJ;
    private static final CEP CEP_VALIDO = new CEP("20040-020");

    private Endereco criarEnderecoValido() {

        return new Endereco(
                LOGRADOURO,
                NUMERO,
                COMPLEMENTO,
                BAIRRO,
                CIDADE,
                UF,
                CEP_VALIDO
        );
    }

    @Test
    public void deveCriarEnderecoValido() {

        Endereco endereco = criarEnderecoValido();

        assertThat(endereco).isNotNull();

        assertThat(endereco.getLogradouro())
                .isEqualTo(LOGRADOURO);
    }

    @Test
    public void deveNormalizarCamposTexto() {

        Endereco endereco = new Endereco(
                "  Rua   das Flores ",
                " 123 ",
                " Apto   101 ",
                " Centro ",
                " Rio   de Janeiro ",
                UF,
                CEP_VALIDO
        );

        assertThat(endereco.getLogradouro())
                .isEqualTo("Rua das Flores");

        assertThat(endereco.getComplemento())
                .isEqualTo("Apto 101");
    }

    @Test
    public void deveAceitarComplementoNulo() {

        Endereco endereco = new Endereco(
                LOGRADOURO,
                NUMERO,
                null,
                BAIRRO,
                CIDADE,
                UF,
                CEP_VALIDO
        );

        assertThat(endereco.getComplemento())
                .isNull();

        assertThat(endereco.possuiComplemento())
                .isFalse();
    }

    @Test
    public void deveTransformarComplementoBlankEmNull() {

        Endereco endereco = new Endereco(
                LOGRADOURO,
                NUMERO,
                " ",
                BAIRRO,
                CIDADE,
                UF,
                CEP_VALIDO
        );

        assertThat(endereco.getComplemento())
                .isNull();
    }

    @Test
    public void deveLancarExcecaoQuandoLogradouroForNulo() {

        assertThrows(
                EnderecoInvalidoException.class,
                () -> new Endereco(
                        null,
                        NUMERO,
                        COMPLEMENTO,
                        BAIRRO,
                        CIDADE,
                        UF,
                        CEP_VALIDO
                )
        );
    }

    @Test
    @DisplayName("Deve lançar exceção quando logradouro exceder tamanho máximo")
    public void deveLancarExcecaoQuandoLogradouroExcederTamanhoMaximo() {

        String texto =
                "a".repeat(151);

        assertThrows(
                EnderecoInvalidoException.class,
                () -> new Endereco(
                        texto,
                        NUMERO,
                        COMPLEMENTO,
                        BAIRRO,
                        CIDADE,
                        UF,
                        CEP_VALIDO
                )
        );
    }

    @Test
    public void deveRetornarEnderecoCompletoFormatado() {

        Endereco endereco = criarEnderecoValido();

        assertThat(endereco.enderecoCompleto())
                .contains("Rua das Flores")
                .contains("20040-020");
    }

    @Test
    public void devePertencerAoEstadoInformado() {

        Endereco endereco = criarEnderecoValido();

        assertThat(endereco.pertenceAoEstado(UnidadeFederativa.RJ))
                .isTrue();
    }

    @Test
    public void deveConsiderarEnderecosIguais() {

        Endereco endereco1 = criarEnderecoValido();

        Endereco endereco2 = criarEnderecoValido();

        assertThat(endereco1)
                .isEqualTo(endereco2);

        assertThat(endereco1.hashCode())
                .isEqualTo(endereco2.hashCode());
    }

    @Test
    public void deveRetornarEnderecoCompletoNoToString() {

        Endereco endereco = criarEnderecoValido();

        assertThat(endereco.toString())
                .isEqualTo(endereco.enderecoCompleto());
    }

    @Test
    public void naoDeveAlterarEstadoAoGerarEnderecoCompleto() {

        Endereco endereco = criarEnderecoValido();

        String logradouroOriginal =
                endereco.getLogradouro();

        endereco.enderecoCompleto();

        assertThat(endereco.getLogradouro())
                .isEqualTo(logradouroOriginal);
    }






}
