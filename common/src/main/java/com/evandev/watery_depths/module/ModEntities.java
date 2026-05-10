package com.evandev.watery_depths.module;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.entity.CatfishEntity;
import com.evandev.watery_depths.entity.ModBoatEntity;
import com.evandev.watery_depths.entity.ModChestBoatEntity;
import com.evandev.watery_depths.mixin.accessor.SpawnPlacementsAccessor;
import com.evandev.watery_depths.registration.holders.EntityTypeDataHolder;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModEntities {
    public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(BuiltInRegistries.ENTITY_TYPE, Constants.MOD_ID);
    private static final Map<ResourceLocation, EntityTypeDataHolder<?>> ENTITY_REGISTRY = new LinkedHashMap<>();

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

    public static final RegistryObject<EntityType<CatfishEntity>> CATFISH = register("catfish", EntityTypeDataHolder.of(
                    () -> EntityType.Builder.of(CatfishEntity::new, MobCategory.WATER_CREATURE)
                            .sized(0.6f, 0.4f)
                            .clientTrackingRange(4)
                            .build(Constants.MOD_ID + ":catfish"))
            .withSpawnPlacement(SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, WaterAnimal::checkSurfaceWaterAnimalSpawnRules)
            .withTranslation("Catfish")
            .drops(ModItems.CATFISH::get)
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityTypeDataHolder<T> holder) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name);
        ENTITY_REGISTRY.put(id, holder);
        return ENTITIES.register(name, holder::get);
    }

    public static Map<ResourceLocation, EntityTypeDataHolder<?>> getEntityRegistry() {
        return ENTITY_REGISTRY;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerSpawnPlacements() {
        for (EntityTypeDataHolder<?> holder : ENTITY_REGISTRY.values()) {
            if (holder.hasSpawnPlacement()) {
                SpawnPlacementsAccessor.invokeRegister(
                        (EntityType<? extends Mob>) holder.get(),
                        holder.getPlacementType(),
                        holder.getHeightmap(),
                        (SpawnPlacements.SpawnPredicate) holder.getSpawnPredicate()
                );
            }
        }
    }

    public static void load() {
    }
}