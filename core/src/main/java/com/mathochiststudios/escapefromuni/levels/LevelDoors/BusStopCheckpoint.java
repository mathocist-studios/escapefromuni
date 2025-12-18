package com.mathochiststudios.escapefromuni.levels.LevelDoors;

import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public class BusStopCheckpoint {

    // Instantiate rectangle to cover desired section of map to block.
    Rectangle rectangle;

    double timeSinceLastNotification = 0.0;

    public BusStopCheckpoint() {
        // instantiate rectangle to block path.
        this.rectangle = new Rectangle(39, 3, 1, 3);
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
        if (!level.getGame().friendFollowing || !player.getEventsCounter().hasMadeItToBusStop()) {
            this.disallowCollision(player);

            if (System.currentTimeMillis() - timeSinceLastNotification < 2000) {
                return;
            }

            timeSinceLastNotification = System.currentTimeMillis();

            Notification notification = new Notification(
                "You should probably find your friend before you get to the bus stop...",
                2f,
                NotificationType.SPEECH,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
        }

    }

}
