// java
package com.mathochiststudios.escapefromuni.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mathochiststudios.escapefromuni.Game;
import com.mathochiststudios.escapefromuni.ShopStuff.Shop;
import com.mathochiststudios.escapefromuni.Entities.Player;

public class ShopUI {

    Mouse mouse = new Mouse();

    private final ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Stage stage;
    private Table rootTable;
    private Table iconTable;
    private Table contentTable;
    private Image edImage;
    private Image bfImage;
    private Image shopIconImage;

    private double lastClickTime = 0.0;

    public void drawShopMenu(Viewport viewport, SpriteBatch batch, Sprite shopIconSprite, Sprite buyEDSprite, Sprite buyBFSprite, BitmapFont shopfont, GlyphLayout layout) {
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        if (stage == null || stage.getViewport() != viewport) {
            if (stage != null) stage.dispose();
            stage = new Stage(viewport, batch);

            // root table fills the stage; it will contain an iconTable (top-right) and a contentTable (center)
            rootTable = new Table();
            rootTable.setFillParent(true);
            stage.addActor(rootTable);

            iconTable = new Table();
            contentTable = new Table();

            edImage = new Image();
            bfImage = new Image();
            shopIconImage = new Image();
        }

        // world sizes & margins
        float worldW = viewport.getWorldWidth();
        float worldH = viewport.getWorldHeight();
        float marginX = Math.max(10f, worldW * 0.05f);
        float marginY = Math.max(10f, worldH * 0.05f);
        float paddingTop = Math.max(20f, worldH * 0.12f);
        float rowSpacing = Math.max(12f, worldH * 0.06f);

        // background (semi-transparent) in world coords
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0f, 0f, 0f, 0.8f);
        shapeRenderer.rect(marginX, marginY, worldW - marginX * 2f, worldH - marginY * 2f);
        shapeRenderer.end();

        // create drawables (copies) and size them preserving aspect ratio
        SpriteDrawable edDrawable = new SpriteDrawable(new Sprite(buyEDSprite));
        SpriteDrawable bfDrawable = new SpriteDrawable(new Sprite(buyBFSprite));
        SpriteDrawable iconDrawable = new SpriteDrawable(new Sprite(shopIconSprite));

        float buttonTargetWidth = Math.min(worldW * 0.5f, 600f);
        float edScale = buttonTargetWidth / buyEDSprite.getWidth();
        float bfScale = buttonTargetWidth / buyBFSprite.getWidth();
        edDrawable.getSprite().setSize(buttonTargetWidth, buyEDSprite.getHeight() * edScale);
        bfDrawable.getSprite().setSize(buttonTargetWidth, buyBFSprite.getHeight() * bfScale);

        float iconW = Math.min(worldW * 0.12f, 96f);
        float iconScale = iconW / shopIconSprite.getWidth();
        iconDrawable.getSprite().setSize(iconW, shopIconSprite.getHeight() * iconScale);

        edImage.setDrawable(edDrawable);
        edImage.setSize(edDrawable.getSprite().getWidth(), edDrawable.getSprite().getHeight());
        bfImage.setDrawable(bfDrawable);
        bfImage.setSize(bfDrawable.getSprite().getWidth(), bfDrawable.getSprite().getHeight());
        shopIconImage.setDrawable(iconDrawable);
        shopIconImage.setSize(iconDrawable.getSprite().getWidth(), iconDrawable.getSprite().getHeight());

        // label style
        Label.LabelStyle ls = new Label.LabelStyle(shopfont, Color.WHITE);

        // rebuild root/icon/content tables each frame so layout follows viewport
        rootTable.clear();
        iconTable.clear();
        contentTable.clear();

        // build iconTable: top-right only, does not affect contentTable
        iconTable.top().right();
        iconTable.add(shopIconImage).padTop(marginY).padRight(marginX);
        iconTable.row();
        Label exitLabel = new Label("Press E to exit shop", ls);
        exitLabel.setAlignment(Align.right);
        iconTable.add(exitLabel).padTop(rowSpacing * 0.2f).padRight(marginX);

        // build contentTable: centered with spacing
        contentTable.center().padTop(paddingTop);
        Label edTitle = new Label("Energy Drink", ls);
        edTitle.setAlignment(Align.center);
        Label edPrice = new Label("Price: 5 coins", ls);
        edPrice.setAlignment(Align.center);

        contentTable.add(edTitle).center().row();
        contentTable.add(edImage).center().padTop(rowSpacing * 0.2f).row();
        contentTable.add(edPrice).center().padTop(rowSpacing * 0.2f).row();
        contentTable.add().height(rowSpacing * 1.2f).row();

        Label bfTitle = new Label("Bird Feed", ls);
        bfTitle.setAlignment(Align.center);
        Label bfPrice = new Label("Price: 5 coins", ls);
        bfPrice.setAlignment(Align.center);

        contentTable.add(bfTitle).center().row();
        contentTable.add(bfImage).center().padTop(rowSpacing * 0.2f).row();
        contentTable.add(bfPrice).center().row();

        // assemble rootTable: icon row (only affects top area) then content fills center
        rootTable.top();
        rootTable.add(iconTable).expandX().fillX();
        rootTable.add(contentTable).expand().center();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void inputShopMenu(Game game, Viewport viewport, Player player) {
        if (stage == null) return;
        if (Gdx.input.isTouched()) {
            mouse.update(viewport);

            if (System.currentTimeMillis() - lastClickTime < 250) {
                return; // ignore clicks within 250ms of last click
            }

            lastClickTime = System.currentTimeMillis();

            Actor hit = stage.hit(mouse.getX(), mouse.getY(), true);
            while (hit != null && hit != edImage && hit != bfImage) {
                hit = hit.getParent();
            }
            if (hit == edImage) {
                Shop.buyItem(game, player, Shop.energyDrink);
            } else if (hit == bfImage) {
                Shop.buyItem(game, player, Shop.birdFeed);
            }

        }
    }

    public void dispose() {
        shapeRenderer.dispose();
        if (stage != null) stage.dispose();
    }
}
