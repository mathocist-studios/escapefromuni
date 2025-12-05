package com.mathochiststudios.escapefromuni.levels;

import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.entities.Enemy;
import com.mathochiststudios.escapefromuni.entities.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Player;
import com.mathochiststudios.escapefromuni.powerups.SpeedPowerup;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class R06_westToEastLevel extends Level{

    public R06_westToEastLevel(Game game) {
        super(game);

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();

        mapName = "West_to_east_map_v2.tmx";

        startX = 17;
        startY = 28;

        endX = 19;
        endY = 0;

        nextLevel = null;
        prevLevel = null;

        // levelCoins = generateLevelCoins(18, 25, 23, 16, 25, 9);
        levelCoins = super.generateLevelCoins(new int[][]{{18, 25}, {23, 16}, {25, 9}});
        levelSpeedPowerups.add(new SpeedPowerup(
            this.getGame().getTextureManager().getPlanetTexture(),
            this.getGame().getTextureManager().getPlanetSound(),
            33,
            14,
            1.25f,
            300.0f
        ));
        levelEnemies.add(new Enemy(
            this.getGame(),
            this.getGame().getTextureManager().getDuckTexture(),
            this.getGame().getTextureManager().getDuckSound(),
            23,
            6,
            EnemyAI.DUCK
        ));
    }
    public void update(float deltaTime, Player player) {}
    public void draw(SpriteBatch batch) {}
    public boolean collides(Player player) {return false;}
}
