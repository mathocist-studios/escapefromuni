package com.mathochiststudios.escapefromuni.Entities.PlayerInventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final ArrayList<InventoryObject> items;
    ShapeRenderer shapeRenderer = new ShapeRenderer();

    // Cache textures by file path/name to avoid creating new Texture every frame
    private final Map<String, Texture> textureCache = new HashMap<>();

    public Inventory() {
        items = new ArrayList<>();
    }

    public boolean addItem(InventoryObject item) {
        if (hasItem(item) && !item.allowsMultiple()) {
            return false;
        }
        items.add(item);
        return true;
    }

    public boolean hasItem(InventoryObject item) {
        return items.contains(item);
    }

    public void clear() {
        // dispose textures used by these items
        for (InventoryObject item : items) {
            String key = item.getObjectName();
            Texture t = textureCache.remove(key);
            if (t != null) t.dispose();
        }
        items.clear();
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {

        // compute layout in world units (assumes camera units)
        float padding = 0.1f;
        float iconSize = 1f; // world units per icon (preserve aspect ratio here)
        float spacing = 1.2f; // space per slot
        int count = items.size();
        float bgWidth = Math.max(1f, count * spacing + padding * 2);
        float bgHeight = iconSize + padding * 2;

        boolean batchWasDrawing = batch.isDrawing();

        // If the batch is drawing, end it before using ShapeRenderer to avoid GL state conflicts.
        if (batchWasDrawing) batch.end();

        // Draw background rectangle with ShapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.85f);
        shapeRenderer.rect(0f, 0f, bgWidth, bgHeight);
        shapeRenderer.end();

        // Ensure the batch uses the same projection matrix as the camera
        batch.setProjectionMatrix(camera.combined);

        // Restore batch state so we can draw textures via the batch
        batch.begin();

        // Make sure blending is enabled (ShapeRenderer may change GL blend state)
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        batch.enableBlending();
        // Reset any tint the batch may have (ShapeRenderer can leave a color state)
        batch.setColor(1f, 1f, 1f, 1f);

        // Draw each icon using cached textures
        for (int i = 0; i < count; i++) {
            InventoryObject item = items.get(i);
            String key = item.getObjectName();
            Texture texture = textureCache.get(key);
            if (texture == null) {
                // load once from internal files; assume objectName is a valid path
                try {
                    texture = new Texture(Gdx.files.internal(key));
                } catch (Exception e) {
                    // fallback: create a 1x1 white texture so the game doesn't crash
                    texture = new Texture(1, 1, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
                }
                textureCache.put(key, texture);
            }

            float x = padding + i * spacing;
            float y = padding;

            // draw the texture to fit the icon size while keeping aspect
            batch.draw(texture, x, y, iconSize, iconSize);
        }

        // If we began the batch here because it wasn't drawing before, end it to restore the original state
        if (!batchWasDrawing) batch.end();
    }

    public void removeItem(InventoryObject item) {
        items.remove(item);
        // optionally dispose the texture for this item if no longer used by others
        String key = item.getObjectName();
        boolean stillUsed = false;
        for (InventoryObject it : items) {
            if (it.getObjectName().equals(key)) {
                stillUsed = true; break;
            }
        }
        if (!stillUsed) {
            Texture t = textureCache.remove(key);
            if (t != null) t.dispose();
        }
    }

    // Call when the inventory is no longer needed to free GPU resources
    public void dispose() {
        for (Texture t : textureCache.values()) {
            if (t != null) t.dispose();
        }
        textureCache.clear();
        shapeRenderer.dispose();
    }

}
