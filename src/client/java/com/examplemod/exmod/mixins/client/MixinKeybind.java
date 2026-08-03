package com.examplemod.exmod.mixins.client;

import finalforeach.cosmicreach.settings.Keybind;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keybind.class)
public class MixinKeybind {

    @Inject(method = "<init>(Ljava/lang/String;IZ)V", at = @At("TAIL"))
    private void onConstruct(String key, int defaultValue, boolean allowMouse, CallbackInfo ci) {
        // your extra logic here, e.g.:
        System.out.println("Custom Keybind constructed: " + key);
    }
}
