package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public abstract class InteractableEntity {

    private Texture texture;
    private float entityX;
    private float entityY;
    private float entityWidth;
    private float entityHeight;
    private float interactionRadius;
    private Rectangle entityCollision;
    private Sprite entitySprite;

    protected Game game;

    public InteractableEntity(Game game, Texture texture, float x, float y, float width, float height, float interactionRadius) {

        this.game = game;

        this.entityX = x;
        this.texture = texture;
        this.entityY = y;
        this.entityWidth = width;
        this.entityHeight = height;
        this.interactionRadius = interactionRadius;
        this.entityCollision = new Rectangle(entityX, entityY, entityWidth, entityHeight);

        this.entitySprite = new Sprite(texture);

    }

    public abstract void onInteract(Player p, Level level);

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

}
