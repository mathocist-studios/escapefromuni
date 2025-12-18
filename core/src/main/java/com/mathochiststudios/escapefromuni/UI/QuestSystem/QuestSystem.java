package com.mathochiststudios.escapefromuni.UI.QuestSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mathochiststudios.escapefromuni.Game;

import java.util.ArrayList;
import java.util.Stack;

public class QuestSystem {

    private final Stack<Quest> mainQuests;
    private final ArrayList<Quest> sideQuests;

    private final Game game;

    public QuestSystem(Game game) {
        mainQuests = new Stack<>();
        sideQuests = new ArrayList<>();

        this.game = game;
    }

    public void addMainQuest(Quest quest) {
        if (mainQuests.isEmpty()) {
            mainQuests.push(quest);
            return;
        }
        if (quest.getLevel() <= mainQuests.peek().getLevel()) {
            return;
        }
        mainQuests.push(quest);
    }

    public void addSideQuest(Quest quest) {
        // check if side quest already exists
        for (Quest q : sideQuests) {
            if (q.getTitle().equals(quest.getTitle())) {
                return;
            }
        }
        sideQuests.add(quest);
    }

    public void update() {
        if (!mainQuests.isEmpty() && mainQuests.peek().isCompleted(game)) {
            mainQuests.pop();
        }

        sideQuests.removeIf(quest -> quest.isCompleted(game));
    }

    public Quest getCurrentMainQuest() {
        return mainQuests.isEmpty() ? null : mainQuests.peek();
    }

    public ArrayList<Quest> getSideQuests() {
        return sideQuests;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera) {

        BitmapFont font = game.getTextureManager().getGameSmallFont();

        GlyphLayout layout = new GlyphLayout(font, "Quests");
        float y = camera.viewportHeight - 20;

        batch.begin();
        font.setColor(1, 1, 0, 1);
        font.draw(batch, "Quests", camera.viewportWidth - layout.width - 20, y);
        y -= layout.height + 10;
        font.setColor(1, 1, 1, 1);
        if (getCurrentMainQuest() != null) {
            layout.setText(font, "Main: " + getCurrentMainQuest().getTitle());
            font.draw(batch, "Main: " + getCurrentMainQuest().getTitle(), camera.viewportWidth - layout.width - 20, y);
            y -= layout.height + 10;
        } else {
            layout.setText(font, "Main: None");
            font.draw(batch, "Main: None", camera.viewportWidth - layout.width - 20, y);
            y -= layout.height + 10;
        }
        for (Quest quest : sideQuests) {
            layout.setText(font, "Side: " + quest.getTitle());
            font.draw(batch, "Side: " + quest.getTitle(), camera.viewportWidth - layout.width - 20, y);
            y -= layout.height + 10;
        }
        batch.end();

    }

}
