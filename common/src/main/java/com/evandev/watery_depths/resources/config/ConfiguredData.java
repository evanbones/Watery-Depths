package com.evandev.watery_depths.resources.config;

import com.evandev.watery_depths.platform.Services;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Credit for this system goes to Lyof (<a href="https://github.com/Lyof429">...</a>) and their mods
 * Do not reuse without crediting
 */
public class ConfiguredData {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static List<ConfiguredData> INSTANCES = new LinkedList<>();
    public final ResourceLocation target;
    public final Supplier<Boolean> enabled;
    public Function<JsonElement, String> provider;

    public ConfiguredData(ResourceLocation target, Supplier<Boolean> enabled, Function<JsonElement, String> provider) {
        this.target = target;
        this.provider = provider;
        this.enabled = enabled;
    }

    public static @Nullable ConfiguredData get(ResourceLocation id) {
        return INSTANCES.stream().filter(data -> data.target.equals(id)).findAny().orElse(null);
    }

    protected static void register(ResourceLocation target, Supplier<Boolean> enabled, Function<JsonElement, String> provider) {
        INSTANCES.add(new ConfiguredData(target, enabled, provider));
    }

    public static void register() {
        // register(ResourceLocation.tryBuild("watery_depths", "loot_tables/blocks/some_block.json"),
        //         () -> Services.PLATFORM.isModLoaded("some_mod"),
        //         json -> Common.dropSelf(json, "watery_depths:some_block"));
    }

    public String apply(@Nullable String original) {
        return gson.fromJson(this.provider.apply(gson.fromJson(original == null ? "" : original, JsonElement.class)),
                JsonElement.class).toString();
    }

    public static class Common {

        private static JsonArray createArray(double min, double max) {
            JsonArray array = new JsonArray();
            array.add(min);
            array.add(max);
            return array;
        }

        public static String appendToTag(JsonElement json, String... newValues) {
            JsonObject obj;
            if (json == null || !json.isJsonObject()) {
                obj = new JsonObject();
                obj.addProperty("replace", false);
                obj.add("values", new JsonArray());
            } else {
                obj = json.getAsJsonObject();
                if (!obj.has("values")) {
                    obj.add("values", new JsonArray());
                }
            }

            JsonArray values = obj.getAsJsonArray("values");
            for (String val : newValues) {
                values.add(val);
            }

            return gson.toJson(obj);
        }

        public static String dropSelf(JsonElement json, String item) {
            return gson.fromJson("{\"type\":\"minecraft:block\",\"pools\":[{\"rolls\":1.0,\"bonus_rolls\":0.0,\"entries\":[{\"type\":\"minecraft:item\",\"name\":\"" + item + "\"}],\"conditions\":[{\"condition\":\"minecraft:survives_explosion\"}]}]}", JsonElement.class).toString();
        }

        public static String createShaped(String resultItem, int count, List<String> pattern, Map<String, String> keys) {
            JsonObject json = new JsonObject();
            json.addProperty("type", "minecraft:crafting_shaped");

            JsonArray patternArray = new JsonArray();
            for (String line : pattern) {
                patternArray.add(line);
            }
            json.add("pattern", patternArray);

            JsonObject keyObj = new JsonObject();
            for (Map.Entry<String, String> entry : keys.entrySet()) {
                JsonObject itemObj = new JsonObject();
                itemObj.addProperty("item", entry.getValue());
                keyObj.add(entry.getKey(), itemObj);
            }
            json.add("key", keyObj);

            JsonObject resultObj = new JsonObject();
            resultObj.addProperty("item", resultItem);
            if (count > 1) resultObj.addProperty("count", count);
            json.add("result", resultObj);

            return gson.toJson(json);
        }

        public static String createPacking3x3(String resultItem, String inputItem) {
            return createShaped(resultItem, 1, List.of("###", "###", "###"), Map.of("#", inputItem));
        }
    }
}