package br.com.genciv.cliente.application.dto;

import br.com.genciv.cliente.domain.enums.TipoContato;

public record ContatoRequest(

        String nome,
        String ddd,
        String telefone,
        String email,
        TipoContato tipo
) {
}
