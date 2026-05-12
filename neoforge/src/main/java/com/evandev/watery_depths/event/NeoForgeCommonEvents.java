package com.evandev.watery_depths.event;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.entity.CatfishEntity;
import com.evandev.watery_depths.content.ModEntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeCommonEvents {
    @SubscribeEvent
    public static void entityAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CATFISH.get(), CatfishEntity.createAttributes().build());
    }
}