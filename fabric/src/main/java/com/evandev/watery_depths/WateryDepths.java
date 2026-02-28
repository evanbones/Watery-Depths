package com.evandev.watery_depths;

import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import com.evandev.watery_depths.world.feature.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WateryDepths implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        registerBlocks();
        registerItems();
        CommonClass.commonSetup();

        registerWorldGeneration();
    }

    public void registerWorldGeneration() {
        BiomeModifications.addFeature(
                BiomeSelectors.tag(BiomeTags.IS_OCEAN).or(BiomeSelectors.tag(BiomeTags.IS_RIVER)),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.SILT_DISK
        );
    }

    public void registerBlocks() {
        ModBlocks.registerBlocks(
                (id, block) -> Registry.register(BuiltInRegistries.BLOCK, id, block),
                (id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item)
        );
    }

    public void registerItems() {
        ModItems.registerItems(
                (id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item)
        );
    }
}