package com.evandev.watery_depths.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AlgalBlock extends FallingBlock implements BonemealableBlock {

    public AlgalBlock(Properties properties, Block baseBlock) {
        super(properties);
    }

    @Override
    public boolean isRandomlyTicking(@NotNull BlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(@NotNull BlockState state, ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!level.isClientSide && level.isWaterAt(pos.above())) {
            // Check for nearby Kelp
            boolean hasKelp = false;
            for (BlockPos checkPos : BlockPos.betweenClosed(pos.offset(-3, -2, -3), pos.offset(3, 2, 3))) {
                BlockState checkState = level.getBlockState(checkPos);
                if (checkState.is(Blocks.KELP) || checkState.is(Blocks.KELP_PLANT)) {
                    hasKelp = true;
                    break;
                }
            }

            if (hasKelp && random.nextInt(4) == 0) {
                // Try spreading to nearby blocks
                BlockPos targetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                BlockState targetState = level.getBlockState(targetPos);
                BlockState newState = getAlgalVariant(targetState);

                if (newState != null && level.isWaterAt(targetPos.above())) {
                    level.setBlockAndUpdate(targetPos, newState);
                }
            }
        }
    }

    private BlockState getAlgalVariant(BlockState state) {
        // You can convert this to a Map or Registry lookup in the future
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (state.is(Blocks.SAND))
            return BuiltInRegistries.BLOCK.get(new ResourceLocation("watery_depths", "algal_sand")).defaultBlockState();
        if (state.is(Blocks.GRAVEL))
            return BuiltInRegistries.BLOCK.get(new ResourceLocation("watery_depths", "algal_gravel")).defaultBlockState();
        if (id.getPath().equals("silt"))
            return BuiltInRegistries.BLOCK.get(new ResourceLocation("watery_depths", "algal_silt")).defaultBlockState();
        return null;
    }

    // --- BonemealableBlock implementation ---
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return level.isWaterAt(pos.above());
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        // Bonemealing works like a forced random tick spread but guarantees it
        for (int i = 0; i < 4; i++) {
            BlockPos targetPos = pos.offset(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
            BlockState targetState = level.getBlockState(targetPos);
            BlockState newState = getAlgalVariant(targetState);
            if (newState != null && level.isWaterAt(targetPos.above())) {
                level.setBlockAndUpdate(targetPos, newState);
            }
        }
    }
}