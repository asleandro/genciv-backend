package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.exception.CatalogoDominioException;
import br.com.genciv.catalogo.domain.valueobject.ComposicaoServicoId;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.genciv.shared.util.StringUtils.isBlank;

@Getter
public class ComposicaoServico {

    private final ComposicaoServicoId id;
    private final Servico servico;
    private String descricao;
    private final List<ItemComposicaoServico> itens;

    public ComposicaoServico(
            ComposicaoServicoId id,
            Servico servico,
            String descricao
    ) {
        this.id = Objects.requireNonNull(id, "Id é obrigatório");
        this.servico = Objects.requireNonNull(servico, "Serviço é obrigatório");
        this.descricao = requireText(descricao, "Descrição é obrigatória");
        this.itens = new ArrayList<>();

    }

    public List<ItemComposicaoServico> getItens() {
        return List.copyOf(itens);
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

        ItemComposicaoServico item = buscarItem(material);

        itens.remove(item);

    }

    public void alterarDescricao(String descricao) {
        this.descricao = requireText(
                descricao,
                "Descrição é obrigatória"
        );
    }

    public void alterarQuantidade(Material material, BigDecimal quantidade) {

        ItemComposicaoServico item = buscarItem(material);

        item.alterarQuantidade(quantidade);
    }

    private ItemComposicaoServico buscarItem(Material material) {
        Objects.requireNonNull(material, "Material é obrigatório");

        return itens.stream()
                .filter(item -> item.getMaterial().equals(material))
                .findFirst()
                .orElseThrow(() ->
                        new CatalogoDominioException(
                                "Material não existe na composição"
                        ));
    }

    private boolean materialJaExiste(Material material) {
        Objects.requireNonNull(material, "Material é obrigatório");
        return itens.stream()
                .anyMatch(item ->
                        item.getMaterial().equals(material));
    }

    private static String requireText(String valor, String mensagem) {
        if (isBlank(valor)) {
            throw new CatalogoDominioException(mensagem);
        }
        return valor.trim();
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
