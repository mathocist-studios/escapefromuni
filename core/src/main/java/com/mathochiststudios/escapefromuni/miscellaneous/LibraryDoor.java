package com.mathochiststudios.escapefromuni.miscellaneous;

import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

public class LibraryDoor {

    // Instantiate rectangle to cover desired section of map to block.
    Rectangle rectangle;


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
        if (this.collides(player)) {
            if (!player.getInventory().hasItem(InventoryObject.KEYCARD)) {
                this.disallowCollision(player);
                player.setNegativeEventsEncountered(player.getNegativeEventsEncountered() + 1);
                Notification notification = new Notification(
                    "You need your library card to exit!",
                    2f,
                    NotificationType.SPEECH,
                    level.getGame().getTextureManager().getGameSmallFont()
                );
                level.getGame().getHud().getNotificationManager().addNotification(notification);
            }
            if (!player.getInventory().hasItem(InventoryObject.RUCKSACK)) {
                this.disallowCollision(player);
                player.setNegativeEventsEncountered(player.getNegativeEventsEncountered() + 1);
                Notification notification = new Notification(
                    "You need your rucksack to carry your things!",
                    2f,
                    NotificationType.SPEECH,
                    level.getGame().getTextureManager().getGameSmallFont()
                );
                level.getGame().getHud().getNotificationManager().addNotification(notification);
            }
        }
    }
}
