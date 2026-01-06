package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class TutorialMenu extends AbstractMenu{

    public TutorialMenu(
            ISpriteBatch batch,
            FitViewport viewport,
            boolean buttonCD,
            Mouse mouse,
            MenuTextureManager textureManager
    ) {
        super(batch, viewport, buttonCD, mouse, textureManager);
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

        if (textX>80) {
            textX=80;
        }

        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        batch.draw(textureManager.getTutorialSprite(),0,0, 1280, 960);

        textureManager.getMainLayout().setText(textureManager.getGameLargeFont(), "Tutorial");
        float titleX = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
        float titleY = Gdx.graphics.getHeight() - 100;
        textureManager.getGameLargeFont().draw(batch, "Tutorial" , titleX, titleY);

        textureManager.getGameMediumFont().draw(batch, 
            "Welcome to Escape From Uni!\n\nYou're studying in the library when you realise you forgot to \nsubmit your coursework with only 5 minues to the deadline! Sadly\nyour only copy of the document is on your computer at home,\nso can you get back in time to submit or fail your course?" 
            , titleX/8, titleY-130);


        if (hoveredOver.equals("Start")) {
        batch.draw(textureManager.getStartButtonSprite(),textX,100, 700, 100);
        }
        else {
        batch.draw(textureManager.getunStartButtonSprite(),textX,100, 700, 100);
        }



        batch.end();
    }

}
