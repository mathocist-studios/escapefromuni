package com.mathochiststudios.escapefromuni.entities.InteractableEntity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

/**
 * Bus entity that the player can interact with to win the game.
 * Using in the bus level and moves towards the bus stop automatically.
 */
public class Bus extends InteractableEntity {

    private final Animation<TextureRegion> stationaryAnimation;
    private final Animation<TextureRegion> walkAnimation;

    // Player starts standing still.
    private TextureRegion[] stationaryFrames;
    private TextureRegion[] walkFrames;

    private float stateTime = 0f;
    private final Sprite sprite;

    private final Texture hintTexture;
    private final float[] objectivePoint;
    private final float busSpeed = 2.0f;
    private boolean isMoving = true;
    private TextureRegion currentFrame;

    public Bus(Game game, float x, float y, float interactionRadius, float[] objectivePoint) {
        super(game, new Texture("yellow_bus_driving.png"), x, y, 10, 5, interactionRadius, true);

        this.objectivePoint = objectivePoint;
        this.sprite = new Sprite(new Texture("yellow_bus_driving.png"));

        this.hintTexture = new Texture("E_key.png");

        this.populateFrames();
        this.stationaryAnimation = new Animation<>(0.1f, this.stationaryFrames);
        this.walkAnimation = new Animation<>(0.05f, this.walkFrames);
        this.currentFrame = walkAnimation.getKeyFrame(0);
    }

    @Override
    public void onInteract(Player p, Level level) {
        if (isMoving) {
            return;
        }

        getGame().gameEnded = true;
        getGame().WinOrLose = "Win";
        getGame().getMainApp().setMenuState("EndMenu");
    }

    @Override
    public void withinInteractionRadius(Player p, Level level, SpriteBatch batch) {
        if (isMoving) {
            return;
        }

        batch.draw(
            hintTexture,
            getEntityX() + (getEntityWidth() - 1) / 2,
            (float) (getEntityY() + 0.5 * Math.sin(System.currentTimeMillis() / 200.0)),
            1,
            1
        );
    }

    @Override
    public void update(float deltaTime, Level level) {
        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time

        // move the bus towards the objective point
        float busX = getEntityX();
        float busY = getEntityY();
        float targetX = objectivePoint[0];
        float targetY = objectivePoint[1];
        float deltaX = targetX - busX;
        float deltaY = targetY - busY;
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        TextureRegion currentFrame;

        if (distance > 0.1f) { // threshold to stop
            float moveX = (deltaX / distance) * busSpeed * Gdx.graphics.getDeltaTime();
            float moveY = (deltaY / distance) * busSpeed * Gdx.graphics.getDeltaTime();
            setEntityX(busX + moveX);
            setEntityY(busY + moveY);
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        } else {
            isMoving = false;
            currentFrame = stationaryAnimation.getKeyFrame(stateTime, true);
        }

        this.currentFrame = currentFrame;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(
            currentFrame,
            getEntityX(),
            getEntityY(),
            getEntityWidth(),
            getEntityHeight()
        );
    }

    // Populates stationaryFrames, upFrames, downFrames and rightFrames.
    private void populateFrames() {
        int ssCols = 4;
        int ssRows = 1;

        // Use the sprite's texture (which was provided as walkSheet in the constructor)
        Texture spriteSheetTexture = this.getSprite().getTexture();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheetTexture, spriteSheetTexture.getWidth() / ssCols, spriteSheetTexture.getHeight() / ssRows);
        // stationaryFrames setup.
        this.stationaryFrames = new TextureRegion[1];
        int index = 0;
        for (int i = 0; i < 1; i ++) {
            this.stationaryFrames[index++] = tmp[0][i];
        }
        // walkFrames setup.
        this.walkFrames = new TextureRegion[4];
        index = 0;
        for (int i = 0; i < 4; i ++) {
            this.walkFrames[index++] = tmp[0][i];
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

}
