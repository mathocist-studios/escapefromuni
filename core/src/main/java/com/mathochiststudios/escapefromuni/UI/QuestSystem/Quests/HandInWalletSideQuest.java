package com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quest;

public class HandInWalletSideQuest extends Quest {

    public HandInWalletSideQuest() {
        super("Hand in wallet", -1);
    }

    @Override
    public boolean isCompleted(Game game) {
        return game.getPlayer().getEventsCounter().hasHandedInWallet();
    }
}
