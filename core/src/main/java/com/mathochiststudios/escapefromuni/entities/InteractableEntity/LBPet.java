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
 * A pet that follows the player around after being summoned.
 */
public class LBPet extends InteractableEntity {

    private final Animation<TextureRegion> stationaryAnimation;
    private final Animation<TextureRegion> walkAnimation;

    // Player starts standing still.
    private TextureRegion[] stationaryFrames;
    private TextureRegion[] walkFrames;

    private float stateTime = 0f;
    private final Sprite sprite;

    private float[] objectivePoint;
    private boolean isVisible = false;

    private TextureRegion currentFrame;

    public LBPet(Game game, float x, float y, float interactionRadius, float[] objectivePoint) {
        super(game, new Texture("duck_spritesheet.png"), x, y, 1, 1, interactionRadius, true);

        this.objectivePoint = objectivePoint;
        this.sprite = new Sprite(new Texture("duck_spritesheet.png"));

        this.populateFrames();
        this.stationaryAnimation = new Animation<>(0.1f, this.stationaryFrames);
        this.walkAnimation = new Animation<>(0.1f, this.walkFrames);

        this.currentFrame = stationaryAnimation.getKeyFrame(0);
    }

    @Override
    public void update(float deltaTime, Level level) {

    }

    @Override
    public void onInteract(Player p, Level level) {

    }

    @Override
    public void withinInteractionRadius(Player p, Level level, SpriteBatch batch) {

    }

    // Update method to move the pet towards the objective point
    // added to made it easier to unit test the pet movement
    public void update() {
        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time

        // move the bus towards the objective point
        float petX = getEntityX();
        float petY = getEntityY();
        float targetX = objectivePoint[0];
        float targetY = objectivePoint[1];
        float deltaX = targetX - petX;
        float deltaY = targetY - petY;
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        TextureRegion currentFrame;

        if (distance > 1.5f) { // threshold to stop
            float moveX = (deltaX / distance) * game.getPlayer().getSpeed() * Gdx.graphics.getDeltaTime();
            float moveY = (deltaY / distance) * game.getPlayer().getSpeed() * Gdx.graphics.getDeltaTime();
            setEntityX(petX + moveX);
            setEntityY(petY + moveY);
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = stationaryAnimation.getKeyFrame(stateTime, true);

        }

        boolean moveDirectionRight = deltaX >= 0;
        if (!moveDirectionRight && !currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        } else if (moveDirectionRight && currentFrame.isFlipX()) {
            currentFrame.flip(true, false);
        }

        this.currentFrame = currentFrame;
    }

    @Override
    public void render(SpriteBatch batch) {

        if (!isVisible) {
            return;
        }

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
        int ssCols = 6;
        int ssRows = 4;

        // Use the sprite's texture (which was provided as walkSheet in the constructor)
        Texture spriteSheetTexture = this.getSprite().getTexture();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheetTexture, spriteSheetTexture.getWidth() / ssCols, spriteSheetTexture.getHeight() / ssRows);
        // stationaryFrames setup.
        this.stationaryFrames = new TextureRegion[4];
        int index = 0;
        for (int i = 0; i < 4; i ++) {
            this.stationaryFrames[index++] = tmp[2][i];
        }
        // walkFrames setup.
        this.walkFrames = new TextureRegion[6];
        index = 0;
        for (int i = 0; i < 6; i ++) {
            this.walkFrames[index++] = tmp[3][i];
        }
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setObjectivePoint(float[] objectivePoint) {
        this.objectivePoint = objectivePoint;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

}
