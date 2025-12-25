package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class LiveTiledMapRenderer extends OrthogonalTiledMapRenderer implements ITiledMapRenderer {

    public LiveTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

}
