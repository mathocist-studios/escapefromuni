package com.mathochiststudios.escapefromuni.levels;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.Notification;
import com.mathochiststudios.escapefromuni.UI.NotificationSystem.NotificationType;
import com.mathochiststudios.escapefromuni.entities.Enemy.EnemyAI.EnemyAI;
import com.mathochiststudios.escapefromuni.entities.Enemy.Friend;
import com.mathochiststudios.escapefromuni.entities.InteractableEntity.Bus;
import com.mathochiststudios.escapefromuni.entities.Player;

import java.util.ArrayList;

public class BusLevel extends Level {

    private final Friend friend;
    private final Bus bus;

    public BusLevel(Game game) {
        super(game);

        mapName = "maps/BusLevel.tmx";

        levelCoins = new ArrayList<>();
        levelSpeedPowerups = new ArrayList<>();
        levelEnemies = new ArrayList<>();
        levelInteractableEntities = new ArrayList<>();

        startX = 1;
        startY = 17;

        endX = 2;
        endY = 3;

        sideX = 1;
        sideY = 15;

        side2X = 33;
        side2Y = 2;

        friend = new Friend(
            this.getGame(),
            new Texture("prototype_character.png"),
            1,
            20,
            EnemyAI.FRIEND
        );
        levelEnemies.add(friend);
        friend.setDead(true);

        bus = new Bus(this.getGame(), -10, 5, 3f, new float[]{7f, 5f});
        levelInteractableEntities.add(bus);

        levelCoins = super.generateLevelCoins(new int[][]{{2, 23}, {14, 28}}); // this is just better

    }

    @Override
    public void update(float deltaTime, Player player) {
        this.friend.update(deltaTime, this, player);
        this.bus.update(deltaTime, this);
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    public boolean collides(Player player) {
        return false;
    }

    @Override
    public void onEnter(TiledMapTileLayer mapCollisionLayer, ArrayList<Rectangle> mapCollisions, Player p) {

        if (!p.getEventsCounter().hasMadeItToBusStop()) {
            Notification notification = new Notification(
                "Make it to the bus",
                5f,
                NotificationType.ACHIEVEMENT,
                this.getGame().getTextureManager().getGameSmallFont()
            );
            this.getGame().getHud().getNotificationManager().addNotification(notification);
        }

        p.getEventsCounter().madeItToBusStop();

        if (!p.getEventsCounter().hasFoundFriend()) {
            return;
        }

        if (this.getGame().friendFollowing && this.getGame().friendLocation != this) {
            this.getGame().friendLocation = this;
            this.friend.setEnemyX(p.getMoneySprite().getX());
            this.friend.setEnemyY(p.getMoneySprite().getY() + 1.0f);
            this.friend.setDead(false);
            return;
        }

        this.friend.setDead(this.getGame().friendLocation != this);

    }

}
