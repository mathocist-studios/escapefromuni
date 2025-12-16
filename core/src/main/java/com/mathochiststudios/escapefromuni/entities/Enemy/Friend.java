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

public class Friend extends Enemy {

    private Animation<TextureRegion> stationaryAnimation;
    private Animation<TextureRegion> upAnimation;
    private Animation<TextureRegion> downAnimation;
    private Animation<TextureRegion> rightAnimation;

    // Player starts standing still.
    private EnemyMoveDirection moveDirection = EnemyMoveDirection.STATIONARY;
    private TextureRegion[] stationaryFrames;
    private TextureRegion[] upFrames;
    private TextureRegion[] downFrames;
    private TextureRegion[] rightFrames;

    private float stateTime = 0f;

    public Friend(Game game, Texture walkSheet, float x, float y, EnemyAI aiType) {
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
    }

    @Override
    public void triggerCollisionBehavior(Player player) {}

    @Override
    public void isMoving(EnemyMoveDirection direction) {

        if (direction != this.moveDirection) {
            this.stateTime = 0f; // Reset state time on direction change
        }

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
                currentFrame = this.downAnimation.getKeyFrame(stateTime, true);
                flipX = false;
                break;
            case UP:
                currentFrame = this.upAnimation.getKeyFrame(stateTime, true);
                flipX = false;
                break;
            case RIGHT:
                currentFrame = this.rightAnimation.getKeyFrame(stateTime, true);
                flipX = false;
                break;
            case LEFT:
                // For LEFT, use the RIGHT animation frame but flip the sprite horizontally.
                currentFrame = this.rightAnimation.getKeyFrame(stateTime, true);
                flipX = true;
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
            this.getSprite().setSize(this.getEnemyCollision().width, this.getEnemyCollision().height);
        }

    }

    @Override
    public void localUpdate(float deltaTime, Level currentLevel, Player player) {
        stateTime += Gdx.graphics.getDeltaTime()*0.25f; // Accumulate elapsed animation time

        double dist_to_friend = Math.hypot(
            (player.getOldMoneyX() - this.getEnemyX()),
            (player.getOldMoneyY() - this.getEnemyY())
        );

        currentLevel.getGame().friendFollowing = dist_to_friend <= 5.0;

        if (!player.getEventsCounter().hasFoundFriend() && currentLevel.getGame().friendFollowing) {
            Notification notification = new Notification(
                "Oh! Hey Player! Are you going to the bus?\nI'm feeling really ill can you help me get there?",
                3,
                NotificationType.SPEECH,
                currentLevel.getGame().getTextureManager().getGameSmallFont()
            );
            currentLevel.getGame().getHud().getNotificationManager().addNotification(notification);
        }

        if (currentLevel.getGame().friendFollowing) {
            player.getEventsCounter().foundFriend();
        }
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
