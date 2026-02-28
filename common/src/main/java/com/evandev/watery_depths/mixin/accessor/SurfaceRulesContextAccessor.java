package com.evandev.watery_depths.mixin.accessor;

import net.minecraft.world.level.levelgen.RandomState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "net.minecraft.world.level.levelgen.SurfaceRules$Context")
public interface SurfaceRulesContextAccessor {

    @Accessor("randomState")
    RandomState watery_depths$getRandomState();

    @Accessor("blockX")
    int watery_depths$getBlockX();

    @Accessor("blockY")
    int watery_depths$getBlockY();

    @Accessor("blockZ")
    int watery_depths$getBlockZ();
}