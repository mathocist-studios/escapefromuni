package com.mathochiststudios.escapefromuni.Menus;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.Tests.IStage;
import com.mathochiststudios.escapefromuni.UI.Mouse;
import com.mathochiststudios.escapefromuni.UI.TextBox;

public class SettingsMenu extends AbstractMenu{

    //
    // ignore this class its old and broke lol
    //
    //
    //


    TextBox nameBox;
    String name = "Player";
    String difficulty = "Normal";
    IStage stage;

    public SettingsMenu(ISpriteBatch batch,
                        FitViewport viewport,
                        int latestScore,
                        boolean wonLastGame,
                        boolean buttonCD,
                        Mouse mouse,
                        MenuTextureManager textureManager,
                        String name,
                        String difficulty,
                        IStage stage
    ) {
        super(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
        this.name = name;
        this.difficulty = difficulty;
        this.stage = stage;

        nameBox = new TextBox(stage, 250+80, 600, 300, 40);

    }

    public void update(ISpriteBatch batch, FitViewport viewport, int latestScore, boolean wonLastGame, boolean buttonCD, Mouse mouse, MenuTextureManager textureManager) {
        this.batch = batch;
        this.viewport = viewport;
        this.latestScore = latestScore;
        this.wonLastGame = wonLastGame;
        this.buttonCD = buttonCD;
        this.mouse = mouse;
        this.textureManager = textureManager;
    }

    boolean alreadyClicked = true;

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
                if (textureManager.getSubmitSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(),mouse.getY()))) {
                    if (name.length() > 0 && name.length() <= 15) {
                        name = nameBox.getText();
                    }
                    else {
                        JFrame coolbox = new JFrame();
                        JOptionPane.showMessageDialog(coolbox , "Pick a valid name below 15 characters!");
                    }
                }
                if (textureManager.getRightArrowSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(),mouse.getY()))  && !alreadyClicked) {
                    if (difficulty.equals("Easy")) {
                        difficulty = "Normal";
                    }
                    else if (difficulty.equals("Normal")) {
                        difficulty = "Hard";
                    }
                    else if (difficulty.equals("Hard")) {
                        difficulty = "Easy";
                    }
                }
                if (textureManager.getLeftArrowSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(),mouse.getY())) && !alreadyClicked) {
                    if (difficulty.equals("Easy")) {
                        difficulty = "Hard";
                    }
                    else if (difficulty.equals("Normal")) {
                        difficulty = "Easy";
                    }
                    else if (difficulty.equals("Hard")) {
                        difficulty = "Normal";
                    }
                }
                alreadyClicked = true;
            }
        }
        else {
            alreadyClicked = false;
        }
        if (
                textureManager.getSubmitSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                hoveredOver = "Submit";
            }
        else {
            hoveredOver = ""; }

        return "Settings";
    }

    @Override
    public void draw() {

        if (nameBox.getText().length() == 0) {
            name = "Player";
        }
        if (nameBox.getText().length() > 15) {
            name = "Shorten your name!";
        }

        viewport.apply();


        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        // title
        textureManager.getMainLayout().setText(textureManager.getGameLargeFont(), "Settings");
        float titleX = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
        float titleY = Gdx.graphics.getHeight() - 100;
        textureManager.getGameLargeFont().draw(batch, "Settings" , titleX, titleY);

        textureManager.getMainLayout().setText(textureManager.getGameMediumFont(), "Settings");
        textureManager.getGameLargeFont().draw(batch, "Name:" , titleX-400, titleY-200);
        textureManager.getGameSmallFont().draw(batch, "Current Name: " + name , titleX-400, titleY-300);
        textureManager.getGameLargeFont().draw(batch, "Difficulty:" , titleX-400, titleY-400);

        batch.draw(textureManager.getLeftArrowTexture(),400+30,370, 100, 100);

        if (difficulty.equals("Easy")) {
            textureManager.getGameMediumFont().draw(batch, "Easy (0.5x Score)" ,490,450);
        }
        else if (difficulty.equals("Normal")) {
            textureManager.getGameMediumFont().draw(batch, "Normal (1x Score)" ,480,450);
        }
        else if (difficulty.equals("Hard")) {
            textureManager.getGameMediumFont().draw(batch, "Hard (2x Score)" ,490,450);
        }

        batch.draw(textureManager.getRightArrowTexture(),400+400+30,370, 100, 100);

        if (hoveredOver.equals("Submit")) {
            batch.draw(textureManager.getHoverSubmitTexture(),600+80,580, 300, 100);
        }
        else {
            batch.draw(textureManager.getSubmitTexture(),600+80,580, 300, 100);
        }




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

    public String getDifficulty() {
        return this.difficulty;
    }
}
