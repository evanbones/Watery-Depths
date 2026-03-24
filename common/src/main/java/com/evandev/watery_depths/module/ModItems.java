package com.evandev.watery_depths.module;

import com.evandev.watery_depths.CommonClass;
import com.evandev.watery_depths.registration.holders.ItemDataHolder;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModItems {
    public static final ModelTemplate SPAWN_EGG = new ModelTemplate(Optional.of(new ResourceLocation("minecraft", "item/template_spawn_egg")), Optional.empty());

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

    public static final ItemDataHolder<?> CATFISH_BUCKET = register("catfish_bucket", ItemDataHolder.of(() ->
                    new Item(new Item.Properties().stacksTo(1)))
            .withModel(ModelTemplates.FLAT_ITEM)
            .withTranslation("Catfish Bucket")
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