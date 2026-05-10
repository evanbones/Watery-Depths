package com.evandev.watery_depths.world.feature;

import com.evandev.watery_depths.CommonClass;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> SILT_DISK = create("silt_disk");
    public static final ResourceKey<PlacedFeature> SAND_DISK = create("sand_disk");
    public static final ResourceKey<PlacedFeature> CLAY_DISK = create("clay_disk");

    public static ResourceKey<PlacedFeature> create(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, CommonClass.makeID(name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configLookup = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(SILT_DISK, new PlacedFeature(configLookup.getOrThrow(ModConfiguredFeatures.SILT_DISK),
                List.of(
                        CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        BiomeFilter.biome()
                )
        ));

        context.register(SAND_DISK, new PlacedFeature(configLookup.getOrThrow(ModConfiguredFeatures.SAND_DISK),
                List.of(
                        CountPlacement.of(3),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        BiomeFilter.biome()
                )
        ));

        context.register(CLAY_DISK, new PlacedFeature(configLookup.getOrThrow(ModConfiguredFeatures.CLAY_DISK),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR_WG),
                        BiomeFilter.biome()
                )
        ));
    }
}