package com.mathochiststudios.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.UI.ShopUI;


/**
 * TextureManager is responsible for managing and loading all textures, sprites, fonts,
 * and other graphical assets used in the game.
 *
 * This was added to improve code organization and separate graphical asset management
 * since the last team's Main class was horrible.
 */
public class TextureManager {

    private BitmapFont mainFont;
    private GlyphLayout mainLayout;

    private BitmapFont gameLargeFont;
    private BitmapFont gameSmallFont;
    private BitmapFont gameMediumFont;

    private Sprite playButtonSprite;
    private Texture playButtonTexture;
    private Sprite settingsButtonSprite;
    private Texture settingsButtonTexture;
    private Sprite exitButtonSprite;
    private Texture exitButtonTexture;
    private Sprite leaderboardButtonSprite;
    private Texture leaderboardButtonTexture;
    private Sprite MenuBackdropSprite;
    private Texture MenuBackdropTexture;

    private Sprite unplayButtonSprite;
    private Texture unplayButtonTexture;
    private Sprite unsettingsButtonSprite;
    private Texture unsettingsButtonTexture;
    private Sprite unexitButtonSprite;
    private Texture unexitButtonTexture;
    private Sprite unleaderboardButtonSprite;
    private Texture unleaderboardButtonTexture;
    private Sprite unMenuBackdropSprite;
    private Texture unMenuBackdropTexture;
    private Sprite OverlaySprite;
    private Texture OverlayTexture;

    private Sprite startButtonSprite;
    private Texture startButtonTexture;
    private Sprite unstartButtonSprite;
    private Texture unstartButtonTexture;
    private Sprite TutorialSprite;
    private Texture TutorialTexture;

    private Sprite RestartSprite;
    private Texture RestartTexture;
    private Sprite returnToMenuButtonSprite;
    private Texture returnToMenuButtonTexture;
    private Sprite resumeButtonSprite;
    private Texture resumeButtonTexture;
    private Sprite hoverRestartSprite;
    private Texture hoverRestartTexture;
    private Sprite hoverreturnToMenuButtonSprite;
    private Texture hoverreturnToMenuButtonTexture;
    private Sprite hoverresumeButtonSprite;
    private Texture hoverresumeButtonTexture;

    private Texture menuText;
    private Texture pausedText;

    private ShopUI shopUIObject = new ShopUI();
    private Texture shopIconTexture;
    private Sprite shopIconSprite;
    private Texture buyEDTexture;
    private Sprite  buyEDSprite;
    private Texture buyBFTexture;
    private Sprite buyBFSprite;

    private BitmapFont shopFont;

    private Sound coinSound;
    private Sprite coinSprite;
    private Texture coinTexture;
    private Sound planetSound;
    private Sprite planetSprite;
    private Texture planetTexture;
    private Sound duckSound;
    private Sound duckSound2;
    private Texture duckTexture;
    private Texture duckSpeechBubbleTexture;

    public Music bgm;

    public TextureManager(FitViewport viewport) {

        // Initialize textures, sprites, fonts, and other graphical assets here
        mainLayout = new GlyphLayout();

        generateFonts();

        menuText = new Texture("escapefromunititle.png");
        pausedText = new Texture("pausedtext.png");

        TutorialTexture = new Texture("Tutorial.png");
        TutorialSprite = new Sprite(TutorialTexture);
        TutorialSprite.setSize(1280, 960);
        TutorialSprite.setPosition(0,0);

        playButtonTexture = new Texture("playButton.png");
        playButtonSprite = new Sprite(playButtonTexture);
        playButtonSprite.setSize(700, 100);
        playButtonSprite.setPosition(100,500);

        exitButtonTexture = new Texture("exitButton.png");
        exitButtonSprite = new Sprite(exitButtonTexture);
        exitButtonSprite.setSize(700, 100);
        exitButtonSprite.setPosition(100,200);

        startButtonTexture = new Texture("startButton.png");
        startButtonSprite = new Sprite(startButtonTexture);
        startButtonSprite.setSize(700, 100);
        startButtonSprite.setPosition(100,100);

        unstartButtonTexture = new Texture("unstartButton.png");
        unstartButtonSprite = new Sprite(unstartButtonTexture);
        unstartButtonSprite.setSize(700, 100);
        unstartButtonSprite.setPosition(100,100);

        settingsButtonTexture = new Texture("settingsButton.png");
        settingsButtonSprite = new Sprite(settingsButtonTexture);
        settingsButtonSprite.setSize(700, 100);
        settingsButtonSprite.setPosition(100,400);

        leaderboardButtonTexture = new Texture("leaderboardButton.png");
        leaderboardButtonSprite = new Sprite(leaderboardButtonTexture);
        leaderboardButtonSprite.setSize(700, 100);
        leaderboardButtonSprite.setPosition(100,300);

        unplayButtonTexture = new Texture("unplayButton.png");
        unplayButtonSprite = new Sprite(unplayButtonTexture);
        unplayButtonSprite.setSize(700, 100);
        unplayButtonSprite.setPosition(100,500);

        unexitButtonTexture = new Texture("unexitButton.png");
        unexitButtonSprite = new Sprite(unexitButtonTexture);
        unexitButtonSprite.setSize(700, 100);
        unexitButtonSprite.setPosition(100,200);

        unsettingsButtonTexture = new Texture("unsettingsButton.png");
        unsettingsButtonSprite = new Sprite(unsettingsButtonTexture);
        unsettingsButtonSprite.setSize(700, 100);
        unsettingsButtonSprite.setPosition(100,400);

        unleaderboardButtonTexture = new Texture("unleaderboardButton.png");
        unleaderboardButtonSprite = new Sprite(unleaderboardButtonTexture);
        unleaderboardButtonSprite.setSize(700, 100);
        unleaderboardButtonSprite.setPosition(100,300);

        returnToMenuButtonTexture = new Texture("returntext.png");
        returnToMenuButtonSprite = new Sprite(returnToMenuButtonTexture);
        returnToMenuButtonSprite.setSize(300, 100);
        returnToMenuButtonSprite.setPosition(240,100);

        resumeButtonTexture = new Texture("resumetext.png");
        resumeButtonSprite = new Sprite(resumeButtonTexture);
        resumeButtonSprite.setSize(300, 100);
        resumeButtonSprite.setPosition(240,500);

        hoverresumeButtonTexture = new Texture("hoverresumetext.png");
        hoverresumeButtonSprite = new Sprite(hoverresumeButtonTexture);
        hoverresumeButtonSprite.setSize(300, 100);
        hoverresumeButtonSprite.setPosition(240,500);

        MenuBackdropTexture = new Texture("MenuBackdrop.png");
        MenuBackdropSprite = new Sprite(MenuBackdropTexture);
        MenuBackdropSprite.setSize((float) 1280 /3, 200);
        MenuBackdropSprite.setPosition((float) 1280 /3,330);

        OverlayTexture = new Texture("Overlay.png");
        OverlaySprite = new Sprite(OverlayTexture);
        OverlaySprite.setSize((float) 1280 /3, 200);
        OverlaySprite.setPosition((float) 1280 /3,330);

        RestartTexture = new Texture("Restart.png");
        RestartSprite = new Sprite(RestartTexture);
        RestartSprite.setSize(300, 100);
        RestartSprite.setPosition(240,300);

        hoverRestartTexture = new Texture("hoverRestart.png");
        hoverRestartSprite = new Sprite(hoverRestartTexture);
        hoverRestartSprite.setSize(300, 100);
        hoverRestartSprite.setPosition(240,300);

        hoverreturnToMenuButtonTexture = new Texture("hoverreturntext.png");
        hoverreturnToMenuButtonSprite = new Sprite(hoverreturnToMenuButtonTexture);
        hoverreturnToMenuButtonSprite.setSize(300, 100);
        hoverreturnToMenuButtonSprite.setPosition(240,100);

        hoverresumeButtonTexture = new Texture("hoverresumetext.png");
        hoverresumeButtonSprite = new Sprite(hoverresumeButtonTexture);
        hoverresumeButtonSprite.setSize(300, 100);
        hoverresumeButtonSprite.setPosition(240,500);


        //seperate shop UI
        shopIconTexture = new Texture("shop290x248.png");
        shopIconSprite = new Sprite(shopIconTexture);
        shopIconSprite.setSize( 290, 248);
        shopIconSprite.setPosition(800, 700);

        buyEDTexture = new Texture("BuyEnergyDrink.png");
        buyEDSprite = new Sprite(buyEDTexture);
        //buyEDSprite.setSize(290,120);
        buyEDSprite.setPosition(viewport.getWorldWidth()/2, 450);

        buyBFTexture = new Texture("BuyBirdFeed.png");
        buyBFSprite = new Sprite(buyBFTexture);
        //buyBFSprite.setSize(290,120);
        buyBFSprite.setPosition(viewport.getWorldWidth()/2, 250);

        gameLargeFont = this.genMainFont(90);
        gameSmallFont = this.genMainFont(30);
        gameMediumFont = this.genMainFont(50);

        coinSound = Gdx.audio.newSound(Gdx.files.internal("coin-drop-422703.mp3"));
        coinTexture = new Texture("Custom_coin_sprite.png");
        coinSprite = new Sprite(coinTexture);

        planetSound = Gdx.audio.newSound(Gdx.files.internal("laser-90052.mp3"));
        planetTexture = new Texture("Neptune_planet_v2.png");
        planetSprite = new Sprite(planetTexture);

        duckSound = Gdx.audio.newSound(Gdx.files.internal("duck-quack-112941.mp3"));
        duckSound2 = Gdx.audio.newSound(Gdx.files.internal("short-beep-351721.mp3"));
        duckTexture = new Texture("custom_duck.png");
        duckSpeechBubbleTexture = new Texture("Custom_speech_bubble_v2.png");

        bgm = Gdx.audio.newMusic(Gdx.files.internal("EscapeAdventure.ogg"));
    }

    public BitmapFont genMainFont(int size) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Jersey25-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.borderWidth = 1;
        parameter.borderColor = com.badlogic.gdx.graphics.Color.BLACK;
        parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
        parameter.magFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear; // smooth scaling up
        parameter.minFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear; // smooth scaling down
        BitmapFont tempFont = generator.generateFont(parameter);
        generator.dispose();
        return tempFont;
    }

    private void generateFonts() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/Jersey25-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 128;
        parameter.borderWidth = 1;
        parameter.borderColor = com.badlogic.gdx.graphics.Color.BLACK;
        parameter.color = com.badlogic.gdx.graphics.Color.WHITE;
        parameter.magFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear; // smooth scaling up
        parameter.minFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear; // smooth scaling down
        mainFont = generator.generateFont(parameter);
        parameter.size = 40;
        shopFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public BitmapFont getMainFont() {
        return mainFont;
    }

    public void setMainFont(BitmapFont mainFont) {
        this.mainFont = mainFont;
    }

    public GlyphLayout getMainLayout() {
        return mainLayout;
    }

    public void setMainLayout(GlyphLayout mainLayout) {
        this.mainLayout = mainLayout;
    }

    public Sprite getPlayButtonSprite() {
        return playButtonSprite;
    }

    public void setPlayButtonSprite(Sprite playButtonSprite) {
        this.playButtonSprite = playButtonSprite;
    }

    public Texture getPlayButtonTexture() {
        return playButtonTexture;
    }

    public void setPlayButtonTexture(Texture playButtonTexture) {
        this.playButtonTexture = playButtonTexture;
    }

    public Sprite getReturnToMenuButtonSprite() {
        return returnToMenuButtonSprite;
    }

    public void setReturnToMenuButtonSprite(Sprite returnToMenuButtonSprite) {
        this.returnToMenuButtonSprite = returnToMenuButtonSprite;
    }

    public Texture getReturnToMenuButtonTexture() {
        return returnToMenuButtonTexture;
    }

    public void setReturnToMenuButtonTexture(Texture returnToMenuButtonTexture) {
        this.returnToMenuButtonTexture = returnToMenuButtonTexture;
    }

    public Sprite getResumeButtonSprite() {
        return resumeButtonSprite;
    }

    public void setResumeButtonSprite(Sprite resumeButtonSprite) {
        this.resumeButtonSprite = resumeButtonSprite;
    }

    public Texture getResumeButtonTexture() {
        return resumeButtonTexture;
    }

    public void setResumeButtonTexture(Texture resumeButtonTexture) {
        this.resumeButtonTexture = resumeButtonTexture;
    }

    public Texture getMenuText() {
        return menuText;
    }

    public void setMenuText(Texture menuText) {
        this.menuText = menuText;
    }

    public Texture getPausedText() {
        return pausedText;
    }

    public void setPausedText(Texture pausedText) {
        this.pausedText = pausedText;
    }

    public ShopUI getShopUIObject() {
        return shopUIObject;
    }

    public void setShopUIObject(ShopUI shopUIObject) {
        this.shopUIObject = shopUIObject;
    }

    public Texture getShopIconTexture() {
        return shopIconTexture;
    }

    public void setShopIconTexture(Texture shopIconTexture) {
        this.shopIconTexture = shopIconTexture;
    }

    public Sprite getShopIconSprite() {
        return shopIconSprite;
    }

    public void setShopIconSprite(Sprite shopIconSprite) {
        this.shopIconSprite = shopIconSprite;
    }

    public Texture getBuyEDTexture() {
        return buyEDTexture;
    }

    public void setBuyEDTexture(Texture buyEDTexture) {
        this.buyEDTexture = buyEDTexture;
    }

    public Sprite getBuyEDSprite() {
        return buyEDSprite;
    }

    public void setBuyEDSprite(Sprite buyEDSprite) {
        this.buyEDSprite = buyEDSprite;
    }

    public Texture getBuyBFTexture() {
        return buyBFTexture;
    }

    public void setBuyBFTexture(Texture buyBFTexture) {
        this.buyBFTexture = buyBFTexture;
    }

    public Sprite getBuyBFSprite() {
        return buyBFSprite;
    }

    public void setBuyBFSprite(Sprite buyBFSprite) {
        this.buyBFSprite = buyBFSprite;
    }

    public BitmapFont getShopFont() {
        return shopFont;
    }

    public void setShopFont(BitmapFont shopfont) {
        this.shopFont = shopfont;
    }

    public BitmapFont getGameLargeFont() {
        return gameLargeFont;
    }

    public BitmapFont getGameMediumFont() {
        return gameMediumFont;
    }

    public BitmapFont getGameSmallFont() {
        return gameSmallFont;
    }

    public Sound getCoinSound() {
        return coinSound;
    }

    public Sprite getCoinSprite() {
        return coinSprite;
    }

    public Texture getCoinTexture() {
        return coinTexture;
    }

    public Sound getPlanetSound() {
        return planetSound;
    }

    public Sprite getPlanetSprite() {
        return planetSprite;
    }

    public Texture getPlanetTexture() {
        return planetTexture;
    }

    public Sound getDuckSound() {
        return duckSound;
    }

    public Sound getDuckSound2() {
        return duckSound2;
    }

    public Texture getDuckTexture() {
        return duckTexture;
    }

    public Texture getDuckSpeechBubbleTexture() {
        return duckSpeechBubbleTexture;
    }

    public Texture getunPlayButtonTexture() {
        return unplayButtonTexture;
    }

    public Sprite getunPlayButtonSprite() {
        return unplayButtonSprite;
    }

    public Texture getExitButtonTexture() {
        return exitButtonTexture;
    }

    public Sprite getExitButtonSprite() {
        return exitButtonSprite;
    }

    public Texture getSettingsButtonTexture() {
        return settingsButtonTexture;
    }

    public Sprite getSettingsButtonSprite() {
        return settingsButtonSprite;
    }

    public Sprite getLeaderboardButtonSprite() {
        return leaderboardButtonSprite;
    }

    public Texture getLeaderboardButtonTexture() {
        return leaderboardButtonTexture;
    }

    public Sprite getStartButtonSprite() {
        return startButtonSprite;
    }

    public Texture getStartButtonTexture() {
        return startButtonTexture;
    }

    public Sprite getunStartButtonSprite() {
        return unstartButtonSprite;
    }

    public Texture getunStartButtonTexture() {
        return unstartButtonTexture;
    }

    public Sprite getTutorialSprite() {
        return TutorialSprite;
    }

    public Texture getTutorialTexture() {
        return TutorialTexture;
    }

    public Sprite getMenuBackdropSprite() {
        return MenuBackdropSprite;
    }

    public Texture getMenuBackdropTexture() {
        return MenuBackdropTexture;
    }
    
    public Texture getunExitButtonTexture() {
        return unexitButtonTexture;
    }

    public Sprite getunExitButtonSprite() {
        return unexitButtonSprite;
    }

    public Texture getunSettingsButtonTexture() {
        return unsettingsButtonTexture;
    }

    public Sprite getunSettingsButtonSprite() {
        return unsettingsButtonSprite;
    }

    public Sprite getunLeaderboardButtonSprite() {
        return unleaderboardButtonSprite;
    }

    public Texture getunLeaderboardButtonTexture() {
        return unleaderboardButtonTexture;
    }

    public Sprite getunMenuBackdropSprite() {
        return unMenuBackdropSprite;
    }

    public Texture getunMenuBackdropTexture() {
        return unMenuBackdropTexture;
    }

    public Music getBgm() {
        return bgm;
    }

    public Sprite getRestartSprite() {
        return RestartSprite;
    }

    public Texture getRestartTexture() {
        return RestartTexture;
    }

    public Sprite gethoverRestartSprite() {
        return hoverRestartSprite;
    }

    public Texture gethoverRestartTexture() {
        return hoverRestartTexture;
    }

    public Sprite gethoverReturnToMenuButtonSprite() {
        return hoverreturnToMenuButtonSprite;
    }

    public Texture gethoverReturnToMenuButtonTexture() {
        return hoverreturnToMenuButtonTexture;
    }

    public Sprite gethoverResumeButtonSprite() {
        return hoverresumeButtonSprite;
    }

    public Texture hovergetResumeButtonTexture() {
        return hoverresumeButtonTexture;
    }

    public Sprite getOverlaySprite() {
        return OverlaySprite;
    }

    public Texture getOverlayTexture() {
        return OverlayTexture;
    }

    public void dispose() {
        // Dispose of all textures, sprites, fonts, and other graphical assets here
        mainFont.dispose();
        shopFont.dispose();
        playButtonTexture.dispose();
        returnToMenuButtonTexture.dispose();
        resumeButtonTexture.dispose();
        menuText.dispose();
        pausedText.dispose();
        shopIconTexture.dispose();
        buyEDTexture.dispose();
        buyBFTexture.dispose();
        coinSound.dispose();
        coinTexture.dispose();
        planetSound.dispose();
        planetTexture.dispose();
        duckSound.dispose();
        duckSound2.dispose();
        duckTexture.dispose();
        duckSpeechBubbleTexture.dispose();
        bgm.dispose();
    }

}
