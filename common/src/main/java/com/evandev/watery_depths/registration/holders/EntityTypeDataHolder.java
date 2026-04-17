package com.evandev.watery_depths.registration.holders;

import com.google.common.collect.ImmutableSet;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class EntityTypeDataHolder<T extends Entity> {
    private final Supplier<EntityType<T>> entrySupplier;
    private EntityType<T> cachedEntry;
    private String defaultTranslation;
    private SpawnPlacements.Type placementType;
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

    public <M extends Mob> EntityTypeDataHolder<T> withSpawnPlacement(SpawnPlacements.Type type, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<M> predicate) {
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

    public SpawnPlacements.Type getPlacementType() {
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

    public static class Builder<T extends Entity> {
        private final EntityType.EntityFactory<T> factory;
        private final MobCategory category;
        private ImmutableSet<Block> immuneTo = ImmutableSet.of();
        private boolean serialize = true;
        private boolean summon = true;
        private boolean fireImmune;
        private boolean canSpawnFarFromPlayer;
        private int clientTrackingRange = 5;
        private int updateInterval = 3;
        private EntityDimensions dimensions = EntityDimensions.scalable(0.6F, 1.8F);
        private FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;

        private Builder(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory) {
            this.factory = entityFactory;
            this.category = mobCategory;
            this.canSpawnFarFromPlayer = mobCategory == MobCategory.CREATURE || mobCategory == MobCategory.MISC;
        }

        public static <T extends Entity> Builder<T> of(EntityType.EntityFactory<T> entityFactory, MobCategory mobCategory) {
            return new Builder<>(entityFactory, mobCategory);
        }

        public Builder<T> sized(float width, float height) {
            this.dimensions = EntityDimensions.scalable(width, height);
            return this;
        }

        public Builder<T> noSummon() {
            this.summon = false;
            return this;
        }

        public Builder<T> noSave() {
            this.serialize = false;
            return this;
        }

        public Builder<T> fireImmune() {
            this.fireImmune = true;
            return this;
        }

        public Builder<T> immuneTo(Block... blocks) {
            this.immuneTo = ImmutableSet.copyOf(blocks);
            return this;
        }

        public Builder<T> canSpawnFarFromPlayer() {
            this.canSpawnFarFromPlayer = true;
            return this;
        }

        public Builder<T> clientTrackingRange(int chunkRange) {
            this.clientTrackingRange = chunkRange;
            return this;
        }

        public Builder<T> updateInterval(int interval) {
            this.updateInterval = interval;
            return this;
        }

        public Builder<T> requiredFeatures(FeatureFlag... $$0) {
            this.requiredFeatures = FeatureFlags.REGISTRY.subset($$0);
            return this;
        }

        public EntityType<T> build() {
            return new EntityType<>(this.factory, this.category, this.serialize, this.summon, this.fireImmune, this.canSpawnFarFromPlayer, this.immuneTo, this.dimensions, this.clientTrackingRange, this.updateInterval, this.requiredFeatures);
        }
    }
}