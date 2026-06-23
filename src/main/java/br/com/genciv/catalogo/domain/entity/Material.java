package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.enums.UnidadeMedida;
import br.com.genciv.catalogo.domain.exception.MaterialDominioException;
import br.com.genciv.catalogo.domain.valueobject.MaterialId;
import lombok.Getter;

import java.util.Objects;

import static br.com.genciv.shared.util.StringUtils.isBlank;

@Getter
public class Material {

    private final MaterialId id;
    private String codigo;
    private String descricao;
    private UnidadeMedida unidadeMedida;
    private boolean ativo;

    private Material(
            MaterialId id,
            String codigo,
            String descricao,
            UnidadeMedida unidadeMedida,
            boolean ativo
    ) {
        this.id = Objects.requireNonNull(id, "ID é obrigatório");
        this.codigo = requireText(codigo, "Código é obrigatório");
        this.descricao = requireText(descricao, "Descrição é obrigatória");
        this.unidadeMedida = Objects.requireNonNull(unidadeMedida, "UnidadeMedida é obrigatória");
        this.ativo = ativo;
    }

    public static Material criar(
            String codigo,
            String descricao,
            UnidadeMedida unidadeMedida,
            boolean ativo
    ) {
        return new Material(
                MaterialId.novo(),
                codigo,
                descricao,
                unidadeMedida,
                ativo
        );
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void alterarCodigo(String novoCodigo) {
        this.codigo = requireText(
                novoCodigo,
                "Código é obrigatório"
        );
    }

    public void alterarDescricao(String novaDescricao) {
        this.descricao = requireText(novaDescricao, "Descrição é obrigatória");
    }

    public void alterarUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = Objects.requireNonNull(unidadeMedida);
    }

    private static String requireText(String valor, String mensagem) {
        if (isBlank(valor)) {
            throw new MaterialDominioException(mensagem);
        }
        return valor.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Material material)) return false;

        return Objects.equals(id, material.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
