package com.mathochiststudios.escapefromuni.collectibles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

public class BasementKey {

    private final Texture texture = new Texture("basement_key.png");
    private final Sprite sprite = new Sprite(texture);

    private final int[] spawn = {38, 26}; // Fixed spawn location for the Rucksack

    private final Rectangle rectangle;
    private boolean isDisposed;

    public BasementKey() {
        this.isDisposed = false;
        this.sprite.setSize(1, 1);
        this.rectangle = new Rectangle(this.spawn[0], this.spawn[1], 1, 1);
        this.sprite.setX(this.spawn[0]);
        this.sprite.setY(this.spawn[1]);
    }

    // Now need to set up the interaction between the player and the LibraryCard.
    public boolean isCollidingWithPlayer(Player player) {
        return this.rectangle.overlaps(player.getMoneyRectangle());
    }

    /**
     * Updates the logic of the LibraryCard, to be invoked every frame.
     *
     * @param player is Player player, the player class.
     */
    public void update(Player player, Level level) {
        // Interaction between the player and the LibraryCard checked every frame.
        if (this.isCollidingWithPlayer(player) && !this.isDisposed) {
            this.pickUp(player);

            player.getEventsCounter().foundBasementKey();
            Notification notification = new Notification(
                "You have picked up the Basement Key. You can now access the basement area.",
                5f,
                NotificationType.SPEECH,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
        }
    }

    /**
     * This method is used to simulate the player picking up the LibraryCard.
     * To be invoked when the player collides with the LibraryCard.
     *
     * @param player is Player player, the player class.
     */
    private void pickUp(Player player) {
        player.getInventory().addItem(InventoryObject.BASEMENT_KEY);
        this.isDisposed = true;
    }

    /**
     * This draws the LibraryCard onto the screen.
     * To be invoked every frame, until the LibraryCard is collected by the player.
     *
     * @param batch is the SpriteBatch.
     */
    public void draw(SpriteBatch batch) {
        // i.e. if the texture has been disposed with don't try to draw it.
        if (!this.isDisposed) {
            this.sprite.draw(batch);
        }
    }

}
