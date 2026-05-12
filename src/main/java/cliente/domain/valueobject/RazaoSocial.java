package cliente.domain.valueobject;

import cliente.domain.exception.RazaoSocialInvalidaException;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class RazaoSocial implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int TAMANHO_MINIMO = 3;
    private static final int TAMANHO_MAXIMO = 250;
    private static final Pattern CARACTERES_INVALIDOS =
            Pattern.compile("[^\\p{L}\\p{N}\\s\\.\\-\\/&()]");

    private static final Pattern APENAS_NUMEROS =
            Pattern.compile("^\\d+$");

    private final String valor;

    public RazaoSocial(String valor){
        Objects.requireNonNull(valor, "Razão Social é obrigatória");

        String valorNormalizado = normalizar(valor);
        validar(valorNormalizado);
        this.valor = valorNormalizado;
    }

    private String normalizar(String valor){
        String texto = removerCaracteresControle(valor);
        texto = removerEspacosDuplicados(texto);
        texto = texto.trim();
        texto = texto.toUpperCase();
        return texto;
    }

    private String removerCaracteresControle(String valor){
        return valor.replaceAll("[\\n\\r\\t]", " ");
    }

    private String removerEspacosDuplicados(String valor){
        return valor.replaceAll("\\s+", " ");
    }

    private void validar(String valor){
        validarNaoVazio(valor);
        validarTamanho(valor);
        validarCaracteres(valor);
        validarNaoNumerico(valor);
    }

    private void validarNaoVazio(String valor){
        if(valor.isBlank()){
            throw new RazaoSocialInvalidaException("Razao social não pode ser vazia");
        }
    }

    private void validarTamanho(String valor){
        if(valor.length() < TAMANHO_MINIMO){
            throw new RazaoSocialInvalidaException(
                    String.format("Razão social deve possuir no mínimo %d caracteres", TAMANHO_MINIMO)
            );
        }

        if(valor.length() < TAMANHO_MAXIMO){
            throw new RazaoSocialInvalidaException(
                    String.format("Razão social deve possuir no máximo %d caracteres", TAMANHO_MAXIMO)
            );
        }
    }

    private void validarCaracteres(String valor){
        if(CARACTERES_INVALIDOS.matcher(valor).find()){
            throw new RazaoSocialInvalidaException("Razão social não pode conter apenas números");
        }
    }

    private void validarNaoNumerico(String valor){
        if(APENAS_NUMEROS.matcher(valor).matches()){
            throw new RazaoSocialInvalidaException("Razão social não pode conter apenas números");
        }
    }

    public String formatado() {

        String[] palavras =
                valor.toLowerCase().split(" ");

        StringBuilder builder =
                new StringBuilder();

        for (String palavra : palavras) {

            if (palavra.isBlank()) {
                continue;
            }

            if (ehSiglaEmpresarial(palavra)) {

                builder.append(
                        palavra.toUpperCase()
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
                texto.toUpperCase()
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
