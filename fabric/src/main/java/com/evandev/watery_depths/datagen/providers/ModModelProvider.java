package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.content.ModBlocks;
import com.evandev.watery_depths.content.ModItems;
import com.evandev.watery_depths.registration.holders.BlockDataHolder;
import com.evandev.watery_depths.registration.holders.ItemDataHolder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Map;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators gen) {
        for (BlockDataHolder<?> holder : ModBlocks.getBlockRegistry().values()) {
            if (!holder.hasModel()) continue;

            Block block = holder.get();
            TextureMapping baseMapping;

            switch (holder.getModel()) {
                case NYLIUM -> {
                    ResourceLocation bottomTexture;
                    if (holder == ModBlocks.ALGAL_SAND) {
                        bottomTexture = ResourceLocation.withDefaultNamespace("block/sand");
                    } else if (holder == ModBlocks.ALGAL_GRAVEL) {
                        bottomTexture = ResourceLocation.withDefaultNamespace("block/gravel");
                    } else {
                        bottomTexture = ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/silt");
                    }
                    baseMapping = new TextureMapping()
                            .put(TextureSlot.TOP, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/algae"))
                            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                            .put(TextureSlot.BOTTOM, bottomTexture)
                            .put(TextureSlot.WALL, TextureMapping.getBlockTexture(block));
                }
                case PILLAR -> {
                    ResourceLocation side;
                    ResourceLocation top;
                    String path = BuiltInRegistries.BLOCK.getKey(block).getPath();

                    if (path.endsWith("_wood")) {
                        side = TextureMapping.getBlockTexture(block).withPath(p -> p.replace("_wood", "_log"));
                        top = side;
                    } else {
                        side = TextureMapping.getBlockTexture(block);
                        top = TextureMapping.getBlockTexture(block, "_top");
                    }
                    baseMapping = TextureMapping.column(side, top)
                            .put(TextureSlot.WALL, side);
                }
                case CUBE_BOTTOM_TOP -> baseMapping = new TextureMapping()
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block))
                        .put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top"))
                        .put(TextureSlot.BOTTOM, TextureMapping.getBlockTexture(block, "_bottom"))
                        .put(TextureSlot.WALL, TextureMapping.getBlockTexture(block));
                default -> baseMapping = TextureMapping.cube(block)
                        .put(TextureSlot.WALL, TextureMapping.getBlockTexture(block));
            }

            switch (holder.getModel()) {
                case CUBE -> gen.createTrivialCube(block);
                case CUBE_BOTTOM_TOP, NYLIUM -> {
                    ResourceLocation model = ModelTemplates.CUBE_BOTTOM_TOP.create(block, baseMapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
                    if (holder.hasItem()) {
                        gen.delegateItemModel(block, model);
                    }
                }
                case PILLAR -> {
                    ResourceLocation model = ModelTemplates.CUBE_COLUMN.create(block, baseMapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(block, model));
                    if (holder.hasItem()) {
                        gen.delegateItemModel(block, model);
                    }
                }
                case CROSS -> {
                    if (block.defaultBlockState().hasProperty(BlockStateProperties.FACING)) {
                        ResourceLocation crossModel = ModelTemplates.CROSS.create(block, TextureMapping.cross(block), gen.modelOutput);
                        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                                .with(PropertyDispatch.property(BlockStateProperties.FACING)
                                        .select(Direction.UP, Variant.variant().with(VariantProperties.MODEL, crossModel))
                                        .select(Direction.DOWN, Variant.variant().with(VariantProperties.MODEL, crossModel).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
                                        .select(Direction.NORTH, Variant.variant().with(VariantProperties.MODEL, crossModel).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
                                        .select(Direction.SOUTH, Variant.variant().with(VariantProperties.MODEL, crossModel).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                                        .select(Direction.WEST, Variant.variant().with(VariantProperties.MODEL, crossModel).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                                        .select(Direction.EAST, Variant.variant().with(VariantProperties.MODEL, crossModel).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                                )
                        );
                    } else {
                        gen.createCrossBlock(block, BlockModelGenerators.TintState.NOT_TINTED);
                    }

                    if (holder.hasItem()) {
                        ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(block.asItem()),
                                TextureMapping.layer0(TextureMapping.getBlockTexture(block)),
                                gen.modelOutput
                        );
                    }
                }
                case CUSTOM ->
                        gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(block).getPath())));
                case DOUBLE_CROSS -> {
                    ResourceLocation top = ModelTemplates.CROSS.create(BuiltInRegistries.BLOCK.getKey(block).withSuffix("_top"), TextureMapping.cross(TextureMapping.getBlockTexture(block, "_top")), gen.modelOutput);
                    ResourceLocation bottom = ModelTemplates.CROSS.create(BuiltInRegistries.BLOCK.getKey(block).withSuffix("_bottom"), TextureMapping.cross(TextureMapping.getBlockTexture(block, "_bottom")), gen.modelOutput);
                    gen.createDoubleBlock(block, top, bottom);

                    if (holder.hasItem()) {
                        ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(block.asItem()),
                                TextureMapping.layer0(TextureMapping.getBlockTexture(block, "_top")),
                                gen.modelOutput
                        );
                    }
                }
                case SIGN -> {
                    TextureMapping textureMapping = TextureMapping.particle(holder.getTextureSourceBlock());
                    ResourceLocation resourceLocation = ModelTemplates.PARTICLE_ONLY.create(block, textureMapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, resourceLocation));
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(holder.getWallSignBlock(), resourceLocation));
                    gen.skipAutoItemBlock(holder.getWallSignBlock());
                }
                case HANGING_SIGN ->
                        gen.createHangingSign(holder.getTextureSourceBlock(), block, holder.getWallSignBlock());
                default -> {
                }
            }

            if (holder.getStairs() != null) {
                Block stairs = holder.getStairs().get();
                ResourceLocation inner = ModelTemplates.STAIRS_INNER.create(stairs, baseMapping, gen.modelOutput);
                ResourceLocation straight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, baseMapping, gen.modelOutput);
                ResourceLocation outer = ModelTemplates.STAIRS_OUTER.create(stairs, baseMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, inner, straight, outer));
                gen.delegateItemModel(stairs, straight);
            }

            if (holder.getSlab() != null) {
                Block slab = holder.getSlab().get();
                ResourceLocation bottom = ModelTemplates.SLAB_BOTTOM.create(slab, baseMapping, gen.modelOutput);
                ResourceLocation top = ModelTemplates.SLAB_TOP.create(slab, baseMapping, gen.modelOutput);
                ResourceLocation full = ModelLocationUtils.getModelLocation(block);
                gen.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, bottom, top, full));
                gen.delegateItemModel(slab, bottom);
            }

            if (holder.getWall() != null) {
                Block wall = holder.getWall().get();
                ResourceLocation post = ModelTemplates.WALL_POST.create(wall, baseMapping, gen.modelOutput);
                ResourceLocation side = ModelTemplates.WALL_LOW_SIDE.create(wall, baseMapping, gen.modelOutput);
                ResourceLocation sideTall = ModelTemplates.WALL_TALL_SIDE.create(wall, baseMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createWall(wall, post, side, sideTall));
                ModelTemplates.WALL_INVENTORY.create(ModelLocationUtils.getModelLocation(wall.asItem()), baseMapping, gen.modelOutput);
            }

            if (holder.getFence() != null) {
                Block fence = holder.getFence().get();
                ResourceLocation post = ModelTemplates.FENCE_POST.create(fence, baseMapping, gen.modelOutput);
                ResourceLocation side = ModelTemplates.FENCE_SIDE.create(fence, baseMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createFence(fence, post, side));
                ModelTemplates.FENCE_INVENTORY.create(ModelLocationUtils.getModelLocation(fence.asItem()), baseMapping, gen.modelOutput);
            }

            if (holder.getFenceGate() != null) {
                Block gate = holder.getFenceGate().get();
                ResourceLocation open = ModelTemplates.FENCE_GATE_OPEN.create(gate, baseMapping, gen.modelOutput);
                ResourceLocation closed = ModelTemplates.FENCE_GATE_CLOSED.create(gate, baseMapping, gen.modelOutput);
                ResourceLocation openWall = ModelTemplates.FENCE_GATE_WALL_OPEN.create(gate, baseMapping, gen.modelOutput);
                ResourceLocation closedWall = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(gate, baseMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createFenceGate(gate, open, closed, openWall, closedWall, false));
                gen.delegateItemModel(gate, closed);
            }

            if (holder.getButton() != null) {
                Block button = holder.getButton().get();
                ResourceLocation btn = ModelTemplates.BUTTON.create(button, baseMapping, gen.modelOutput);
                ResourceLocation btnPressed = ModelTemplates.BUTTON_PRESSED.create(button, baseMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createButton(button, btn, btnPressed));
                ModelTemplates.BUTTON_INVENTORY.create(ModelLocationUtils.getModelLocation(button.asItem()), baseMapping, gen.modelOutput);
            }

            if (holder.getPressurePlate() != null) {
                Block plate = holder.getPressurePlate().get();
                ResourceLocation up = ModelTemplates.PRESSURE_PLATE_UP.create(plate, baseMapping, gen.modelOutput);
                ResourceLocation down = ModelTemplates.PRESSURE_PLATE_DOWN.create(plate, baseMapping, gen.modelOutput);
                gen.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(plate, up, down));
                gen.delegateItemModel(plate, up);
            }

            if (holder.getDoor() != null) {
                gen.createDoor(holder.getDoor().get());
            }

            if (holder.getTrapdoor() != null) {
                gen.createTrapdoor(holder.getTrapdoor().get());
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : ModItems.getItemRegistry().entrySet()) {
            ResourceLocation id = entry.getKey();
            ItemDataHolder<?> holder = entry.getValue();

            if (id.getPath().endsWith("_hanging_sign")) {
                continue;
            }

            if (holder.getModel() != null) {
                gen.generateFlatItem(holder.get(), holder.getModel());
            }
        }
    }
}