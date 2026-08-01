package com.examplemod.exmod;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.lang.Lang;
import finalforeach.cosmicreach.util.settings.types.CharSetting;
import finalforeach.cosmicreach.util.settings.types.IntSetting;
import java.lang.Character.UnicodeBlock;
import java.util.HashMap;

public class ExampleOfNewKeybind extends IntSetting {
    public static HashMap<Identifier, ExampleOfNewKeybind> allKeybinds = new HashMap<>();
    public static final ExampleOfNewKeybind MISSINGKEYBIND = new ExampleOfNewKeybind(Identifier.of("base:MISSINGKEYBIND"), -1, true) {
        @Override
        public String getKeyName() {
            return "MISSINGKEYBIND";
        }
    };
    Identifier id;
    boolean allowMouse;

    private ExampleOfNewKeybind(Identifier id, int defaultValue, boolean allowMouse) {
        super(id.getNamespace() + ":" + "keybind_" + id.getName(), defaultValue);
        allKeybinds.put(id, this);
        this.id = id;
        this.allowMouse = allowMouse;
    }

    public static ExampleOfNewKeybind fromDefaultKey(Identifier id, int defaultKeyValue) {
        return new ExampleOfNewKeybind(id, defaultKeyValue, true);
    }

    public static ExampleOfNewKeybind fromDefaultKeyNeverMouse(Identifier id, int defaultKeyValue) {
        return new ExampleOfNewKeybind(id, defaultKeyValue, false);
    }

    public static ExampleOfNewKeybind fromDefaultMouse(Identifier id, int defaultButtonValue) {
        return new ExampleOfNewKeybind(id, -2 - defaultButtonValue, true);
    }

    public boolean isPressed() {
        if (this.isMouseButton()) {
            int button = this.getMouseButtonCode();
            return Controls.isMouseIgnored(button) ? false : Gdx.input.isButtonPressed(button);
        } else {
            return Gdx.input.isKeyPressed(this.getValue());
        }
    }

    public boolean isJustPressed() {
        if (this.isMouseButton()) {
            int button = this.getMouseButtonCode();
            return Controls.isMouseIgnored(button) ? false : Gdx.input.isButtonJustPressed(button);
        } else {
            return Gdx.input.isKeyJustPressed(this.getValue());
        }
    }

    public boolean isMouseButton() {
        if (!this.allowMouse) {
            return false;
        }

        int val = this.getValue();
        return val < -1;
    }

    public int getMouseButtonCode() {
        return this.isMouseButton() ? -(this.getValue() + 2) : -1;
    }

    public String getKeyName() {
        if (this.isMouseButton()) {
            switch (this.getMouseButtonCode()) {
                case 0:
                    return Lang.get("mouseLeftButton");
                case 1:
                    return Lang.get("mouseRightButton");
                case 2:
                    return Lang.get("mouseMiddleButton");
                case 3:
                    return Lang.get("mouseBackButton");
                case 4:
                    return Lang.get("mouseForwardButton");
            }
        }

        return Keys.toString(this.getValue());
    }

    public Identifier getId(){
        return id;
    }

}

