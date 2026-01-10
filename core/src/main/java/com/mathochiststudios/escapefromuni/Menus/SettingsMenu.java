package com.mathochiststudios.escapefromuni.Menus;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.HeadlessStage;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.Tests.IStage;
import com.mathochiststudios.escapefromuni.Tests.LiveStage;
import com.mathochiststudios.escapefromuni.UI.Mouse;
import com.mathochiststudios.escapefromuni.UI.TextBox;

public class SettingsMenu extends AbstractMenu{

    IStage stage;
    TextBox nameBox;
    String name = "Player";
    String difficulty = "Normal";
    boolean music = true;

    public SettingsMenu(ISpriteBatch batch,
                               FitViewport viewport,
                               boolean buttonCD,
                               Mouse mouse,
                               MenuTextureManager textureManager,
                               String name,
                               String difficulty,
                               Boolean music,
                               IStage stage
    ) {
        super(batch, viewport, buttonCD, mouse, textureManager);

        try {
            stage = new LiveStage(viewport);
            Gdx.input.setInputProcessor((InputProcessor) stage);
        } catch (IllegalArgumentException e) {
            stage = new HeadlessStage(null);
        }

        this.name = name;
        this.difficulty = difficulty;
        this.stage = stage;
        this.music = music;

        nameBox = new TextBox(stage, 250+80, 600, 300, 40);
    }


    boolean alreadyClicked = true;

    @Override
    public String input() {
        mouse.update(viewport);
        if (Gdx.input.isTouched()) {
            if (!buttonCD) {

                if (textureManager.getNextButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    if (name.length() > 0 && name.length() <= 15) {
                        return "Main";
                    }
                    else {
                        JFrame coolbox = new JFrame();
                        JOptionPane.showMessageDialog(coolbox , "Pick a valid name below 15 characters!");
                    }
                }
                if (textureManager.getSubmitSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(),mouse.getY()))) {
                    name = nameBox.getText();
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
                if (textureManager.getTwoRightArrowSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(),mouse.getY()))  && !alreadyClicked) {
                    music = !music;
                }
                if (textureManager.getTwoLeftArrowSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(),mouse.getY())) && !alreadyClicked) {
                    music = !music;
                }
                alreadyClicked = true;
            }
        }
        if (
                textureManager.getSubmitSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                hoveredOver = "Submit";
            }
        else if (
                textureManager.getNextButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                hoveredOver = "Next";
            }
        else {
            hoveredOver = ""; }

        return "PreGameSettings";
    }

    @Override
    public void draw() {

        viewport.apply();


        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);
        ScreenUtils.clear(Color.BLACK);

        // title
        textureManager.getMainLayout().setText(textureManager.getGameLargeFont(), "Game Settings");
        float titleX = 400;
        float titleY = 800;
        textureManager.getGameLargeFont().draw(batch, "Game Settings" , titleX, titleY);

        textureManager.getMainLayout().setText(textureManager.getGameMediumFont(), "Game Settings");
        textureManager.getGameLargeFont().draw(batch, "Name:" , titleX-400, titleY-200);
        textureManager.getGameSmallFont().draw(batch, "Current Name: " + name , titleX-400, titleY-300);
        textureManager.getGameLargeFont().draw(batch, "Difficulty:" , titleX-400, titleY-400);
        textureManager.getGameLargeFont().draw(batch, "Music:" , titleX-400, titleY-550);

        batch.draw(textureManager.getLeftArrowTexture(),400+30,titleY-475, 100, 100);

        if (difficulty.equals("Easy")) {
            textureManager.getGameMediumFont().draw(batch, "Easy (0.5x Score)" ,490,titleY-400);
        }
        else if (difficulty.equals("Normal")) {
            textureManager.getGameMediumFont().draw(batch, "Normal (1x Score)" ,480,titleY-400);
        }
        else if (difficulty.equals("Hard")) {
            textureManager.getGameMediumFont().draw(batch, "Hard (2x Score)" ,490,titleY-400);
            //textureManager.getGameMediumFont().draw(batch, "Mouse Coords:  " + mouse.getX() + "." +mouse.getY() + "\nSubmit Coords: " + textureManager.getNextButtonSprite().getX() + "." + textureManager.getNextButtonSprite().getY(),690,450);
        }

        batch.draw(textureManager.getRightArrowTexture(),400+400+30, titleY-475 , 100, 100);


        
        batch.draw(textureManager.getTwoLeftArrowTexture(),400+30,titleY-640, 100, 100);
        if (music) {
            textureManager.getGameMediumFont().draw(batch, "On" ,490,titleY-575);
        }
        else if (!music) {
            textureManager.getGameMediumFont().draw(batch, "Off" ,480,titleY-575);
        }
        batch.draw(textureManager.getTwoRightArrowTexture(),400+200, titleY-640 , 100, 100);

        

        if (hoveredOver.equals("Submit")) {
            batch.draw(textureManager.getHoverSubmitTexture(),600+80,580, 300, 100);
        }
        else {
            batch.draw(textureManager.getSubmitTexture(),600+80,580, 300, 100);
        }
            




        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
        if (hoveredOver.equals("Next")) {
            batch.draw(textureManager.gethoverNextButtonTexture(),900,20, 300, 100);
        }
        else {
            batch.draw(textureManager.getNextButtonTexture(),900,20, 300, 100);
        }

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

    public Boolean getMusic() {
        return this.music;
    }

    public void textBoxFix(){
        nameBox.dispose();
    }
    public void textBoxunFix(){
        nameBox = new TextBox(stage, 250+80, 600, 300, 40);
    }

    public String getName(){
        return this.name;
    }
}
