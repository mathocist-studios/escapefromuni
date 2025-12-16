package com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public class AStarAI implements IEnemyAI {

    /**
     * Update the enemy's position to move towards the player using a simple greedy approach.
     *
     * @param game         The game instance.
     * @param enemy        The enemy to update.
     * @param deltaTime    The time elapsed since the last update.
     * @param currentLevel The current level.
     * @param player       The player instance.
     * @param speed        The speed at which the enemy moves.
     * @return The direction the enemy moved.
     */
    @Override
    public EnemyMoveDirection update(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed) {
        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();

        float playerX = player.getMoneySprite().getX();
        float playerY = player.getMoneySprite().getY();

        boolean[] collisions = EnemyAI.getCollisions(game, speed * deltaTime, enemy.getEnemyCollision());
        float diffX = playerX - enemyX;
        float diffY = playerY - enemyY;

        // move in direction of player updating the enemy position
        if (Math.abs(diffX) > Math.abs(diffY)) {
            if (diffX > 0 && !collisions[3]) {
                enemy.setEnemyX( enemyX + speed * deltaTime );
                return EnemyMoveDirection.RIGHT;
            } else if (diffX < 0 && !collisions[2]) {
                enemy.setEnemyX( enemyX - speed * deltaTime );
                return EnemyMoveDirection.LEFT;
            } else if (diffY > 0 && !collisions[0]) {
                enemy.setEnemyY( enemyY + speed * deltaTime );
                return EnemyMoveDirection.UP;
            } else if (diffY < 0 && !collisions[1]) {
                enemy.setEnemyY( enemyY - speed * deltaTime );
                return EnemyMoveDirection.DOWN;
            }
        } else {
            if (diffY > 0 && !collisions[0]) {
                enemy.setEnemyY( enemyY + speed * deltaTime );
                return EnemyMoveDirection.UP;
            } else if (diffY < 0 && !collisions[1]) {
                enemy.setEnemyY( enemyY - speed * deltaTime );
                return EnemyMoveDirection.DOWN;
            } else if (diffX > 0 && !collisions[3]) {
                enemy.setEnemyX( enemyX + speed * deltaTime );
                return EnemyMoveDirection.RIGHT;
            } else if (diffX < 0 && !collisions[2]) {
                enemy.setEnemyX( enemyX - speed * deltaTime );
                return EnemyMoveDirection.LEFT;
            }
        }
        return EnemyMoveDirection.STATIONARY;
    }

}
