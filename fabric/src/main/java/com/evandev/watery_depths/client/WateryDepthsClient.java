package com.evandev.watery_depths.client;

import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class WateryDepthsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockDataHolder.getCutoutBlocks().forEach(holder -> {
            BlockRenderLayerMap.INSTANCE.putBlock(holder.get(), RenderType.cutout());
        });
    }
}