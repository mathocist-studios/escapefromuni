package com.mathochiststudios.escapefromuni.Levels;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.Entities.InteractableEntity.ShopEntity;
import com.mathochiststudios.escapefromuni.Entities.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ShopLevel extends Level {

    ShopEntity shopEntity;

    public ShopLevel(Game game) {
        super(game);

        mapName = "ShopLevel.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();
        levelInteractableEntities = new ArrayList<>();

        startX = 28;
        startY = 15;

        endX = 16;
        endY = 3;

        sideX = 15;
        sideY = 2;

        shopEntity = new ShopEntity(game, 27, 27, 3.0f);
        levelInteractableEntities.add(shopEntity);

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
