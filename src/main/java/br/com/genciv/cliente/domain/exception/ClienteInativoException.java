package br.com.genciv.cliente.domain.exception;

import java.util.UUID;

public class ClienteInativoException extends ClienteException {

  private static final String ERROR_CODE = "CLIENTE_INATIVO";

  public ClienteInativoException(UUID clientId) {
        super(
                ERROR_CODE,
                String.format("O cliente %s está inativo", clientId));
    }
}
