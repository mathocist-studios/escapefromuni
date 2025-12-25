package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.TextureManager;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public abstract class AbstractMenuLegacy {

    protected ISpriteBatch batch;
    protected FitViewport viewport;

    protected int latestScore = -1;
    protected boolean wonLastGame;
    protected boolean buttonCD;

    protected Mouse mouse;
    protected TextureManager textureManager;

    protected String hoveredOver = "Play";

    protected AbstractMenuLegacy(
            ISpriteBatch batch,
            FitViewport viewport,
            int latestScore,
            boolean wonLastGame,
            boolean buttonCD,
            Mouse mouse,
            TextureManager textureManager
    ) {
        this.batch = batch;
        this.viewport = viewport;
        this.latestScore = latestScore;
        this.wonLastGame = wonLastGame;
        this.buttonCD = buttonCD;
        this.mouse = mouse;
        this.textureManager = textureManager;
    }

    /**
     * Update shared menu state (useful when switching menus)
     */
    public void update(
            ISpriteBatch batch,
            FitViewport viewport,
            int latestScore,
            boolean wonLastGame,
            boolean buttonCD,
            Mouse mouse,
            TextureManager textureManager,
            String hoveredOver
    ) {
        this.batch = batch;
        this.viewport = viewport;
        this.latestScore = latestScore;
        this.wonLastGame = wonLastGame;
        this.buttonCD = buttonCD;
        this.mouse = mouse;
        this.textureManager = textureManager;
        this.hoveredOver = hoveredOver;
    }

    /**
     * Handle input for the menu.
     * @return a state change string (e.g. "Main", "Settings", "Start Game")
     */
    public abstract String input();

    /**
     * Draw the menu contents.
     */
    public abstract void draw();
}
