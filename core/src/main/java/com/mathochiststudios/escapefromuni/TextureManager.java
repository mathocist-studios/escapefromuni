package com.mathochiststudios.escapefromuni;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoadRuntimeException;
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

    private Texture playButtonTexture;
    private Texture returnToMenuButtonTexture;
    private Texture resumeButtonTexture;


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

        try {
            this.generateFonts();
            gameLargeFont = this.genMainFont(90);
            gameSmallFont = this.genMainFont(30);
            gameMediumFont = this.genMainFont(50);
        } catch (SharedLibraryLoadRuntimeException e) {
            mainFont = null;
            shopFont = null;
            gameLargeFont = null;
            gameSmallFont = null;
            gameMediumFont = null;
        }

        menuText = new Texture("escapefromunititle.png");
        pausedText = new Texture("pausedtext.png");

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

    public Music getBgm() {
        return bgm;
    }

    public void setBgm(Music bgm) {
        this.bgm = bgm;
    }

    public void dispose() {
        // Dispose of all textures, sprites, fonts, and other graphical assets here
        if (mainFont != null) {
            mainFont.dispose();
            shopFont.dispose();
        }

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
