package com.evandev.watery_depths.content;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModSounds {
    public static final RegistrationProvider<SoundEvent> SOUNDS = RegistrationProvider.get(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    // Tubeworms
    //public static final RegistryObject<SoundEvent> TUBEWORM_HIDE = register("block.watery_depths.tubeworm.hide");
    //public static final RegistryObject<SoundEvent> TUBEWORM_SHOW = register("block.watery_depths.tubeworm.show");

    // Algae
    public static final RegistryObject<SoundEvent> ALGAE_BREAK = register("block.watery_depths.algae.break");
    public static final RegistryObject<SoundEvent> ALGAE_STEP = register("block.watery_depths.algae.step");
    public static final SoundType ALGAE_SOUNDS = new ModSoundType(1.0f, 1.0f, ALGAE_BREAK, ALGAE_STEP, ALGAE_BREAK, ALGAE_BREAK, ALGAE_STEP);
    public static final RegistryObject<SoundEvent> ALGAL_GRAVEL_BREAK = register("block.watery_depths.algal_gravel.break");
    public static final RegistryObject<SoundEvent> ALGAL_GRAVEL_STEP = register("block.watery_depths.algal_gravel.step");
    public static final SoundType ALGAL_GRAVEL_SOUNDS = new ModSoundType(1.0f, 1.0f, ALGAL_GRAVEL_BREAK, ALGAL_GRAVEL_STEP, ALGAL_GRAVEL_BREAK, ALGAL_GRAVEL_BREAK, ALGAL_GRAVEL_STEP);
    public static final RegistryObject<SoundEvent> ALGAL_SAND_BREAK = register("block.watery_depths.algal_sand.break");
    public static final RegistryObject<SoundEvent> ALGAL_SAND_STEP = register("block.watery_depths.algal_sand.step");
    public static final SoundType ALGAL_SAND_SOUNDS = new ModSoundType(1.0f, 1.0f, ALGAL_SAND_BREAK, ALGAL_SAND_STEP, ALGAL_SAND_BREAK, ALGAL_SAND_BREAK, ALGAL_SAND_STEP);
    public static final RegistryObject<SoundEvent> ALGAL_SILT_BREAK = register("block.watery_depths.algal_silt.break");
    public static final RegistryObject<SoundEvent> ALGAL_SILT_STEP = register("block.watery_depths.algal_silt.step");
    public static final SoundType ALGAL_SILT_SOUNDS = new ModSoundType(1.0f, 1.0f, ALGAL_SILT_BREAK, ALGAL_SILT_STEP, ALGAL_SILT_BREAK, ALGAL_SILT_BREAK, ALGAL_SILT_STEP);
    public static final RegistryObject<SoundEvent> CHROMARINE_BREAK = register("block.watery_depths.chromarine.break");
    public static final RegistryObject<SoundEvent> CHROMARINE_STEP = register("block.watery_depths.chromarine.step");

    // Chromarine and Prismarine cluster
    public static final SoundType CHROMARINE_SOUNDS = new ModSoundType(1.0f, 1.0f, CHROMARINE_BREAK, CHROMARINE_STEP, CHROMARINE_BREAK, CHROMARINE_BREAK, CHROMARINE_STEP);
    public static final RegistryObject<SoundEvent> PRISMARINE_CLUSTER_BREAK = register("block.watery_depths.prismarine_cluster.break");

    public static final SoundType PRISMARINE_CLUSTER_SOUNDS = new ModSoundType(1.0f, 1.0f, PRISMARINE_CLUSTER_BREAK, SoundType.GLASS::getStepSound, SoundType.GLASS::getPlaceSound, SoundType.GLASS::getHitSound, SoundType.GLASS::getFallSound);
    public static final RegistryObject<SoundEvent> SILT_BREAK = register("block.watery_depths.silt.break");
    public static final RegistryObject<SoundEvent> SILT_STEP = register("block.watery_depths.silt.step");
    public static final SoundType SILT_SOUNDS = new ModSoundType(1.0f, 1.0f, SILT_BREAK, SILT_STEP, SILT_BREAK, SILT_BREAK, SILT_STEP);

    public static final RegistryObject<SoundEvent> DRIED_SILT_BREAK = register("block.watery_depths.dried_silt.break");
    public static final RegistryObject<SoundEvent> DRIED_SILT_STEP = register("block.watery_depths.dried_silt.step");
    public static final SoundType DRIED_SILT_SOUNDS = new ModSoundType(1.0f, 1.0f, DRIED_SILT_BREAK, DRIED_SILT_STEP, DRIED_SILT_BREAK, DRIED_SILT_BREAK, DRIED_SILT_STEP);

    private static RegistryObject<SoundEvent> register(String name) {
        return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, name)));
    }

    public static void load() {
    }

    /**
     * Prevents Forge from trying to access RegistryObjects before the register events fire.
     */
    private static class ModSoundType extends SoundType {
        private final Supplier<SoundEvent> breakSound;
        private final Supplier<SoundEvent> stepSound;
        private final Supplier<SoundEvent> placeSound;
        private final Supplier<SoundEvent> hitSound;
        private final Supplier<SoundEvent> fallSound;

        public ModSoundType(float volume, float pitch, Supplier<SoundEvent> breakSound, Supplier<SoundEvent> stepSound, Supplier<SoundEvent> placeSound, Supplier<SoundEvent> hitSound, Supplier<SoundEvent> fallSound) {
            super(volume, pitch, null, null, null, null, null);
            this.breakSound = breakSound;
            this.stepSound = stepSound;
            this.placeSound = placeSound;
            this.hitSound = hitSound;
            this.fallSound = fallSound;
        }

        @Override
        public @NotNull SoundEvent getBreakSound() {
            return breakSound.get();
        }

        @Override
        public @NotNull SoundEvent getStepSound() {
            return stepSound.get();
        }

        @Override
        public @NotNull SoundEvent getPlaceSound() {
            return placeSound.get();
        }

        @Override
        public @NotNull SoundEvent getHitSound() {
            return hitSound.get();
        }

        @Override
        public @NotNull SoundEvent getFallSound() {
            return fallSound.get();
        }
    }
}