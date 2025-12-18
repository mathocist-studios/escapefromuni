package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

/**
 * Abstract class representing an interactable entity in the game.
 * <br>
 * Originally created to represent vending machines, but expanded to include other interactable entities.
 * <br><br>
 * Since the previous developers created such specific classes for specific use-cases, Towards the end of development
 * I ended up using this class as a general static entity class for anything that the
 * player can interact with. (e.g. Shops, Bird Seed, etc.)
 */
public abstract class InteractableEntity {

    private final Texture texture;
    private float entityX;
    private float entityY;
    private final float entityWidth;
    private final float entityHeight;
    private final float interactionRadius;
    private final Rectangle entityCollision;
    private final Sprite entitySprite;
    private final boolean isAbovePlayer;

    protected Game game;

    public InteractableEntity(Game game, Texture texture, float x, float y, float width, float height, float interactionRadius, boolean isAbovePlayer) {

        this.game = game;

        this.entityX = x;
        this.texture = texture;
        this.entityY = y;
        this.entityWidth = width;
        this.entityHeight = height;
        this.interactionRadius = interactionRadius;
        this.isAbovePlayer = isAbovePlayer;
        this.entityCollision = new Rectangle(entityX, entityY, entityWidth, entityHeight);

        this.entitySprite = new Sprite(texture);

    }

    public abstract void onInteract(Player p, Level level);

    public abstract void withinInteractionRadius(Player p, Level level, SpriteBatch batch);

    public abstract void render(SpriteBatch batch);

    public Texture getTexture() {
        return texture;
    }

    public float getEntityX() {
        return entityX;
    }

    public float getEntityY() {
        return entityY;
    }

    public float getEntityWidth() {
        return entityWidth;
    }

    public float getInteractionRadius() {
        return interactionRadius;
    }

    public float getEntityHeight() {
        return entityHeight;
    }

    public Rectangle getEntityCollision() {
        return entityCollision;
    }

    public Game getGame() {
        return game;
    }

    public Sprite getEntitySprite() {
        return entitySprite;
    }

    public boolean isAbovePlayer() {
        return isAbovePlayer;
    }

    public void setEntityX(float x) {
        this.entityX = x;
        this.entityCollision.setX(x);
    }

    public void setEntityY(float y) {
        this.entityY = y;
        this.entityCollision.setY(y);
    }

}
