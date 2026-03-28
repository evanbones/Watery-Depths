package com.evandev.watery_depths.datagen.providers;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.module.ModBlocks;
import com.evandev.watery_depths.module.ModItems;
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
            if (holder.hasModel()) {
                TextureMapping baseMapping = TextureMapping.cube(holder.get());

                if (holder.getModel() == BlockDataHolder.Model.CUBE) {
                    gen.createTrivialCube(holder.get());
                } else if (holder.getModel() == BlockDataHolder.Model.NYLIUM) {
                    ResourceLocation bottomTexture;
                    if (holder == ModBlocks.ALGAL_SAND) {
                        bottomTexture = new ResourceLocation("minecraft", "block/sand");
                    } else if (holder == ModBlocks.ALGAL_GRAVEL) {
                        bottomTexture = new ResourceLocation("minecraft", "block/gravel");
                    } else {
                        bottomTexture = new ResourceLocation(Constants.MOD_ID, "block/silt");
                    }

                    TextureMapping mapping = new TextureMapping()
                            .put(TextureSlot.TOP, new ResourceLocation(Constants.MOD_ID, "block/algae"))
                            .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(holder.get()))
                            .put(TextureSlot.BOTTOM, bottomTexture);

                    ResourceLocation customModel = ModelTemplates.CUBE_BOTTOM_TOP.create(holder.get(), mapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(holder.get(), customModel));
                } else if (holder.getModel() == BlockDataHolder.Model.PILLAR) {
                    ResourceLocation side;
                    ResourceLocation top;
                    String path = BuiltInRegistries.BLOCK.getKey(holder.get()).getPath();

                    if (path.endsWith("_wood")) {
                        side = TextureMapping.getBlockTexture(holder.get()).withPath(p -> p.replace("_wood", "_log"));
                        top = side;
                    } else {
                        side = TextureMapping.getBlockTexture(holder.get());
                        top = TextureMapping.getBlockTexture(holder.get(), "_top");
                    }

                    TextureMapping mapping = TextureMapping.column(side, top);
                    ResourceLocation model = ModelTemplates.CUBE_COLUMN.create(holder.get(), mapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(holder.get(), model));
                    if (holder.hasItem()) {
                        gen.delegateItemModel(holder.get(), model);
                    }
                } else if (holder.getModel() == BlockDataHolder.Model.CROSS) {
                    if (holder.get().defaultBlockState().hasProperty(BlockStateProperties.FACING)) {
                        ResourceLocation crossModel = ModelTemplates.CROSS.create(holder.get(), TextureMapping.cross(holder.get()), gen.modelOutput);
                        gen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(holder.get())
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
                        gen.createCrossBlock(holder.get(), BlockModelGenerators.TintState.NOT_TINTED);
                    }

                    if (holder.hasItem()) {
                        ModelTemplates.FLAT_ITEM.create(
                                ModelLocationUtils.getModelLocation(holder.get().asItem()),
                                TextureMapping.layer0(TextureMapping.getBlockTexture(holder.get())),
                                gen.modelOutput
                        );
                    }
                } else if (holder.getModel() == BlockDataHolder.Model.CUSTOM) {
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(holder.get(), new ResourceLocation(Constants.MOD_ID, "block/" + BuiltInRegistries.BLOCK.getKey(holder.get()).getPath())));
                } else if (holder.getModel() == BlockDataHolder.Model.DOUBLE_CROSS) {
                    ResourceLocation top = ModelTemplates.CROSS.create(BuiltInRegistries.BLOCK.getKey(holder.get()).withSuffix("_top"), TextureMapping.cross(TextureMapping.getBlockTexture(holder.get(), "_top")), gen.modelOutput);
                    ResourceLocation bottom = ModelTemplates.CROSS.create(BuiltInRegistries.BLOCK.getKey(holder.get()).withSuffix("_bottom"), TextureMapping.cross(TextureMapping.getBlockTexture(holder.get(), "_bottom")), gen.modelOutput);
                    gen.createDoubleBlock(holder.get(), top, bottom);

                    if (holder.hasItem()) {
                        ModelTemplates.FLAT_ITEM.create(
                                net.minecraft.data.models.model.ModelLocationUtils.getModelLocation(holder.get().asItem()),
                                TextureMapping.layer0(TextureMapping.getBlockTexture(holder.get(), "_top")),
                                gen.modelOutput
                        );
                    }
                } else if (holder.getModel() == BlockDataHolder.Model.SIGN) {
                    TextureMapping textureMapping = TextureMapping.particle(holder.getTextureSourceBlock());
                    ResourceLocation resourceLocation = ModelTemplates.PARTICLE_ONLY.create(holder.get(), textureMapping, gen.modelOutput);
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(holder.get(), resourceLocation));
                    gen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(holder.getWallSignBlock(), resourceLocation));
                    gen.skipAutoItemBlock(holder.getWallSignBlock());
                } else if (holder.getModel() == BlockDataHolder.Model.HANGING_SIGN) {
                    gen.createHangingSign(holder.getTextureSourceBlock(), holder.get(), holder.getWallSignBlock());
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
                    ResourceLocation full = ModelLocationUtils.getModelLocation(holder.get());
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