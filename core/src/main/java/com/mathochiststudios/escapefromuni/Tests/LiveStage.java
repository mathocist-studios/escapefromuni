package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LiveStage extends Stage implements IStage {

    public LiveStage(Viewport viewport) {
        super(viewport);
    }

    @Override
    public void addActor(TextField textField) {
        super.addActor(textField);
    }
}
