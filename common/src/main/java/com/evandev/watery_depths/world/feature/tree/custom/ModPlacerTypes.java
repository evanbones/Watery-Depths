package com.evandev.watery_depths.world.feature.tree.custom;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.mixin.accessor.FoliagePlacerTypeAccessor;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class ModPlacerTypes {
    public static final RegistrationProvider<FoliagePlacerType<?>> PLACERS = RegistrationProvider.get(BuiltInRegistries.FOLIAGE_PLACER_TYPE, Constants.MOD_ID);

    public static final RegistryObject<FoliagePlacerType<CypressFoliagePlacer>> CYPRESS_FOLIAGE_PLACER = PLACERS.register(
            "cypress_foliage_placer",
            () -> FoliagePlacerTypeAccessor.createFoliagePlacerType(CypressFoliagePlacer.CODEC)
    );

    public static void load() {
    }
}