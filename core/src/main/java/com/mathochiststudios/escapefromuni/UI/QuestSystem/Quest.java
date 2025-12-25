package com.mathochiststudios.escapefromuni.UI.QuestSystem;

import com.mathochiststudios.escapefromuni.Game;

/**
 * Abstract class representing a quest in the game.
 */
public abstract class Quest {

    private final String title;
    private final int level;

    public Quest(String title, int level) {
        this.title = title;
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Returns the level associated with this quest.
     * <br>
     * Level numbers correspond to sub-main quest levels <br>
     * i.e. if a level 3 quest is currently active at the top of the main quest stack,
     * then a level 2 quest cannot be added until the level 3 quest is completed.
     *
     * @return the level number
     */
    public int getLevel() {
        return level;
    }

    /**
     * Checks if the quest is completed based on the current game state.
     * <br>
     * This method must be implemented by subclasses to define specific completion criteria.
     *
     * @param game the current game instance
     * @return true if the quest is completed, false otherwise
     */
    public abstract boolean isCompleted(Game game);

}
