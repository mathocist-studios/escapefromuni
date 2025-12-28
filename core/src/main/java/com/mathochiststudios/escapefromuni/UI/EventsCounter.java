package com.mathochiststudios.escapefromuni.UI;

public class EventsCounter {

    private int positiveEventsEncountered = 0;
    private int negativeEventsEncountered = 0;
    private int hiddenEventsEncountered = 0;

    // Event trackers
    private boolean speedPositiveEventsEncountered = false;
    private boolean foundLibraryCard = false;
    private boolean foundBasementKey = false;
    private boolean handedInWallet = false;
    private boolean caughtByDean = false;
    private boolean buyRollerSkates = false;
    private boolean isSlowedByWater = false;
    private boolean foundFriend = false;
    private boolean boughtEnergyDrink = false;
    private boolean movedDucks = false;
    private boolean hasLongBoiPet = false;

    // Achievement trackers
    private boolean hasExitLibraryAchieved = false;
    private boolean hasMadeItToBusStopAchieved = false;
    private boolean collectedAllCoinsAchieved = false;
    private boolean collectedAllPowerUpsAchieved = false;
    private boolean completedGameAchieved = false;

    public int getPositiveEventsEncountered() {
        return positiveEventsEncountered;
    }

    public int getNegativeEventsEncountered() {
        return negativeEventsEncountered;
    }

    public int getHiddenEventsEncountered() {
        return hiddenEventsEncountered;
    }

    public void speedPositiveEventsEncountered() {
        if (this.speedPositiveEventsEncountered) {
            return;
        }
        this.positiveEventsEncountered += 1;
        this.speedPositiveEventsEncountered = true;
    }

    public void foundLibraryCard() {
        if (this.foundLibraryCard) {
            return;
        }
        this.negativeEventsEncountered += 1;
        this.foundLibraryCard = true;
    }

    public boolean hasFoundLibraryCard() {
        return this.foundLibraryCard;
    }

    public void foundBasementKey() {
        if (this.foundBasementKey) {
            return;
        }
        this.negativeEventsEncountered += 1;
        this.foundBasementKey = true;
    }

    public boolean hasFoundBasementKey() {
        return this.foundBasementKey;
    }

    public void handedInWallet() {
        if (this.handedInWallet) {
            return;
        }
        this.hiddenEventsEncountered += 1;
        this.handedInWallet = true;
    }

    public boolean hasHandedInWallet() {
        return this.handedInWallet;
    }

    public void hasExitLibrary() {
        this.hasExitLibraryAchieved = true;
    }

    public boolean getHasExitLibraryAchieved() {
        return this.hasExitLibraryAchieved;
    }

    public void caughtByDean() {
        if (this.caughtByDean) {
            return;
        }
        this.hiddenEventsEncountered += 1;
        this.caughtByDean = true;
    }

    public boolean getCaughtByDean() {
        return this.caughtByDean;
    }

    public void boughtRollerSkates() {
        if (this.buyRollerSkates) {
            return;
        }
        this.positiveEventsEncountered += 1;
        this.buyRollerSkates = true;
    }

    public boolean hasBoughtRollerSkates() {
        return this.buyRollerSkates;
    }

    public void slowedByWater() {
        if (this.isSlowedByWater) {
            return;
        }
        this.negativeEventsEncountered += 1;
        this.isSlowedByWater = true;
    }

    public boolean hasBeenSlowedByWater() {
        return this.isSlowedByWater;
    }

    public void foundFriend() {
        if (this.foundFriend) {
            return;
        }
        this.negativeEventsEncountered += 1;
        this.foundFriend = true;
    }

    public boolean hasFoundFriend() {
        return this.foundFriend;
    }

    public void boughtEnergyDrink() {
        if (this.boughtEnergyDrink) {
            return;
        }
        this.positiveEventsEncountered += 1;
        this.boughtEnergyDrink = true;
    }

    public boolean hasBoughtEnergyDrink() {
        return this.boughtEnergyDrink;
    }

    public void movedDucks() {
        if (this.movedDucks) {
            return;
        }
        this.negativeEventsEncountered += 1;
        this.movedDucks = true;
    }

    public boolean hasMovedDucks() {
        return this.movedDucks;
    }

    public void madeItToBusStop() {
        this.hasMadeItToBusStopAchieved = true;
    }

    public boolean hasMadeItToBusStop() {
        return this.hasMadeItToBusStopAchieved;
    }

    public void collectedAllCoins() {
        this.collectedAllCoinsAchieved = true;
    }

    public boolean hasCollectedAllCoins() {
        return this.collectedAllCoinsAchieved;
    }

    public void collectedAllPowerUps() {
        this.collectedAllPowerUpsAchieved = true;
    }

    public boolean hasCollectedAllPowerUps() {
        return this.collectedAllPowerUpsAchieved;
    }

    public void completedGame() {
        this.completedGameAchieved = true;
    }

    public boolean hasCompletedGame() {
        return this.completedGameAchieved;
    }

    public void hasLongBoiPet() {
        if (this.hasLongBoiPet) {
            return;
        }
        this.positiveEventsEncountered += 1;
        this.hasLongBoiPet = true;
    }

    public boolean getHasLongBoiPet() {
        return this.hasLongBoiPet;
    }

}
