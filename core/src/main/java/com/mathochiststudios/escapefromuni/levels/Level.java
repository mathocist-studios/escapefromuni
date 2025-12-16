package com.mathochiststudios.escapefromuni.levels;

import java.util.ArrayList;

import com.mathochiststudios.escapefromuni.entities.InteractableEntity.InteractableEntity;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.collectibles.Collectible;
import com.mathochiststudios.escapefromuni.entities.Enemy.Enemy;

import com.mathochiststudios.escapefromuni.Game;

public abstract class Level {

    protected ArrayList<Collectible> levelCoins;
    protected ArrayList<SpeedPowerup> levelSpeedPowerups;
    protected ArrayList<Enemy> levelEnemies;
    protected ArrayList<InteractableEntity> levelInteractableEntities;

    protected int startX = -1;
    protected int startY = -1;

    protected int sideX = -1;
    protected int sideY = -1;

    // endX and endY are used to spawn the player when they are backtracking, i.e moving back to the previous room
    protected int endX = -1;
    protected int endY = -1;

    protected int side2X = -1;
    protected int side2Y = -1;

    protected String mapName;

    protected Level nextLevel = null;
    protected Level sideLevel = null;
    protected Level side2Level = null;
    protected Level prevLevel = null;

    protected Sprite minimapIcon;

    private final Game game;

    public Level(Game game) {
        this.game = game;
    }

    public String getMapName() {
        return mapName;
    }

    // To be invoked in Game, in the logic method.
    // This will update the entities on the level.
    public abstract void update(float deltaTime, Player player);

    // To be invoked in Game, in the draw method.
    // This will re-draw the entities on the level in their updated positions.
    public abstract void draw(SpriteBatch batch);

    // To be invoked in Game, in the logic method.
    // This will check collision logic between player and entities on the level.
    // Can have general use, i.e. can be expanded to collectibles.
    public abstract boolean collides(Player player);

    public int getStartX() {
        return startX;
    }
    public int getStartY() {
        return startY;
    }

    public int getSideX() {
        return sideX;
    }
    public int getSideY() {
        return sideY;
    }

    public int getSide2X() {
        return side2X;
    }
    public int getSide2Y() {
        return side2Y;
    }

    public int getEndX() {
        return endX;
    }
    public int getEndY() {
        return endY;
    }

    public Level getNextLevel() {
        return nextLevel;
    }
    public void setNextLevel(Level nextLevel) {
        this.nextLevel = nextLevel;
    }

    public Level getSideLevel() {
        return sideLevel;
    }
    public void setSideLevel(Level sideLevel) {
        this.sideLevel = sideLevel;
    }

    public Level getSide2Level() {
        return side2Level;
    }
    public void setSide2Level(Level side2Level) {
        this.side2Level = side2Level;
    }

    public Level getPrevLevel() {
        return prevLevel;
    }
    public void setPrevLevel(Level prevLevel) {
        this.prevLevel = prevLevel;
    }

    public Sprite getMinimapSprite() {
        return minimapIcon;
    }
    public void setMinimapSprite(Sprite newSprite) {
        this.minimapIcon = newSprite;
    }

    public ArrayList<Collectible> getLevelCoins() {
        return levelCoins;
    }
    public ArrayList<SpeedPowerup> getLevelPowerups() {
        return levelSpeedPowerups;
    }
    public ArrayList<Enemy> getLevelEnemies() {
        return levelEnemies;
    }
    public ArrayList<InteractableEntity> getLevelInteractableEntities() {
        return levelInteractableEntities;
    }

    // replaced to just make it better...
    public ArrayList<Collectible> generateLevelCoins(int[][] coinCoordinates) {
        if (coinCoordinates == null || coinCoordinates.length == 0) {
            return new ArrayList<>();
        }
        if (game == null || game.getTextureManager() == null) {
            throw new IllegalStateException("Game or TextureManager is not initialized");
        }

        ArrayList<Collectible> newCoinList = new ArrayList<>(coinCoordinates.length);
        for (int i = 0; i < coinCoordinates.length; i++) {
            int[] pair = coinCoordinates[i];
            if (pair == null || pair.length != 2) {
                throw new IllegalArgumentException("Each coordinate entry must be an int[2] pair; invalid at index " + i);
            }
            int x = pair[0];
            int y = pair[1];
            newCoinList.add(new Collectible(
                game.getTextureManager().getCoinTexture(),
                game.getTextureManager().getCoinSound(),
                x, y
            ));
        }
        return newCoinList;
    }

    /**
     * Optional lifecycle hook invoked when the game switches to this level.
     * Provides the collision layer and collision rectangles for the level so
     * implementations can query map data (for example, to place collectibles).
     * Default implementation does nothing.
     */
    public abstract void onEnter(TiledMapTileLayer mapCollisionLayer, ArrayList<Rectangle> mapCollisions, Player p);

    public Game getGame() {
        return this.game;
    }

}
