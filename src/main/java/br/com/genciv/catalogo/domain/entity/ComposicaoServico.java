package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.exception.CatalogoDominioException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static br.com.genciv.shared.util.StringUtils.isBlank;

public class ComposicaoServico {

    private final UUID id;
    private final Servico servico;
    private String descricao;
    private final List<ItemComposicaoServico> itens;

    public ComposicaoServico(
            Servico servico,
            String descricao
    ) {
        this.id = UUID.randomUUID();
        this.servico = Objects.requireNonNull(servico, "Serviço é obrigatório");
        this.descricao = requireText(descricao, "Descrição é obrigatória");
        this.itens = new ArrayList<>();

    }

    public List<ItemComposicaoServico> getItens() {
        return List.copyOf(itens);
    }

    private static String requireText(String valor, String mensagem) {
        if (isBlank(valor)) {
            throw new CatalogoDominioException(mensagem);
        }
        return valor.trim();
    }

    public void adicionarItem(Material material, BigDecimal quantidade) {

        if (materialJaExiste(material)) {
            throw new CatalogoDominioException("Material já existe na composição");
        }

        this.itens.add(
                new ItemComposicaoServico(material, quantidade)
        );
    }

    public void removerItem(Material material) {

        Objects.requireNonNull(material, "Material é obrigatório");

        boolean removido = itens.removeIf(item ->
                item.getMaterial().equals(material));

        if (!removido) {
            throw new CatalogoDominioException("Material não existe na composição");
        }

    }

    public void alterarDescricao(String descricao) {
        this.descricao = requireText(
                descricao,
                "Descrição é obrigatória"
        );
    }

    public boolean materialJaExiste(Material material) {
        Objects.requireNonNull(material, "Material é obrigatório");
        return itens.stream()
                .anyMatch(item ->
                        item.getMaterial().equals(material));
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof ComposicaoServico composicaoServico)) return false;

        return Objects.equals(id, composicaoServico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
