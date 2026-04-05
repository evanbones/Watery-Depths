package com.evandev.watery_depths.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.OverworldBiomeBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(OverworldBiomeBuilder.class)
public class OverworldBiomeBuilderMixin {

    @Inject(method = "addBiomes", at = @At("TAIL"))
    private void watery_depths$injectBayou(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> key, CallbackInfo ci) {

        ResourceKey<Biome> bayouKey = ResourceKey.create(Registries.BIOME, new ResourceLocation("watery_depths", "bayou"));

        Climate.ParameterPoint bayouPoint = Climate.parameters(
                Climate.Parameter.span(0.55F, 1.0F),
                Climate.Parameter.span(0.55F, 1.0F),
                Climate.Parameter.span(-0.11F, 0.55F),
                Climate.Parameter.span(-0.375F, -0.2225F),
                Climate.Parameter.point(0.0F),
                Climate.Parameter.span(-0.2F, 0.2F),
                0.0F
        );

        key.accept(Pair.of(bayouPoint, bayouKey));
    }
}