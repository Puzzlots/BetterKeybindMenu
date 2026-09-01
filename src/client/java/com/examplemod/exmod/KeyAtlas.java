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

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class KeyAtlas {
    public static final IntArray oneByTwos = IntArray.with(57, 58, 59, 60, 61, 62, 66, 67, 115, 129, 130, 160);

    private static final AtomicBoolean initialised = new AtomicBoolean(false);

    private static @Nullable GameTexture keysTexture = null;
    private static @Nullable GameTexture keysTextureLarge = null;
    private static @Nullable GameTexture mouseTexture = null;
    private static @Nullable TextureRegion[][] keyRegions = null;
    private static @Nullable TextureRegion[][] keyLargeRegions = null;
    private static @Nullable TextureRegion[][] mouseRegions = null;
    private static @Nullable GameTexture unboundKey = null;

    private static void init() {
        if (initialised.get()) return;

        keysTexture = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas.png");

        keysTextureLarge = GameTexture
                .load("base:textures/ui/keys/keyboard-atlas-wide.png");

        mouseTexture = GameTexture
                .load("base:textures/ui/keys/mouse-atlas.png");

        keyRegions = TextureRegion.split(keysTexture.get(), 16, 16);

        keyLargeRegions = TextureRegion.split(keysTextureLarge.get(), 24, 16);

        mouseRegions = TextureRegion.split(mouseTexture.get(), 15, 15);


        unboundKey = GameTexture
                .load("base:textures/ui/keys/keyboard-unbound.png");

        initialised.set(true);
    }

    public static Image getImageOfKey(Keybind key) {
        if (!initialised.get()) init();

        Image image;

        int keyCode = key.getValue();
        if (keyCode > Input.Keys.MAX_KEYCODE) throw new RuntimeException("keyCode above max, keycode: "+ keyCode + " maxKeyCode: " + Input.Keys.MAX_KEYCODE);

        if (key.isKeyUnset()){
            image = new Image(unboundKey.get());
            image.setSize(16, 16);
            return image;
        } else if (key.isMouseButton()) {
            image = new Image(mouseRegions[0][Math.abs(keyCode)]);
            image.setSize(15, 15);
            return image;
        } else if (oneByTwos.contains(keyCode)) {
            TextureRegion textureRegion = keyLargeRegions[keyCode / 16][keyCode % 16];
            if (textureRegion == null) throw new RuntimeException("keyCode image null");
            image = new Image(textureRegion);
            image.setSize(24,16);
            return image;
        } else {
            TextureRegion textureRegion = keyRegions[keyCode / 16][keyCode % 16];
            if (textureRegion == null) throw new RuntimeException("keyCode image null");
            image = new Image(textureRegion);
            image.setSize(16, 16);
            return image;
        }
    }


    public static Image getImageOfKey(int keyCode) {
        if (!initialised.get()) init();

        Image image;

        if (keyCode > Input.Keys.MAX_KEYCODE) throw new RuntimeException("keyCode above max, keycode: "+ keyCode + " maxKeyCode: " + Input.Keys.MAX_KEYCODE);
        if (oneByTwos.contains(keyCode)) {
            TextureRegion textureRegion = keyLargeRegions[keyCode / 16][keyCode % 16];
            if (textureRegion == null) throw new RuntimeException("keyCode image null");
            image = new Image(textureRegion);
            image.setSize(24,16);
            return image;
        } else {
            TextureRegion textureRegion = keyRegions[keyCode / 16][keyCode % 16];
            if (textureRegion == null) throw new RuntimeException("keyCode image null");
            image = new Image(textureRegion);
            image.setSize(16, 16);
            return image;
        }
    }

    public static void dispose() {
        if (!initialised.compareAndSet(true, false)) return;

        if (keysTexture != null) {
            disposeGameTexture(keysTexture);
            keysTexture = null;
        }
        if (keysTextureLarge != null) {
            disposeGameTexture(keysTextureLarge);
            keysTextureLarge = null;
        }

        if (unboundKey != null) {
            disposeGameTexture(unboundKey);
            unboundKey = null;
        }

        keyRegions = null;
        keyLargeRegions = null;
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
