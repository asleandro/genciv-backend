package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.enums.UnidadeMedida;
import br.com.genciv.catalogo.domain.exception.MaterialDominioException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

import static br.com.genciv.shared.util.StringUtils.isBlank;

@Getter
public class Servico {

    private UUID id;
    private String codigo;
    private String descricao;
    private UnidadeMedida unidadeMedida;
    private boolean ativo;

    private Servico(
            UUID id,
            String codigo,
            String descricao,
            UnidadeMedida unidadeMedida,
            boolean ativo
    ) {
        this.id = Objects.requireNonNull(id, "ID é obrigatório");
        this.codigo = requireText(codigo, "Código é obrigatório");
        this.descricao = requireText(descricao, "Descrição é obrigatória");
        this.unidadeMedida = Objects.requireNonNull(unidadeMedida, "UnidadeMedida é obrigaória");
        this.ativo = ativo;
    }

    public static Servico criar(
            String codigo,
            String descricao,
            UnidadeMedida unidadeMedida,
            boolean ativo
    ) {
        return new Servico(
                UUID.randomUUID(),
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

    public void alterarDescricao(String novaDescricao) {
        this.descricao = requireText(novaDescricao, "Descrição é obrigatória");
    }

    private static String requireText(String valor, String mensagem) {
        if (isBlank(valor)) {
            throw new MaterialDominioException(mensagem);
        }
        return valor.trim();
    }


}
