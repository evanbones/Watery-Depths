package com.evandev.watery_depths.registration.holders;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class EntityTypeDataHolder<T extends Entity> {
    private final Supplier<EntityType<T>> entrySupplier;
    private EntityType<T> cachedEntry;
    private String defaultTranslation;
    private SpawnPlacementType placementType;
    private Heightmap.Types heightmap;
    private SpawnPlacements.SpawnPredicate<?> spawnPredicate;
    private Supplier<ItemLike> drop;

    private Supplier<AttributeSupplier.Builder> attributesBuilderSupplier;

    public EntityTypeDataHolder(Supplier<EntityType<T>> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static <U extends Entity> EntityTypeDataHolder<U> of(Supplier<EntityType<U>> entityTypeSupplier) {
        return new EntityTypeDataHolder<>(entityTypeSupplier);
    }

    public EntityTypeDataHolder<T> withTranslation(String translation) {
        this.defaultTranslation = translation;
        return this;
    }

    public boolean hasTranslation() {
        return this.defaultTranslation != null;
    }

    public String getTranslation() {
        return this.defaultTranslation;
    }

    public EntityType<T> get() {
        if (this.cachedEntry != null) return cachedEntry;

        EntityType<T> entry = entrySupplier.get();
        this.cachedEntry = entry;

        return entry;
    }

    public EntityTypeDataHolder<T> attributes(Supplier<AttributeSupplier.Builder> attributesBuilderSupplier) {
        this.attributesBuilderSupplier = attributesBuilderSupplier;
        return this;
    }

    public boolean hasAttributes() {
        return this.attributesBuilderSupplier != null;
    }

    public Supplier<AttributeSupplier.Builder> getAttributesSupplier() {
        return this.attributesBuilderSupplier;
    }

    public <M extends Mob> EntityTypeDataHolder<T> withSpawnPlacement(SpawnPlacementType type, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<M> predicate) {
        this.placementType = type;
        this.heightmap = heightmap;
        this.spawnPredicate = predicate;
        return this;
    }

    public EntityTypeDataHolder<T> drops(Supplier<ItemLike> drop) {
        this.drop = drop;
        return this;
    }

    public boolean hasSpawnPlacement() {
        return this.placementType != null;
    }

    public SpawnPlacementType getPlacementType() {
        return this.placementType;
    }

    public Heightmap.Types getHeightmap() {
        return this.heightmap;
    }

    public SpawnPlacements.SpawnPredicate<?> getSpawnPredicate() {
        return this.spawnPredicate;
    }

    public boolean hasDrop() {
        return this.drop != null;
    }

    public Supplier<ItemLike> getDrop() {
        return this.drop;
    }
}