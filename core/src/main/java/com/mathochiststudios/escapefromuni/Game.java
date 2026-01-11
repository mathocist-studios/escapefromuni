package com.mathochiststudios.escapefromuni;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.*;
import com.mathochiststudios.escapefromuni.UI.HUD;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.UI.QuestSystem.Quests.EscapeUniQuest;
import com.mathochiststudios.escapefromuni.collectibles.Collectible;
import com.mathochiststudios.escapefromuni.entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.InteractableEntity;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.levels.BasementLevel;
import com.mathochiststudios.escapefromuni.levels.BusLevel;
import com.mathochiststudios.escapefromuni.levels.LBShrineLevel;
import com.mathochiststudios.escapefromuni.levels.LakeLevel;
import com.mathochiststudios.escapefromuni.levels.Level;
import com.mathochiststudios.escapefromuni.levels.R01_LibraryFloor3;
import com.mathochiststudios.escapefromuni.levels.R04_LibraryFloor0;
import com.mathochiststudios.escapefromuni.levels.R05_MarketSquare;
import com.mathochiststudios.escapefromuni.levels.ShopLevel;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;

public class Game {

    public boolean gameEnded;
    public String WinOrLose;

    private final float root2 = 1.41f; // i like this

    private final float minimapTileSize = 1.4f;

    private final Player player;

    private final ArrayList<Level> levels;

    TiledMap map; // define map
    ITiledMapRenderer mapRenderer; // define map renderer
    FitViewport viewport;
    OrthographicCamera camera;
    ISpriteBatch spriteBatch;

    public static Level currentLevel;

    float unitScale;

    TiledMapTileLayer mapCollisionLayer;
    public ArrayList<Rectangle> mapCollisions;

    TiledMapTileLayer mapExitBackLayer;
    ArrayList<Rectangle> mapExitBackCollisions;

    TiledMapTileLayer mapExitForwardLayer;
    ArrayList<Rectangle> mapExitForwardCollisions;

    TiledMapTileLayer mapExitSideLayer;
    ArrayList<Rectangle> mapExitSideCollisions;

    TiledMapTileLayer mapExitSide2Layer;
    ArrayList<Rectangle> mapExitSide2Collisions;

    TiledMapTileLayer mapSlowLayer;
    ArrayList<Rectangle> mapSlowCollisions;

    float stateTime;

    public boolean shopActive;
    public boolean friendFollowing = false;
    public Level friendLocation;

    private final HUD hud;
    private final TextureManager textureManager;
    private GameDifficulty gameDifficulty;

    private final Main mainApp;

    // Runs at start
    public Game(Main mainApp, GameDifficulty gameDifficulty) {

        this.mainApp = mainApp;
        this.gameDifficulty = gameDifficulty;

        player = new Player(this, (float) gameDifficulty.getBaseMovementSpeed());

        hud = new HUD(this, player);
        hud.getQuestSystem().addMainQuest(
                new EscapeUniQuest());

        textureManager = new TextureManager(hud.getUiViewport());

        WinOrLose = "Return"; // Should be "Return"
        gameEnded = false;
        player.setGameTimer(new Timer(5 * 60));

        // IMPORTANT: This is the list of levels, the player can traverse back and forth
        // in this order.
        // Add appropriate exits forward and/or backward in the tilemap on their
        // individual layers.
        // levels = new ArrayList<Level>(Arrays.asList(new R01_LibraryFloor3(), new
        // R02_LibraryFloor2(), new R03_LibraryFloor1(), new R04_LibraryFloor0(), new
        // R05_MarketSquare(), new R06_westToEastLevel(), new R07_BusLevel()));

        // remove library floor 1 and 2 to shorten game
        levels = new ArrayList<>(Arrays.asList(
                new R01_LibraryFloor3(this),
                new R04_LibraryFloor0(this),
                new R05_MarketSquare(this),
                new BusLevel(this)));

        // This sets the next and previous level attributes of the room objects for ease
        // of use
        for (int i = 0; i < levels.size(); i++) {
            levels.get(i).setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
            levels.get(i).getMinimapSprite().setX(38f);
            levels.get(i).getMinimapSprite().setSize(minimapTileSize - 0.1f, minimapTileSize - 0.1f);
            if (i - 1 >= 0) {
                levels.get(i).setPrevLevel(levels.get(i - 1));
            }
            if (i + 1 < levels.size()) {
                levels.get(i).setNextLevel(levels.get(i + 1));
            }
        }

        // Set up misc. side level stuff here

        // Jacob: Currently set up for ShopLevel
        Level ShopLevel = new ShopLevel(this);
        levels.get(2).setSideLevel(ShopLevel);
        ShopLevel.setSideLevel(levels.get(2));
        // generate minimap for side level
        ShopLevel.setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
        ShopLevel.getMinimapSprite().setX(38f - minimapTileSize);
        ShopLevel.getMinimapSprite().setSize(minimapTileSize - 0.1f, minimapTileSize - 0.1f);
        ShopLevel.setNextLevel(levels.get(2));

        // Setup lake level
        Level LakeLevel = new LakeLevel(this);
        levels.get(2).setSide2Level(LakeLevel);
        LakeLevel.setSideLevel(levels.get(2));
        LakeLevel.setPrevLevel(levels.get(2));
        // generate minimap for side level
        LakeLevel.setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
        LakeLevel.getMinimapSprite().setX(38f - 2 * minimapTileSize);
        LakeLevel.getMinimapSprite().setSize(minimapTileSize - 0.1f, minimapTileSize - 0.1f);
        LakeLevel.setNextLevel(levels.get(2));
        friendLocation = LakeLevel;

        // setup basement level
        Level BasementLevel = new BasementLevel(this);
        levels.get(1).setSideLevel(BasementLevel);
        BasementLevel.setSideLevel(levels.get(1));
        BasementLevel.setPrevLevel(levels.get(1));
        // generate minimap for side level
        BasementLevel.setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
        BasementLevel.getMinimapSprite().setX(38f - minimapTileSize);
        BasementLevel.getMinimapSprite().setSize(minimapTileSize - 0.1f, minimapTileSize - 0.1f);
        BasementLevel.setNextLevel(levels.get(1));

        // setup LBShrine level
        Level lbShrineLevel = new LBShrineLevel(this);
        levels.get(1).setSide2Level(lbShrineLevel);
        lbShrineLevel.setSide2Level(levels.get(1));
        lbShrineLevel.setPrevLevel(levels.get(1));
        // generate minimap for side level
        lbShrineLevel.setMinimapSprite(new Sprite(hud.getEmptyMinimapIcon()));
        lbShrineLevel.getMinimapSprite().setX(38f - 2 * minimapTileSize);
        lbShrineLevel.getMinimapSprite().setSize(minimapTileSize - 0.1f, minimapTileSize - 0.1f);
        lbShrineLevel.setNextLevel(levels.get(1));

        // The player always starts at the first level in the array.
        currentLevel = levels.get(0);

        viewport = new FitViewport(40, 30);
        // Starting level is floor 0 of the library (a tmx).
        map = new TmxMapLoader().load("maps/libraryfloor0.tmx");
        unitScale = 1 / 16f;

        try {
            mapRenderer = new LiveTiledMapRenderer(map, unitScale);
        } catch (IllegalArgumentException e) {
            mapRenderer = new HeadlessTiledMapRenderer();
        }

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 40, 30);
        mapRenderer.setView(camera);

        if (Main.TESTING) {
            spriteBatch = new HeadlessBatch();
        } else {
            spriteBatch = new LiveSpriteBatch();
        }

        switchToLevel(currentLevel, "Forward");

        stateTime = 0f;

        Notification welcomeNotification = new Notification(
                "Ohh.. I think I fell asleep in the library again...\nI better get going before it closes!",
                5,
                NotificationType.SPEECH,
                textureManager.getGameSmallFont());
        hud.getNotificationManager().addNotification(welcomeNotification);

    }

    public Player getPlayer() {
        return this.player;
    }

    private ArrayList<String> mapLayersToList(MapLayers mapLayers) {
        ArrayList<String> tempList = new ArrayList<>();
        for (int i = 0; i < mapLayers.size(); i++) {
            tempList.add(mapLayers.get(i).getName());
        }
        return tempList;
    }

    // Switches to a new map and moves the player appropriately when entering a new
    // room.
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

        viewport.setWorldSize(mapCollisionLayer.getWidth(), mapCollisionLayer.getHeight());

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

        // check for ExitSide
        if (mapLayersToList(map.getLayers()).contains("ExitSide")) {
            mapExitSideLayer = (TiledMapTileLayer) map.getLayers().get("ExitSide");
            mapExitSideCollisions = createCollisionRects(mapExitSideLayer);
        } else {
            mapExitSideLayer = null;
            mapExitSideCollisions = new ArrayList<>();
        }

        // check for ExitSide2
        if (mapLayersToList(map.getLayers()).contains("ExitSide2")) {
            mapExitSide2Layer = (TiledMapTileLayer) map.getLayers().get("ExitSide2");
            mapExitSide2Collisions = createCollisionRects(mapExitSide2Layer);
        } else {
            mapExitSide2Layer = null;
            mapExitSide2Collisions = new ArrayList<>();
        }

        if (mapLayersToList(map.getLayers()).contains("Slow")) {
            mapSlowLayer = (TiledMapTileLayer) map.getLayers().get("Slow");
            mapSlowCollisions = createCollisionRects(mapSlowLayer);
        } else {
            mapSlowLayer = null;
            mapSlowCollisions = new ArrayList<>();
        }

        // mapCollisions will be used to collide, update when switching map.
        mapCollisions = createCollisionRects(mapCollisionLayer);
        // Notify the level that it has been entered so it can access map collision data
        newLevel.onEnter(mapCollisionLayer, mapCollisions, player);
        player.getLbPet().setEntityX(player.getMoneySprite().getX() - 2);
        player.getLbPet().setEntityY(player.getMoneySprite().getY());
    }

    // Constructs an ArrayList of all collision rectangles for the layer provided
    private ArrayList<Rectangle> createCollisionRects(TiledMapTileLayer layer) {
        ArrayList<Rectangle> tempRectArray = new ArrayList<>();

        // Iterate over every tile, construct a rectangle around the tile, and add to
        // arraylist
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
        // If the window is minimized on a desktop (LWJGL3) platform, width and height
        // are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a
        // normal size before updating.
        if (width <= 0 || height <= 0)
            return;

        // Resize your application here. The parameters represent the new window size.

        // what?
    }

    // render is called in main

    public void input() {
        float delta = Gdx.graphics.getDeltaTime(); // Change in time between frames
        player.update(delta); // called to check for active powerups

        // We will use these variables to allow for consistent speed on diagonal
        // movement.
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
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            velX = -player.getSpeed() * delta;
            player.setMoveDirection("Left");
            isMoving = true;
        }
        // Use if here rather than else if, so movement can happen on both axis at once
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            velY = player.getSpeed() * delta;
            player.setMoveDirection("Up");
            isMoving = true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
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

        // If the player moves diagonally, we need to divide their speed by root 2 to
        // maintain the correct speed
        // in the direction of motion.
        if (Math.abs(velX) > 0.01f && Math.abs(velY) > 0.01f) {
            velX = velX / root2;
            velY = velY / root2;
        }

        // We will use this rectangle as a preview, to see if the player would collide
        // if they moved
        // as they are trying to.
        // If they would collide, disallow the movement in that axis.
        Rectangle tRect = new Rectangle();

        // NOTE: kind of flawed but won't be noticeable unless very high speed or very
        // low fps,
        // as it doesn't make you flush against a wall if you move into it

        tRect.set(player.getMoneySprite().getX() + velX, player.getMoneySprite().getY(), player.getMoneyWidth(),
                player.getMoneyHeight());
        if (!wallCollisionCheck(tRect) && !shopActive) {
            player.getMoneySprite().translateX(velX);
        }

        tRect.set(player.getMoneySprite().getX(), player.getMoneySprite().getY() + velY, player.getMoneyWidth(),
                player.getMoneyHeight());
        if (!wallCollisionCheck(tRect) && !shopActive) {
            player.getMoneySprite().translateY(velY);
        }

        // Update the player's collision rectangle for the trigger collision check
        player.getMoneyRectangle().x = player.getMoneySprite().getX();
        player.getMoneyRectangle().y = player.getMoneySprite().getY();

        // Check for collisions with non-walls and respond appropriately
        triggerCollisionCheck(player.getMoneyRectangle());

        for (Collectible coin : currentLevel.getLevelCoins()) {
            if (!(coin.isCollected()) && player.getMoneyRectangle().overlaps(coin.getCollider())) {
                coin.collect();
                coin.SoundEffect.play();
            }
        }

        for (SpeedPowerup powerup : currentLevel.getLevelPowerups()) {
            if (!(powerup.isCollected()) && player.getMoneyRectangle().overlaps(powerup.getCollider())) {
                powerup.collect();
                powerup.apply(player);
                powerup.getSoundEffect().play();
                player.getEventsCounter().speedPositiveEventsEncountered();

            }
        }

        // TODO: check this logic
        for (Enemy enemy : currentLevel.getLevelEnemies()) {
            if (!(enemy.isDead()) && player.getMoneyRectangle().overlaps(enemy.getCollider())) {
                // Trigger enemy behavior
                enemy.triggerCollisionBehavior(player);
                break; // Exit loop after first collision to prevent multiple reactions
            }
        }

        for (InteractableEntity entity : getInteractableInRange(
                player.getMoneySprite().getX() + player.getMoneyWidth() / 2,
                player.getMoneySprite().getY() + player.getMoneyHeight() / 2)) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
                entity.onInteract(player, currentLevel);
            }
        }

        player.getLbPet()
                .setObjectivePoint(new float[] { player.getMoneySprite().getX(), player.getMoneySprite().getY() });
        player.getLbPet().update();

    }

    private ArrayList<InteractableEntity> getInteractableInRange(float playerX, float playerY) {
        ArrayList<InteractableEntity> interactablesInRange = new ArrayList<>();

        for (InteractableEntity entity : currentLevel.getLevelInteractableEntities()) {
            float interactionRange = entity.getInteractionRadius();
            float[] entityBBox = new float[] {
                    entity.getEntityX(),
                    entity.getEntityY(),
                    entity.getEntityWidth(),
                    entity.getEntityHeight()
            };

            boolean is_in_range = distanceFromBBox(entityBBox, playerX, playerY) <= interactionRange;
            if (is_in_range) {
                interactablesInRange.add(entity);
            }
        }

        return interactablesInRange;
    }

    // USE FOR NON-WALL COLLISIONS, I.E ITEMS OR ROOM TRANSITIONS
    private void triggerCollisionCheck(Rectangle pRect) {

        for (Rectangle tileRect : mapExitForwardCollisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getNextLevel() != null) {
                    switchToLevel(currentLevel.getNextLevel(), "Forward");
                }
                break;
            }
        }

        // If on LibraryFloor3 and no ExitForward layer exists, check if player is at
        // end position
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
                    switchToLevel(currentLevel.getSideLevel(), "Side");
                }
                break;
            }
        }

        for (Rectangle tileRect : mapExitSide2Collisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getSide2Level() != null) {
                    switchToLevel(currentLevel.getSide2Level(), "Side2");
                }
                break;
            }
        }

        for (Rectangle tileRect : mapExitBackCollisions) {
            if (pRect.overlaps(tileRect)) {
                if (currentLevel.getPrevLevel() != null) {
                    switchToLevel(currentLevel.getPrevLevel(), "Back");
                }
                break;
            }
        }

        boolean isSlowed = false;
        for (Rectangle tileRect : mapSlowCollisions) {
            if (pRect.overlaps(tileRect)) {
                isSlowed = true;
                player.getEventsCounter().slowedByWater();
                player.setHappiness(player.getHappiness() - Gdx.graphics.getDeltaTime() * 2); // Decrease happiness over
                                                                                              // time in water
                break;
            }
        }
        player.setSlowedByWater(isSlowed);

    }

    // USED FOR MOVEMENT BASED WALL COLLISIONS ONLY
    // Checks the collision layer against the parameter rectangle,
    // returns TRUE if there is a collision, FALSE otherwise.
    private boolean wallCollisionCheck(Rectangle pRect) {

        // Iterate over every tile in our already made collision map and check if it
        // intersects pRect
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
        // Rectangle playerRectangle = new Rectangle(moneySprite.getX(),
        // moneySprite.getY(), 1, 1);

        float delta = Gdx.graphics.getDeltaTime();
        // Updates the level entities.
        currentLevel.update(delta, this.player);

        if (player.getTotalCoinsCollected() == 12 && !player.getEventsCounter().hasCollectedAllCoins()) {
            Notification allCoinsNotification = new Notification(
                    "Collected all coins",
                    5,
                    NotificationType.ACHIEVEMENT,
                    textureManager.getGameSmallFont());
            hud.getNotificationManager().addNotification(allCoinsNotification);
            player.getEventsCounter().collectedAllCoins();
        }

        if (player.getTotalSpeedPowerupsCollected() == 2 &&
                player.getInventory().hasItem(InventoryObject.ENERGY_DRINK) &&
                player.getInventory().hasItem(InventoryObject.YORCUP) &&
                !player.getEventsCounter().hasCollectedAllPowerUps()) {
            Notification allPowerupsNotification = new Notification(
                    "Collected all power-ups",
                    5,
                    NotificationType.ACHIEVEMENT,
                    textureManager.getGameSmallFont());
            hud.getNotificationManager().addNotification(allPowerupsNotification);
            player.getEventsCounter().collectedAllPowerUps();
        }

        if (player.getEventsCounter().hasCollectedAllPowerUps() &&
                player.getEventsCounter().hasCollectedAllCoins() &&
                player.getEventsCounter().hasMadeItToBusStop() &&
                player.getEventsCounter().getHasExitLibraryAchieved() &&
                player.getEventsCounter().getHasLongBoiPet() &&
                !player.getEventsCounter().hasCompletedGame()) {
            Notification completedGameNotification = new Notification(
                    "You 100% completed the game!",
                    5,
                    NotificationType.ACHIEVEMENT,
                    textureManager.getGameSmallFont());
            hud.getNotificationManager().addNotification(completedGameNotification);
            player.getEventsCounter().completedGame();
        }

        // Rectangle playerRect = new Rectangle(moneySprite.getX(), moneySprite.getY(),
        // 1, 1);
        // boolean collided = currentLevel.collides(player);
        // if (collided) {
        // // If we're on the BusLevel and the player has reached the bus, game
        // terminates.
        // if (currentLevel instanceof R07_BusLevel) {
        // if (currentLevel.getPrevLevel() != null) {
        // Gdx.app.exit();
        // }
        // } else {
        // // Generic collision handling for other levels
        // this.player.getGameTimer().removeTime(2F);
        // }
        // }
        // apply the bucket position and size to the bucket rectangle

    }

    public void draw(SpriteBatch masterBatch, FitViewport masterViewport) {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        mapRenderer.render();

        stateTime += Gdx.graphics.getDeltaTime() * 0.25f; // Accumulate elapsed animation time

        spriteBatch.begin();

        // Long boi pet colour effect when spawned
        if (Math.abs(player.getStartTimeLongBoiPet() - System.currentTimeMillis()) < 5000
                && player.getStartTimeLongBoiPet() > 0) {
            double timeSinceStart = (System.currentTimeMillis() - player.getStartTimeLongBoiPet()) / 1000.0;
            double colourOffset = 0.5 * Math.sin((timeSinceStart * Math.PI) / 5);
            spriteBatch.setColor(1.0f, (float) (1.0 - colourOffset), (float) (1.0 - colourOffset), 1.0f);
            mapRenderer.getBatch().setColor(1.0f, (float) (1.0 - colourOffset), (float) (1.0 - colourOffset), 1.0f);
        }

        // Draws the level entities.
        currentLevel.draw((SpriteBatch) spriteBatch);

        for (InteractableEntity entity : currentLevel.getLevelInteractableEntities()) {
            if (entity.isAbovePlayer()) {
                continue;
            }
            entity.render((SpriteBatch) spriteBatch);
        }

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
            spriteBatch.draw(currentFrame, player.getMoneySprite().getX() + player.getMoneyWidth() / 2 + 1.3f,
                    player.getMoneySprite().getY() - player.getMoneyHeight() / 2 - 0.25f, -2.5f, 2.5f);
        } else {
            spriteBatch.draw(currentFrame, player.getMoneySprite().getX() - player.getMoneyWidth() / 2 - 0.3f,
                    player.getMoneySprite().getY() - player.getMoneyHeight() / 2 - 0.25f, 2.5f, 2.5f);
        }
        // moneySprite.draw(spriteBatch); // Draw the character

        player.getLbPet().render((SpriteBatch) spriteBatch);

        for (InteractableEntity entity : currentLevel.getLevelInteractableEntities()) {
            if (!entity.isAbovePlayer()) {
                continue;
            }
            entity.render((SpriteBatch) spriteBatch);
        }

        for (Collectible coin : currentLevel.getLevelCoins()) {
            if (!(coin.isCollected())) {
                coin.render((SpriteBatch) spriteBatch);
            } else if (coin.isCollected() && coin.getCollectibleAdded()) {
            } else if (coin.isCollected()) {
                this.player.addCoins(1);
                coin.setCollectibleAdded(true);
            }
        }

        // OK this is impossible.
        for (SpeedPowerup planet : currentLevel.getLevelPowerups()) {
            if (!(planet.isCollected())) {
                planet.render((SpriteBatch) spriteBatch);
            } else if (planet.isCollected() && planet.isSpeedPowerUpAdded()) {
            } else if (planet.isCollected()) {
                planet.setSpeedPowerUpAdded(true);
            }
        }

        for (Enemy enemy : currentLevel.getLevelEnemies()) {
            if (!(enemy.isDead())) {
                enemy.render((SpriteBatch) spriteBatch);
            }
        }

        player.getInventory().render((SpriteBatch) spriteBatch, camera);

        for (InteractableEntity entity : getInteractableInRange(
                player.getMoneySprite().getX() + player.getMoneyWidth() / 2,
                player.getMoneySprite().getY() + player.getMoneyHeight() / 2)) {
            entity.withinInteractionRadius(player, currentLevel, (SpriteBatch) spriteBatch);
        }

        spriteBatch.end();

        if (shopActive) {
            textureManager.getShopUIObject().drawShopMenu(
                    masterViewport,
                    masterBatch,
                    textureManager.getShopIconSprite(),
                    textureManager.getBuyEDSprite(),
                    textureManager.getBuyBFSprite(),
                    textureManager.getShopFont(),
                    textureManager.getMainLayout());
            textureManager.getShopUIObject().inputShopMenu(
                    this,
                    masterViewport,
                    player);
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

    /**
     * Calculate the distance from a point to the nearest edge of an entity's
     * bounding box.
     * <br>
     * <br>
     * <b>CREDITS: <a href=
     * "https://stackoverflow.com/questions/5254838/calculating-distance-between-a-point-and-a-rectangular-box-nearest-point">StackOverflow</a></b>
     *
     * @param entityBBox the bounding box of the entity in tile coordinates [x, y,
     *                   width, height]
     * @param tileX1     the x coordinate of the point in tile units
     * @param tileY1     the y coordinate of the point in tile units
     * @return the distance from the point to the nearest edge of the bounding box
     */
    public static double distanceFromBBox(float[] entityBBox, float tileX1, float tileY1) {
        float rx_min = entityBBox[0];
        float rx_max = entityBBox[0] + entityBBox[2];
        float ry_min = entityBBox[1];
        float ry_max = entityBBox[1] + entityBBox[3];

        if (rx_min <= tileX1 && tileX1 <= rx_max && ry_min <= tileY1 && tileY1 <= ry_max) {
            return Math.min(Math.min(tileX1 - rx_min, rx_max - tileX1), Math.min(tileY1 - ry_min, ry_max - tileY1));
        }

        double dx = Math.max(0, Math.max(rx_min - tileX1, tileX1 - rx_max));
        double dy = Math.max(0, Math.max(ry_min - tileY1, tileY1 - ry_max));
        return Math.sqrt(dx * dx + dy * dy);
    }

    public GameDifficulty getGameDifficulty() {
        return gameDifficulty;
    }

    public Main getMainApp() {
        return mainApp;
    }

    public SpriteBatch getSpriteBatch() {
        return (SpriteBatch) spriteBatch;
    }

    public int getScore() {
        return Math.toIntExact(Math.round(
                10 * player.getTotalCoinsCollected() +
                        20 * player.getTotalSpeedPowerupsCollected() +
                        player.getHappiness() +
                        player.getGameTimer().getSecsRemaining() +
                        (gameDifficulty == GameDifficulty.EASY ? 0
                                : gameDifficulty == GameDifficulty.NORMAL ? 50
                                        : gameDifficulty == GameDifficulty.HARD ? 100
                                                : gameDifficulty == GameDifficulty.IMPOSSIBLE ? 150 : 0)
                        +
                        (player.getInventory().hasItem(InventoryObject.YORCUP) ? 50 : 0) +
                        (player.getInventory().hasItem(InventoryObject.ENERGY_DRINK) ? 50 : 0) +
                        (player.getEventsCounter().hasCompletedGame() ? 200 : 0)));
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }

    public void setGameDifficulty(GameDifficulty gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

}
