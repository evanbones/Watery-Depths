package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import com.evandev.watery_depths.module.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        for (BlockDataHolder<?> holder : ModBlocks.getBlockRegistry().values()) {
            if (holder.hasModel()) {
                if (holder.getModel() == BlockDataHolder.Model.CUBE) {
                    gen.createTrivialCube(holder.get());
                }
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {}
}