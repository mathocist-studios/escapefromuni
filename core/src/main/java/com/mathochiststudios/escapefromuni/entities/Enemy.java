package com.mathochiststudios.escapefromuni.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.badlogic.gdx.audio.Sound;

import java.util.List;
import java.util.Objects;

public class Enemy {


    protected Texture texture;
    public Sound soundEffect;
    protected float enemyX;
    protected float enemyY;
    protected Rectangle enemyCollision;
    protected Boolean isDead = false;
    protected Sprite enemySprite;
    protected EnemyAI enemyAI;
    protected Boolean showText = false;
    protected float speechMaxLength = 1f;
    protected float speechDuration;
    protected Texture speechTexture;

    private Game game;

    // Takes in basic enemy info with a default size of 1, 1
    public Enemy(Game game, Texture texture, Sound sound, float x, float y, EnemyAI aiType) {

        this.game = game;

        this.enemyX = x;
        this.enemySprite = new Sprite(texture);
        this.enemyY = y;
        this.texture = texture;
        this.soundEffect = sound;
        this.enemyCollision = new Rectangle(enemyX, enemyY, 6, 1);
        this.enemyAI = aiType;

        if (Objects.equals(this.enemyAI, EnemyAI.DUCK)) {
            this.enemySpeech(game.getTextureManager().getDuckSpeechBubbleTexture());
        }
    }

    public void render(SpriteBatch batch) {
        if (isDead) {
            return;
        }
        batch.draw(texture, enemyX, enemyY, 1, 1);
    }

    // Generates the speech bubble sprite for talking enemies
    // changed to make better for future use :AIDEN
    public void renderSpeech(SpriteBatch batch) {
        if (batch == null || isDead || !showText) {
            return;
        }

        Texture tex = this.speechTexture;
        // Fallback to texture manager only if speechTexture wasn't set
        if (tex == null && game != null && game.getTextureManager() != null) {
            if (Objects.equals(this.enemyAI, EnemyAI.DUCK)) {
                tex = game.getTextureManager().getDuckSpeechBubbleTexture();
            }
            // add other AI-specific fallbacks here if needed
        }

        if (tex == null) {
            return; // nothing to draw
        }

        // Per-AI placement/size defaults (tweak values to fit your world units)
        float offsetX = 0.5f;
        float offsetY = 1.1f;
        float width = 3f;
        float height = 2f;

        if (Objects.equals(this.enemyAI, EnemyAI.DUCK)) {
            offsetX = 1f;
            offsetY = 1f;
            width = 5f;
            height = 5f;
        }

        batch.draw(tex, this.enemyX + offsetX, this.enemyY + offsetY, width, height);
    }


    public void collect() {
        isDead = true;
    }

    public Boolean isDead() {
        return isDead;
    }

    public void deleteEnemy() {
        texture.dispose();
    }

    public Rectangle getCollider() {
        return enemyCollision;
    }

    public Sprite getSprite() {
        return enemySprite;
    }

    public EnemyAI getAIType() {
        return enemyAI;
    }

    public Boolean getShowText() {
        return this.showText;
    }

    // Despawns the duck when requirements are met
    public void enemyBehaviour1() {
        if (Game.Score >= 10) {
            soundEffect.play();
            isDead = true;
            texture.dispose(); // needs to be edited to the birdseed code for buying bird seeds in the shop
            return;
        }
        game.getTextureManager().getDuckSound2().play();
    }

    // Sets the speech bubble and timer for an enemy's speech
    public void enemySpeech(Texture speechTexture) {
            this.speechTexture = speechTexture;
            this.speechDuration = 0;
            //this.showText = true;
    }

    public void speechTimeCheck(float delta) {
        speechDuration += delta;
        if (speechDuration >= speechMaxLength) {
            showText = false;
        }
    }

    public void enableShowText() {
        this.showText = true;
        this.speechDuration = 0;
    }

    //Reverts player position and performs enemy behaviour, on enemy collision
//    public static void enemyCollisionLogic(float oldX, float oldY, Player player) {
//        for (Enemy enemy : Game.currentLevel.getLevelEnemies()) {
//            if (!(enemy.isDead()) && player.getMoneyRectangle().overlaps(enemy.getCollider())) {
//                enemy.enableShowText();
//                if (Objects.equals(enemy.getAIType(), EnemyAI.DUCK)) {
//                    enemy.enemyBehaviour1();
//                    player.getMoneySprite().setPosition(oldX, oldY);
//                    player.getMoneyRectangle().setPosition(oldX, oldY);
//                }
//            }
//        }
//    }

    //Improved null-safety, caches references, reverts the player's money position safely,
    // triggers enemy behavior after reverting, and stops after the first valid collision to
    // avoid multiple reactions. :AIDEN
    public static void enemyCollisionLogic(float oldX, float oldY, Player player) {
        if (player == null || Game.currentLevel == null) {
            return;
        }

        Rectangle moneyRect = player.getMoneyRectangle();
        if (moneyRect == null) {
            return;
        }

        List<Enemy> enemies = Game.currentLevel.getLevelEnemies();
        if (enemies == null || enemies.isEmpty()) {
            return;
        }

        for (Enemy enemy : enemies) {
            if (enemy == null || enemy.isDead()) {
                continue;
            }
            Rectangle enemyCollider = enemy.getCollider();
            if (enemyCollider == null) {
                continue;
            }

            if (moneyRect.overlaps(enemyCollider)) {
                enemy.enableShowText();

                // Revert player money sprite/rectangle position safely
                if (player.getMoneySprite() != null) {
                    try {
                        player.getMoneySprite().setPosition(oldX, oldY);
                    } catch (Exception ignored) { }
                }
                try {
                    player.getMoneyRectangle().setPosition(oldX, oldY);
                } catch (Exception ignored) { }

                // Handle AI-specific behaviour after reverting position
                if (Objects.equals(enemy.getAIType(), EnemyAI.DUCK)) {
                    try {
                        enemy.enemyBehaviour1();
                    } catch (Exception ignored) { }
                }

                // Only handle the first overlapping enemy to avoid multiple reactions
                break;
            }
        }
    }

}
