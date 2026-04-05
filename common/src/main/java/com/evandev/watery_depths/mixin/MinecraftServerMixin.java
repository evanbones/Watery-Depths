package com.evandev.watery_depths.mixin;

import com.evandev.watery_depths.world.biome.WateryDepthsBiomeSlicesManager;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "loadLevel", at = @At("HEAD"))
    private void watery_depths$onLoadLevel(CallbackInfo ci) {
        WateryDepthsBiomeSlicesManager.onServerAboutToStart((MinecraftServer) (Object) this);
    }
}
