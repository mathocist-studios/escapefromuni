package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.Friend;
import com.mathochiststudios.escapefromuni.entities.Player;

import java.util.ArrayList;

public class LakeLevel extends Level {

    Friend friend;

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

        friend = new Friend(
            this.getGame(),
            new Texture("prototype_character.png"),
            1,
            20,
            EnemyAI.FRIEND
        );
        levelEnemies.add(friend);

    }

    @Override
    public void update(float deltaTime, Player player) {
        this.friend.update(deltaTime, this, player);
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
