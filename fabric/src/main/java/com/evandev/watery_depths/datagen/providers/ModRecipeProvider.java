package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.level.block.Block;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        for (BlockDataHolder<?> holder : ModBlocks.getBlockRegistry().values()) {
            if (!holder.hasItem()) continue;
            Block baseBlock = holder.get();

            if (holder.getStairs() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, holder.getStairs().get(), 4)
                        .define('#', baseBlock)
                        .pattern("#  ")
                        .pattern("## ")
                        .pattern("###")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getSlab() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, holder.getSlab().get(), 6)
                        .define('#', baseBlock)
                        .pattern("###")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getWall() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, holder.getWall().get(), 6)
                        .define('#', baseBlock)
                        .pattern("###")
                        .pattern("###")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getFence() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, holder.getFence().get(), 3)
                        .define('#', baseBlock)
                        .define('S', net.minecraft.world.item.Items.STICK)
                        .pattern("#S#")
                        .pattern("#S#")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getFenceGate() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, holder.getFenceGate().get(), 1)
                        .define('#', baseBlock)
                        .define('S', net.minecraft.world.item.Items.STICK)
                        .pattern("S#S")
                        .pattern("S#S")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getDoor() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, holder.getDoor().get(), 3)
                        .define('#', baseBlock)
                        .pattern("##")
                        .pattern("##")
                        .pattern("##")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getTrapdoor() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, holder.getTrapdoor().get(), 2)
                        .define('#', baseBlock)
                        .pattern("###")
                        .pattern("###")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getPressurePlate() != null) {
                ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, holder.getPressurePlate().get(), 1)
                        .define('#', baseBlock)
                        .pattern("##")
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }

            if (holder.getButton() != null) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, holder.getButton().get(), 1)
                        .requires(baseBlock)
                        .unlockedBy(getHasName(baseBlock), has(baseBlock))
                        .save(exporter);
            }
        }
    }
}