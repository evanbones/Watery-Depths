package com.evandev.watery_depths.client;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.client.renderer.BoatRenderer;
import com.evandev.watery_depths.module.ModEntities;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;


public class WateryDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockDataHolder.getCutoutBlocks().forEach(holder -> {
            BlockRenderLayerMap.INSTANCE.putBlock(holder.get(), RenderType.cutout());
        });

        EntityRendererRegistry.register(ModEntities.CYPRESS_BOAT.get(), context ->
                new BoatRenderer(context, false, new ResourceLocation(Constants.MOD_ID, "textures/entity/boat/cypress.png"))
        );

        EntityRendererRegistry.register(ModEntities.CYPRESS_CHEST_BOAT.get(), context ->
                new BoatRenderer(context, true, new ResourceLocation(Constants.MOD_ID, "textures/entity/chest_boat/cypress.png"))
        );
    }
}