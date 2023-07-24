package io.github.tiagoadmstz.productionmonitoring.models;

public enum RegisterType {

    PRODUCAO(1), PARADA(2), PARADA_NAO_PROGRAMADA(3);

    private int value;

    RegisterType(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
