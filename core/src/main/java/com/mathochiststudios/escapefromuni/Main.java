package com.mathochiststudios.escapefromuni;

import java.awt.GraphicsEnvironment;
import java.util.Objects;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Menus.EndGameMenu;
import com.mathochiststudios.escapefromuni.Menus.LeaderboardMenu;
import com.mathochiststudios.escapefromuni.Menus.MainMenu;
import com.mathochiststudios.escapefromuni.Menus.MenuTextureManager;
import com.mathochiststudios.escapefromuni.Menus.PauseMenu;
import com.mathochiststudios.escapefromuni.Menus.PreGameSettingsMenu;
import com.mathochiststudios.escapefromuni.Menus.SettingsMenu;
import com.mathochiststudios.escapefromuni.Menus.TutorialMenu;
import com.mathochiststudios.escapefromuni.Tests.HeadlessBatch;
import com.mathochiststudios.escapefromuni.Tests.HeadlessStage;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.Tests.IStage;
import com.mathochiststudios.escapefromuni.Tests.LiveSpriteBatch;
import com.mathochiststudios.escapefromuni.Tests.LiveStage;
import com.mathochiststudios.escapefromuni.UI.Mouse;



/** {@link ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {

    public static boolean TESTING = GraphicsEnvironment.isHeadless(); // Set to false for production
    //

    Game game;

    Boolean hasReset = true;

    Boolean gameStarted;

    public ISpriteBatch batch;
    public FitViewport viewport;

    boolean paused = false;
    boolean allowPauseButton = false;

    int latestScore = -1;
    boolean wonLastGame;

    boolean buttonCD;

    // used for mouse coordinates
    Mouse mouse = new Mouse();
    

    MenuTextureManager textureManager;

    TextureManager textureManagerClassic;

    String menuState = "Main";

    String hoveredOver = "";
    boolean isEndGameMenu = false; // dont ask. I needed a on switch event for end menu

    MainMenu mainMenu;
    LeaderboardMenu leaderboardMenu;
    TutorialMenu tutorialMenu;
    EndGameMenu endGameMenu;
    PauseMenu pauseMenu;
    SettingsMenu settingsMenu;
    PreGameSettingsMenu preGamesettingsMenu;

    String input;
    String pauseState;
    GameDifficulty gameDifficulty = GameDifficulty.NORMAL;
    String difficulty = "Normal";

    String name = "Player";

    IStage stage;

    @Override
    public void create() {
        gameStarted = false;

        buttonCD = false;
        wonLastGame = false;

        if (TESTING) {
            batch = new HeadlessBatch();
        } else {
            batch = new LiveSpriteBatch();
        }

        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (TESTING) {
            stage = new HeadlessStage(viewport);
        } else {
            stage = new LiveStage(viewport);
            Gdx.input.setInputProcessor((InputProcessor) stage);
        }

        game = new Game(this, gameDifficulty);

        mainMenu = new MainMenu(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
        leaderboardMenu = new LeaderboardMenu(batch, viewport, buttonCD, mouse, textureManager);
        tutorialMenu = new TutorialMenu(batch, viewport, buttonCD, mouse, textureManager);
        endGameMenu = new EndGameMenu(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
        pauseMenu = new PauseMenu(batch, viewport, buttonCD, mouse, textureManager);
        settingsMenu = new SettingsMenu(batch, viewport, buttonCD, mouse, textureManager, name, difficulty, stage);
        preGamesettingsMenu = new PreGameSettingsMenu(batch, viewport, buttonCD, mouse, textureManager, name,
                difficulty, stage);

        // Load fonts
        textureManager = new MenuTextureManager(viewport);
        textureManagerClassic = new TextureManager(viewport);
    }

    public void startGame() {
        game = new Game(this, gameDifficulty);

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
                if (allowPauseButton && Gdx.input.isKeyJustPressed(Input.Keys.P)) {
                    // allowPauseButton = false;
                    pauseMenu.resetText();
                    paused = !paused;
                    ScreenUtils.clear(Color.CLEAR);
                }
                textureManagerClassic.getBgm().setVolume(0.3f);
                textureManagerClassic.getBgm().play();

                if (!TESTING) {
                    game.draw((SpriteBatch) batch, viewport);
                }

                if (!paused) {
                    game.input();
                    game.logic();
                    return;
                }
                pauseMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);

                if (TESTING) {
                    return;
                }
                pauseMenu.draw();
                pauseState = pauseMenu.input();
                switch (pauseState) {
                    case "Resume" -> {
                        paused = false;
                        pauseMenu.resetText();
                    }
                    case "Main" -> {
                        textureManagerClassic.getBgm().stop();
                        textureManagerClassic.getBgm().pause();
                        endGame(game.getScore(), game.WinOrLose);
                        textureManagerClassic.getBgm().stop();
                    }
                    case "Restart" -> {
                        startGame();
                        menuState = "Main";
                    }
                    default -> {
                    }
                }
                return;
            }

            endGame(game.getScore(), game.WinOrLose);
            return;
        }

        switch (menuState) {
            case "Main" -> {
                if (hasReset) {
                    mainMenu.resetText();
                    hasReset = false;
                }
                mainMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
                mainMenu.draw();
                menuState = mainMenu.input();
                if (!menuState.equals("Main")) {
                    hasReset = true;
                }
            }
            case "Tutorial" -> {
                if (hasReset) {
                    tutorialMenu.resetText();
                    hasReset = false;
                }
                tutorialMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
                tutorialMenu.draw();
                menuState = tutorialMenu.input();
                if (!menuState.equals("Tutorial")) {
                    hasReset = true;
                }
            }
            case "Start Game" -> {
                startGame();
                menuState = "Main";
            }
            case "Leaderboard" -> {
                if (hasReset) {
                    // leaderboardMenu.addLeaderboardEntry("test", 184, "Easy");
                    // leaderboardMenu.addLeaderboardEntry("test2", 1834, "Normal");
                    // leaderboardMenu.addLeaderboardEntry("test3", 14, "Hard");
                    leaderboardMenu.resetText();
                    hasReset = false;
                }
                leaderboardMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
                leaderboardMenu.draw();
                menuState = leaderboardMenu.input();
                if (!menuState.equals("Leaderboard")) {
                    hasReset = true;
                }
            }
            case "Settings" -> {
                menuState = "PreGameSettings";
                /*
                 * if (hasReset) {
                 * settingsMenu.resetText();
                 * hasReset = false;
                 * }
                 * settingsMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD,
                 * mouse, textureManager);
                 * settingsMenu.draw();
                 * menuState = settingsMenu.input();
                 * if (!menuState.equals("Settings")) {
                 * hasReset = true;
                 * }
                 * difficulty = settingsMenu.getDifficulty();
                 * if (settingsMenu.getDifficulty().equals("Easy")) {
                 * gameDifficulty = GameDifficulty.EASY;
                 * }
                 * else if (settingsMenu.getDifficulty().equals("Normal")) {
                 * gameDifficulty = GameDifficulty.NORMAL;
                 * }
                 * else if (settingsMenu.getDifficulty().equals("Hard")) {
                 * gameDifficulty = GameDifficulty.HARD;
                 * }
                 */
            }
            case "PreGameSettings" -> {
                if (hasReset) {
                    preGamesettingsMenu.textBoxunFix();
                    preGamesettingsMenu.resetText();
                    hasReset = false;
                }
                preGamesettingsMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
                preGamesettingsMenu.draw();
                menuState = preGamesettingsMenu.input();
                name = preGamesettingsMenu.getName();
                if (!menuState.equals("PreGameSettings")) {
                    hasReset = true;
                    preGamesettingsMenu.textBoxFix();
                }
                difficulty = preGamesettingsMenu.getDifficulty();
                if (preGamesettingsMenu.getDifficulty().equals("Easy")) {
                    gameDifficulty = GameDifficulty.EASY;
                } else if (preGamesettingsMenu.getDifficulty().equals("Normal")) {
                    gameDifficulty = GameDifficulty.NORMAL;
                } else if (preGamesettingsMenu.getDifficulty().equals("Hard")) {
                    gameDifficulty = GameDifficulty.HARD;
                }
            }
            case "EndMenu" -> {
                if (!isEndGameMenu) {
                    endGameMenu.onSwitchIn();
                    isEndGameMenu = true;
                    leaderboardMenu.addLeaderboardEntry(name, latestScore, difficulty);
                }
                endGameMenu.update(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
                endGameMenu.draw();
                menuState = endGameMenu.input();
                if (!menuState.equals("EndMenu")) {
                    isEndGameMenu = false;
                }
            }
            default -> menuState = "Main";
        }

    }

    public void endGame(int score, String winOrLose) {
        gameStarted = false;
        paused = false;
        allowPauseButton = false;
        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!Objects.equals(winOrLose, "Return")) { // should be not equals
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
        // game.pause();
        paused = true;
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
        // game.resume();
        pauseMenu.resetText();
        paused = false;
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        // game.dispose();
        textureManager.dispose();
    }

    public void setMenuState(String state) {
        this.menuState = state;
    }

    public String getMenuState() {
        return this.menuState;
    }

    public String getPlayerName() {
        return name;
    }

    public Game getGame() {
        return game;
    }

    // For testing purposes
    public void setPauseMenu(PauseMenu pm) {
        this.pauseMenu = pm;
    }

    public void setTextureManagerClassic(TextureManager tm) {
        this.textureManagerClassic = tm;
    }

    public TextureManager getTextureManagerClassic() {
        return this.textureManagerClassic;
    }

}
