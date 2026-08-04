package com.examplemod.exmod.mixins.client;

import com.studiohartman.jamepad.Configuration;
import com.studiohartman.jamepad.ControllerManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ControllerManager.class)
public class MixinControllerManager {

    @Shadow
    private String mappingsPath;

    @Inject(method = "<init>(Lcom/studiohartman/jamepad/Configuration;Ljava/lang/String;)V", at = @At("TAIL"))
    public void init(Configuration configuration, String mappingsPath, CallbackInfo ci){
        this.mappingsPath = "db/gamecontrollerdb.txt";
    }
}
