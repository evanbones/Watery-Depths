package com.evandev.watery_depths.module;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeTabs {
    /**
     * The provider for the mod creative tabs.
     */
    public static final RegistrationProvider<CreativeModeTab> TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final RegistryObject<CreativeModeTab> WATERY_DEPTHS_TAB = TABS.register(Constants.MOD_ID, () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0).title(Component.translatable("itemgroup.watery_depths")).icon(() -> new ItemStack(ModItems.TAB_ICON.get())).displayItems((itemDisplayParameters, entries) -> {

        entries.accept(ModBlocks.SILT.get());
        entries.accept(ModBlocks.DEEPSILT.get());
        entries.accept(ModBlocks.ALGAL_SILT.get());
        entries.accept(ModBlocks.ALGAL_SAND.get());
        entries.accept(ModBlocks.ALGAL_GRAVEL.get());
        entries.accept(ModBlocks.ALGAE.get());

        entries.accept(ModBlocks.ACHROMARINE.get());
        entries.accept(ModBlocks.CHROMARINE.get());
        entries.accept(ModBlocks.PRISMARINE_TILES.get());
        entries.accept(ModBlocks.DARK_PRISMARINE_BRICKS.get());
        entries.accept(ModBlocks.BUDDING_PRISMARINE.get());

        entries.accept(ModBlocks.PRISMARINE_CLUSTER.get());
        entries.accept(ModBlocks.LARGE_PRISMARINE_BUD.get());
        entries.accept(ModBlocks.MEDIUM_PRISMARINE_BUD.get());
        entries.accept(ModBlocks.SMALL_PRISMARINE_BUD.get());

        entries.accept(ModBlocks.SULFIDE.get());
        entries.accept(ModBlocks.SULFIDE_VENT.get());
        entries.accept(ModBlocks.CALCITE_VENT.get());

        entries.accept(ModBlocks.CYPRESS_LOG.get());
        entries.accept(ModBlocks.CYPRESS_ROOTS.get());
        entries.accept(ModBlocks.CYPRESS_LEAVES.get());

        entries.accept(ModBlocks.DUCKWEED.get());
        entries.accept(ModBlocks.CHROMATIC_BRAMBLE.get());
        entries.accept(ModBlocks.TALL_CHROMATIC_BRAMBLE.get());
        entries.accept(ModBlocks.ACHROMATIC_BRAMBLE.get());
        entries.accept(ModBlocks.TALL_ACHROMATIC_BRAMBLE.get());

        entries.accept(ModBlocks.TUBEWORM.get());

        entries.accept(ModItems.CATFISH.get());
        entries.accept(ModItems.CATFISH_BUCKET.get());

    }).build());

    public static void load() {
    }
}