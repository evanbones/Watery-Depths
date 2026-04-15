package com.evandev.watery_depths.world.feature.tree.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class CypressSkirtDecorator extends TreeDecorator {
    public static final Codec<CypressSkirtDecorator> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockStateProvider.CODEC.fieldOf("block_provider").forGetter(d -> d.blockProvider)
            ).apply(instance, CypressSkirtDecorator::new)
    );

    private final BlockStateProvider blockProvider;

    public CypressSkirtDecorator(BlockStateProvider blockProvider) {
        this.blockProvider = blockProvider;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModDecoratorTypes.CYPRESS_SKIRT.get();
    }

    @Override
    public void place(Context context) {
        List<BlockPos> logs = context.logs();
        if (logs.isEmpty()) return;

        BlockPos midLog = logs.get(logs.size() / 2);

        context.setBlock(midLog.north(), this.blockProvider.getState(context.random(), midLog.north()));
        context.setBlock(midLog.south(), this.blockProvider.getState(context.random(), midLog.south()));
        context.setBlock(midLog.east(), this.blockProvider.getState(context.random(), midLog.east()));
        context.setBlock(midLog.west(), this.blockProvider.getState(context.random(), midLog.west()));
    }
}