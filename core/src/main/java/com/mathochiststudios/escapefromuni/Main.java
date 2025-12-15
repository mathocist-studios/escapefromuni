package com.mathochiststudios.escapefromuni;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.UI.Mouse;

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

    String menuState = "Main";

    String hoveredOver = "";

    // cached leaderboard lines (loaded when entering leaderboard menu)
    private List<String> leaderboardLines = new ArrayList<>();

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
        game = new Game(this, GameDifficulty.NORMAL);

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
            if (menuState.equals("Main")){
                drawMainMenu();
                inputMainMenu();
            }
            if (menuState.equals("Leaderboard")){
                drawLeaderboardMenu();
                inputLeaderboardMenu();
            }
            if (menuState.equals("Settings")){
                menuState = "Main";
                drawSettingsMenu();
                inputSettingsMenu();
            }

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



    private void inputLeaderboardMenu() {
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
                    menuState = "Main";
                    buttonCD = true;
                    hoveredOver = "";
                }
            }
        }
    }

    private void inputSettingsMenu() {
        // settings screen uses same bottom-right back arrow
        mouse.update(viewport);
        if (Gdx.input.isTouched()) {
            if (!buttonCD) {
                Sprite back = new Sprite(textureManager.getReturnToMenuButtonTexture());
                float w = 160;
                float h = 80;
                float padding = 20;
                float sx = viewport.getWorldWidth() - padding - w;
                float sy = padding;
                back.setSize(w, h);
                back.setPosition(sx, sy);

                if (back.getBoundingRectangle().contains(new Vector2(mouse.getX(), mouse.getY()))) {
                    menuState = "Main";
                    buttonCD = true;
                    hoveredOver = "";
                }
            }
        }
    }

    private void drawLeaderboardMenu() {
        loadLeaderboard();

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        // title
        textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), "Leaderboard");
        float titleX = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
        float titleY = Gdx.graphics.getHeight() - 100;
        textureManager.getGameSmallFont().draw(batch, "Leaderboard" , titleX, titleY);

        float startY = titleY - 60;
        float lineSpacing = 48f;
        if (leaderboardLines.size() == 0) {
            textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), "No leaderboard found.");
            float x = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
            textureManager.getGameSmallFont().draw(batch, textureManager.getMainLayout(), x, startY);
        } else {
            float y = startY;
            for (String line : leaderboardLines) {
                textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), line);
                float x = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
                textureManager.getGameSmallFont().draw(batch, textureManager.getMainLayout(), x, y);
                y -= lineSpacing;
            }
        }

        //got to do it a fucky way since its stuck from the pause menu and ica editing that rn
        Sprite back = new Sprite(textureManager.getReturnToMenuButtonTexture());
        back.setSize(180, 100);
        back.setPosition(1050, 20);
        back.draw(batch);
        batch.end();
    }

    private void drawSettingsMenu() {

    }

    private void loadLeaderboard() {
        leaderboardLines.clear();

        String path = System.getProperty("user.dir");
        File leaderboardFile = new File(path, "leaderboard.txt");

        try {
            leaderboardFile.createNewFile();
        } catch (IOException e) {
            return;
        }

        if (!leaderboardFile.exists()) {
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                leaderboardLines.add(line);
                if (leaderboardLines.size() >= 20) break;
            }
        } catch (IOException e) {
            leaderboardLines.clear();
            leaderboardLines.add("Error reading leaderboard.");
        }
}


    private void inputMainMenu() {
        mouse.update(viewport);
        if (Gdx.input.isTouched()) {

            //Gdx.app.log("MyTag", mouseX + " " + mouseY);
            //Gdx.app.log("buttonxy", playButtonSprite.getBoundingRectangle().x+" "+playButtonSprite.getBoundingRectangle().y);

            if (!buttonCD &&
                textureManager.getPlayButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                startGame();
                buttonCD = true;
            }
            if (!buttonCD &&
                textureManager.getSettingsButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                menuState = "Settings";
                buttonCD = true;
            }
            if (!buttonCD &&
                textureManager.getLeaderboardButtonSprite().getBoundingRectangle().contains(
                    new Vector2(mouse.getX(),mouse.getY())
                )) {
                menuState = "Leaderboard";
                leaderboardLines.clear();
                loadLeaderboard();
                buttonCD = true;
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
    }

    private void drawMainMenu() {

        ScreenUtils.clear(Color.SALMON);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        batch.draw(textureManager.getMenuText(),100,700, 700, 100);


        // im so sorry
        if (hoveredOver.equals("Play")) {
            batch.draw(textureManager.getPlayButtonSprite(),100,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),100,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),100,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),100,200, 700, 100);
        }
        else if (hoveredOver.equals("Leaderboard")) {
            batch.draw(textureManager.getunPlayButtonSprite(),100,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),100,400, 700, 100);
        batch.draw(textureManager.getLeaderboardButtonSprite(),100,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),100,200, 700, 100);
        }
        else if (hoveredOver.equals("Settings")) {
            batch.draw(textureManager.getunPlayButtonSprite(),100,500, 700, 100);
        batch.draw(textureManager.getSettingsButtonSprite(),100,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),100,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),100,200, 700, 100);
        }
        else if (hoveredOver.equals("Exit")) {
            batch.draw(textureManager.getunPlayButtonSprite(),100,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),100,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),100,300, 700, 100);
        batch.draw(textureManager.getExitButtonSprite(),100,200, 700, 100);
        }
        else {
            batch.draw(textureManager.getunPlayButtonSprite(),100,500, 700, 100);
        batch.draw(textureManager.getunSettingsButtonSprite(),100,400, 700, 100);
        batch.draw(textureManager.getunLeaderboardButtonSprite(),100,300, 700, 100);
        batch.draw(textureManager.getunExitButtonSprite(),100,200, 700, 100);
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
