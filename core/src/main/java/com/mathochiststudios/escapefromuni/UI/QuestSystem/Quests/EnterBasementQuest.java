package com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quest;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;

public class EnterBasementQuest extends Quest {

    public EnterBasementQuest() {
        super("Find the basement key", 2);
    }

    @Override
    public boolean isCompleted(Game game) {
        return game.getPlayer().getInventory().hasItem(InventoryObject.BASEMENT_KEY);
    }
}
