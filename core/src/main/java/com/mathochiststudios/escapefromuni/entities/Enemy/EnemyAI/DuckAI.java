package com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.Duck;
import com.mathochiststudios.escapefromuni.entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.entities.Utils.Polygon;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public class DuckAI implements IEnemyAI {

    private final Polygon roamArea = new Polygon(new float[][]{
        {20.0f, 27.0f},
        {26.0f, 25.0f},
        {28.0f, 28.0f},
        {26.0f, 30.0f},
        {22.0f, 30.0f}
    });
    private float[] nextPoint = {0, 0};
    private final float[] seedDestination = {0, 0};

    // handle moving towards birdseed if placed down
    private EnemyMoveDirection handleBirdSeedDown(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed) {

        // if not point select a point near the birdseed to stop at
        if (seedDestination[0] == 0 && seedDestination[1] == 0) {
            float birdSeedX = ((Duck) enemy).getBirdSeed().getEntityX() + ((Duck) enemy).getBirdSeed().getEntityWidth() / 2;
            float birdSeedY = ((Duck) enemy).getBirdSeed().getEntityY() + ((Duck) enemy).getBirdSeed().getEntityHeight() / 2;

            seedDestination[0] = birdSeedX + (float)(Math.random() * 2.0f - 1f);
            seedDestination[1] = birdSeedY + (float)(Math.random() * 2.0f - 1f);
        }

        float birdSeedX = seedDestination[0];
        float birdSeedY = seedDestination[1];

        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();

        float angleToBirdSeed = (float) Math.atan2(birdSeedY - enemyY, birdSeedX - enemyX);
        float distanceToStop = 0.5f; // stop 0.5 units away from the birdseed
        float targetX = birdSeedX - (float) Math.cos(angleToBirdSeed) * distanceToStop;
        float targetY = birdSeedY - (float) Math.sin(angleToBirdSeed) * distanceToStop;

        boolean[] collisions = EnemyAI.getCollisions(game, speed * deltaTime, enemy.getEnemyCollision());

        float angle = (float) Math.atan2(targetY - enemyY, targetX - enemyX);
        float dx = (float) Math.cos(angle) * speed * deltaTime;
        float dy = (float) Math.sin(angle) * speed * deltaTime;

        if (Math.abs(enemyX - targetX) < 0.1 && Math.abs(enemyY - targetY) < 0.1) {
            return EnemyMoveDirection.STATIONARY;
        }

        // check if moving in that direction will cause a collision
        // if so try to move in the other axis first
        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0 && !collisions[3]) {
                enemy.setEnemyX( enemyX + dx );
                return EnemyMoveDirection.RIGHT;
            } else if (dx < 0 && !collisions[2]) {
                enemy.setEnemyX( enemyX - Math.abs(dx) );
                return EnemyMoveDirection.LEFT;
            } else if (dy > 0 && !collisions[0]) {
                enemy.setEnemyY( enemyY + Math.abs(dy) );
                return EnemyMoveDirection.UP;
            } else if (dy < 0 && !collisions[1]) {
                enemy.setEnemyY( enemyY - Math.abs(dy) );
                return EnemyMoveDirection.DOWN;
            }
        } else {
            if (dy > 0 && !collisions[0]) {
                enemy.setEnemyY( enemyY + dy );
                return EnemyMoveDirection.UP;
            } else if (dy < 0 && !collisions[1]) {
                enemy.setEnemyY( enemyY - Math.abs(dy) );
                return EnemyMoveDirection.DOWN;
            } else if (dx > 0 && !collisions[3]) {
                enemy.setEnemyX( enemyX + Math.abs(dx) );
                return EnemyMoveDirection.RIGHT;
            } else if (dx < 0 && !collisions[2]) {
                enemy.setEnemyX( enemyX - Math.abs(dx) );
                return EnemyMoveDirection.LEFT;
            }
        }
        return EnemyMoveDirection.STATIONARY;
    }

    @Override
    public EnemyMoveDirection update(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed) {
        // ensure enemy is a duck so we can access duck specific methods like getBirdSeed
        if (!(enemy instanceof Duck)) {
            throw new IllegalArgumentException("DuckAI can only be used with Duck enemies.");
        }

        // check if there is birdseed placed down and go towards it if so
        // pick a random distance to stop from the point

        if (((Duck) enemy).getBirdSeed() != null) {
            return handleBirdSeedDown(game, enemy, deltaTime, currentLevel, player, speed);
        }

        // ROAMING BEHAVIOR if no birdseed
        // if not point select a point in the roam area
        // check if duck can get to that point
        // move towards that point

        if (nextPoint[0] == 0 && nextPoint[1] == 0) {
            // select a random point in the roam area
            nextPoint = roamArea.pickPointInPolygon();
        }

        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();

        if (Math.abs(enemyX - nextPoint[0]) < 0.1 && Math.abs(enemyY - nextPoint[1]) < 0.1) {
            nextPoint[0] = 0;
            nextPoint[1] = 0;
            return EnemyMoveDirection.STATIONARY;
        }

        boolean[] collisions = EnemyAI.getCollisions(game, speed * deltaTime, enemy.getEnemyCollision());

        float angle = (float) Math.atan2(nextPoint[1] - enemyY, nextPoint[0] - enemyX);
        float dx = (float) Math.cos(angle) * speed * deltaTime;
        float dy = (float) Math.sin(angle) * speed * deltaTime;

        // check if moving in that direction will cause a collision

        if (Math.abs(dx) >= Math.abs(dy)) {
            if (dx > 0 && !collisions[3]) {
                enemy.setEnemyX( enemyX + dx );
                return EnemyMoveDirection.RIGHT;
            } else if (dx < 0 && !collisions[2]) {
                enemy.setEnemyX( enemyX - Math.abs(dx) );
                return EnemyMoveDirection.LEFT;
            } else if (dy > 0 && !collisions[0]) {
                enemy.setEnemyY( enemyY + Math.abs(dy) );
                return EnemyMoveDirection.UP;
            } else if (dy < 0 && !collisions[1]) {
                enemy.setEnemyY( enemyY - Math.abs(dy) );
                return EnemyMoveDirection.DOWN;
            }
        } else {
            if (dy > 0 && !collisions[0]) {
                enemy.setEnemyY( enemyY + dy );
                return EnemyMoveDirection.UP;
            } else if (dy < 0 && !collisions[1]) {
                enemy.setEnemyY( enemyY - Math.abs(dy) );
                return EnemyMoveDirection.DOWN;
            } else if (dx > 0 && !collisions[3]) {
                enemy.setEnemyX( enemyX + Math.abs(dx) );
                return EnemyMoveDirection.RIGHT;
            } else if (dx < 0 && !collisions[2]) {
                enemy.setEnemyX( enemyX - Math.abs(dx) );
                return EnemyMoveDirection.LEFT;
            }
        }

        return EnemyMoveDirection.STATIONARY;

    }

}
