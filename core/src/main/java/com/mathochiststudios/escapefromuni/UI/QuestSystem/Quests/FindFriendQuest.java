package com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quest;

public class FindFriendQuest extends Quest {

    public FindFriendQuest() {
        super("Find your friend", 1);
    }

    @Override
    public boolean isCompleted(Game game) {
        return game.friendFollowing;
    }
}
