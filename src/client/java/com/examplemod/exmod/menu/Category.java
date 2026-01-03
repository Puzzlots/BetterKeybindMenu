package com.examplemod.exmod.menu;

import finalforeach.cosmicreach.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class Category {
    List<KeybindEntry> keybinds = new ArrayList<>();

    private final CategoryType type;
    private final Identifier id;

    public Category(CategoryType type, Identifier id) {
        this.type = type;
        this.id = id;
    }

    public void addKeybind(KeybindEntry keybindEntry) {
        keybinds.add(keybindEntry);
    }

    public CategoryType getType() {
        return type;
    }

    public Identifier getId() {
        return id;
    }

}
