package com.examplemod.exmod.mixins.client;

import com.examplemod.exmod.menu.BetterKeybindMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MainMenu.class)
public class MixinMainMenu extends GameState{


    @Unique
    private static boolean betterKeybindMenu$hasntRun = true;

    /**
     * @author Me
     * @reason Instantly load the menu for debugging purposes
     */
    @Inject(at = @At(value = "HEAD"), method = "render")
    public void render(CallbackInfo ci) {
//        if (betterKeybindMenu$hasntRun) {
//            GameState.switchToGameState(new BetterKeybindMenu(new MainMenu()));
//            betterKeybindMenu$hasntRun = !betterKeybindMenu$hasntRun;
//        }
    }

}
