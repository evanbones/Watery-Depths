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

    private static int getTubewormColor(DyeColor color) {
        return switch (color) {
            case BLACK -> 0x2f2332;
            case GRAY -> 0x62636e;
            case LIGHT_GRAY -> 0x9fb2b1;
            case WHITE -> 0xe1e5e5;
            case PINK -> 0xff87c5;
            case MAGENTA -> 0xee61e6;
            case PURPLE -> 0x8d4ad0;
            case BLUE -> 0x5163ee;
            case LIGHT_BLUE -> 0x6aadff;
            case CYAN -> 0x4ad3e3;
            case GREEN -> 0x409f42;
            case LIME -> 0x50ff52;
            case YELLOW -> 0xfbd734;
            case ORANGE -> 0xff930b;
            case RED -> 0xb82f35;
            case BROWN -> 0x9c5b39;
        };
    }

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
                return getTubewormColor(state.getValue(TubewormBlock.COLOR));
            }
            return -1;
        }, ModBlocks.TUBEWORM.get());

    }
}