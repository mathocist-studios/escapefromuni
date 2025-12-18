package com.mathochiststudios.escapefromuni.entities.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyMoveDirection;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.BirdSeed;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public class Duck extends Enemy {

    private Animation<TextureRegion> stationaryAnimation;
    private Animation<TextureRegion> walkAnimation;

    // Player starts standing still.
    private EnemyMoveDirection moveDirection = EnemyMoveDirection.STATIONARY;
    private TextureRegion[] stationaryFrames;
    private TextureRegion[] walkFrames;

    private float stateTime = 0f;
    private BirdSeed birdSeed;

    private boolean isLeft = false;

    public Duck(Game game, Texture walkSheet, float x, float y, EnemyAI aiType, BirdSeed birdSeed) {
        super(game, walkSheet, x, y, aiType);

        this.birdSeed = birdSeed;

        this.populateFrames();
        this.stationaryAnimation = new Animation<>(0.1f, this.stationaryFrames);
        this.walkAnimation = new Animation<>(0.025f, this.walkFrames);

        this.spriteScale = 0.5;

        // Set the sprite's region to the first stationary frame so the sprite draws only the frame
        this.getSprite().setRegion(this.stationaryAnimation.getKeyFrame(0f, true));
        // Ensure sprite flip is reset
        this.getSprite().setFlip(false, false);
        // Ensure sprite is sized to match the enemy's collision box (world units)
        if (this.getEnemyCollision() != null) {
            this.getSprite().setSize(this.getEnemyCollision().getWidth(), this.getEnemyCollision().getHeight());
            this.getSprite().setOriginCenter();
        }

        setSpeed(1.5f);
    }

    @Override
    public void triggerCollisionBehavior(Player player) {}

    @Override
    public void isMoving(EnemyMoveDirection direction) {

//        if (direction != this.moveDirection) {
//            this.stateTime = 0f; // Reset state time on direction change
//        }

        this.moveDirection = direction;

        TextureRegion currentFrame;
        boolean flipX;

        switch (direction) {
            case STATIONARY:
                currentFrame = this.stationaryAnimation.getKeyFrame(stateTime, true);
                // ensure not flipped
                flipX = false;
                break;
            case DOWN:
                currentFrame = this.walkAnimation.getKeyFrame(stateTime, true);
                flipX = isLeft;
                break;
            case UP:
                currentFrame = this.walkAnimation.getKeyFrame(stateTime, true);
                flipX = isLeft;
                break;
            case RIGHT:
                currentFrame = this.walkAnimation.getKeyFrame(stateTime, true);
                flipX = false;
                isLeft = false;
                break;
            case LEFT:
                // For LEFT, use the RIGHT animation frame but flip the sprite horizontally.
                currentFrame = this.walkAnimation.getKeyFrame(stateTime, true);
                flipX = true;
                isLeft = true;
                break;
            default:
                currentFrame = this.stationaryAnimation.getKeyFrame(stateTime, true);
                flipX = false;
                break;
        }

        // Update the sprite's texture region to draw just the current frame
        this.getSprite().setRegion(currentFrame);
        // Apply flip after setting the region so region-setting doesn't overwrite flip state
        this.getSprite().setFlip(flipX, false);
        // Keep sprite size consistent with collision box (region doesn't change sprite size)
        if (this.getEnemyCollision() != null) {
            this.getSprite().setSize(this.getEnemyCollision().getWidth(), this.getEnemyCollision().getHeight());
            this.getSprite().setOriginCenter();
        }

    }

    @Override
    public void localUpdate(float deltaTime, Level currentLevel, Player player) {
        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time
    }

    // Populates stationaryFrames, upFrames, downFrames and rightFrames.
    private void populateFrames() {
        int ssCols = 6;
        int ssRows = 4;

        // Use the sprite's texture (which was provided as walkSheet in the constructor)
        Texture spriteSheetTexture = this.getSprite().getTexture();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheetTexture, spriteSheetTexture.getWidth() / ssCols, spriteSheetTexture.getHeight() / ssRows);
        // stationaryFrames setup.
        this.stationaryFrames = new TextureRegion[2];
        int index = 0;
        for (int i = 0; i < 2; i ++) {
            this.stationaryFrames[index++] = tmp[0][i];
        }
        // walkFrames setup.
        this.walkFrames = new TextureRegion[6];
        index = 0;
        for (int i = 0; i < 6; i ++) {
            this.walkFrames[index++] = tmp[1][i];
        }
    }

    public void setBirdSeed(BirdSeed birdSeed) { // used to update the birdseed reference when placed down
        this.birdSeed = birdSeed;
    }

    public BirdSeed getBirdSeed() {
        return this.birdSeed;
    }

}
