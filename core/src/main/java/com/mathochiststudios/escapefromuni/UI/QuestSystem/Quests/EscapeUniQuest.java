package com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quest;

public class EscapeUniQuest extends Quest {

    public EscapeUniQuest() {
        super("Escape the University", 0);
    }

    @Override
    public boolean isCompleted(Game game) {
        return false;
    }
}
