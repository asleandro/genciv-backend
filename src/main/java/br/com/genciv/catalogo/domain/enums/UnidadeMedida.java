package br.com.genciv.catalogo.domain.enums;

public enum UnidadeMedida {

    UNID("un", "Unidade"),

    M("m", "Metro"),
    M2("m²", "Metro Quadrado"),
    M3("m³", "Metro Cúbico"),

    KG("kg", "Quilograma"),
    TON("t", "Tonelada"),

    L("l", "Litro"),

    H("h", "Hora"),
    DIA("dia", "Diária"),
    MES("mês", "Mensal"),

    RL("rolo", "Rolo"),
    BD("balde", "Balde"),
    CX("caixa", "Caixa"),
    CJ("conjunto", "Conjunto"),

    SACO("saco", "Saco"),
    PACOTE("pacote", "Pacote"),
    PALETE("palete", "Palete"),

    BOBINA("bobina", "Bobina"),
    CARTELA("cartela", "Cartela"),
    LATA("lata", "Lata"),

    CENTO("cento", "Cento"),
    MILHEIRO("milheiro", "Milheiro"),

    VB("vb", "Verba");

    private final String simbolo;
    private final String descricao;

    UnidadeMedida(String simbolo, String descricao) {
        this.simbolo = simbolo;
        this.descricao = descricao;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDescricaoCompleta() {
        return descricao + " (" + simbolo + ")";
    }
}
