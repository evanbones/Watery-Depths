package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.content.ModBlocks;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIED_SILT.get(), 1)
                .define('#', ModBlocks.SILT.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.SILT.get()), has(ModBlocks.SILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIED_DEEPSILT.get(), 1)
                .define('#', ModBlocks.DEEPSILT.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.DEEPSILT.get()), has(ModBlocks.DEEPSILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_DRIED_SILT.get(), 4)
                .define('#', ModBlocks.DRIED_SILT.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_DRIED_DEEPSILT.get(), 4)
                .define('#', ModBlocks.DRIED_DEEPSILT.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILT_BRICKS.get(), 4)
                .define('#', ModBlocks.CUT_DRIED_SILT.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.CUT_DRIED_SILT.get()), has(ModBlocks.CUT_DRIED_SILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEEPSILT_BRICKS.get(), 4)
                .define('#', ModBlocks.CUT_DRIED_DEEPSILT.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(ModBlocks.CUT_DRIED_DEEPSILT.get()), has(ModBlocks.CUT_DRIED_DEEPSILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIED_SILT_PILLAR.get(), 2)
                .define('#', ModBlocks.DRIED_SILT.getSlab().get())
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRIED_DEEPSILT_PILLAR.get(), 2)
                .define('#', ModBlocks.DRIED_DEEPSILT.getSlab().get())
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_PRISMARINE_BRICKS.get(), 4)
                .define('#', Blocks.DARK_PRISMARINE)
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(Blocks.DARK_PRISMARINE), has(Blocks.DARK_PRISMARINE))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DRIED_SILT.get(), 1)
                .define('#', ModBlocks.DRIED_SILT.getSlab().get())
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DRIED_DEEPSILT.get(), 1)
                .define('#', ModBlocks.DRIED_DEEPSILT.getSlab().get())
                .pattern("#")
                .pattern("#")
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter);

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.DRIED_SILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DRIED_SILT.get(), 1)
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chiseled_dried_silt_from_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.DRIED_DEEPSILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CHISELED_DRIED_DEEPSILT.get(), 1)
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "chiseled_dried_deepsilt_from_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.DRIED_SILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_DRIED_SILT.get(), 1)
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cut_dried_silt_from_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.DRIED_DEEPSILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.CUT_DRIED_DEEPSILT.get(), 1)
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "cut_dried_deepsilt_from_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.DRIED_SILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SILT_BRICKS.get(), 1)
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "silt_bricks_from_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ModBlocks.DRIED_DEEPSILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.DEEPSILT_BRICKS.get(), 1)
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "deepsilt_bricks_from_stonecutting"));

        SingleItemRecipeBuilder.stonecutting(Ingredient.of(Blocks.DARK_PRISMARINE), RecipeCategory.BUILDING_BLOCKS, ModBlocks.DARK_PRISMARINE_BRICKS.get(), 1)
                .unlockedBy(getHasName(Blocks.DARK_PRISMARINE), has(Blocks.DARK_PRISMARINE))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "dark_prismarine_bricks_from_stonecutting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DRIED_SILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_DRIED_SILT.get(), 0.1f, 200)
                .unlockedBy(getHasName(ModBlocks.DRIED_SILT.get()), has(ModBlocks.DRIED_SILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "smooth_dried_silt_from_smelting"));

        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DRIED_DEEPSILT.get()), RecipeCategory.BUILDING_BLOCKS, ModBlocks.SMOOTH_DRIED_DEEPSILT.get(), 0.1f, 200)
                .unlockedBy(getHasName(ModBlocks.DRIED_DEEPSILT.get()), has(ModBlocks.DRIED_DEEPSILT.get()))
                .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "smooth_dried_deepsilt_from_smelting"));

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

            boolean isWood = holder.getTranslation() != null &&
                    (holder.getTranslation().contains("Planks") ||
                            holder.getTranslation().contains("Wood") ||
                            holder.getTranslation().contains("Log") ||
                            holder.getTranslation().contains("Sign") ||
                            holder.getTranslation().contains("Roots"));

            if (!isWood) {
                if (holder.getStairs() != null) {
                    SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseBlock), RecipeCategory.BUILDING_BLOCKS, holder.getStairs().get(), 1)
                            .unlockedBy(getHasName(baseBlock), has(baseBlock))
                            .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, BuiltInRegistries.BLOCK.getKey(holder.getStairs().get()).getPath() + "_from_stonecutting"));
                }
                if (holder.getSlab() != null) {
                    SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseBlock), RecipeCategory.BUILDING_BLOCKS, holder.getSlab().get(), 2)
                            .unlockedBy(getHasName(baseBlock), has(baseBlock))
                            .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, BuiltInRegistries.BLOCK.getKey(holder.getSlab().get()).getPath() + "_from_stonecutting"));
                }
                if (holder.getWall() != null) {
                    SingleItemRecipeBuilder.stonecutting(Ingredient.of(baseBlock), RecipeCategory.DECORATIONS, holder.getWall().get(), 1)
                            .unlockedBy(getHasName(baseBlock), has(baseBlock))
                            .save(exporter, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, BuiltInRegistries.BLOCK.getKey(holder.getWall().get()).getPath() + "_from_stonecutting"));
                }
            }
        }
    }
}