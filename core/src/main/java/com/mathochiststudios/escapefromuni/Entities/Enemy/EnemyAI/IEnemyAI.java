package com.mathochiststudios.escapefromuni.Entities.Enemy.EnemyAI;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.Entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.Entities.Player;
import com.mathochiststudios.escapefromuni.Levels.Level;

public interface IEnemyAI {

    /**
     * Update the enemy's behavior based on its AI type.
     *
     * @param game The main game instance.
     * @param enemy The enemy instance to update.
     * @param deltaTime Time elapsed since the last update.
     * @param currentLevel The current level the enemy is in.
     * @param player The player instance for interaction.
     * @param speed The movement speed of the enemy.
     * @return The new movement direction of the enemy.
     */
    EnemyMoveDirection update(Game game, Enemy enemy, float deltaTime, Level currentLevel, Player player, float speed);

}
