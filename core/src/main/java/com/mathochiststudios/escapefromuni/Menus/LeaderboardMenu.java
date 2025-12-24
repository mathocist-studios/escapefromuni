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

public class LeaderboardMenu extends AbstractMenu{

    private List<String> leaderboardLines = new ArrayList<>();
    String path = System.getProperty("user.dir");
    File leaderboardFile = new File(path, "leaderboards.txt");

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

        textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), "Leaderboard");
        float titleX = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
        float titleY = Gdx.graphics.getHeight() - 100;
        textureManager.getGameLargeFont().draw(batch, "Leaderboard" , titleX-130, titleY+50);

        float startY = titleY - 60;
        float lineSpacing = 48f;
        int count = 1;
        if (leaderboardLines.size() == 0) {
            textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), "No leaderboard has been created. Please do at least 1 playthrough.");
            float x = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
            textureManager.getGameSmallFont().draw(batch, textureManager.getMainLayout(), x, startY);
        } else {
            float y = startY;
            for (String line : leaderboardLines) {
                if (count<11){
                    String[] parts = line.split(" - ");
                    String[] scoreParts = parts[1].split("-");
                    textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), count + ". " + parts[0] + " - " + scoreParts[0] + " (" + scoreParts[1] + ")");
                    float x = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
                    textureManager.getGameSmallFont().draw(batch, textureManager.getMainLayout(), x, y);
                    y -= lineSpacing;
                    count++;
                }
            }
        }

        textX=textX+acceleration;
        acceleration=acceleration-1;

        if (acceleration<0) {
            acceleration=0;
        }

        if (textX>1050) {
            textX=1050;
        }

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

    public void addLeaderboardEntry(String name, int score, String difficulty) {
        List<String> newleaderboardLines = new ArrayList<>();
        System.out.println("trying to add" + name + " - " + score + "-" + difficulty);
        Boolean hasLineBeenAdded = false;
        try (BufferedReader br = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                String[] parts = line.split(" - ");
                String[] scoreParts = parts[1].split("-");
                int lineScore = Integer.parseInt(scoreParts[0]);
                if (lineScore <= score && !hasLineBeenAdded) {
                    newleaderboardLines.add(name + " - " + score + "-" + difficulty);
                    hasLineBeenAdded = true;
                }
                newleaderboardLines.add(line);
            }
            if (!hasLineBeenAdded) {
                newleaderboardLines.add(name + " - " + score + "-" + difficulty);
            }
        } catch (IOException e) {
            leaderboardLines.clear();
            leaderboardLines.add("Error reading leaderboard.");
        }

        try {
            if (!leaderboardFile.exists()) {
                leaderboardFile.createNewFile();
            }
            java.io.FileWriter writer = new java.io.FileWriter(leaderboardFile, false);
            for (String line : newleaderboardLines) {
                System.out.println(line);
                writer.write(line + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLeaderboard() {
        leaderboardLines.clear();

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
