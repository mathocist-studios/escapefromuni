package com.mathochiststudios.escapefromuni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.mathochiststudios.escapefromuni.Main;
import com.mathochiststudios.escapefromuni.Menus.PauseMenu;
import com.mathochiststudios.escapefromuni.TextureManager;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.headless.Utils.SimulateKeyPress;
import com.mathochiststudios.escapefromuni.headless.Utils.TestingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class PlayerTests extends AbstractHeadlessGdxTest {

    @Test
    public void testPlayerAssetExists() {
        assertTrue(Gdx.files.internal(Player.ASSET).exists(),
            "The asset for Player should be available");
    }

    @Test
    public void testPlayerMoveUp() {

        Main testGame = TestingUtils.createTestGame();
        Player player = testGame.getGame().getPlayer();

        // Mock Gdx.input so the game sees the W key as pressed during input()
        SimulateKeyPress mockInput = TestingUtils.simulateGdxInputKeyPress(Input.Keys.W);

        // Simulate input and logic steps
        testGame.getGame().input();
        testGame.getGame().logic();

        // Restore/release the key state
        mockInput.release();

        assertEquals("Up", player.getMoveDirection(), "Player should be moving up");

    }

    @Test
    public void testPlayerMoveDown() {

        Main testGame = TestingUtils.createTestGame();
        Player player = testGame.getGame().getPlayer();

        // Mock Gdx.input so the game sees the S key as pressed during input()
        SimulateKeyPress mockInput = TestingUtils.simulateGdxInputKeyPress(Input.Keys.S);

        // Simulate input and logic steps
        testGame.getGame().input();
        testGame.getGame().logic();

        // Restore/release the key state
        mockInput.release();

        assertEquals("Down", player.getMoveDirection(), "Player should be moving down");

    }

    @Test
    public void testPlayerMoveLeft() {

        Main testGame = TestingUtils.createTestGame();
        Player player = testGame.getGame().getPlayer();

        // Mock Gdx.input so the game sees the A key as pressed during input()
        SimulateKeyPress mockInput = TestingUtils.simulateGdxInputKeyPress(Input.Keys.A);

        // Simulate input and logic steps
        testGame.getGame().input();
        testGame.getGame().logic();

        // Restore/release the key state
        mockInput.release();

        assertEquals("Left", player.getMoveDirection(), "Player should be moving left");

    }

    @Test
    public void testPlayerMoveRight() {
        Main testGame = TestingUtils.createTestGame();
        Player player = testGame.getGame().getPlayer();

        // Mock Gdx.input so the game sees the D key as pressed during input()
        SimulateKeyPress mockInput = TestingUtils.simulateGdxInputKeyPress(Input.Keys.D);

        // Simulate input and logic steps
        testGame.getGame().input();
        testGame.getGame().logic();

        // Restore/release the key state
        mockInput.release();

        assertEquals("Right", player.getMoveDirection(), "Player should be moving right");
    }

    @Test
    public void testPlayerMultipleKeyPresses() {

        Main testGame = TestingUtils.createTestGame();
        Player player = testGame.getGame().getPlayer();

        // send player to the shop level to avoid collisions with walls/buildings
        testGame.getGame().switchToLevel(testGame.getGame().getLevels().get(2).getSideLevel(), "Side2");

        // Move player into free space
        testGame.getGame().getPlayer().getMoneySprite().setX(33);
        testGame.getGame().getPlayer().getMoneySprite().setY(4);
        testGame.getGame().getPlayer().getMoneyRectangle().x = testGame.getGame().getPlayer().getMoneySprite().getX();
        testGame.getGame().getPlayer().getMoneyRectangle().y = testGame.getGame().getPlayer().getMoneySprite().getY();

        float initialX = player.getMoneySprite().getX();
        float initialY = player.getMoneySprite().getY();

        // Mock Gdx.input so the game sees the W and D keys as pressed during input()
        SimulateKeyPress mockInput = TestingUtils.simulateMultiGdxInputKeyPress(Input.Keys.W, Input.Keys.D);

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // Simulate input and logic steps
        for (int i = 0; i < 60; i++) { // Simulate 1 second of game time
            testGame.getGame().input();
            testGame.getGame().logic();
        }

        // Restore/release the key states
        mockInput.release();

        float finalX = player.getMoneySprite().getX();
        float finalY = player.getMoneySprite().getY();

        assertTrue(finalX > initialX, "Player should have moved right");
        assertTrue(finalY > initialY, "Player should have moved up");

    }

    @Test
    public void testPetMovementWithPlayer() {

        Main testGame = TestingUtils.createTestGame();
        Player player = testGame.getGame().getPlayer();

        // send player to the shop level to avoid collisions with walls/buildings
        testGame.getGame().switchToLevel(testGame.getGame().getLevels().get(2).getSideLevel(), "Side2");

        // Give player the Long Boi Pet
        player.getEventsCounter().hasLongBoiPet();

        // Move player into free space
        testGame.getGame().getPlayer().getMoneySprite().setX(33);
        testGame.getGame().getPlayer().getMoneySprite().setY(4);
        testGame.getGame().getPlayer().getMoneyRectangle().x = testGame.getGame().getPlayer().getMoneySprite().getX();
        testGame.getGame().getPlayer().getMoneyRectangle().y = testGame.getGame().getPlayer().getMoneySprite().getY();

        // Position pet at player's position
        // this is done because the pet moves towards the player so starting
        // them at the same position makes it easier to test the pet movement
        // relative to the player's movement
        player.getLbPet().setEntityX(testGame.getGame().getPlayer().getMoneySprite().getX());
        player.getLbPet().setEntityY(testGame.getGame().getPlayer().getMoneySprite().getY());

        float initialPetX = player.getLbPet().getEntityX();
        float initialPetY = player.getLbPet().getEntityY();

        // Mock Gdx.input so the game sees the W key as pressed during input()
        SimulateKeyPress mockInput = TestingUtils.simulateGdxInputKeyPress(Input.Keys.W);

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // Simulate input and logic steps
        for (int i = 0; i < 60; i++) { // Simulate 1 second of game time
            testGame.getGame().input();
            testGame.getGame().logic();
        }

        // Restore/release the key state
        mockInput.release();

        float finalPetX = player.getLbPet().getEntityX();
        float finalPetY = player.getLbPet().getEntityY();

        assertEquals(initialPetX, finalPetX, 0.1f, "Pet's X position should remain approximately the same");
        assertTrue(finalPetY > initialPetY, "Pet should have moved up with the player");

    }

    @Test
    public void testGameEndAtTimeLimit() {

        Main testGame = TestingUtils.createTestGame();

        // Fast forward game time to just before the limit
        int time_remaining = testGame.getGame().getPlayer().getGameTimer().getSecsRemaining();

        assertEquals(300, time_remaining, "Initial game time should be 300 seconds");

        testGame.getGame().getPlayer().getGameTimer().removeTime(testGame.getGame().getPlayer().getGameTimer().getSecsRemaining());

        // Simulate input and logic steps to trigger game end
        testGame.getGame().input();
        testGame.getGame().logic();

        assertEquals("Lose", testGame.getGame().WinOrLose, "Game should end with a loss when time runs out");

    }

    @Test
    public void testPauseFunctionality() {

        Main testGame = TestingUtils.createTestGame();

        // Pause the game
        testGame.pause();

        int secsBefore = testGame.getGame().getPlayer().getGameTimer().getSecsRemaining();

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        PauseMenu pm = mock(PauseMenu.class);
        testGame.setPauseMenu(pm);

        TextureManager tm = mock(TextureManager.class);
        Mockito.when(tm.getBgm()).thenReturn(mock(Music.class));
        testGame.setTextureManagerClassic(tm);

        // Simulate input and logic steps
        for (int i = 0; i < 60; i++) { // Simulate 1 second of game time
            testGame.render();
        }

        int secsAfter = testGame.getGame().getPlayer().getGameTimer().getSecsRemaining();

        assertEquals(secsBefore, secsAfter, "Game time should not decrease while the game is paused");

    }

}
