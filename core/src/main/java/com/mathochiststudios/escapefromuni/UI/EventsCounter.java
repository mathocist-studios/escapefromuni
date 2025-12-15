package com.mathochiststudios.escapefromuni.UI;

public class EventsCounter {

    private int positiveEventsEncountered = 0;
    private int negativeEventsEncountered = 0;
    private int hiddenEventsEncountered = 0;

    private boolean speedPositiveEventsEncountered = false;
    private boolean foundLibraryCard = false;
    private boolean foundBasementKey = false;
    private boolean handedInWallet = false;
    private boolean caughtByDean = false;
    private boolean buyRollerSkates = false;

    private boolean hasExitLibraryAchieved = false;

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

    public void foundBasementKey() {
        if (this.foundBasementKey) {
            return;
        }
        this.negativeEventsEncountered += 1;
        this.foundBasementKey = true;
    }

    public void handedInWallet() {
        if (this.handedInWallet) {
            return;
        }
        this.hiddenEventsEncountered += 1;
        this.handedInWallet = true;
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

}
