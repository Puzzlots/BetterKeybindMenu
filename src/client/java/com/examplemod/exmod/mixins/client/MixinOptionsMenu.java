package com.examplemod.exmod.mixins.client;

import com.examplemod.exmod.menu.BetterKeybindMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.KeybindsMenu;
import finalforeach.cosmicreach.gamestates.OptionsMenu;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "finalforeach/cosmicreach/gamestates/OptionsMenu$9")
public class MixinOptionsMenu extends CRButton {
    @Override
    public void onClick() {
        super.onClick();

           GameState.switchToGameState(new BetterKeybindMenu(GameState.currentGameState));
    }
}
