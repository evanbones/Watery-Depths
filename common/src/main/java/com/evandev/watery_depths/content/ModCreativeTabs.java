package com.evandev.watery_depths.content;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WATERY_DEPTHS_TAB =
            TABS.register(Constants.MOD_ID, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
                    .title(Component.translatable("itemgroup.watery_depths"))
                    .icon(() -> new ItemStack(ModItems.TAB_ICON.get()))
                    .displayItems((itemDisplayParameters, entries) -> {
                        entries.accept(ModBlocks.SILT.get());
                        entries.accept(ModBlocks.DEEPSILT.get());
                        entries.accept(ModBlocks.ALGAL_SILT.get());
                        entries.accept(ModBlocks.ALGAL_SAND.get());
                        entries.accept(ModBlocks.ALGAL_GRAVEL.get());
                        entries.accept(ModBlocks.ALGAE.get());

                        entries.accept(ModBlocks.ACHROMARINE.get());
                        entries.accept(ModBlocks.ACHROMARINE.getStairs().get());
                        entries.accept(ModBlocks.ACHROMARINE.getSlab().get());
                        entries.accept(ModBlocks.ACHROMARINE.getWall().get());

                        entries.accept(ModBlocks.CHROMARINE.get());
                        entries.accept(ModBlocks.CHROMARINE.getStairs().get());
                        entries.accept(ModBlocks.CHROMARINE.getSlab().get());
                        entries.accept(ModBlocks.CHROMARINE.getWall().get());

                        entries.accept(ModBlocks.PRISMARINE_TILES.get());
                        entries.accept(ModBlocks.PRISMARINE_TILES.getStairs().get());
                        entries.accept(ModBlocks.PRISMARINE_TILES.getSlab().get());
                        entries.accept(ModBlocks.PRISMARINE_TILES.getWall().get());

                        entries.accept(ModBlocks.DARK_PRISMARINE_BRICKS.get());
                        entries.accept(ModBlocks.DARK_PRISMARINE_BRICKS.getStairs().get());
                        entries.accept(ModBlocks.DARK_PRISMARINE_BRICKS.getSlab().get());
                        entries.accept(ModBlocks.DARK_PRISMARINE_BRICKS.getWall().get());

                        entries.accept(ModBlocks.BUDDING_PRISMARINE.get());

                        entries.accept(ModBlocks.PRISMARINE_CLUSTER.get());
                        entries.accept(ModBlocks.LARGE_PRISMARINE_BUD.get());
                        entries.accept(ModBlocks.MEDIUM_PRISMARINE_BUD.get());
                        entries.accept(ModBlocks.SMALL_PRISMARINE_BUD.get());

                        entries.accept(ModBlocks.SULFIDE.get());
                        entries.accept(ModBlocks.SULFIDE_VENT.get());
                        entries.accept(ModBlocks.CALCITE_VENT.get());

                        entries.accept(ModBlocks.CYPRESS_LOG.get());
                        entries.accept(ModBlocks.STRIPPED_CYPRESS_LOG.get());
                        entries.accept(ModBlocks.CYPRESS_WOOD.get());
                        entries.accept(ModBlocks.STRIPPED_CYPRESS_WOOD.get());

                        entries.accept(ModBlocks.CYPRESS_PLANKS.get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getStairs().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getSlab().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getFence().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getFenceGate().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getDoor().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getTrapdoor().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getPressurePlate().get());
                        entries.accept(ModBlocks.CYPRESS_PLANKS.getButton().get());

                        entries.accept(ModBlocks.CYPRESS_SAPLING.get());
                        entries.accept(ModItems.CYPRESS_SIGN.get());
                        entries.accept(ModItems.CYPRESS_HANGING_SIGN.get());
                        entries.accept(ModItems.CYPRESS_BOAT.get());
                        entries.accept(ModItems.CYPRESS_CHEST_BOAT.get());
                        entries.accept(ModBlocks.CYPRESS_ROOTS.get());
                        entries.accept(ModBlocks.CYPRESS_LEAVES.get());

                        entries.accept(ModBlocks.DUCKWEED.get());
                        entries.accept(ModBlocks.CHROMATIC_BRAMBLE.get());
                        entries.accept(ModBlocks.TALL_CHROMATIC_BRAMBLE.get());
                        entries.accept(ModBlocks.ACHROMATIC_BRAMBLE.get());
                        entries.accept(ModBlocks.TALL_ACHROMATIC_BRAMBLE.get());

                        entries.accept(ModBlocks.TUBEWORM.get());

                        entries.accept(ModBlocks.DRIED_SILT.get());
                        entries.accept(ModBlocks.DRIED_SILT.getStairs().get());
                        entries.accept(ModBlocks.DRIED_SILT.getSlab().get());
                        entries.accept(ModBlocks.DRIED_SILT.getWall().get());
                        entries.accept(ModBlocks.CHISELED_DRIED_SILT.get());
                        entries.accept(ModBlocks.SMOOTH_DRIED_SILT.get());
                        entries.accept(ModBlocks.SMOOTH_DRIED_SILT.getStairs().get());
                        entries.accept(ModBlocks.SMOOTH_DRIED_SILT.getSlab().get());
                        entries.accept(ModBlocks.CUT_DRIED_SILT.get());
                        entries.accept(ModBlocks.CUT_DRIED_SILT.getSlab().get());

                        entries.accept(ModBlocks.DRIED_DEEPSILT.get());
                        entries.accept(ModBlocks.DRIED_DEEPSILT.getStairs().get());
                        entries.accept(ModBlocks.DRIED_DEEPSILT.getSlab().get());
                        entries.accept(ModBlocks.DRIED_DEEPSILT.getWall().get());
                        entries.accept(ModBlocks.CHISELED_DRIED_DEEPSILT.get());
                        entries.accept(ModBlocks.SMOOTH_DRIED_DEEPSILT.get());
                        entries.accept(ModBlocks.SMOOTH_DRIED_DEEPSILT.getStairs().get());
                        entries.accept(ModBlocks.SMOOTH_DRIED_DEEPSILT.getSlab().get());
                        entries.accept(ModBlocks.CUT_DRIED_DEEPSILT.get());
                        entries.accept(ModBlocks.CUT_DRIED_DEEPSILT.getSlab().get());

                        entries.accept(ModBlocks.SILT_BRICKS.get());
                        entries.accept(ModBlocks.SILT_BRICKS.getStairs().get());
                        entries.accept(ModBlocks.SILT_BRICKS.getSlab().get());
                        entries.accept(ModBlocks.SILT_BRICKS.getWall().get());

                        entries.accept(ModBlocks.DEEPSILT_BRICKS.get());
                        entries.accept(ModBlocks.DEEPSILT_BRICKS.getStairs().get());
                        entries.accept(ModBlocks.DEEPSILT_BRICKS.getSlab().get());
                        entries.accept(ModBlocks.DEEPSILT_BRICKS.getWall().get());

                        entries.accept(ModBlocks.DRIED_SILT_PILLAR.get());
                        entries.accept(ModBlocks.DRIED_DEEPSILT_PILLAR.get());

                        entries.accept(ModItems.CATFISH.get());
                        entries.accept(ModItems.CATFISH_BUCKET.get());
                        entries.accept(ModItems.CATFISH_SPAWN_EGG.get());
                    }).build());

    public static void load() {
    }
}