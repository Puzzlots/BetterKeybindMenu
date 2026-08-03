package com.examplemod.exmod;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import finalforeach.cosmicreach.settings.Controls;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.lang.Lang;
import finalforeach.cosmicreach.util.settings.types.BooleanSetting;
import finalforeach.cosmicreach.util.settings.types.CharSetting;
import finalforeach.cosmicreach.util.settings.types.IntSetting;
import java.lang.Character.UnicodeBlock;
import java.util.HashMap;

public class ExampleOfNewKeybind extends IntSetting {
    public static HashMap<Identifier, ExampleOfNewKeybind> allKeybinds = new HashMap<>();
    public static final ExampleOfNewKeybind MISSINGKEYBIND = new ExampleOfNewKeybind(Identifier.of("base:MISSINGKEYBIND"), -1, true, true) {
        @Override
        public String getKeyName() {
            return "MISSINGKEYBIND";
        }
    };
    Identifier id;
    boolean allowMouse;
    BooleanSetting isUnset;

    private ExampleOfNewKeybind(Identifier id, int defaultValue, boolean allowMouse, boolean isUnset) {
        super(id.getNamespace() + ":" + "keybind_" + id.getName(), defaultValue);
        allKeybinds.put(id, this);
        this.id = id;
        this.allowMouse = allowMouse;
        this.isUnset = new BooleanSetting(id.getNamespace() + ":" + "keybind_" + id.getName() + "_unset", isUnset);
    }

    public static ExampleOfNewKeybind fromDefaultKey(Identifier id, int defaultKeyValue) {
        return new ExampleOfNewKeybind(id, defaultKeyValue, true, false);
    }

    public static ExampleOfNewKeybind fromDefaultKey(Identifier id, int defaultKeyValue, boolean isUnset) {
        return new ExampleOfNewKeybind(id, defaultKeyValue, true, isUnset);
    }

    public static ExampleOfNewKeybind fromDefaultKeyNeverMouse(Identifier id, int defaultKeyValue) {
        return new ExampleOfNewKeybind(id, defaultKeyValue, false, false);
    }

    public static ExampleOfNewKeybind fromDefaultKeyNeverMouse(Identifier id, int defaultKeyValue, boolean isUnset) {
        return new ExampleOfNewKeybind(id, defaultKeyValue, false, isUnset);
    }

    public static ExampleOfNewKeybind fromDefaultMouse(Identifier id, int defaultButtonValue) {
        return new ExampleOfNewKeybind(id, -2 - defaultButtonValue, true, false);
    }

    public static ExampleOfNewKeybind fromDefaultMouse(Identifier id, int defaultButtonValue, boolean isUnset) {
        return new ExampleOfNewKeybind(id, -2 - defaultButtonValue, true, isUnset);
    }

    public Identifier getId(){
        return id;
    }

    public boolean isPressed() {
        if (isUnset.getValue()) return false;
        if (this.isMouseButton()) {
            int button = this.getMouseButtonCode();
            return !Controls.isMouseIgnored(button) && Gdx.input.isButtonPressed(button);
        } else {
            return Gdx.input.isKeyPressed(this.getValue());
        }
    }

    public boolean isJustPressed() {
        if (isUnset.getValue()) return false;
        if (this.isMouseButton()) {
            int button = this.getMouseButtonCode();
            return !Controls.isMouseIgnored(button) && Gdx.input.isButtonJustPressed(button);
        } else {
            return Gdx.input.isKeyJustPressed(this.getValue());
        }
    }

    public boolean isMouseButton() {
        if (isUnset.getValue()) return false;
        if (!this.allowMouse) {
            return false;
        }

        int val = this.getValue();
        return val < -1;
    }

    public boolean mouseAllowed(){
        return this.allowMouse;
    }


    public int getMouseButtonCode() {
        return this.isMouseButton() ? -(this.getValue() + 2) : -1;
    }

    public boolean isKeyUnset(){
        return this.isUnset.getValue();
    }

    public BooleanSetting getKeyUnset(){
        return this.isUnset;
    }


    @Override
    public void setValue(int newValue) {
        super.setValue(newValue);
        this.isUnset.setValue(false);
    }

    //TODO don't know what to do with this as isUnset needs to be checked on call but i can't edit that
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

}

