package com.mathochiststudios.escapefromuni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mathochiststudios.escapefromuni.Main;
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

}
