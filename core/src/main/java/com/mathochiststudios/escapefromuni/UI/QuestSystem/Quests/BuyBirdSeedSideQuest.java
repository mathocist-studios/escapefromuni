package com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quest;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;

public class BuyBirdSeedSideQuest extends Quest {

    public BuyBirdSeedSideQuest() {
        super("Buy Bird Seed", -1);
    }

    @Override
    public boolean isCompleted(Game game) {
        return game.getPlayer().getInventory().hasItem(InventoryObject.BIRDSEED);
    }

}
