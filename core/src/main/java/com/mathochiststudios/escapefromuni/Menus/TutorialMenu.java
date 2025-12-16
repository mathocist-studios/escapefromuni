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

    
    public void draw() {

        ScreenUtils.clear(Color.SALMON);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        batch.draw(textureManager.getTutorialSprite(),0,0, 1280, 960);

        if (hoveredOver.equals("Start")) {
        batch.draw(textureManager.getStartButtonSprite(),500,100, 700, 100);
        }
        else {
        batch.draw(textureManager.getunStartButtonSprite(),500,100, 700, 100);
        }
        

        
        

        if (latestScore > -1) {

            if (wonLastGame) {
                textureManager.getMainLayout().setText(textureManager.getMainFont(), "Score: " + latestScore);

                float tempx = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
                float tempy = 480;

                textureManager.getMainFont().draw(batch, textureManager.getMainLayout(), tempx, tempy);

                textureManager.getMainLayout().setText(textureManager.getMainFont(), "You won!");

                tempx = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
                tempy = 640;

                textureManager.getMainFont().draw(batch, textureManager.getMainLayout(), tempx, tempy);
            } else {
                textureManager.getMainLayout().setText(textureManager.getMainFont(), "You lost :(");

                float tempx = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
                float tempy = 560;

                textureManager.getMainFont().draw(batch, textureManager.getMainLayout(), tempx, tempy);
            }
        }

        batch.end();
    }

}
