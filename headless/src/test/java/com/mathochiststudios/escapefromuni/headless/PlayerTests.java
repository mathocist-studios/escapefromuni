package com.mathochiststudios.escapefromuni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.Main;
import com.mathochiststudios.escapefromuni.entities.Player;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        Robot robot = TestingUtils.createRobot();
        robot.keyPress(KeyEvent.VK_W);
        testGame.getGame().input();
        robot.keyRelease(KeyEvent.VK_W);
        System.out.println(player.getMoveDirection());
        assertEquals("Up", player.getMoveDirection(), "Player should be moving up");
    }

}
