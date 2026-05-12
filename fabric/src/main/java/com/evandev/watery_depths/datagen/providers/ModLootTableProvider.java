package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.content.ModBlocks;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture);
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

            for (BlockDataHolder<?> setHolder : holder.getBlocksets().values()) {
                if (setHolder.getModel() == BlockDataHolder.Model.SLAB) {
                    add(setHolder.get(), createSlabItemTable(setHolder.get()));
                } else if (setHolder.getModel() == BlockDataHolder.Model.DOOR) {
                    add(setHolder.get(), createDoorTable(setHolder.get()));
                } else {
                    dropSelf(setHolder.get());
                }
            }
        }
    }
}