package com.mathochiststudios.escapefromuni;

import com.mathochiststudios.escapefromuni.UI.Mouse;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Objects;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {

    Game game;

    Boolean gameStarted;

    public SpriteBatch batch;
    public FitViewport viewport;

    boolean paused = false;
    boolean allowPauseButton = false;

    int latestScore = -1;
    boolean wonLastGame;

    boolean buttonCD;

    //used for mouse coordinates
    Mouse mouse = new Mouse();

    TextureManager textureManager;

    @Override
    public void create() {
        gameStarted = false;

        buttonCD = false;
        wonLastGame = false;

        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Load fonts
        textureManager = new TextureManager(viewport);
    }

    public void startGame() {
        game = new Game(this);

        gameStarted = true;
        paused = false;
        allowPauseButton = true;
        game.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void resize(int width, int height) {
        if (gameStarted) {
            game.resize(width, height);
        } else {
            viewport.update(width, height, true);
        }
    }

    // Runs every frame
    @Override
    public void render() {

        if (!Gdx.input.isTouched()) {
            buttonCD = false;
        }

        if (gameStarted) {
            if (!game.gameEnded) {
                if(allowPauseButton && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    //allowPauseButton = false;
                    paused = !paused;
                    ScreenUtils.clear(Color.CLEAR);
                }

                game.draw(batch, viewport);

                if (!paused) {
                    game.input();
                    game.logic();
                } else {
                    drawPauseMenu();
                    inputPauseMenu();
                }
            } else {
                endGame(Game.Score, game.WinOrLose);
            }
        } else {
            drawMainMenu();
            inputMainMenu();
        }
    }

    public void endGame(int score, String winOrLose) {
        gameStarted = false;
        paused = false;
        allowPauseButton = false;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!Objects.equals(winOrLose, "Return")) { //should be not equals
            latestScore = score;
            wonLastGame = Objects.equals(winOrLose, "Win");
        } else {
            latestScore = -1;
            wonLastGame = false;
        }

        game = null;
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
        //game.pause();
        paused = true;
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
        //game.resume();
        paused = false;
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        //game.dispose();
        textureManager.dispose();
    }

    private void inputMainMenu() {
        if (Gdx.input.isTouched()) {
            mouse.update(viewport);

            //Gdx.app.log("MyTag", mouseX + " " + mouseY);
            //Gdx.app.log("buttonxy", playButtonSprite.getBoundingRectangle().x+" "+playButtonSprite.getBoundingRectangle().y);

            if (!buttonCD &&
                textureManager.getPlayButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                startGame();
                buttonCD = true;
            }
        }
    }

    private void drawMainMenu() {

        ScreenUtils.clear(Color.SALMON);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        textureManager.getPlayButtonSprite().draw(batch);
        batch.draw(textureManager.getMenuText(),200,700, 880, 110);

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

    private void drawPauseMenu() {

        //ScreenUtils.clear(Color.CLEAR);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        batch.draw(textureManager.getPausedText(),240,660, 800, 200);

        textureManager.getReturnToMenuButtonSprite().draw(batch);
        textureManager.getResumeButtonSprite().draw(batch);

        batch.end();
    }

    private void inputPauseMenu() {
        if (Gdx.input.isTouched()) {
            mouse.update(viewport);

            if (!buttonCD) {
                if (textureManager.getResumeButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    paused = false;
                    ScreenUtils.clear(Color.CLEAR);
                    game.draw(batch, viewport);
                } else if (textureManager.getReturnToMenuButtonSprite().getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    endGame(Game.Score, game.WinOrLose);
                    buttonCD = true;
                }
            }
        }
    }

    public boolean getButtonCD() {
        return buttonCD;
    }

}
