package com.evandev.watery_depths.datagen;

import com.evandev.watery_depths.datagen.providers.ModBlockTagProvider;
import com.evandev.watery_depths.datagen.providers.ModLanguageProvider;
import com.evandev.watery_depths.datagen.providers.ModLootTableProvider;
import com.evandev.watery_depths.datagen.providers.ModModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class ModDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModLanguageProvider::new);
    }
}