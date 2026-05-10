package com.evandev.watery_depths.world.feature.tree.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import org.jetbrains.annotations.NotNull;

public class CypressFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<CypressFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(instance ->
            foliagePlacerParts(instance).apply(instance, CypressFoliagePlacer::new)
    );

    public CypressFoliagePlacer(IntProvider radius, IntProvider offset) {
        super(radius, offset);
    }

    @Override
    protected @NotNull FoliagePlacerType<?> type() {
        return ModPlacerTypes.CYPRESS_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(@NotNull LevelSimulatedReader level, @NotNull FoliageSetter setter, RandomSource random, @NotNull TreeConfiguration config, int maxFreeTreeHeight, FoliageAttachment attachment, int foliageHeight, int foliageRadius, int offset) {
        BlockPos topPos = attachment.pos().above(offset);

        int topSpireHeight = 2 + random.nextInt(2);
        for (int i = 0; i < topSpireHeight; i++) {
            tryPlaceLeaf(level, setter, random, config, topPos.above(i));
        }

        int midTiers = 2 + random.nextInt(2);
        for (int i = 0; i < midTiers; i++) {
            BlockPos layerPos = topPos.below(i);
            tryPlaceLeaf(level, setter, random, config, layerPos.north());
            tryPlaceLeaf(level, setter, random, config, layerPos.south());
            tryPlaceLeaf(level, setter, random, config, layerPos.east());
            tryPlaceLeaf(level, setter, random, config, layerPos.west());
        }

        int baseTiers = 1 + random.nextInt(2);
        for (int i = 0; i < baseTiers; i++) {
            BlockPos layerPos = topPos.below(midTiers + i);
            this.placeLeavesRow(level, setter, random, config, layerPos, 2, 0, attachment.doubleTrunk());
        }
    }

    @Override
    public int foliageHeight(@NotNull RandomSource random, int height, @NotNull TreeConfiguration config) {
        return 6;
    }

    @Override
    protected boolean shouldSkipLocation(@NotNull RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
        return Math.abs(localX) == range && Math.abs(localZ) == range;
    }
}