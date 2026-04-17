package com.evandev.watery_depths.platform.services;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.function.Supplier;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the configuration directory for the current platform.
     *
     * @return The path to the config directory.
     */
    Path getConfigDirectory();

    /**
     * Checks if the code is running on the physical client.
     *
     * @return True if on the client, false if on a dedicated server.
     */
    boolean isPhysicalClient();

    /**
     * Creates a BlockEntityType using platform-specific builders.
     */
    <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(BlockEntityFactory<T> factory, Block... blocks);

    /**
     * Creates a Spawn Egg Item using platform-specific logic.
     */
    Supplier<SpawnEggItem> createSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> entityType, int primaryColor, int secondaryColor, Item.Properties properties);

    /**
     * Creates a Mob Bucket Item using platform-specific logic.
     */
    Supplier<Item> createMobBucketItem(Supplier<? extends EntityType<?>> entityType, Supplier<? extends Fluid> fluid, Supplier<? extends SoundEvent> soundEvent, Item.Properties properties);

    @FunctionalInterface
    interface BlockEntityFactory<T extends BlockEntity> {
        @NotNull T create(BlockPos pos, BlockState state);
    }
}