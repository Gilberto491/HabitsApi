package com.app.habits.domain.model;

import lombok.Getter;

@Getter
public enum Rank {

    RECRUTA(0, "Recruta"),
    CABO(10, "Cabo"),
    SARGENTO1(20, "Sargento 1"),
    SARGENTO2(40, "Sargento 2"),
    SARGENTO3(70, "Sargento 3"),
    SUBTENENTE(100, "Subtenente"),
    SEGUNDO_TENENTE(150, "2º Tenente"),
    PRIMEIRO_TENENTE(200, "1º Tenente"),
    CAPITAO(300, "Capitão"),
    MAJOR(400, "Major"),
    TENENTE_CORONEL(500, "Tenente-Coronel"),
    CORONEL(650, "Coronel"),
    CONTRA_ALMIRANTE(800, "Contra-Almirante"),
    VICE_ALMIRANTE(1000, "Vice-Almirante"),
    GENERAL_EXERCITO(1300, "General de Exército"),
    MARECHAL(1600, "Marechal");

    private final int minPoints;
    private final String displayName;

    Rank(int minPoints, String displayName) {
        this.minPoints = minPoints;
        this.displayName = displayName;
    }

    public static Rank fromPoints(int points) {
        Rank current = RECRUTA;
        for (Rank rank : values()) {
            if (points >= rank.minPoints) {
                current = rank;
            }
        }
        return current;
    }
}
