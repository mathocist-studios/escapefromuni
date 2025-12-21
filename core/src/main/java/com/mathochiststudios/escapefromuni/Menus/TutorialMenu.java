package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.TextureManager;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class TutorialMenu extends AbstractMenu{

    public TutorialMenu(
            SpriteBatch batch,
            FitViewport viewport,
            int latestScore,
            boolean wonLastGame,
            boolean buttonCD,
            Mouse mouse,
            TextureManager textureManager
    ) {
        super(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
    }

    public void update(SpriteBatch batch, FitViewport viewport, int latestScore, boolean wonLastGame, boolean buttonCD, Mouse mouse, TextureManager textureManager) {
        this.batch = batch;
        this.viewport = viewport;
        this.latestScore = latestScore;
        this.wonLastGame = wonLastGame;
        this.buttonCD = buttonCD;
        this.mouse = mouse;
        this.textureManager = textureManager;
    }


    @Override
    public String input() {
        mouse.update(viewport);
        if (Gdx.input.isTouched()) {
            if (!buttonCD &&
                textureManager.getStartButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                return "Start Game";
            }
        }
            if (
                textureManager.getStartButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                    hoveredOver = "Start";
            }
            else {
                hoveredOver = "";
            }
        return "Tutorial";
    }

    @Override
    public void resetText() {
        this.textX = -400;
        this.acceleration = 40;
    }

    
    public void draw() {

        textX=textX+acceleration;
        acceleration=acceleration-1;

        if (acceleration<0) {
            acceleration=0;
        }

        if (textX>500) {
            textX=500;
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        batch.draw(textureManager.getTutorialSprite(),0,0, 1280, 960);

        if (hoveredOver.equals("Start")) {
        batch.draw(textureManager.getStartButtonSprite(),textX,100, 700, 100);
        }
        else {
        batch.draw(textureManager.getunStartButtonSprite(),textX,100, 700, 100);
        }
        
        

        batch.end();
    }

}
