package com.evandev.watery_depths.world.biome;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.mixin.accessor.ChunkGeneratorAccessor;
import com.google.common.base.Suppliers;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.FeatureSorter;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WateryDepthsBiomeSlicesManager {

    public static void onServerAboutToStart(MinecraftServer server) {
        List<WateryDepthsBiomeSource.ModdedBiomeSlice> loadedSlices = new ArrayList<>();
        ResourceManager resourceManager = server.getResourceManager();
        Map<ResourceLocation, Resource> resources = resourceManager.listResources("watery_depths_biome_slices", id -> id.getPath().endsWith(".json"));

        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            try (Reader reader = entry.getValue().openAsReader()) {
                JsonElement json = JsonParser.parseReader(reader);
                MultiNoiseModdedBiomeSlice.CODEC.parse(JsonOps.INSTANCE, json)
                        .resultOrPartial(err -> Constants.LOG.error("Failed to parse biome slice: {}", err))
                        .ifPresent(loadedSlices::add);
            } catch (Exception e) {
                Constants.LOG.error("Failed to read biome slice: {}", entry.getKey());
                e.printStackTrace();
            }
        }

        if (loadedSlices.isEmpty()) return;

        RegistryAccess registryAccess = server.registryAccess();
        Registry<Biome> biomeRegistry = registryAccess.registryOrThrow(Registries.BIOME);
        Registry<LevelStem> dimensions = registryAccess.registryOrThrow(Registries.LEVEL_STEM);
        long seed = server.getWorldData().worldGenOptions().seed();

        for (Map.Entry<ResourceKey<LevelStem>, LevelStem> entry : dimensions.entrySet()) {
            if (entry.getKey().equals(LevelStem.OVERWORLD)) {
                ChunkGenerator chunkGenerator = entry.getValue().generator();
                BiomeSource source = chunkGenerator.getBiomeSource();

                if (!(source instanceof WateryDepthsBiomeSource)) {
                    WateryDepthsBiomeSource moddedSource = new WateryDepthsBiomeSource(biomeRegistry, source, loadedSlices, 8, seed, entry.getKey().location().hashCode());
                    ((ChunkGeneratorAccessor) chunkGenerator).setBiomeSource(moddedSource);
                    ((ChunkGeneratorAccessor) chunkGenerator).setFeaturesPerStep(Suppliers.memoize(() -> {
                        return FeatureSorter.buildFeaturesPerStep(List.copyOf(moddedSource.possibleBiomes()), (biomeHolder) -> {
                            return chunkGenerator.getBiomeGenerationSettings(biomeHolder).features();
                        }, true);
                    }));
                }
            }
        }
    }

    public static class MultiNoiseModdedBiomeSlice implements WateryDepthsBiomeSource.ModdedBiomeSlice {
        private static final Codec<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> PAIR_CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Climate.ParameterPoint.CODEC.fieldOf("parameters").forGetter(Pair::getFirst),
                ResourceKey.codec(Registries.BIOME).fieldOf("biome").forGetter(Pair::getSecond)
        ).apply(instance, Pair::of));

        public static final Codec<MultiNoiseModdedBiomeSlice> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.INT.fieldOf("weight").forGetter(MultiNoiseModdedBiomeSlice::weight),
                PAIR_CODEC.listOf().fieldOf("biomes").forGetter(slice -> slice.biomeKeys)
        ).apply(instance, MultiNoiseModdedBiomeSlice::new));

        private final int weight;
        private final List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeKeys;
        private Climate.ParameterList<Holder<Biome>> resolvedBiomes;

        public MultiNoiseModdedBiomeSlice(int weight, List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> biomeKeys) {
            this.weight = weight;
            this.biomeKeys = biomeKeys;
        }

        @Override
        public int weight() {
            return weight;
        }

        @Override
        public void resolveBiomes(Registry<Biome> biomes) {
            List<Pair<Climate.ParameterPoint, Holder<Biome>>> list = new ArrayList<>();
            for (var pair : this.biomeKeys) {
                biomes.getHolder(pair.getSecond()).ifPresent(holder -> list.add(Pair.of(pair.getFirst(), holder)));
            }
            if (!list.isEmpty()) {
                this.resolvedBiomes = new Climate.ParameterList<>(list);
            }
        }

        @Override
        public Holder<Biome> getNoiseBiome(int x, int y, int z, Climate.Sampler sampler, BiomeSource original, Registry<Biome> biomes) {
            if (this.resolvedBiomes == null) return null;
            return this.resolvedBiomes.findValue(sampler.sample(x, y, z));
        }

        @Override
        public List<Holder<Biome>> possibleBiomes(Registry<Biome> biomes) {
            return this.biomeKeys.stream()
                    .flatMap(pair -> biomes.getHolder(pair.getSecond()).stream())
                    .map(holder -> (Holder<Biome>) holder)
                    .toList();
        }
    }
}