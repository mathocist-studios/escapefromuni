package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.TextureManager;
import com.mathochiststudios.escapefromuni.UI.Mouse;
import com.mathochiststudios.escapefromuni.UI.TextBox;

public class SettingsMenu extends AbstractMenu{

    Stage stage;
    TextBox nameBox;

    public SettingsMenu(SpriteBatch batch,
            FitViewport viewport,
            int latestScore,
            boolean wonLastGame,
            boolean buttonCD,
            Mouse mouse,
            TextureManager textureManager
    ) {
        super(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        nameBox = new TextBox(stage, 250, 600, 300, 40);
        
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
            if (!buttonCD) {
                // create a temporary sprite for the back arrow using the existing return texture
                Sprite back = new Sprite(textureManager.getReturnToMenuButtonTexture());
                float w = 160; // arrow width
                float h = 80;  // arrow height
                float padding = 20;
                float sx = viewport.getWorldWidth() - padding - w;
                float sy = padding;
                back.setSize(w, h);
                back.setPosition(sx, sy);

                if (back.getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    return "Main";
                }
            }
        }

        String name = nameBox.getText();

        return "Settings";
    }

    @Override
    public void draw() {

        viewport.apply();

        
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        // title
        textureManager.getMainLayout().setText(textureManager.getGameLargeFont(), "Leaderboard");
        float titleX = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
        float titleY = Gdx.graphics.getHeight() - 100;
        textureManager.getGameLargeFont().draw(batch, "Leaderboard" , titleX, titleY);

        textureManager.getMainLayout().setText(textureManager.getGameMediumFont(), "Leaderboard");
        textureManager.getGameLargeFont().draw(batch, "Name:" , titleX-400, titleY-200);
        textureManager.getGameLargeFont().draw(batch, "Difficulty:" , titleX-400, titleY-400);

        
        

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        //got to do it a fucky way since its stuck from the pause menu and ica editing that rn
        Sprite back = new Sprite(textureManager.getReturnToMenuButtonTexture());
        back.setSize(180, 100);
        back.setPosition(textX, 20);
        back.draw(batch);
        batch.end();
    }

    @Override
    public void resetText() {
        this.textX = 600;
        this.acceleration = 40;
    }
}
