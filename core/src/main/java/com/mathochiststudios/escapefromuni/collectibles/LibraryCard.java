package com.mathochiststudios.escapefromuni.collectibles;

import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Class LibraryCard represents the library card, it has collision, texture, sprite, a list of spawns.
 * It will be instantiated in LibraryFloor1 and its spawns are therefore hard-coded.
 */
public class LibraryCard {

    private Texture texture = new Texture("pixelartkeycard.png");
    private Sprite sprite = new Sprite(texture);
    private int[][] spawnArray = {
            {10, 5},
            {22, 8},
            {30, 15},
            {5, 20},
            {18, 25}
    };
    private Rectangle rectangle;
    private int[] spawn;
    private boolean isDisposed;

    public LibraryCard() {
        // logic for assigning spawn randomly and creating rectangle
        this.isDisposed = false;
        this.assignSpawn();
        this.sprite.setSize(1, 1);
        // Creates the rectangle for the LibraryCard around its spawn with the size of the sprite.
        // spawn.get(0) being the x-coordinate and spawn.get(y) being the y-coordinate.
        this.rectangle = new Rectangle(this.spawn[0], this.spawn[1], 1, 1);
        // Setting the location of the library card.
        this.sprite.setX(this.spawn[0]);
        this.sprite.setY(this.spawn[1]);
    }

    /**
     * This method randomly assigns the spawn for the LibraryCard.
     */
    private void assignSpawn() {
        Random random = new Random();
        int randomIndex = random.nextInt(this.spawnArray.length);
        this.spawn = this.spawnArray[randomIndex];
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
    public void update(Player player) {
        // Interaction between the player and the LibraryCard checked every frame.
        if (this.isCollidingWithPlayer(player)) {
            this.pickUp(player);
        }
    }

    /**
     * This method is used to simulate the player picking up the LibraryCard.
     * To be invoked when the player collides with the LibraryCard.
     *
     * @param player is Player player, the player class.
     */
    private void pickUp(Player player) {
        player.setHasLibraryCard(true);
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
