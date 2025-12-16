package com.mathochiststudios.escapefromuni.Entities.Enemy.EnemyAI;

import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.Entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.Entities.Player;
import com.mathochiststudios.escapefromuni.Levels.Level;

public class AStarAI implements IEnemyAI {

    private boolean[] getCollisions(Game game, float vel, Rectangle enemyCollision) {
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

    @Override
    public EnemyMoveDirection update(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed) {
        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();

        float playerX = player.getMoneySprite().getX();
        float playerY = player.getMoneySprite().getY();

        boolean[] collisions = getCollisions(game, speed * deltaTime, enemy.getEnemyCollision());
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
