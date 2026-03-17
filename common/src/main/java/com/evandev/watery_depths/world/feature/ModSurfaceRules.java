package com.evandev.watery_depths.world.feature;

import com.evandev.watery_depths.module.ModBlocks;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource SILT = SurfaceRules.state(ModBlocks.SILT.get().defaultBlockState());

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isUnderwater = SurfaceRules.waterBlockCheck(-1, 0);

        SurfaceRules.ConditionSource isColdOcean = SurfaceRules.isBiome(
                Biomes.COLD_OCEAN,
                Biomes.DEEP_COLD_OCEAN,
                Biomes.FROZEN_OCEAN,
                Biomes.DEEP_FROZEN_OCEAN
        );

        SurfaceRules.ConditionSource isRiver = SurfaceRules.isBiome(
                Biomes.RIVER,
                Biomes.FROZEN_RIVER
        );

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(
                        isUnderwater,
                        SurfaceRules.sequence(
                                SurfaceRules.ifTrue(isColdOcean, SILT),
                                SurfaceRules.ifTrue(isRiver, SILT)
                        )
                )
        );
    }
}