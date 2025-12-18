package com.mathochiststudios.escapefromuni.levels.LevelDoors;

import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests.EnterBasementQuest;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

public class LibraryBasementDoor {

    // Instantiate rectangle to cover desired section of map to block.
    private final Rectangle rectangle;

    private double notificationCooldown = 0.0;

    public LibraryBasementDoor() {
        // instantiate rectangle to block path.
        this.rectangle = new Rectangle(23, 4, 1, 3);
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

        if (player.getInventory().hasItem(InventoryObject.BASEMENT_KEY)) {
            return;
        }

        this.disallowCollision(player);

        if (System.currentTimeMillis() - notificationCooldown < 2000) {
            return;
        }

        notificationCooldown = System.currentTimeMillis();

        Notification notification = new Notification(
            "You will need the basement key to enter the library basement.",
            2f,
            NotificationType.SPEECH,
            level.getGame().getTextureManager().getGameSmallFont()
        );
        level.getGame().getHud().getNotificationManager().addNotification(notification);
        level.getGame().getHud().getQuestSystem().addMainQuest(
            new EnterBasementQuest()
        );

    }
}
