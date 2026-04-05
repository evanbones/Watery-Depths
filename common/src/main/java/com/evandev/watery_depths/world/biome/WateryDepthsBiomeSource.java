package com.evandev.watery_depths.world.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LinearCongruentialGenerator;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public final class WateryDepthsBiomeSource extends BiomeSource {
    public static final ResourceLocation ORIGINAL_SOURCE_MARKER_LOCATION = new ResourceLocation("watery_depths", "original_source_marker");

    public static final Codec<BiomeSource> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            BiomeSource.CODEC.fieldOf("original_biome_source").forGetter(s -> s instanceof WateryDepthsBiomeSource w ? w.originalSource : s)
    ).apply(instance, source -> source));

    private final Registry<Biome> biomes;
    private final BiomeSource originalSource;
    private final List<ModdedBiomeSlice> slices;
    private final int totalWeight;
    private final int size;
    private final long slicesSeed;
    private final long slicesZoomSeed;
    private final ThreadLocal<PositionalRandomCache> positionalRandomCache = ThreadLocal.withInitial(PositionalRandomCache::new);

    public WateryDepthsBiomeSource(Registry<Biome> biomes, BiomeSource originalSource, List<ModdedBiomeSlice> slices, int size, long seed, long dimensionSeedModifier) {
        this.biomes = biomes;
        this.originalSource = originalSource;
        this.slices = slices;
        this.totalWeight = slices != null ? slices.stream().mapToInt(ModdedBiomeSlice::weight).sum() : 0;
        this.size = size;
        this.slicesSeed = seed + 1791510900 + dimensionSeedModifier;
        this.slicesZoomSeed = seed - 771160217 + dimensionSeedModifier;

        if (this.slices != null && this.biomes != null) {
            for (ModdedBiomeSlice slice : this.slices) {
                slice.resolveBiomes(this.biomes);
            }
        }
    }

    private static int nextInt(long seed, int x, int z, int bound) {
        return Math.floorMod(next(seed, x, z), bound);
    }

    private static long next(long seed, int x, int z) {
        long next = LinearCongruentialGenerator.next(seed, x);
        next = LinearCongruentialGenerator.next(next, z);
        next = LinearCongruentialGenerator.next(next, x);
        return LinearCongruentialGenerator.next(next, z) >> 24;
    }

    @Override
    protected @NotNull Codec<? extends BiomeSource> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull Stream<Holder<Biome>> collectPossibleBiomes() {
        Stream<Holder<Biome>> originalPossible = this.originalSource.possibleBiomes().stream();
        if (this.slices == null) return originalPossible;
        Stream<Holder<Biome>> slicePossible = this.slices.stream().flatMap(s -> s.possibleBiomes(this.biomes).stream());
        return Stream.concat(originalPossible, slicePossible);
    }

    @Override
    public @NotNull Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.@NotNull Sampler sampler) {
        if (this.slices == null || this.slices.isEmpty()) return this.originalSource.getNoiseBiome(x, y, z, sampler);

        long random = this.positionalRandomCache.get().getRandom(this, x, z);
        int randomWeight = Math.floorMod(random, this.totalWeight);

        for (ModdedBiomeSlice slice : this.slices) {
            if ((randomWeight -= slice.weight()) < 0) {
                Holder<Biome> biome = slice.getNoiseBiome(x, y, z, sampler, this.originalSource, this.biomes);
                if (biome != null && !biome.is(ORIGINAL_SOURCE_MARKER_LOCATION)) {
                    return biome;
                }
                break;
            }
        }

        return this.originalSource.getNoiseBiome(x, y, z, sampler);
    }

    private long computeZoomedPositionalRandom(int x, int z) {
        int cordX = QuartPos.toBlock(x);
        int cordZ = QuartPos.toBlock(z);
        long slicesZoomSeed = this.slicesZoomSeed;
        for (int i = 0; i < this.size; i++) {
            int cellPosX = cordX & 1;
            int cellPosZ = cordZ & 1;
            int cellX = cordX >> 1;
            int cellZ = cordZ >> 1;
            if (cellPosX == 0 && cellPosZ == 0) {
                cordX = cellX;
                cordZ = cellZ;
            } else if (cellPosX == 0) {
                if (nextInt(slicesZoomSeed, cellX << 1, cellZ << 1, 2) == 0) {
                    cordZ = cellZ;
                } else {
                    cordZ = (cordZ + 1) >> 1;
                }
                cordX = cellX;
            } else if (cellPosZ == 0) {
                if (nextInt(slicesZoomSeed, cellX << 1, cellZ << 1, 2) == 0) {
                    cordX = cellX;
                } else {
                    cordX = (cordX + 1) >> 1;
                }
                cordZ = cellZ;
            } else {
                int offsetChoice = nextInt(slicesZoomSeed, cellX << 1, cellZ << 1, 4);
                if (offsetChoice == 0) {
                    cordX = cellX;
                    cordZ = cellZ;
                } else if (offsetChoice == 1) {
                    cordX = (cordX + 1) >> 1;
                    cordZ = cellZ;
                } else if (offsetChoice == 2) {
                    cordX = cellX;
                    cordZ = (cordZ + 1) >> 1;
                } else {
                    cordX = (cordX + 1) >> 1;
                    cordZ = (cordZ + 1) >> 1;
                }
            }
        }
        return next(this.slicesSeed, cordX, cordZ);
    }

    public interface ModdedBiomeSlice {
        int weight();

        void resolveBiomes(Registry<Biome> biomes);

        Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, BiomeSource original, Registry<Biome> biomes);

        List<Holder<Biome>> possibleBiomes(Registry<Biome> biomes);
    }

    private static class PositionalRandomCache {
        private final long[] lastXZHashes = new long[256];
        private final long[] randoms = new long[256];

        private PositionalRandomCache() {
            Arrays.fill(lastXZHashes, Long.MIN_VALUE);
        }

        private long getRandom(WateryDepthsBiomeSource biomeSource, int x, int z) {
            int xIndex = SectionPos.sectionRelative(x);
            int zIndex = SectionPos.sectionRelative(z);
            int index = 16 * xIndex + zIndex;
            long xzHash = ChunkPos.asLong(x, z);
            if (this.lastXZHashes[index] != xzHash) {
                this.lastXZHashes[index] = xzHash;
                return this.randoms[index] = biomeSource.computeZoomedPositionalRandom(x, z);
            }
            return this.randoms[index];
        }
    }
}