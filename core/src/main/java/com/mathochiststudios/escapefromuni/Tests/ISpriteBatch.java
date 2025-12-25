package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Matrix4;

public interface ISpriteBatch extends Batch {
    void begin();

    void end();

    void setColor(Color tint);

    void setColor(float r, float g, float b, float a);

    Color getColor();

    void setPackedColor(float packedColor);

    float getPackedColor();

    void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY);

    void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY);

    void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight);

    void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2);

    void draw(Texture texture, float x, float y);

    void draw(Texture texture, float x, float y, float width, float height);

    void draw(Texture texture, float[] spriteVertices, int offset, int count);

    void draw(TextureRegion region, float x, float y);

    void draw(TextureRegion region, float x, float y, float width, float height);

    void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation);

    void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise);

    void draw(TextureRegion region, float width, float height, Affine2 transform);

    void flush();

    void disableBlending();

    void enableBlending();

    void setBlendFunction(int srcFunc, int dstFunc);

    void setBlendFunctionSeparate(int srcFuncColor, int dstFuncColor, int srcFuncAlpha, int dstFuncAlpha);

    int getBlendSrcFunc();

    int getBlendDstFunc();

    int getBlendSrcFuncAlpha();

    int getBlendDstFuncAlpha();

    Matrix4 getProjectionMatrix();

    Matrix4 getTransformMatrix();

    void setProjectionMatrix(Matrix4 projection);

    void setTransformMatrix(Matrix4 transform);

    void setShader(ShaderProgram shader);

    ShaderProgram getShader();

    boolean isBlendingEnabled();

    boolean isDrawing();

    void dispose();
}
