package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.collectibles.LibraryCard;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;

import java.util.ArrayList;

public class BasementLevel extends Level {

    // Instantiate the LibraryCard.
    LibraryCard libraryCard = new LibraryCard();

    public BasementLevel(Game game) {
        super(game);

        mapName = "maps/BasementLib.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();

        startX = 28;
        startY = 15;

        endX = 2;
        endY = 3;

        sideX = 1;
        sideY = 15;

        side2X = 33;
        side2Y = 2;

        levelSpeedPowerups.add(
            new SpeedPowerup(
                this.getGame().getTextureManager().getPlanetTexture(),
                this.getGame().getTextureManager().getPlanetSound(),
                30,
                3,
                1.5f,
                300.0f
            )
        );

        // levelCoins = Level.generateLevelCoins(38, 26); // Needs even int pairs
        levelCoins = super.generateLevelCoins(new int[][]{{38, 26}}); // this is just better

    }

    @Override
    public void update(float deltaTime, Player player) {
        this.libraryCard.update(player, this);
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.libraryCard.draw(batch);
    }

    @Override
    public boolean collides(Player player) {
        return false;
    }

}
