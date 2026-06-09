package br.com.genciv.cliente.application.mapper;

import br.com.genciv.cliente.application.dto.ContatoRequest;
import br.com.genciv.cliente.domain.entity.Contato;
import br.com.genciv.cliente.domain.valueobject.Email;
import br.com.genciv.cliente.domain.valueobject.Telefone;

public final class ContatoMapper {

    private ContatoMapper() {
    }

    public static Contato toDomain(ContatoRequest request) {

        return Contato.criar(
                request.nome(),
                new Telefone(
                        request.ddd(),
                        request.telefone()
                ),
                new Email(request.email()),
                request.tipo()
        );
    }
}
