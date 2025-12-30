package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class PauseMenu extends AbstractMenu{

    public PauseMenu(
            ISpriteBatch batch,
            FitViewport viewport,
            boolean buttonCD,
            Mouse mouse,
            MenuTextureManager textureManager
    ) {
        super(batch, viewport, buttonCD, mouse, textureManager);
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
            mouse.update(viewport);

            if (!buttonCD) {
                if (textureManager.getResumeButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    return "Resume";

                } else if (textureManager.getReturnToMenuButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    buttonCD = true;
                    return "Main";
                } else if (textureManager.getRestartSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    buttonCD = true;
                    return "Restart";
                }
            }
        }
        if (!buttonCD) {
                if (textureManager.getResumeButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    hoveredOver = "Resume";

                } else if (textureManager.getReturnToMenuButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    hoveredOver = "ReturnToMenu";
                } else if (textureManager.getRestartSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    hoveredOver = "Restart";
                }
            }
        return "Pause";
    }

    @Override
    public void draw() {
        //ScreenUtils.clear(Color.CLEAR);

        textX=textX+acceleration;
        acceleration=acceleration-1;

        if (acceleration<0) {
            acceleration=0;
        }

        if (textX>290) {
            textX=290;
        }

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(textureManager.getOverlayTexture(),0,0, 1280, 960);
        batch.draw(textureManager.getPausedText(),240,660, 800, 200);

        if (hoveredOver.equals("Resume")){
            batch.draw(textureManager.hovergetResumeButtonTexture(),textX,500, 300, 100);
        } else {
            batch.draw(textureManager.getResumeButtonSprite(),textX,500, 300, 100);
        }
        if (hoveredOver.equals("Restart")){
            batch.draw(textureManager.gethoverRestartTexture(),textX,300, 300, 100);
        } else {
            batch.draw(textureManager.getRestartSprite(),textX,300, 300, 100);
        }
        if (hoveredOver.equals("ReturnToMenu")){
            batch.draw(textureManager.gethoverReturnToMenuButtonTexture(),textX,100, 300, 100);
        } else {
            batch.draw(textureManager.getReturnToMenuButtonSprite(),textX,100, 300, 100);
        }

        batch.end();
    }

}
