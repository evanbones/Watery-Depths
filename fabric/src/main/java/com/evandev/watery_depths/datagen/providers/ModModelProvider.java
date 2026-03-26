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
                    ResourceLocation side = TextureMapping.getBlockTexture(holder.get(), "_side");
                    ResourceLocation top = TextureMapping.getBlockTexture(holder.get(), "_top");
                    TextureMapping mapping = TextureMapping.column(side, top);
                    gen.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(holder.get(), ModelTemplates.CUBE_COLUMN.create(holder.get(), mapping, gen.modelOutput)));
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
                }
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators gen) {
        for (Map.Entry<ResourceLocation, ItemDataHolder<?>> entry : ModItems.getItemRegistry().entrySet()) {
            ItemDataHolder<?> holder = entry.getValue();

            if (holder.getModel() != null) {
                gen.generateFlatItem(holder.get(), holder.getModel());
            }
        }
    }
}