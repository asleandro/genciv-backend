package br.com.genciv.cliente.domain.valueobject;

import br.com.genciv.cliente.domain.exception.RazaoSocialInvalidaException;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class RazaoSocial implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int TAMANHO_MINIMO = 3;
    private static final int TAMANHO_MAXIMO = 250;

    private static final Pattern CARACTERES_VALIDOS =
            Pattern.compile("^[\\p{L}0-9 .&()/'-]+$");

    private static final Pattern POSSUI_LETRA =
            Pattern.compile(".*\\p{L}.*");

    private final String valor;

    public RazaoSocial(String valor) {
        Objects.requireNonNull(valor, "Razão Social é obrigatória");

        String valorNormalizado = normalizar(valor);
        validar(valorNormalizado);
        this.valor = valorNormalizado;
    }

    private String normalizar(String valor) {
        String texto = removerCaracteresControle(valor);
        texto = removerEspacosDuplicados(texto);
        texto = texto.trim();
        texto = texto.toUpperCase(Locale.ROOT);
        return texto;
    }

    private String removerCaracteresControle(String valor) {

        return valor.replaceAll("[\\n\\r\\t]", " ");
    }

    private String removerEspacosDuplicados(String valor) {

        return valor.replaceAll("\\s+", " ");
    }

    private void validar(String valor) {
        validarNaoVazio(valor);
        validarTamanho(valor);
        validarCaracteres(valor);
        validarPossuiLetras(valor);
    }

    private void validarNaoVazio(String valor) {
        if (valor.isBlank()) {
            throw new RazaoSocialInvalidaException("Razão social não pode ser vazia");
        }
    }

    private void validarTamanho(String valor) {
        if (valor.length() < TAMANHO_MINIMO) {
            throw new RazaoSocialInvalidaException(
                    String.format("Razão social deve possuir no mínimo %d caracteres", TAMANHO_MINIMO)
            );
        }

        if (valor.length() > TAMANHO_MAXIMO) {
            throw new RazaoSocialInvalidaException(
                    String.format("Razão social deve possuir no máximo %d caracteres", TAMANHO_MAXIMO)
            );
        }
    }

    private void validarCaracteres(String valor) {
        if (!CARACTERES_VALIDOS.matcher(valor).matches()) {
            throw new RazaoSocialInvalidaException("Razão social possui caracteres inválidos");
        }
    }

    private void validarPossuiLetras(String valor) {
        if (!POSSUI_LETRA.matcher(valor).matches()) {
            throw new RazaoSocialInvalidaException(
                    "Razão social deve possuir ao menos uma letra"
            );
        }
    }

    //TODO: Implementar com stream
    public String formatado() {

        String[] palavras =
                valor.toLowerCase(Locale.ROOT).split(" ");

        StringBuilder builder =
                new StringBuilder();

        for (String palavra : palavras) {

            if (palavra.isBlank()) {
                continue;
            }

            if (ehSiglaEmpresarial(palavra)) {

                builder.append(
                        palavra.toUpperCase(Locale.ROOT)
                );

            } else {

                builder.append(
                        Character.toUpperCase(
                                palavra.charAt(0)
                        )
                ).append(
                        palavra.substring(1)
                );
            }

            builder.append(" ");
        }

        return builder.toString().trim();
    }

    private boolean ehSiglaEmpresarial(
            String palavra
    ) {

        return palavra.equalsIgnoreCase("LTDA")
                || palavra.equalsIgnoreCase("S/A")
                || palavra.equalsIgnoreCase("ME")
                || palavra.equalsIgnoreCase("MEI")
                || palavra.equalsIgnoreCase("EPP")
                || palavra.equalsIgnoreCase("EIRELI");
    }

    public boolean contem(
            String texto
    ) {

        Objects.requireNonNull(
                texto,
                "Texto é obrigatório"
        );

        return valor.contains(
                texto.toUpperCase(Locale.ROOT)
        );
    }

    public int tamanho() {

        return valor.length();
    }

    @Override
    public boolean equals(
            Object o
    ) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof RazaoSocial that)) {
            return false;
        }

        return valor.equals(that.valor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(valor);
    }

    @Override
    public String toString() {

        return valor;
    }

}
