package com.mathochiststudios.escapefromuni.UI.NotificationSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class NotificationManager {

    private final ArrayList<Notification> notifications;

    public NotificationManager() {
        notifications = new ArrayList<>();
    }

    public void addNotification(Notification notification) {
        notification.start(System.currentTimeMillis());
        notifications.add(notification);
    }

    private void removeExpiredNotifications() {
        notifications.removeIf(Notification::isExpired);
    }

    public void render(SpriteBatch spriteBatch, OrthographicCamera camera) {
        removeExpiredNotifications();
        for (Notification notification : notifications) {
            notification.render(spriteBatch, camera);
        }
    }

}
