package com.mathochiststudios.escapefromuni.levels;

import com.mathochiststudios.escapefromuni.collectibles.LibraryCard;
import com.mathochiststudios.escapefromuni.collectibles.Wallet;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;

import java.util.ArrayList;

/**
 * The LibraryFloor3 class represents the third floor of the Library level, floor 3.
 */
public class R01_LibraryFloor3 extends Level {

    // Instantiate the LibraryCard.
    LibraryCard libraryCard = new LibraryCard();

    Wallet wallet = new Wallet();

    /**
     * Constructs a new LibraryFloor3 with its name (path in assets), in addition to start and end coordinates.
     */
    public R01_LibraryFloor3(Game game) {
        super(game);

        // Name of the level.
        mapName = "maps/libraryfloor3.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();

        // wtf is this
        // levelCoins = Level.generateLevelCoins(14, 20, 14, 8); // Needs even int pairs
        levelCoins = super.generateLevelCoins(new int[][]{{14, 20}, {14, 8}}); // this is just better

        levelSpeedPowerups.add(
            new SpeedPowerup(
                this.getGame().getTextureManager().getPlanetTexture(),
                this.getGame().getTextureManager().getPlanetSound(),
                25,
                15,
                1.5f,
                300.0f
            )
        );
        //levelEnemies.add(new Enemy(Game.duckTexture, Game.duckSound, 14, 12, "Duck"));

        // Tile that the player spawns at when first entering the level.
        startX = 38;
        startY = 25;

        // Tile that takes player to next level, or starting tile if the player re-enters the level.
        endX = 3;
        endY = 3;
    }

    public void update(float deltaTime, Player player) {
        this.libraryCard.update(player);
        this.wallet.update(player);
    }
    public void draw(SpriteBatch batch) {
        this.libraryCard.draw(batch);
        this.wallet.draw(batch);
    }
    public boolean collides(Player player) {
        return false;
    }
}
