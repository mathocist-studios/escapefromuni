package com.mathochiststudios.escapefromuni.headless.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mathochiststudios.escapefromuni.Main;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

public class TestingUtils {

    public static Main createTestGame() {
        Main.TESTING = true;
        Main app = new Main();
        app.create();
        app.startGame();
        return app;
    }

    public static SimulateKeyPress simulateGdxInputKeyPress(int keycode) {
        Input mockInput = mock(Input.class);
        Mockito.when(mockInput.isKeyPressed(keycode)).thenReturn(true);
        Mockito.when(mockInput.isKeyJustPressed(keycode)).thenReturn(true);
        Gdx.input = mockInput;
        return new SimulateKeyPress(mockInput, keycode);
    }

    public static SimulateKeyPress simulateMultiGdxInputKeyPress(int... keycodes) {
        Input mockInput = mock(Input.class);
        for (int keycode : keycodes) {
            Mockito.when(mockInput.isKeyPressed(keycode)).thenReturn(true);
            Mockito.when(mockInput.isKeyJustPressed(keycode)).thenReturn(true);
        }
        Gdx.input = mockInput;
        return new SimulateKeyPress(mockInput, keycodes);
    }

}
