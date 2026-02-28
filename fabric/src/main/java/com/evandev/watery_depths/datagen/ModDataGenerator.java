package com.evandev.watery_depths.datagen;

import com.evandev.watery_depths.datagen.providers.*;
import com.evandev.watery_depths.world.feature.ModConfiguredFeatures;
import com.evandev.watery_depths.world.feature.ModPlacedFeatures;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModWorldGenProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModLanguageProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder builder) {
        builder.add(Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        builder.add(Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }
}