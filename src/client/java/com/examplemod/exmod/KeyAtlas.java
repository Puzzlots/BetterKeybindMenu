package com.examplemod.exmod;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.IntArray;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.assets.GameAssetCache;
import finalforeach.cosmicreach.util.assets.GameAssetLoader;
import finalforeach.cosmicreach.util.assets.GameTexture;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeyAtlas {
    public static final IntArray oneByTwos = IntArray.with(57, 58, 59, 60, 61, 62, 66, 67, 115, 129, 130, 160);

    private static final AtomicBoolean initialised = new AtomicBoolean(false);

    private static GameTexture keysTexture1x1 = null;
    private static GameTexture keysTexture1x2 = null;
    private static GameTexture mouseTexture1x1 = null;
    private static TextureRegion[][] key1x1Regions = null;
    private static TextureRegion[][] key1x2Regions = null;
    private static TextureRegion[][] mouse1x2Regions = null;
    private static GameTexture blankKey = null;

    private static void init() {
        if (initialised.get()) return;

        keysTexture1x1 = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-flat.png");

        keysTexture1x2 = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-1x2.png");

        mouseTexture1x1 = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-1x2.png");

        key1x1Regions = TextureRegion.split(keysTexture1x1.get(), 16, 16);

        key1x2Regions = TextureRegion.split(keysTexture1x2.get(), 32, 16);

        mouse1x2Regions = TextureRegion.split(mouseTexture1x1.get(), 16, 16);


        blankKey = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-unbound.png");

        initialised.set(true);
    }

    public static Image getImageOfKey(ExampleOfNewKeybind key) {
        if (!initialised.get()) init();

        Image image;

        int keyCode = key.getValue();
        if (keyCode > Input.Keys.MAX_KEYCODE) throw new RuntimeException("keyCode above max, keycode: "+ keyCode + " maxKeyCode: " + Input.Keys.MAX_KEYCODE);

        if (key.isKeyUnset()){
            image = new Image(blankKey.get());
            image.setSize(16, 16);
            return image;
        } else if (key.isMouseButton()) {
            image = new Image(mouse1x2Regions[0][0]); // TODO fix this
            return image;
        } else if (oneByTwos.contains(keyCode)) {
            TextureRegion textureRegion = key1x2Regions[keyCode / 16][keyCode % 16];
            if (textureRegion == null) throw new RuntimeException("keyCode image null");
            image = new Image(textureRegion);
            image.setSize(32,16);
            return image;
        } else {
            TextureRegion textureRegion = key1x1Regions[keyCode / 16][keyCode % 16];
            if (textureRegion == null) throw new RuntimeException("keyCode image null");
            image = new Image(textureRegion);
            image.setSize(16, 16);
            return image;
        }
    }

    public static void dispose() {
        if (!initialised.compareAndSet(true, false)) return;

        if (keysTexture1x1 != null) {
            disposeGameTexture(keysTexture1x1);
            keysTexture1x1 = null;
        }
        if (keysTexture1x2 != null) {
            disposeGameTexture(keysTexture1x2);
            keysTexture1x2 = null;
        }

        if (blankKey != null) {
            disposeGameTexture(blankKey);
            blankKey = null;
        }

        key1x1Regions = null;
        key1x2Regions = null;
    }


    /**
     * Required to properly dispose of GameTextures that are cached,
     * otherwise textures that are disposed are not cleared from cache and cause blank images
     * FINAL YOU MUST ADD THIS!!
     *
     * Made by a very proud CrabKing, that this work first try :)
     */
    @SuppressWarnings("unchecked")
    public static void disposeGameTexture(GameTexture gameTexture) {
        gameTexture.get().dispose();

        try {
            Field fieldMAP = GameTexture.class.getDeclaredField("MAP");
            fieldMAP.setAccessible(true);
            HashMap<Identifier, GameTexture> valueMAP = (HashMap<Identifier, GameTexture>) fieldMAP.get(null);
            valueMAP.remove(gameTexture.getID());

            Field field = GameTexture.class.getDeclaredField("TEXTURE_CACHE");
            field.setAccessible(true);
            GameAssetCache<Texture> value = (GameAssetCache<Texture>) field.get(null);

            FileHandle fileHandle = GameAssetLoader.loadAsset(gameTexture.getID());
            value.allAssetsOfType.remove(fileHandle);


        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("could not dispose gameTexture");
        }


    }
}
