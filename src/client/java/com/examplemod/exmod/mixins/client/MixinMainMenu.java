package com.examplemod.exmod.mixins.client;

import com.examplemod.exmod.GetKeyNumMenu;
import com.examplemod.exmod.menu.BetterKeybindMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.MainMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MainMenu.class)
public class MixinMainMenu extends GameState{

    /**
     * @author Me
     * @reason Instantly load the menu for debugging purposes
     */
    @Overwrite
    public void create() {

        this.onCRFontUpdate();
        GameState.switchToGameState(new BetterKeybindMenu());
//        GameState.switchToGameState(new GetKeyNumMenu());
    }

}
