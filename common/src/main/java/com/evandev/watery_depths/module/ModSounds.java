package com.evandev.watery_depths.module;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;

public class ModSounds {
    public static final RegistrationProvider<SoundEvent> SOUNDS = RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    // Sound Events
    public static final RegistryObject<SoundEvent> ALGAE_BREAK = register("block.watery_depths.algae.break");
    public static final RegistryObject<SoundEvent> ALGAE_STEP = register("block.watery_depths.algae.step");

    public static final RegistryObject<SoundEvent> ALGAL_GRAVEL_BREAK = register("block.watery_depths.algal_gravel.break");
    public static final RegistryObject<SoundEvent> ALGAL_GRAVEL_STEP = register("block.watery_depths.algal_gravel.step");

    public static final RegistryObject<SoundEvent> ALGAL_SAND_BREAK = register("block.watery_depths.algal_sand.break");
    public static final RegistryObject<SoundEvent> ALGAL_SAND_STEP = register("block.watery_depths.algal_sand.step");

    public static final RegistryObject<SoundEvent> ALGAL_SILT_BREAK = register("block.watery_depths.algal_silt.break");
    public static final RegistryObject<SoundEvent> ALGAL_SILT_STEP = register("block.watery_depths.algal_silt.step");

    public static final RegistryObject<SoundEvent> CHROMARINE_BREAK = register("block.watery_depths.chromarine.break");
    public static final RegistryObject<SoundEvent> CHROMARINE_STEP = register("block.watery_depths.chromarine.step");

    public static final RegistryObject<SoundEvent> PRISMARINE_CLUSTER_BREAK = register("block.watery_depths.prismarine_cluster.break");

    public static final RegistryObject<SoundEvent> SILT_BREAK = register("block.watery_depths.silt.break");
    public static final RegistryObject<SoundEvent> SILT_STEP = register("block.watery_depths.silt.step");

    // Sound Types
    public static final SoundType ALGAE_SOUNDS = new SoundType(1.0f, 1.0f, ALGAE_BREAK.get(), ALGAE_STEP.get(), ALGAE_BREAK.get(), ALGAE_BREAK.get(), ALGAE_STEP.get());
    public static final SoundType ALGAL_GRAVEL_SOUNDS = new SoundType(1.0f, 1.0f, ALGAL_GRAVEL_BREAK.get(), ALGAL_GRAVEL_STEP.get(), ALGAL_GRAVEL_BREAK.get(), ALGAL_GRAVEL_BREAK.get(), ALGAL_GRAVEL_STEP.get());
    public static final SoundType ALGAL_SAND_SOUNDS = new SoundType(1.0f, 1.0f, ALGAL_SAND_BREAK.get(), ALGAL_SAND_STEP.get(), ALGAL_SAND_BREAK.get(), ALGAL_SAND_BREAK.get(), ALGAL_SAND_STEP.get());
    public static final SoundType ALGAL_SILT_SOUNDS = new SoundType(1.0f, 1.0f, ALGAL_SILT_BREAK.get(), ALGAL_SILT_STEP.get(), ALGAL_SILT_BREAK.get(), ALGAL_SILT_BREAK.get(), ALGAL_SILT_STEP.get());
    public static final SoundType SILT_SOUNDS = new SoundType(1.0f, 1.0f, SILT_BREAK.get(), SILT_STEP.get(), SILT_BREAK.get(), SILT_BREAK.get(), SILT_STEP.get());

    // Chromarine and Prismarine cluster
    public static final SoundType CHROMARINE_SOUNDS = new SoundType(1.0f, 1.0f, CHROMARINE_BREAK.get(), CHROMARINE_STEP.get(), CHROMARINE_BREAK.get(), CHROMARINE_BREAK.get(), CHROMARINE_STEP.get());
    public static final SoundType PRISMARINE_CLUSTER_SOUNDS = new SoundType(1.0f, 1.0f, PRISMARINE_CLUSTER_BREAK.get(), SoundType.GLASS.getStepSound(), SoundType.GLASS.getPlaceSound(), SoundType.GLASS.getHitSound(), SoundType.GLASS.getFallSound());

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Constants.MOD_ID, name)));
    }

    public static void load() {
    }
}