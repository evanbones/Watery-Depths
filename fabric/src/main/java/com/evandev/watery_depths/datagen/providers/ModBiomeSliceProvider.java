package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.world.biome.WateryDepthsBiomeSlicesManager.MultiNoiseModdedBiomeSlice;
import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ModBiomeSliceProvider implements DataProvider {
    public static final ResourceKey<Biome> VANILLA = ResourceKey.create(Registries.BIOME, new ResourceLocation("watery_depths", "original_source_marker"));
    public static final ResourceKey<Biome> BAYOU = ResourceKey.create(Registries.BIOME, new ResourceLocation("watery_depths", "bayou"));
    private final PackOutput output;

    public ModBiomeSliceProvider(PackOutput output) {
        this.output = output;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> futures = new ArrayList<>();

        List<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> bayouSlicePoints = new ArrayList<>();
        WateryDepthsBiomeBuilder builder = new WateryDepthsBiomeBuilder();
        builder.addBiomes(bayouSlicePoints::add);

        MultiNoiseModdedBiomeSlice bayouSlice = new MultiNoiseModdedBiomeSlice(8, bayouSlicePoints);
        JsonElement json = MultiNoiseModdedBiomeSlice.CODEC.encodeStart(JsonOps.INSTANCE, bayouSlice).getOrThrow(false, System.err::println);

        PackOutput.PathProvider pathProvider = this.output.createPathProvider(PackOutput.Target.DATA_PACK, "watery_depths_biome_slices");
        futures.add(DataProvider.saveStable(cache, json, pathProvider.json(new ResourceLocation("watery_depths", "overworld_slice"))));

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
    }

    @Override
    public @NotNull String getName() {
        return "Watery Depths Biome Slices";
    }

    private static final class WateryDepthsBiomeBuilder {
        private final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
        private final Climate.Parameter[] temperatures = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.45F), Climate.Parameter.span(-0.45F, -0.15F), Climate.Parameter.span(-0.15F, 0.2F), Climate.Parameter.span(0.2F, 0.55F), Climate.Parameter.span(0.55F, 1.0F)};
        private final Climate.Parameter[] humidities = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.35F), Climate.Parameter.span(-0.35F, -0.1F), Climate.Parameter.span(-0.1F, 0.1F), Climate.Parameter.span(0.1F, 0.3F), Climate.Parameter.span(0.3F, 1.0F)};
        private final Climate.Parameter[] erosions = new Climate.Parameter[]{Climate.Parameter.span(-1.0F, -0.78F), Climate.Parameter.span(-0.78F, -0.375F), Climate.Parameter.span(-0.375F, -0.2225F), Climate.Parameter.span(-0.2225F, 0.05F), Climate.Parameter.span(0.05F, 0.45F), Climate.Parameter.span(0.45F, 0.55F), Climate.Parameter.span(0.55F, 1.0F)};
        private final Climate.Parameter mushroomFieldsContinentalness = Climate.Parameter.span(-1.2F, -1.05F);
        private final Climate.Parameter deepOceanContinentalness = Climate.Parameter.span(-1.05F, -0.455F);
        private final Climate.Parameter oceanContinentalness = Climate.Parameter.span(-0.455F, -0.19F);
        private final Climate.Parameter coastContinentalness = Climate.Parameter.span(-0.19F, -0.11F);
        private final Climate.Parameter nearInlandContinentalness = Climate.Parameter.span(-0.11F, 0.03F);
        private final Climate.Parameter midInlandContinentalness = Climate.Parameter.span(0.03F, 0.3F);
        private final Climate.Parameter farInlandContinentalness = Climate.Parameter.span(0.3F, 1.0F);

        private final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}
        };

        private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, BAYOU, BAYOU},
                {VANILLA, VANILLA, VANILLA, BAYOU, BAYOU}
        };

        private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA}
        };

        private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        private final ResourceKey<Biome>[][] SHATTERED_BIOMES = new ResourceKey[][]{
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {VANILLA, VANILLA, VANILLA, VANILLA, VANILLA},
                {null, null, null, null, null},
                {null, null, null, null, null}
        };

        public void addBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
            this.addOffCoastBiomes(consumer);
            this.addInlandBiomes(consumer);
            this.addUndergroundBiomes(consumer);
        }

        private void addOffCoastBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
            this.addSurfaceBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
            for (int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter temp = this.temperatures[i];
                this.addSurfaceBiome(consumer, temp, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[0][i]);
                this.addSurfaceBiome(consumer, temp, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[1][i]);
            }
        }

        private void addInlandBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
            this.addMidSlice(consumer, Climate.Parameter.span(-1.0F, -0.93333334F));
            this.addHighSlice(consumer, Climate.Parameter.span(-0.93333334F, -0.7666667F));
            this.addPeaks(consumer, Climate.Parameter.span(-0.7666667F, -0.56666666F));
            this.addHighSlice(consumer, Climate.Parameter.span(-0.56666666F, -0.4F));
            this.addMidSlice(consumer, Climate.Parameter.span(-0.4F, -0.26666668F));
            this.addLowSlice(consumer, Climate.Parameter.span(-0.26666668F, -0.075F));
            this.addValleys(consumer, Climate.Parameter.span(-0.15F, -0.05F));
            this.addValleys(consumer, Climate.Parameter.span(-0.05F, 0.05F));
            this.addValleys(consumer, Climate.Parameter.span(0.05F, 0.15F));
            this.addLowSlice(consumer, Climate.Parameter.span(0.075F, 0.26666668F));
            this.addMidSlice(consumer, Climate.Parameter.span(0.26666668F, 0.4F));
            this.addHighSlice(consumer, Climate.Parameter.span(0.4F, 0.56666666F));
            this.addPeaks(consumer, Climate.Parameter.span(0.56666666F, 0.7666667F));
            this.addHighSlice(consumer, Climate.Parameter.span(0.7666667F, 0.93333334F));
            this.addMidSlice(consumer, Climate.Parameter.span(0.93333334F, 1.0F));
        }

        private void addPeaks(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
            for (int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter temp = this.temperatures[i];
                for (int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter hum = this.humidities[j];
                    ResourceKey<Biome> res1 = this.pickMiddleBiome(i, j, weirdness);
                    ResourceKey<Biome> res2 = this.pickPlateauBiome(i, j, weirdness);
                    ResourceKey<Biome> res3 = this.pickShatteredBiome(i, j, weirdness);

                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[0], weirdness, 0.0F, VANILLA);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], weirdness, 0.0F, res2);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, res3);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, res1);
                }
            }
        }

        private void addHighSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
            for (int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter temp = this.temperatures[i];
                for (int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter hum = this.humidities[j];
                    ResourceKey<Biome> res1 = this.pickMiddleBiome(i, j, weirdness);
                    ResourceKey<Biome> res2 = this.pickPlateauBiome(i, j, weirdness);
                    ResourceKey<Biome> res3 = this.pickShatteredBiome(i, j, weirdness);

                    this.addSurfaceBiome(consumer, temp, hum, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[2], weirdness, 0.0F, res2);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, res3);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, res1);
                }
            }
        }

        private void addMidSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
            this.addSurfaceBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), weirdness, 0.0F, VANILLA);
            for (int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter temp = this.temperatures[i];
                for (int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter hum = this.humidities[j];
                    ResourceKey<Biome> res1 = this.pickMiddleBiome(i, j, weirdness);
                    ResourceKey<Biome> res2 = this.pickShatteredBiome(i, j, weirdness);
                    ResourceKey<Biome> res3 = this.pickPlateauBiome(i, j, weirdness);

                    this.addSurfaceBiome(consumer, temp, hum, this.farInlandContinentalness, this.erosions[1], weirdness, 0.0F, i == 0 ? VANILLA : res3);
                    this.addSurfaceBiome(consumer, temp, hum, this.nearInlandContinentalness, this.erosions[2], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, this.farInlandContinentalness, this.erosions[2], weirdness, 0.0F, res2);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.erosions[3], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, res3);
                    this.addSurfaceBiome(consumer, temp, hum, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, VANILLA);

                    if (i == 0)
                        this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, res1);
                }
            }
        }

        private void addLowSlice(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
            this.addSurfaceBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[2]), weirdness, 0.0F, VANILLA);
            for (int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter temp = this.temperatures[i];
                for (int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter hum = this.humidities[j];
                    ResourceKey<Biome> res1 = this.pickMiddleBiome(i, j, weirdness);

                    this.addSurfaceBiome(consumer, temp, hum, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[2], this.erosions[3]), weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, this.coastContinentalness, Climate.Parameter.span(this.erosions[3], this.erosions[4]), weirdness, 0.0F, VANILLA);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[4], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.erosions[5], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, VANILLA);

                    if (i == 0)
                        this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, res1);
                }
            }
        }

        private void addValleys(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter weirdness) {
            for (int i = 0; i < this.temperatures.length; ++i) {
                Climate.Parameter temp = this.temperatures[i];
                for (int j = 0; j < this.humidities.length; ++j) {
                    Climate.Parameter hum = this.humidities[j];
                    ResourceKey<Biome> res1 = this.pickMiddleBiome(i, j, weirdness);

                    this.addSurfaceBiome(consumer, temp, hum, this.coastContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, VANILLA);
                    this.addSurfaceBiome(consumer, temp, hum, this.nearInlandContinentalness, Climate.Parameter.span(this.erosions[0], this.erosions[1]), weirdness, 0.0F, VANILLA);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.erosions[2], this.erosions[5]), weirdness, 0.0F, VANILLA);

                    this.addSurfaceBiome(consumer, temp, hum, this.coastContinentalness, this.erosions[6], weirdness, 0.0F, res1);
                    this.addSurfaceBiome(consumer, temp, hum, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.erosions[6], weirdness, 0.0F, res1);
                }
            }
        }

        private void addUndergroundBiomes(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer) {
            this.addUndergroundBiome(consumer, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
            this.addUndergroundBiome(consumer, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, VANILLA);
        }

        private ResourceKey<Biome> pickMiddleBiome(int t, int h, Climate.Parameter weirdness) {
            ResourceKey<Biome> res = weirdness.max() < 0L ? this.MIDDLE_BIOMES[t][h] : (this.MIDDLE_BIOMES_VARIANT[t][h] == null ? this.MIDDLE_BIOMES[t][h] : this.MIDDLE_BIOMES_VARIANT[t][h]);
            return res == null && (weirdness.min() >= 4000.0F || weirdness.max() <= -4000F) ? VANILLA : res;
        }

        private ResourceKey<Biome> pickPlateauBiome(int t, int h, Climate.Parameter weirdness) {
            ResourceKey<Biome> res = weirdness.max() < 0L ? this.PLATEAU_BIOMES[t][h] : (this.PLATEAU_BIOMES_VARIANT[t][h] == null ? this.PLATEAU_BIOMES[t][h] : this.PLATEAU_BIOMES_VARIANT[t][h]);
            return res == null ? VANILLA : res;
        }

        private ResourceKey<Biome> pickShatteredBiome(int t, int h, Climate.Parameter weirdness) {
            ResourceKey<Biome> res = this.SHATTERED_BIOMES[t][h];
            return res == null ? this.pickMiddleBiome(t, h, weirdness) : res;
        }

        private void addSurfaceBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter t, Climate.Parameter h, Climate.Parameter c, Climate.Parameter e, Climate.Parameter w, float depth, ResourceKey<Biome> biome) {
            consumer.accept(Pair.of(Climate.parameters(t, h, c, e, Climate.Parameter.point(0.0F), w, depth), biome));
            consumer.accept(Pair.of(Climate.parameters(t, h, c, e, Climate.Parameter.point(1.0F), w, depth), biome));
        }

        private void addUndergroundBiome(Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> consumer, Climate.Parameter t, Climate.Parameter h, Climate.Parameter c, Climate.Parameter e, Climate.Parameter w, float depth, ResourceKey<Biome> biome) {
            consumer.accept(Pair.of(Climate.parameters(t, h, c, e, Climate.Parameter.span(0.2F, 0.9F), w, depth), biome));
        }
    }
}