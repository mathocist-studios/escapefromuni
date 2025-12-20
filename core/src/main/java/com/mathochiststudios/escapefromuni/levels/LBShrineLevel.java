package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.LBEntity;
import com.mathochiststudios.escapefromuni.entities.Player;

import java.util.ArrayList;

public class LBShrineLevel extends Level {

    private final LBEntity lbEntity;

    public LBShrineLevel(Game game) {
        super(game);

        mapName = "maps/LBShrine.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();
        levelInteractableEntities = new ArrayList<>();

        startX = 33;
        startY = 2;

        endX = 2;
        endY = 3;

        sideX = 6;
        sideY = 20;

        side2X = 1;
        side2Y = 14;

        lbEntity = new LBEntity(game, 14, 14, 3.0f);
        levelInteractableEntities.add(lbEntity);

    }

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

    @Override
    public void onEnter(TiledMapTileLayer mapCollisionLayer, ArrayList<Rectangle> mapCollisions, Player p) {

    }

}
