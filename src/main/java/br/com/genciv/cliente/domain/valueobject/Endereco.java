package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.enums.UnidadeFederativa;
import br.com.genciv.cliente.domain.exception.EnderecoInvalidoException;
import lombok.Getter;

import java.io.Serial;
import java.util.Objects;

@Getter
public final class Endereco {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int TAMANHO_MAXIMO_LOGRADOURO = 150;

    private static final int TAMANHO_MAXIMO_COMPLEMENTO = 120;

    private static final int TAMANHO_MAXIMO_BAIRRO = 80;

    private static final int TAMANHO_MAXIMO_CIDADE = 80;

    private static final int TAMANHO_MAXIMO_NUMERO = 20;

    private final String logradouro;

    private final String numero;

    private final String complemento;

    private final String bairro;

    private final String cidade;

    private final UnidadeFederativa uf;

    private final CEP cep;

    public Endereco(
            String logradouro,
            String numero,
            String complemento,
            String bairro,
            String cidade,
            UnidadeFederativa uf,
            CEP cep
    ) {

        this.logradouro =
                normalizarObrigatorio(
                        "Logradouro",
                        logradouro,
                        "Logradouro é obrigatório",
                        TAMANHO_MAXIMO_LOGRADOURO
                );

        this.numero =
                normalizarObrigatorio(
                        "Número",
                        numero,
                        "Número é obrigatório",
                        TAMANHO_MAXIMO_NUMERO
                );

        this.complemento =
                normalizarOpcional(
                        complemento,
                        TAMANHO_MAXIMO_COMPLEMENTO
                );

        this.bairro =
                normalizarObrigatorio(
                        "Bairro",
                        bairro,
                        "Bairro é obrigatório",
                        TAMANHO_MAXIMO_BAIRRO
                );

        this.cidade =
                normalizarObrigatorio(
                        "Cidade",
                        cidade,
                        "Cidade é obrigatória",
                        TAMANHO_MAXIMO_CIDADE
                );

        if (uf == null) {
            throw new EnderecoInvalidoException("UF é obrigatória");
        }
        this.uf = uf;

        if (cep == null) {
            throw new EnderecoInvalidoException("CEP é obrigatório");
        }
        this.cep = cep;
    }

    private String normalizarObrigatorio(
            String nomeCampo,
            String valor,
            String mensagemErro,
            int tamanhoMaximo
    ) {

        if (valor == null) {
            throw new EnderecoInvalidoException(mensagemErro);
        }

        String valorNormalizado =
                normalizar(valor);

        if (valorNormalizado.isBlank()) {

            throw new EnderecoInvalidoException(
                    mensagemErro
            );
        }

        if (valorNormalizado.length() > tamanhoMaximo) {

            throw new EnderecoInvalidoException(
                    String.format(
                            "%s excede tamanho máximo permitido de %d caracteres",
                            nomeCampo,tamanhoMaximo
                    )
            );
        }

        return valorNormalizado;
    }

    private String normalizarOpcional(
            String valor,
            int tamanhoMaximo
    ) {

        if (valor == null) {
            return null;
        }

        String valorNormalizado =
                normalizar(valor);

        if (valorNormalizado.isBlank()) {
            return null;
        }

        if (valorNormalizado.length() > tamanhoMaximo) {

            throw new EnderecoInvalidoException(
                    String.format(
                            "Complemento excede tamanho máximo permitido de %d caracteres",
                            tamanhoMaximo
                    )
            );
        }

        return valorNormalizado;
    }

    private String normalizar(
            String valor
    ) {

        return valor
                .replaceAll("[\\n\\r\\t]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }

    public boolean possuiComplemento() {

        return complemento != null;
    }

    public String enderecoCompleto() {

        StringBuilder builder =
                new StringBuilder();

        builder.append(logradouro)
                .append(", ")
                .append(numero);

        if (possuiComplemento()) {

            builder.append(" - ")
                    .append(complemento);
        }

        builder.append(" - ")
                .append(bairro)
                .append(" - ")
                .append(cidade)
                .append("/")
                .append(uf.name())
                .append(" - CEP: ")
                .append(cep.formatar());

        return builder.toString();
    }

    public boolean pertenceAoEstado(
            UnidadeFederativa uf
    ) {

        if (uf == null) {
            throw new EnderecoInvalidoException("UF é obrigatória");
        }

        return this.uf == uf;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Endereco that)) {
            return false;
        }

        return Objects.equals(logradouro, that.logradouro)
                && Objects.equals(numero, that.numero)
                && Objects.equals(complemento, that.complemento)
                && Objects.equals(bairro, that.bairro)
                && Objects.equals(cidade, that.cidade)
                && uf == that.uf
                && Objects.equals(cep, that.cep);
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                logradouro,
                numero,
                complemento,
                bairro,
                cidade,
                uf,
                cep
        );
    }

    @Override
    public String toString() {

        return enderecoCompleto();
    }
}