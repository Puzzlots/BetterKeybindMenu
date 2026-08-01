package com.examplemod.exmod;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.IntArray;
import finalforeach.cosmicreach.settings.Keybind;
import finalforeach.cosmicreach.util.assets.GameTexture;

public class KeyAtlas {
    public static final IntArray oneByTwos = IntArray.with(57, 58, 59, 60, 61, 62, 66, 67, 115, 129, 130, 160);

    private static boolean initialised = false;

    private static Texture keysTexture1x1;
    private static Texture keysTexture1x2;
    private static TextureRegion[][] key1x1Regions;
    private static TextureRegion[][] key1x2Regions;

    public static void init() {
        keysTexture1x1 = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-flat.png")
                .get();

        keysTexture1x2 = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-1x2.png")
                .get();

        key1x1Regions = TextureRegion.split(keysTexture1x1, 16, 16);

        key1x2Regions = TextureRegion.split(keysTexture1x2, 32, 16);

        initialised = true;
    }

    public static Image getImageOfKey(Keybind key) {
        if (!initialised) init();

        Image image;

        int keyCode = key.getValue();
        if (keyCode >= Input.Keys.MAX_KEYCODE) throw new RuntimeException("keyCode above max");

        if (key.isMouseButton()) {
            image = new Image(keysTexture1x1);
            return image;
        } else if (oneByTwos.contains(keyCode)) {
            image = new Image(key1x2Regions[keyCode / 16][keyCode % 16]);
            image.setSize(32,16);
            return image;
        } else {
            image = new Image(key1x1Regions[keyCode / 16][keyCode % 16]);
            image.setSize(16, 16);
            return image;
        }
    }

    public static void dispose() {
        if (!initialised) return;

        initialised = false;
        keysTexture1x1.dispose();
        keysTexture1x2.dispose();

        key1x1Regions = null;
        key1x2Regions = null;
    }
}
