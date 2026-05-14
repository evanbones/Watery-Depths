package com.evandev.watery_depths.registration.holders;

import com.evandev.watery_depths.mixin.accessor.*;
import com.evandev.watery_depths.registration.FlammabilityRegistry;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class BlockDataHolder<T extends Block> {
    private static final Map<TagKey<Block>, List<BlockDataHolder<?>>> BLOCK_TAGS = new HashMap<>();
    private static final List<BlockDataHolder<?>> CUTOUT_BLOCKS = new ArrayList<>();
    private final Supplier<T> entrySupplier;
    private final Map<Block, FlammabilityRegistry.Entry> FLAMMABILITIES = new HashMap<>();
    private final Map<Model, BlockDataHolder<?>> BLOCKSETS = new HashMap<>();
    private T cachedEntry;
    private ItemDataHolder<? extends Item> blockItem;
    private Model model;
    private String defaultTranslation;
    private Supplier<? extends Block> strippingResult;
    private int fuelDuration;
    private float compostChance;
    private Supplier<ItemLike> drop;
    private NumberProvider dropCount;
    private boolean isGlass;
    private BlockDataHolder<?> paneBlock;
    private Supplier<? extends Block> wallSignBlock;
    private Supplier<? extends Block> textureSourceBlock;

    public BlockDataHolder(Supplier<T> entrySupplier) {
        this.entrySupplier = entrySupplier;
    }

    public static BlockDataHolder<? extends Block> of(Supplier<?> blockSupplier) {
        return new BlockDataHolder(blockSupplier);
    }

    public static Map<TagKey<Block>, List<BlockDataHolder<?>>> getBlockTags() {
        return BLOCK_TAGS;
    }

    public static List<BlockDataHolder<?>> getCutoutBlocks() {
        return CUTOUT_BLOCKS;
    }

    public T get() {
        if (this.cachedEntry != null) return cachedEntry;

        T entry = entrySupplier.get();
        this.cachedEntry = entry;

        return entry;
    }

    public BlockDataHolder<?> withSign(Supplier<? extends Block> wallSign, Supplier<? extends Block> textureSource) {
        this.wallSignBlock = wallSign;
        this.textureSourceBlock = textureSource;
        return this.withModel(Model.SIGN);
    }

    public BlockDataHolder<?> withHangingSign(Supplier<? extends Block> wallSign, Supplier<? extends Block> textureSource) {
        this.wallSignBlock = wallSign;
        this.textureSourceBlock = textureSource;
        return this.withModel(Model.HANGING_SIGN);
    }

    public Block getWallSignBlock() {
        return wallSignBlock != null ? wallSignBlock.get() : null;
    }

    public Block getTextureSourceBlock() {
        return textureSourceBlock != null ? textureSourceBlock.get() : null;
    }

    public BlockDataHolder<? extends Block> withItem() {
        this.blockItem = ItemDataHolder.of(() -> new BlockItem(this.get(), new Item.Properties())).withModel(ModelTemplates.FLAT_ITEM);
        return this;
    }

    public BlockDataHolder<?> withStripping(Block stripResult) {
        this.strippingResult = () -> stripResult;
        return this;
    }

    public BlockDataHolder<?> withStripping(Supplier<? extends Block> stripResult) {
        this.strippingResult = stripResult;
        return this;
    }

    public boolean hasStrippingResult() {
        return this.strippingResult != null;
    }

    public Block getStrippingResult() {
        return this.strippingResult != null ? this.strippingResult.get() : null;
    }

    public BlockDataHolder<?> withFlammableDefault(FlammabilityRegistry.Entry flammabilityEntry) {
        return this.withFlammable(Blocks.FIRE, flammabilityEntry);
    }

    public BlockDataHolder<?> withFlammable(Block fireBlock, FlammabilityRegistry.Entry flammabilityEntry) {
        this.FLAMMABILITIES.put(fireBlock, flammabilityEntry);
        return this;
    }

    public Map<Block, FlammabilityRegistry.Entry> getFlammabilities() {
        return this.FLAMMABILITIES;
    }

    public BlockDataHolder<?> withFuel(int fuelDuration) {
        this.fuelDuration = fuelDuration;
        return this;
    }

    public boolean isFuel() {
        return this.fuelDuration > 0;
    }

    public int getFuelDuration() {
        return this.fuelDuration;
    }

    public BlockDataHolder<?> withCompost(float chance) {
        this.compostChance = chance;
        return this;
    }

    public boolean isCompostable() {
        return this.compostChance > 0;
    }

    public float getCompostChance() {
        return this.compostChance;
    }

    @SafeVarargs
    public final BlockDataHolder<?> withTags(TagKey<Block>... tags) {
        for (TagKey<Block> tag : tags) {
            BLOCK_TAGS.putIfAbsent(tag, new ArrayList<>());
            BLOCK_TAGS.get(tag).add(this);
            for (BlockDataHolder<?> block : this.getBlocksets().values())
                BLOCK_TAGS.get(tag).add(block);
        }

        return this;
    }

    public boolean hasItem() {
        return this.blockItem != null;
    }

    public ItemDataHolder<?> getBlockItem() {
        return this.blockItem;
    }

    public BlockDataHolder<?> withModel(Model model) {
        this.model = model;
        return this;
    }

    public boolean hasModel() {
        return this.model != null;
    }

    public Model getModel() {
        return this.model;
    }

    public BlockDataHolder<?> cutout() {
        CUTOUT_BLOCKS.add(this);
        return this;
    }

    public BlockDataHolder<?> glass() {
        this.isGlass = true;
        this.paneBlock = BlockDataHolder.of(() -> IronBarsBlockAccessor.createIronBarsBlock(BlockBehaviour.Properties.ofFullCopy(this.get()))).cutout().withItem();
        return this;
    }

    public BlockDataHolder<?> glass(DyeColor dye) {
        this.isGlass = true;
        this.paneBlock = BlockDataHolder.of(() -> new StainedGlassPaneBlock(dye, BlockBehaviour.Properties.ofFullCopy(this.get()))).cutout().withItem();
        return this;
    }

    public boolean isGlass() {
        return this.isGlass;
    }

    public BlockDataHolder<?> getPaneBlock() {
        return this.paneBlock;
    }

    public BlockDataHolder<?> withTranslation(String translation) {
        this.defaultTranslation = translation;
        return this;
    }

    public boolean hasTranslation() {
        return this.defaultTranslation != null;
    }

    public String getTranslation() {
        return this.defaultTranslation;
    }

    private String getBaseTranslation() {
        if (this.defaultTranslation == null) return null;
        return this.defaultTranslation.replace(" Planks", "").replace(" Bricks", " Brick").replace(" Tiles", " Tile");
    }

    public Map<Model, BlockDataHolder<?>> getBlocksets() {
        return this.BLOCKSETS;
    }

    public Supplier<ItemLike> getDrop() {
        return this.drop;
    }

    public NumberProvider getDropCount() {
        return this.dropCount;
    }

    public BlockDataHolder<?> dropsSelf() {
        this.drop = this::get;
        this.dropCount = ConstantValue.exactly(1);
        return this;
    }

    public BlockDataHolder<?> dropsSelf(int count) {
        this.drop = this::get;
        this.dropCount = ConstantValue.exactly(count);
        return this;
    }

    public final BlockDataHolder<?> dropsOther(Supplier<ItemLike> drop) {
        this.drop = drop;
        this.dropCount = ConstantValue.exactly(1);
        return this;
    }

    public final BlockDataHolder<?> dropsOther(Supplier<ItemLike> drop, int count) {
        this.drop = drop;
        this.dropCount = ConstantValue.exactly(count);
        return this;
    }

    public final BlockDataHolder<?> dropsOther(Supplier<ItemLike> drop, NumberProvider count) {
        this.drop = drop;
        this.dropCount = count;
        return this;
    }

    public BlockDataHolder<?> dropsWithSilk() {
        this.drop = this::get;
        this.dropCount = null;
        return this;
    }

    public boolean hasDrop() {
        return this.drop != null;
    }

    public BlockDataHolder<?> withStairs() {
        BlockDataHolder<?> stairs = BlockDataHolder.of(() -> StairBlockAccessor.createStairBlock(this.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(this.get())))
                .withModel(Model.STAIRS)
                .withItem()
                .withTags(BlockTags.STAIRS);
        if (this.hasTranslation()) stairs.withTranslation(this.getBaseTranslation() + " " + Model.STAIRS.getLang());
        this.BLOCKSETS.put(Model.STAIRS, stairs);
        return this;
    }

    public BlockDataHolder<?> getStairs() {
        return this.BLOCKSETS.get(Model.STAIRS);
    }

    public BlockDataHolder<?> withSlab() {
        BlockDataHolder<?> slab = BlockDataHolder.of(() -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(this.get())))
                .withModel(Model.SLAB)
                .withItem()
                .withTags(BlockTags.SLABS);
        if (this.hasTranslation()) slab.withTranslation(this.getBaseTranslation() + " " + Model.SLAB.getLang());
        this.BLOCKSETS.put(Model.SLAB, slab);
        return this;
    }

    public BlockDataHolder<?> getSlab() {
        return this.BLOCKSETS.get(Model.SLAB);
    }

    public BlockDataHolder<?> withWall() {
        BlockDataHolder<?> wall = BlockDataHolder.of(() -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(this.get())))
                .withModel(Model.WALL)
                .withItem()
                .withTags(BlockTags.WALLS);
        if (this.hasTranslation()) wall.withTranslation(this.getBaseTranslation() + " " + Model.WALL.getLang());
        this.BLOCKSETS.put(Model.WALL, wall);
        return this;
    }

    public BlockDataHolder<?> getWall() {
        return this.BLOCKSETS.get(Model.WALL);
    }

    public BlockDataHolder<?> withButton(BlockSetType type, int ticksPressed, boolean arrowCanPress) {
        BlockDataHolder<?> button = BlockDataHolder.of(() -> ButtonBlockAccessor.createButtonBlock(type, ticksPressed, BlockBehaviour.Properties.ofFullCopy(this.get()).noCollission()))
                .withModel(Model.BUTTON)
                .withItem()
                .withTags(BlockTags.BUTTONS);
        if (this.hasTranslation()) button.withTranslation(this.getBaseTranslation() + " " + Model.BUTTON.getLang());
        this.BLOCKSETS.put(Model.BUTTON, button);
        return this;
    }

    public BlockDataHolder<?> getButton() {
        return this.BLOCKSETS.get(Model.BUTTON);
    }

    public BlockDataHolder<?> withPressurePlate(BlockSetType type) {
        BlockDataHolder<?> pressurePlate = BlockDataHolder.of(() -> PressurePlateBlockAccessor.createPressurePlateBlock(type, BlockBehaviour.Properties.ofFullCopy(this.get()).noCollission()))
                .withModel(Model.PRESSURE_PLATE)
                .withItem()
                .withTags(BlockTags.PRESSURE_PLATES);
        if (this.hasTranslation())
            pressurePlate.withTranslation(this.getBaseTranslation() + " " + Model.PRESSURE_PLATE.getLang());
        this.BLOCKSETS.put(Model.PRESSURE_PLATE, pressurePlate);
        return this;
    }

    public BlockDataHolder<?> getPressurePlate() {
        return this.BLOCKSETS.get(Model.PRESSURE_PLATE);
    }

    public BlockDataHolder<?> withFence() {
        BlockDataHolder<?> fence = BlockDataHolder.of(() -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(this.get())))
                .withModel(Model.FENCE)
                .withItem()
                .withTags(BlockTags.FENCES);
        if (this.hasTranslation()) fence.withTranslation(this.getBaseTranslation() + " " + Model.FENCE.getLang());
        this.BLOCKSETS.put(Model.FENCE, fence);
        return this;
    }

    public BlockDataHolder<?> getFence() {
        return this.BLOCKSETS.get(Model.FENCE);
    }

    public BlockDataHolder<?> withFenceGate(WoodType woodType) {
        BlockDataHolder<?> fenceGate = BlockDataHolder.of(() -> new FenceGateBlock(woodType, BlockBehaviour.Properties.ofFullCopy(this.get())))
                .withModel(Model.FENCE_GATE)
                .withItem()
                .withTags(BlockTags.FENCE_GATES);
        if (this.hasTranslation())
            fenceGate.withTranslation(this.getBaseTranslation() + " " + Model.FENCE_GATE.getLang());
        this.BLOCKSETS.put(Model.FENCE_GATE, fenceGate);
        return this;
    }

    public BlockDataHolder<?> getFenceGate() {
        return this.BLOCKSETS.get(Model.FENCE_GATE);
    }

    public BlockDataHolder<?> withDoor(BlockSetType type) {
        BlockDataHolder<?> door = BlockDataHolder.of(() -> DoorBlockAccessor.createDoorBlock(type, BlockBehaviour.Properties.ofFullCopy(this.get()).noOcclusion()))
                .withModel(Model.DOOR)
                .cutout()
                .withItem()
                .withTags(BlockTags.DOORS);
        if (this.hasTranslation()) door.withTranslation(this.getBaseTranslation() + " " + Model.DOOR.getLang());
        this.BLOCKSETS.put(Model.DOOR, door);
        return this;
    }

    public BlockDataHolder<?> getDoor() {
        return this.BLOCKSETS.get(Model.DOOR);
    }

    public BlockDataHolder<?> withTrapdoor(BlockSetType type) {
        BlockDataHolder<?> trapdoor = BlockDataHolder.of(() -> TrapDoorBlockAccessor.createTrapDoorBlock(type, BlockBehaviour.Properties.ofFullCopy(this.get()).noOcclusion()))
                .withModel(Model.TRAPDOOR)
                .cutout()
                .withItem()
                .withTags(BlockTags.TRAPDOORS);
        if (this.hasTranslation()) trapdoor.withTranslation(this.getBaseTranslation() + " " + Model.TRAPDOOR.getLang());
        this.BLOCKSETS.put(Model.TRAPDOOR, trapdoor);
        return this;
    }

    public BlockDataHolder<?> getTrapdoor() {
        return this.BLOCKSETS.get(Model.TRAPDOOR);
    }

    public BlockDataHolder<? extends Block> withCustomItem(Function<T, BlockItem> itemFactory) {
        this.blockItem = ItemDataHolder.of(() -> itemFactory.apply(this.get()))
                .withModel(ModelTemplates.FLAT_ITEM);
        return this;
    }

    public enum Model {
        CUBE("", ""),
        CUBE_BOTTOM_TOP("", ""),
        NYLIUM("", ""),
        CUBE_ALL_TOP_TEXTURE("", ""),
        PILLAR("", ""),
        WOOD("", ""),
        ROTATABLE("", ""),
        CROSS("", ""),
        DOOR("door", "Door"),
        TRAPDOOR("trapdoor", "Trapdoor"),
        STAIRS("stairs", "Stairs"),
        SLAB("slab", "Slab"),
        WALL("wall", "Wall"),
        BUTTON("button", "Button"),
        PRESSURE_PLATE("pressure_plate", "Pressure Plate"),
        FENCE("fence", "Fence"),
        FENCE_GATE("fence_gate", "Fence Gate"),
        FLOWER_POT("flower_pot", "Flower Pot"),
        SIGN("sign", "Sign"),
        HANGING_SIGN("hanging_sign", "Hanging Sign"),
        WALL_SIGN("wall_sign", "Wall Sign"),
        WALL_HANGING_SIGN("wall_hanging_sign", "Wall Hanging Sign"),
        CUSTOM("", ""),
        DOUBLE_CROSS("", "");

        private final String suffix;
        private final String lang;

        Model(String suffix, String lang) {
            this.suffix = suffix;
            this.lang = lang;
        }

        public String suffix() {
            return this.suffix;
        }

        public String getLang() {
            return this.lang;
        }
    }
}