package com.evandev.watery_depths.module;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.block.*;
import com.evandev.watery_depths.block.RootsBlock;
import com.evandev.watery_depths.mixin.accessor.SaplingBlockAccessor;
import com.evandev.watery_depths.mixin.accessor.WoodTypeAccessor;
import com.evandev.watery_depths.registration.FlammabilityRegistry;
import com.evandev.watery_depths.registration.FuelRegistry;
import com.evandev.watery_depths.registration.StrippableRegistry;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModBlocks {
    public static final WoodType CYPRESS_WOOD_TYPE = WoodTypeAccessor.register(new WoodType("watery_depths:cypress", BlockSetType.OAK));
    private static final Map<ResourceLocation, BlockDataHolder<?>> BLOCK_REGISTRY = new LinkedHashMap<>();
    public static final BlockDataHolder<?> SILT = register("silt", BlockDataHolder.of(() ->
                    new SandBlock(0x8c7c6a, BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.COLOR_GRAY).sound(ModSounds.SILT_SOUNDS)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Silt")
    );

    public static final BlockDataHolder<?> ALGAL_SILT = register("algal_silt", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.SAND).sound(ModSounds.ALGAL_SILT_SOUNDS).mapColor(MapColor.COLOR_LIGHT_GREEN), SILT.get()))
            .withModel(BlockDataHolder.Model.NYLIUM)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Algal Silt")
    );

    public static final BlockDataHolder<?> DEEPSILT = register("deepsilt", BlockDataHolder.of(() ->
                    new SandBlock(0x3a3a3a, BlockBehaviour.Properties.copy(Blocks.SAND).mapColor(MapColor.COLOR_BLACK).sound(ModSounds.SILT_SOUNDS)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Deepsilt")
    );

    public static final BlockDataHolder<?> ALGAL_SAND = register("algal_sand", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.SAND).sound(ModSounds.ALGAL_SAND_SOUNDS).mapColor(MapColor.COLOR_LIGHT_GREEN), Blocks.SAND))
            .withModel(BlockDataHolder.Model.NYLIUM)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL, BlockTags.SAND)
            .withTranslation("Algal Sand")
    );

    public static final BlockDataHolder<?> ALGAL_GRAVEL = register("algal_gravel", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.GRAVEL).sound(ModSounds.ALGAL_GRAVEL_SOUNDS).mapColor(MapColor.COLOR_LIGHT_GREEN), Blocks.GRAVEL))
            .withModel(BlockDataHolder.Model.NYLIUM)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_SHOVEL)
            .withTranslation("Algal Gravel")
    );

    public static final BlockDataHolder<?> ALGAE = register("algae", BlockDataHolder.of(() ->
                    new AlgalBlock(BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK).mapColor(MapColor.COLOR_GREEN).sound(ModSounds.ALGAE_SOUNDS), Blocks.DIRT))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_HOE, BlockTags.DIRT)
            .withTranslation("Algae")
            .withCompost(0.65f)
    );

    public static final BlockDataHolder<?> ACHROMARINE = register("achromarine", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.PRISMARINE)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Achromarine")
            .withStairs()
            .withSlab()
            .withWall()
    );

    public static final BlockDataHolder<?> CHROMARINE = register("chromarine", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.PRISMARINE).sound(ModSounds.CHROMARINE_SOUNDS)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Chromarine")
            .withStairs()
            .withSlab()
            .withWall()
    );

    public static final BlockDataHolder<?> PRISMARINE_TILES = register("prismarine_tiles", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICKS)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Prismarine Tiles")
            .withStairs()
            .withSlab()
            .withWall()
    );

    public static final BlockDataHolder<?> DARK_PRISMARINE_BRICKS = register("dark_prismarine_bricks", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Dark Prismarine Bricks")
            .withStairs()
            .withSlab()
            .withWall()
    );

    public static final BlockDataHolder<?> SULFIDE = register("sulfide", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.STONE)))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Sulfide")
    );

    public static final BlockDataHolder<?> BUDDING_PRISMARINE = register("budding_prismarine", BlockDataHolder.of(() ->
                    new BuddingPrismarineBlock(BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST).randomTicks()))
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Budding Prismarine")
    );

    public static final BlockDataHolder<?> SMALL_PRISMARINE_BUD = register("small_prismarine_bud", BlockDataHolder.of(() ->
                    new PrismarineClusterBlock(3, 4, BlockBehaviour.Properties.copy(Blocks.SMALL_AMETHYST_BUD).sound(ModSounds.PRISMARINE_CLUSTER_SOUNDS).lightLevel((state) -> 0)))
            .cutout()
            .withModel(BlockDataHolder.Model.CROSS)
            .withItem()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Small Prismarine Bud")
    );

    public static final BlockDataHolder<?> MEDIUM_PRISMARINE_BUD = register("medium_prismarine_bud", BlockDataHolder.of(() ->
                    new PrismarineClusterBlock(4, 3, BlockBehaviour.Properties.copy(Blocks.MEDIUM_AMETHYST_BUD).sound(ModSounds.PRISMARINE_CLUSTER_SOUNDS).lightLevel((state) -> 0)))
            .cutout()
            .withModel(BlockDataHolder.Model.CROSS).withItem()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Medium Prismarine Bud")
    );

    public static final BlockDataHolder<?> LARGE_PRISMARINE_BUD = register("large_prismarine_bud", BlockDataHolder.of(() ->
                    new PrismarineClusterBlock(5, 3, BlockBehaviour.Properties.copy(Blocks.LARGE_AMETHYST_BUD).sound(ModSounds.PRISMARINE_CLUSTER_SOUNDS).lightLevel((state) -> 0)))
            .cutout()
            .withModel(BlockDataHolder.Model.CROSS).withItem()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Large Prismarine Bud")
    );

    public static final BlockDataHolder<?> PRISMARINE_CLUSTER = register("prismarine_cluster", BlockDataHolder.of(() ->
                    new PrismarineClusterBlock(7, 3, BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).sound(ModSounds.PRISMARINE_CLUSTER_SOUNDS).lightLevel((state) -> 0)))
            .cutout()
            .withModel(BlockDataHolder.Model.CROSS)
            .withItem()
            .dropsOther(() -> Items.PRISMARINE_SHARD, 4)
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Prismarine Cluster")
    );

    public static final BlockDataHolder<?> CYPRESS_LOG = register("cypress_log", BlockDataHolder.of(() ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)))
            .withModel(BlockDataHolder.Model.PILLAR)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN)
            .withTranslation("Cypress Log")
    );

    public static final BlockDataHolder<?> STRIPPED_CYPRESS_LOG = register("stripped_cypress_log", BlockDataHolder.of(() ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)))
            .withModel(BlockDataHolder.Model.PILLAR)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN)
            .withTranslation("Stripped Cypress Log")
    );

    public static final BlockDataHolder<?> CYPRESS_WOOD = register("cypress_wood", BlockDataHolder.of(() ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)))
            .withModel(BlockDataHolder.Model.PILLAR)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN)
            .withTranslation("Cypress Wood")
    );

    public static final BlockDataHolder<?> STRIPPED_CYPRESS_WOOD = register("stripped_cypress_wood", BlockDataHolder.of(() ->
                    new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)))
            .withModel(BlockDataHolder.Model.PILLAR)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.LOGS, BlockTags.LOGS_THAT_BURN)
            .withTranslation("Stripped Cypress Wood")
    );

    public static final BlockDataHolder<?> CYPRESS_PLANKS = register("cypress_planks", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)))
            .withModel(BlockDataHolder.Model.CUBE).withItem().dropsSelf().withTags(BlockTags.MINEABLE_WITH_AXE, BlockTags.PLANKS).withTranslation("Cypress Planks")
            .withStairs()
            .withSlab()
            .withFence()
            .withFenceGate(WoodType.OAK)
            .withDoor(BlockSetType.OAK)
            .withTrapdoor(BlockSetType.OAK)
            .withPressurePlate(PressurePlateBlock.Sensitivity.EVERYTHING, BlockSetType.OAK)
            .withButton(BlockSetType.OAK, 30, true)
    );

    public static final BlockDataHolder<?> CYPRESS_ROOTS = register("cypress_roots", BlockDataHolder.of(() ->
                    new RootsBlock(BlockBehaviour.Properties.copy(Blocks.MANGROVE_ROOTS)))
            .withModel(BlockDataHolder.Model.CUSTOM)
            .cutout()
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_AXE)
            .withTranslation("Cypress Roots")
    );

    public static final BlockDataHolder<?> CYPRESS_LEAVES = register("cypress_leaves", BlockDataHolder.of(() ->
                    new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)))
            .cutout()
            .withModel(BlockDataHolder.Model.CUBE)
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.MINEABLE_WITH_HOE, BlockTags.LEAVES)
            .withTranslation("Cypress Leaves")
    );

    public static final BlockDataHolder<?> CYPRESS_SAPLING = register("cypress_sapling", BlockDataHolder.of(() ->
                    SaplingBlockAccessor.createSaplingBlock(null, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING))) // TODO: add custom treegrower
            .withModel(BlockDataHolder.Model.CROSS)
            .cutout()
            .withItem()
            .dropsSelf()
            .withTags(BlockTags.SAPLINGS)
            .withTranslation("Cypress Sapling")
    );

    public static final BlockDataHolder<?> CALCITE_VENT = register("calcite_vent", BlockDataHolder.of(() ->
                    new VentBlock(BlockBehaviour.Properties.copy(Blocks.CALCITE).noOcclusion()))
            .withItem()
            .dropsSelf()
            .cutout()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Calcite Vent")
    );

    public static final BlockDataHolder<?> SULFIDE_VENT = register("sulfide_vent", BlockDataHolder.of(() ->
                    new VentBlock(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()))
            .withItem()
            .dropsSelf()
            .cutout()
            .withTags(BlockTags.MINEABLE_WITH_PICKAXE)
            .withTranslation("Sulfide Vent")
    );

    public static final BlockDataHolder<?> ACHROMATIC_BRAMBLE = register("achromatic_bramble", BlockDataHolder.of(() ->
                    new UnderwaterBrambleBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)))
            .cutout()
            .withModel(BlockDataHolder.Model.CROSS)
            .withItem()
            .dropsSelf()
            .withTranslation("Achromatic Bramble")
    );

    public static final BlockDataHolder<?> CHROMATIC_BRAMBLE = register("chromatic_bramble", BlockDataHolder.of(() ->
                    new UnderwaterBrambleBlock(BlockBehaviour.Properties.copy(Blocks.DEAD_BUSH)))
            .cutout()
            .withModel(BlockDataHolder.Model.CROSS)
            .withItem()
            .dropsSelf()
            .withTranslation("Chromatic Bramble")
    );

    public static final BlockDataHolder<?> DUCKWEED = register("duckweed", BlockDataHolder.of(() ->
                    new DuckweedBlock(BlockBehaviour.Properties.copy(Blocks.LILY_PAD).noOcclusion()))
            .cutout()
            .withModel(BlockDataHolder.Model.CUSTOM)
            .withCustomItem(block -> new PlaceOnWaterBlockItem(block, new Item.Properties()))
            .dropsSelf()
            .withTranslation("Duckweed")
    );

    public static final BlockDataHolder<?> TALL_ACHROMATIC_BRAMBLE = register("tall_achromatic_bramble", BlockDataHolder.of(() ->
                    new TallUnderwaterBrambleBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)))
            .cutout()
            .withModel(BlockDataHolder.Model.DOUBLE_CROSS)
            .withItem()
            .dropsSelf()
            .withTranslation("Tall Achromatic Bramble")
    );

    public static final BlockDataHolder<?> CYPRESS_SIGN = register("cypress_sign", BlockDataHolder.of(() ->
                    new StandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), CYPRESS_WOOD_TYPE))
            .withSign(
                    () -> ModBlocks.CYPRESS_WALL_SIGN.get(),
                    ModBlocks.CYPRESS_PLANKS::get
            )
            .dropsSelf()
            .withTranslation("Cypress Sign")
    );

    public static final BlockDataHolder<?> CYPRESS_HANGING_SIGN = register("cypress_hanging_sign", BlockDataHolder.of(() ->
                    new CeilingHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), CYPRESS_WOOD_TYPE))
            .withHangingSign(
                    () -> ModBlocks.CYPRESS_WALL_HANGING_SIGN.get(),
                    ModBlocks.STRIPPED_CYPRESS_LOG::get
            )
            .dropsSelf()
            .withTranslation("Cypress Hanging Sign")
    );

    public static final BlockDataHolder<?> CYPRESS_WALL_HANGING_SIGN = register("cypress_wall_hanging_sign", BlockDataHolder.of(() ->
                    new WallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), CYPRESS_WOOD_TYPE))
            .withModel(BlockDataHolder.Model.WALL_HANGING_SIGN)
            .dropsOther(CYPRESS_HANGING_SIGN::get)
    );

    public static final BlockDataHolder<?> CYPRESS_WALL_SIGN = register("cypress_wall_sign", BlockDataHolder.of(() ->
                    new WallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), CYPRESS_WOOD_TYPE))
            .withModel(BlockDataHolder.Model.WALL_SIGN)
            .dropsOther(CYPRESS_SIGN::get)
    );

    public static final BlockDataHolder<?> TALL_CHROMATIC_BRAMBLE = register("tall_chromatic_bramble", BlockDataHolder.of(() ->
                    new TallUnderwaterBrambleBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS)))
            .cutout()
            .withModel(BlockDataHolder.Model.DOUBLE_CROSS)
            .withItem()
            .dropsSelf()
            .withTranslation("Tall Chromatic Bramble")
    );

    public static final BlockDataHolder<?> TUBEWORM = register("tubeworm", BlockDataHolder.of(() ->
                    new Block(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion()))
            .cutout()
            .withModel(BlockDataHolder.Model.CUSTOM)
            .withItem()
            .dropsSelf()
            .withTranslation("Tubeworm")
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
                String path = id.getPath();
                if (path.endsWith("_planks")) {
                    path = path.substring(0, path.length() - 7);
                }

                ResourceLocation setId = new ResourceLocation(id.getNamespace(), path + "_" + setEntry.getKey().suffix());
                blockRegister.accept(setId, () -> setEntry.getValue().get());

                if (holder.hasItem()) {
                    itemRegister.accept(setId, () -> setEntry.getValue().getBlockItem().get());
                }
            }
        }
    }

    public static void registerBlockInteractions() {
        StrippableRegistry.register(CYPRESS_LOG.get(), STRIPPED_CYPRESS_LOG.get());
        StrippableRegistry.register(CYPRESS_WOOD.get(), STRIPPED_CYPRESS_WOOD.get());

        for (BlockDataHolder<?> holder : BLOCK_REGISTRY.values()) {
            if (holder.hasItem() && holder.isFuel()) {
                FuelRegistry.register(holder.getBlockItem().get(), holder.getFuelDuration());
            }

            for (Map.Entry<Block, FlammabilityRegistry.Entry> flammability : holder.getFlammabilities().entrySet()) {
                FlammabilityRegistry.getRegistry(flammability.getKey()).register(holder.get(), flammability.getValue());
            }

            if (holder.hasStrippingResult()) {
                StrippableRegistry.register(holder.get(), holder.getStrippingResult());
            }
        }
    }
}