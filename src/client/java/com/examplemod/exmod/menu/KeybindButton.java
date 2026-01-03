package com.examplemod.exmod.menu;

import finalforeach.cosmicreach.lang.Lang;
import finalforeach.cosmicreach.util.Identifier;

public class KeybindButton extends LangButton {

    Identifier categoryId;

    public KeybindButton(Identifier categoryId) {
        super("test");
        this.categoryId = categoryId;
    }

}
