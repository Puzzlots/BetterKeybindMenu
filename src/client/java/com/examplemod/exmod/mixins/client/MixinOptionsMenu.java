package com.examplemod.exmod.mixins.client;

import com.examplemod.exmod.menu.BetterKeybindMenu;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.ui.widgets.CRButton;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "finalforeach/cosmicreach/gamestates/OptionsMenu$7")
public class MixinOptionsMenu extends CRButton {
    @Override
    public void onClick() {
        super.onClick();
        GameState.switchToGameState(new BetterKeybindMenu());
    }
}
