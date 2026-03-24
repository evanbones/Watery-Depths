package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import com.evandev.watery_depths.registration.holders.ItemDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModLanguageProvider extends FabricLanguageProvider {
    public ModLanguageProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        for (BlockDataHolder<?> holder : ModBlocks.getBlockRegistry().values()) {
            if (holder.hasTranslation()) {
                translationBuilder.add(holder.get(), holder.getTranslation());
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
    }
}