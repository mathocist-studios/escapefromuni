package com.mathochiststudios.escapefromuni.Menus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.TextureManager;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class LeaderboardMenu  extends AbstractMenu{

    private List<String> leaderboardLines = new ArrayList<>();

    public LeaderboardMenu(SpriteBatch batch,
            FitViewport viewport,
            int latestScore,
            boolean wonLastGame,
            boolean buttonCD,
            Mouse mouse,
            TextureManager textureManager
    ) {
        super(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);
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
        return "Leaderboard";
    }

    @Override
    public void draw() {
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

    public void loadLeaderboard() {
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
}
