package com.examplemod.exmod;

import dev.puzzleshq.puzzleloader.loader.mod.entrypoint.client.ClientPreModInit;
import dev.puzzleshq.puzzleloader.loader.util.RawAssetLoader;
import finalforeach.cosmicreach.util.assets.GameAssetLoaderUtils;

public class BetterKeybindMenu implements ClientPreModInit {

    @Override
    public void onClientPreInit() {
        GameAssetLoaderUtils.addAssetList(RawAssetLoader.getLowLevelClassPathAssetErrors(Constants.MOD_ID + "-assets.txt", false).getString().split("\n"));
    }
}
