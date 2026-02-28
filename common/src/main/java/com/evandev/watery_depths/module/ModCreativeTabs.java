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
        entries.accept(ModBlocks.ALGAE.get());
        entries.accept(ModBlocks.ALGAL_GRAVEL.get());
        entries.accept(ModBlocks.ALGAL_SAND.get());
        entries.accept(ModBlocks.ALGAL_SILT.get());

    }).build());

    public static void load() {
    }
}