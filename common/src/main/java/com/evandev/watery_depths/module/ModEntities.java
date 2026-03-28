package com.evandev.watery_depths.module;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.entity.ModBoatEntity;
import com.evandev.watery_depths.entity.ModChestBoatEntity;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<EntityType<ModBoatEntity>> CYPRESS_BOAT = ENTITIES.register("cypress_boat",
            () -> EntityType.Builder.<ModBoatEntity>of((type, level) ->
                            new ModBoatEntity(type, level, ModItems.CYPRESS_BOAT::get), MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build(Constants.MOD_ID + ":cypress_boat"));

    public static final RegistryObject<EntityType<ModChestBoatEntity>> CYPRESS_CHEST_BOAT = ENTITIES.register("cypress_chest_boat",
            () -> EntityType.Builder.<ModChestBoatEntity>of((type, level) ->
                            new ModChestBoatEntity(type, level, ModItems.CYPRESS_CHEST_BOAT::get), MobCategory.MISC)
                    .sized(1.375F, 0.5625F)
                    .clientTrackingRange(10)
                    .build(Constants.MOD_ID + ":cypress_chest_boat"));

    public static void load() {
    }
}