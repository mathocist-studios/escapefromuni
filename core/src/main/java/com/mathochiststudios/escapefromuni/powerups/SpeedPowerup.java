package com.mathochiststudios.escapefromuni.powerups;

import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Sound;

public class SpeedPowerup {
    private float speedMult;
    private float duration; //seconds
    private float timeActive;
    protected Texture texture;

    protected Sound SoundEffect;
    protected float itemX;
    protected float itemY;
    protected Rectangle itemCollision;
    protected Boolean collected = false;
    private boolean speedPowerUpAdded;

    public SpeedPowerup(Texture textureName, Sound sound, float x, float y, float speedMult, float duration) {
        this.speedMult = speedMult;
        this.duration = duration;
        this.timeActive = 0.0f;
        this.itemX = x;
        this.itemY = y;
        this.texture = textureName;
        this.SoundEffect = sound;
        this.itemCollision = new Rectangle(x, y, 1, 1);
    }

    //@Override
    public boolean isPowerUpAdded() {
        return isSpeedPowerUpAdded();
    }

    public void setSpeedPowerUpAdded(boolean speedPowerUpAdded) {
        this.speedPowerUpAdded = speedPowerUpAdded;
    }

    //if changing speed then remember it is a multiplicative change rather then linear
    //@Override
    public void apply(Player player) {
        player.addSpeed(speedMult);
        player.incrementTotalSpeedPowerupsCollected();
    }

    //@Override
    public void remove(Player player) {
        player.addSpeed(-speedMult);
    }

    //@Override
    public boolean isTemp() {
        return true;
    }

    // Call this each frame
    public boolean update(Player player, float deltaTime) {
        if (!isTemp()) return false;

        timeActive += deltaTime;
        if (timeActive >= duration) {
            remove(player);
            return true; // expired
        }
        return false;
    }

    public void render(SpriteBatch batch) {
        if (!collected) {
            batch.draw(texture, itemX, itemY, 1, 1);
        }
    }

    public void collect() {
        collected = true;
    }

    public Boolean isCollected() {
        return collected;
    }

    // Removes the item texture
    public void deleteItem() {
        texture.dispose();
    }

    public Rectangle getCollider() {
        return itemCollision;
    }

    public Sound getSoundEffect() {
        return this.SoundEffect;
    }

    // Getter for speedPowerUpAdded.
    public boolean isSpeedPowerUpAdded() {
        return this.speedPowerUpAdded;
    }
}
