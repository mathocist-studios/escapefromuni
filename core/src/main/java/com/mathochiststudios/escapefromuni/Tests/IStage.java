package com.mathochiststudios.escapefromuni.Tests;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public interface IStage {

    void act(float delta);
    void draw();

    void addActor(TextField textField);
}
