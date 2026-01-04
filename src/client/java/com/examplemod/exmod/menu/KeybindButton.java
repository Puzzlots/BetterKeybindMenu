package com.examplemod.exmod.menu;

import finalforeach.cosmicreach.lang.Lang;

public class KeybindButton extends LangButton {

    KeybindEntry keybindEntry;

    public KeybindButton(KeybindEntry keybindEntry) {
        super(Lang.get(keybindEntry.id().toString()));
        this.keybindEntry = keybindEntry;
    }
//
//    KeybindsMenu.KeybindEntry keybindEntry;
//
//    public KeybindButton(KeybindsMenu.KeybindEntry e) {
//        super(e.text());
//        this.keybindEntry = e;
//    }
//
//    @Override
//    public void onClick() {
//        super.onClick();
//        if (KeybindsMenu.activeKeybindButton == null && !KeybindsMenu.this.keybindJustSet && !KeybindsMenu.this.didTouchDownOnSet) {
//            KeybindsMenu.activeKeybindButton = this;
//            KeybindsMenu.this.keybindJustSet = true;
//            this.updateText();
//        }
//    }
//
//    public void updateText() {
//        Keybind keybind = this.keybindEntry.keybind();
//        int i = keybind.getValue();
//        String s = "[" + keybind.getKeyName(i) + "]";
//        if (KeybindsMenu.activeKeybindButton == this) {
//            s = "[???]";
//        }
//
//        this.setText(this.keybindEntry.text() + ": " + s);
//    }
//
}
