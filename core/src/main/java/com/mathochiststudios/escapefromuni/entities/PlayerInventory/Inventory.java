package com.mathochiststudios.escapefromuni.entities.PlayerInventory;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<InventoryObject> items;

    public Inventory() {
        items = new ArrayList<InventoryObject>();
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
        items.clear();
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < items.size(); i++) {
            InventoryObject item = items.get(i);
            Texture texture = new Texture(item.getObjectName());
            Sprite sprite = new Sprite(texture);
            sprite.setSize(1, 1);
            sprite.setPosition(0.1f + i * 1.2f, 0.1f);
            sprite.draw(batch);
        }
    }

    public void removeItem(InventoryObject item) {
        items.remove(item);
    }

}
