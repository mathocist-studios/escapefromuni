package com.mathochiststudios.escapefromuni.Menus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Tests.ISpriteBatch;
import com.mathochiststudios.escapefromuni.UI.Mouse;
import com.mathochiststudios.escapefromuni.entities.Utils.Leaderboard;

public class LeaderboardMenu extends AbstractMenu{

    private List<String> leaderboardLines = new ArrayList<>();
    String path = System.getProperty("user.dir");
    File leaderboardFile = new File(path, "leaderboards.txt");
    private Leaderboard leaderboard;

    public LeaderboardMenu(ISpriteBatch batch,
                           FitViewport viewport,
                           boolean buttonCD,
                           Mouse mouse,
                           MenuTextureManager textureManager
    ) {
        super(batch, viewport, buttonCD, mouse, textureManager);
        leaderboard = new Leaderboard();
        leaderboard.loadLeaderboard();
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

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        batch.draw(textureManager.getMenuBackdropSprite(),0,0, 1280, 960);

        textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), "Leaderboard");
        float titleX = 400;
        float titleY = 800;
        textureManager.getGameLargeFont().draw(batch, "Leaderboard" , titleX+40, titleY+50);

        float startY = titleY - 60;
        float lineSpacing = 48f;
        int count = 1;
        if (leaderboard.getLeaderboardLines().size() == 0) {
            textureManager.getMainLayout().setText(textureManager.getGameSmallFont(), "No leaderboard has been created. Please do at least 1 playthrough.");
            float x = (Gdx.graphics.getWidth() - textureManager.getMainLayout().width) / 2f;
            textureManager.getGameSmallFont().draw(batch, textureManager.getMainLayout(), x, startY);
        } else {
            float y = startY;
            for (String line : leaderboard.getLeaderboardLines()) {
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
        leaderboard.addLeaderboardEntry(name, score, difficulty);
    }
}
