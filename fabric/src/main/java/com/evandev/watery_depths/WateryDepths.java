package com.evandev.watery_depths;

import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import com.evandev.watery_depths.world.feature.ModPlacedFeatures;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WateryDepths implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();

        ModBlocks.registerBlocks(
                (id, blockSupplier) -> Registry.register(BuiltInRegistries.BLOCK, id, blockSupplier.get()),
                (id, itemSupplier) -> Registry.register(BuiltInRegistries.ITEM, id, itemSupplier.get())
        );

        ModItems.registerItems(
                (id, itemSupplier) -> Registry.register(BuiltInRegistries.ITEM, id, itemSupplier.get())
        );

        CommonClass.commonSetup();

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.SILT_DISK
        );

        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(
                        Biomes.COLD_OCEAN, Biomes.DEEP_COLD_OCEAN,
                        Biomes.FROZEN_OCEAN, Biomes.DEEP_FROZEN_OCEAN,
                        Biomes.OCEAN, Biomes.DEEP_OCEAN
                ),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ModPlacedFeatures.SAND_DISK
        );

    }
}