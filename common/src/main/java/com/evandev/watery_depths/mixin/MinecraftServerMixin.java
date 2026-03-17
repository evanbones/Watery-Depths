package com.evandev.watery_depths.mixin;

import com.evandev.watery_depths.mixin.accessor.NoiseGeneratorSettingsAccessor;
import com.evandev.watery_depths.world.feature.ModSurfaceRules;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Inject(method = "createLevels", at = @At("HEAD"))
    private void watery_depths$appendSurfaceRules(ChunkProgressListener listener, CallbackInfo ci) {
        MinecraftServer server = (MinecraftServer) (Object) this;
        LevelStem levelStem = server.registries().compositeAccess()
                .registryOrThrow(Registries.LEVEL_STEM).get(LevelStem.OVERWORLD);

        if (levelStem != null && levelStem.generator() instanceof NoiseBasedChunkGenerator noiseGenerator) {
            NoiseGeneratorSettings settings = noiseGenerator.generatorSettings().value();
            SurfaceRules.RuleSource currentRules = settings.surfaceRule();

            if (currentRules instanceof SurfaceRules.SequenceRuleSource sequenceSource) {
                if (sequenceSource.sequence().contains(ModSurfaceRules.INSTANCE)) {
                    return;
                }
            }

            SurfaceRules.RuleSource combinedRules = SurfaceRules.sequence(
                    ModSurfaceRules.INSTANCE,
                    currentRules
            );

            ((NoiseGeneratorSettingsAccessor) (Object) settings).setSurfaceRule(combinedRules);
        }
    }
}