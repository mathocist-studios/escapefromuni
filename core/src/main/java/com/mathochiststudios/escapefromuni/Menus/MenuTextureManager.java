package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.SharedLibraryLoadRuntimeException;
import com.badlogic.gdx.utils.viewport.FitViewport;



/**
 * TextureManager is responsible for managing and loading all textures, sprites, fonts,
 * and other graphical assets used in the game.
 *
 * This was added to improve code organization and separate graphical asset management
 * since the last team's Main class was horrible.
 */
public class MenuTextureManager {

    private BitmapFont mainFont;
    private GlyphLayout mainLayout;

    private BitmapFont gameLargeFont;
    private BitmapFont gameSmallFont;
    private BitmapFont gameMediumFont;

    private Sprite playButtonSprite;
    private Texture playButtonTexture;
    private Sprite creditsButtonSprite;
    private Texture creditsButtonTexture;
    private Sprite uncreditsButtonSprite;
    private Texture uncreditsButtonTexture;
    private Sprite settingsButtonSprite;
    private Texture settingsButtonTexture;
    private Sprite exitButtonSprite;
    private Texture exitButtonTexture;
    private Sprite leaderboardButtonSprite;
    private Texture leaderboardButtonTexture;
    private Sprite MenuBackdropSprite;
    private Texture MenuBackdropTexture;

    private BitmapFont shopFont;

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

    private Sprite hoverSubmitSprite;
    private Texture hoverSubmitTexture;
    private Sprite SubmitSprite;
    private Texture SubmitTexture;
    private Sprite RightArrowSprite;
    private Texture RightArrowTexture;
    private Sprite LeftArrowSprite;
    private Texture LeftArrowTexture;

    private Sprite TwoRightArrowSprite;
    private Texture TwoRightArrowTexture;
    private Sprite TwoLeftArrowSprite;
    private Texture TwoLeftArrowTexture;

    private Sprite nextButtonSprite;
    private Texture nextButtonTexture;
    private Sprite hovernextButtonSprite;
    private Texture hovernextButtonTexture;

    private Texture menuText;
    private Texture pausedText;

    public MenuTextureManager(FitViewport viewport) {

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

        TutorialTexture = new Texture("Tutorial.png");
        TutorialSprite = new Sprite(TutorialTexture);
        TutorialSprite.setSize(1280, 960);
        TutorialSprite.setPosition(0,0);

        playButtonTexture = new Texture("playButton.png");
        playButtonSprite = new Sprite(playButtonTexture);
        playButtonSprite.setSize(700, 100);
        playButtonSprite.setPosition(100,500);

        creditsButtonTexture = new Texture("creditsButton.png");
        creditsButtonSprite = new Sprite(creditsButtonTexture);
        creditsButtonSprite.setSize(700, 100);
        creditsButtonSprite.setPosition(100,200);

        uncreditsButtonTexture = new Texture("uncreditsButton.png");
        uncreditsButtonSprite = new Sprite(uncreditsButtonTexture);
        uncreditsButtonSprite.setSize(700, 100);
        uncreditsButtonSprite.setPosition(100,100);

        exitButtonTexture = new Texture("exitButton.png");
        exitButtonSprite = new Sprite(exitButtonTexture);
        exitButtonSprite.setSize(700, 100);
        exitButtonSprite.setPosition(100,100);

        startButtonTexture = new Texture("startButton.png");
        startButtonSprite = new Sprite(startButtonTexture);
        startButtonSprite.setSize(700, 100);
        startButtonSprite.setPosition(80,100);

        unstartButtonTexture = new Texture("unstartButton.png");
        unstartButtonSprite = new Sprite(unstartButtonTexture);
        unstartButtonSprite.setSize(700, 100);
        unstartButtonSprite.setPosition(80,100);

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

        nextButtonTexture = new Texture("next.png");
        nextButtonSprite = new Sprite(nextButtonTexture);
        nextButtonSprite.setSize(300, 100);
        nextButtonSprite.setPosition(900,20);

        hovernextButtonTexture = new Texture("hovernext.png");
        hovernextButtonSprite = new Sprite(nextButtonTexture);
        hovernextButtonSprite.setSize(300, 100);
        hovernextButtonSprite.setPosition(900,20);

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
        hoverRestartSprite.setPosition(240+80,300);

        SubmitTexture = new Texture("Submit.png");
        SubmitSprite = new Sprite(SubmitTexture);
        SubmitSprite.setSize(300, 100);
        SubmitSprite.setPosition(600+80,580);

        hoverSubmitTexture = new Texture("hoverSubmit.png");
        hoverSubmitSprite = new Sprite(hoverSubmitTexture);
        hoverSubmitSprite.setSize(300, 100);
        hoverSubmitSprite.setPosition(600,580);

        RightArrowTexture = new Texture("RightArrow.png");
        RightArrowSprite = new Sprite(RightArrowTexture);
        RightArrowSprite.setSize(100, 100);
        RightArrowSprite.setPosition(400+400+30,370);

        LeftArrowTexture = new Texture("LeftArrow.png");
        LeftArrowSprite = new Sprite(LeftArrowTexture);
        LeftArrowSprite.setSize(100, 100);
        LeftArrowSprite.setPosition(400+30,370);

        TwoRightArrowTexture = new Texture("RightArrow.png");
        TwoRightArrowSprite = new Sprite(RightArrowTexture);
        TwoRightArrowSprite.setSize(100, 100);
        TwoRightArrowSprite.setPosition(400+200, 800-640);

        TwoLeftArrowTexture = new Texture("LeftArrow.png");
        TwoLeftArrowSprite = new Sprite(LeftArrowTexture);
        TwoLeftArrowSprite.setSize(100, 100);
        TwoLeftArrowSprite.setPosition(400+30,800-640);

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
    }

    public MenuTextureManager(Sprite LeftArrowSprite, Texture LeftArrowTexture, Sprite RightArrowSprite, Texture RightArrowTexture) {
        this.LeftArrowSprite = LeftArrowSprite;
        this.LeftArrowTexture = LeftArrowTexture;
        this.RightArrowSprite = RightArrowSprite;
        this.RightArrowTexture = RightArrowTexture;
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

    public BitmapFont getGameLargeFont() {
        return gameLargeFont;
    }

    public BitmapFont getGameMediumFont() {
        return gameMediumFont;
    }

    public BitmapFont getGameSmallFont() {
        return gameSmallFont;
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
        if (mainFont != null) {
            mainFont.dispose();
            shopFont.dispose();
        }

        playButtonTexture.dispose();
        returnToMenuButtonTexture.dispose();
        resumeButtonTexture.dispose();
        menuText.dispose();
        pausedText.dispose();
    }

    public Sprite getHoverSubmitSprite() {
    return hoverSubmitSprite;
    }

    public Texture getHoverSubmitTexture() {
        return hoverSubmitTexture;
    }

    public Sprite getSubmitSprite() {
        return SubmitSprite;
    }

    public Texture getSubmitTexture() {
        return SubmitTexture;
    }

    public Sprite getRightArrowSprite() {
        return RightArrowSprite;
    }

    public Texture getRightArrowTexture() {
        return RightArrowTexture;
    }

    public Sprite getLeftArrowSprite() {
        return LeftArrowSprite;
    }

    public Texture getLeftArrowTexture() {
        return LeftArrowTexture;
    }

    public Sprite getTwoRightArrowSprite() {
        return TwoRightArrowSprite;
    }

    public Texture getTwoRightArrowTexture() {
        return TwoRightArrowTexture;
    }

    public Sprite getTwoLeftArrowSprite() {
        return TwoLeftArrowSprite;
    }

    public Texture getTwoLeftArrowTexture() {
        return TwoLeftArrowTexture;
    }

    public Sprite getNextButtonSprite() {
        return nextButtonSprite;
    }

    public Texture getNextButtonTexture() {
        return nextButtonTexture;
    }

    public Sprite gethoverNextButtonSprite() {
        return hovernextButtonSprite;
    }

    public Texture gethoverNextButtonTexture() {
        return hovernextButtonTexture;
    }

    public BitmapFont getShopFont() {
        return shopFont;
    }

    public void setShopFont(BitmapFont shopfont) {
        this.shopFont = shopfont;
    }

    public Sprite getuncreditsButtonSprite() {
        return uncreditsButtonSprite;
    }

    public Texture getuncreditsButtonTexture() {
        return uncreditsButtonTexture;
    }

    public Sprite getcreditsButtonSprite() {
        return creditsButtonSprite;
    }

    public Texture getcreditsButtonTexture() {
        return creditsButtonTexture;
    }

}
