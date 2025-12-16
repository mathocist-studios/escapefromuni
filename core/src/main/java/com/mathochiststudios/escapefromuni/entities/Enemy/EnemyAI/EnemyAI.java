package com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI;

import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;

/**
 * Enum representing different types of Enemy AI behaviors.
 */
public enum EnemyAI {

    STATIC,
    PATROL,
    DUCK,
    A_STAR(AStarAI.class),
    FRIEND(FriendAI.class);

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

    /**
     * Checks for potential collisions in all four directions based on the given velocity and enemy collision rectangle.
     *
     * @param game            The game instance containing map collision rectangles.
     * @param vel             The velocity to check for potential collisions.
     * @param enemyCollision  The rectangle representing the enemy's collision box.
     * @return A boolean array indicating collisions in the order: up, down, left, right.
     */
    public static boolean[] getCollisions(Game game, float vel, Rectangle enemyCollision) {
        boolean[] collisions = new boolean[4]; // up, down, left, right

        for (Rectangle tRect : game.mapCollisions) {
            // Up
            collisions[0] = tRect.overlaps(new Rectangle(enemyCollision.x, enemyCollision.y + vel, enemyCollision.width, enemyCollision.height)) || collisions[0];
            // Down
            collisions[1] = tRect.overlaps(new Rectangle(enemyCollision.x, enemyCollision.y - vel, enemyCollision.width, enemyCollision.height)) || collisions[1];
            // Left
            collisions[2] = tRect.overlaps(new Rectangle(enemyCollision.x - vel, enemyCollision.y, enemyCollision.width, enemyCollision.height)) || collisions[2];
            // Right
            collisions[3] = tRect.overlaps(new Rectangle(enemyCollision.x + vel, enemyCollision.y, enemyCollision.width, enemyCollision.height)) || collisions[3];
        }

        return collisions;
    }

}

