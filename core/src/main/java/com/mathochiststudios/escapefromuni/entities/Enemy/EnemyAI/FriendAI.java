package com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendAI implements IEnemyAI {

    private final ArrayList<EnemyMoveDirection> recentMoves = new ArrayList<>();
    private static final int MAX_HISTORY = 40; // increased history for better pattern detection

    /**
     * Non-mutating check whether appending newMove to recent would create a repeating pattern.
     * Ignores STATIONARY and constant repeated chunks (e.g. RIGHT, RIGHT, RIGHT).
     */
    private boolean wouldCreatePattern(List<EnemyMoveDirection> recent, EnemyMoveDirection newMove) {
        if (newMove == EnemyMoveDirection.STATIONARY) {
            return false;
        }

        ArrayList<EnemyMoveDirection> temp = new ArrayList<>(recent);
        temp.add(newMove);
        while (temp.size() > MAX_HISTORY) {
            temp.remove(0);
        }

        // Check for repeating chunks of size 2..min(5, temp.size()/2)
        int maxPattern = Math.min(5, temp.size() / 2);
        for (int patternLength = 2; patternLength <= maxPattern; patternLength++) {
            boolean patternFound = true;
            // compare last patternLength elements with the previous patternLength elements
            for (int i = 0; i < patternLength; i++) {
                int idxA = temp.size() - 1 - i;
                int idxB = temp.size() - 1 - i - patternLength;
                if (temp.get(idxA) != temp.get(idxB)) {
                    patternFound = false;
                    break;
                }
            }
            if (!patternFound) {
                continue;
            }

            // If the detected repeating chunk is constant (all same direction), ignore it.
            boolean allSame = true;
            EnemyMoveDirection first = temp.get(temp.size() - 1);
            for (int i = 1; i < patternLength; i++) {
                if (temp.get(temp.size() - 1 - i) != first) {
                    allSame = false;
                    break;
                }
            }
            if (allSame) {
                continue; // treat constant repetition as not a pattern
            }

            // Found a non-constant repeating chunk => pattern
            return true;
        }

        return false;
    }

    @Override
    public EnemyMoveDirection update(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed) {
        EnemyMoveDirection nextMove = evaluate_next_move(game, enemy, deltaTime, currentLevel, player, speed);

        // If executing nextMove would create a repeating pattern, try to pick an alternative
        if (wouldCreatePattern(recentMoves, nextMove)) {
            nextMove = chooseBreakMove(game, enemy, deltaTime, currentLevel, player, speed, nextMove);
        }

        // Apply the decided move (mutate enemy position) and record it
        if (nextMove != EnemyMoveDirection.STATIONARY) {
            applyMove(enemy, nextMove, deltaTime, speed);
            recentMoves.add(nextMove);
            if (recentMoves.size() > MAX_HISTORY) {
                recentMoves.remove(0);
            }
        }

        return nextMove;
    }

    /**
     * Choose an alternative move that:
     * - is legal (no collision),
     * - tends to reduce Manhattan distance to the player,
     * - does not create a repeating pattern when appended.
     * If none suitable found, returns the originalDesired move.
     */
    private EnemyMoveDirection chooseBreakMove(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed, EnemyMoveDirection originalDesired) {
        // Get current positions
        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();
        float playerX = player.getMoneySprite().getX();
        float playerY = player.getMoneySprite().getY();

        float step = speed * deltaTime;
        boolean[] collisions = EnemyAI.getCollisions(game, step, enemy.getEnemyCollision());

        // Candidate directions (prefer those that reduce Manhattan distance)
        List<EnemyMoveDirection> candidates = Arrays.asList(
            EnemyMoveDirection.RIGHT,
            EnemyMoveDirection.LEFT,
            EnemyMoveDirection.UP,
            EnemyMoveDirection.DOWN
        );

        // Evaluate each candidate: projected Manhattan distance and validity
        EnemyMoveDirection best = originalDesired;
        float bestDist = manhattanDist(enemyX, enemyY, playerX, playerY); // baseline (no move)
        // compute baseline after doing originalDesired if allowed
        if (originalDesired != EnemyMoveDirection.STATIONARY) {
            if (isDirectionAllowed(originalDesired, collisions)) {
                float[] p = projectedPos(enemyX, enemyY, originalDesired, step);
                bestDist = manhattanDist(p[0], p[1], playerX, playerY);
            }
        }

        // Try other candidates first (prefer moves different from originalDesired)
        for (EnemyMoveDirection cand : candidates) {
            if (cand == originalDesired) continue; // try alternatives first
            if (!isDirectionAllowed(cand, collisions)) continue;

            float[] proj = projectedPos(enemyX, enemyY, cand, step);
            float dist = manhattanDist(proj[0], proj[1], playerX, playerY);

            // Prefer moves that reduce Manhattan distance compared to baseline
            if (dist <= bestDist) {
                // Ensure selecting this candidate will not create a repeating pattern
                if (!wouldCreatePattern(recentMoves, cand)) {
                    // pick the candidate with smallest distance
                    if (dist < bestDist || best == originalDesired) {
                        best = cand;
                        bestDist = dist;
                    }
                }
            }
        }

        // If we didn't find an alternative (best still originalDesired), try allowing original but verify it is legal
        if (best == originalDesired) {
            if (originalDesired != EnemyMoveDirection.STATIONARY && isDirectionAllowed(originalDesired, collisions) && !wouldCreatePattern(recentMoves, originalDesired)) {
                return originalDesired;
            }
            // try any legal candidate even if it doesn't reduce distance, to break pattern
            for (EnemyMoveDirection cand : candidates) {
                if (!isDirectionAllowed(cand, collisions)) continue;
                if (!wouldCreatePattern(recentMoves, cand)) {
                    return cand;
                }
            }
            // As a last resort, return originalDesired (may create pattern) or STATIONARY
            if (isDirectionAllowed(originalDesired, collisions)) {
                return originalDesired;
            } else {
                return EnemyMoveDirection.STATIONARY;
            }
        }

        return best;
    }

    private boolean isDirectionAllowed(EnemyMoveDirection dir, boolean[] collisions) {
        if (dir == EnemyMoveDirection.STATIONARY) return false;
        switch (dir) {
            case RIGHT:
                return !collisions[3];
            case LEFT:
                return !collisions[2];
            case UP:
                return !collisions[0];
            case DOWN:
                return !collisions[1];
            default:
                return false;
        }
    }

    private float[] projectedPos(float x, float y, EnemyMoveDirection dir, float step) {
        switch (dir) {
            case RIGHT:
                return new float[]{x + step, y};
            case LEFT:
                return new float[]{x - step, y};
            case UP:
                return new float[]{x, y + step};
            case DOWN:
                return new float[]{x, y - step};
            default:
                return new float[]{x, y};
        }
    }

    private float manhattanDist(float x1, float y1, float x2, float y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Decide the next move toward player without changing enemy position.
     * Uses axis preference but is essentially Manhattan-friendly.
     */
    private EnemyMoveDirection evaluate_next_move(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed) {
        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();

        float playerX = player.getMoneySprite().getX();
        float playerY = player.getMoneySprite().getY();

        boolean[] collisions = EnemyAI.getCollisions(game, speed * deltaTime, enemy.getEnemyCollision());
        float diffX = playerX - enemyX;
        float diffY = playerY - enemyY;

        double distance = Math.sqrt(diffX * diffX + diffY * diffY);
        if (distance < 2.0) {
            return EnemyMoveDirection.STATIONARY;
        }

        if (distance > 5.0) {
            return EnemyMoveDirection.STATIONARY;
        }

        // Prefer the axis that reduces Manhattan distance most; tie-break toward horizontal
        if (Math.abs(diffX) >= Math.abs(diffY)) {
            // prefer horizontal
            if (diffX > 0 && !collisions[3]) {
                return EnemyMoveDirection.RIGHT;
            } else if (diffX < 0 && !collisions[2]) {
                return EnemyMoveDirection.LEFT;
            } else if (diffY > 0 && !collisions[0]) {
                return EnemyMoveDirection.UP;
            } else if (diffY < 0 && !collisions[1]) {
                return EnemyMoveDirection.DOWN;
            }
        } else {
            // prefer vertical
            if (diffY > 0 && !collisions[0]) {
                return EnemyMoveDirection.UP;
            } else if (diffY < 0 && !collisions[1]) {
                return EnemyMoveDirection.DOWN;
            } else if (diffX > 0 && !collisions[3]) {
                return EnemyMoveDirection.RIGHT;
            } else if (diffX < 0 && !collisions[2]) {
                return EnemyMoveDirection.LEFT;
            }
        }
        return EnemyMoveDirection.STATIONARY;
    }

    /**
     * Mutate enemy position by applying the given move.
     */
    private void applyMove(Enemy enemy, EnemyMoveDirection move, float deltaTime, float speed) {
        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();
        switch (move) {
            case RIGHT:
                enemy.setEnemyX(enemyX + speed * deltaTime);
                break;
            case LEFT:
                enemy.setEnemyX(enemyX - speed * deltaTime);
                break;
            case UP:
                enemy.setEnemyY(enemyY + speed * deltaTime);
                break;
            case DOWN:
                enemy.setEnemyY(enemyY - speed * deltaTime);
                break;
            case STATIONARY:
            default:
                break;
        }
    }
}
