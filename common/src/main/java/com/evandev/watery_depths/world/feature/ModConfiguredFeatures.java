package com.evandev.watery_depths.world.feature;

import com.evandev.watery_depths.CommonClass;
import com.evandev.watery_depths.module.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILT_DISK = create("silt_disk");

    public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, CommonClass.makeID(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleBasedBlockStateProvider stateProvider = RuleBasedBlockStateProvider.simple(ModBlocks.SILT.get().defaultBlockState().getBlock());

        context.register(SILT_DISK, new ConfiguredFeature<>(Feature.DISK,
                new DiskConfiguration(stateProvider,
                        BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.CLAY, Blocks.GRAVEL, Blocks.SAND)),
                        UniformInt.of(2, 5),
                        2
                )
        ));
    }
}