package com.evandev.watery_depths.registration.holders;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ItemDataHolder<T extends Item> {
    private static final Map<TagKey<Item>, List<ItemDataHolder<?>>> ITEM_TAGS = new HashMap<>();
    private final Supplier<T> entrySupplier;
    private T cachedEntry;
    private ModelTemplate model;
    private String defaultTranslation;
    private int fuelDuration;

    public ItemDataHolder(Supplier<T> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static ItemDataHolder<? extends Item> of(Supplier<? extends Item> itemSupplier) {
        return new ItemDataHolder<>(itemSupplier);
    }

    public static Map<TagKey<Item>, List<ItemDataHolder<?>>> getItemTags() {
        return ITEM_TAGS;
    }

    public T get() {
        if (this.cachedEntry != null) return cachedEntry;

        T entry = entrySupplier.get();
        this.cachedEntry = entry;

        return entry;
    }

    public ItemDataHolder<?> withFuel(int fuelDuration) {
        this.fuelDuration = fuelDuration;
        return this;
    }

    public boolean isFuel() {
        return this.fuelDuration > 0;
    }

    public int getFuelDuration() {
        return this.fuelDuration;
    }

    @SafeVarargs
    public final ItemDataHolder<?> withTags(TagKey<Item>... tags) {
        for (TagKey<Item> tag : tags) {
            ITEM_TAGS.putIfAbsent(tag, new ArrayList<>());
            ITEM_TAGS.get(tag).add(this);
        }
        return this;
    }

    public ItemDataHolder<?> withModel(ModelTemplate model) {
        this.model = model;
        return this;
    }

    public boolean hasModel() {
        return this.model != null;
    }

    public ModelTemplate getModel() {
        return this.model;
    }

    public ItemDataHolder<?> withTranslation(String translation) {
        this.defaultTranslation = translation;
        return this;
    }

    public boolean hasTranslation() {
        return this.defaultTranslation != null;
    }

    public String getTranslation() {
        return this.defaultTranslation;
    }
}