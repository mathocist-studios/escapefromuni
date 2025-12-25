package com.mathochiststudios.escapefromuni.UI.NotificationSystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mathochiststudios.escapefromuni.Tests.HeadlessShapeRenderer;
import com.mathochiststudios.escapefromuni.Tests.IShapeRenderer;
import com.mathochiststudios.escapefromuni.Tests.LiveShapeRenderer;

public class Notification {

    private final String message;
    private final double duration;
    private final NotificationType type;

    private double startTimeMillis = 0;
    private IShapeRenderer shapeRenderer;
    private final BitmapFont font;

    public Notification(String message, double duration, NotificationType type, BitmapFont font) {
        this.message = message;
        this.duration = duration;
        this.type = type;
        try {
            shapeRenderer = new LiveShapeRenderer();
        } catch (GdxRuntimeException e) {
            shapeRenderer = new HeadlessShapeRenderer();
        }
        this.font = font;
    }

    public void start(double currentTime) {
        if (!(startTimeMillis == 0)) {
            return; // already started
        }
        this.startTimeMillis = currentTime;
    }

    public boolean isExpired() {
        if (startTimeMillis == 0) {
            return false; // not started yet
        }
        return System.currentTimeMillis() - startTimeMillis >= duration * 1000L;
    }

    public String getMessage() {
        return message;
    }

    public double getDuration() {
        return duration;
    }

    public NotificationType getType() {
        return type;
    }

    private void render_as_hint(SpriteBatch batch, OrthographicCamera camera, double offsetY) {
        // Specific rendering logic for hint notifications
        // same as speech but with hint icon in the bubble TODO
        render_as_speech(batch, camera, offsetY);
    }

    private void render_as_speech(SpriteBatch batch, OrthographicCamera camera, double offsetY) {

        float opacity = 0.7f;

        // Specific rendering logic for speech notifications
        GlyphLayout layout = new GlyphLayout(font, message);

        // draw speech bubble in center top
        float padding = 40.0f;
        float bubble_width = layout.width + padding * 2;
        float bubble_height = layout.height + padding * 2;
        float bubble_x = (camera.viewportWidth - bubble_width) / 2.0f;
        // float bubble_y = camera.viewportHeight - bubble_height - 40.0f;
        float bubble_y = (float) (40.0 + offsetY); // bottom of the screen
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, opacity);
        shapeRenderer.rect(bubble_x, bubble_y, bubble_width, bubble_height);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, message, bubble_x + padding, bubble_y + bubble_height - padding);
        batch.end();

        // draw carriage return arrow below speech bubble using shapeRenderer
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, opacity);
        float arrow_x = camera.viewportWidth / 2.0f;
        float arrow_y = (float) (bubble_y + 5.0f * Math.sin(System.currentTimeMillis() / 200.0));
        shapeRenderer.triangle(arrow_x - 15, arrow_y + 15, arrow_x + 15, arrow_y + 15, arrow_x, arrow_y);
        shapeRenderer.end();
    }

    private void render_as_achievement(SpriteBatch batch, OrthographicCamera camera) {
        // Specific rendering logic for achievement notifications
        // notif. rendered bottom right like a steam achievement popup

        GlyphLayout layout = new GlyphLayout(font, message);
        float padding = 20.0f;
        float bubble_width = layout.width + padding * 2;
        float bubble_height = layout.height + padding * 2;
        float bubble_x = camera.viewportWidth - bubble_width - 20.0f;

        // slide in from bottom for first 20% of duration then stay for 60% then slide out for last 20%
        float timeElapsed = (float)(System.currentTimeMillis() - startTimeMillis);
        float bubble_y = getAchievementBubbleY(timeElapsed, bubble_height);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(bubble_x, bubble_y, bubble_width, bubble_height);
        shapeRenderer.end();

        batch.begin();
        font.draw(batch, message, bubble_x + padding, bubble_y + bubble_height - padding);
        batch.end();
    }

    private float getAchievementBubbleY(float timeElapsed, float bubble_height) {
        float totalDuration = (float)(duration * 1000L);
        float bubble_y;
        if (timeElapsed < totalDuration * 0.2f) {
            float progress = timeElapsed / (totalDuration * 0.2f);
            bubble_y = -bubble_height + progress * bubble_height;
        } else if (timeElapsed > totalDuration * 0.8f) {
            float progress = (timeElapsed - totalDuration * 0.8f) / (totalDuration * 0.2f);
            bubble_y = -progress * bubble_height;
        } else {
            bubble_y = 0;
        }
        return bubble_y;
    }

    public void render(SpriteBatch batch, OrthographicCamera camera, double offsetY) {
        // Rendering logic based on notification type
        switch (type) {
            case HINT -> render_as_hint(batch, camera, offsetY);
            case SPEECH -> render_as_speech(batch, camera, offsetY);
            case ACHIEVEMENT -> render_as_achievement(batch, camera);
        }
    }

}
