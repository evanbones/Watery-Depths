package com.evandev.watery_depths.block.entity;

import com.evandev.watery_depths.block.TubewormBlock;
import com.evandev.watery_depths.block.TubewormPart;
import com.evandev.watery_depths.module.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TubewormBlockEntity extends BlockEntity {
    public TubewormBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TUBEWORM.get(), pos, state);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T entity) {
        if (state.getValue(TubewormBlock.PART) == TubewormPart.MIDDLE || state.getValue(TubewormBlock.SHEARED)) {
            if (state.getValue(TubewormBlock.HIDING)) {
                level.setBlockAndUpdate(pos, state.setValue(TubewormBlock.HIDING, false));
            }
            return;
        }

        // search for players within 4 blocks
        boolean hasPlayerNearby = level.hasNearbyAlivePlayer(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 4.0D);
        boolean currentlyHiding = state.getValue(TubewormBlock.HIDING);

        if (hasPlayerNearby != currentlyHiding) {
            level.setBlockAndUpdate(pos, state.setValue(TubewormBlock.HIDING, hasPlayerNearby));
        }
    }
}