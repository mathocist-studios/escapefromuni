package com.mathochiststudios.escapefromuni.entities.Enemy;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyMoveDirection;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public class Dean extends Enemy {

    private final Animation<TextureRegion> stationaryAnimation;
    private final Animation<TextureRegion> upAnimation;
    private final Animation<TextureRegion> downAnimation;
    private final Animation<TextureRegion> rightAnimation;

    // Player starts standing still.
    private EnemyMoveDirection moveDirection = EnemyMoveDirection.STATIONARY;
    private TextureRegion[] stationaryFrames;
    private TextureRegion[] upFrames;
    private TextureRegion[] downFrames;
    private TextureRegion[] rightFrames;

    private float stateTime = 0f;

    public Dean(Game game, Texture walkSheet, float x, float y, EnemyAI aiType) {
        super(game, walkSheet, x, y, aiType);

        this.populateFrames();
        this.stationaryAnimation = new Animation<>(0.1f, this.stationaryFrames);
        this.upAnimation = new Animation<>(0.025f, this.upFrames);
        this.downAnimation = new Animation<>(0.025f, this.downFrames);
        this.rightAnimation = new Animation<>(0.025f, this.rightFrames);

        // Set the sprite's region to the first stationary frame so the sprite draws only the frame
        this.getSprite().setRegion(this.stationaryAnimation.getKeyFrame(0f, true));
        // Ensure sprite flip is reset
        this.getSprite().setFlip(false, false);
        // Ensure sprite is sized to match the enemy's collision box (world units)
        if (this.getEnemyCollision() != null) {
            this.getSprite().setSize(this.getEnemyCollision().width, this.getEnemyCollision().height);
        }

        setSpeed(game.getGameDifficulty().getDeanSpeed());
    }

    @Override
    public void triggerCollisionBehavior(Player player) {
        // On collision with the player, send them back to the previous level and penalize time
        // since dean is in the basement the previous level is the final library level
        this.game.switchToLevel(game.currentLevel.getPrevLevel(), "Side");
        player.getGameTimer().addTime(-15.0f);
        player.getEventsCounter().caughtByDean();
        Notification notification = new Notification(
            "The Dean caught you in the basement! (-15s)",
            5.0f,
            NotificationType.SPEECH,
            game.getTextureManager().getGameSmallFont()
        );
        game.getHud().getNotificationManager().addNotification(notification);
        this.setDead(true); // Dean deactivates after catching the player
    }

    @Override
    public void isMoving(EnemyMoveDirection direction) {

        if (direction != this.moveDirection) {
            this.stateTime = 0f; // Reset state time on direction change
        }

        this.moveDirection = direction;

        // Determine the current frame based on direction and state time
        TextureRegion currentFrame;
        boolean flipX = switch (direction) {
            case STATIONARY -> {
                currentFrame = this.stationaryAnimation.getKeyFrame(stateTime, true);
                // ensure not flipped
                yield false;
            }
            case DOWN -> {
                currentFrame = this.downAnimation.getKeyFrame(stateTime, true);
                yield false;
            }
            case UP -> {
                currentFrame = this.upAnimation.getKeyFrame(stateTime, true);
                yield false;
            }
            case RIGHT -> {
                currentFrame = this.rightAnimation.getKeyFrame(stateTime, true);
                yield false;
            }
            case LEFT -> {
                // For LEFT, use the RIGHT animation frame but flip the sprite horizontally.
                currentFrame = this.rightAnimation.getKeyFrame(stateTime, true);
                yield true;
            }
            default -> {
                currentFrame = this.stationaryAnimation.getKeyFrame(stateTime, true);
                yield false;
            }
        };

        // Update the sprite's texture region to draw just the current frame
        this.getSprite().setRegion(currentFrame);
        // Apply flip after setting the region so region-setting doesn't overwrite flip state
        this.getSprite().setFlip(flipX, false);
        // Keep sprite size consistent with collision box (region doesn't change sprite size)
        if (this.getEnemyCollision() != null) {
            this.getSprite().setSize(this.getEnemyCollision().width, this.getEnemyCollision().height);
        }

    }

    @Override
    public void localUpdate(float deltaTime, Level currentLevel, Player player) {
        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time
    }

    // Populates stationaryFrames, upFrames, downFrames and rightFrames.
    private void populateFrames() {
        int ssCols = 4;
        int ssRows = 12;

        // Use the sprite's texture (which was provided as walkSheet in the constructor)
        Texture spriteSheetTexture = this.getSprite().getTexture();
        TextureRegion[][] tmp = TextureRegion.split(spriteSheetTexture, spriteSheetTexture.getWidth() / ssCols, spriteSheetTexture.getHeight() / ssRows);
        // stationaryFrames setup.
        this.stationaryFrames = new TextureRegion[2];
        int index = 0;
        for (int i = 0; i < 2; i ++) {
            this.stationaryFrames[index++] = tmp[0][i];
        }
        // upFrames setup.
        this.upFrames = new TextureRegion[4];
        index = 0;
        for (int i = 0; i < 4; i ++) {
            this.upFrames[index++] = tmp[5][i];
        }
        // downFrames setup.
        this.downFrames = new TextureRegion[4];
        index = 0;
        for (int i = 0; i < 4; i ++) {
            this.downFrames[index++] = tmp[3][i];
        }
        // rightFrames setup.
        this.rightFrames = new TextureRegion[4];
        index = 0;
        for (int i = 0; i < 4; i ++) {
            this.rightFrames[index++] = tmp[4][i];
        }
    }

}
