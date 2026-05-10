package com.evandev.watery_depths.world.feature;

import com.evandev.watery_depths.CommonClass;
import com.evandev.watery_depths.module.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
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
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAND_DISK = create("sand_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLAY_DISK = create("clay_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS = create("cypress");

    public static ResourceKey<ConfiguredFeature<?, ?>> create(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, CommonClass.makeID(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleBasedBlockStateProvider siltState = RuleBasedBlockStateProvider.simple(ModBlocks.SILT.get().defaultBlockState().getBlock());
        RuleBasedBlockStateProvider sandState = RuleBasedBlockStateProvider.simple(Blocks.SAND);
        RuleBasedBlockStateProvider clayState = RuleBasedBlockStateProvider.simple(Blocks.CLAY);

        context.register(SILT_DISK, new ConfiguredFeature<>(Feature.DISK,
                new DiskConfiguration(siltState,
                        BlockPredicate.matchesBlocks(List.of(Blocks.DIRT, Blocks.CLAY, Blocks.GRAVEL, Blocks.SAND)),
                        UniformInt.of(2, 5),
                        2
                )
        ));

        context.register(SAND_DISK, new ConfiguredFeature<>(Feature.DISK,
                new DiskConfiguration(sandState,
                        BlockPredicate.matchesBlocks(List.of(ModBlocks.SILT.get().defaultBlockState().getBlock(), Blocks.GRAVEL, Blocks.DIRT)),
                        UniformInt.of(2, 5),
                        2
                )
        ));

        context.register(CLAY_DISK, new ConfiguredFeature<>(Feature.DISK,
                new DiskConfiguration(clayState,
                        BlockPredicate.matchesBlocks(List.of(ModBlocks.SILT.get().defaultBlockState().getBlock(), Blocks.DIRT, Blocks.CLAY)),
                        UniformInt.of(2, 3),
                        1
                )
        ));
    }
}