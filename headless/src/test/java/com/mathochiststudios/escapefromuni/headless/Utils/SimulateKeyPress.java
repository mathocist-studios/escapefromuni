package com.mathochiststudios.escapefromuni.headless.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.mockito.Mockito;

public class SimulateKeyPress {

    private final int[] keycodes;
    private final Input mockInput;

    public SimulateKeyPress(Input mockInput, int keycode) {
        this.keycodes = new int[] { keycode };
        this.mockInput = mockInput;
    }

    public SimulateKeyPress(Input mockInput, int[] keycodes) {
        this.keycodes = keycodes;
        this.mockInput = mockInput;
    }

    public void release() {
        for (int keycode : keycodes) {
            Mockito.when(mockInput.isKeyPressed(keycode)).thenReturn(false);
            Mockito.when(mockInput.isKeyJustPressed(keycode)).thenReturn(false);
            Gdx.input = mockInput;
        }
    }

}
