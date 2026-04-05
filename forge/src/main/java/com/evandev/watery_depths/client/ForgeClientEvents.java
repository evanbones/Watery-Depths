package com.evandev.watery_depths.client;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.block.TubewormBlock;
import com.evandev.watery_depths.client.renderer.BoatRenderer;
import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModEntities;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
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

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((state, level, pos, tintIndex) -> {
            if (tintIndex == 0) {
                return getTubewormColor(state.getValue(TubewormBlock.COLOR));
            }
            return -1; // no tint
        }, ModBlocks.TUBEWORM.get());
    }

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
}