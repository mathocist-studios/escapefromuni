package com.mathochiststudios.escapefromuni;

import com.mathochiststudios.escapefromuni.UI.HUD;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.*;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.collectibles.Collectible;
import com.mathochiststudios.escapefromuni.entities.Enemy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Game {

    public boolean gameEnded;
    public static int Score;
    public String WinOrLose;

    private final float root2 = 1.41f; // i like this

    private float minimapTileSize = 1.4f;

    private Player player = new Player();

    private ArrayList<Level> levels;

    TiledMap map; // define map
    OrthogonalTiledMapRenderer mapRenderer; // define map renderer
    FitViewport viewport;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;

    public static Level currentLevel;

    float unitScale;

    public static float money = 0;

    TiledMapTileLayer mapCollisionLayer;
    ArrayList<Rectangle> mapCollisions;

    TiledMapTileLayer mapExitBackLayer;
    ArrayList<Rectangle> mapExitBackCollisions;

    TiledMapTileLayer mapExitForwardLayer;
    ArrayList<Rectangle> mapExitForwardCollisions;

    TiledMapTileLayer mapExitSideLayer;
    ArrayList<Rectangle> mapExitSideCollisions;

    TiledMapTileLayer mapExitSide2Layer;
    ArrayList<Rectangle> mapExitSide2Collisions;

    //added for shop ui logic
    TiledMapTileLayer mapShopLayer;
    ArrayList<Rectangle> mapShopCollisions;

    float stateTime;

    public boolean shopActive;

    private HUD hud;
    private TextureManager textureManager;

    private final Main mainApp;

    // Runs at start
    public Game(Main mainApp) {

        this.mainApp = mainApp;

        hud = new HUD(this, player);
        textureManager = new TextureManager(hud.getUiViewport());

        WinOrLose = "Return"; // Should be "Return"
        gameEnded = false;
        Score = 0; // Maybe issues with old lowercase "score" which needs to be replaced
        player.setGameTimer(new Timer(5*60));

        textureManager.getBgm().setVolume(0.3f);
        textureManager.getBgm().play();

        // IMPORTANT: This is the list of levels, the player can traverse back and forth in this order.
        //            Add appropriate exits forward and/or backward in the tilemap on their individual layers.
        // levels = new ArrayList<Level>(Arrays.asList(new R01_LibraryFloor3(), new R02_LibraryFloor2(), new R03_LibraryFloor1(), new R04_LibraryFloor0(), new R05_MarketSquare(), new R06_westToEastLevel(), new R07_BusLevel()));

        // remove library floor 1 and 2 to shorten game
        levels = new ArrayList<>(Arrays.asList(
            new R01_LibraryFloor3(this),
            new R04_LibraryFloor0(this),
            new R05_MarketSquare(this),
            new R06_westToEastLevel(this),
            new R07_BusLevel(this)
        ));

        // This sets the next and previous level attributes of the room objects for ease of use
        for (int i = 0; i < levels.size(); i++){
            levels.get(i).setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
            levels.get(i).getMinimapSprite().setX(38f);
            levels.get(i).getMinimapSprite().setSize(minimapTileSize-0.1f,minimapTileSize-0.1f);
            if (i-1>=0){
                levels.get(i).setPrevLevel(levels.get(i-1));
            }
            if (i+1<levels.size()){
                levels.get(i).setNextLevel(levels.get(i+1));
            }
        }

        // Set up misc. side level stuff here

        // Jacob: Currently set up for ShopLevel
        Level ShopLevel = new ShopLevel(this);
        levels.get(2).setSideLevel(ShopLevel);
        ShopLevel.setSideLevel(levels.get(2));
        //generate minimap for side level
        ShopLevel.setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
        ShopLevel.getMinimapSprite().setX(38f-minimapTileSize);
        ShopLevel.getMinimapSprite().setSize(minimapTileSize-0.1f,minimapTileSize-0.1f);
        ShopLevel.setNextLevel(levels.get(3));

        // Setup lake level
        Level LakeLevel = new LakeLevel(this);
        levels.get(2).setSide2Level(LakeLevel);
        LakeLevel.setSideLevel(levels.get(2));
        LakeLevel.setPrevLevel(levels.get(2));
        //generate minimap for side level
        LakeLevel.setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
        LakeLevel.getMinimapSprite().setX(38f-2*minimapTileSize);
        LakeLevel.getMinimapSprite().setSize(minimapTileSize-0.1f,minimapTileSize-0.1f);
        LakeLevel.setNextLevel(levels.get(3));

        // The player always starts at the first level in the array.
        currentLevel = levels.get(0);

        viewport = new FitViewport(40, 30);
        // Starting level is floor 0 of the library (a tmx).
        map = new TmxMapLoader().load("maps/libraryfloor0.tmx");
        unitScale = 1 / 16f;
        mapRenderer = new OrthogonalTiledMapRenderer(map, unitScale);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 40, 30);
        mapRenderer.setView(camera);
        spriteBatch = new SpriteBatch();

        switchToLevel(currentLevel, "Forward");

        stateTime = 0f;

        Notification welcomeNotification = new Notification(
            "Ohh.. I think I fell asleep in the library again...\nI better get going before it closes!",
            2,
            NotificationType.SPEECH,
            textureManager.getGameSmallFont()
        );
        hud.getNotificationManager().addNotification(welcomeNotification);

    }

    public Player getPlayer(){
        return this.player;
    }

    private ArrayList<String> mapLayersToList(MapLayers mapLayers) {
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 0; i < mapLayers.size(); i++) {
            tempList.add(mapLayers.get(i).getName());
        }
        return tempList;
    }

    // Switches to a new map and moves the player appropriately when entering a new room.
    public void switchToLevel(Level newLevel, String enterDirection) {
        // Check if newLevel is null to prevent NullPointerException
        if (newLevel == null) {
            return;
        }

        // Prepare your application here.
        currentLevel = newLevel;


        newLevel.getMinimapSprite().setTexture(hud.getPlayerMinimapIcon());

        map = new TmxMapLoader().load(newLevel.getMapName());
        mapRenderer.setMap(map);

        switch (enterDirection) {
            case "Forward":
                player.getMoneySprite().setX(newLevel.getStartX());
                player.getMoneySprite().setY(newLevel.getStartY());
                if (newLevel.getPrevLevel() != null) {
                    newLevel.getPrevLevel().getMinimapSprite().setTexture(hud.getEmptyMinimapIcon());
                }
                break;
            case "Side":
                player.getMoneySprite().setX(newLevel.getSideX());
                player.getMoneySprite().setY(newLevel.getSideY());
                if (newLevel.getSideLevel() != null) {
                    newLevel.getSideLevel().getMinimapSprite().setTexture(hud.getEmptyMinimapIcon());
                }
                break;
            case "Side2":
                player.getMoneySprite().setX(newLevel.getSide2X());
                player.getMoneySprite().setY(newLevel.getSide2Y());
                if (newLevel.getSide2Level() != null) {
                    newLevel.getSide2Level().getMinimapSprite().setTexture(hud.getEmptyMinimapIcon());
                }
                break;
            case "Back":
                player.getMoneySprite().setX(newLevel.getEndX());
                player.getMoneySprite().setY(newLevel.getEndY());
                if (newLevel.getNextLevel() != null) {
                    newLevel.getNextLevel().getMinimapSprite().setTexture(hud.getEmptyMinimapIcon());
                }
                break;
        }

        // Handle both "Collision" and "collision" layer names
        if (mapLayersToList(map.getLayers()).contains("Collision")) {
            mapCollisionLayer = (TiledMapTileLayer) map.getLayers().get("Collision");
        } else if (mapLayersToList(map.getLayers()).contains("collision")) {
            mapCollisionLayer = (TiledMapTileLayer) map.getLayers().get("collision");
        } else {
            // Default to first layer if collision layer not found
            mapCollisionLayer = (TiledMapTileLayer) map.getLayers().get(0);
        }

        viewport.setWorldSize(mapCollisionLayer.getWidth(),mapCollisionLayer.getHeight());

        // Handle ExitBack layer (may not exist in all maps)
        if (mapLayersToList(map.getLayers()).contains("ExitBack")) {
            mapExitBackLayer = (TiledMapTileLayer) map.getLayers().get("ExitBack");
            mapExitBackCollisions = createCollisionRects(mapExitBackLayer);
        } else {
            mapExitBackLayer = null;
            mapExitBackCollisions = new ArrayList<>();
        }

        // Handle ExitForward layer (may not exist in all maps)
        if (mapLayersToList(map.getLayers()).contains("ExitForward")) {
            mapExitForwardLayer = (TiledMapTileLayer) map.getLayers().get("ExitForward");
            mapExitForwardCollisions = createCollisionRects(mapExitForwardLayer);
        } else {
            mapExitForwardLayer = null;
            mapExitForwardCollisions = new ArrayList<>();
        }

        //check for ExitSide
        if (mapLayersToList(map.getLayers()).contains("ExitSide")) {
            mapExitSideLayer = (TiledMapTileLayer) map.getLayers().get("ExitSide");
            mapExitSideCollisions = createCollisionRects(mapExitSideLayer);
        } else {
            mapExitSideLayer = null;
            mapExitSideCollisions = new ArrayList<>();
        }

        //check for ExitSide2
        if (mapLayersToList(map.getLayers()).contains("ExitSide2")) {
            mapExitSide2Layer = (TiledMapTileLayer) map.getLayers().get("ExitSide2");
            mapExitSide2Collisions = createCollisionRects(mapExitSide2Layer);
        } else {
            mapExitSide2Layer = null;
            mapExitSide2Collisions = new ArrayList<>();
        }

        //same logic for shopblock layer
        if (mapLayersToList(map.getLayers()).contains("ShopBlock")) {
            mapShopLayer = (TiledMapTileLayer) map.getLayers().get("ShopBlock");
            mapShopCollisions = createCollisionRects(mapShopLayer);
        } else {
            mapShopLayer = null;
            mapShopCollisions = new ArrayList<>();
        }

        // mapCollisions will be used to collide, update when switching map.
        mapCollisions = createCollisionRects(mapCollisionLayer);
        // Notify the level that it has been entered so it can access map collision data
        newLevel.onEnter(mapCollisionLayer, mapCollisions);
    }

    // Constructs an ArrayList of all collision rectangles for the layer provided
    private ArrayList<Rectangle> createCollisionRects(TiledMapTileLayer layer) {
        ArrayList<Rectangle> tempRectArray = new ArrayList<>();

        // Iterate over every tile, construct a rectangle around the tile, and add to arraylist
        for (int x = 0; x < layer.getWidth(); x++) {
            for (int y = 0; y < layer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y); // Retrieve cell at our x and y

                if (cell == null) // If nothing here, skip over it
                    continue;

                // Construct rectangle at this tile position
                Rectangle tileRect = new Rectangle(x, y, 1, 1);

                // Add this rectangle to our array
                tempRectArray.add(tileRect);
            }
        }

        return tempRectArray;
    }

    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
        hud.getUiViewport().update(width, height, true);
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.

        // what?
    }

    //render is called in main

    public void input() {
        float delta = Gdx.graphics.getDeltaTime(); // Change in time between frames
        player.update(delta); //called to check for active powerups

        // We will use these variables to allow for consistent speed on diagonal movement.
        float velX = 0f;
        float velY = 0f;

        player.setOldMoneyX(player.getMoneySprite().getX());
        player.setOldMoneyY(player.getMoneySprite().getY());

        String oldMoveDir = player.getMoveDirection();
        boolean isMoving = false;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            velX = player.getSpeed() * delta; // Convert to speed/s for consistent gameplay on different FPS
            player.setMoveDirection("Right");
            isMoving = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -player.getSpeed() * delta;
            player.setMoveDirection("Left");
            isMoving = true;
        }
        // Use if here rather than else if, so movement can happen on both axis at once
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY = player.getSpeed() * delta;
            player.setMoveDirection("Up");
            isMoving = true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            velY = -player.getSpeed() * delta;
            player.setMoveDirection("Down");
            isMoving = true;
        }
        if (!isMoving) {
            player.setMoveDirection("Stationary");
        }
        if (!Objects.equals(player.getMoveDirection(), oldMoveDir)) {
            stateTime = 0f;
        }

        // If the player moves diagonally, we need to divide their speed by root 2 to maintain the correct speed
        // in the direction of motion.
        if (Math.abs(velX) > 0.01f && Math.abs(velY) > 0.01f) {
            velX = velX / root2;
            velY = velY / root2;
        }

        // We will use this rectangle as a preview, to see if the player would collide if they moved
        // as they are trying to.
        // If they would collide, disallow the movement in that axis.
        Rectangle tRect = new Rectangle();

        // NOTE: kind of flawed but won't be noticeable unless very high speed or very low fps,
        // as it doesn't make you flush against a wall if you move into it

        tRect.set(player.getMoneySprite().getX()+velX, player.getMoneySprite().getY(), player.getMoneyWidth(),
            player.getMoneyHeight());
        if (!wallCollisionCheck(tRect)) {
            player.getMoneySprite().translateX(velX);
        }

        tRect.set(player.getMoneySprite().getX(), player.getMoneySprite().getY()+velY, player.getMoneyWidth(),
            player.getMoneyHeight());
        if (!wallCollisionCheck(tRect)) {
            player.getMoneySprite().translateY(velY);
        }

        // Update the player's collision rectangle for the trigger collision check
        player.getMoneyRectangle().x = player.getMoneySprite().getX();
        player.getMoneyRectangle().y = player.getMoneySprite().getY();

        // Check for collisions with non-walls and respond appropriately
        triggerCollisionCheck(player.getMoneyRectangle());

        Enemy.enemyCollisionLogic(player.getOldMoneyX(), player.getOldMoneyY(), this.player);

        for (Collectible coin : currentLevel.getLevelCoins()) {
            if (!(coin.isCollected()) && player.getMoneyRectangle().overlaps(coin.getCollider())) {
                coin.collect();
                money += 10;
                Score += 10;
                coin.SoundEffect.play();
            }
        }

        for (SpeedPowerup powerup : currentLevel.getLevelPowerups()) {
            if (!(powerup.isCollected()) && player.getMoneyRectangle().overlaps(powerup.getCollider())) {
                powerup.collect();
                powerup.apply(player);
                powerup.getSoundEffect().play();
            }
        }

        for (Enemy enemy : currentLevel.getLevelEnemies()) {
            if (enemy.getShowText()) {
                enemy.speechTimeCheck(delta);
            }
        }
    }

    // USE FOR NON-WALL COLLISIONS, I.E ITEMS OR ROOM TRANSITIONS
    private void triggerCollisionCheck(Rectangle pRect) {

        for (Rectangle tileRect : mapExitForwardCollisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getNextLevel() != null) {
                    switchToLevel(currentLevel.getNextLevel(),"Forward");
                }
                break;
            }
        }

        // If on LibraryFloor3 and no ExitForward layer exists, check if player is at end position
        // and automatically transition to BusLevel
        if (currentLevel.getMapName().equals("maps/libraryfloor3.tmx") && mapExitForwardCollisions.isEmpty()) {
            int endX = currentLevel.getEndX();
            int endY = currentLevel.getEndY();
            if (endX >= 0 && endY >= 0) {
                Rectangle endRect = new Rectangle(endX, endY, 1, 1);
                if (pRect.overlaps(endRect) && currentLevel.getNextLevel() != null) {
                    switchToLevel(currentLevel.getNextLevel(), "Forward");
                    return;
                }
            }
        }

        for (Rectangle tileRect : mapExitSideCollisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getSideLevel() != null) {
                    switchToLevel(currentLevel.getSideLevel(),"Side");
                }
                break;
            }
        }

        for (Rectangle tileRect : mapExitSide2Collisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getSide2Level() != null) {
                    switchToLevel(currentLevel.getSide2Level(),"Side2");
                }
                break;
            }
        }

        for (Rectangle tileRect : mapExitBackCollisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getPrevLevel() != null) {
                    switchToLevel(currentLevel.getPrevLevel(),"Back");
                }
                break;
            }
        }

        //added for Shop Ui to be detected when the player collides
        //with the player
        if (mapShopCollisions == null || mapShopCollisions.isEmpty()) {
            shopActive = false;
        } else {
            shopActive = false;
            for (Rectangle tileRect : mapShopCollisions) {
                if (pRect.overlaps(tileRect)) {
                    shopActive = true;
                    break;
                }
            }
        }

    }

    // USED FOR MOVEMENT BASED WALL COLLISIONS ONLY
    // Checks the collision layer against the parameter rectangle,
    // returns TRUE if there is a collision, FALSE otherwise.
    private boolean wallCollisionCheck(Rectangle pRect) {

        // Iterate over every tile in our already made collision map and check if it intersects pRect
        // return TRUE if there is an overlap
        for (Rectangle tileRect : mapCollisions) {
            // If this rectangle overlaps our player's then return true.
            if (pRect.overlaps(tileRect)) {
                return true;
            }
        }
        // No collision was detected, so we can return false.
        return false;
    }

    // wtf is this
    public void logic() {
        player.getGameTimer().tick(); // So that the timer counts down

        if (player.getGameTimer().hasCompleted()) {
            WinOrLose = "Lose";
            gameEnded = true;
            return;
        }

        // store the worldWidth and worldHeight as local variables for brevity
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        // clamp x to values 0 and worldWidth -- subtract the bucketWidth
        player.getMoneySprite().setX(MathUtils.clamp(player.getMoneySprite().getX(), 0,
            worldWidth - player.getMoneyWidth()));
        // clamp y vals
        player.getMoneySprite().setY(MathUtils.clamp(player.getMoneySprite().getY(), 0,
            worldHeight - player.getMoneyHeight()));

        // Player rectangle constructed for collision logic in levels.
        //Rectangle playerRectangle = new Rectangle(moneySprite.getX(), moneySprite.getY(), 1, 1);

        float delta = Gdx.graphics.getDeltaTime();
        // Updates the level entities.
        currentLevel.update(delta, this.player);
        // Collision logic for active level.

        //Rectangle playerRect = new Rectangle(moneySprite.getX(), moneySprite.getY(), 1, 1);
        boolean collided = currentLevel.collides(player);
        if (collided) {
            // If we're on the BusLevel and the player has reached the bus, game terminates.
            if (currentLevel instanceof R07_BusLevel) {
                if (currentLevel.getPrevLevel() != null) {
                    Gdx.app.exit();
                }
            } else {
                // Generic collision handling for other levels
                this.player.getGameTimer().removeTime(2F);
            }
        }
        // apply the bucket position and size to the bucket rectangle

    }

    public void draw(SpriteBatch masterBatch, FitViewport masterViewport) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        mapRenderer.render();

        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time

        spriteBatch.begin();

        // Draws the level entities.
        currentLevel.draw(spriteBatch);

        TextureRegion currentFrame;

        if (Objects.equals(player.getMoveDirection(), "Stationary")) {
            currentFrame = player.getStationaryAnimation().getKeyFrame(stateTime, true);
        } else if (Objects.equals(player.getMoveDirection(), "Down")) {
            currentFrame = player.getDownAnimation().getKeyFrame(stateTime, true);
        } else if (Objects.equals(player.getMoveDirection(), "Up")) {
            currentFrame = player.getUpAnimation().getKeyFrame(stateTime, true);
        } else {
            currentFrame = player.getRightAnimation().getKeyFrame(stateTime, true);
        }

        if (Objects.equals(player.getMoveDirection(), "Left")) {
            spriteBatch.draw(currentFrame, player.getMoneySprite().getX() + player.getMoneyWidth() / 2 + 1.3f, player.getMoneySprite().getY() - player.getMoneyHeight() / 2 - 0.25f, -2.5f, 2.5f);
        } else {
            spriteBatch.draw(currentFrame, player.getMoneySprite().getX() - player.getMoneyWidth() / 2 - 0.3f, player.getMoneySprite().getY() - player.getMoneyHeight() / 2 - 0.25f, 2.5f, 2.5f);
        }
        //moneySprite.draw(spriteBatch); // Draw the character

        for (Collectible coin : currentLevel.getLevelCoins()) {
            if (!(coin.isCollected())) {
                coin.render(spriteBatch);
            }
            else if (coin.isCollected() && coin.getCollectibleAdded()) {
            }
            else if (coin.isCollected()) {
                this.player.addCoins(1);
                coin.setCollectibleAdded(true);
            }
        }

        // OK this is impossible.
        for (SpeedPowerup planet : currentLevel.getLevelPowerups()) {
            if (!(planet.isCollected())) {
                planet.render(spriteBatch);
            }
            else if (planet.isCollected() && planet.isSpeedPowerUpAdded()) {
            }
            else if (planet.isCollected()) {
                this.player.setPositiveEventsEncountered(this.player.getPositiveEventsEncountered() + 1);
                planet.setSpeedPowerUpAdded(true);
            }
        }

        for (Enemy enemy : currentLevel.getLevelEnemies()) {
            if (!(enemy.isDead())) {
                enemy.render(spriteBatch);
            }
            if (enemy.getShowText()) {
                enemy.renderSpeech(spriteBatch);
            }
        }

        player.getInventory().render(spriteBatch);

        spriteBatch.end();

        if (shopActive) {
            textureManager.getShopUIObject().drawShopMenu(
                masterViewport,
                masterBatch,
                textureManager.getShopIconSprite(),
                textureManager.getBuyEDSprite(),
                textureManager.getBuyBFSprite(),
                textureManager.getShopFont(),
                textureManager.getMainLayout()
            );
            textureManager.getShopUIObject().inputShopMenu(
                this,
                masterViewport,
                mainApp.getButtonCD(),
                player
            );
        }

        // Draw the ui after this spritebatch as we use a separate viewport / camera
        hud.render(masterBatch);
    }

    public void pause() {
        // Invoked when your application is paused.
    }

    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    public void dispose() {
        // Destroy application's resources here.
        textureManager.dispose();
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public float getMinimapTileSize() {
        return minimapTileSize;
    }

    public HUD getHud() {
        return hud;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

}
