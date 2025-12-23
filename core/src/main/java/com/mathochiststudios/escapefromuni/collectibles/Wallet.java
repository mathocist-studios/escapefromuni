package com.mathochiststudios.escapefromuni.collectibles;

import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests.HandInWalletSideQuest;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

/**
 * Class Wallet represents the wallet, it has collision, texture, sprite, a list of spawns.
 * It will be instantiated in LibraryFloor2 and its spawns are therefore hard-coded.
 */
public class Wallet {

    Texture texture = new Texture("wallet.png");
    Sprite sprite = new Sprite(texture);
    Rectangle rectangle;
    int[] spawn = {38, 12};
    boolean isDisposed;

    public Wallet() {
        // logic for assigning spawn randomly and creating rectangle
        this.isDisposed = false;
        this.sprite.setSize(1, 1);
        // Creates the rectangle for the Wallet around its spawn with the size of the sprite.
        // spawn.get(0) being the x-coordinate and spawn.get(y) being the y-coordinate.
        this.rectangle = new Rectangle(this.spawn[0], this.spawn[1], 1, 1);
        // Setting the location of the wallet.
        this.sprite.setX(this.spawn[0]);
        this.sprite.setY(this.spawn[1]);
    }

    // Now need to set up the interaction between the player and the Wallet.
    public boolean isCollidingWithPlayer(Player player) {
        return this.rectangle.overlaps(player.getMoneyRectangle());
    }

    /**
     * Updates the logic of the Wallet, to be invoked every frame.
     *
     * @param player is Player player, the player class.
     */
    public void update(Player player, Level level) {
        // Interaction between the player and the Wallet checked every frame.
        if (this.isCollidingWithPlayer(player) && !this.isDisposed) {
            this.pickUp(player);
            Notification notification = new Notification(
                "You found someones wallet!, hand it into the receptionist",
                5,
                NotificationType.SPEECH,
                level.getGame().getTextureManager().getGameSmallFont()
            );
            level.getGame().getHud().getNotificationManager().addNotification(notification);
            level.getGame().getHud().getQuestSystem().addSideQuest(
                new HandInWalletSideQuest()
            );
        }
    }

    /**
     * This method is used to simulate the player picking up the Wallet.
     * To be invoked when the player collides with the Wallet.
     *
     * @param player is Player player, the player class.
     */
    private void pickUp(Player player) {
        player.getInventory().addItem(InventoryObject.WALLET);
        this.isDisposed = true;
    }

    /**
     * This draws the Wallet onto the screen.
     * To be invoked every frame, until the Wallet is collected by the player.
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
