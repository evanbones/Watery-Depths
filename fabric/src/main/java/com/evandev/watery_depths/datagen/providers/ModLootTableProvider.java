package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import com.evandev.watery_depths.module.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        for (BlockDataHolder<?> holder : ModBlocks.getBlockRegistry().values()) {
            if (holder.hasDrop()) {
                if (holder.getDrop().get() == holder.get()) {
                    dropSelf(holder.get());
                } else {
                    add(holder.get(), createSingleItemTable(holder.getDrop().get(), holder.getDropCount()));
                }
            }
        }
    }
}