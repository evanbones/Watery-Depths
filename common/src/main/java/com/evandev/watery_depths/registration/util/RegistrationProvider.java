package com.evandev.watery_depths.registration.util;

import com.evandev.watery_depths.platform.Services;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.Collection;
import java.util.function.Supplier;

/**
 * Utility class for multiloader registration.
 * <p>
 * Example usage:
 * <pre>{@code
 * public static final RegistrationProvider<Test> PROVIDER = RegistrationProvider.get(Test.REGISTRY, "modid");
 * public static final RegistryObject<Test> OBJECT = PROVIDER.register("object", () -> new Test());
 *
 * // The purpose of this method is to be called in the mod's constructor, in order to assure that the class is loaded, and that objects can be registered.
 * public static void load(){}
 * }</pre>
 *
 * @param <T> the type of the objects that this class registers
 */
public interface RegistrationProvider<T> {

    static <T> RegistrationProvider<T> get(ResourceKey<? extends Registry<T>> resourceKey, String modId) {
        return Factory.INSTANCE.create(resourceKey, modId);
    }

    static <T> RegistrationProvider<T> get(Registry<T> registry, String modId) {
        return Factory.INSTANCE.create(registry, modId);
    }

    <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier);

    Collection<RegistryObject<T>> getEntries();

    String getModId();

    interface Factory {

        Factory INSTANCE = Services.load(Factory.class);

        <T> RegistrationProvider<T> create(ResourceKey<? extends Registry<T>> resourceKey, String modId);

        default <T> RegistrationProvider<T> create(Registry<T> registry, String modId) {
            return create(registry.key(), modId);
        }
    }
}