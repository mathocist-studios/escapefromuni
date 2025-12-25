package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public class HeadlessShapeRenderer implements IShapeRenderer {

    public void begin(ShapeRenderer.ShapeType shapeType) {
        // No-op
    }

    public void setProjectionMatrix(Matrix4 combined) {
        // No-op
    }

    public void setColor(float r, float g, float b, float a) {
        // No-op
    }

    public void rect(float x, float y, float width, float height) {
        // No-op
    }

    public void end() {
        // No-op
    }

    public void dispose() {
        // No-op
    }

    @Override
    public void triangle(float v, float v1, float v2, float v3, float arrowX, float arrowY) {
        // No-op
    }
}
