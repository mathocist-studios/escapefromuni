package com.mathochiststudios.escapefromuni.levels.LevelDoors;

import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.BirdSeed;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

public class DuckBlockerP1 {

    // Instantiate rectangle to cover desired section of map to block.
    private final Rectangle rectangle;
    private BirdSeed birdSeed;

    public static double timeSinceLastNotification = 0.0;

    public DuckBlockerP1(BirdSeed birdSeed) {
        // instantiate rectangle to block path.
        this.rectangle = new Rectangle(20, 25, 7, 1);
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

    if (birdSeed == null) {
            this.disallowCollision(player);

            if (System.currentTimeMillis() - timeSinceLastNotification < 2000) {
                return;
            }

            timeSinceLastNotification = System.currentTimeMillis();

            Notification notification = new Notification(
                "Ugh ducks! I need to put down some birdseed to get past them.",
                2f,
                NotificationType.SPEECH,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
        }

    }

    public void setBirdSeed(BirdSeed birdSeed) {
        this.birdSeed = birdSeed;
    }


}
