package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public class ShopEntity extends InteractableEntity {

    private final Texture hintTexture;

    public ShopEntity(Game game, float x, float y, float interactionRadius) {
        super(game, new Texture("blank.png"), x, y, 1, 1, interactionRadius);

        this.hintTexture = new Texture("E_key.png");
    }

    @Override
    public void onInteract(Player p, Level level) {
        game.shopActive = !game.shopActive;
    }

    @Override
    public void withinInteractionRadius(Player p, Level level, SpriteBatch batch) {
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
