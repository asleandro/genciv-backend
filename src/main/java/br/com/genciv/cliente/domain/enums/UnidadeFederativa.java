package br.com.genciv.cliente.domain.enums;

public enum UnidadeFederativa {

    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernambuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio Grande do Norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private final String nome;

    UnidadeFederativa(
            String nome
    ) {

        this.nome = nome;
    }

    public String getNome() {

        return nome;
    }

    public static UnidadeFederativa fromSigla(
            String sigla
    ) {

        if (sigla == null || sigla.isBlank()) {

            throw new IllegalArgumentException(
                    "UF é obrigatória"
            );
        }

        try {

            return UnidadeFederativa.valueOf(
                    sigla.trim().toUpperCase()
            );

        } catch (IllegalArgumentException ex) {

            throw new IllegalArgumentException(
                    "UF inválida: " + sigla
            );
        }
    }
}
