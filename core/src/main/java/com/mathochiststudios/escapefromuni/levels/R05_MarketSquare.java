package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.Friend;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.levels.LevelDoors.BusStopCheckpoint;

import java.util.ArrayList;

/**
 * Constructs a new MarketSquare04 with its name (path in assets), in addition to start and end coordinates.
 *
 * This level includes a side level called shop so it also has side coordinates
 * if you wish to add a side level such as another shop to another level just include the side coordinates and a
 * "ShopBlock" layer in the tmx file
 */
public class R05_MarketSquare extends Level{

    BusStopCheckpoint busStopCheckpoint = new BusStopCheckpoint();

    Friend friend;

    public R05_MarketSquare(Game game) {
        super(game);

        // Name of the level.
        mapName = "maps/Marketsquare.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();
        levelInteractableEntities = new ArrayList<>();

        // Tile that the player spawns at when first entering the level.
        startX = 31;
        startY = 27;

        // Tile that takes player to next level, or starting tile if the player re-enters the level.
        endX = 36;
        endY = 3;

        // Tile that the player is moved to when entering the side level
        sideX = 3;
        sideY = 3;
        // levelCoins = Level.generateLevelCoins(14, 28); // Needs even int pairs
        levelCoins = super.generateLevelCoins(new int[][]{{14, 28}, {3, 26}}); // this is just better

        friend = new Friend(
            this.getGame(),
            new Texture("prototype_character.png"),
            0,
            0,
            EnemyAI.FRIEND
        );

        levelEnemies.add(friend);
        friend.setDead(true); // Friend is not active in this level at the moment

    }
    // These are redundant as there are no entities on floor 3.
    public void update(float deltaTime, Player player) {
        busStopCheckpoint.update(player, this);
        this.friend.update(deltaTime, this, player);
    }
    public void draw(SpriteBatch batch) {}
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


