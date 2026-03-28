package com.evandev.watery_depths.client;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.client.renderer.BoatRenderer;
import com.evandev.watery_depths.module.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForgeClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CYPRESS_BOAT.get(), context ->
                new BoatRenderer(context, false, new ResourceLocation(Constants.MOD_ID, "textures/entity/boat/cypress.png"))
        );

        event.registerEntityRenderer(ModEntities.CYPRESS_CHEST_BOAT.get(), context ->
                new BoatRenderer(context, true, new ResourceLocation(Constants.MOD_ID, "textures/entity/chest_boat/cypress.png"))
        );
    }
}