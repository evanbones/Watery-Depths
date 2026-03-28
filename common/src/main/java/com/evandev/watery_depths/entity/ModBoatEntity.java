package com.evandev.watery_depths.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModBoatEntity extends Boat {
    private final Supplier<Item> dropItem;

    public ModBoatEntity(EntityType<? extends Boat> entityType, Level level, Supplier<Item> dropItem) {
        super(entityType, level);
        this.dropItem = dropItem;
    }

    @Override
    public @NotNull Item getDropItem() {
        return dropItem.get();
    }
}