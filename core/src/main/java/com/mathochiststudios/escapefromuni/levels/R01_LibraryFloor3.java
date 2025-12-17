package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.collectibles.Rucksack;
import com.mathochiststudios.escapefromuni.collectibles.Wallet;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;

import java.util.ArrayList;

/**
 * The LibraryFloor3 class represents the third floor of the Library level, floor 3.
 */
public class R01_LibraryFloor3 extends Level {

    private final Rucksack rucksack = new Rucksack();
    private final Wallet wallet = new Wallet();

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
        levelInteractableEntities = new ArrayList<>();

        // wtf is this
        // levelCoins = Level.generateLevelCoins(14, 20, 14, 8); // Needs even int pairs
        levelCoins = super.generateLevelCoins(new int[][]{{14, 20}, {14, 8}, {34, 7}}); // this is just better

        //levelEnemies.add(new Enemy(Game.duckTexture, Game.duckSound, 14, 12, "Duck"));

        // Tile that the player spawns at when first entering the level.
        startX = 38;
        startY = 24;

        // Tile that takes player to next level, or starting tile if the player re-enters the level.
        endX = 3;
        endY = 3;
    }

    public void update(float deltaTime, Player player) {
        this.wallet.update(player, this);
        this.rucksack.update(player);
    }

    public void draw(SpriteBatch batch) {
        this.wallet.draw(batch);
        this.rucksack.draw(batch);
    }

    public boolean collides(Player player) {
        return false;
    }

    @Override
    public void onEnter(TiledMapTileLayer mapCollisionLayer, ArrayList<Rectangle> mapCollisions, Player p) {

    }

}
