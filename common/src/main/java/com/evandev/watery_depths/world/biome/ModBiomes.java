package com.evandev.watery_depths.world.biome;

import com.evandev.watery_depths.CommonClass;
import com.terraformersmc.biolith.api.biome.BiomePlacement;
import com.terraformersmc.biolith.api.biome.sub.BiomeParameterTargets;
import com.terraformersmc.biolith.api.biome.sub.CriterionBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class ModBiomes {
    public static final ResourceKey<Biome> BAYOU = ResourceKey.create(Registries.BIOME, CommonClass.makeID("bayou"));

    public static void load() {
        BiomePlacement.replaceOverworld(Biomes.MANGROVE_SWAMP, BAYOU, 0.25);

        BiomePlacement.addSubOverworld(
                Biomes.SWAMP,
                BAYOU,
                CriterionBuilder.valueMin(BiomeParameterTargets.TEMPERATURE, 0.2F)
        );
    }
}