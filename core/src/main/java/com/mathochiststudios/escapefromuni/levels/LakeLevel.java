package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.Duck;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.Friend;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.BirdSeed;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.entities.Utils.Polygon;
import com.mathochiststudios.escapefromuni.levels.LevelDoors.DuckBlockerP1;
import com.mathochiststudios.escapefromuni.levels.LevelDoors.DuckBlockerP2;

import java.util.ArrayList;

public class LakeLevel extends Level {

    private final Friend friend;
    private final Duck duck1;
    private final Duck duck2;
    private final Duck duck3;

    private final DuckBlockerP1 duckBlockerP1;
    private final DuckBlockerP2 duckBlockerP2;

    private BirdSeed birdSeed;
    private final Polygon birdSeedPlaceArea = new Polygon(new float[][]{
        {20.0f, 25.0f},
        {28.0f, 25.0f},
        {28.0f, 30.0f},
        {35.0f, 30.0f},
        {35.0f, 22.0f},
        {20.0f, 22.0f}
    });
    private final Texture hintTexture;
    private boolean isPlayerInBirdSeedArea = false;

    public LakeLevel(Game game) {
        super(game);

        mapName = "maps/Lake.tmx";

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

        side2X = 33;
        side2Y = 2;

        friend = new Friend(
            this.getGame(),
            new Texture("prototype_character.png"),
            1,
            20,
            EnemyAI.FRIEND
        );
        levelEnemies.add(friend);

        duck1 = new Duck(
            this.getGame(),
            new Texture("duck_spritesheet.png"),
            25,
            27,
            EnemyAI.DUCK,
            birdSeed
        );
        levelEnemies.add(duck1);
        duck2 = new Duck(
            this.getGame(),
            new Texture("duck_spritesheet.png"),
            26,
            25,
            EnemyAI.DUCK,
            birdSeed
        );
        levelEnemies.add(duck2);
        duck3 = new Duck(
            this.getGame(),
            new Texture("duck_spritesheet.png"),
            22,
            28,
            EnemyAI.DUCK,
            birdSeed
        );
        levelEnemies.add(duck3);

        this.duckBlockerP1 = new DuckBlockerP1(birdSeed);
        this.duckBlockerP2 = new DuckBlockerP2(birdSeed);

        this.hintTexture = new Texture("E_key.png");

    }

    @Override
    public void update(float deltaTime, Player player) {
        this.friend.update(deltaTime, this, player);
        this.duck1.update(deltaTime, this, player);
        this.duck2.update(deltaTime, this, player);
        this.duck3.update(deltaTime, this, player);

        this.duckBlockerP1.update(player, this);
        this.duckBlockerP2.update(player, this);

        isPlayerInBirdSeedArea = birdSeedPlaceArea.isPointInPolygon(new float[]{player.getMoneySprite().getX(), player.getMoneySprite().getY()});

        if (Gdx.input.isKeyPressed(Input.Keys.E) && isPlayerInBirdSeedArea && birdSeed == null && player.getInventory().hasItem(InventoryObject.BIRDSEED)) {
            birdSeed = new BirdSeed(
                this.getGame(),
                player.getMoneySprite().getX(),
                player.getMoneySprite().getY(),
                1.0f
            );
            levelInteractableEntities.add(birdSeed);

            // update ducks to go after the new birdseed
            duck1.setBirdSeed(birdSeed);
            duck2.setBirdSeed(birdSeed);
            duck3.setBirdSeed(birdSeed);

            // update duck blockers to use the new birdseed
            this.duckBlockerP1.setBirdSeed(birdSeed);
            this.duckBlockerP2.setBirdSeed(birdSeed);

            player.getInventory().removeItem(InventoryObject.BIRDSEED);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {

        if (isPlayerInBirdSeedArea && birdSeed == null && getGame().getPlayer().getInventory().hasItem(InventoryObject.BIRDSEED)) {
            batch.draw(
                hintTexture,
                getGame().getPlayer().getMoneySprite().getX() + (getGame().getPlayer().getMoneySprite().getWidth() - 1) / 2,
                (float) (getGame().getPlayer().getMoneySprite().getY() + getGame().getPlayer().getMoneySprite().getHeight() + 0.5 * Math.sin(System.currentTimeMillis() / 200.0)),
                1,
                1
            );
        }

    }

    @Override
    public boolean collides(Player player) {
        return false;
    }

    @Override
    public void onEnter(TiledMapTileLayer mapCollisionLayer, ArrayList<Rectangle> mapCollisions, Player p) {

        if (!p.getEventsCounter().hasFoundFriend()) {
            return;
        }

        if (this.getGame().friendFollowing && this.getGame().friendLocation != this) {
            this.getGame().friendLocation = this;
            this.friend.setEnemyX(p.getMoneySprite().getX());
            this.friend.setEnemyY(p.getMoneySprite().getY() + 1.0f);
            this.friend.setDead(false);
            return;
        }

        this.friend.setDead(this.getGame().friendLocation != this);

    }

}
