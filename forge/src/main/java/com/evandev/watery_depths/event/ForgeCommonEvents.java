package com.evandev.watery_depths.event;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.entity.CatfishEntity;
import com.evandev.watery_depths.module.ModEntities;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeCommonEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CATFISH.get(), CatfishEntity.createAttributes().build());
    }
}