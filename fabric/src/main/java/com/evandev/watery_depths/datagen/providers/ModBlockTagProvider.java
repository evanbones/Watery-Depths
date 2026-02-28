package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        for (Map.Entry<TagKey<Block>, List<BlockDataHolder<?>>> entry : BlockDataHolder.getBlockTags().entrySet()) {
            FabricTagBuilder builder = getOrCreateTagBuilder(entry.getKey());
            for (BlockDataHolder<?> holder : entry.getValue()) {
                builder.add(holder.get());
            }
        }
    }
}