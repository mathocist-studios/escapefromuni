package com.mathochiststudios.escapefromuni.UI.QuestSystem;

import com.mathochiststudios.escapefromuni.Game;

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

    public int getLevel() {
        return level;
    }

    public abstract boolean isCompleted(Game game);

}
