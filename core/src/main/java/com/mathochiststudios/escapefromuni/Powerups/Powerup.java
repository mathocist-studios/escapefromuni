package com.mathochiststudios.escapefromuni.Powerups;

import com.mathochiststudios.escapefromuni.Entities.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Sound;

public interface Powerup {


    public boolean isPowerUpAdded();

    public void setPowerUpAdded(boolean powerUpAdded);

    public void apply(Player player); //called when used


    void remove(Player player); //called when the power up ends (if we want it timed)


    boolean isTemp();

    boolean update(Player player, float deltaTime);

    public void render(SpriteBatch batch);

    public void collect();

    public Boolean isCollected();

    // Removes the item texture
    public void deleteItem();

    public Rectangle getCollider();

    public Sound getSoundEffect();

}
