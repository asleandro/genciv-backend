package br.com.genciv.cliente.domain.entity;

import br.com.genciv.cliente.domain.enums.TipoContato;
import br.com.genciv.cliente.domain.exception.RegraNegocioException;
import br.com.genciv.cliente.domain.valueobject.Email;
import br.com.genciv.cliente.domain.valueobject.Telefone;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContatoTest {

    private String nome = "Leleco Moscardo";
    private Telefone telefone = new Telefone("21", "77777-7777");
    private Email email = new Email("leleco@moscardo.com.br");
    private TipoContato tipo = TipoContato.SUPORTE;

    private Contato criarContato() {
        return Contato.criar(
                nome,
                telefone,
                email,
                tipo
        );
    }

    @Test
    public void deveCriarContatoComSucesso() {

        Contato contato = criarContato();

        assertThat(contato).isNotNull();
        assertThat(contato.getId()).isNotNull();
        assertThat(contato.getNome()).isEqualTo("Leleco Moscardo");
    }

    @Test
    public void deveLancarExcecaoQuandoNomeForNulo() {

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> Contato.criar(
                        null,
                        telefone,
                        email,
                        tipo
                )
        );

        assertThat(exception.getMessage()).isEqualTo("Nome do contato é obrigatório");

    }

    @Test
    public void deveLancarExcecaoQuandoNomeEstiverEmBranco() {

        RegraNegocioException exception = assertThrows(RegraNegocioException.class,
                () -> Contato.criar(
                        " ",
                        telefone,
                        email,
                        tipo
                )
        );

        assertThat(exception.getMessage()).isEqualTo("Nome do contato é obrigatório");

    }

    @Test
    public void deveLancarExcecaoQuandoTelefoneForNulo() {

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> Contato.criar(
                        nome,
                        null,
                        email,
                        tipo
                )
        );

        assertThat(exception.getMessage()).isEqualTo("Telefone é obrigatório");

    }

    @Test
    public void deveLancarExcecaoQuandoTipoForNulo() {

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> Contato.criar(
                        nome,
                        telefone,
                        email,
                        null
                )
        );

        assertThat(exception.getMessage()).isEqualTo("Tipo de contato é obrigatório");

    }

    @Test
    public void deveAlterarNome() {

        Contato contato = criarContato();

        contato.alterarNome("João Joanes");

        assertThat(contato.getNome())
                .isEqualTo("João Joanes");

    }

    @Test
    public void deveAlterarTelefone() {

        Contato contato = criarContato();

        contato.alterarTelefone(new Telefone("31", "3131-3131"));

        assertThat(contato.getTelefone())
                .isEqualTo(new Telefone("31", "3131-3131"));

    }

    @Test
    public void deveAlterarEmail() {

        Contato contato = criarContato();

        contato.alterarEmail(new Email("leleco@email.com"));

        assertThat(contato.getEmail())
                .isEqualTo(new Email("leleco@email.com"));

    }

    @Test
    public void deveAlterarTipoContato() {

        Contato contato = criarContato();

        contato.alterarTipo(TipoContato.COMERCIAL);

        assertThat(contato.getTipo())
                .isEqualTo(TipoContato.COMERCIAL);

    }

    @Test
    public void deveLancarExcecaoAoAlterarNomeParaNulo() {

        Contato contato = criarContato();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> contato.alterarNome(null)
        );

        assertThat(exception.getMessage()).isEqualTo("Nome do contato é obrigatório");

    }

    @Test
    public void deveLancarExcecaoAoAlterarNomeParaBranco() {

        Contato contato = criarContato();

        RegraNegocioException exception = assertThrows(RegraNegocioException.class,
                () -> contato.alterarNome("")
        );

        assertThat(exception.getMessage()).isEqualTo("Nome do contato é obrigatório");

    }

    @Test
    public void deveLancarExcecaoAoAlterarTelefoneParaNulo() {
        Contato contato = criarContato();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> contato.alterarTelefone(null)
        );

        assertThat(exception.getMessage()).isEqualTo("Telefone é obrigatório");

    }

    @Test
    public void deveLancarExcecaoAoAlterarTipoParaNulo() {

        Contato contato = criarContato();

        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> contato.alterarTipo(null)
        );

        assertThat(exception.getMessage()).isEqualTo("Tipo de contato é obrigatório");

    }

}
