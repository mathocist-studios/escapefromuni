package com.mathochiststudios.escapefromuni.Menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mathochiststudios.escapefromuni.TextureManager;
import com.mathochiststudios.escapefromuni.UI.Mouse;

public class EndGameMenu extends AbstractMenuLegacy {

    // Parallax background variables
    private static final float SCROLL_SPEED = 60f;
    private float parallaxOffset = 0f;
    private final Texture backgroundLayer1;
    private final Texture backgroundLayer2;
    private final Texture backgroundLayer3;
    private final Texture backgroundLayer4;

    // Animation variables
    private final Animation<TextureRegion> walkAnimation;
    private TextureRegion[] walkFrames;
    private float stateTime = 0f;
    private final Sprite sprite;

    // Credits variables
    private float creditsOffset = 0f;
    private static final float CREDITS_SCROLL_SPEED = 30f;
    private static final String[][] creditsLines = {
        {"big", "Developed by Mathochist Studios"},
        {"big", "Developers:"},
        {"small", " - Aiden Turner"},
        {"small", " - Marcus Williamson"},
        {"big", "Requirements Analysis and Design:"},
        {"small", " - Charlie Thoo-tinsley"},
        {"big", "Risk assessment and Testing:"},
        {"small", " - Harri Thorman"},
        {"big", "User Testing:"},
        {"small", " - Will King"},
        {"big", "Method Selection and Project Management:"},
        {"small", " - Josh Zacek"},
        {"small", " - Euan Cottam"},
        {"big", "Special Thanks to Team 9 for their initial work on the project!"},
        {"big", "Thank you for playing Escape From Uni!"}
    };

    private String nextMenu = "EndMenu";
    private double buttonCooldown = 0;

    public EndGameMenu(SpriteBatch batch,
                           FitViewport viewport,
                           int latestScore,
                           boolean wonLastGame,
                           boolean buttonCD,
                           Mouse mouse,
                           TextureManager textureManager
    ) {
        super(batch, viewport, latestScore, wonLastGame, buttonCD, mouse, textureManager);

        backgroundLayer1 = new Texture("road_parallax_layer_1.png");
        backgroundLayer2 = new Texture("road_parallax_layer_2.png");
        backgroundLayer3 = new Texture("road_parallax_layer_3.png");
        backgroundLayer4 = new Texture("road_parallax_layer_4.png");

        this.sprite = new Sprite(new Texture("yellow_bus_driving.png"));

        this.populateFrames();
        this.walkAnimation = new Animation<>(0.05f, this.walkFrames);
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
        return this.nextMenu;
    }

    /**
     * Computes a positive modulo, ensuring the result is always non-negative.
     * <br>
     * Primarily used for wrapping offsets in parallax scrolling.
     *
     * @param value   The value to be wrapped.
     * @param modulus The modulus to wrap against.
     * @return A non-negative result of value mod modulus.
     */
    private static float positiveMod(float value, float modulus) {
        float m = value % modulus;
        return m < 0 ? m + modulus : m;
    }

    private void drawParallaxBackground() {
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        if (width <= 0f) return; // guard against divide-by-zero / invalid viewport

        // accumulate offset (no modulo here)
        parallaxOffset += Gdx.graphics.getDeltaTime() * SCROLL_SPEED;
        // optionally clamp to avoid huge values accumulating over very long runs
        if (parallaxOffset > 1e6f) parallaxOffset -= 1e6f;

        // compute per-layer offsets from the running offset and wrap per-layer
        float o4 = positiveMod(parallaxOffset * 0.2f, width);
        float o3 = positiveMod(parallaxOffset * 0.4f, width);
        float o2 = positiveMod(parallaxOffset * 0.4f, width);

        batch.draw(backgroundLayer4, -o4, 0, width, height);
        batch.draw(backgroundLayer4, -o4 + width, 0, width, height);

        batch.draw(backgroundLayer3, -o3, 0, width, height);
        batch.draw(backgroundLayer3, -o3 + width, 0, width, height);

        batch.draw(backgroundLayer2, -o2, 0, width, height);
        batch.draw(backgroundLayer2, -o2 + width, 0, width, height);

    }

    private void drawParallaxForeground() {
        float width = viewport.getWorldWidth();
        float height = viewport.getWorldHeight();

        if (width <= 0f) return;

        // compute per-layer offsets from the running offset and wrap per-layer
        float o1 = positiveMod(parallaxOffset * 0.8f, width);

        batch.draw(backgroundLayer1, -o1, 0, width, height);
        batch.draw(backgroundLayer1, -o1 + width, 0, width, height);
    }

    private void drawCredits() {
        float startY = -160; // Starting Y position for the first credit line
        creditsOffset += Gdx.graphics.getDeltaTime() * CREDITS_SCROLL_SPEED;

        textureManager.getMainLayout().setText(textureManager.getShopFont(), "You Escaped!");
        float titleWidth = textureManager.getMainLayout().width;
        float titleX = (viewport.getWorldWidth() - titleWidth) / 2f;
        float titleY = creditsOffset;
        textureManager.getShopFont().draw(batch, "You Escaped!", titleX, titleY);

        textureManager.getMainLayout().setText(textureManager.getShopFont(), "Your Score: " + this.latestScore);
        titleWidth = textureManager.getMainLayout().width;
        titleX = (viewport.getWorldWidth() - titleWidth) / 2f;
        titleY = -60 + creditsOffset;
        textureManager.getShopFont().draw(batch, "Your Score: " + this.latestScore, titleX, titleY);

        // Loop through credits lines and draw them
        for (int i = 0; i < creditsLines.length; i++) {
            float y = startY - (i * 60) + creditsOffset;
            if (y < -20) continue; // Skip lines that are off-screen at the bottom
            if (y > viewport.getWorldHeight() + 20) continue; // Skip lines that are off-screen at the top

            BitmapFont fontToUse = creditsLines[i][0].equals("big") ? textureManager.getShopFont() : textureManager.getGameSmallFont();

            textureManager.getMainLayout().setText(fontToUse, creditsLines[i][1]);
            float textWidth = textureManager.getMainLayout().width;
            float x = (viewport.getWorldWidth() - textWidth) / 2f;
            fontToUse.draw(batch, creditsLines[i][1], x, y);

        }
    }

    private void drawTextOverlay() {

        // draw press 'E' to continue at bottom right
        String prompt = "Press 'E' to continue";
        BitmapFont fontToUse = textureManager.getGameSmallFont();
        textureManager.getMainLayout().setText(fontToUse, prompt);
        float textWidth = textureManager.getMainLayout().width;
        float x = viewport.getWorldWidth() - textWidth - 20f;
        float y = 40f;
        fontToUse.draw(batch, prompt, x, y);

    }

    @Override
    public void draw() {
        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time

        // Setup for new frame
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.E) && System.currentTimeMillis() - this.buttonCooldown > 5000) {
            System.out.println(System.currentTimeMillis() - this.buttonCooldown);
            this.nextMenu = "Main";
            return;
        }

        batch.begin();
        drawParallaxBackground();

        // Draw animated bus in the center
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        batch.draw(
            currentFrame,
            100,
            200,
            600,
            300
        );

        drawCredits();
        drawParallaxForeground();

        if (System.currentTimeMillis() - this.buttonCooldown > 5000) {
            drawTextOverlay();
        }
        batch.end();
    }

    // Populates walkFrames.
    private void populateFrames() {
        int ssCols = 4;
        int ssRows = 1;

        // Use the sprite's texture (which was provided as walkSheet in the constructor)
        Texture spriteSheetTexture = this.sprite.getTexture();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheetTexture, spriteSheetTexture.getWidth() / ssCols, spriteSheetTexture.getHeight() / ssRows);
        // walkFrames setup.
        this.walkFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 4; i ++) {
            this.walkFrames[index++] = tmp[0][i];
        }
    }

    public void onSwitchIn() {
        this.buttonCooldown = System.currentTimeMillis();
        this.nextMenu = "EndMenu";
        this.creditsOffset = 0f;
        this.parallaxOffset = 0f;
        this.stateTime = 0f;
    }

}
