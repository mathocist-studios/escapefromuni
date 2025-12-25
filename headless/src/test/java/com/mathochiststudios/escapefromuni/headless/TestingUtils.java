package com.mathochiststudios.escapefromuni.headless;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.Main;
import com.mathochiststudios.escapefromuni.entities.Player;

import java.awt.*;

public class TestingUtils {

    public static Player createTestPlayer() {
        Main.TESTING = true;
        Main app = new Main();
        app.startGame();
        Game game = app.getGame();
        return game.getPlayer();
    }

    public static Main createTestGame() {
        Main.TESTING = true;
        Main app = new Main();
        app.startGame();
        return app;
    }

    public static Robot createRobot() {
        try {
            return new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

}
