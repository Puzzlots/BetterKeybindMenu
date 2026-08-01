package com.examplemod.exmod.ui.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.examplemod.exmod.KeyAtlas;
import com.examplemod.exmod.data.KeybindEntry;
import com.examplemod.exmod.menu.BetterKeybindMenu;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import finalforeach.cosmicreach.ui.widgets.CRLabel;
import finalforeach.cosmicreach.util.lang.Lang;


public class KeybindWidget extends Stack {
    CRLabel label;
    CRButton button;
    Image keyIconImage;
    KeybindEntry keybindEntry;

    Cell<Image> keyIconImageCell;
    Table overlay;

    public KeybindWidget(KeybindEntry keybindEntry) {
        this.keybindEntry = keybindEntry;

        this.label = new CRLabel(Lang.get(keybindEntry.langId().toString()));
        this.label.setAlignment(Align.center);
        this.label.setWrap(false);
        this.label.setHeight(70);
        this.label.setTouchable(Touchable.disabled);

        this.button = new CRButton() {
            @Override
            public void onClick() {
                BetterKeybindMenu.activeKeybindWidget = KeybindWidget.this;
            }
        };

        this.add(this.button);

        overlay = new Table();
        overlay.setFillParent(true);
        overlay.setTouchable(Touchable.disabled);

        overlay.add(this.label).expand().center().fill();
        overlay.add().grow();

        this.keyIconImage = KeyAtlas.getImageOfKey(keybindEntry.keybind());
        this.keyIconImage.setTouchable(Touchable.disabled);

        this.keyIconImageCell = overlay.add(this.keyIconImage)
                .size(this.keyIconImage.getWidth() * 4, this.keyIconImage.getHeight() * 4)
                .expand().center();


        this.add(overlay);
    }

    public KeybindEntry getKeybindEntry() {
        return keybindEntry;
    }

    public void updateIcon() {
        Image newKeyIconImage = KeyAtlas.getImageOfKey(keybindEntry.keybind());
        this.keyIconImage.setDrawable(newKeyIconImage.getDrawable());

        float newWidth = newKeyIconImage.getWidth() * 4;
        float newHeight = newKeyIconImage.getHeight() * 4;

        this.keyIconImage.setSize(newWidth, newHeight);
        this.keyIconImageCell.size(newWidth, newHeight);

        overlay.invalidate();
    }

    public void setColor(Color color) {
        this.label.setColor(color);
    }
}
