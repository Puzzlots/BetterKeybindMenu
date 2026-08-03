package com.examplemod.exmod;

import dev.puzzleshq.puzzleloader.loader.mod.entrypoint.client.ClientPreModInit;
import dev.puzzleshq.puzzleloader.loader.util.RawAssetLoader;
import finalforeach.cosmicreach.util.Identifier;
import finalforeach.cosmicreach.util.assets.GameAssetLoaderUtils;

public class BetterKeybindMenuInit implements ClientPreModInit {


    public static final ExampleOfNewKeybind keyForward = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "forward"), 51);
    public static final ExampleOfNewKeybind keyBackward = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "backward"), 47);
    public static final ExampleOfNewKeybind keyLeft = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "left"), 29);
    public static final ExampleOfNewKeybind keyRight = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "right"), 32);
    public static final ExampleOfNewKeybind keyJump = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "jump"), 62);
    public static final ExampleOfNewKeybind keyCrouch = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "crouch"), 59);
    public static final ExampleOfNewKeybind keySprint = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "sprint"), 129);
    public static final ExampleOfNewKeybind keyProne = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "prone"), 54);
    public static final ExampleOfNewKeybind keyInventory = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "openInventory"), 33);
    public static final ExampleOfNewKeybind keyDropItem = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "dropItem"), 45);
    public static final ExampleOfNewKeybind keySwapGroupItem = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "swapGroupItem"), 68);
    public static final ExampleOfNewKeybind keyHideUI = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "hideUI"), 131);
    public static final ExampleOfNewKeybind keyScreenshot = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "screenshot"), 132);
    public static final ExampleOfNewKeybind keyDebugInfo = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "debugInfo"), 133);
    public static final ExampleOfNewKeybind keyChangePerspective = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "changePerspective"), 135);
    public static final ExampleOfNewKeybind keyDebugReloadShaders = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "reloadShaders"), 136);
    public static final ExampleOfNewKeybind keyFullscreen = ExampleOfNewKeybind.fromDefaultKeyNeverMouse(Identifier.of("base", "fullscreen"), 141);
    public static final ExampleOfNewKeybind keyAttackBreak = ExampleOfNewKeybind.fromDefaultMouse(Identifier.of("base", "attackBreak"), 0);
    public static final ExampleOfNewKeybind keyPickBlock = ExampleOfNewKeybind.fromDefaultMouse(Identifier.of("base", "pickBlock"), 2);
    public static final ExampleOfNewKeybind keyUsePlace = ExampleOfNewKeybind.fromDefaultMouse(Identifier.of("base", "usePlace"), 1);
    public static final ExampleOfNewKeybind keyChat = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "chat"), 48);
    public static final ExampleOfNewKeybind keyVoice = ExampleOfNewKeybind.fromDefaultKey(Identifier.of("base", "voice"), 50);

    @Override
    public void onClientPreInit() {
        GameAssetLoaderUtils.addAssetList(RawAssetLoader.getLowLevelClassPathAssetErrors(Constants.MOD_ID + "-assets.txt", false).getString().split("\n"));
    }
}
