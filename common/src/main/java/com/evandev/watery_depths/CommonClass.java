package com.evandev.watery_depths;

import com.evandev.watery_depths.config.ModConfig;
import com.evandev.watery_depths.module.ModBlocks;
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
        ModBlocks.getBlockRegistry();
    }

    public static void commonSetup() {
        // Automatically register all compostables
        ModBlocks.getBlockRegistry().values().forEach(holder -> {
            if (holder.isCompostable() && holder.hasItem()) {
                ComposterBlock.COMPOSTABLES.put(holder.get().asItem(), holder.getCompostChance());
            }
        });
    }
}