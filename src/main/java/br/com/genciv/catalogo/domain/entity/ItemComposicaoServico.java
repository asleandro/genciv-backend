package br.com.genciv.catalogo.domain.entity;

import br.com.genciv.catalogo.domain.exception.CatalogoDominioException;
import br.com.genciv.catalogo.domain.valueobject.ItemComposicaoServicoId;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
public class ItemComposicaoServico {

    private final ItemComposicaoServicoId id;
    private final Material material;
    private BigDecimal quantidade;

    private ItemComposicaoServico(ItemComposicaoServicoId id, Material material, BigDecimal quantidade) {
        this.id = Objects.requireNonNull(id, "Id é obrigatório");
        this.material = Objects.requireNonNull(material, "Material é obrigatório");
        this.quantidade = validarQuantidade(quantidade);
    }

    public static ItemComposicaoServico criar(
            Material material,
            BigDecimal quantidade
    ) {
        return new ItemComposicaoServico(
                ItemComposicaoServicoId.novo(),
                material,
                quantidade
        );
    }

    public void alterarQuantidade(BigDecimal quantidade) {
        this.quantidade = validarQuantidade(quantidade);
    }

    private BigDecimal validarQuantidade(BigDecimal quantidade) {

        Objects.requireNonNull(quantidade, "Quantidade é obrigatória");

        if (quantidade.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CatalogoDominioException("Quantidade deve ser maior que zero");
        }
        return quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ItemComposicaoServico itemComposicaoServico)) {
            return false;
        }
        return Objects.equals(id, itemComposicaoServico.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
