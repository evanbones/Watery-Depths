package com.evandev.watery_depths.mixin;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.resources.config.ConfiguredData;
import com.evandev.watery_depths.resources.config.ConfiguredResources;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Mixin(MultiPackResourceManager.class)
public class MultiPackResourceManagerMixin {

    @Unique
    private static Resource watery_depths$readAndApply(Optional<Resource> resource, ConfiguredData data) {
        Constants.LOG.info("Applying configured data: {}", data.target);

        if (resource.isEmpty()) {
            String result = data.apply(null);
            return new Resource(ConfiguredResources.INSTANCE,
                    () -> new CharSequenceInputStream(result, StandardCharsets.UTF_8));
        }

        try (InputStream stream = resource.get().open()) {
            String originalText = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            String result = data.apply(originalText);

            return new Resource(ConfiguredResources.INSTANCE,
                    () -> new CharSequenceInputStream(result, StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
            return resource.get();
        }
    }

    @Unique
    private static Resource watery_depths$readAndApply(Resource resource, ConfiguredData data) {
        if (resource.source() instanceof ConfiguredResources) return resource;
        return watery_depths$readAndApply(Optional.of(resource), data);
    }

    @ModifyReturnValue(method = "getResource", at = @At("RETURN"))
    public Optional<Resource> watery_depths$getConfiguredResource(Optional<Resource> original, ResourceLocation location) {
        ConfiguredData data = ConfiguredData.get(location);
        if (data == null || !data.enabled.get() || (original.isPresent() && original.get().source() instanceof ConfiguredResources))
            return original;

        return Optional.of(watery_depths$readAndApply(original, data));
    }

    @ModifyReturnValue(method = "getResourceStack", at = @At("RETURN"))
    public List<Resource> watery_depths$getAllConfiguredResource(List<Resource> original, ResourceLocation location) {
        ConfiguredData data = ConfiguredData.get(location);
        if (data == null || !data.enabled.get()) return original;

        return original.stream()
                .map(resource -> watery_depths$readAndApply(resource, data)).toList();
    }

    @ModifyReturnValue(method = "listResources", at = @At("RETURN"))
    public Map<ResourceLocation, Resource> watery_depths$findConfiguredResources(Map<ResourceLocation, Resource> original,
                                                                                 String path, Predicate<ResourceLocation> filter) {

        for (ConfiguredData data : ConfiguredData.INSTANCES) {
            if (data.enabled.get() && data.target.getPath().startsWith(path + "/") && filter.test(data.target)) {
                if (!original.containsKey(data.target)) {
                    original.put(data.target, watery_depths$readAndApply(Optional.empty(), data));
                }
            }
        }

        List<ResourceLocation> ids = original.keySet().stream().toList();
        for (ResourceLocation id : ids) {
            ConfiguredData data = ConfiguredData.get(id);
            if (data == null || !data.enabled.get()) continue;

            original.replace(id, watery_depths$readAndApply(original.get(id), data));
        }
        return original;
    }

    @ModifyReturnValue(method = "listResourceStacks", at = @At("RETURN"))
    public Map<ResourceLocation, List<Resource>> watery_depths$findAllConfiguredResources(Map<ResourceLocation, List<Resource>> original,
                                                                                          String path, Predicate<ResourceLocation> filter) {

        for (ConfiguredData data : ConfiguredData.INSTANCES) {
            if (data.enabled.get() && data.target.getPath().startsWith(path) && filter.test(data.target)) {
                if (!original.containsKey(data.target)) {
                    original.put(data.target, List.of(watery_depths$readAndApply(Optional.empty(), data)));
                }
            }
        }

        List<ResourceLocation> ids = original.keySet().stream().toList();
        for (ResourceLocation id : ids) {
            ConfiguredData data = ConfiguredData.get(id);
            if (data == null || !data.enabled.get()) continue;

            original.replace(id, original.get(id).stream()
                    .map(resource -> watery_depths$readAndApply(resource, data)).toList());
        }
        return original;
    }
}