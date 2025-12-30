package com.mathochiststudios.escapefromuni.entities.Enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyMoveDirection;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.IEnemyAI;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.levels.Level;

public abstract class Enemy {

    private Texture texture;
    private float enemyX;
    private float enemyY;
    protected Rectangle enemyCollision;
    private Boolean isDead = false;
    private final Sprite enemySprite;
    private final EnemyAI enemyAI;

    private IEnemyAI aiBehavior;
    private float SPEED = 5.0f;
    protected double spriteScale = 1.0;

    protected Game game;

    // Takes in basic enemy info with a default size of 1, 1
    public Enemy(Game game, Texture texture, float x, float y, EnemyAI aiType) {

        this.game = game;

        this.enemyX = x;
        this.enemySprite = new Sprite(texture);
        this.enemyY = y;
        this.texture = texture;
        this.enemyCollision = new Rectangle(enemyX, enemyY, 1, 1);
        this.enemyAI = aiType;

        // Instantiate the AI behavior if the enum provides a class
        if (aiType != null && aiType.getAIClass() != null) {
            try {
                this.aiBehavior = aiType.getAIClass().getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                // Failed to instantiate AI; log and fall back to no AI
                e.printStackTrace();
                this.aiBehavior = null;
            }
        } else {
            this.aiBehavior = null;
        }
    }

    public void render(SpriteBatch batch) {
        if (isDead) {
            return;
        }
        batch.draw(enemySprite, enemyX - enemySprite.getWidth() / 2, enemyY - enemySprite.getHeight() / 2, (float) (3 * spriteScale), (float) (3 * spriteScale));
    }

    public void update(float deltaTime, Level currentLevel, Player player) {
        if (isDead) {
            return;
        }
        // If there's an AI behavior instance, let it drive the enemy
        if (aiBehavior != null) {
            EnemyMoveDirection direction = aiBehavior.update(game, this, deltaTime, currentLevel, player, SPEED);
            this.isMoving(direction);
        }

        this.enemyCollision = new Rectangle(enemyX, enemyY, 1, 1);

        this.localUpdate(deltaTime, currentLevel, player);
    }

    public void collect() {
        isDead = true;
    }

    public Boolean isDead() {
        return isDead;
    }

    public void setDead(Boolean dead) {
        isDead = dead;
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

    public abstract void triggerCollisionBehavior(Player player);

    public abstract void isMoving(EnemyMoveDirection direction);

    public abstract void localUpdate(float deltaTime, Level currentLevel, Player player);

    public float getEnemyX() {
        return enemyX;
    }

    public float getEnemyY() {
        return enemyY;
    }

    public void setEnemyX(float x) {
        this.enemyX = x;
    }

    public void setEnemyY(float y) {
        this.enemyY = y;
    }

    public Rectangle getEnemyCollision() {
        return enemyCollision;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    protected void setSpeed(float speed) {
        this.SPEED = speed;
    }

    public EnemyAI getEnemyAI() {
        return enemyAI;
    }

    public IEnemyAI getAiBehavior() {
        return aiBehavior;
    }

}
