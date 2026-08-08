package com.examplemod.exmod.data;

import finalforeach.cosmicreach.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record Category(List<KeybindEntry> keybinds, Identifier id, boolean isSearchCategory) {

    public Category(Identifier id) {
        this(new ArrayList<>(), id, false);
    }

    public Category(String namespace, String name) {
        this(new ArrayList<>(), Identifier.of(namespace, name), false);
    }

    public Category(boolean isSearchCategory) {
        this(null, null, true);
    }

    public void addKeybind(KeybindEntry keybindEntry) {
        keybinds.add(keybindEntry);
    }

}
