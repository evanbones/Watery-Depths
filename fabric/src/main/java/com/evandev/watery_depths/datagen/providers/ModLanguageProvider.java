package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.content.ModBlocks;
import com.evandev.watery_depths.content.ModItems;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import com.evandev.watery_depths.registration.holders.ItemDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, "en_us", registriesFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        for (BlockDataHolder<?> holder : ModBlocks.getBlockRegistry().values()) {
            if (holder.hasTranslation()) {
                translationBuilder.add(holder.get(), holder.getTranslation());
            }

            for (BlockDataHolder<?> setHolder : holder.getBlocksets().values()) {
                if (setHolder.hasTranslation()) {
                    translationBuilder.add(setHolder.get(), setHolder.getTranslation());
                }
            }
        }

        for (ItemDataHolder<?> holder : ModItems.getItemRegistry().values()) {
            if (holder.hasTranslation()) {
                translationBuilder.add(holder.get(), holder.getTranslation());
            }
        }

        translationBuilder.add("config.watery_depths.title", "Watery Depths Config");
        translationBuilder.add("config.watery_depths.category.general", "General");
        translationBuilder.add("itemgroup.watery_depths", "Watery Depths");
        translationBuilder.add("entity.watery_depths.cypress_chest_boat", "Boat with Chest");
    }
}