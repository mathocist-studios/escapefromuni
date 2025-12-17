package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.collectibles.BasementKey;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.VendingMachine;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.Receptionist;
import com.mathochiststudios.escapefromuni.levels.LevelDoors.LibraryBasementDoor;
import com.mathochiststudios.escapefromuni.levels.LevelDoors.LibraryDoor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * The LibraryFloor0 class represents the ground floor of the Library level, floor 0.
 */
public class R04_LibraryFloor0 extends Level{

    // Receptionist entity created.
    private final Texture receptionistTexture = new Texture("entities/receptionist.png");
    private final Receptionist receptionist = new Receptionist(receptionistTexture, 35, 24, 2f, 1, 1, 34, 36);

    // Door constructed, to block the path of the player.
    private final LibraryDoor libraryDoor = new LibraryDoor();
    private final LibraryBasementDoor libraryBasementDoor = new LibraryBasementDoor();

    private final BasementKey basementKey = new BasementKey();

    private final VendingMachine vendingMachine;

    /**
     * Constructs a new LibraryFloor0 with its name (path), in addition to start and end coordinates.
     */
    public R04_LibraryFloor0(Game game) {
        super(game);

        // Name of the level.
        this.mapName = "maps/libraryfloor0.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();
        levelInteractableEntities = new ArrayList<>();

        // Tile that the player spawns at when first entering the level, or tile that takes player to previous level.
        this.startX = 3;
        this.startY = 23;

        // Tile that takes player to next level, or starting tile if the player re-enters the level.
        this.endX = 4;
        this.endY = 3;

        // Tile that the player is moved to when entering the side level
        this.sideX = 22;
        this.sideY = 6;

        vendingMachine = new VendingMachine(game, 18, 24, 2f);
        this.levelInteractableEntities.add(vendingMachine);

    }

    // To be invoked in Game to update the entities on this level, when it is the active level.
    public void update(float deltaTime, Player player) {
        // Updates the position and logic of the receptionist.
        this.receptionist.update(deltaTime);
        this.libraryDoor.update(player, this);
        this.libraryBasementDoor.update(player, this);
        this.receptionist.update(deltaTime, player, this);

        this.basementKey.update(player);
    }

    // To be invoked in Game to draw the entities on this level, when it is the active level.
    public void draw(SpriteBatch batch) {
        // Draws the receptionist.
        this.receptionist.draw(batch);
        this.basementKey.draw(batch);
    }

    // To be invoked in Game to check collision between the player sprite and the entities on this level.
    public boolean collides(Player player) {
//        Rectangle receptionistRectangle = new Rectangle(receptionist.getSprite().getX(), receptionist.getSprite().getY(), 1,1);
//        return receptionistRectangle.overlaps(player.getMoneyRectangle());
        return false;
    }

    @Override
    public void onEnter(TiledMapTileLayer mapCollisionLayer, ArrayList<Rectangle> mapCollisions, Player p) {

    }

}
