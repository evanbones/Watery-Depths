package com.evandev.watery_depths;

import com.evandev.watery_depths.config.ModConfig;
import com.evandev.watery_depths.module.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.ComposterBlock;

public class CommonClass {

    public static void init() {
        ModConfig.load();
        ModSounds.load();
        ModBlocks.load();
        ModItems.load();
        ModEntities.load();
        ModCreativeTabs.load();
    }

    public static ResourceLocation makeID(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static void commonSetup() {
        ModBlocks.registerBlockInteractions();

        ModBlocks.getBlockRegistry().values().forEach(holder -> {
            if (holder.isCompostable() && holder.hasItem()) {
                ComposterBlock.COMPOSTABLES.put(holder.get().asItem(), holder.getCompostChance());
            }
        });
    }
}