package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class LoseMenu extends AbstractMenu {

    public LoseMenu(
            ISpriteBatch batch,
            FitViewport viewport,
            boolean buttonCD,
            Mouse mouse,
            MenuTextureManager textureManager) {
        super(batch, viewport, buttonCD, mouse, textureManager);
    }

    @Override
    public String input() {
        mouse.update(viewport);
        if (Gdx.input.isTouched()) {
            if (!buttonCD &&
                    textureManager.getStartButtonSprite().getBoundingRectangle().contains(
                            new Vector2(mouse.getX(), mouse.getY()))) {
                return "Start Game";
            }
        }
        if (textureManager.getStartButtonSprite().getBoundingRectangle().contains(
                new Vector2(mouse.getX(), mouse.getY()))) {
            hoveredOver = "Start";
        } else {
            hoveredOver = "";
        }
        return "Lose";
    }

    @Override
    public void resetText() {
        this.textX = -400;
        this.acceleration = 40;
    }

    public void draw() {

        textX = textX + acceleration;
        acceleration = acceleration - 1;

        if (acceleration < 0) {
            acceleration = 0;
        }

        if (textX > 80) {
            textX = 80;
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(), 0, 0, 1280, 960);

        batch.draw(textureManager.getaStartButtonSprite(), textX, 100, 700, 100);

        textureManager.getMainLayout().setText(textureManager.getGameLargeFont(), "You Lost!");
        float titleX = 250;
        float titleY = 900;
        textureManager.getGameLargeFont().draw(batch, "You Lost!", titleX, titleY);

        textureManager.getGameMediumFont().draw(batch,
                "Uh-oh, you ran out of time!\n\nYou only have 5 minutes, make sure to use your\ntime wisely!\n\n\n\n\n\n\n\n\nClick here to try agian!",
                titleX / 8, titleY - 130);

        if (hoveredOver.equals("Start")) {
            batch.draw(textureManager.getaStartButtonSprite(), textX, 100, 700, 100);
        } else {
            batch.draw(textureManager.getaunStartButtonSprite(), textX, 100, 700, 100);
        }

        batch.end();
    }

}
