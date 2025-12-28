package com.mathochiststudios.escapefromuni.headless.Utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import org.mockito.Mockito;

public class SimulateKeyPress {

    private final int keycode;
    private final Input mockInput;

    public SimulateKeyPress(Input mockInput, int keycode) {
        this.keycode = keycode;
        this.mockInput = mockInput;
    }

    public void release() {
        Mockito.when(mockInput.isKeyPressed(keycode)).thenReturn(false);
        Mockito.when(mockInput.isKeyJustPressed(keycode)).thenReturn(false);
        Gdx.input = mockInput;
    }

}
