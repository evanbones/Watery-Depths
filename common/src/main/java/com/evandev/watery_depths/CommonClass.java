package com.evandev.watery_depths;

import com.evandev.watery_depths.config.ModConfig;
import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModCreativeTabs;
import com.evandev.watery_depths.module.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.ComposterBlock;

public class CommonClass {
    private static MinecraftServer currentServer;

    public static MinecraftServer getServer() {
        return currentServer;
    }

    public static void setServer(MinecraftServer server) {
        currentServer = server;
    }

    public static void init() {
        ModConfig.load();
        ModBlocks.load();
        ModItems.load();
        ModCreativeTabs.load();
    }

    public static ResourceLocation makeID(String name) {
        return new ResourceLocation(Constants.MOD_ID, name);
    }

    public static void commonSetup() {
        ModBlocks.getBlockRegistry().values().forEach(holder -> {
            if (holder.isCompostable() && holder.hasItem()) {
                ComposterBlock.COMPOSTABLES.put(holder.get().asItem(), holder.getCompostChance());
            }
        });
    }
}