package com.mathochiststudios.escapefromuni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Main;
import com.mathochiststudios.escapefromuni.entities.Enemy.Duck;
import com.mathochiststudios.escapefromuni.entities.Enemy.Enemy;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.AStarAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.DuckAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyMoveDirection;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.entities.PlayerInventory.InventoryObject;
import com.mathochiststudios.escapefromuni.entities.Utils.Polygon;
import com.mathochiststudios.escapefromuni.headless.Utils.SimulateKeyPress;
import com.mathochiststudios.escapefromuni.headless.Utils.TestingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class EnemyAITests extends AbstractHeadlessGdxTest {

    @Test
    public void testDuckAIRoaming() {

        Main app = TestingUtils.createTestGame();

        // change level to lake level with ducks
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // simulate some input and logic steps to initialize enemy positions
        app.getGame().input();
        app.getGame().logic();

        // get a duck entity from the level
        Duck duck = (Duck) app.getGame().getCurrentLevel().getLevelEnemies().get(1);
        float initialDuckX = duck.getEnemyX();
        float initialDuckY = duck.getEnemyY();

        // get duck ai behavior
        DuckAI duckAI = (DuckAI) duck.getAiBehavior();
        Polygon roamArea = duckAI.getRoamArea();
        float[] objective = duckAI.getNextPoint();

        // ensure the objective point is within the roaming area
        assertTrue(roamArea.isPointInPolygon(objective), "The duck's next objective point should be within its roaming area");

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // simulate some game logic steps to let the duck move
        for (int i = 0; i < 60; i++) { // simulate 1 second of game time
            app.getGame().input();
            app.getGame().logic();
        }

        // get new duck position
        float newDuckX = duck.getEnemyX();
        float newDuckY = duck.getEnemyY();

        // ensure duck has moved towards the objective point
        float distInitialToObjective = (float) Math.hypot(objective[0] - initialDuckX, objective[1] - initialDuckY);
        float distNewToObjective = (float) Math.hypot(objective[0] - newDuckX, objective[1] - newDuckY);
        assertTrue(distNewToObjective < distInitialToObjective, "The duck should have moved closer to its objective point");

    }

    @Test
    public void testDuckAIApproachesBirdSeed() {

        Main app = TestingUtils.createTestGame();
        Player player = app.getGame().getPlayer();

        // change level to lake level with ducks
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // simulate some input and logic steps to initialize enemy positions
        app.getGame().input();
        app.getGame().logic();

        // place birdseed down for ducks to approach
        player.getInventory().addItem(InventoryObject.BIRDSEED);

        // Move player into duck area to trigger moved ducks event
        int birdseedX = 30;
        int birdseedY = 25;

        app.getGame().getPlayer().getMoneySprite().setX(birdseedX);
        app.getGame().getPlayer().getMoneySprite().setY(birdseedY);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Mock Gdx.input so the game sees the E key as pressed during input()
        SimulateKeyPress simulateEKeyPress = TestingUtils.simulateGdxInputKeyPress(Input.Keys.E);

        // Simulate input and logic to trigger moved ducks event
        app.getGame().input();
        app.getGame().logic();

        // Restore/release the key state
        simulateEKeyPress.release();

        // get a duck entity from the level
        Duck duck = (Duck) app.getGame().getCurrentLevel().getLevelEnemies().get(1);
        float initialDuckX = duck.getEnemyX();
        float initialDuckY = duck.getEnemyY();

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // simulate some game logic steps to let the duck move
        for (int i = 0; i < 60; i++) { // simulate 1 second of game time
            app.getGame().input();
            app.getGame().logic();
        }

        // get new duck position
        float newDuckX = duck.getEnemyX();
        float newDuckY = duck.getEnemyY();

        // ensure duck has moved closer to the birdseed
        float distInitialToBirdseed = (float) Math.hypot(birdseedX - initialDuckX, birdseedY - initialDuckY);
        float distNewToBirdseed = (float) Math.hypot(birdseedX - newDuckX, birdseedY - newDuckY);
        assertTrue(distNewToBirdseed < distInitialToBirdseed, "The duck should have moved closer to the birdseed");

    }

    @Test
    public void testDuckAIStopsNearBirdSeed() {

        Main app = TestingUtils.createTestGame();
        Player player = app.getGame().getPlayer();

        // change level to lake level with ducks
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // simulate some input and logic steps to initialize enemy positions
        app.getGame().input();
        app.getGame().logic();

        // place birdseed down for ducks to approach
        player.getInventory().addItem(InventoryObject.BIRDSEED);

        // Move player into duck area to trigger moved ducks event
        int birdseedX = 30;
        int birdseedY = 25;

        app.getGame().getPlayer().getMoneySprite().setX(birdseedX);
        app.getGame().getPlayer().getMoneySprite().setY(birdseedY);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        // Mock Gdx.input so the game sees the E key as pressed during input()
        SimulateKeyPress simulateEKeyPress = TestingUtils.simulateGdxInputKeyPress(Input.Keys.E);

        // Simulate input and logic to trigger moved ducks event
        app.getGame().input();
        app.getGame().logic();

        // Restore/release the key state
        simulateEKeyPress.release();

        // get a duck entity from the level
        Duck duck = (Duck) app.getGame().getCurrentLevel().getLevelEnemies().get(1);

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // simulate some game logic steps to let the duck move
        for (int i = 0; i < 999; i++) { // simulate ~16 seconds of game time
            app.getGame().input();
            app.getGame().logic();
        }

        // get new duck position
        float newDuckX = duck.getEnemyX();
        float newDuckY = duck.getEnemyY();

        // ensure duck is within stopping distance of the birdseed
        float distToBirdseed = (float) Math.hypot(birdseedX - newDuckX, birdseedY - newDuckY);
        assertTrue(distToBirdseed <= 1.5f, "The duck should have stopped within 1.5 units of the birdseed");

    }

    @Test
    public void testFriendAIApproachesPlayer() {

        Main app = TestingUtils.createTestGame();
        Player player = app.getGame().getPlayer();

        // change level to lake level with friend
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // simulate some input and logic steps to initialize enemy positions
        app.getGame().input();
        app.getGame().logic();

        // get a friend entity from the level
        var friend = app.getGame().getCurrentLevel().getLevelEnemies().get(0);
        float initialFriendX = friend.getEnemyX();
        float initialFriendY = friend.getEnemyY();

        // move player withing a 5 unit radius of the friend to trigger approach behavior
        app.getGame().getPlayer().getMoneySprite().setX(initialFriendX + 2);
        app.getGame().getPlayer().getMoneySprite().setY(initialFriendY + 4);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // simulate some game logic steps to let the duck move
        for (int i = 0; i < 60; i++) { // simulate 1 second of game time
            app.getGame().input();
            app.getGame().logic();
        }

        // get new friend position
        float newFriendX = friend.getEnemyX();
        float newFriendY = friend.getEnemyY();

        // ensure friend has moved closer to the player
        float distInitialToPlayer = (float) Math.hypot(player.getMoneySprite().getX() - initialFriendX, player.getMoneySprite().getY() - initialFriendY);
        float distNewToPlayer = (float) Math.hypot(player.getMoneySprite().getX() - newFriendX, player.getMoneySprite().getY() - newFriendY);
        assertTrue(distNewToPlayer < distInitialToPlayer, "The friend should have moved closer to the player");

    }

    @Test
    public void testFriendAIStopsNearPlayer() {

        Main app = TestingUtils.createTestGame();
        Player player = app.getGame().getPlayer();

        // change level to lake level with friend
        app.getGame().switchToLevel(app.getGame().getLevels().get(2).getSide2Level(), "Forward");

        // simulate some input and logic steps to initialize enemy positions
        app.getGame().input();
        app.getGame().logic();

        // get a friend entity from the level
        var friend = app.getGame().getCurrentLevel().getLevelEnemies().get(0);
        float initialFriendX = friend.getEnemyX();
        float initialFriendY = friend.getEnemyY();

        // move player withing a 5 unit radius of the friend to trigger approach behavior
        app.getGame().getPlayer().getMoneySprite().setX(initialFriendX + 2);
        app.getGame().getPlayer().getMoneySprite().setY(initialFriendY + 4);
        app.getGame().getPlayer().getMoneyRectangle().x = app.getGame().getPlayer().getMoneySprite().getX();
        app.getGame().getPlayer().getMoneyRectangle().y = app.getGame().getPlayer().getMoneySprite().getY();

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // simulate some game logic steps to let the duck move
        for (int i = 0; i < 999; i++) { // simulate ~16 seconds of game time
            app.getGame().input();
            app.getGame().logic();
        }

        // get new friend position
        float newFriendX = friend.getEnemyX();
        float newFriendY = friend.getEnemyY();

        // ensure friend is within stopping distance of the player
        float distToPlayer = (float) Math.hypot(player.getMoneySprite().getX() - newFriendX, player.getMoneySprite().getY() - newFriendY);
        assertTrue(distToPlayer <= 2.0f, "The friend should have stopped within 2 units of the player");

    }

    @Test
    public void testAStarPathfinding() {

        Main app = TestingUtils.createTestGame();
        Player player = app.getGame().getPlayer();

        // mock an enemy positioned to the left of the player
        float enemyStartX = player.getMoneySprite().getX() - 5;
        float enemyStartY = player.getMoneySprite().getY();

        Enemy testEnemy = mock(Enemy.class);
        Mockito.when(testEnemy.getEnemyX()).thenReturn(enemyStartX);
        Mockito.when(testEnemy.getEnemyY()).thenReturn(enemyStartY);
        Mockito.when(testEnemy.getEnemyCollision()).thenReturn(new Rectangle(enemyStartX, enemyStartY, 1, 1));

        // test A* pathfinding update
        AStarAI aStarAI = new AStarAI();
        EnemyMoveDirection moveDirection = aStarAI.update(app.getGame(), testEnemy, 0.016f, app.getGame().getCurrentLevel(), player, 5);

        // enemy should move right towards the player
        assertSame(EnemyMoveDirection.RIGHT, moveDirection, "The enemy should move right towards the player");

    }

}
