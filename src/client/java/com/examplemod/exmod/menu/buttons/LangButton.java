package com.examplemod.exmod.menu.buttons;


import com.badlogic.gdx.graphics.Color;
import finalforeach.cosmicreach.ui.widgets.CRButton;

/**
 * this is just the langButton as we need to set text colour at runtime
 */
public class LangButton extends CRButton {

    public LangButton(String text) {
        super(text);
    }

    @Override
    public void updateText() {

    }

    public void setTextColor(Color color){
        this.enabledColor = color;
    }

    public void setDisabledTextColor(Color color){
        this.disabledColor = color;
    }

}
