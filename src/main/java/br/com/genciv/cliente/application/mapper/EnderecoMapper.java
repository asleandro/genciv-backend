package br.com.genciv.cliente.application.mapper;

import br.com.genciv.cliente.application.dto.EnderecoRequest;
import br.com.genciv.cliente.domain.enums.UnidadeFederativa;
import br.com.genciv.cliente.domain.valueobject.CEP;
import br.com.genciv.cliente.domain.valueobject.Endereco;

public final class EnderecoMapper {

    private EnderecoMapper() {

    }

    public static Endereco toDomain(EnderecoRequest request) {

        CEP cep = request.cep() != null
                ? new CEP(request.cep())
                : null;

        UnidadeFederativa uf = request.uf() != null
                ? UnidadeFederativa.valueOf(
                request.uf()
                        .trim()
                        .toUpperCase()
        )
                : null;

        return new Endereco(
                request.logradouro(),
                request.numero(),
                request.complemento(),
                request.bairro(),
                request.cidade(),
                uf,
                cep
        );

    }
}
