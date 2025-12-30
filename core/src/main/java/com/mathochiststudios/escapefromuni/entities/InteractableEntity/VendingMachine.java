package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.Level;

/**
 * A vending machine that sells rollerblades to the player for 4 coins.
 * Once purchased, the player gains a speed boost.
 */
public class VendingMachine extends InteractableEntity {

    private float notificationCooldown = 0.0f;
    private final Texture hintTexture;

    public VendingMachine(Game game, float x, float y, float interactionRadius) {
        super(game, new Texture("vending_machine.png"), x, y, 2, 4, interactionRadius, false);

        this.hintTexture = new Texture("E_key.png");
    }

    private void sendNotification(String message) {

        if (System.currentTimeMillis() - notificationCooldown < 5000) {
            return;
        }

        notificationCooldown = System.currentTimeMillis();

        Notification notification = new Notification(
            message,
            5.0f,
            NotificationType.SPEECH,
            game.getTextureManager().getGameSmallFont()
        );
        game.getHud().getNotificationManager().addNotification(notification);
    }

    @Override
    public void update(float deltaTime, Level level) {

    }

    @Override
    public void onInteract(Player p, Level level) {
        if (p.getInventory().hasItem(InventoryObject.ROLLERBLADES)) {
            sendNotification("Vending machine is empty!");
            return;
        }
        if (p.getCoins() < 4) {
            sendNotification("You need 4 coins to use the vending machine.");
            return;
        }

        p.getInventory().addItem(InventoryObject.ROLLERBLADES);
        p.spendCoins(4);
        sendNotification("You bought rollerblades! You can now move faster.");
        p.addSpeed((float) (5 * level.getGame().getGameDifficulty().getSpeedBuffMultiplier()));
        Notification notification = new Notification(
            "Buy rollerblades",
            5,
            NotificationType.ACHIEVEMENT,
            game.getTextureManager().getGameSmallFont()
        );
        game.getHud().getNotificationManager().addNotification(notification);
        p.getEventsCounter().boughtRollerSkates();
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), getEntityX(), getEntityY(), getEntityWidth(), getEntityHeight());
    }

    @Override
    public void withinInteractionRadius(Player p, Level level, SpriteBatch batch) {
        if (p.getInventory().hasItem(InventoryObject.ROLLERBLADES)) {
            return;
        }

        // No special behavior when within interaction radius
        batch.draw(
            hintTexture,
            getEntityX() + (getEntityWidth() - 1) / 2,
            (float) (getEntityY() + 0.5 * Math.sin(System.currentTimeMillis() / 200.0)),
            1,
            1
        );

    }

}
