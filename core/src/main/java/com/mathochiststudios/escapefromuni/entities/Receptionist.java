package com.mathochiststudios.escapefromuni.entities;

import com.badlogic.gdx.graphics.Texture;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

public class Receptionist extends XAxisSlidingEntity{

    public Receptionist(Texture texture, float initialX, float initialY, float speed, float width, float height, int x1, int x2) {
        super(texture, initialX, initialY, speed, width, height, x1, x2);
    }

    public void update(float deltaTime, Player player, Level level) {
        // Checks for collision between player and receptionist, and if player has wallet then some logic.
        this.handInWallet(player, level);
        // Created the updated x value of the sprite, given the speed it is moving and the time that has passed.
        float updatedX = this.sprite.getX() + (speed * deltaTime);
        if (updatedX < this.x1) {
            updatedX = this.x1;
            this.reverseMovement();
        }
        // If the x value has passed to the right of x2 (i.e. is greater than) then the x value is set to x2 and its
        // speed is reversed.
        else if (updatedX + this.sprite.getWidth() + (this.speed * deltaTime) > this.x2) {
            updatedX = this.x2 - this.sprite.getWidth();
            this.reverseMovement();
        }
        this.sprite.setX(updatedX);
    }

    public void handInWallet(Player player, Level level) {
        if (this.isCollidingWithPlayer(player.getMoneyRectangle())) {
            if (player.getInventory().hasItem(InventoryObject.WALLET)) {
                // Add to hidden events encountered, as this is a hidden event.
                player.setHiddenEventsEncountered(player.getHiddenEventsEncountered() + 1);
                player.getGameTimer().addTime(40);
                player.getInventory().removeItem(InventoryObject.WALLET);
                // Time + 30s
                Notification notification = new Notification(
                    "You handed in the lost wallet to the receptionist (+40s)!",
                    3f,
                    NotificationType.SPEECH,
                    level.getGame().getTextureManager().getGameSmallFont()
                );
                level.getGame().getHud().getNotificationManager().addNotification(notification);
            }
        }
    }
}
