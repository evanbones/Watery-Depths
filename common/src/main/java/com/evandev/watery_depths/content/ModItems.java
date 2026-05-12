package com.evandev.watery_depths.content;

import com.evandev.watery_depths.CommonClass;
import com.evandev.watery_depths.item.ModBoatItem;
import com.evandev.watery_depths.platform.Services;
import com.evandev.watery_depths.registration.holders.ItemDataHolder;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.material.Fluids;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModItems {
    public static final ModelTemplate SPAWN_EGG = new ModelTemplate(Optional.of(ResourceLocation.withDefaultNamespace("item/template_spawn_egg")), Optional.empty());

    /**
     * Map of all Item Resource Locations to their ItemDataHolders.
     */
    private static final Map<ResourceLocation, ItemDataHolder<?>> ITEM_REGISTRY = new HashMap<>();

    public static final ItemDataHolder<?> TAB_ICON = register("tab_icon", ItemDataHolder.of(() ->
                    new Item(new Item.Properties()))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Watery Depths")
    );

    public static final ItemDataHolder<?> CATFISH = register("catfish", ItemDataHolder.of(() ->
                    new Item(new Item.Properties()))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Catfish")
    );

    public static final ItemDataHolder<?> CATFISH_BUCKET = register("catfish_bucket", ItemDataHolder.of(
                    Services.PLATFORM.createMobBucketItem(ModEntities.CATFISH, () -> Fluids.WATER, () -> SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Catfish Bucket")
    );

    public static final ItemDataHolder<?> CATFISH_SPAWN_EGG = register("catfish_spawn_egg", ItemDataHolder.of(
                    Services.PLATFORM.createSpawnEggItem(ModEntities.CATFISH, 0x545B49, 0x939A86, new Item.Properties()))
            .withModel(SPAWN_EGG)
            .withTranslation("Catfish Spawn Egg")
    );

    public static final ItemDataHolder<?> CYPRESS_SIGN = register("cypress_sign", ItemDataHolder.of(() ->
                    new SignItem(new Item.Properties().stacksTo(16), ModBlocks.CYPRESS_SIGN.get(), ModBlocks.CYPRESS_WALL_SIGN.get()))
            .withModel(ModelTemplates.FLAT_ITEM)
    );

    public static final ItemDataHolder<?> CYPRESS_HANGING_SIGN = register("cypress_hanging_sign", ItemDataHolder.of(() ->
                    new HangingSignItem(ModBlocks.CYPRESS_HANGING_SIGN.get(), ModBlocks.CYPRESS_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)))
            .withModel(ModelTemplates.FLAT_ITEM)
    );

    public static final ItemDataHolder<?> CYPRESS_BOAT = register("cypress_boat", ItemDataHolder.of(() ->
                    new ModBoatItem(ModEntities.CYPRESS_BOAT::get, new Item.Properties().stacksTo(1)))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Cypress Boat")
    );

    public static final ItemDataHolder<?> CYPRESS_CHEST_BOAT = register("cypress_chest_boat", ItemDataHolder.of(() ->
                    new ModBoatItem(ModEntities.CYPRESS_CHEST_BOAT::get, new Item.Properties().stacksTo(1)))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Cypress Boat with Chest")
    );

    public static ItemDataHolder<?> register(String name, ItemDataHolder<?> itemDataHolder) {
        return register(CommonClass.makeID(name), itemDataHolder);
    }

    public static ItemDataHolder<?> register(ResourceLocation id, ItemDataHolder<?> itemDataHolder) {
        ITEM_REGISTRY.put(id, itemDataHolder);
        return itemDataHolder;
    }

    public static void registerItems(BiConsumer<ResourceLocation, Supplier<Item>> itemRegister) {
        for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : ITEM_REGISTRY.entrySet()) {
            itemRegister.accept(entry.getKey(), entry.getValue()::get);
        }
    }

    public static Map<ResourceLocation, ItemDataHolder<?>> getItemRegistry() {
        return ITEM_REGISTRY;
    }

    public static void load() {
    }
}