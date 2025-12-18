package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

/**
 * BirdSeed is an interactable entity that can be placed down to attract certain enemies.
 * <br>
 * Currently, it is used to attract ducks in the Lake level.
 * <br>
 * There is no real reason why its an interactable entity since the player cannot interact with it after placing it down.
 * im just slightly lazy at this point in development.
 */
public class BirdSeed extends InteractableEntity {

    public BirdSeed(Game game, float x, float y, float interactionRadius) {
        super(game, new Texture("birdseed.png"), x, y, 1, 1, interactionRadius, false);
    }

    @Override
    public void onInteract(Player p, Level level) {

    }

    @Override
    public void withinInteractionRadius(Player p, Level level, SpriteBatch batch) {

    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), getEntityX(), getEntityY(), getEntityWidth(), getEntityHeight());
    }

}
