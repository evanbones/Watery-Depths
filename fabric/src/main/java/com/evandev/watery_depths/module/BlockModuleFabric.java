package com.evandev.watery_depths.module;

import com.evandev.watery_depths.module.ModBlocks;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;

public class BlockModuleFabric {
    public static void registerBlocks() {
        ModBlocks.registerBlocks(
                (id, block) -> Registry.register(BuiltInRegistries.BLOCK, id, block),
                (id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item)
        );
    }
}