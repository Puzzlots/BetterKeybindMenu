package com.examplemod.exmod.ui.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.examplemod.exmod.menu.KeybindEntry;
import finalforeach.cosmicreach.GameAssetLoader;
import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.rendering.GameTexture;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;

public class KeybindWidget extends Stack {
    CRLabel label;
    CRButton button;
    GameTexture keyIcon;

    KeybindEntry keybindEntry;

    public KeybindWidget(KeybindEntry keybindEntry) {
        this.keybindEntry = keybindEntry;
        this.label = new CRLabel(Lang.get(keybindEntry.id().toString()));
        this.button = new CRButton();

        this.add(this.button);
//        Container<Label> container = new Container<>(this.label);
//        container.pad(10);
//        this.add(container);
        this.add(this.label);

        this.keyIcon = GameTexture.load("base:textures/ui/keys/keyboard/8-flat.png");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        float scale = 4.0F;
        float width = 16F;
        float height = 16F;

        Texture texture = this.keyIcon.get();
        batch.draw(texture, 0, 0, 0.0F, 0.0F, width, height, scale, scale, 0.0F, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
    }
}
