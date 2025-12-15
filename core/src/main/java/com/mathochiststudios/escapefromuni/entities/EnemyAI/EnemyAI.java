package com.mathochiststudios.escapefromuni.entities.EnemyAI;

public enum EnemyAI {

    STATIC,
    PATROL,
    DUCK,
    A_STAR(AStarAI.class);

    public final Class<? extends IEnemyAI> aiClass;

    EnemyAI(Class<? extends IEnemyAI> aiClass) {
        this.aiClass = aiClass;
    }

    EnemyAI() { // for STATIC, PATROL, DUCK since not implemented yet
        this.aiClass = null;
    }

    public Class<? extends IEnemyAI> getAIClass() {
        return aiClass;
    }

}

