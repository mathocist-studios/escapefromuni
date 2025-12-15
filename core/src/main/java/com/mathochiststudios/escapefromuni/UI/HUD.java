package com.mathochiststudios.escapefromuni.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationManager;
import com.mathochiststudios.escapefromuni.entities.Player;

public class HUD {

    private FitViewport uiViewport;
    private OrthographicCamera uiCamera;

    private Texture emptyMinimapIcon;
    private Texture playerMinimapIcon;
    private float minimapBottomHeight;

    private Player player;
    private Game game;

    private NotificationManager notificationManager;

    public HUD(Game game, Player player) {

        this.game = game;
        this.player = player;

        uiCamera = new OrthographicCamera();
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);
        uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        emptyMinimapIcon = new Texture("emptyminimap.png");
        playerMinimapIcon = new Texture("occupiedminimap.png");
        minimapBottomHeight = 22;

        notificationManager = new NotificationManager();

    }

    public FitViewport getUiViewport() {
        return uiViewport;
    }

    public OrthographicCamera getUiCamera() {
        return uiCamera;
    }

    public Texture getEmptyMinimapIcon() {
        return emptyMinimapIcon;
    }

    public Texture getPlayerMinimapIcon() {
        return playerMinimapIcon;
    }

    public float getMinimapBottomHeight() {
        return minimapBottomHeight;
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public void render(SpriteBatch spriteBatch) {
        // We use a separate ui viewport / camera as the game's resolution is too low to write any text.
        uiViewport.apply();
        spriteBatch.setProjectionMatrix(uiCamera.combined);

        spriteBatch.begin();

        this.drawUI(spriteBatch);
        this.drawMinimap(spriteBatch);

        spriteBatch.end();

        notificationManager.render(spriteBatch, uiCamera);
    }


    private void drawUI(SpriteBatch spriteBatch) {
        // Format the time as mm:ss from the second remaining
        String tempSecs = ""+(player.getGameTimer().getSecsRemaining()%60);
        if (tempSecs.length() == 1) {
            tempSecs = "0"+tempSecs;
        }
        String tempMins = "0"+(player.getGameTimer().getSecsRemaining()/60);

        // Draw the formatted timer at the top center of the screen
        game.getTextureManager().getMainLayout().setText(game.getTextureManager().getGameLargeFont(), tempMins+":"+tempSecs);
        float tempx = (uiViewport.getWorldWidth() - game.getTextureManager().getMainLayout().width) / 2f;
        game.getTextureManager().getGameLargeFont().draw(spriteBatch, game.getTextureManager().getMainLayout(), tempx, 900);

        // Draw the coin counter at the top center of the screen, under the timer
        game.getTextureManager().getMainLayout().setText(game.getTextureManager().getGameSmallFont(), "Coins: "+player.getCoins());
        tempx = (uiViewport.getWorldWidth() - game.getTextureManager().getMainLayout().width) / 2f;
        game.getTextureManager().getGameSmallFont().draw(spriteBatch, game.getTextureManager().getMainLayout(), tempx, 820);

        // Draw all the event counters
        game.getTextureManager().getGameSmallFont().draw(spriteBatch, "Positive Events: " + player.getEventsCounter().getPositiveEventsEncountered(), 20, 900);
        game.getTextureManager().getGameSmallFont().draw(spriteBatch, "Negative Events: " + player.getEventsCounter().getNegativeEventsEncountered(), 20, 850);
        game.getTextureManager().getGameSmallFont().draw(spriteBatch, "Hidden Events: " + player.getEventsCounter().getHiddenEventsEncountered(), 20, 800);
    }

    private void drawMinimap(SpriteBatch spriteBatch) {
        for (int i = 0; i < game.getLevels().size(); i++){
            float h = minimapBottomHeight+i*game.getMinimapTileSize();
            game.getLevels().get(i).getMinimapSprite().setY(h);
            if (game.getLevels().get(i).getSideLevel() != null) {
                game.getLevels().get(i).getSideLevel().getMinimapSprite().setY(h);
                game.getLevels().get(i).getSideLevel().getMinimapSprite().draw(spriteBatch);
            }
            if (game.getLevels().get(i).getSide2Level() != null) {
                game.getLevels().get(i).getSide2Level().getMinimapSprite().setY(h);
                game.getLevels().get(i).getSide2Level().getMinimapSprite().draw(spriteBatch);
            }
            game.getLevels().get(i).getMinimapSprite().draw(spriteBatch);
        }
    }

}
