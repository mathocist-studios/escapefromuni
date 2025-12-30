package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

/**
 * Represents the Long Boi entity that the player can interact with to obtain a Long Boi Pet at the long boi altar.
 */
public class LBEntity extends InteractableEntity {

    private final Texture hintTexture;

    public LBEntity(Game game, float x, float y, float interactionRadius) {
        super(game, new Texture("blank.png"), x, y, 1, 1, interactionRadius, false);

        this.hintTexture = new Texture("E_key.png");
    }

    @Override
    public void update(float deltaTime, Level level) {

    }

    @Override
    public void onInteract(Player p, Level level) {
        if (p.getEventsCounter().getHasLongBoiPet()) {
            return;
        }

        p.getEventsCounter().hasLongBoiPet();
        p.setStartTimeLongBoiPet(System.currentTimeMillis());
        p.spendCoins(1);
        Notification notification = new Notification(
            "You feel the room shake! Long boi has accepted your offering! (-1 Coin) (+1 Long Boi Pet)",
            5,
            NotificationType.SPEECH,
            game.getTextureManager().getGameSmallFont()
        );
        game.getHud().getNotificationManager().addNotification(notification);
        p.setHappiness(100); // Set happiness to max when getting Long Boi Pet
    }

    @Override
    public void withinInteractionRadius(Player p, Level level, SpriteBatch batch) {
        if (p.getEventsCounter().getHasLongBoiPet()) {
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

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), getEntityX(), getEntityY(), getEntityWidth(), getEntityHeight());
    }
}
