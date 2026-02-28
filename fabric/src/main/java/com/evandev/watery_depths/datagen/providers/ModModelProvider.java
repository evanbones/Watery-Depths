package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import com.evandev.watery_depths.registration.holders.ItemDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

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
                } else if (holder.getModel() == BlockDataHolder.Model.NYLIUM) {
                    ResourceLocation bottomTexture;
                    if (holder == ModBlocks.ALGAL_SAND) {
                        bottomTexture = new ResourceLocation("minecraft", "block/sand");
                    } else if (holder == ModBlocks.ALGAL_GRAVEL) {
                        bottomTexture = new ResourceLocation("minecraft", "block/gravel");
                    } else {
                        bottomTexture = new ResourceLocation("watery_depths", "block/silt");
                    }

                    TextureMapping mapping = new TextureMapping()
                            .put(TextureSlot.TOP, new ResourceLocation("watery_depths", "block/algae"))
                            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(holder.get()))
                            .put(TextureSlot.BOTTOM, bottomTexture);

                    ResourceLocation customModel = ModelTemplates.CUBE_BOTTOM_TOP.create(holder.get(), mapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(holder.get(), customModel));
                }
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : ModItems.getItemRegistry().entrySet()) {
            ItemDataHolder<?> holder = entry.getValue();

            if (holder.getModel() != null) {
                gen.generateFlatItem(holder.get(), holder.getModel());
            }
        }
    }
}