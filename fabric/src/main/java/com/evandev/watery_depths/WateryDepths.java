package com.evandev.watery_depths;

import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class WateryDepths implements ModInitializer {
    @Override
    public void onInitialize() {
        CommonClass.init();
        registerBlocks();
        registerItems();
        CommonClass.commonSetup();
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