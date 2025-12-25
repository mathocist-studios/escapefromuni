package com.mathochiststudios.escapefromuni.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.mathochiststudios.escapefromuni.Tests.IStage;

public class TextBox{
    private TextField textField;
    private Texture background;
    private Texture cursor;
    private Texture selection;

    public TextBox(IStage stage, float x, float y, float width, float height) {

        BitmapFont font = new BitmapFont();

        background = createTexture((int) width, (int) height, Color.DARK_GRAY);
        cursor = createTexture(2, (int) height - 10, Color.WHITE);
        selection = createTexture(1, 1, Color.BLUE);

        TextField.TextFieldStyle style = new TextField.TextFieldStyle();
        style.font = font;
        style.fontColor = Color.WHITE;
        style.background = new TextureRegionDrawable(background);
        style.cursor = new TextureRegionDrawable(cursor);
        style.selection = new TextureRegionDrawable(selection);

        textField = new TextField("", style);
        textField.setBounds(x, y, width, height);
        textField.setMessageText("Type here...");

        stage.addActor(textField);
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public void setPosition(float x, float y) {
        textField.setPosition(x, y);
    }

    public void setSize(float width, float height) {
        textField.setSize(width, height);
    }

    public void setMaxLength(int length) {
        textField.setMaxLength(length);
    }

    public void setPasswordMode(char maskChar) {
        textField.setPasswordMode(true);
        textField.setPasswordCharacter(maskChar);
    }

    public TextField getActor() {
        return textField;
    }

    public void dispose() {
        background.dispose();
        cursor.dispose();
        selection.dispose();
    }

    private Texture createTexture(int width, int height, Color color) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
