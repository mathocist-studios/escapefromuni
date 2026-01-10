package com.mathochiststudios.escapefromuni.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Mouse class that provides WORLD coordinates
 * Works correctly with camera + viewport resizing
 */
public class Mouse {

    private final Vector2 position = new Vector2();

    public void update(Viewport viewport) {
        position.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(position);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public Vector2 getPosition() {
        return position;
    }
}