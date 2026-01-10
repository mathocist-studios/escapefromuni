package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class MainMenu extends AbstractMenu{

    boolean wonLastGame = false;
    int latestScore = -1;

    public MainMenu(
            ISpriteBatch batch,
            FitViewport viewport,
            int latestScore,
            boolean wonLastGame,
            boolean buttonCD,
            Mouse mouse,
            MenuTextureManager textureManager
    ) {
        super(batch, viewport, buttonCD, mouse, textureManager);
        this.latestScore = latestScore;
        this.wonLastGame = wonLastGame;
        textX=0;
    }

    @Override
    public void resetText() {
        this.textX = -400;
        this.acceleration = 40;
    }


    @Override
    public String input() {
        mouse.update(viewport);
        if (Gdx.input.isTouched()) {

            //Gdx.app.log("MyTag", mouseX + " " + mouseY);
            //Gdx.app.log("buttonxy", playButtonSprite.getBoundingRectangle().x+" "+playButtonSprite.getBoundingRectangle().y);

            if (!buttonCD &&
                textureManager.getPlayButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                return "Tutorial";
            }

            if (!buttonCD &&
                textureManager.getSettingsButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                return "PreGameSettings";
            }
            if (!buttonCD &&
                textureManager.getcreditsButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                return "Credits";
            }
            if (!buttonCD &&
                textureManager.getLeaderboardButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                return "Leaderboard";
            }
            if (!buttonCD &&
                textureManager.getExitButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                Gdx.app.exit();
            }
        }
        if (
                textureManager.getPlayButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                hoveredOver = "Play";
            }
            if (
                textureManager.getSettingsButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                    hoveredOver = "Settings";
            }
            if (
                textureManager.getLeaderboardButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                    hoveredOver = "Leaderboard";
            }
            if (
                textureManager.getExitButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                    hoveredOver = "Exit";
            }
            if (
                textureManager.getcreditsButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                    hoveredOver = "Credits";
            }
        return "Main";
    }


    @Override
    public void draw() {

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        batch.draw(textureManager.getMenuText(),100,700, 700, 100);

        textX=textX+acceleration;
        acceleration=acceleration-1;

        if (acceleration<0) {
            acceleration=0;
        }

        if (textX>100) {
            textX=100;
        }

        // im so sorry
        if (hoveredOver.equals("Play")) {
            batch.draw(textureManager.getPlayButtonSprite(),textX,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),textX,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),textX,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),textX,100, 700, 100);
        batch.draw(textureManager.getuncreditsButtonSprite(),textX,200, 700, 100);
        }
        else if (hoveredOver.equals("Leaderboard")) {
            batch.draw(textureManager.getunPlayButtonSprite(),textX,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),textX,400, 700, 100);
        batch.draw(textureManager.getLeaderboardButtonSprite(),textX,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),textX,100, 700, 100);
        batch.draw(textureManager.getuncreditsButtonSprite(),textX,200, 700, 100);
        }
        else if (hoveredOver.equals("Settings")) {
            batch.draw(textureManager.getunPlayButtonSprite(),textX,500, 700, 100);
        batch.draw(textureManager.getSettingsButtonSprite(),textX,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),textX,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),textX,100, 700, 100);
        batch.draw(textureManager.getuncreditsButtonSprite(),textX,200, 700, 100);
        }
        else if (hoveredOver.equals("Exit")) {
            batch.draw(textureManager.getunPlayButtonSprite(),textX,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),textX,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),textX,300, 700, 100);
        batch.draw(textureManager.getExitButtonSprite(),textX,100, 700, 100);
        batch.draw(textureManager.getuncreditsButtonSprite(),textX,200, 700, 100);
        }
        else if (hoveredOver.equals("Credits")) {
            batch.draw(textureManager.getunPlayButtonSprite(),textX,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),textX,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),textX,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),textX,100, 700, 100);
        batch.draw(textureManager.getcreditsButtonSprite(),textX,200, 700, 100);
        }
        else {
            batch.draw(textureManager.getunPlayButtonSprite(),textX,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),textX,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),textX,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),textX,100, 700, 100);
        batch.draw(textureManager.getuncreditsButtonSprite(),textX,200, 700, 100);
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
