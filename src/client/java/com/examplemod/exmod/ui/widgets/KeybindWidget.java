package com.examplemod.exmod.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.examplemod.exmod.KeyAtlas;
import com.examplemod.exmod.menu.KeybindEntry;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;
import finalforeach.cosmicreach.util.lang.Lang;


public class KeybindWidget extends Stack {
    CRLabel label;
    Button button;
    Image keyIconImage;

    KeybindEntry keybindEntry;

    public KeybindWidget(KeybindEntry keybindEntry) {
        this.keybindEntry = keybindEntry;

        this.label = new CRLabel(Lang.get(keybindEntry.id().toString()));
        this.label.setAlignment(Align.center);
        this.label.setWrap(false);
        this.label.setHeight(70);

        this.button = new CRButton();

        this.keyIconImage = KeyAtlas.getImageOfKey(keybindEntry.keybind());

        this.label.setTouchable(Touchable.disabled);
        this.keyIconImage.setTouchable(Touchable.disabled);

        this.add(this.button);

        Table overlay = new Table();
        overlay.setFillParent(true);
        overlay.setTouchable(Touchable.disabled);

        overlay.add(this.label).expand().center().fill();
        overlay.add().grow();


        overlay.add(this.keyIconImage).size(this.keyIconImage.getWidth() *4, this.keyIconImage.getHeight() *4).expand().center();


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
