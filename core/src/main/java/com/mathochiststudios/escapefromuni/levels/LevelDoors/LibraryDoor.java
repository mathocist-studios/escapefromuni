package com.mathochiststudios.escapefromuni.levels.LevelDoors;

import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests.EscapeLibraryQuest;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

public class LibraryDoor {

    // Instantiate rectangle to cover desired section of map to block.
    private final Rectangle rectangle;

    private double timeSinceLastNotification = 0.0;

    public LibraryDoor() {
        // instantiate rectangle to block path.
        this.rectangle = new Rectangle(3, 3, 3, 1);
    }

    // Checks for collision between player and door.
    public boolean collides(Player player) {
        return rectangle.overlaps(player.getMoneyRectangle());
    }

    // Disallows the collision between player and wall.
    public void disallowCollision(Player player) {
        player.getMoneySprite().setX(player.getOldMoneyX());
        player.getMoneySprite().setY(player.getOldMoneyY());
    }

    // Invoked every frame, allows player to pass if hasLibraryCard, else collision is active.
    public void update(Player player, Level level) {
        if (!this.collides(player)) {
            return;
        }
        if (!player.getInventory().hasItem(InventoryObject.KEYCARD)) {
            this.disallowCollision(player);

            if (System.currentTimeMillis() - timeSinceLastNotification < 2000) {
                return;
            }

            timeSinceLastNotification = System.currentTimeMillis();

            Notification notification = new Notification(
                "You need your library card to exit!",
                2f,
                NotificationType.SPEECH,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
            level.getGame().getHud().getQuestSystem().addMainQuest(
                new EscapeLibraryQuest()
            );
            return;
        }
        if (!player.getInventory().hasItem(InventoryObject.RUCKSACK)) {
            this.disallowCollision(player);

            if (System.currentTimeMillis() - timeSinceLastNotification < 2000) {
                return;
            }

            timeSinceLastNotification = System.currentTimeMillis();


            Notification notification = new Notification(
                "You need your rucksack to carry your things!",
                2f,
                NotificationType.SPEECH,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
            level.getGame().getHud().getQuestSystem().addMainQuest(
                new EscapeLibraryQuest()
            );
            return;
        }
        if (player.getInventory().hasItem(InventoryObject.KEYCARD) &&
            player.getInventory().hasItem(InventoryObject.RUCKSACK) &&
            !player.getEventsCounter().getHasExitLibraryAchieved()) {
            Notification notification = new Notification(
                "Exited the library",
                4f,
                NotificationType.ACHIEVEMENT,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
            player.getEventsCounter().hasExitLibrary();
        }
    }
}
