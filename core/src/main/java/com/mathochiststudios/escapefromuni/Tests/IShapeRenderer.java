package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

public interface IShapeRenderer {
    void setColor(float v, float v1, float v2, float v3);

    void rect(float v, float v1, float bgWidth, float bgHeight);

    void end();

    void begin(ShapeRenderer.ShapeType shapeType);

    void setProjectionMatrix(Matrix4 combined);

    void dispose();

    void triangle(float v, float v1, float v2, float v3, float arrowX, float arrowY);
}
