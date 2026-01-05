package com.examplemod.exmod.ui.widgets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.examplemod.exmod.menu.KeybindEntry;
import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.rendering.GameTexture;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;

public class KeybindWidget extends Stack {
    CRLabel label;
    Button button;
    Image keyIconImage;

    KeybindEntry keybindEntry;

    public KeybindWidget(KeybindEntry keybindEntry) {
        this.keybindEntry = keybindEntry;
        Texture keysTexture = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-flat.png")
                .get();

        TextureRegion[][] keyRegions =
                TextureRegion.split(keysTexture, 16, 16);


        this.label = new CRLabel(Lang.get(keybindEntry.id().toString()));
        this.label.setAlignment(Align.center);
        this.label.setWrap(false);
        this.label.setHeight(70);

        this.button = new CRButton();

        int keyCode = keybindEntry.keybind().getValue();
        if (keybindEntry.keybind().isMouseButton()) {
            this.keyIconImage = new Image(keysTexture);
        } else {
            this.keyIconImage = new Image(keyRegions[keyCode % 16][keyCode / 16]);
        }

        this.label.setTouchable(Touchable.disabled);
        this.keyIconImage.setTouchable(Touchable.disabled);

        this.add(this.button);

        Table overlay = new Table();
        overlay.setFillParent(true);
        overlay.setTouchable(Touchable.disabled);

        overlay.add(this.label).expand().center().fill();
        overlay.add().grow();
        overlay.add(this.keyIconImage).size(64).expand().center();

        this.add(overlay);
    }
//
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//
//        float scale = 4.0F;
//        float width = 16F;
//        float height = 16F;
//        float x = this.getOriginX();
//        float y = this.getOriginY();
//
//        Texture texture = this.keyIcon.get();
//        batch.draw(texture, x, y, 0.0F, 0.0F, width, height, scale, scale, 0.0F, 0, 0, texture.getWidth(), texture.getHeight(), false, false);
//    }
}
