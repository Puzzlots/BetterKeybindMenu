package com.examplemod.exmod;

import finalforeach.cosmicreach.util.Identifier;

public class KeyBindRegistry {

    public static final Keybind keyForward = Keybind.fromDefaultKey(Identifier.of("base", "forward"), 51);
    public static final Keybind keyBackward = Keybind.fromDefaultKey(Identifier.of("base", "backward"), 47);
    public static final Keybind keyLeft = Keybind.fromDefaultKey(Identifier.of("base", "left"), 29);
    public static final Keybind keyRight = Keybind.fromDefaultKey(Identifier.of("base", "right"), 32);
    public static final Keybind keyJump = Keybind.fromDefaultKey(Identifier.of("base", "jump"), 62);
    public static final Keybind keyCrouch = Keybind.fromDefaultKey(Identifier.of("base", "crouch"), 59);
    public static final Keybind keySprint = Keybind.fromDefaultKey(Identifier.of("base", "sprint"), 129);
    public static final Keybind keyProne = Keybind.fromDefaultKey(Identifier.of("base", "prone"), 54);
    public static final Keybind keyInventory = Keybind.fromDefaultKey(Identifier.of("base", "openInventory"), 33);
    public static final Keybind keyDropItem = Keybind.fromDefaultKey(Identifier.of("base", "dropItem"), 45);
    public static final Keybind keySwapGroupItem = Keybind.fromDefaultKey(Identifier.of("base", "swapGroupItem"), 68);
    public static final Keybind keyHideUI = Keybind.fromDefaultKey(Identifier.of("base", "hideUI"), 131);
    public static final Keybind keyScreenshot = Keybind.fromDefaultKey(Identifier.of("base", "screenshot"), 132);
    public static final Keybind keyDebugInfo = Keybind.fromDefaultKey(Identifier.of("base", "debugInfo"), 133);
    public static final Keybind keyChangePerspective = Keybind.fromDefaultKey(Identifier.of("base", "changePerspective"), 135);
    public static final Keybind keyDebugReloadShaders = Keybind.fromDefaultKey(Identifier.of("base", "reloadShaders"), 136);
    public static final Keybind keyFullscreen = Keybind.fromDefaultKeyNeverMouse(Identifier.of("base", "fullscreen"), 141);
    public static final Keybind keyAttackBreak = Keybind.fromDefaultMouse(Identifier.of("base", "attackBreak"), 0);
    public static final Keybind keyPickBlock = Keybind.fromDefaultMouse(Identifier.of("base", "pickBlock"), 2);
    public static final Keybind keyUsePlace = Keybind.fromDefaultMouse(Identifier.of("base", "usePlace"), 1);
    public static final Keybind keyChat = Keybind.fromDefaultKey(Identifier.of("base", "chat"), 48);
    public static final Keybind keyVoice = Keybind.fromDefaultKey(Identifier.of("base", "voice"), 50);

}
