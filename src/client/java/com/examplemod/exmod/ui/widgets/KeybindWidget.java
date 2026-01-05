package com.examplemod.exmod.ui.widgets;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.examplemod.exmod.menu.BetterKeybindMenu;
import com.examplemod.exmod.menu.KeybindEntry;
import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.rendering.GameTexture;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;

public class KeybindWidget extends Stack {
    CRLabel label;
    Button button;
    GameTexture keyIcon;
    Image keyIconImage;

    KeybindEntry keybindEntry;

    public KeybindWidget(KeybindEntry keybindEntry) {
        this.keybindEntry = keybindEntry;
        this.label = new CRLabel(Lang.get(keybindEntry.id().toString()));
        this.button = new CRButton();
        this.keyIcon = GameTexture.load("base:textures/ui/keys/keyboard/8-flat.png");
        this.keyIconImage = new Image(this.keyIcon.get());
//        this.keyIcon = GameTexture.load("base:textures/ui/keys/keyboard/" + this.keybindEntry +  "-flat.png");
        this.keybindEntry.keybind().getDisplayChar();


        this.label.setTouchable(Touchable.disabled);
        this.keyIconImage.setTouchable(Touchable.disabled);
        this.keyIconImage.setSize(this.keyIconImage.getImageWidth(), this.keyIconImage.getImageHeight());

        this.add(this.button);

        Table overlay = new Table();
        overlay.setFillParent(true);
        overlay.setTouchable(Touchable.disabled);
        overlay.setDebug(BetterKeybindMenu.DEBUG);

        overlay.add(this.label).expandX().center().left().padLeft(5);
        overlay.add().expandX();
        overlay.add(this.keyIconImage).center().right().padRight(5);

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
