package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;

import java.util.ArrayList;

public class LakeLevel extends Level {

    public LakeLevel(Game game) {
        super(game);

        mapName = "maps/Lake.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();
        levelInteractableEntities = new ArrayList<>();

        startX = 28;
        startY = 15;

        endX = 2;
        endY = 3;

        sideX = 6;
        sideY = 20;

        side2X = 33;
        side2Y = 2;

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
