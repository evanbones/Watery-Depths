package com.evandev.watery_depths.mixin;

import com.evandev.watery_depths.mixin.accessor.NoiseGeneratorSettingsAccessor;
import com.evandev.watery_depths.world.feature.ModSurfaceRules;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {

    @Inject(method = "overworld", at = @At("RETURN"))
    private static void watery_depths$injectSurfaceRules(BootstapContext<?> context, boolean amplified, boolean large, CallbackInfoReturnable<NoiseGeneratorSettings> cir) {
        NoiseGeneratorSettings settings = cir.getReturnValue();

        SurfaceRules.RuleSource combinedRules = SurfaceRules.sequence(
                ModSurfaceRules.makeRules(),
                settings.surfaceRule()
        );

        ((NoiseGeneratorSettingsAccessor) (Object) settings).setSurfaceRule(combinedRules);
    }
}