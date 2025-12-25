package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;

public interface ITiledMapRenderer {
    void setView(OrthographicCamera camera);

    void setMap(TiledMap map);

    void render();

    Batch getBatch();
}
