package com.mathochiststudios.escapefromuni.headless;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.mathochiststudios.escapefromuni.Main;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.headless.Utils.SimulateKeyPress;
import com.mathochiststudios.escapefromuni.headless.Utils.TestingUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

public class EntityTests extends AbstractHeadlessGdxTest {

    @Test
    public void testBusEntityEndPoint() {

        Main app = TestingUtils.createTestGame();
        Player player = app.getGame().getPlayer();

        // Switch to BusLevel
        app.getGame().switchToLevel(app.getGame().getLevels().get(3), "BusLevel");

        // Move player to bus interaction point
        player.getMoneySprite().setX(7);
        player.getMoneySprite().setY(5);
        player.getMoneyRectangle().x = player.getMoneySprite().getX();
        player.getMoneyRectangle().y = player.getMoneySprite().getY();

        Graphics mockGraphics = mock(Graphics.class);
        Mockito.when(mockGraphics.getDeltaTime()).thenReturn(0.016f); // Approx. 60 FPS
        Gdx.graphics = mockGraphics;

        // simulate some game logic steps to let the duck move
        for (int i = 0; i < 540; i++) { // simulate 9 seconds of game time so the bus can move to the interaction spot
            app.getGame().input();
            app.getGame().logic();
        }

        // press interact key
        SimulateKeyPress eKeyPress = TestingUtils.simulateGdxInputKeyPress(Input.Keys.E);

        // simulate input and logic steps
        app.getGame().input();
        app.getGame().logic();

        // release interact key
        eKeyPress.release();

        // Check if game ended with a win
        assertSame("EndMenu", app.getMenuState(), "Game should have switched to EndMenu");
        assertSame("Win", app.getGame().WinOrLose, "Player should have won the game");

    }

}
