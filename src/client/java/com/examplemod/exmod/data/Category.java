package com.examplemod.exmod.data;

import finalforeach.cosmicreach.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record Category(List<KeybindEntry> keybinds, Identifier id) {
    public Category(Identifier id) {
        this(new ArrayList<>(), id);
    }

    public Category(String namespace, String name) {
        this(new ArrayList<>(), Identifier.of(namespace, name));
    }
    public void addKeybind(KeybindEntry keybindEntry) {
        keybinds.add(keybindEntry);
    }

}
