package com.evandev.watery_depths.module;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.block.AlgalBlock;
import com.evandev.watery_depths.registration.FlammabilityRegistry;
import com.evandev.watery_depths.registration.FuelRegistry;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModBlocks {
    private static final Map<ResourceLocation, BlockDataHolder<?>> BLOCK_REGISTRY = new LinkedHashMap<>();

    public static final BlockDataHolder<?> SILT = register("silt", BlockDataHolder.of(() ->
                    new SandBlock(0x8c7c6a, BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.COLOR_GRAY)))
            .withModel(BlockDataHolder.Model.CUBE).withItem().dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Silt")
    );

    public static final BlockDataHolder<?> ALGAL_SILT = register("algal_silt", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SLIME_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN), SILT.get()))
            .withModel(BlockDataHolder.Model.NYLIUM)
            .withItem().dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Algal Silt")
    );

    public static final BlockDataHolder<?> ALGAL_SAND = register("algal_sand", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SLIME_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN), Blocks.SAND))
            .withModel(BlockDataHolder.Model.NYLIUM)
            .withItem().dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Algal Sand")
    );

    public static final BlockDataHolder<?> ALGAL_GRAVEL = register("algal_gravel", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).sound(SoundType.SLIME_BLOCK).mapColor(MapColor.COLOR_LIGHT_GREEN), Blocks.GRAVEL))
            .withModel(BlockDataHolder.Model.NYLIUM)
            .withItem().dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Algal Gravel")
    );

    public static final BlockDataHolder<?> ALGAE = register("algae", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_GREEN), Blocks.DIRT))
            .withModel(BlockDataHolder.Model.CUBE).withItem().dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_HOE, BlockTags.DIRT)
            .withTranslation("Algae")
            .withCompost(0.65f)
    );

    public static BlockDataHolder<?> register(String name, BlockDataHolder<?> blockDataHolder) {
        ResourceLocation id = new ResourceLocation(Constants.MOD_ID, name);
        BLOCK_REGISTRY.put(id, blockDataHolder);
        return blockDataHolder;
    }

    public static Map<ResourceLocation, BlockDataHolder<?>> getBlockRegistry() {
        return BLOCK_REGISTRY;
    }

    public static void load() {
    }

    public static void registerBlocks(BiConsumer<ResourceLocation, Supplier<Block>> blockRegister, BiConsumer<ResourceLocation, Supplier<Item>> itemRegister) {
        for (Map.Entry<ResourceLocation, BlockDataHolder<?>> entry : BLOCK_REGISTRY.entrySet()) {
            ResourceLocation id = entry.getKey();
            BlockDataHolder<?> holder = entry.getValue();

            blockRegister.accept(id, holder::get);

            if (holder.hasItem()) {
                itemRegister.accept(id, () -> holder.getBlockItem().get());
            }

            if (holder.isGlass()) {
                ResourceLocation paneId = new ResourceLocation(id.getNamespace(), id.getPath() + "_pane");
                blockRegister.accept(paneId, () -> holder.getPaneBlock().get());
                itemRegister.accept(paneId, () -> holder.getPaneBlock().getBlockItem().get());
            }

            for (Map.Entry<BlockDataHolder.Model, BlockDataHolder<?>> setEntry : holder.getBlocksets().entrySet()) {
                ResourceLocation setId = new ResourceLocation(id.getNamespace(), id.getPath() + "_" + setEntry.getKey().suffix());
                blockRegister.accept(setId, () -> setEntry.getValue().get());

                if (holder.hasItem()) {
                    itemRegister.accept(setId, () -> setEntry.getValue().getBlockItem().get());
                }
            }
        }
    }

    public static void registerBlockInteractions() {
        for (BlockDataHolder<?> holder : BLOCK_REGISTRY.values()) {
            if (holder.hasItem() && holder.isFuel()) {
                FuelRegistry.register(holder.getBlockItem().get(), holder.getFuelDuration());
            }

            for (Map.Entry<Block, FlammabilityRegistry.Entry> flammability : holder.getFlammabilities().entrySet()) {
                FlammabilityRegistry.getRegistry(flammability.getKey()).register(holder.get(), flammability.getValue());
            }

            if (holder.hasStrippingResult()) {
                com.evandev.watery_depths.registration.StrippableRegistry.register(holder.get(), holder.getStrippingResult());
            }
        }
    }
}