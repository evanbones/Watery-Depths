package com.evandev.watery_depths.client;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.block.TubewormBlock;
import com.evandev.watery_depths.client.renderer.BoatRenderer;
import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModEntities;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;


public class WateryDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockDataHolder.getCutoutBlocks().forEach(holder -> BlockRenderLayerMap.INSTANCE.putBlock(holder.get(), RenderType.cutout()));

        EntityRendererRegistry.register(ModEntities.CYPRESS_BOAT.get(), context ->
                new BoatRenderer(context, false, new ResourceLocation(Constants.MOD_ID, "textures/entity/boat/cypress.png"))
        );

        EntityRendererRegistry.register(ModEntities.CYPRESS_CHEST_BOAT.get(), context ->
                new BoatRenderer(context, true, new ResourceLocation(Constants.MOD_ID, "textures/entity/chest_boat/cypress.png"))
        );

        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> {
            if (tintIndex == 0) {
                return state.getValue(TubewormBlock.COLOR).getMapColor().col;
            }
            return -1;
        }, ModBlocks.TUBEWORM.get());

        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
            if (tintIndex == 0) {
                return DyeColor.RED.getTextColor();
            }
            return -1;
        }, ModBlocks.TUBEWORM.get());
    }
}