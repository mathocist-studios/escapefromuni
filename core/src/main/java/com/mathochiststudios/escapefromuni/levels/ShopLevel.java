package com.mathochiststudios.escapefromuni.levels;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ShopLevel extends Level{

    public ShopLevel(Game game) {
        super(game);

        mapName = "ShopLevel.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();

        startX = 28;
        startY = 15;

        endX = 16;
        endY = 3;

        sideX = 6;
        sideY = 20;

    }

    // These are redundant as there are no entities on shopLevel

    @Override
    public void update(float deltaTime, Player player) {

    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public boolean collides(Player player) {
        return false;
    }
}
