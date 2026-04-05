package com.evandev.watery_depths.world.feature.tree;

import com.evandev.watery_depths.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CypressTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(@NotNull RandomSource random, boolean hasFlowers) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, CommonClass.makeID("cypress"));
    }
}